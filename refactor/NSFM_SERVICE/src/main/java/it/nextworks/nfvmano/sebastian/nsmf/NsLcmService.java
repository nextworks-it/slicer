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

package it.nextworks.nfvmano.sebastian.nsmf;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.nfvodriver.NsStatusChange;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.N2VCommunicationService.NsManagementInterface;
import it.nextworks.nfvmano.sebastian.nsmf.nsmanagement.NsLocalEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NsLcmService implements NsManagementInterface {
    @Autowired
    private NsLocalEngine nsLocalEngine;

    @Override
    public void initNewNsLcmManager(String nsiId, String tenantId, String sliceName, String sliceDescription) {
        nsLocalEngine.initNewNsLcmManager(nsiId, tenantId, sliceName, sliceDescription);
    }

    @Override
    public void instantiateNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String vsiId, List<String> nsSubnetIds) throws NotExistingEntityException {
        nsLocalEngine.instantiateNs(nsiId, tenantId, nsdId, nsdVersion, dfId, instantiationLevelId, vsiId, nsSubnetIds);
    }

    @Override
    public void modifyNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String vsiId) throws NotExistingEntityException {
        nsLocalEngine.modifyNs(nsiId, tenantId, nsdId, nsdVersion, dfId, instantiationLevelId, vsiId);
    }

    @Override
    public void terminateNs(String nsiId) throws Exception {
        nsLocalEngine.terminateNs(nsiId);
    }

    /**
     * This method processes a notification about a change in the status of a network slice.
     * This notification is dispatched towards the VSManagement
     *
     * @param networkSliceId ID of the network slice affected by the change of status
     * @param changeType type of change in the network slice
     * @param successful indicates if the change has been successful or not
     */
    public void notifyNetworkSliceStatusChange(String networkSliceId, NsStatusChange changeType, boolean successful) {
        //TODO: To Remote Engine
    }
}
