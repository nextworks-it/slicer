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

package it.nextworks.nfvmano.sebastian.nsmf.N2VCommunicationService;

import it.nextworks.nfvmano.nfvodriver.NsStatusChange;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.N2VCommunicationInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class N2VComminicationService implements N2VCommunicationInterface {
    private static final Logger log = LoggerFactory.getLogger(N2VComminicationService.class);

    @Value("${vertical.network.communication}")
    private String comType;

    private N2VAbstractCommunication n2VComm;


    @PostConstruct
    public void initN2VCom(){
        log.debug("Initializing Network to Vertical Communication");
        if (comType.equals("LOCAL")) {
            log.debug("The NSFM is configured to communicate with VSFM using local queues.");
            n2VComm = new N2VCommunicationLocal();
        } else {
            log.error("Arbitrator not configured!");
        }

    }

    @Override
    public void notifyNetworkSliceStatusChange(String networkSliceId, NsStatusChange changeType, boolean successful) {
        n2VComm.notifyNetworkSliceStatusChange(networkSliceId, changeType, successful);
    }
}
