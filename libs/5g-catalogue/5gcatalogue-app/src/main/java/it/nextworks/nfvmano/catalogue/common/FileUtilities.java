/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.catalogue.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Class with static methods to download, remove, unzip files
 * Used for MEC AppD
 * 
 * @author nextworks
 *
 */
@Service
public class FileUtilities {

	private static final Logger log = LoggerFactory.getLogger(FileUtilities.class);
	
	@Value("${environment.tmpDir}")
	private String localTmpDir;
	
	public FileUtilities() { }
	
	/**
	 * Method to download a file in the local tmp dir
	 * 
	 * @param remoteFilePath URL of the remote file to be downloaded
	 * @return the name of the downloaded file. The name is chosen in a random manner.
	 * @throws MalformedURLException if the URL is malformed
	 * @throws IOException if there is an IO exception
	 */
	public String downloadFile(String remoteFilePath) throws MalformedURLException, IOException {
		log.debug("Downloading file from " + remoteFilePath + " in " + localTmpDir);
		String fileName = UUID.randomUUID().toString() + ".tar";
		String dstFilePath = localTmpDir + "/" + fileName;
		File destination = new File(dstFilePath);
		FileUtils.copyURLToFile(new URL(remoteFilePath), destination);
		log.debug("File downloaded in " + dstFilePath);
		return fileName;
	}
	
	/**
	 * Method to extract a .tar file placed in the local tmp dir 
	 * 
	 * @param origFile	file to be extracted. It must be in .tar format
	 * @return the name of the folder within the local tmp dir where the file has been extracted
	 * @throws IllegalStateException if the action cannot be executed
	 * @throws FileNotFoundException if the file is not present
	 * @throws ArchiveException if the archive format is wrong
	 * @throws IOException in case of a general IO exception
	 */
	public String extractFile(String origFile) throws IllegalStateException, FileNotFoundException, ArchiveException, IOException {
		log.debug("Extracting file " + origFile + " in " + localTmpDir);
		String fileName = localTmpDir + "/" + origFile;
		
		String[] parts = origFile.split("\\.");
		String outputDir = localTmpDir + "/" + parts[0];
		log.debug("Creating folder " + outputDir);
		new File(outputDir).mkdir();		
		
		InputStream is = new FileInputStream(new File(fileName));
		TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", is);
		TarArchiveEntry entry = null;
		while ((entry = (TarArchiveEntry)debInputStream.getNextTarEntry()) != null) {
			File outputFile = new File(outputDir, entry.getName());
			if (entry.isDirectory()) {
				log.info(String.format("Attempting to write output directory %s.", outputFile.getAbsolutePath()));
				if (!outputFile.exists()) {
	                log.info(String.format("Attempting to create output directory %s.", outputFile.getAbsolutePath()));
	                if (!outputFile.mkdirs()) {
	                    throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
	                }
	            }
			} else {
				log.info(String.format("Creating output file %s.", outputFile.getAbsolutePath()));
	            final OutputStream outputFileStream = new FileOutputStream(outputFile); 
	            IOUtils.copy(debInputStream, outputFileStream);
	            outputFileStream.close();
			}
		}
		debInputStream.close();
		log.debug("File extracted");
		return parts[0];
	}
	
	/**
	 * Returns the json file from the given folder within the local tmp dir.
	 * 
	 * @param inputDir	folder in the local tmp dir where the file must be searched for. The folder must include a single json file.
	 * @return the json file
	 * @throws FileNotFoundException if the file is not found
	 * @throws IllegalStateException if more than one json file is found
	 */
	public File findJsonFileInDir(String inputDir) throws FileNotFoundException, IllegalStateException {
		log.debug("Searching json file in " + inputDir);
		String absoluteDirPath = localTmpDir + "/" + inputDir;
		
		File folder = new File(absoluteDirPath);
		File[] listOfFiles = folder.listFiles();

		int jsonIndex = 0;
		int foundJsonIndex = 0;
		boolean found = false;
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().endsWith(".json")) {
					log.debug("Found json file " + listOfFiles[i].getName());
					found = true;
					jsonIndex++;
					if (jsonIndex > 1) {
						log.error("More than one json file found - bad .tar format");
						throw new IllegalStateException("More than one json file found - bad .tar format");
					}
					foundJsonIndex = i;
				}
			}
		}
		
		if (found == true) {
			log.debug("Found unique json file. Returning it.");
			return listOfFiles[foundJsonIndex];
		} else {
			log.error("Json file not found - bad .tar format");
			throw new FileNotFoundException("Json file not found - bad .tar format");
		}
	}
	
	/**
	 * Method to remove a file from the local tmp dir
	 * 
	 * @param fileName name of the file to be removed
	 * @param folderName name of the folder to be removed
	 * @throws IOException in case of a general IO exception
	 */
	public void removeFileAndFolder(String fileName, String folderName) throws IOException {
		log.debug("Removing file " + fileName + " from dir " + localTmpDir);
		String target = localTmpDir + "/" + fileName;
		File targetFile = new File(target);
		targetFile.delete();
		log.debug("File " + fileName + " removed.");
		log.debug("Removing folder " + folderName + " from dir " + localTmpDir);
		String targetFolder = localTmpDir + "/" + folderName;
		FileUtils.deleteDirectory(new File(targetFolder));
		log.debug("Folder " + folderName + " removed.");
	}
}
