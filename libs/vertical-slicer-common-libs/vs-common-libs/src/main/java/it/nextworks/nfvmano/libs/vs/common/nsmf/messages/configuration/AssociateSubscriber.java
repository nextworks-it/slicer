package it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration;

public class AssociateSubscriber extends UpdateConfigurationRequest{

    private String imsi;

    public AssociateSubscriber(String imsi){
        super(null, null, null, ConfigSliceSubnetType.E2E);
        this.imsi = imsi;
        this.actionType = ConfigurationActionType.ASSOCIATE_SUBSCRIBER;
    }

    public AssociateSubscriber(){
        super(null, null, null, ConfigSliceSubnetType.E2E);
        this.actionType = ConfigurationActionType.ASSOCIATE_SUBSCRIBER;
    }


    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }


}
