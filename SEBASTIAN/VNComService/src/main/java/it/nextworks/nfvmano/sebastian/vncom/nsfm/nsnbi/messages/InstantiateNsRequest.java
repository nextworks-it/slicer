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

import java.util.List;

public class InstantiateNsRequest implements InterfaceMessage {


    private String nstUuid;
    private String tenantId;
    private String nstDescription;
    private String nstVersion;
    private List<String> nsstUuids;
    private String vsiId;

    public InstantiateNsRequest() { }

    /**
     *
     * @param nstUuid
     * @param tenantId
     * @param nstDescription
     * @param nstVersion
     * @param nsstUuids
     * @param vsiId
     */
    public InstantiateNsRequest(String nstUuid, String tenantId, String nstDescription, String nstVersion,
                                List<String> nsstUuids, String vsiId) {
        this.nstUuid = nstUuid;
        this.tenantId = tenantId;
        this.nstDescription = nstDescription;
        this.nstVersion = nstVersion;
        this.nsstUuids = nsstUuids;
        this.vsiId = vsiId;
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

    public String getNstDescription() {
        return nstDescription;
    }

    public void setNstDescription(String nstDescription) {
        this.nstDescription = nstDescription;
    }

    public String getNstVersion() {
        return nstVersion;
    }

    public void setNstVersion(String nstVersion) {
        this.nstVersion = nstVersion;
    }

    public List<String> getNsstUuids() {
        return nsstUuids;
    }

    public void setNsstUuids(List<String> nsstUuids) {
        this.nsstUuids = nsstUuids;
    }

    /**
     *
     * @return vsiId
     */
    public String getVsiId() {
        return vsiId;
    }

    /**
     *
     * @param vsiId
     */
    public void setVsiId(String vsiId) {
        this.vsiId = vsiId;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
