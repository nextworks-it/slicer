package it.nextworks.nfvmano.sbi.cnc.UPFsliceAssociationCnc;

import it.nextworks.nfvmano.sbi.cnc.dnnList.DnnUpfInfoList;
import it.nextworks.nfvmano.sbi.cnc.dnnList.InterfaceUpfInfoList;
import it.nextworks.nfvmano.sbi.cnc.dnnList.SNssai;
import it.nextworks.nfvmano.sbi.cnc.dnnList.UpfInfo;

import java.util.ArrayList;

public class UpfSliceAssociationCnc {
    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
UpfSliceAssociationCnc root = om.readValue(myJsonString, UpfSliceAssociationCnc.class); */
    public String nfInstanceId;
    public String nfInstanceName;
    public String nfType;
    public String nfStatus;
    public ArrayList<String> ipv4Addresses;
    public UpfInfo upfInfo;

}
