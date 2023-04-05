package it.nextworks.nfvmano.sbi.cnc.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PerfReqUrllcList {
    @JsonProperty("CSAvailabilityTarget")
    public int cSAvailabilityTarget;

    @JsonProperty("cSReliabilityMeanTime")
    public int cSReliabilityMeanTime;

    @JsonProperty("expDataRateDL")
    public int expDataRateDL;

    @JsonProperty("msgSizeByte")
    public String msgSizeByte;
}
