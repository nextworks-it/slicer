package it.nextworks.nfvmano.core.recordIM;


import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class SubscriberListForSlice {
    private String sliceName;
    private List<Subscriber> subscriberList;

    public SubscriberListForSlice() {
        subscriberList = new ArrayList<>();
    }


    public String getSliceName() {
        return sliceName;
    }

    public void setSliceName(String sliceName) {
        this.sliceName = sliceName;
    }

    public List<Subscriber> getSubscribers() {
        return subscriberList;
    }

    public void setSubscriberList(List<Subscriber> subscriberList) {
        this.subscriberList = subscriberList;
    }
}
