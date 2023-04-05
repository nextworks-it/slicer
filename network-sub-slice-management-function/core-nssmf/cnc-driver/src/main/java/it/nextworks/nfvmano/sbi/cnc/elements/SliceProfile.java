package it.nextworks.nfvmano.sbi.cnc.elements;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SliceProfile{
    @JsonProperty("sNSSAIList")
    public List sNSSAIList;
    //public Object sNSSAIList;
    @JsonProperty("pLMNIdList")
    public List pLMNIdList;
    //public Object pLMNIdList;
    @JsonProperty("perfReq")
    public PrfReq prfReq;
    @JsonProperty("cNSliceSubnetProfile")
    public CNSliceSubnetProfile cNSliceSubnetProfile;
    @JsonProperty("rANSliceSubnetProfile")
    public RANSliceSubnetProfile rANSliceSubnetProfile;
    @JsonProperty("topSliceSubnetProfile")
    public String topSliceSubnetProfile;

    public SliceProfile(){
        //sNSSAIList = new Object();
        sNSSAIList = new ArrayList();
        //pLMNIdList = new Object();
        pLMNIdList = new ArrayList<>();
        prfReq = new PrfReq();
        cNSliceSubnetProfile = new CNSliceSubnetProfile();
        rANSliceSubnetProfile = new RANSliceSubnetProfile();
    }
}
