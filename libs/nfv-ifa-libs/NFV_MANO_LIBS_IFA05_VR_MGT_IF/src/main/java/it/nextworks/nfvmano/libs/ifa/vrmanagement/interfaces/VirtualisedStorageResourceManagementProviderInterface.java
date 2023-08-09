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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet.TerminateNetworkRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vresources.CreateResourceAffinityGroupRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.AllocateStorageResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.MigrateStorageRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.MigrateStorageResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.OperateStorageRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.OperateStorageResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.QueryVirtualStorageResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.ScaleStorageRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.ScaleStorageResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.TerminateStorageResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.UpdateStorageRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage.UpdateStorageResponse;

/**
 * This interface allows an authorized consumer functional block 
 * to perform operations on virtualised storage resources available 
 * to the consumer functional block. 
 * 
 * The interface includes operations for allocating, querying, updating and
 * terminating virtualised storage resources as well as operations for scaling, 
 * migrating and operating the administrative status of a virtualised resource.
 * 
 * This interface implements the provider side; 
 * it must be implemented by a VIM plugin and invoked by 
 * the NFVO (IFA 005) or the VNFM (IFA 006).
 * 
 * REF IFA 005 v2.3.1 - sect. 7.5.1-2
 * 
 * @author nextworks
 *
 */
public interface VirtualisedStorageResourceManagementProviderInterface {

	/**
	 * This operation allows requesting the allocation of 
	 * virtualised storage resources as indicated by the 
	 * consumer functional block.
	 * 
	 * REF IFA 005 v2.3.1 - sect. 7.5.1.2
	 * 
	 * @param request request to allocate a virtual storage resource
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public AllocateStorageResponse allocateVirtualisedStorageResource(AllocateNetworkRequest request)
	throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation allows querying information about instantiated 
	 * virtualised storage resources.
	 * 
	 * REF IFA 005 v2.3.1 - sect. 7.5.1.3
	 * 
	 * @param request query for a virtual storage resource
	 * @return the queried virtual storage resources
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the requested resource does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryVirtualStorageResponse queryVirtualisedStorageResource(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows updating the configuration and/or parameters 
	 * of an instantiated virtualised storage resource, including updating 
	 * its metadata. 
	 * 
	 * REF IFA 005 v2.3.1 - sect. 7.5.1.4
	 * 
	 * @param request request to update a virtual compute resource
	 * @return response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the resource to be updated does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public UpdateStorageResponse updateVirtualisedStorageResource(UpdateStorageRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows de-allocating and terminating one or more 
	 * instantiated virtualised storage resource(s). 
	 * When the operation is done on multiple ids, it is assumed to be 
	 * best-effort, i.e. it can succeed for a subset of the ids, and fail for
	 * the remaining ones.
	 * 
	 * REF IFA 005 v2.3.1 - sect. 7.5.1.5
	 * 
	 * @param request request to terminate a virtual storage resource
	 * @return response 
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the resource to be deleted does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public TerminateStorageResponse terminateVirtualisedStorageResource(TerminateNetworkRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows executing specific operation command on instantiated virtualised storage resources.
	 * 
	 * REF IFA 005 v2.3.1 - sect. 7.5.1.6
	 * 
	 * @param request request to execute an operation command on a storage resource
	 * @return response 
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the resource to be deleted does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public OperateStorageResponse operateVirtualisedStorageResource(OperateStorageRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows resizing an instantiated virtualised storage resource.
	 * 
	 * REF IFA 005 v2.3.1 - sect. 7.5.1.7
	 * 
	 * @param request request to scale a storage resource
	 * @return response 
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the resource to be deleted does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public ScaleStorageResponse scaleVirtualisedStorageResource(ScaleStorageRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows migrating instantiated virtualised storage resources 
	 * from one storage location to another. For instance, the operation performs 
	 * the migration of a volume resource from one physical machine (host) to another
	 * physical machine.
	 * 
	 * REF IFA 005 v2.3.1 - sect. 7.5.1.8
	 * 
	 * @param request request to migrate a storage resource
	 * @return response 
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the resource to be deleted does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public MigrateStorageResponse migrateVirtualisedStorageResource(MigrateStorageRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows an authorized consumer functional block to request 
	 * the creation of a resource affinity or anti-affinity constraints group. 
	 * An anti-affinity group contains resources that are not placed in proximity, 
	 * e.g. that do not share the same physical storage node. 
	 * An affinity group contains resources that are placed in proximity, 
	 * e.g. that do share the same physical storage node.
	 * 
	 * REF IFA 005 v2.3.1 - sect. 7.5.1.9
	 * 
	 * @param request request to create an affinity group for virtual storage resource
	 * @return the ID of the affinity group
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String createStorageResourceAffinityGroup(CreateResourceAffinityGroupRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the NFVO to subscribe with a filter for the notifications 
	 * related to virtualised storage resource changes sent by the VIM.
	 * 
	 * REF IFA 005 v2.3.1 - 7.5.2.2
	 * 
	 * @param request subscription request
	 * @param consumer subscriber
	 * @return the subscription ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws FailedOperationException if the subscription fails
	 */
	public String subscribeVirtualStorageResourceChange(SubscribeRequest request, VirtualisedStorageResourceManagementConsumerInterface consumer) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	/**
	 * Method to remove a previous subscription
	 * 
	 * REF IFA 005 v2.3.1 - 7.5.2.2
	 * 
	 * @param subscriptionId	ID of the subscription to be removed
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the subscription does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public void unsubscribeVirtualStorageResourceChange(String subscriptionId) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
}
