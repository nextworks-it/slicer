package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmVDUInterfaceSimplified {

    @JsonProperty("ip-address")
    private String ipAddress;
    @JsonProperty("name")
    private String name;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
