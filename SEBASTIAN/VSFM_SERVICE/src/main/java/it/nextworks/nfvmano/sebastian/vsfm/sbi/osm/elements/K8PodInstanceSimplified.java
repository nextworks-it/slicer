package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class K8PodInstanceSimplified {

    @JsonProperty("metadata")
    private K8PodMetadataSimplified metadata;

    public K8PodMetadataSimplified getMetadata() {
        return metadata;
    }

    public void setMetadata(K8PodMetadataSimplified metadata) {
        this.metadata = metadata;
    }
}
