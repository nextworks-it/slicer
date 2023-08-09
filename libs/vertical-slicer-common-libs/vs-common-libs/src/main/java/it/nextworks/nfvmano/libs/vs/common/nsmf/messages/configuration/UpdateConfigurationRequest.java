package it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.NsmfGenericNsiMessage;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "actionType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SliceTransferConfig.class, name = "SLICE_TRANSFER"),
        @JsonSubTypes.Type(value = SatelliteNetworkConfiguration.class, name = "SATELLITE_NETWORK_CONFIGURATION"),
        @JsonSubTypes.Type(value = AssociateSubscriber.class, name = "ASSOCIATE_SUBSCRIBER"),
        @JsonSubTypes.Type(value = ScaleNetworkSlice.class, name = "SLICE_SCALING"),
        @JsonSubTypes.Type(value = ScaleNetworksSliceNewUpf.class, name = "UPF_SCALING"),
})
public class UpdateConfigurationRequest extends NsmfGenericNsiMessage {


    private String nstId;
    private UUID nssiId;
    private ConfigSliceSubnetType sliceSubnetType;
    @JsonProperty("actionType")
    ConfigurationActionType actionType;

    @Override
    public void isValid() throws MalformattedElementException {

    }

    public UpdateConfigurationRequest(){

    }

    public UpdateConfigurationRequest(UUID nsiId, UUID nssiId, String nstId, ConfigSliceSubnetType sliceSubnetType){
        super(nsiId);
        this.nssiId=nssiId;
        this.sliceSubnetType=sliceSubnetType;

        this.nstId=nstId;

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

    public ConfigurationActionType getActionType() {
        return actionType;
    }
}
