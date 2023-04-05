package it.nextworks.nfvmano.sbi.cnc.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.sbi.cnc.elements.*;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

public class NetworkSliceCNC {
    @JsonProperty("sliceName")
    public String sliceName;
    @JsonProperty("activate_slice")
    public int activate_slice;
    @JsonProperty("serviceProfile")
    public ServiceProfile serviceProfile;
    @JsonProperty("networkSliceSubnet")
    public NetworkSliceSubnet networkSliceSubnet;
    @JsonProperty("sliceDescription")
    public String sliceDescription;

    public NetworkSliceCNC(){
        serviceProfile = new ServiceProfile();
        networkSliceSubnet = new NetworkSliceSubnet();
    }
}

