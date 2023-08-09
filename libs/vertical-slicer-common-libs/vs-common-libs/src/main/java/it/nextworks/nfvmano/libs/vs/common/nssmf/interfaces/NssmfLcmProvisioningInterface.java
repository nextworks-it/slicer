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

package it.nextworks.nfvmano.libs.vs.common.nssmf.interfaces;

import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;

import java.util.UUID;

/**
 * Interface used to model the provisioning API exposed by NSSMF LCM NBI
 * @author Pietro G. Giardina
 */
public interface NssmfLcmProvisioningInterface {

    /**
     *
     * @return Identifier created for the new nssi
     * @throws MethodNotImplementedException
     * @throws FailedOperationException
     * @throws MalformattedElementException
     * @throws NotPermittedOperationException
     */
    public UUID createNetworkSubSliceIdentifier()
            throws MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException, IllegalAccessException, ClassNotFoundException, InstantiationException;


    /**
     *
     * @param request message detailing the instantiation request
     * @throws NotExistingEntityException
     * @throws MethodNotImplementedException
     * @throws FailedOperationException
     * @throws MalformattedElementException
     * @throws NotPermittedOperationException
     */
    public void instantiateNetworkSubSlice(NssmfBaseProvisioningMessage request)
            throws MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException, AlreadyExistingEntityException, NotExistingEntityException;

    /**
     *
     * @param request message detailing the modification request
     * @throws NotExistingEntityException
     * @throws MethodNotImplementedException
     * @throws FailedOperationException
     * @throws MalformattedElementException
     * @throws NotPermittedOperationException
     */
    public void modifyNetworkSlice(NssmfBaseProvisioningMessage request)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException;

    /**
     *
     * @param request message detailing the termination request
     * @throws NotExistingEntityException
     * @throws MethodNotImplementedException
     * @throws FailedOperationException
     * @throws MalformattedElementException
     * @throws NotPermittedOperationException
     */
    public void terminateNetworkSliceInstance(NssmfBaseProvisioningMessage request)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException;

    //TODO add query once NSSI descriptor is ready
}
