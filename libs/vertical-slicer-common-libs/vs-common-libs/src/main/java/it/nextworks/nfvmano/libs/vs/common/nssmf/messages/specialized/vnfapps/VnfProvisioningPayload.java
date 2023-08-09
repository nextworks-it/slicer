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

package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.vnfapps;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;

import java.util.HashMap;
import java.util.Map;

public class VnfProvisioningPayload extends NssmfBaseProvisioningMessage {
    @JsonProperty("e2e-slice-id")
    private String e2eSliceId;

    @JsonProperty("nsd-id")
    private String nsdId;

    @JsonProperty("vnf-apps")
    private Map<String, String> vnfApps = new HashMap<>();

    public  VnfProvisioningPayload() { super(); }

    public String getE2eSliceId() {
        return e2eSliceId;
    }

    public Map<String, String> getVnfApps() {
        return vnfApps;
    }

    public String getNsdId() {
        return nsdId;
    }

    public void setE2eSliceId(String e2eSliceId) {
        this.e2eSliceId = e2eSliceId;
    }

    public void setNsdId(String nsdId) {
        this.nsdId = nsdId;
    }

    public void setVnfApps(Map<String, String> vnfApps) {
        this.vnfApps = vnfApps;
    }
}
