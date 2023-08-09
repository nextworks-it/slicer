package it.nextworks.nfvmano.core.recordIM;

import java.util.ArrayList;
import java.util.List;

public class SubscriberGroupInfo {

    private String groupId;
    private String subscriptionProfileId;
    private String sliceId;


    private List<String> ueImsis;
    public SubscriberGroupInfo(String groupId, String subscriptionProfileId, String sliceId) {
        this.groupId = groupId;
        this.subscriptionProfileId = subscriptionProfileId;
        this.sliceId = sliceId;
        ueImsis = new ArrayList<>();
    }

    public String getSubscriptionProfileId() {
        return subscriptionProfileId;
    }

    public void setSubscriptionProfileId(String subscriptionProfileId) {
        this.subscriptionProfileId = subscriptionProfileId;
    }

    public void addImsi(String imsi){
        ueImsis.add(imsi);
    }
    public String getSliceId() {
        return sliceId;
    }

    public void setSliceId(String sliceId) {
        this.sliceId = sliceId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getUeImsis() {
        return ueImsis;
    }

}
