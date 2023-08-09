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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet.AllocateNetworkRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet.AllocateNetworkResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet.QueryNetworkResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet.TerminateNetworkRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet.TerminateNetworkResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet.UpdateNetworkRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet.UpdateNetworkResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vresources.CreateResourceAffinityGroupRequest;


/**
 * This interface allows an authorized consumer functional block to perform operations 
 * on virtualised network resources available to the consumer functional block. 
 * The interface includes operations for allocating, querying, updating and 
 * terminating virtualised network resources.
 * 
 * It must be implemented by a VIM plugin and invoked by the
 * NFVO core components to request virtual network related operations. 
 * 
 * REF IFA 005 v2.3.1 - 7.4.1 and 7.4.2
 * 
 * @author nextworks
 *
 */
public interface VirtualisedNetworkResourceManagementProviderInterface {
	
	/**
	 * This operation allows an authorized consumer functional block to request 
	 * the allocation of virtualised network resources as indicated by the 
	 * consumer functional block.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.1.2
	 * 
	 * @param request request to allocate a new network resource on the VIM
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public AllocateNetworkResponse allocateVirtualisedNetworkResource(AllocateNetworkRequest request) 
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation allows querying information about instantiated virtualised network resources.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.1.3
	 * 
	 * @param request query request
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the requested entity does not exist
	 * @throws MalformattedElementException if the query is malformatted
	 */
	public QueryNetworkResponse queryVirtualisedNetworkResource(GeneralizedQueryRequest request) 
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows de-allocating and terminating one or more instantiated virtualised network resource(s). 
	 * When the operation is done on multiple ids, it is assumed to be best-effort, i.e. it can succeed for a subset of the ids, 
	 * and fail for the remaining ones.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.1.5
	 * 
	 * @param request terminate network request
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the entity to be terminated does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public TerminateNetworkResponse terminateVirtualisedNetworkResource(TerminateNetworkRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows updating the information of an instantiated virtualised network resource.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.1.4
	 * 
	 * @param request update network request
	 * @return update network response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the entity to be updated does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public UpdateNetworkResponse updateVirtualisedNetworkResource(UpdateNetworkRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows an authorized consumer functional block to request 
	 * the creation of a resource affinity or anti-affinity constraints group. 
	 * 
	 * An anti-affinity group contains resources that are not placed in proximity, 
	 * e.g. that do not share the same physical networking device. 
	 * An affinity group contains resources that are placed in proximity, e.g. that
	 * do share the same physical networking device.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.1.6
	 * 
	 * @param request request to create a resource affinity group for the network
	 * @return the ID of the group
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String createVirtualisedNetworkResourceAffinityConstraintsGroup(CreateResourceAffinityGroupRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the NFVO to subscribe with a filter for the 
	 * notifications related to network resource changes sent by the VIM.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.2.2
	 * 
	 * @param request subscription request
	 * @param consumer subscriber
	 * @return the subscription ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws FailedOperationException if the subscription fails
	 */
	public String subscribeVirtualNetworkResourceChange(SubscribeRequest request, VirtualComputeResourceManagementConsumerInterface consumer) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	/**
	 * Method to remove a previous subscription
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.2.2
	 * 
	 * @param subscriptionId	ID of the subscription to be removed
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the subscription does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public void unsubscribeVirtualNetworkResourceChange(String subscriptionId) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
}
