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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.sebastian.common.VsAction;

import java.util.Map;

public class CoordinateVsiRequest extends EngineMessage{
    @JsonProperty("vsiCoordinatorId")
    private String vsiCoordinatorId;

    @JsonProperty("vsiId")
    private String vsiId;

    @JsonProperty("candidateVsis")
    private Map<String, VsAction> candidateVsis;  //Note <vsiId, VsAction>


    /**
     *
     * @param vsiCoordinatorId
     * @param candidateVsis
     */
    @JsonCreator
    public CoordinateVsiRequest(@JsonProperty("vsiCoordinatorId") String vsiCoordinatorId,
                                @JsonProperty("candidateVsis") Map<String, VsAction> candidateVsis) {
        this.type = EngineMessageType.COORDINATE_VSI_REQUEST;
        this.vsiCoordinatorId = vsiCoordinatorId;
        if(candidateVsis != null)
            this.candidateVsis = candidateVsis;

    }

    /**
     *
     * @return vsiCoordinatorId
     */
    public String getVsiCoordinatorId() {
        return vsiCoordinatorId;
    }

    /**
     *
     * @return candidateVsis
     */
    public Map<String, VsAction> getCandidateVsis() {
        return candidateVsis;
    }
}
