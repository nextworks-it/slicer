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

package it.nextworks.nfvmano.sebastian.nsmf.interfaces;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import java.util.List;


public interface NsManagementInterface {

    /**
     * This method initializes a new NS LCM manager that will be in charge
     * of processing all the requests and events for that NSI.
     *
     * @param nsiId ID of the network slice instance for which the NS LCM Manager must be initialized
     */
    void initNewNsLcmManager(String nsiId, String tenantId, String sliceName, String sliceDescription);

    /**
     * This method starts the procedures to instantiate a NSI, sending a message to
     * the related NS LCM Manager
     *
     * @param nsiId ID of the NS instance to be instantiated
     * @param tenantId tenant owning the NS instance
     * @param nsdId NSD ID of the NFV NS that implements the NS instance
     * @param nsdVersion NSD version of the NFV NS that implements the NS instance
     * @param dfId DF ID of the NFV NS that implements the NS instance
     * @param instantiationLevelId instantiation level ID of the NFV NS that implements the NS instance
     * @param vsiId ID of the Vertical Service instance associated to the network slice, if available
     * @throws NotExistingEntityException if the NS LCM manager is not found
     */
    void instantiateNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId,
                       String instantiationLevelId, String vsiId, List<String> nsSubnetIds)
            throws NotExistingEntityException;

    /**
     * This method starts the procedures to modify a NSI, sending a message to
     * the related NS LCM Manager
     *
     * @param nsiId ID of the NS instance to be instantiated
     * @param tenantId tenant owning the NS instance
     * @param nsdId NSD ID of the NFV NS that implements the NS instance
     * @param nsdVersion NSD version of the NFV NS that implements the NS instance
     * @param dfId DF ID of the NFV NS that implements the NS instance
     * @param instantiationLevelId instantiation level ID of the NFV NS that implements the NS instance
     * @param vsiId ID of the Vertical Service instance associated to the network slice, if available
     * @throws NotExistingEntityException if the NS LCM manager is not found
     */
    void modifyNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String vsiId)
            throws NotExistingEntityException;

    /**
     * This method starts the procedures to terminate a NSI, sending a message to
     * the related NS LCM Manager
     *
     * @param nsiId ID of the NS instance to be terminated
     * @throws Exception
     */
    void terminateNs(String nsiId) throws Exception;
}
