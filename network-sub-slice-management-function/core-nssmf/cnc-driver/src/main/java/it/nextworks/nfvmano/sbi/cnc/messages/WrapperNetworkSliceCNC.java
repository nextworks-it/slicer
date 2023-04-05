package it.nextworks.nfvmano.sbi.cnc.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperNetworkSliceCNC {
    @JsonProperty("Data")
    public NetworkSliceCNC networkSliceCNC;
    @JsonProperty("StatusCode")
    public int status;

    public WrapperNetworkSliceCNC(){
        networkSliceCNC = new NetworkSliceCNC();
    }
}
