package it.nextworks.nfvmano.sbi.cnc.elements;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PerfReqEmbbList{
    @JsonProperty("expDataRateDL")
    public int expDataRateDL;
    @JsonProperty("expDataRateUL")
    public int expDataRateUL;
    @JsonProperty("areaTrafficCapDL")
    public int areaTrafficCapDL;
    @JsonProperty("areaTrafficCapUL")
    public int areaTrafficCapUL;
    @JsonProperty("userDensity")
    public int userDensity;
    @JsonProperty("activityFactor")
    public int activityFactory;
}