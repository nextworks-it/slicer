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

package it.nextworks.nfvmano.sebastian.vsfm.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.sebastian.common.VsNssiAction;

import java.util.Map;

public class CoordinateVsiNssiRequest extends VsmfEngineMessage{
    @JsonProperty("vsiRequesterId")
    private String vsiRequesterId;



    @JsonProperty("vsNssiAction")
    private VsNssiAction vsNssiAction;


    /**
     *
     * @param vsiRequesterId
     * @param vsNsiAction
     */
    @JsonCreator
    public CoordinateVsiNssiRequest(@JsonProperty("vsiRequesterId") String vsiRequesterId,
                                    @JsonProperty("vsNssiAction") VsNssiAction vsNsiAction) {
        this.type = VsmfEngineMessageType.COORDINATE_VSI_NSSI_REQUEST;
        this.vsiRequesterId = vsiRequesterId;
        this.vsNssiAction = vsNsiAction;


    }

    /**
     *
     * @return vsiRequesterId
     */
    public String getVsiRequesterId() {
        return vsiRequesterId;
    }

    /**
     *
     * @return vsNssiAction
     */
    public VsNssiAction getVsNssiAction() {
        return vsNssiAction;
    }
}
