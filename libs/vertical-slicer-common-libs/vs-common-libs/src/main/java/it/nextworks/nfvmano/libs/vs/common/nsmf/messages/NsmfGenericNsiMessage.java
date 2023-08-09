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

package it.nextworks.nfvmano.libs.vs.common.nsmf.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.interfaces.InterfaceMessage;

import java.util.UUID;

public abstract class NsmfGenericNsiMessage implements InterfaceMessage {
    @JsonProperty("nsiId")
    private UUID nsiId;

    /**
     * Generic Constructor
     */
    public NsmfGenericNsiMessage() {
    }

    /**
     * Constructor
     * @param nsiId ID of the network slice instance
     */
    public NsmfGenericNsiMessage(UUID nsiId) {
        this.nsiId = nsiId;
    }

    public UUID getNsiId() {
        return nsiId;
    }

    public void setNsiId(UUID nsiId) {
        this.nsiId = nsiId;
    }

    public void isValid() throws MalformattedElementException {
        if (nsiId == null) throw new MalformattedElementException("NSI ID not specified.");
    }
}
