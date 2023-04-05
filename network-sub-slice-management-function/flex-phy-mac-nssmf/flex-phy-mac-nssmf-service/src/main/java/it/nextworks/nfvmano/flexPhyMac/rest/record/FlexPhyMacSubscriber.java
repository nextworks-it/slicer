package it.nextworks.nfvmano.flexPhyMac.rest.record;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FlexPhyMacSubscriber {
    @Id
    @GeneratedValue
    private Long ID;
    private String imsi;
    private String flexiblePhyMacUeId;


    public FlexPhyMacSubscriber(){

    }
    public FlexPhyMacSubscriber(String imsi, String flexiblePhyMacUeId) {
        this.imsi = imsi;
        this.flexiblePhyMacUeId = flexiblePhyMacUeId;
    }

    public String getImsi() {
        return imsi;
    }

    public String getFlexiblePhyMacUeId() {
        return flexiblePhyMacUeId;
    }
}
