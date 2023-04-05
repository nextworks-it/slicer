/*
 * Copyright (c) 2022 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.nssmf.service.messages.provisioning;

import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.nssmf.service.messages.BaseMessage;
import it.nextworks.nfvmano.nssmf.service.messages.NssmfMessageType;

public class ModifyNssiRequestMessage extends BaseMessage {

    private NssmfBaseProvisioningMessage modifyNssiRequest;
    public ModifyNssiRequestMessage() {
        super.setMessageType(NssmfMessageType.MODIFY_NSSI_REQUEST);
    }

    public NssmfBaseProvisioningMessage getModifyNssiRequest() {
        return modifyNssiRequest;
    }

    public void setModifyNssiRequest(NssmfBaseProvisioningMessage modifyNssiRequest) {
        this.modifyNssiRequest = modifyNssiRequest;
    }

}
