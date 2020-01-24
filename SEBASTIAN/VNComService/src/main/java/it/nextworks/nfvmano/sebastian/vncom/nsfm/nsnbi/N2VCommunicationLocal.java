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

package it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi;

import it.nextworks.nfvmano.nfvodriver.NsStatusChange;
import it.nextworks.nfvmano.sebastian.vncom.vsfm.vssbi.N2VCommunicationInterface;
import org.springframework.beans.factory.annotation.Autowired;

public class N2VCommunicationLocal extends N2VAbstractCommunication{
    @Autowired
    private N2VCommunicationInterface vsLocalEngine;

    @Override
    public void setVsLocalEngine(N2VCommunicationInterface vsLocalEngine) {
        this.vsLocalEngine = vsLocalEngine;
    }

    @Override
    public void notifyNetworkSliceStatusChange(String networkSliceId, NsStatusChange changeType, boolean successful) {
        vsLocalEngine.notifyNetworkSliceStatusChange(networkSliceId, changeType, successful);
    }
}