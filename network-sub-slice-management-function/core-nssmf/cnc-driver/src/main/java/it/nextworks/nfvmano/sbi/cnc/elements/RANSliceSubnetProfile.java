package it.nextworks.nfvmano.sbi.cnc.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RANSliceSubnetProfile{
    @JsonProperty("coverageAreaTAList")
    public CoverageAreaTAList coverageAreaTAList;
    @JsonProperty("uEMobilityLevel")
    public String uEMobilityLevel;
    public int resourceSharingLevel;
    public int maxNumberofUEs;
    public int activityFactor;
    @JsonProperty("dLThptPerUEPerSubnet")
    public DLThptPerUEPerSubnet dLThptPerUEPerSubnet;
    @JsonProperty("uLThptPerUEPerSubnet")
    public ULThptPerUEPerSubnet uLThptPerUEPerSubnet;
    public int uESpeed;

    public RANSliceSubnetProfile(){

        coverageAreaTAList = new CoverageAreaTAList();
        dLThptPerUEPerSubnet = new DLThptPerUEPerSubnet();
        uLThptPerUEPerSubnet = new ULThptPerUEPerSubnet();
    }
}
