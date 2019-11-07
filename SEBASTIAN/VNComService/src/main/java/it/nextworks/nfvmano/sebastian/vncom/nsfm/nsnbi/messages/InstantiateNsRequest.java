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


    private String nstId;
    private String tenantId;
    private String nstDescription;
    private String nstVersion;
    private List<String> nsstIds;
    private String vsiId;

    public InstantiateNsRequest() { }

    /**
     *
     * @param nstId
     * @param tenantId
     * @param nstDescription
     * @param nstVersion
     * @param nsstIds
     * @param vsiId
     */
    public InstantiateNsRequest(String nstId, String tenantId, String nstDescription, String nstVersion,
                                List<String> nsstIds, String vsiId) {
        this.nstId = nstId;
        this.tenantId = tenantId;
        this.nstDescription = nstDescription;
        this.nstVersion = nstVersion;
        this.nsstIds = nsstIds;
        this.vsiId = vsiId;
    }

    public String getNstId() {
        return nstId;
    }

    public void setNstId(String nstId) {
        this.nstId = nstId;
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

    public List<String> getNsstIds() {
        return nsstIds;
    }

    public void setNsstIds(List<String> nsstIds) {
        this.nsstIds = nsstIds;
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
