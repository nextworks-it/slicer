package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SonataNSTerminationRequest extends SonataRequest {

    @JsonProperty("instance_uuid")
    private String instanceId;

    public SonataNSTerminationRequest(String instanceId) {
        super(SonataRequestType.TERMINATE_SLICE);
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
