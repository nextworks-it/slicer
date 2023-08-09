package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OsmVNFPackage {

    @JsonProperty("vnfd-catalog")
    @JsonAlias({ "vnfd-catalog", "vnfd:vnfd-catalog" })
    private VNFDCatalog vnfdCatalog;

    public OsmVNFPackage() { }

    public VNFDCatalog getVnfdCatalog() {
            return vnfdCatalog;
        }

    public OsmVNFPackage setVnfdCatalog(VNFDCatalog vnfdCatalog) {
        this.vnfdCatalog = vnfdCatalog;
        return this;
    }

    @Override
    public String toString() {
        return "TopLevelContainer{" +
                "catalog=" + vnfdCatalog +
                '}';
    }
}
