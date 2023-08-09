package it.nextworks.nfvmano.catalogue.engine;

import it.nextworks.nfvmano.catalogue.auth.usermanagement.UserResource;
import it.nextworks.nfvmano.catalogue.repos.UserRepository;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);

    public static boolean checkUserProjects(UserRepository userRepository, String userName, String projectId) throws NotExistingEntityException {

        Optional<UserResource> optional = userRepository.findByUserName(userName);

        if (optional.isPresent()) {
            UserResource userResource = optional.get();

            List<String> projectResources = userResource.getProjects();
            for (String project : projectResources) {
                if (project.equals(projectId))
                    return true;
            }
        } else {
            throw new NotExistingEntityException("User with userName " + userName + " not found in Catalogue's DB");
        }
        log.error("Current user cannot access to the specified project");
        return false;
    }

    public static void checkZipArchive(MultipartFile vnfPkg) throws FailedOperationException {

        byte[] bytes = new byte[0];
        try {
            bytes = vnfPkg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = new ByteArrayInputStream(bytes);

        ZipInputStream zis = new ZipInputStream(is);

        try {
            zis.getNextEntry();
        } catch (IOException e) {
            throw new FailedOperationException("CSAR Archive is corrupted: " + e.getMessage());
        }

        try {
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            throw new FailedOperationException("CSAR Archive is corrupted: " + e.getMessage());
        }
    }

    public static File convertToFile(MultipartFile multipart) throws Exception {
        File convFile = new File(multipart.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipart.getBytes());
        fos.close();
        return convFile;
    }

    @SuppressWarnings("resource")
    public static MultipartFile extractFile(File file) throws IOException {

        MultipartFile archived = null;

        ZipFile zipFile = new ZipFile(file);
        if (zipFile.size() == 0) {
            throw new IOException("The zip archive does not contain any entries");
        }
        if (zipFile.size() > 1) {
            throw new IOException("The zip archive contains more than one entry");
        }
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (!entry.isDirectory() && entry.getName().endsWith(".yaml")) {
                // it is the NSD/PNFD
                log.debug("File inside zip: " + entry.getName());

                if (archived != null) {
                    log.error("Archive validation failed: multiple NSDs/PNFDs in the zip");
                    throw new IOException("Multiple NSDs/PNFDs in the zip");
                }
                InputStream zipIn = zipFile.getInputStream(entry);
                archived = new MockMultipartFile(entry.getName(), entry.getName(), null, zipIn);
                // found (ASSUME one single .yaml in the zip)
                break;
            }
        }
        if (archived == null) {
            throw new IOException("The zip archive does not contain NSD/PNFD file");
        }
        zipFile.close();
        return archived;
    }

    public static MultipartFile createMultiPartFromFile(File file) throws FailedOperationException {

        /*byte[] content = null;
        try {
            content = Files.readAllBytes(file.toPath());
        } catch (final IOException e) {
        }*/
        DiskFileItem fileItem;
        try {
            fileItem = new DiskFileItem("file",  Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length() , file.getParentFile());
            InputStream input =  new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            int ret = input.read();
            while ( ret != -1 )
            {
                os.write(ret);
                ret = input.read();
            }
            os.flush();
        } catch (Exception e) {
            throw new FailedOperationException("Unable  to create Multipart file");
        }


        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        /*MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), contentType, content);*/

        return multipartFile;
    }

    public static boolean isUUID(String id) {
        String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"; //UUID format
        if (id.matches(regex))
            return true;
        return false;
    }
}
