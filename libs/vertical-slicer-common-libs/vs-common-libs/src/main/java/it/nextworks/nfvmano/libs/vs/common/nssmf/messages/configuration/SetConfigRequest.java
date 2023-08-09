/*
 * Copyright (c) 2021 Nextworks s.r.l.
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

/**
 * @author Pietro G. Giardina
 */

package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.BaseConfigurationPayload;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.NssmfGenericNssiMessage;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;

import java.util.UUID;

public class SetConfigRequest extends NssmfGenericNssiMessage {

    @JsonProperty("configurationId")
    private UUID configurationId;
    @JsonProperty("configurationPayload")
    private BaseConfigurationPayload configurationPayload;

    /**
     *
     * @param nssiId ID of the network sub-slice instance
     * @param configurationPayload Payload that specifies the configuration request
     * NOTE: It generates a random Configuration ID
     */
    public SetConfigRequest(UUID nssiId, BaseConfigurationPayload configurationPayload) {
        super(nssiId);
        this.configurationPayload = configurationPayload;
        this.configurationId = UUID.randomUUID();
    }

    /**
     *
     * @param nssiId ID of the network sub-slice instance
     * @param configurationId Id of the configuration
     * @param configurationPayload Payload that specifies the configuration reques
     */
    public SetConfigRequest(UUID nssiId, UUID configurationId, BaseConfigurationPayload configurationPayload) {
        super(nssiId);
        this.configurationId = configurationId;
        this.configurationPayload = configurationPayload;
    }

    public SetConfigRequest(UUID nssiId) {
        super(nssiId);
    }

    public BaseConfigurationPayload getConfigurationPayload() {
        return configurationPayload;
    }

    public void setConfigurationPayload(BaseConfigurationPayload configurationPayload) {
        this.configurationPayload = configurationPayload;
    }

    public UUID getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(UUID configurationId) {
        this.configurationId = configurationId;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        super.isValid();
        if (configurationId == null) throw new MalformattedElementException("Configuration ID not specified.");
    }
}
