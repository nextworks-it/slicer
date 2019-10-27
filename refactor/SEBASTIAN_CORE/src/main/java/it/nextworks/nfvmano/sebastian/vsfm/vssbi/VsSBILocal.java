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

package it.nextworks.nfvmano.sebastian.vsfm.vssbi;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VsSBILocal extends VsAbstractSBI {
    @Autowired
    NsLcmService nsLcmService;

    @Override
    public void initNewNsLcmManager(String nsiId, String tenantId, String sliceName, String sliceDescription) {
        nsLcmService.initNewNsLcmManager(nsiId, tenantId, sliceName, sliceDescription);
    }

    @Override
    public void instantiateNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String vsiId, List<String> nsSubnetIds) throws NotExistingEntityException {
        nsLcmService.instantiateNs(nsiId, tenantId, nsdId, nsdVersion, dfId, instantiationLevelId, vsiId, nsSubnetIds);
    }

    @Override
    public void modifyNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String vsiId) throws NotExistingEntityException {
        nsLcmService.modifyNs(nsiId, tenantId, nsdId, nsdVersion, dfId, instantiationLevelId, vsiId);
    }

    @Override
    public void terminateNs(String nsiId) throws Exception {
        nsLcmService.terminateNs(nsiId);
    }
}
