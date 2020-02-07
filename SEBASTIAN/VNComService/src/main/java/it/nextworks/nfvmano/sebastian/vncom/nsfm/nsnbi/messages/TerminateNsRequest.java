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

package it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.messages;


import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

public class TerminateNsRequest implements InterfaceMessage {

    private String nstUuid;
    private String tenantId;

    public TerminateNsRequest() { }

    /**
     *
     * @param nstUuid
     * @param tenantId
     */
    public TerminateNsRequest(String nstUuid, String tenantId) {
        this.nstUuid = nstUuid;
        this.tenantId = tenantId;
    }

    public String getNstUuid() {
        return nstUuid;
    }

    public void setNstUuid(String nstUuid) {
        this.nstUuid = nstUuid;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
