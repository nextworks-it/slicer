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

import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.nfvodriver.NsStatusChange;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.interfaces.NsManagementInterface;
import it.nextworks.nfvmano.sebastian.nsmf.nsmanagement.NsLocalEngine;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.interfaces.NsNbInterface;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.messages.InstantiateNsRequest;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.messages.ModifyNsRequest;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.messages.TerminateNsRequest;
import it.nextworks.nfvmano.sebastian.vncom.vsfm.vssbi.VsSBIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NsLcmService implements NsNbInterface {

    private static final Logger log = LoggerFactory.getLogger(NsLcmService.class);

    @Autowired
    private NsLocalEngine nsLocalEngine;

    @Autowired
    private VsSBIService vsSBIService;

    @PostConstruct
    public void configComService(){
        setNsLcmService(this);
    }

    public void setNsLcmService(NsNbInterface nsLcmService) {
        vsSBIService.setNsLcmService(nsLcmService);
    }

    /********************************************************************************/

    @Override
    public void instantiateNs(InstantiateNsRequest request) throws NotExistingEntityException, MalformattedElementException {
        log.debug("Received request to instantiate a new Vertical Service instance.");
        request.isValid();

        String tenantId = request.getTenantId();
        String nstId = request.getNstId();

        //STEP 1: Extract all of the NSSTs from NST and the NSDs
        //STEP 2: For each nsd:
        //nsLocalEngine.initNewNsLcmManager(nsiId, tenantId, sliceName, sliceDescription);
        //nsLocalEngine.instantiateNs(nsiId, tenantId, nsdId, nsdVersion, dfId, instantiationLevelId, vsiId, nsSubnetIds);

    }

    @Override
    public void modifyNs(ModifyNsRequest request) throws NotExistingEntityException {
//        nsLocalEngine.modifyNs(nsiId, tenantId, nsdId, nsdVersion, dfId, instantiationLevelId, vsiId);
    }

    @Override
    public void terminateNs(TerminateNsRequest request) throws Exception {
//        nsLocalEngine.terminateNs(nsiId);
    }

}
