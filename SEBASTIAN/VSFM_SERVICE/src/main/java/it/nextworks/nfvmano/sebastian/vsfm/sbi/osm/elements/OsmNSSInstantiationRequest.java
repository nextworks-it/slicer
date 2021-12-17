package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OsmNSSInstantiationRequest {

    @JsonProperty("id")
    private String netSliceSubnetId;
    @JsonProperty("additionalParamsForVnf")
    private List<OsmAdditionalParamForVnf> additionalParamsForVnf;

    public OsmNSSInstantiationRequest(String netSliceSubnetId, List<OsmAdditionalParamForVnf> additionalParamsForVnf) {
        this.additionalParamsForVnf = additionalParamsForVnf;
        this.netSliceSubnetId = netSliceSubnetId;
    }

    public List<OsmAdditionalParamForVnf> getAdditionalParamsForVnf() {
        return additionalParamsForVnf;
    }

    public void setAdditionalParamsForVnf(List<OsmAdditionalParamForVnf> additionalParamsForVnf) {
        this.additionalParamsForVnf = additionalParamsForVnf;
    }

    public void addAdditionalParamForVnf(OsmAdditionalParamForVnf additionalParamForVnf) {
        this.additionalParamsForVnf.add(additionalParamForVnf);
    }

    public String getNetSliceSubnetId() {
        return netSliceSubnetId;
    }

    public void setNetSliceSubnetId(String netSliceSubnetId) {
        this.netSliceSubnetId = netSliceSubnetId;
    }
}
