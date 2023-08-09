/*
 * Copyright (c) 2022 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.TransportConfig;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ConfigurationActionType;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;

import java.util.*;

public class SdnConfigPayload extends NssmfBaseProvisioningMessage{

    @JsonProperty("target-nssi-id")
    private UUID targetNssiId;
    @JsonProperty("target-transport-id")
    private String targetTransportId;

    @JsonProperty("transport-specifications")
    private List<Map<String, String>> transportSpecifications = new ArrayList<>();




    //TODO: Fix this
    @JsonProperty("configurationActionType")
    private ConfigurationActionType configurationActionType;
    @JsonProperty("sliceFlowTransfers")
    private Map<String, String> sliceFlowTransfers = new HashMap<>();
    @JsonProperty("transportConfig")
    private TransportConfig transportConfig;
    @JsonProperty("operation-id")
    private UUID operationId;

    public SdnConfigPayload() { super();}

    public UUID getTargetNssiId() {
        return targetNssiId;
    }

    public String getTargetTransportId() {
        return targetTransportId;
    }

    public List<Map<String, String>> getTransportSpecifications() {
        return transportSpecifications;
    }

    public void setTargetNssiId(UUID targetNssiId) {
        this.targetNssiId = targetNssiId;
    }

    public void setTargetTransportId(String targetTransportId) {
        this.targetTransportId = targetTransportId;
    }

    public void setTransportSpecifications(List<Map<String, String>> transportSpecifications) {
        this.transportSpecifications = transportSpecifications;
    }

    public UUID getOperationId() {
        return operationId;
    }

    public void setOperationId(UUID operationId) {
        this.operationId = operationId;
    }

    public ConfigurationActionType getConfigurationActionType() {
        return configurationActionType;
    }

    public void setConfigurationActionType(ConfigurationActionType configurationActionType) {
        this.configurationActionType = configurationActionType;
    }

    public Map<String, String> getSliceFlowTransfers() {
        return sliceFlowTransfers;
    }

    public void setSliceFlowTransfers(Map<String, String> sliceFlowTransfers) {
        this.sliceFlowTransfers = sliceFlowTransfers;
    }

    public TransportConfig getTransportConfig() {
        return transportConfig;
    }

    public void setTransportConfig(TransportConfig transportConfig) {
        this.transportConfig = transportConfig;
    }
}
