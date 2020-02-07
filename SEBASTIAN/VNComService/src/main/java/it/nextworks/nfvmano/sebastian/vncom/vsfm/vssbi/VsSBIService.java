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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class VsSBIService implements NsNbInterface {

    private static final Logger log = LoggerFactory.getLogger(VsSBIService.class);

    @Value("${vertical.network.communication}")
    private String comType;

    private VsAbstractSBI vsSBI;


    @PostConstruct
    public void initV2NCom() {
        log.debug("Initializing Vertical to Network Communication");
        if (comType.equals("LOCAL")) {
            log.debug("The VSFM is configured to communicate with NSFM using local queues.");
            vsSBI = new VsSBILocal();
        } else {
            log.error("V2N Comm service not configured!");
        }

    }


    public void setNsLcmService(NsNbInterface nsLcmService) {
        vsSBI.setNsLcmService(nsLcmService);
    }

    @Override
    public void instantiateNs(InstantiateNsRequest request) throws NotExistingEntityException, MalformattedElementException {
        vsSBI.instantiateNs(request);
    }

    @Override
    public void modifyNs(ModifyNsRequest request) throws NotExistingEntityException {
        vsSBI.modifyNs(request);
    }

    @Override
    public void terminateNs(TerminateNsRequest request) throws Exception {
        vsSBI.terminateNs(request);
    }
}
