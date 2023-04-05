package it.nextworks.nfvmano.sbi.cnc.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class WrapperListNetworkSliceCNC {
    @JsonProperty("Data")
    public ArrayList<NetworkSliceCNC> networkSliceCNC;
    @JsonProperty("StatusCode")
    public int status;

    public WrapperListNetworkSliceCNC(){
        networkSliceCNC = new ArrayList<>();
    }
}
