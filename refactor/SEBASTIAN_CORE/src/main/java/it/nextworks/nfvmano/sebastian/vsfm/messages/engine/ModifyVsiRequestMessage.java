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

package it.nextworks.nfvmano.sebastian.vsfm.messages.engine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.sebastian.vsfm.messages.ModifyVsRequest;

public class ModifyVsiRequestMessage extends EngineMessage {

    @JsonProperty("vsiId")
    private String vsiId;

    @JsonProperty("request")
    private ModifyVsRequest request;

    /**
     * Constructor
     *
     * @param vsiId ID of the VS to be instantiated
     * @param request VSI modification request
     */
    @JsonCreator
    public ModifyVsiRequestMessage(@JsonProperty("vsiId") String vsiId,
                                   @JsonProperty("request") ModifyVsRequest request) {
        this.type = EngineMessageType.MODIFY_VSI_REQUEST;
        this.vsiId = vsiId;
        this.request = request;
    }

    /**
     * @return the vsiId
     */
    public String getVsiId() {
        return vsiId;
    }

    /**
     * @return the request
     */
    public ModifyVsRequest getRequest() {
        return request;
    }
}
