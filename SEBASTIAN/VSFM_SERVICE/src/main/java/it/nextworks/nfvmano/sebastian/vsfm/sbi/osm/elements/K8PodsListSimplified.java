package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class K8PodsListSimplified {

    @JsonProperty("items")
    private List<K8PodInstanceSimplified> items;

    public List<K8PodInstanceSimplified> getItems() {
        return items;
    }

    public void setItems(List<K8PodInstanceSimplified> items) {
        this.items = items;
    }
}
