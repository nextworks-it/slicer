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

package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpfProvisioningPayload extends NssmfBaseProvisioningMessage {

    @JsonProperty("e2e-slice-id")
    private String e2eSliceId;

    @JsonProperty("user-plane-functions")
    private Map<String, String> userPlaneFunctions = new HashMap<>();

    @JsonProperty("network-slice-type")
    private String networkSliceType;


    @JsonProperty("network-slice-parameters")
    private Map<String, Integer> networkSliceParamters = new HashMap<>();



    public UpfProvisioningPayload(){ super();}

    public String getNetworkSliceType() {
        return networkSliceType;
    }

    public Map<String, Integer> getNetworkSliceParamters() {
        return networkSliceParamters;
    }
    public Map<String, String> getUserPlaneFunctions() {
        return userPlaneFunctions;
    }

    public String getE2eSliceId() {
        return e2eSliceId;
    }

    public void setE2eSliceId(String e2eSliceId) {
        this.e2eSliceId = e2eSliceId;
    }

    public void setNetworkSliceType(String networkSliceType) {
        this.networkSliceType = networkSliceType;
    }

    public void setNetworkSliceParamters(Map<String, Integer> networkSliceParamters) {
        this.networkSliceParamters = networkSliceParamters;


    }

    public void setUserPlaneFunctions(Map<String, String> userPlaneFunctions) {
        this.userPlaneFunctions = userPlaneFunctions;
    }
}

