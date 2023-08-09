package it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.sol006.Nsd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Vnfd;
import it.nextworks.nfvmano.libs.descriptors.templates.DescriptorTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ToscaArchiveBuilder {

    private static final Logger log = LoggerFactory.getLogger(ToscaArchiveBuilder.class);

    public static String createVNFCSAR(String packageIdentifier, DescriptorTemplate template, String tmpDir, File cloudInit) throws IllegalStateException{
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        List<String> strings = new ArrayList<>();

        try{
            String vnfName = template.getTopologyTemplate().getVNFNodes().keySet().iterator().next();

            log.debug("Creating folder structure");
            //Create directories
            File root = makeFolder(tmpDir,vnfName + "_" + packageIdentifier);
            File definitions = makeSubFolder(root, "Definitions");
            File files = makeSubFolder(root, "Files");
            File licenses = makeSubFolder(files, "Licences");
            File monitoring = makeSubFolder(files, "Monitoring");
            File scripts = makeSubFolder(files, "Scripts");
            File tests = makeSubFolder(files, "Tests");
            File metadata = makeSubFolder(root, "TOSCA-Metadata");

            //Create standard files
            File manifest = new File(root, vnfName + ".mf");
            strings.add("metadata:");
            strings.add("\tvnf_product_name: " + vnfName);
            strings.add("\tvnf_provider_id: " + template.getMetadata().getVendor());
            strings.add("\tvnf_package_version: " + template.getMetadata().getVersion());
            strings.add(String.format("\tvnf_release_date_time: %1$TD %1$TT", ts));
            if(cloudInit != null) {
                strings.add("\nconfiguration:");
                strings.add("\tcloud_init:");
                strings.add("\t\tSource: Files/Scripts/" + cloudInit.getName());
                copyFile(scripts, cloudInit);
            }
            Files.write(manifest.toPath(), strings);
            strings.clear();
            File license = new File(licenses, "LICENSE");
            Files.write(license.toPath(), strings);
            File changeLog = new File(files, "ChangeLog.txt");
            strings.add(String.format("%1$TD %1$TT - New VNF Package according to ETSI GS NFV-SOL004 v 2.5.1", ts));
            Files.write(changeLog.toPath(), strings);
            strings.clear();
            File certificate = new File(files, vnfName + ".cert");
            Files.write(certificate.toPath(), strings);
            File toscaMetadata = new File(metadata, "TOSCA.meta");
            strings.add("TOSCA-Meta-File-Version: 1.0");
            strings.add("CSAR-Version: 1.1");
            strings.add("CreatedBy: 5G Apps & Services Catalogue");
            strings.add("Entry-Definitions: Definitions/"+ vnfName + ".yaml");
            Files.write(toscaMetadata.toPath(), strings);
            strings.clear();

            //Create descriptor files
            File descriptorFile = new File(definitions, vnfName + ".yaml");
            final YAMLFactory yamlFactory = new YAMLFactory()
                    .configure(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID, false)
                    .configure(YAMLGenerator.Feature.USE_NATIVE_OBJECT_ID, false)
                    .configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
            ObjectMapper mapper = new ObjectMapper(yamlFactory);
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.writeValue(descriptorFile, template);

            log.debug("Creating CSAR archive");
            return compress(root.toPath().toString());//returns package path
        } catch (IOException | MalformattedElementException e) {
            throw new IllegalStateException(String.format("Could not write files. Error: %s", e.getMessage()));
        }
    }

    public static String createNSCSAR(String packageIdentifier, DescriptorTemplate template, String tmpDir) throws IllegalStateException{
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        List<String> strings = new ArrayList<>();

        try{
            String nsName = template.getTopologyTemplate().getNSNodes().keySet().iterator().next();

            log.debug("Creating folder structure");
            //Create directories
            File root = makeFolder(tmpDir,nsName + "_" + packageIdentifier);
            File definitions = makeSubFolder(root, "Definitions");
            File files = makeSubFolder(root, "Files");
            File licenses = makeSubFolder(files, "Licences");
            File monitoring = makeSubFolder(files, "Monitoring");
            File scripts = makeSubFolder(files, "Scripts");
            File tests = makeSubFolder(files, "Tests");
            File metadata = makeSubFolder(root, "TOSCA-Metadata");

            //Create standard files
            File manifest = new File(root, nsName + ".mf");
            strings.add("metadata:");
            strings.add("\tns_name: " + nsName);
            strings.add("\tns_vendor_id: " + template.getMetadata().getVendor());
            strings.add("\tns_version: " + template.getMetadata().getVersion());
            strings.add(String.format("\tns_release_date_time: %1$TD %1$TT", ts));

            Files.write(manifest.toPath(), strings);
            strings.clear();
            File license = new File(licenses, "LICENSE");
            Files.write(license.toPath(), strings);
            File changeLog = new File(files, "ChangeLog.txt");
            strings.add(String.format("%1$TD %1$TT - New NS Package according to ETSI GS NFV-SOL004 v 2.5.1", ts));
            Files.write(changeLog.toPath(), strings);
            strings.clear();
            File certificate = new File(files, nsName + ".cert");
            Files.write(certificate.toPath(), strings);
            File toscaMetadata = new File(metadata, "TOSCA.meta");
            strings.add("TOSCA-Meta-File-Version: 1.0");
            strings.add("CSAR-Version: 1.1");
            strings.add("CreatedBy: 5GCity-SDK");
            strings.add("Entry-Definitions: Definitions/"+ nsName + ".yaml");
            Files.write(toscaMetadata.toPath(), strings);
            strings.clear();

            //Create descriptor files
            File descriptorFile = new File(definitions, nsName + ".yaml");
            final YAMLFactory yamlFactory = new YAMLFactory()
                    .configure(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID, false)
                    .configure(YAMLGenerator.Feature.USE_NATIVE_OBJECT_ID, false)
                    .configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
            ObjectMapper mapper = new ObjectMapper(yamlFactory);
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.writeValue(descriptorFile, template);

            log.debug("Creating CSAR archive");
            return compress(root.toPath().toString()); //returns package path
        } catch (IOException | MalformattedElementException e) {
            throw new IllegalStateException(String.format("Could not write files. Error: %s", e.getMessage()));
        }
    }

    public static String createVNFCSAR(String packageIdentifier, Vnfd vnfd, String tmpDir, Map<String, File> cloudInitMap) {

        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        List<String> strings = new ArrayList<>();

        try {
            String vnfName = vnfd.getProductName();

            log.debug("Creating folder structure");

            File root = makeFolder(tmpDir,vnfName + "_" + packageIdentifier);
            File definitions = makeSubFolder(root, "Definitions");
            File files = makeSubFolder(root, "Files");
            File licenses = makeSubFolder(files, "Licences");
            File monitoring = makeSubFolder(files, "Monitoring");
            File scripts = makeSubFolder(files, "Scripts");
            File tests = makeSubFolder(files, "Tests");
            File metadata = makeSubFolder(root, "TOSCA-Metadata");

            File manifest = new File(root, vnfName + ".mf");
            strings.add("metadata:");
            strings.add("\tvnf_product_name: " + vnfName);
            strings.add("\tvnf_provider_id: " + vnfd.getProvider());
            strings.add("\tvnf_package_version: " + vnfd.getVersion());
            strings.add(String.format("\tvnf_release_date_time: %1$TD %1$TT", ts));
            strings.add("\tdatamodel_spec: SOL006");
            if(cloudInitMap != null && !cloudInitMap.entrySet().isEmpty()) {
                for(Map.Entry<String, File> cloudInit : cloudInitMap.entrySet())
                    copyFile(scripts, cloudInit.getValue());
            }
            Files.write(manifest.toPath(), strings);
            strings.clear();

            File license = new File(licenses, "LICENSE");
            Files.write(license.toPath(), strings);

            File changeLog = new File(files, "ChangeLog.txt");
            strings.add(String.format("%1$TD %1$TT - New VNF Package according to ETSI GS NFV-SOL004 v 2.5.1", ts));
            Files.write(changeLog.toPath(), strings);
            strings.clear();

            File certificate = new File(files, vnfName + ".cert");
            Files.write(certificate.toPath(), strings);

            File toscaMetadata = new File(metadata, "TOSCA.meta");
            strings.add("TOSCA-Meta-File-Version: 1.0");
            strings.add("CSAR-Version: 1.1");
            strings.add("CreatedBy: 5G Apps & Services Catalogue");
            strings.add("Entry-Definitions: Definitions/"+ vnfName + ".yaml");
            Files.write(toscaMetadata.toPath(), strings);
            strings.clear();

            File descriptorFile = new File(definitions, vnfName + ".yaml");
            final YAMLFactory yamlFactory = new YAMLFactory()
                    .configure(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID, false)
                    .configure(YAMLGenerator.Feature.USE_NATIVE_OBJECT_ID, false)
                    .configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
            ObjectMapper mapper = new ObjectMapper(yamlFactory);
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.writeValue(descriptorFile, vnfd);

            log.debug("Creating CSAR archive");
            return compress(root.toPath().toString());//returns package path
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Could not write files. Error: %s", e.getMessage()));
        }
    }

    public static String createNSCSAR(String packageIdentifier, Nsd nsd, String tmpDir) {

        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        List<String> strings = new ArrayList<>();

        try {
            String nsName = nsd.getName();

            log.debug("Creating folder structure");

            File root = makeFolder(tmpDir,nsName + "_" + packageIdentifier);
            File definitions = makeSubFolder(root, "Definitions");
            File files = makeSubFolder(root, "Files");
            File licenses = makeSubFolder(files, "Licences");
            File monitoring = makeSubFolder(files, "Monitoring");
            File scripts = makeSubFolder(files, "Scripts");
            File tests = makeSubFolder(files, "Tests");
            File metadata = makeSubFolder(root, "TOSCA-Metadata");

            File manifest = new File(root, nsName + ".mf");
            strings.add("metadata:");
            strings.add("\tns_name: " + nsName);
            strings.add("\tns_vendor_id: " + nsd.getDesigner());
            strings.add("\tns_version: " + nsd.getVersion());
            strings.add(String.format("\tns_release_date_time: %1$TD %1$TT", ts));
            strings.add("\tdatamodel_spec: SOL006");
            Files.write(manifest.toPath(), strings);
            strings.clear();

            File license = new File(licenses, "LICENSE");
            Files.write(license.toPath(), strings);

            File changeLog = new File(files, "ChangeLog.txt");
            strings.add(String.format("%1$TD %1$TT - New NS Package according to ETSI GS NFV-SOL004 v 2.5.1", ts));
            Files.write(changeLog.toPath(), strings);
            strings.clear();

            File certificate = new File(files, nsName + ".cert");
            Files.write(certificate.toPath(), strings);

            File toscaMetadata = new File(metadata, "TOSCA.meta");
            strings.add("TOSCA-Meta-File-Version: 1.0");
            strings.add("CSAR-Version: 1.1");
            strings.add("CreatedBy: 5G Apps & Services Catalogue");
            strings.add("Entry-Definitions: Definitions/"+ nsName + ".yaml");
            Files.write(toscaMetadata.toPath(), strings);
            strings.clear();

            File descriptorFile = new File(definitions, nsName + ".yaml");
            final YAMLFactory yamlFactory = new YAMLFactory()
                    .configure(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID, false)
                    .configure(YAMLGenerator.Feature.USE_NATIVE_OBJECT_ID, false)
                    .configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
            ObjectMapper mapper = new ObjectMapper(yamlFactory);
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.writeValue(descriptorFile, nsd);

            log.debug("Creating CSAR archive");
            return compress(root.toPath().toString());
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Could not write files. Error: %s", e.getMessage()));
        }
    }

    public static File makeFolder(String root, String name) {
        File folder = new File(root, name);
        if (folder.isDirectory()) {
            if (!rmRecursively(folder)) {
                throw new IllegalStateException(
                        String.format("Could not delete folder %s", folder.getAbsolutePath())
                );
            }
        }
        if (!folder.mkdir()) {
            throw new IllegalArgumentException(
                    String.format("Cannot create folder %s", folder.getAbsolutePath())
            );
        }
        return folder;
    }

    public static boolean rmRecursively(File folder) {
        SimpleFileVisitor<Path> deleter = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e)
                    throws IOException {
                if (e == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    // directory iteration failed
                    throw e;
                }
            }
        };
        try {
            Files.walkFileTree(folder.toPath(), deleter);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static File makeSubFolder(File folder, String subFolder) {
        File newFolder = new File(folder, subFolder);
        if (!newFolder.mkdir()) {
            throw new IllegalArgumentException(
                    String.format("Cannot create folder %s", newFolder.getAbsolutePath())
            );
        }
        return newFolder;
    }

    public static String compress(String dirPath) {
        final Path sourceDir = Paths.get(dirPath);
        String zipFileName = dirPath.concat(".zip");
        try {
            final ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                    try {
                        Path targetFile = sourceDir.relativize(file);
                        outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
                        byte[] bytes = Files.readAllBytes(file);
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zipFileName;
    }

    public static void copyFile(File fileFolder, File file) {
        try {
            Files.copy(file.toPath(), new File(fileFolder, file.getName()).toPath());
        } catch (IOException e) {
            String msg = String.format(
                    "Cannot copy icon file %s to folder %s",
                    file.getAbsolutePath(),
                    fileFolder.getAbsolutePath()
            );
            throw new IllegalArgumentException(msg, e);
        }
    }
}
