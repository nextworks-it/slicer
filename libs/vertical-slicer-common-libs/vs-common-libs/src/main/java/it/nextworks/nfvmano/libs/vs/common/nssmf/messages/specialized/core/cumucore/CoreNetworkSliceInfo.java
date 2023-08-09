package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NstServiceProfile;

import java.util.List;

//TODO Delete or move. First check if is user by NSSMF-core
public class CoreNetworkSliceInfo {
    @JsonProperty("serviceProfile")
    private NstServiceProfile serviceProfile;
    @JsonProperty("nsst")
    private NSST nsst;
    @JsonProperty("subscriberList")
    private List<Subscriber> subscriberList;

    public CoreNetworkSliceInfo() {

    }

    public NstServiceProfile getServiceProfile() {
        return serviceProfile;
    }

    public void setServiceProfile(NstServiceProfile serviceProfile) {
        this.serviceProfile = serviceProfile;
    }

    public NSST getNsst() {
        return nsst;
    }

    public void setNsst(NSST nsst) {
        this.nsst = nsst;
    }

    public List<Subscriber> getSubscriberList() {
        return subscriberList;
    }

    public void setSubscriberList(List<Subscriber> subscriberList) {
        this.subscriberList = subscriberList;
    }
}
