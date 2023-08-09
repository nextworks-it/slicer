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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces;

import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.CreateNsIdentifierRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.HealNsRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.InstantiateNsRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.QueryNsResponse;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.ScaleNsRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.TerminateNsRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.UpdateNsRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.UpdateNsResponse;


/**
 * This interface allows to invoke NS lifecycle management 
 * operations towards the NFVO.
 * 
 * The following operations are defined for this interface:
 * 		Create NS Identifier;
 * 		Instantiate NS;
 * 		Scale NS;
 * 		Update NS;
 * 		Query NS;
 * 		Terminate NS;
 * 		Delete NS Identifier;
 * 		Heal NS;
 * 		Get Operation Status.
 * 
 * An identifier (i.e. lifecycleOperationOccurrenceId) is generated for each 
 * NS lifecycle operation occurrence, except for Query NS, Create NS, Delete NS 
 * and Get operation status.
 *  
 * It must be implemented by the core of the NFVO and invoked by the
 * plugins who manage the NFVO external interface (e.g. a REST Controller)
 * 
 * REF IFA 013 v2.3.1 - 7.3
 * 
 * @author nextworks
 *
 */
public interface NsLcmProviderInterface {

	/**
	 * Method to create an NS instance identifier, and an associated instance of an NsInfo information element, 
	 * identified by that identifier, in the NOT_INSTANTIATED state without instantiating the NS 
	 * or doing any additional lifecycle operation(s). 
	 * It allows the immediate return of an NS instance identifier that can be used in subsequent lifecycle
	 * operations, such as the Instantiate NS operation.
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.2
	 *
	 * @param request request to create a new NS identifier
	 * @return the result of the NS ID creation
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the NSD does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String createNsIdentifier(CreateNsIdentifierRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * Method to instantiate an NS. 
	 * This operation can only be used with an NS instance in the NOT_INSTANTIATED state.
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.3
     *
	 * @param request request to instantiate a new NS
	 * @return The identifier of the NS lifecycle operation occurrence.
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the NS instance ID does not exist or if the referred nested NS IDs or VNF instance IDs do not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String instantiateNs(InstantiateNsRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * Method to scale an NS instance.
	 * 
	 * This operation will scale an NS instance. 
	 * Scaling an NS instance can be performed by explicitly adding/removing existing VNF instances to/from the NS instance, 
	 * by leveraging on the abstraction mechanism provided by the NS scaling aspects 
	 * and NS levels information elements declared in the NSD or by scaling individual VNF instances that are
	 * part of the NS itself. 
	 * When adding VNFs and nested NSs - already existing or not - to the NS to be scaled, the NFVO
	 * shall follow the indications provided by the dependencies attribute, as specified in the corresponding NSD.
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.4
	 * 
	 * @param request request to scale a NS instance
	 * @return The identifier of the NS lifecycle operation occurrence.
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the NS instance does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String scaleNs(ScaleNsRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation updates an NS instance.
	 * Actions that can be performed with an update include:
	 * 		Adding existing VNF instances to the NS instance.
	 * 		Removing VNF instances from the NS instance.
	 * 		Instantiating new VNF instances and adding them to the NS instance.
	 * 		Changing the DF of VNF instances belonging to the NS instance.
	 * 		Changing the operational state of a VNF instance belonging to the NS instance.
	 * 		Modifying information data and/or the configurable properties of a VNF instance belonging to the NS instance.
	 * 		Changing the external connectivity of a VNF instance belonging to the NS instance.
	 * 		Adding SAPs to the NS instance.
	 * 		Removing SAPs from the NS instance.
	 * 		Adding existing NS instances to the NS instance.
	 * 		Removing nested NS instances from the NS instance.
	 * 		Associate a new NSD version to the NS instance.
	 * 		Moving VNF instances from one NS instance to another NS instance.
	 * 		Adding VNFFGs to the NS instance.
	 * 		Removing VNFFGs from the NS instance.
	 * 		Update VNFFGs of the NS instance.
	 * 		Changing the DF of the NS instance.
	 * Only one type of update shall be allowed per operation.
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.5
	 * 
	 * @param request request to update a NS instance
	 * @return Update NS response with operation ID and IDs of updated entities
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the NS instance does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public UpdateNsResponse updateNs(UpdateNsRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will enable the OSS/BSS to query from the NFVO information on one or more NS(s). 
	 * The operation also supports querying information about VNF instance(s) that is (are) part of an NS.
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.6
	 * 
	 * @param request query
	 * @return query response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the NS instance does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryNsResponse queryNs(GeneralizedQueryRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;

	/**
	 * This operation will terminate an NS.
	 * This operation can only be used with an NS instance in the INSTANTIATED state.
	 * Terminating an NS instance does not delete the NS instance identifier, and the associated instance of the NsInfo
	 * information element, but rather transitions the NS into the NOT_INSTANTIATED state.
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.7
	 * 
	 * @param request request to terminate an existing NS instance
	 * @return response The identifier of the NS lifecycle operation occurrence.
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the NS instance does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String terminateNs(TerminateNsRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation deletes an NS instance identifier and 
	 * the associated NsInfo information element which is in the
	 * NOT_INSTANTIATED state.
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.8
	 * 
	 * @param nsInstanceIdentifier ID of the NS instance to be deleted
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the NS instance ID does not exist
	 * @throws FailedOperationException if the operation fails
	 */
	public void deleteNsIdentifier(String nsInstanceIdentifier) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * This operation supports the healing of an NS instance, 
	 * either by healing the complete NS instance or by healing one of
	 * more of the VNF instances that are part of this NS.
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.9
	 * 
	 * @param request request to heal a NS instance
	 * @return operation ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the NS instance does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String healNs(HealNsRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation provides the status of an NS lifecycle management operation.
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.10
	 * 
	 * @param operationId Identifier of the NS lifecycle operation occurrence.
	 * @return status of the operation
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the NS instance does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public OperationStatus getOperationStatus(String operationId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;

	/**
	 * Method to to subscribe with a filter for the notifications sent by the NFVO which are related
	 * to NS lifecycle changes, as well as to the creation/deletion of NS instance identifiers and 
	 * the associated NsInfo information element instances.
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.11
	 * 
	 * @param request subscription request
	 * @param consumer subscriber
	 * @return the subscription ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws FailedOperationException if the subscription fails
	 */
	public String subscribeNsLcmEvents(SubscribeRequest request, NsLcmConsumerInterface consumer) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	/**
	 * Method to remove a previous subscription
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.13
	 * 
	 * @param subscriptionId	ID of the subscription to be removed
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the subscription does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public void unsubscribeNsLcmEvents(String subscriptionId) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * This operation enables the OSS/BSS to query information about subscriptions.
	 * TODO: still to be defined the format of the request
	 * 
	 * REF IFA 013 v2.3.1 - 7.3.14
	 * 
	 * @param request subscription query
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void queryNsSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
}
