package it.nextworks.nfvmano.sbi.cnc.elements;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CNSliceSubnetProfile{
    public int maxNumberofUEs;
    public int latency;
    @JsonProperty("dLThptPerSliceSubnet")
    public DLThptPerSliceSubnet dLThptPerSliceSubnet;
    @JsonProperty("dLThptPerUEPerSubnet")
    public DLThptPerUEPerSubnet dLThptPerUEPerSubnet;
    @JsonProperty("uLThptPerSlicePerSubnet")
    public ULThptPerSlicePerSubnet uLThptPerSlicePerSubnet;
    @JsonProperty("uLThptPerUEPerSubnet")
    public ULThptPerUEPerSubnet uLThptPerUEPerSubnet;
    public int maxNumberofPDUSessions;
    @JsonProperty("coverageAreaTAList")
    public CoverageAreaTAList coverageAreaTAList;

public CNSliceSubnetProfile(){
    dLThptPerSliceSubnet = new DLThptPerSliceSubnet();
    dLThptPerUEPerSubnet = new DLThptPerUEPerSubnet();
    uLThptPerSlicePerSubnet = new ULThptPerSlicePerSubnet();
    uLThptPerUEPerSubnet = new ULThptPerUEPerSubnet();
    }
}

