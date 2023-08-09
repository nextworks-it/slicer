package it.nextworks.nfvmano.sbi.cnc.dnnList;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SNssaiUpfInfoList{
    @JsonProperty("DnnUpfInfoList")
    public ArrayList<DnnUpfInfoList> dnnUpfInfoList;
    @JsonProperty("sNssai")
    public SNssai sNssai;
}
