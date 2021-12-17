package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmConstituentVnfSimplified {

    @JsonProperty("vnfd-id-ref")
    private String vnfdId;
    @JsonProperty("member-vnf-index")
    private Integer memberVnfIndex;

    public String getVnfdId() {
        return vnfdId;
    }

    public void setVnfdId(String vnfdId) {
        this.vnfdId = vnfdId;
    }

    public Integer getMemberVnfIndex() {
        return memberVnfIndex;
    }

    public void setMemberVnfIndex(Integer memberVnfIndex) {
        this.memberVnfIndex = memberVnfIndex;
    }
}
