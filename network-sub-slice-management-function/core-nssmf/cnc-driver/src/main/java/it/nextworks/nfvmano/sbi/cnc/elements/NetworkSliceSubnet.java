package it.nextworks.nfvmano.sbi.cnc.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class NetworkSliceSubnet {
    @JsonProperty("sliceProfile")
    public SliceProfile sliceProfile;
    @JsonProperty("epTransport")
    public EpTransport epTransport;
    @JsonProperty("sNSSAIList")
    public ArrayList<SNSSAIList> sNSSAIList;
    @JsonProperty("pLMNIdList")
    public ArrayList<Object> pLMNIdList;
    @JsonProperty("operationalState")
    public boolean operationalState;
    @JsonProperty("administrativeState")
    public String administrativeState;
    public String nsInfo;
    @JsonProperty("managedFunction")
    public String managedFunction;

    public NetworkSliceSubnet(){
        sliceProfile = new SliceProfile();
        epTransport = new EpTransport();
        sNSSAIList = new ArrayList<>();
        pLMNIdList = new ArrayList<>();
    }
}
