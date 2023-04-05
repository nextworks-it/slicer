package it.nextworks.nfvmano.sbi.cnc.elements;

import java.util.ArrayList;

public class EpTransport {
    public String ioAddress;
    public String logicInterfaceId;
    public int qosProfile;
    public ArrayList<String> epApplication;

    public EpTransport(){
        epApplication = new ArrayList();
    }
}
