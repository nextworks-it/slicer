package it.nextworks.nfvmano.sbi.cnc.dnnList;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class UpfInfo {
    @JsonProperty("interfaceUpfInfoList")
    public ArrayList<InterfaceUpfInfoList> interfaceUpfInfoList;
    @JsonProperty("sNssaiUpfInfoList")
    public ArrayList<SNssaiUpfInfoList> sNssaiUpfInfoList;
}
