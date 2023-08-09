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
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.configuration.RemoveConfigRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.configuration.SetConfigRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.configuration.UpdateConfigRequest;

/**
 * Interface used to model the configuration API exposed by NSSMF LCM NBI
 * @author Pietro G. Giardina
 */

public interface NssmfLcmConfigInterface {

    /**
     * Method to set a new NSS Configuration
     * @param request message detailing the new configuration to be set
     * @throws MethodNotImplementedException
     * @throws FailedOperationException
     * @throws MalformattedElementException
     * @throws NotPermittedOperationException
     * @throws NotExistingEntityException
     * @throws AlreadyExistingEntityException
     */
    public void setNetworkSubSliceConfiguration(SetConfigRequest request)
            throws MethodNotImplementedException, FailedOperationException, MalformattedElementException,
            NotPermittedOperationException, NotExistingEntityException, AlreadyExistingEntityException;

    /**
     * Method to modify an existing NSS Configuration
     * @param request message detailing the updates for an existing configuration
     * @throws MethodNotImplementedException
     * @throws FailedOperationException
     * @throws MalformattedElementException
     * @throws NotPermittedOperationException
     * @throws NotExistingEntityException
     */
    public void updateNetworkSubSliceConfiguration(UpdateConfigRequest request)
            throws MethodNotImplementedException, FailedOperationException, MalformattedElementException,
            NotPermittedOperationException, NotExistingEntityException;

    /**
     * Method to remove an existing NSS Configuration
     * @param request message specifying the configuration to be removed
     * @throws MethodNotImplementedException
     * @throws FailedOperationException
     * @throws MalformattedElementException
     * @throws NotPermittedOperationException
     * @throws NotExistingEntityException
     */
    public void removeNetworkSubSliceConfiguration(RemoveConfigRequest request)
            throws MethodNotImplementedException, FailedOperationException, MalformattedElementException,
            NotPermittedOperationException, NotExistingEntityException;

    //TODO create get config once the reply message is defined
}   
