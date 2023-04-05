package it.nextworks.nfvmano.nsmf.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceSubnetType;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ConfigSliceSubnetType;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.UpdateConfigurationRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;

import java.util.UUID;

public class EngineUpdateNsiRequest extends NsmfEngineMessage{


    @JsonProperty("nstId")
    private String nstId;
    @JsonProperty("nssiId")
    private UUID nssiId;
    @JsonProperty("sliceSubnetType")
    private ConfigSliceSubnetType sliceSubnetType;


    private UUID configurationRequestId;
    @JsonProperty("updateConfigurationRequest")
    private UpdateConfigurationRequest updateConfigurationRequest;


    @JsonCreator
    public EngineUpdateNsiRequest(
            @JsonProperty("configurationRequestId") UUID configurationRequestId,
            @JsonProperty("nstId") String nstId,
                                  @JsonProperty("nssiId") UUID nssiId,
                                  @JsonProperty("updateConfigurationRequest")UpdateConfigurationRequest updateConfigurationRequest,
                                  @JsonProperty("sliceSubnetType") ConfigSliceSubnetType sliceSubnetType) {
        this.type = NsmfEngineMessageType.UPDATE_NSI_REQUEST;
        this.nstId = nstId;
        this.nssiId = nssiId;
        this.updateConfigurationRequest =updateConfigurationRequest;
        this.sliceSubnetType=sliceSubnetType;
        this.configurationRequestId= configurationRequestId;
    }

    public UUID getConfigurationRequestId() {
        return configurationRequestId;
    }

    public ConfigSliceSubnetType getSliceSubnetType() {
        return sliceSubnetType;
    }

    public String getNstId() {
        return nstId;
    }

    public UUID getNssiId() {
        return nssiId;
    }

    public UpdateConfigurationRequest getUpdateConfigurationRequest() {
        return updateConfigurationRequest;
    }
}
