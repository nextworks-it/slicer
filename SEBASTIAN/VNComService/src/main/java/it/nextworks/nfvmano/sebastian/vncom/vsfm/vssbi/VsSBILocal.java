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

package it.nextworks.nfvmano.sebastian.vncom.vsfm.vssbi;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.interfaces.NsNbInterface;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.messages.InstantiateNsRequest;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.messages.ModifyNsRequest;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.messages.TerminateNsRequest;

public class VsSBILocal extends VsAbstractSBI {


    private NsNbInterface nsLcmService;

    public void setNsLcmService(NsNbInterface nsLcmService) {
        this.nsLcmService = nsLcmService;
    }

    @Override
    public void instantiateNs(InstantiateNsRequest request) throws NotExistingEntityException, MalformattedElementException {
        nsLcmService.instantiateNs(request);
    }

    @Override
    public void modifyNs(ModifyNsRequest request) throws NotExistingEntityException {
        nsLcmService.modifyNs(request);
    }

    @Override
    public void terminateNs(TerminateNsRequest request) throws Exception {
        nsLcmService.terminateNs(request);
    }
}
