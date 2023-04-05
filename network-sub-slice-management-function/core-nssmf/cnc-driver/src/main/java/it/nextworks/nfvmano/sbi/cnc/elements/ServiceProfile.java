package it.nextworks.nfvmano.sbi.cnc.elements;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ServiceProfile{
    @JsonProperty("sNSSAIList")
    public ArrayList<SNSSAIList> sNSSAIList;
    @JsonProperty("pLMNIdList")
    public ArrayList<PLMNIdList> pLMNIdList;
    public int maxNumberofUEs;
    public int latency;
    @JsonProperty("dLThptPerSlice")
    public DLThptPerSlice dLThptPerSlice;
    @JsonProperty("dLThptPerUE")
    public DLThptPerUE dLThptPerUE;
    @JsonProperty("uLThptPerSlice")
    public ULThptPerSlice uLThptPerSlice;
    @JsonProperty("uLThptPerUE")
    public ULThptPerUE uLThptPerUE;
    public String maxDLDataVolume;
    public String maxULDataVolume;
    public String survivalTime;
    public String dnn;

    public ServiceProfile(){
        sNSSAIList = new ArrayList<>();
        pLMNIdList = new ArrayList<>();
        dLThptPerSlice = new DLThptPerSlice();
        dLThptPerUE = new DLThptPerUE();
        uLThptPerSlice = new ULThptPerSlice();
        uLThptPerUE = new ULThptPerUE();

    }
}
