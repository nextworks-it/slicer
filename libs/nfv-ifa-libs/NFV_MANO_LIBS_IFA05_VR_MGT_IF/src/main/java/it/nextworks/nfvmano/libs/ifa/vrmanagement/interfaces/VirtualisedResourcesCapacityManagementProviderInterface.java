/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vresources.NfviPopInformationResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vresources.QueryResourceCapacityRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vresources.QueryResourceCapacityResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vresources.QueryResourceZoneResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vresources.SubscribeResourceCapacityNotificationsRequest;


/**
 * This interface allows an authorized consumer functional block 
 * to request operations related to capacity and usage reporting. 
 * The interface allows retrieval of information about:
 * 
 * 1. The available, allocated, reserved and total capacity of the 
 * compute resources managed by a VIM instance, globally or per resource zone.
 * 
 * 2. Utilization of the capacity, both on VIM global level but also per resource zone.
 * 
 * 3. The geographical location and network connectivity endpoints 
 * (e.g. network gateway) to the NFVI-PoP(s) administered by the VIM.
 * 
 * It must be implemented by a VIM plugin and invoked by the
 * NFVO core components. 
 * 
 * Note: this interface can be also used for network resources: REF IFA 005 v2.3.1 - 7.4.4
 * 
 * REF IFA 005 v2.3.1 - 7.3.4
 * 
 * 
 * @author nextworks
 *
 */
public interface VirtualisedResourcesCapacityManagementProviderInterface {

	/**
	 * This operation supports retrieval of capacity information 
	 * for the various types of consumable virtualised compute or network or storage
	 * resources available in the Virtualised Compute or Network Resources 
	 * Information Management Interface.
	 * 
	 * REF IFA 005 v2.3.1 - 7.3.4.2 - 7.4.4.2 - 7.5.4.2
	 * 
	 * @param request request
	 * @return the queried compute capacity
	 * @throws NotExistingEntityException if the entity described in the filter does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public QueryResourceCapacityResponse queryResourceCapacity(QueryResourceCapacityRequest request) 
			throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException;
	
	/**
	 * This operation supports subscribing to compute capacity change notifications.
	 * 
	 * REF IFA 005 v2.3.1 - 7.3.4.3 - 7.4.4.3 - 7.5.4.3
	 * 
	 * @param request subscription request
	 * @param consumer consumer of the notification
	 * @return the subscription ID
	 * @throws NotExistingEntityException if the entity described in the filter does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public String subscribeResourceCapacityNotification(SubscribeResourceCapacityNotificationsRequest request, VirtualisedResourcesCapacityManagementConsumerInterface consumer)
			throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException;
	
	/**
	 * This operation removes an existing subscription
	 * 
	 * @param subscriptionId ID of the subscription to be removed
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public void unsubscribeResourceCapacityNotification(String subscriptionId) 
			throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException;
	
	/**
	 * This operation enables the NFVO to query information about a Resource Zone, 
	 * e.g. listing the properties of the Resource Zone, and other metadata.
	 * 
	 * REF IFA 005 v2.3.1 - 7.3.4.5 - 7.5.4.6
	 * 
	 * @param request request
	 * @return the requested information
	 * @throws NotExistingEntityException if the entity described in the filter does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public QueryResourceZoneResponse queryResourceZone(GeneralizedQueryRequest request)
			throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException;
	
	/**
	 * This operation enables the NFVOs to query general information 
	 * to the VIM concerning the geographical location and network 
	 * connectivity endpoints to the NFVI-PoP(s) administered by the VIM, 
	 * and to determine network endpoints to reach VNFs instantiated making 
	 * use of virtualised compute resources in the NFVI as specified by the 
	 * exchanged information elements.
	 * 
	 * REF IFA 005 v2.3.1 - 7.3.4.6
	 * REF IFA 005 v2.3.1 - 7.4.4.5
	 * REF IFA 005 v2.3.1 - 7.5.4.5
	 * 
	 * @param request request
	 * @return the requested information
	 * @throws NotExistingEntityException if the entity described in the filter does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public NfviPopInformationResponse queryNfviPopInformation(GeneralizedQueryRequest request)
			throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException;
}
