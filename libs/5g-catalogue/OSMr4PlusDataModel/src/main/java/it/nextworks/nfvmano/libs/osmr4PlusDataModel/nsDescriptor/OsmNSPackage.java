package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OsmNSPackage {

    @JsonProperty("nsd-catalog")
	@JsonAlias({ "nsd-catalog", "nsd:nsd-catalog" })
    public NSDCatalog nsdCatalog;

    public OsmNSPackage() { }

    public NSDCatalog getNsdCatalog() {
        return nsdCatalog;
    }

    public OsmNSPackage setNsdCatalog(NSDCatalog nsdCatalog) {
        this.nsdCatalog = nsdCatalog;
        return this;
    }

    @Override
    public String toString() {
        return "OsmNSPackage{" +
                "catalog=" + nsdCatalog +
                '}';
    }
}
