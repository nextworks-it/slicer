package it.nextworks.nfvmano.sebastian.vsfm;

import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChange;

public class NetworkSliceInternalInfo {

    private String domainId;
    private String nstUuid;
    private LocationInfo locationInfo;

    public NetworkSliceInternalInfo(String domainId, String nstUuid, LocationInfo locationInfo){
        this.domainId=domainId;
        this.nstUuid=nstUuid;
        this.locationInfo=locationInfo;
    }

    public String getDomainId() {
        return domainId;
    }

    public String getNstUuid() {
        return nstUuid;
    }

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

}
