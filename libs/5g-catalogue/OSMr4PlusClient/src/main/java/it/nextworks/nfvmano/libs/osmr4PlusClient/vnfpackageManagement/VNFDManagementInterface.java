package it.nextworks.nfvmano.libs.osmr4PlusClient.vnfpackageManagement;

import it.nextworks.nfvmano.libs.osmr4PlusClient.utilities.OSMHttpResponse;

import java.io.File;

public interface VNFDManagementInterface {

    OSMHttpResponse createVnfPackage();
    OSMHttpResponse uploadVnfPackageContent(String vnfPkgId, File content);
    OSMHttpResponse getVnfd(String vnfPkgId);
    OSMHttpResponse getVnfPackageInfo(String vnfPkgId);
    OSMHttpResponse deleteVnfPackage(String vnfPkgId);
    OSMHttpResponse getVnfPackageContent(String vnfPkgId, String storagePath);
    OSMHttpResponse getVnfPackageList();
}
