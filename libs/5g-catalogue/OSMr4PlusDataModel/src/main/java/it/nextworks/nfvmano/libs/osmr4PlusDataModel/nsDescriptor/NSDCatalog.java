package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NSDCatalog {

    @JsonProperty("nsd")
    public List<NSDescriptor> nsds;

    public List<NSDescriptor> getNsds() { return nsds; }

    public void setNsds(List<NSDescriptor> nsds) { this.nsds = nsds; }

    @Override
    public String toString() {
        return "NSDCatalog{" +
                "descriptors=" + nsds +
                '}';
    }
}
