package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.transport.pon;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;

public class TnPonPayload extends NssmfBaseProvisioningMessage {

    @JsonProperty("traffic-type")
    private TrafficType trafficType;
    @JsonProperty("sla-name")
    private String slaName;
    @JsonProperty("fixed-bw")
    private int fixedBw;
    @JsonProperty("assured-bw")
    private int assuredBw;

    @JsonProperty("be-bw")
    private int beBw;


    @JsonProperty("vlan")
    private int vlan;

    @JsonProperty("onu")
    private String onu;


    public TnPonPayload(){
    }

    public TrafficType getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(TrafficType trafficType) {
        this.trafficType = trafficType;
    }

    public String getSlaName() {
        return slaName;
    }

    public void setSlaName(String slaName) {
        this.slaName = slaName;
    }

    public int getFixedBw() {
        return fixedBw;
    }

    public void setFixedBw(int fixedBw) {
        this.fixedBw = fixedBw;
    }

    public int getAssuredBw() {
        return assuredBw;
    }

    public void setAssuredBw(int assuredBw) {
        this.assuredBw = assuredBw;
    }

    public int getBeBw() {
        return beBw;
    }

    public void setBeBw(int beBw) {
        this.beBw = beBw;
    }

    public int getVlan() {
        return vlan;
    }

    public void setVlan(int vlan) {
        this.vlan = vlan;
    }

    public String getOnu() {
        return onu;
    }

    public void setOnu(String onu) {
        this.onu = onu;
    }

    public void isValid() throws MalformattedElementException {
        if (slaName == null) throw new MalformattedElementException("slaName not specified.");
        if (trafficType == null) throw new MalformattedElementException("trafficType not specified.");
        if (onu == null) throw new MalformattedElementException("onu not specified.");
        if (vlan == 0) throw new MalformattedElementException("vlan not specified.");
        if (fixedBw == 0 && assuredBw==0 && beBw==0) throw new MalformattedElementException("At least one of either fixed or assured or Best effort BW must be greater than 0.");

    }


}
