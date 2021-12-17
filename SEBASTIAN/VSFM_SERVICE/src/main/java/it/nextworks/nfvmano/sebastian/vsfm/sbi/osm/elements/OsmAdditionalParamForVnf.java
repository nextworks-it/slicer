package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OsmAdditionalParamForVnf {

    @JsonProperty("member-vnf-index")
    private String memberVnfIndex;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("additionalParams")
    private Map<String, String> additionalParams;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("additionalParamsForKdu")
    private List<OsmAdditionalParamForKdu> additionalParamForKdu;

    public OsmAdditionalParamForVnf(String memberVnfIndex, Map<String, String> additionalParams, List<OsmAdditionalParamForKdu> additionalParamForKdu) {
        this.memberVnfIndex = memberVnfIndex;
        this.additionalParams = additionalParams;
        this.additionalParamForKdu = additionalParamForKdu;
    }

    public String getMemberVnfIndex() {
        return memberVnfIndex;
    }

    public void setMemberVnfIndex(String memberVnfIndex) {
        this.memberVnfIndex = memberVnfIndex;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }

    public List<OsmAdditionalParamForKdu> getAdditionalParamForKdu() {
        return additionalParamForKdu;
    }

    public void setAdditionalParamForKdu(List<OsmAdditionalParamForKdu> additionalParamForKdu) {
        this.additionalParamForKdu = additionalParamForKdu;
    }
}
