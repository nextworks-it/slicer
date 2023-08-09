package it.nextworks.nfvmano.libs.vs.common.nsmf.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ConfigurationActionType;
public class ConfigurationOperation {

    private UUID operationId;
    private ConfigurationRequestStatus status;
    private ConfigurationActionType actionType;
    private UUID networkSliceInstanceId;
    private List<UUID> networkSliceSubnetIds  = new ArrayList<>();



    public ConfigurationOperation(UUID operationId, ConfigurationRequestStatus status, ConfigurationActionType actionType, UUID networkSliceInstanceId, List<UUID> networkSliceSubnetIds) {
        this.operationId = operationId;
        this.status = status;
        this.actionType = actionType;
        this.networkSliceInstanceId = networkSliceInstanceId;
        this.networkSliceSubnetIds = networkSliceSubnetIds;
    }

    public UUID getOperationId() {
        return operationId;
    }

    public ConfigurationRequestStatus getStatus() {
        return status;
    }

    public ConfigurationActionType getActionType() {
        return actionType;
    }

    public UUID getNetworkSliceInstanceId() {
        return networkSliceInstanceId;
    }

    public List<UUID> getNetworkSliceSubnetIds() {
        return networkSliceSubnetIds;
    }
}
