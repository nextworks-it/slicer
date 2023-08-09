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
package it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces;



import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.ChangeExternalVnfConnectivityRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.ChangeVnfFlavourRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.CreateVnfIdentifierRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.HealVnfRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.InstantiateVnfRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.ModifyVnfInformationRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.OperateVnfRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.QueryVnfResponse;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.ScaleVnfRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.ScaleVnfToLevelRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages.TerminateVnfRequest;


/**
 * This interface allows the NFVO to invoke VNF lifecycle management 
 * operations towards the VNFM.
 * 
 * It must be implemented by a VNFM and invoked by the
 * NFVO core components to request virtual network related operations.
 * 
 * 
 * The following operations are defined:
 * • Create VNF Identifier.
 * • Instantiate VNF.
 * • Scale VNF.
 * • Scale VNF to Level.
 * • Change VNF Flavour.
 * • Terminate VNF.
 * • Delete VNF Identifier.
 * • Query VNF.
 * • Heal VNF.
 * • Operate VNF.
 * • Modify VNF Information.
 * • Get Operation Status.
 * • Change External VNF connectivity.
 * 
 * REF IFA 007 v2.3.1 - 7.2
 * 
 * @author nextworks
 *
 */
public interface VnfLcmProviderInterface {

	/**
	 * This operation creates a VNF instance identifier, 
	 * and an associated instance of a VnfInfo information element,
	 * identified by that identifier, in the NOT_INSTANTIATED state 
	 * without instantiating the VNF or doing any additional lifecycle 
	 * operation(s). It allows returning right away a VNF instance 
	 * identifier that can be used in subsequent lifecycle operations, 
	 * like the Instantiate VNF operation.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.2
	 * 
	 * @param request request
	 * @return VNF instance identifier just created
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if the VNFD is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String createVnfIdentifier(CreateVnfIdentifierRequest request) 
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation instantiates a particular DF of a 
	 * VNF based on the definition in the VNFD.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.3
	 * 
	 * @param request request
	 * @return the ID of the operation
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String instantiateVnf(InstantiateVnfRequest request)
		throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation provides methods to request the scaling of a VNF.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.4
	 * 
	 * @param request request
	 * @return the ID of the operation
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String scaleVnf(ScaleVnfRequest request)
		throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation scales an instantiated VNF of a particular DF to a target size. 
	 * The target size is either expressed as an instantiation level of that DF as defined in the VNFD, 
	 * or given as a list of scale levels, one per scaling aspect of that DF.
	 * Instantiation levels and scaling aspects are declared in the VNFD. 
	 * Typically, the result of this operation is adding and/or removing 
	 * Network Functions Virtualization Infrastructure (NFVI) resources to/from the VNF.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.5
	 * 
	 * @param request request
	 * @return the ID of the operation
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String scaleVnfToLevel(ScaleVnfToLevelRequest request)
		throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation changes the DF of a VNF instance.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.6
	 * 
	 * @param request request
	 * @return the ID of the operation
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String changeVnfFlavour(ChangeVnfFlavourRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation terminates a VNF instance.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.7
	 * 
	 * @param request request
	 * @return the ID of the operation
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String terminateVnf(TerminateVnfRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation deletes a VNF instance identifier and the associated instance 
	 * of a VnfInfo information element in the NOT_INSTANTIATED state.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.8
	 * 
	 * @param vnfInstanceId VNF instance ID to be deleted
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void deleteVnfIdentifier(String vnfInstanceId) 
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation provides information about VNF instances. 
	 * The applicable VNF instances can be chosen based on
	 * filtering criteria, and the information can be restricted 
	 * to selected attributes.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.9
	 * 
	 * @param request request
	 * @return the VNF information
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryVnfResponse queryVnf(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation heals a VNF instance.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.10
	 * 
	 * @param request request
	 * @return the ID of the operation
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String healVnf(HealVnfRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation enables requesting to change the state of a VNF instance, 
	 * including starting and stopping the VNF instance.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.11
	 * 
	 * @param request request
	 * @return the ID of the operation
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String operateVnf(OperateVnfRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation allows updating information about a VNF instance.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.12
	 * 
	 * @param request request
	 * @return the ID of the operation
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String modifyVnfInformation(ModifyVnfInformationRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation provides the status of a VNF lifecycle management operation. 
	 * This means, it is not a VNF lifecycle management operation itself, 
	 * but an operation on VNF lifecycle management operations. 
	 * Therefore, this operation shall be supported for all VNFs.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.13
	 * 
	 * @param operationId Identifier of the VNF lifecycle operation occurrence.
	 * @return The operation status
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if the operation is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public OperationStatus getOperationStatus(String operationId)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation enables the NFVO to subscribe with a filter for the notifications sent by the VNFM which are related to
	 * VNF lifecycle management operation occurrences, as well as creation/deletion of VNF instance identifiers and the
	 * associated VnfInfo information element instances.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.14
	 * 
	 * @param request subscription request
	 * @param consumer subscriber
	 * @return the subscription ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws FailedOperationException if the subscription fails
	 */
	public String subscribe(SubscribeRequest request, VnfLcmConsumerInterface consumer) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	/**
	 * Method to remove a previous subscription
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.16
	 * 
	 * @param subscriptionId	ID of the subscription to be removed
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the subscription does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public void unsubscribe(String subscriptionId) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * This operation enables the NFVO to query information about subscriptions.
	 * TODO: still to be defined the format of the request
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.17
	 * 
	 * @param request subscription query
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void queryNsSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables changing the external connectivity of a VNF instance. 
	 * The types of changes that this operation supports are:
	 * • Disconnect the external CPs that are connected to a particular external VL, 
	 * and connect them to a different external VL.
	 * • Change the connectivity parameters of the existing external CPs, 
	 * including changing addresses.
	 * 
	 * REF IFA 007 v2.3.1 - 7.2.18
	 * 
	 * @param request change external VNF connectivity request
	 * @return the operation ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VNF instance is not found
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String changeExternalVnfConnectivity(ChangeExternalVnfConnectivityRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
}
