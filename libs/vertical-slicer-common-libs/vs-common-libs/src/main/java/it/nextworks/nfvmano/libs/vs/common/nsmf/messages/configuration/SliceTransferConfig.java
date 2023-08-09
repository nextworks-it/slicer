package it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration;

import java.util.UUID;

public class SliceTransferConfig extends UpdateConfigurationRequest{


    private String origin;
    private String target;

    public SliceTransferConfig(){
        this.actionType = ConfigurationActionType.SLICE_TRANSFER;
    }


    public SliceTransferConfig(UUID nsiId, UUID nssiId, String nstId, ConfigSliceSubnetType sliceSubnetType, String origin, String target){

        super(nsiId, nssiId, nstId, sliceSubnetType);
        this.actionType = ConfigurationActionType.SLICE_TRANSFER;
        this.origin = origin;
        this.target = target;

    }

    public String getOrigin() {
        return origin;
    }

    public String getTarget() {
        return target;
    }
}
