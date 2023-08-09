/*
 * Copyright (c) 2021 Nextworks s.r.l.
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

package it.nextworks.nfvmano.libs.vs.common.nsmf.messages;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.interfaces.InterfaceMessage;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NssiNotifType;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiErrors;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;

import java.util.UUID;

public class NsmfNotificationMessage implements InterfaceMessage {

    private UUID nssiId;
    private NssiNotifType nssiNotifType;
    private NssiStatus nssiStatus;

    public void setNssiError(NssiErrors nssiError) {
        this.nssiError = nssiError;
    }


    public NsmfNotificationMessage(){

    }

    public NsmfNotificationMessage(UUID nssiId, NssiNotifType nssiNotifType, NssiStatus nssiStatus) {
        this.nssiId = nssiId;
        this.nssiNotifType = nssiNotifType;
        this.nssiStatus = nssiStatus;
    }

    public UUID getNssiId() {
        return nssiId;
    }

    public NssiNotifType getNssiNotifType() {
        return nssiNotifType;
    }

    public NssiStatus getNssiStatus() {
        return nssiStatus;
    }

    public NssiErrors getNssiError() {
        return nssiError;
    }

    private NssiErrors nssiError; //In case of NssiStatus.ERROR only

    @Override
    public void isValid() throws MalformattedElementException {
        if (nssiId == null)
            throw new MalformattedElementException("NSSI ID not specified.");
        if (nssiNotifType == null)
            throw new MalformattedElementException("NSSI Notification Type not specified.");
        if (nssiStatus == null)
            throw new MalformattedElementException("NSSI Status not specified.");
        if (nssiStatus == NssiStatus.ERROR && nssiError == null)
            throw new MalformattedElementException("NSSI Error Type not specified.");
    }
}
