package it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin;

import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANO;
import it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin.elements.OnapNsDescriptor;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Utilities {

    public static File getNsDescriptorFile(File nsPackage) throws FailedOperationException {
        File definitionsFolder = new File(nsPackage, "Definitions");
        FilenameFilter fileTemplateFilter = (f, name) -> name.startsWith("service-") && name.endsWith("-template.yml");
        String[] files = definitionsFolder.list(fileTemplateFilter);
        if(files == null || files.length != 1)
            throw new FailedOperationException("Cannot obtain the NS Descriptor file");
        return new File(definitionsFolder, files[0]);
    }

    public static List<File> getVnfDescriptorFiles(File nsPackage, OnapNsDescriptor nsDescriptor) throws FailedOperationException{
        List<File> vnfDescriptorFiles = new ArrayList<>();
        File definitionsFolder = new File(nsPackage, "Definitions");
        //Map <nodeName, vfIdentifier>
        Map<String, String> nodeVfIdentifierMapping = nsDescriptor.getVFIdentifiers();
        Set<String> vfIdentifiersSet = new HashSet<>(nodeVfIdentifierMapping.values());
        for(String vfIdentifier : vfIdentifiersSet)
            vnfDescriptorFiles.add(new File(definitionsFolder, "resource-" + vfIdentifier + "-template.yml"));
        return vnfDescriptorFiles;
    }

    public static boolean isTargetMano (List<String> siteOrManoIds, MANO mano){
        if(siteOrManoIds == null || siteOrManoIds.contains(mano.getManoId()) || siteOrManoIds.contains(mano.getManoSite()))
            return true;
        return false;
    }

    public static void unzip(File zipfile, File directory) throws IOException {
        ZipFile zfile = new ZipFile(zipfile);
        Enumeration<? extends ZipEntry> entries = zfile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            File file = new File(directory, entry.getName());
            if (entry.isDirectory()) {
                file.mkdirs();
            } else {
                file.getParentFile().mkdirs();
                InputStream in = zfile.getInputStream(entry);
                try {
                    copy(in, file);
                } finally {
                    in.close();
                }
            }
        }
    }

    private static void copy(InputStream in, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        try {
            copy(in, out);
        } finally {
            out.close();
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int readCount = in.read(buffer);
            if (readCount < 0) {
                break;
            }
            out.write(buffer, 0, readCount);
        }
    }
}
