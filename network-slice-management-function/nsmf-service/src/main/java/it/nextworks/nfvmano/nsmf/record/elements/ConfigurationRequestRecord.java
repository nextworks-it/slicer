package it.nextworks.nfvmano.nsmf.record.elements;

import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.ConfigurationOperation;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ConfigurationActionType;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.ConfigurationRequestStatus;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ConfigurationRequestRecord {

    @Id
    @GeneratedValue
    private UUID id;

    private ConfigurationRequestStatus status;

    private ConfigurationActionType actionType;

    private UUID networkSliceInstanceId;

    @ElementCollection
    private List<UUID> networkSliceSubnetInstanceIds = new ArrayList<>();

    public ConfigurationRequestRecord() {
    }

    public ConfigurationRequestRecord(ConfigurationRequestStatus status, UUID networkSliceInstanceId, ConfigurationActionType actionType) {
        this.status = status;
        this.networkSliceInstanceId = networkSliceInstanceId;
        this.actionType=actionType;

    }

    public ConfigurationActionType getActionType() {
        return actionType;
    }

    public UUID getId() {
        return id;
    }

    public ConfigurationRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ConfigurationRequestStatus status) {
        this.status = status;
    }

    public UUID getNetworkSliceInstanceId() {
        return networkSliceInstanceId;
    }

    public List<UUID> getNetworkSliceSubnetInstanceIds() {
        return networkSliceSubnetInstanceIds;
    }

    public void setNetworkSliceSubnetInstanceId(List<UUID> networkSliceSubnetInstanceIds) {
        this.networkSliceSubnetInstanceIds = networkSliceSubnetInstanceIds;
    }


    public ConfigurationOperation getConfigurationOperation(){
        return  new ConfigurationOperation(this.id, this.status, this.actionType, this.networkSliceInstanceId, this.networkSliceSubnetInstanceIds);
    }
}
