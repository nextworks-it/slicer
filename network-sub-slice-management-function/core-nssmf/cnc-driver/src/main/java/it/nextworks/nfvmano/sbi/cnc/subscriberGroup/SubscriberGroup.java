package it.nextworks.nfvmano.sbi.cnc.subscriberGroup;

import java.util.ArrayList;
    public class SubscriberGroup{
        public String _id;
        public ArrayList<Subscription> subscription;
        public SubscribedUeAmbr subscribedUeAmbr;
        public String description;
        // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
}
