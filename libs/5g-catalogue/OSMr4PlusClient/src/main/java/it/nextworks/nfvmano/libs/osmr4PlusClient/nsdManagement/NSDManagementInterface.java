package it.nextworks.nfvmano.libs.osmr4PlusClient.nsdManagement;

import it.nextworks.nfvmano.libs.osmr4PlusClient.utilities.OSMHttpResponse;

import java.io.File;

public interface NSDManagementInterface {

    OSMHttpResponse createNsd();
    OSMHttpResponse uploadNsdContent(String nsdInfoId, File content);
    OSMHttpResponse getNsd(String nsdInfoId);
    OSMHttpResponse getNsdInfo(String nsdInfoId);
    OSMHttpResponse deleteNsd(String nsdInfoId);
    OSMHttpResponse getNsdContent(String nsdInfoId, String storagePath);
    OSMHttpResponse getNsdInfoList();
}
