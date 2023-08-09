package it.nextworks.nfvmano.sbi.cnc.messages;

import it.nextworks.nfvmano.sbi.cnc.dnnList.SNssai;

import java.util.List;

public class UpfMainInfo {
    private String nfInstanceId;
    private String nfInstanceName;
    private String ip;

    public List<SNssai> getsNssaiList() {
        return sNssaiList;
    }

    public void setsNssaiList(List<SNssai> sNssaiList) {
        this.sNssaiList = sNssaiList;
    }

    private List<SNssai> sNssaiList;

    public UpfMainInfo(String nfInstanceId, String nfInstanceName, String ip, List<SNssai> sNssaiList) {
        this.nfInstanceId = nfInstanceId;
        this.nfInstanceName = nfInstanceName;
        this.ip = ip;
        this.sNssaiList = sNssaiList;
    }

    public String getNfInstanceId() {
        return nfInstanceId;
    }

    public void setNfInstanceId(String nfInstanceId) {
        this.nfInstanceId = nfInstanceId;
    }

    public String getNfInstanceName() {
        return nfInstanceName;
    }

    public void setNfInstanceName(String nfInstanceName) {
        this.nfInstanceName = nfInstanceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
