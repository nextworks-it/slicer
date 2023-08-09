package it.nextworks.nfvmano.sbi.nfvo.osm.rest.model;

import com.google.gson.annotations.SerializedName;

public class ConnectionPoint{
    public String name;
    @SerializedName("connection-point-id")
    public String connectionPointId;
    @SerializedName("connection-point-vdu-id") 
    public String connectionPointVduId;
    public String id;
}
