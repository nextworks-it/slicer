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
package it.nextworks.nfvmano.catalogue.translators.tosca;

import it.nextworks.nfvmano.catalogue.common.enums.DataModelSpec;
import it.nextworks.nfvmano.catalogue.common.enums.DescriptorType;
import it.nextworks.nfvmano.catalogue.storage.FileSystemStorageService;
import it.nextworks.nfvmano.catalogue.translators.tosca.elements.CSARInfo;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.sol006.Nsd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Pnfd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Vnfd;
import it.nextworks.nfvmano.libs.descriptors.templates.DescriptorTemplate;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ArchiveParser {

    private static final Logger log = LoggerFactory.getLogger(ArchiveParser.class);

    private static Set<String> admittedFolders = new HashSet<>();

    @Autowired
    DescriptorsParser descriptorsParser;

    @Autowired
    FileSystemStorageService storageService;

    public ArchiveParser() {
    }

    @PostConstruct
    void init() {
        admittedFolders.add("TOSCA-Metadata/");
        admittedFolders.add("Definitions/");
        admittedFolders.add("Files/");
        admittedFolders.add("Files/Tests/");
        admittedFolders.add("Files/Licenses/");
        admittedFolders.add("Files/Scripts/");
        admittedFolders.add("Files/Monitoring/");
    }

    public CSARInfo archiveToCSARInfo(String project, MultipartFile file, DescriptorType descriptorType, boolean store)
            throws IOException, MalformattedElementException, FailedOperationException {

        CSARInfo csarInfo = new CSARInfo();

        ByteArrayOutputStream metadata = null;
        ByteArrayOutputStream manifest = null;
        ByteArrayOutputStream mainServiceTemplate;
        Map<String, ByteArrayOutputStream> templates = new HashMap<>();

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();

            InputStream input = new ByteArrayInputStream(bytes);

            log.debug("Going to parse archive " + file.getName() + "...");

            ZipEntry entry;

            ZipInputStream zipStream = new ZipInputStream(input);
            while ((entry = zipStream.getNextEntry()) != null) {

                if (!entry.isDirectory()) {
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();

                    int count;
                    byte[] buffer = new byte[1024];
                    while ((count = zipStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, count);
                    }

                    String fileName = entry.getName();
                    log.debug("Parsing Archive: found file with name " + fileName);

                    if (fileName.toLowerCase().endsWith(".mf")) {
                        manifest = outStream;
                        csarInfo.setMfFilename(fileName);
                    } else if (fileName.toLowerCase().endsWith(".meta")) {
                        metadata = outStream;
                        csarInfo.setMetaFilename(fileName);
                    } else if (fileName.toLowerCase().endsWith(".yaml")
                            || fileName.toLowerCase().endsWith(".yml")
                            || fileName.toLowerCase().endsWith(".json")) {
                        templates.put(fileName, outStream);
                    } else {
                        // TODO: process remaining content
                    }
                } else {
                    log.debug("Parsing Archive: checking folder with name " + entry.getName());
                    if (!admittedFolders.contains(entry.getName())) {
                        log.error("Folder with name " + entry.getName() + " not admitted in CSAR option#1 structure");
                        throw new MalformattedElementException("Folder with name " + entry.getName() + " not admitted in CSAR option#1 structure");
                    }
                }
            }

            String mst_name;

            if (metadata == null) {
                log.error("CSAR without TOSCA.meta");
                throw new MalformattedElementException("CSAR without TOSCA.meta");
            }
            if (manifest == null) {
                log.error("CSAR without manifest");
                throw new MalformattedElementException("CSAR without manifest");
            } else {
                mst_name = getMainServiceTemplateFromMetadata(metadata.toByteArray());
                if (mst_name != null) {
                    csarInfo.setDescriptorFilename(mst_name);
                    log.debug("Parsing metadata: found Main Service Template " + mst_name);
                    if (templates.containsKey(mst_name)) {
                        mainServiceTemplate = templates.get(mst_name);
                    } else {
                        log.error("Main Service Template specified in TOSCA.meta not present in CSAR Definitions directory: " + mst_name);
                        throw new MalformattedElementException(
                                "Main Service Template specified in TOSCA.meta not present in CSAR Definitions directory: " + mst_name);
                    }
                } else {
                    log.error("Unable to get Main Service Template name from TOSCA.meta");
                    throw new MalformattedElementException("Unable to get Main Service Template name from TOSCA.meta");
                }
            }

            String descriptorId = null;
            String version = null;
            if (mainServiceTemplate != null) {
                log.debug("Going to parse main service template...");
                String mst_content = mainServiceTemplate.toString("UTF-8");

                DataModelSpec dataModelSpec =
                        getDataModelSpecFromManifest(csarInfo.getMfFilename(), manifest.toByteArray());
                csarInfo.setDataModelSpec(dataModelSpec);

                if(dataModelSpec == DataModelSpec.SOL001) {
                    DescriptorTemplate dt = descriptorsParser.stringToDescriptorTemplate(mst_content);
                    csarInfo.setMst(dt);
                    descriptorId = dt.getMetadata().getDescriptorId();
                    version = dt.getMetadata().getVersion();
                    log.debug("Main service template with descriptor Id {} and version {} successfully parsed",
                            descriptorId, version);
                } else {
                    if(descriptorType == DescriptorType.VNFD) {
                        Vnfd vnfd = descriptorsParser.stringToSol006(mst_name, mst_content, Vnfd.class);
                        csarInfo.setVnfd(vnfd);
                        descriptorId = vnfd.getId();
                        version = vnfd.getVersion();
                        log.debug("Main service template with descriptor Id {} and version {} successfully parsed",
                                descriptorId, version);
                    }
                    else if(descriptorType == DescriptorType.PNFD) {
                        Pnfd pnfd = descriptorsParser.stringToSol006(mst_name, mst_content, Pnfd.class);
                        csarInfo.setPnfd(pnfd);
                        descriptorId = pnfd.getId();
                        version = pnfd.getVersion();
                        log.debug("Main service template with descriptor Id {} and version {} successfully parsed",
                                descriptorId, version);
                    } else {
                        Nsd nsd = descriptorsParser.stringToSol006(mst_name, mst_content, Nsd.class);
                        csarInfo.setNsd(nsd);
                        descriptorId = nsd.getId();
                        version = nsd.getVersion();
                        log.debug("Main service template with descriptor Id {} and version {} successfully parsed",
                                descriptorId, version);
                    }
                }
            }

            if (store) {
                try {
                    String packageFilename =
                            storageService.storePkg(project, descriptorId, version, file, descriptorType);
                    csarInfo.setPackageFilename(packageFilename);
                    log.debug("Stored Pkg: " + packageFilename);
                } catch (FailedOperationException e) {
                    log.error("Failure while storing Pkg with descriptor Id " + descriptorId + ": " + e.getMessage());
                    throw new FailedOperationException("Failure while storing Pkg with descriptor Id " + descriptorId +
                            ": " + e.getMessage());
                }

                try {
                    unzip(new ByteArrayInputStream(bytes), project, descriptorId, version, descriptorType);
                } catch (FailedOperationException e) {
                    log.error("Failure while unzipping Pkg with descriptor Id " + descriptorId + ": " + e.getMessage());
                    throw new FailedOperationException("Failure while unzipping Pkg with descriptor Id " +
                            descriptorId + ": " + e.getMessage());
                }
            }

            if(mainServiceTemplate != null)
                mainServiceTemplate.close();
            metadata.close();
            manifest.close();
            for (Map.Entry<String, ByteArrayOutputStream> template : templates.entrySet()) {
                template.getValue().close();
            }
            input.close();
        }

        return csarInfo;
    }

    private String getMainServiceTemplateFromMetadata(byte[] metadata) throws IOException {

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(new ByteArrayInputStream(metadata), StandardCharsets.UTF_8));

        log.debug("Going to parse TOSCA.meta...");

        String mst_name = null;

        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                    String regex = "^Entry-Definitions: (Definitions\\/[^\\\\]*\\.(yaml|json|yml))$";
                    if (line.matches(regex)) {
                        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            mst_name = matcher.group(1);
                        }
                    }
                }
            }
        } finally {
            reader.close();
        }

        return mst_name;
    }

    private DataModelSpec getDataModelSpecFromManifest(String mfFilename, byte[] manifest)
            throws IOException, MalformattedElementException {

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(new ByteArrayInputStream(manifest), StandardCharsets.UTF_8));

        log.debug("Going to parse " + mfFilename + "...");

        String dataModelSpec = null;

        String line;
        String regex = "^[ \t]*datamodel_spec: ([^\\\\]*)$";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        boolean found = false;
        try {
            while((line = reader.readLine()) != null) {
                if(line.matches(regex)) {
                    Matcher matcher = pattern.matcher(line);
                    if(matcher.find()) {
                        dataModelSpec = matcher.group(1);
                        found = true;
                        break;
                    }
                }
            }
        } finally {
            reader.close();
        }

        if(!found)
            return DataModelSpec.SOL001;

        DataModelSpec dms = DataModelSpec.fromValue(dataModelSpec);
        if(dms == null) {
            String msg = "Invalid datamodel_spec field inside " + mfFilename + ": specify SOL001 or SOL006.";
            log.error(msg);
            throw new MalformattedElementException(msg);
        }

        return dms;
    }

    public void unzip(InputStream archive, String project, String descriptorId,
                      String version, DescriptorType descriptorType)
            throws IOException, FailedOperationException, MalformattedElementException {
        ZipInputStream zis = new ZipInputStream(archive);
        ZipEntry zipEntry = zis.getNextEntry();
        String element_filename;
        while (zipEntry != null) {
            log.debug("Storing CSAR element: " + zipEntry.getName());
            element_filename =
                    storageService.storePkgElement(zis, zipEntry, project, descriptorId, version, descriptorType);
            log.debug("Stored Pkg element: " + element_filename);
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }
}
