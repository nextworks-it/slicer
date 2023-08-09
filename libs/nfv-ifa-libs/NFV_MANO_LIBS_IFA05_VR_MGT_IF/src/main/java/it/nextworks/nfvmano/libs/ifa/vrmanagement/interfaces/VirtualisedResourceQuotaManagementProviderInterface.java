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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.CreateComputeResourceQuotaRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.CreateComputeResourceQuotaResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.CreateNetworkResourceQuotaRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.CreateNetworkResourceQuotaResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.CreateStorageResourceQuotaRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.CreateStorageResourceQuotaResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.QueryComputeResourceQuotaResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.QueryNetworkResourceQuotaResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.QueryStorageResourceQuotaResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.TerminateResourceQuotaRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.TerminateResourceQuotaResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.UpdateComputeResourceQuotaRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.UpdateComputeResourceQuotaResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.UpdateNetworkResourceQuotaRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.UpdateNetworkResourceQuotaResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.UpdateStorageResourceQuotaRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas.UpdateStorageResourceQuotaResponse;



/**
 * This interface allows to perform management operations on quotas for virtual resources.
 * 
 * It must be implemented by a VIM plugin and invoked by the
 * NFVO core components.
 * 
 * REF IFA 005 v2.3.1 - 7.9.1 - compute resources
 * REF IFA 005 v2.3.1 - 7.9.2 - network resources
 * REF IFA 005 v2.3.1 - 7.9.3 - storage resources
 * REF IFA 005 v2.3.1 - 7.9.4 - quotas change notification
 * 
 * @author nextworks
 *
 */
public interface VirtualisedResourceQuotaManagementProviderInterface {

	/**
	 * This operation allows requesting the quota of virtualised compute resources.
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.1.2
	 * 
	 * @param request request to create a compute resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public CreateComputeResourceQuotaResponse createComputeResourceQuota(CreateComputeResourceQuotaRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation allows querying quota information about compute resources that the consumer has access to.
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.1.3
	 * 
	 * @param request query for a compute resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the quota does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryComputeResourceQuotaResponse queryComputeResourceQuota(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows updating compute resource quotas (e.g. increase or decrease the amount of quota resources).
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.1.4
	 * 
	 * @param request request to update a compute resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the quota does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public UpdateComputeResourceQuotaResponse updateComputeResourceQuota(UpdateComputeResourceQuotaRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows terminating one or more issued compute resource quota(s). 
	 * When the operation is done on multiple ids, it is assumed to be best-effort, 
	 * i.e. it can succeed for a subset of the ids, and fail for the remaining ones.
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.1.5
	 * 
	 * @param request request to terminate a compute resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the quota does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public TerminateResourceQuotaResponse terminateComputeResourceQuota(TerminateResourceQuotaRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	
	/**
	 * This operation allows requesting the quota of virtualised network resources.
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.2.2
	 * 
	 * @param request request to create a network resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public CreateNetworkResourceQuotaResponse createNetworkResourceQuota(CreateNetworkResourceQuotaRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation allows querying quota information about network resources that the consumer has access to.
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.2.3
	 * 
	 * @param request query for a network resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the quota does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryNetworkResourceQuotaResponse queryNetworkResourceQuota(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows updating network resource quotas (e.g. increase or decrease the amount of quota resources).
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.2.4
	 * 
	 * @param request request to update a network resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the quota does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public UpdateNetworkResourceQuotaResponse updateNetworkResourceQuota(UpdateNetworkResourceQuotaRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows terminating one or more issued network resource quota(s). 
	 * When the operation is done on multiple ids, it is assumed to be best-effort, 
	 * i.e. it can succeed for a subset of the ids, and fail for the remaining ones.
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.2.5
	 * 
	 * @param request request to terminate a network resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the quota does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public TerminateResourceQuotaResponse terminateNetworkResourceQuota(TerminateResourceQuotaRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows requesting the quota of virtualised storage resources.
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.3.2
	 * 
	 * @param request request to create a storage resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public CreateStorageResourceQuotaResponse createStorageResourceQuota(CreateStorageResourceQuotaRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation allows querying quota information about storage resources that the consumer has access to.
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.3.3
	 * 
	 * @param request query for a storage resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the quota does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryStorageResourceQuotaResponse queryStorageResourceQuota(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows updating storage resource quotas (e.g. increase or decrease the amount of quota resources).
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.3.4
	 * 
	 * @param request request to update a storage resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the quota does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public UpdateStorageResourceQuotaResponse updateStorageResourceQuota(UpdateStorageResourceQuotaRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows terminating one or more issued storage resource quota(s). 
	 * When the operation is done on multiple ids, it is assumed to be best-effort, 
	 * i.e. it can succeed for a subset of the ids, and fail for the remaining ones.
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.3.5
	 * 
	 * @param request request to terminate a storage resource quota
	 * @return the response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the quota does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public TerminateResourceQuotaResponse terminateStorageResourceQuota(TerminateResourceQuotaRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation enables the NFVO to subscribe with a filter for 
	 * the notifications related to quota on virtualised resources sent by the VIM.
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.4.2
	 * 
	 * @param request subscription request
	 * @param consumer consumer that will receive the notification
	 * @return the subscription ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws FailedOperationException if the operation fails
	 */
	public String subscribeVirtualResourceQuotaChange(SubscribeRequest request, VirtualisedResourceQuotaManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	/**
	 * Method to remove a previous subscription
	 * 
	 * REF IFA 005 v2.3.1 - 7.9.4.2
	 * 
	 * @param subscriptionId	ID of the subscription to be removed
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the subscription does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public void unsubscribeVirtualResourceQuotaChange(String subscriptionId) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
}

