package it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration;

import java.util.UUID;

public class ScaleNetworksSliceNewUpf extends UpdateConfigurationRequest{
    private String scalingOption;
    private String upfName;

    public ScaleNetworksSliceNewUpf(String upfName, ConfigurationActionType configurationActionType, String scalingOption){
        super(null, null, null, ConfigSliceSubnetType.E2E);
        this.actionType = configurationActionType.UPF_SCALING;
        this.scalingOption = scalingOption;
        this.upfName = upfName;
    }

    public ScaleNetworksSliceNewUpf(){
        super(null, null, null, ConfigSliceSubnetType.E2E);
        this.actionType = ConfigurationActionType.UPF_SCALING;
    }

    public String getScalingOption() {
        return scalingOption;
    }

    public void setScalingOption(String scalingOption) {
        this.scalingOption = scalingOption;
    }

    public String getUpfName() {
        return upfName;
    }

    public void setUpfName(String upfName) {
        this.upfName = upfName;
    }
}
