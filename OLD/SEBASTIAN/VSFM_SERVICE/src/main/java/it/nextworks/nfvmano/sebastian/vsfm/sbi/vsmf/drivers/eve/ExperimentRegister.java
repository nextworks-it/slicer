package it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers.eve;

import io.swagger.elcm.client.model.Experiment;
import it.nextworks.nfvmano.sebastian.admin.elements.RemoteTenantInfo;

import java.util.HashMap;
import java.util.Map;

public class ExperimentRegister {

    private String name;
    private String expdId;
    private String expId;
    private Experiment.StatusEnum experimentStatus;
    private RemoteTenantInfo remoteTenantInfo;
    private Map<String, String> userData= new HashMap<>();

    public ExperimentRegister(String name, String expdId, String expId, Experiment.StatusEnum experimentStatus, RemoteTenantInfo remoteTenantInfo, Map<String, String> userData) {
        this.name = name;
        this.expdId = expdId;
        this.expId = expId;
        this.experimentStatus = experimentStatus;
        this.remoteTenantInfo = remoteTenantInfo;
        if(userData!=null) this.userData=userData;
    }

    public String getName() {
        return name;
    }

    public RemoteTenantInfo getRemoteTenantInfo(){
        return remoteTenantInfo;
    }

    public String getExpdId() {
        return expdId;
    }

    public Map<String, String> getUserData() {
        return userData;
    }

    public String getExpId() {
        return expId;
    }



    public Experiment.StatusEnum getExperimentStatus() {
        return experimentStatus;
    }

    public void setExperimentStatus(Experiment.StatusEnum experimentStatus) {
        this.experimentStatus = experimentStatus;
    }
}
