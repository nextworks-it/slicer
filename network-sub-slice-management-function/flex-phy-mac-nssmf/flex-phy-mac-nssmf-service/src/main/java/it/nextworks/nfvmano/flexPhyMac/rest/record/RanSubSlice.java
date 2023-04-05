package it.nextworks.nfvmano.flexPhyMac.rest.record;

import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RanSubSlice {
    @Id
    @GeneratedValue
    private Long ID;
    private String sliceId;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<FlexPhyMacSubscriber> subscriberList = new ArrayList<>();
    private String networkServiceInstanceId;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nsst_id", referencedColumnName = "id")
    private NSST nsst;

    public RanSubSlice(String sliceId) {
        this.sliceId = sliceId;
        subscriberList = new ArrayList<>();
    }

    public RanSubSlice(){}
    public RanSubSlice(String sliceId, ArrayList<FlexPhyMacSubscriber> subscriberList, String networkServiceInstanceId, NSST nsst) {
        this.sliceId = sliceId;
        this.subscriberList = subscriberList;
        this.networkServiceInstanceId = networkServiceInstanceId;
        this.nsst = nsst;
    }


    public String getSliceId() {
        return sliceId;
    }

    public void setSliceId(String sliceId) {
        this.sliceId = sliceId;
    }

    public List<FlexPhyMacSubscriber> getSubscribers() {
        return subscriberList;
    }

    public void setSubscriberList(List<FlexPhyMacSubscriber> subscriberList) {
        this.subscriberList = subscriberList;
    }

    public void setNetworkServiceInstanceId(String networkServiceInstanceId) {
        this.networkServiceInstanceId = networkServiceInstanceId;
    }
    public NSST getNsst() {
        return nsst;
    }

    public void setNsst(NSST nsst) {
        this.nsst = nsst;
    }
}
