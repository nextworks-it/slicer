package it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration;

public class ScaleNetworkSlice extends UpdateConfigurationRequest{
    private String scalingOption;
    private int newDataRate;

    public ScaleNetworkSlice(int newDataRate, ConfigurationActionType configurationActionType, String scalingOption){
        super(null, null, null, ConfigSliceSubnetType.E2E);
        this.actionType = configurationActionType.SLICE_SCALING;
        this.scalingOption = scalingOption;
        this.newDataRate = newDataRate;
    }

    public ScaleNetworkSlice(){
        super(null, null, null, ConfigSliceSubnetType.E2E);
        this.actionType = ConfigurationActionType.SLICE_SCALING;
    }


    public int getNewDataRate() {
        return newDataRate;
    }

    public void setNewDataRate(int newDataRate) {
        this.newDataRate = newDataRate;
    }

    public String getScalingOption() {
        return scalingOption;
    }

    public void setScalingOption(String scalingOption) {
        this.scalingOption = scalingOption;
    }



}
