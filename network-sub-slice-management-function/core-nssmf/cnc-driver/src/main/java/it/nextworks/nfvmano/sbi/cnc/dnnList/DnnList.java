package it.nextworks.nfvmano.sbi.cnc.dnnList;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


    public class DnnList{
        @JsonProperty("heartBeatTimer")
        public int heartBeatTimer;
        @JsonProperty("ipv4Addresses")
        public ArrayList<String> ipv4Addresses;
        @JsonProperty("nfInstanceId")
        public String nfInstanceId;
        @JsonProperty("nfInstanceName")
        public String nfInstanceName;
        @JsonProperty("nfStatus")
        public String nfStatus;
        @JsonProperty("nfType")
        public String nfType;
        @JsonProperty("upfInfo")
        public UpfInfo upfInfo;
    }
