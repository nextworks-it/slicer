package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VNFDCatalog {

    @JsonProperty("vnfd")
    public List<VNFDescriptor> vnfds;

    public List<VNFDescriptor> getVnfd() {
        return vnfds;
    }

    public void setVnfd(List<VNFDescriptor> vnfds) {
        this.vnfds = vnfds;
    }

    @Override
    public String toString() {
        return "VNFDCatalog{" +
                "vnfDescriptors=" + vnfds +
                '}';
    }
}
