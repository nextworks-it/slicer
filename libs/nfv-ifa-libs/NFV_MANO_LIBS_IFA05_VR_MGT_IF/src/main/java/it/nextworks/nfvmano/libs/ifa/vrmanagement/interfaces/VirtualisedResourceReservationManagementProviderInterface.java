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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.CreateComputeResourceReservationRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.CreateComputeResourceReservationResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.CreateNetworkResourceReservationRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.CreateNetworkResourceReservationResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.CreateStorageResourceReservationRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.CreateStorageResourceReservationResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.QueryComputeResourceReservationResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.QueryNetworkResourceReservationResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.QueryStorageResourceReservationResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.TerminateResourceReservationRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.TerminateResourceReservationResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.UpdateComputeResourceReservationRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.UpdateComputeResourceReservationResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.UpdateNetworkResourceReservationRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.UpdateNetworkResourceReservationResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.UpdateStorageResourceReservationRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation.UpdateStorageResourceReservationResponse;



/**
 * This interface allows an authorized consumer functional block 
 * to perform operations on virtualised compute, storage and network resources reservations
 * available to the consumer functional block. 
 * The interface includes operations for creating, querying, updating and terminating 
 * reservations on virtualised compute, storage and network resources.
 * 
 * It must be implemented by a VIM plugin and invoked by the
 * NFVO core components.
 * 
 * REF IFA 005 v2.3.1 - 7.8.1 - compute resources
 * REF IFA 005 v2.3.1 - 7.8.2 - network resources
 * REF IFA 005 v2.3.1 - 7.8.3 - storage resources
 * REF IFA 005 v2.3.1 - 7.8.4 - reservation change notification
 * 
 * @author nextworks
 *
 */
public interface VirtualisedResourceReservationManagementProviderInterface {

	/**
	 * This operation allows requesting the reservation of virtualised compute 
	 * resources as indicated by the consumer functional block.
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.1.2
	 * 
	 * @param request reservation request
	 * @return reservation response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public CreateComputeResourceReservationResponse createComputeResourceReservation(CreateComputeResourceReservationRequest request)
		throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation allows querying information about reserved compute resources that the consumer has access to.
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.1.3
	 * 
	 * @param request query request
	 * @return reservation details
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the reservation is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryComputeResourceReservationResponse queryComputeResourceReservation(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows updating compute resource reservations (e.g. increase or decrease 
	 * the amount of reserved resources).
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.1.4
	 * 
	 * @param request update request
	 * @return update response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the reservation is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public UpdateComputeResourceReservationResponse updateComputeResourceReservation(UpdateComputeResourceReservationRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows terminating one or more issued compute resource reservation(s). 
	 * When the operation is done on multiple ids, it is assumed to be best-effort, 
	 * i.e. it can succeed for a subset of the ids, and fail for the remaining ones.
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.1.5
	 * 
	 * @param request termination request
	 * @return termination response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the reservation is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public TerminateResourceReservationResponse terminateComputeResourceReservation(TerminateResourceReservationRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows requesting the reservation of virtualised network resources 
	 * as indicated by the consumer functional block.
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.2.2
	 * 
	 * @param request reservation request
	 * @return reservation response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public CreateNetworkResourceReservationResponse createNetworkResourceReservation(CreateNetworkResourceReservationRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation allows querying information about reserved network resources that the consumer has access to.
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.2.3
	 * 
	 * @param request query request
	 * @return reservation details
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the reservation is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryNetworkResourceReservationResponse queryNetworkResourceReservation(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows updating network resource reservations (e.g. increase or decrease 
	 * the amount of reserved resources).
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.2.4
	 * 
	 * @param request update request
	 * @return update response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the reservation is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public UpdateNetworkResourceReservationResponse updateNetworkResourceReservation(UpdateNetworkResourceReservationRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows terminating one or more issued network resource reservation(s). 
	 * When the operation is done on multiple ids, it is assumed to be best-effort, 
	 * i.e. it can succeed for a subset of the ids, and fail for the remaining ones.
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.2.5
	 * 
	 * @param request termination request
	 * @return termination response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the reservation is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public TerminateResourceReservationResponse terminateNetworkResourceReservation(TerminateResourceReservationRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows requesting the reservation of virtualised storage resources 
	 * as indicated by the consumer functional block.
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.3.2
	 * 
	 * @param request request to create a reservation for a storage resource
	 * @return the creation response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public CreateStorageResourceReservationResponse createStorageResourceReservation(CreateStorageResourceReservationRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation allows querying information about reserved storage resources that the consumer has access to.
	 * 
	 *  REF IFA 005 v2.3.1 - 7.8.3.3
	 * 
	 * @param request query request
	 * @return response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the reserved resource does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryStorageResourceReservationResponse queryStorageResourceReservation(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows updating storage resource reservations (e.g. increase or decrease the amount of reserved resources).
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.3.4
	 * 
	 * @param request update request
	 * @return response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the reservation to be updated does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public UpdateStorageResourceReservationResponse updateStorageResourceReservation(UpdateStorageResourceReservationRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows terminating one or more issued storage resource reservation(s). 
	 * When the operation is done on multiple ids, it is assumed to be best-effort, 
	 * i.e. it can succeed for a subset of the ids, and fail for the remaining ones.
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.3.5
	 * 
	 * @param request termination request
	 * @return termination response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the reservation is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public TerminateResourceReservationResponse terminateStorageResourceReservation(TerminateResourceReservationRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation enables the NFVO to subscribe with a filter for the notifications
	 * related to reservation on virtualised resources sent by the VIM
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.4.2
	 * 
	 * @param request subscription request
	 * @return subscription ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String subscribeVirtualResourceChangeNotification(SubscribeRequest request)
		throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation removes a previous subscription.
	 * 
	 * REF IFA 005 v2.3.1 - 7.8.4.2
	 * 
	 * @param subscriptionId ID of the subscription to be removed
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void unsubscribeVirtualResourceChangeNotification(String subscriptionId)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
}
