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

public class NotifyVsiNssiCoordinationEnd extends VsmfEngineMessage{



    @JsonProperty("networkSliceInstanceId")
    private String networkSliceInstanceId;
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("successful")
    private boolean successful;



    @JsonCreator
    public NotifyVsiNssiCoordinationEnd(@JsonProperty("networkSliceInstanceId")String networkSliceInstanceId,
                                        @JsonProperty("domain")String domain,
                                        @JsonProperty("vsiId")boolean successful) {
        this.type = VsmfEngineMessageType.NOTIFY_VSI_NSSI_COORDINATION_END;
        this.networkSliceInstanceId = networkSliceInstanceId;
        this.domain=domain;
        this.successful = successful;


    }

    public String getNetworkSliceInstanceId() {
        return networkSliceInstanceId;
    }

    public String getDomain() {
        return domain;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
