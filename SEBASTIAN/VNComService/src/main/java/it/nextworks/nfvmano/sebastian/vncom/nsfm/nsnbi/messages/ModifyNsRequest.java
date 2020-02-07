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

public class ModifyNsRequest implements InterfaceMessage {

    private String nsiUuid;
    private String nstUuid;
    private String tenantId;

    /**
     *
     * @param nsiUuid
     * @param nstUuid
     * @param tenantId
     */
    public ModifyNsRequest(String nsiUuid, String nstUuid, String tenantId) {
        this.nsiUuid = nsiUuid;
        this.nstUuid = nstUuid;
        this.tenantId = tenantId;
    }

    public ModifyNsRequest() { }

    public String getNsiUuid() {
        return nsiUuid;
    }

    public void setNsiUuid(String nsiUuid) {
        this.nsiUuid = nsiUuid;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
