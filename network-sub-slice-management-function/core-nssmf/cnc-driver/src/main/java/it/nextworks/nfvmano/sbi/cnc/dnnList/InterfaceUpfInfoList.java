package it.nextworks.nfvmano.sbi.cnc.dnnList;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class InterfaceUpfInfoList{
    @JsonProperty("interfaceType")
    public String interfaceType;
    @JsonProperty("ipv4EndpointAddresses")
    public ArrayList<String> ipv4EndpointAddresses;
}
