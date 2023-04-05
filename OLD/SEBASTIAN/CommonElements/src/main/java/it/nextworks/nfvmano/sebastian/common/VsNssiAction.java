/*
 * Copyright 2019 Nextworks s.r.l.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.sebastian.common;

import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;

import javax.persistence.Embeddable;

@Embeddable
public class VsNssiAction {

    private VsNssiActionType actionType;

    //NetworkSliceSubnet instance id
    private String nssiId;
    //Not used for the moment
    private String nstId;
    private String dfId;
    private String ilId;
    private String domainId;



    public VsNssiAction() {
    }

    /**
     *
     * @param actionType type of action
     * @param nstId nstId
     * @param dfId deployment flavor id
     * @param ilId instantiation level id
     */
    public VsNssiAction( VsNssiActionType actionType, String nssiId, String nstId, String dfId, String ilId, String domainId) {

        this.actionType = actionType;
        this.nstId = nstId;
        this.ilId= ilId;
        this.dfId= dfId;
        this.nssiId= nssiId;
        this.domainId= domainId;
    }


    public String getNssiId() {
        return nssiId;
    }



    /**
     *
     * @return actionType
     */
    public VsNssiActionType getActionType() {
        return actionType;
    }

    public String getNstId() {
        return nstId;
    }

    public String getDfId() {
        return dfId;
    }

    public String getIlId() {
        return ilId;
    }

    public String getDomainId() {
        return domainId;
    }
}
