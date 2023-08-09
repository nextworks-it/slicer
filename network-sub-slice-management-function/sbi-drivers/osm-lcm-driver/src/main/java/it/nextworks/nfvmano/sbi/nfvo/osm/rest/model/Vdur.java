package it.nextworks.nfvmano.sbi.nfvo.osm.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Vdur{
    public String _id;

    @SerializedName("count-index")
    public int countIndex;
    public String id;
    public ArrayList<VnfInterface> interfaces;
    @SerializedName("internal-connection-point") 
    public ArrayList<InternalConnectionPoint> internalConnectionPoint;
    @SerializedName("ip-address") 
    public String ipAddress;
    @SerializedName("ns-flavor-id") 
    public String nsFlavorId;
    @SerializedName("ns-image-id") 
    public String nsImageId;
    @SerializedName("vdu-id-ref") 
    public String vduIdRef;
    @SerializedName("vdu-name") 
    public String vduName;

    public String status;
    @SerializedName("vim-id") 
    public String vimId;
    public String name;

    public String get_id() {
        return _id;
    }

    public int getCountIndex() {
        return countIndex;
    }

    public String getId() {
        return id;
    }

    public ArrayList<VnfInterface> getInterfaces() {
        return interfaces;
    }

    public ArrayList<InternalConnectionPoint> getInternalConnectionPoint() {
        return internalConnectionPoint;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getNsFlavorId() {
        return nsFlavorId;
    }

    public String getNsImageId() {
        return nsImageId;
    }

    public String getVduIdRef() {
        return vduIdRef;
    }

    public String getVduName() {
        return vduName;
    }

    public String getStatus() {
        return status;
    }

    public String getVimId() {
        return vimId;
    }

    public String getName() {
        return name;
    }
}
