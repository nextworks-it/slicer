/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.sebastian.engine.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class VsiTerminationNotificationMessage extends EngineMessage {
    @JsonProperty("vsiId")
    String vsiId;

    /**
     *
     * @param vsiId Id of terminated Vsi
     */
    public VsiTerminationNotificationMessage(@JsonProperty("vsiId") String vsiId){
        this.vsiId = vsiId;
        this.type = EngineMessageType.NOTIFY_TERMINATION;
    }

    /**
     *
     * @return vsiId
     */
    public String getVsiId() {
        return vsiId;
    }
}
