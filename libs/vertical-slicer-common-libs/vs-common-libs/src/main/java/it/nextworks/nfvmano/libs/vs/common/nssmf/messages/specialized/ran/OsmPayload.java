package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.ran;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import org.springframework.data.util.Pair;

import java.util.Map;

public class OsmPayload extends NssmfBaseProvisioningMessage {

    @JsonProperty("nstId")
    private String nstId;
    @JsonProperty("nsdId")
    private String nsdId;
    @JsonProperty("networkServiceName")
    private String networkServiceName;

    @JsonProperty("additionalParams")
    private Map<String, String> additionalParams;

    private Pair<String,String> additionalParameters;

    public OsmPayload(){ }

    public OsmPayload(String nstId, String nsdId){

        this.nsdId=nsdId;
        this.nstId=nstId;
    }



    public OsmPayload(String nstId, String nsdId, String networkServiceName){
        this.nsdId = nsdId;
        this.networkServiceName=networkServiceName;
        this.nstId=nstId;
    }

    public void isValid() throws MalformattedElementException {
        if(nsdId==null)
            throw new MalformattedElementException("Error: nsdId not provided into body request.");

        if(networkServiceName==null){
            networkServiceName = "NS instance from "+nsdId;
        }
    }

    public String getNstId() {
        return nstId;
    }

    public String getNsdId() {
        return nsdId;
    }

    public void setNsdId(String nsdId) {
        this.nsdId = nsdId;
    }


    public void setNstId(String nstId){
        this.nstId= nstId;
    }
    public String getNetworkServiceName() {
        return networkServiceName;
    }

    public void setNetworkServiceName(String networkServiceName) {
        this.networkServiceName = networkServiceName;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }

    public Pair<String, String> getAdditionalParameters() {
        return additionalParameters;
    }

    public void setAdditionalParameters(Pair<String, String> additionalParameters) {
        this.additionalParameters = additionalParameters;
    }
}
