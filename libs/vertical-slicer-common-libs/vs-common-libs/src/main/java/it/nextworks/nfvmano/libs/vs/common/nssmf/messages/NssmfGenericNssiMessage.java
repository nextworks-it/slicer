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

package it.nextworks.nfvmano.libs.vs.common.nssmf.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.interfaces.InterfaceMessage;


import java.util.UUID;

public abstract class NssmfGenericNssiMessage implements InterfaceMessage {
    @JsonProperty("nssiId")
    private UUID nssiId;

    /**
     * Generic Constructor
     */
    public NssmfGenericNssiMessage() {
    }

    /**
     * Constructor
     * @param nssiId ID of the network sub-slice instance
     */
    public NssmfGenericNssiMessage(UUID nssiId) {
        this.nssiId = nssiId;
    }

    public UUID getNssiId() {
        return nssiId;
    }

    public void setNssiId(UUID nssiId) {
        this.nssiId = nssiId;
    }

    public void isValid() throws MalformattedElementException {
        if (nssiId == null) throw new MalformattedElementException("NSSI ID not specified.");
    }
}
