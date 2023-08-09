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
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vcompute.QueryVirtualComputeResourceInfoResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet.QueryVirtualNetworkResourceInfoResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.QueryVirtualStorageResourceInfoResponse;

/**
 * This interface allows an authorized consumer functional block 
 * to request operations related to the information about consumable 
 * virtualised compute resources. 
 * The consumable virtualised compute resources include (not limited to) 
 * virtualised compute (virtualised CPU, virtualised memory), virtualised 
 * storage, virtualised NIC, etc. which are managed by a VIM.
 * 
 * The information elements related to consumable virtualised compute 
 * resources describe the types and characteristics of the virtualised 
 * resources that a consumer functional block can request for allocation 
 * as part of the Virtualised Compute Resource Management interface. 
 * The interface and related parameters also support the retrieval of information
 * necessary for describing the types and characteristics of the virtualised 
 * resources that are exposed over the Virtualised Compute Resource Capacity interface.
 * 
 * It must be implemented by a VIM plugin and invoked by the
 * NFVO core components. 
 * 
 * Note: this interface can be also used for network resources: REF IFA 005 v2.3.1 - 7.4.3
 * 
 * REF IFA 005 v2.3.1 - 7.3.3
 * 
 * 
 * @author nextworks
 *
 */
public interface VirtualisedResourcesInformationManagementProviderInterface {

	/**
	 * This operation enables the NFVOs to subscribe for the notifications 
	 * related to information changes about consumable virtualised compute resources. 
	 * This also enables the NFVO to specify the scope of the subscription in terms of the
	 * specific virtual compute resources to be reported by the VIM using a filter as the input.
	 * 
	 * REF IFA 005 v2.3.1 - 7.3.3.2
	 * REF IFA 005 v2.3.1 - 7.4.3.2
	 * REF IFA 005 v2.3.1 - 7.5.3.2
	 * 
	 * @param request subscription request
	 * @param consumer consumer of the notification
	 * @return the subscription ID
	 * @throws NotExistingEntityException if the entity described in the filter does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String subscribeResourceInformationNotification(SubscribeRequest request, VirtualisedResourcesInformationManagementConsumerInterface consumer)
			throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException, MalformattedElementException;
	
	/**
	 * This operation removes an existing subscription
	 * 
	 * @param subscriptionId ID of the subscription to be removed
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public void unsubscribeResourceInformationNotification(String subscriptionId) 
			throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException;
	
	/**
	 * This operation supports retrieval of information for the various types of 
	 * virtualised compute resources managed by the VIM.
	 * 
	 * REF IFA 005 v2.3.1 - 7.3.3.4
	 * 
	 * @param request request
	 * @return the details of the queried virtual compute resource information
	 * @throws NotExistingEntityException if the resource is not found
	 * @throws FailedOperationException if the operation fails
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public QueryVirtualComputeResourceInfoResponse queryVirtualisedComputeResourceInformation(GeneralizedQueryRequest request)
			throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException;
	
	/**
	 * This operation supports retrieval of information for the various types of 
	 * virtualised network resources managed by the VIM.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.3.4
	 * 
	 * @param request request
	 * @return the details of the queried virtual network resource information
	 * @throws NotExistingEntityException if the resource is not found
	 * @throws FailedOperationException if the operation fails
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public QueryVirtualNetworkResourceInfoResponse queryVirtualisedNetworkResourceInformation(GeneralizedQueryRequest request)
			throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException;
	
	/**
	 * This operation supports retrieval of information for the various types of 
	 * virtualised storage resources managed by the VIM.
	 * 
	 * REF IFA 005 v2.3.1 - 7.5.3.4
	 * 
	 * @param request request
	 * @return the details of the queried virtual storage resource information
	 * @throws NotExistingEntityException if the resource is not found
	 * @throws FailedOperationException if the operation fails
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public QueryVirtualStorageResourceInfoResponse queryVirtualisedStorageResourceInformation(GeneralizedQueryRequest request)
			throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException;
}
