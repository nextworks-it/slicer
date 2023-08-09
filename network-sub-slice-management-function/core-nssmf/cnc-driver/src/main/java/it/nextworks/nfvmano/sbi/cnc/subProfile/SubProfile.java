package it.nextworks.nfvmano.sbi.cnc.subProfile;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SubProfile {
        // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
            public String _id;
            public String dnn;
            @JsonProperty("5gQosProfile")
            public _5gQosProfile _5gQosProfile;
            public SessionAmbr sessionAmbr;
            public PduSessionTypes pduSessionTypes;
            public SscModes sscModes;
}

