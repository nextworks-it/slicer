package it.nextworks.nfvmano.sbi.cnc.subscribersManagement;

public class SubscriberInfo {
    public Subscriberprofile subscriberprofile;
    public SessionManSubscription sessionManSubscription;

    public SubscriberInfo(){
        subscriberprofile = new Subscriberprofile();
        sessionManSubscription = new SessionManSubscription();

    }
}
