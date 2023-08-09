package it.nextworks.nfvmano.sbi.cnc.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.sbi.cnc.dnnList.DnnList;

import java.util.ArrayList;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
DnnListCnc root = om.readValue(myJsonString, DnnListCnc.class); */

public class DnnListCnc {
    @JsonProperty("DnnList")
    public ArrayList<DnnList> dnnList;

}
