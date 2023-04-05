package it.nextworks.nfvmano.sbi.cnc.subscribersManagement;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class CoreSubscriberListInfo {
    @JsonProperty("Data1")
    public ArrayList<Data1> data1;
    @JsonProperty("Data2")
    public ArrayList<Data2> data2;
    public int status;
}
