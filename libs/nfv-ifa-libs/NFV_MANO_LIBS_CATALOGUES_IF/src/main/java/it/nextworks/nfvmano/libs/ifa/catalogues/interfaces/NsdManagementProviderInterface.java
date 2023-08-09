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
package it.nextworks.nfvmano.libs.ifa.catalogues.interfaces;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.*;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;

/**
 * This interface allows the management of NSDs and associated PNFDs. 
 * Virtual Link Descriptors (VLDs) and VNF Forwarding Graph Descriptors (VNFFGDs) 
 * are considered as part of the NSD and handled along with it.
 * 
 * It must be implemented by the core of the NFVO and invoked by the
 * plugins who manage the NFVO external interface (e.g. a REST Controller)
 * 
 * REF IFA 013 v2.3.1 - 7.2
 * 
 * @author nextworks
 *
 */
public interface NsdManagementProviderInterface {

	/**
	 * Method to on-board an NSD in the NFVO.
	 * Associated descriptors ( VLD and VNFFGD), that are part of the NSD, are on-boarded at the same time.
	 * All descriptors needed by the NSD: VNFD, PNFD and NSD for nested NSs shall be on-boarded before being able to
	 * successfully on-board the NSD.
	 * 
	 * @param request on-board NSD request.
	 * @return Identifier of the on-boarded instance of the NSD.
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws AlreadyExistingEntityException if the NSD already exists.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public String onboardNsd(OnboardNsdRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, FailedOperationException;
	
	/**
	 * Method to enable a previously disabled NSD instance, 
	 * allowing again its use for instantiation of new network
	 * service with this descriptor. 
	 * The "In use/Not in use" sub-state shall not change as a result of the operation.
	 * 
	 * @param request enable NSD request.
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the NSD does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public void enableNsd(EnableNsdRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * Method to disable a previously enabled NSD instance, 
	 * preventing any further use for instantiation of new
	 * network service with this descriptor.
	 * The "In use/Not in use" sub-state shall not change as a result of the operation.
	 * 
	 * @param request disable NSD request
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the NSD does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public void disableNsd(DisableNsdRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * Method to update an already on-boarded NSD, creating a new version of the NSD. 
	 * The operation can also be used to update the userDefinedData of an existing NsdInfo 
	 * information element without creating a new version of the NSD.
	 * 
	 * The previous versions of the NSDs are not modified.
	 * 
	 * It is possible to add (remove) constituent descriptors (i.e. VNFDs, PNFDs, nested NSDs, VLDs, VNFFGDs and Service
	 * Access Point Descriptors (SAPDs)) to (from) an NSD via the Update NSD operation. This is done by changing the
	 * various descriptor references in the new NSD. 
	 * For example, to add VNFDs to an NSD, the OSS/BSS adds corresponding VNFD identifiers to the list of vnfdIds in the new NSD. 
	 * To remove VNFDs, the OSS/BSS simply does not include the vnfdIds (of the VNFDs to be removed) in the new NSD.
	 *
	 * @param request request to update an NSD 
	 * @return the ID of the updated NSD.
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws AlreadyExistingEntityException if the updated NSD is already existing.
	 * @throws NotExistingEntityException if the original NSD does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public String updateNsd(UpdateNsdRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * Method to delete one or more NSD(s). It is possible to delete only a single version of an NSD or all versions.
	 * 
	 * An NSD can only be deleted when there is no instantiated NS using it.
	 * An NSD in the deletion pending state can no longer be enabled, disabled or updated. 
	 * It is not possible to instantiate NS(s) using an NSD in the deletion pending state.
	 * 
	 * @param request request to delete an existing NSD
	 * @return the IDs of the deleted NSDs
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the NSD does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public DeleteNsdResponse deleteNsd(DeleteNsdRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * Method to query the NFVO concerning details of one or more NSDs.
	 * 
	 * @param request query
	 * @return NSD query response
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the NSD does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public QueryNsdResponse queryNsd(GeneralizedQueryRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * Method to subscribe with a filter for the notifications related to changes of NSD sent
	 * by the NFVO.
	 * 
	 * @param request subscription request
	 * @param consumer	subscriber
	 * @return subscription ID
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public String subscribeNsdInfo(SubscribeRequest request, NsdManagementConsumerInterface consumer) 
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	/**
	 * Method to remove a previous subscription
	 * 
	 * @param subscriptionId	ID of the subscription to be removed
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the subscription does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public void unsubscribeNsdInfo(String subscriptionId) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * This operation will on-board a PNF in the NFVO, making it available to be used by NSDs.
	 * 
	 * @param request on-board PNFD request.
	 * @return Identifier of the on-boarded instance of the PNFD.
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws AlreadyExistingEntityException if the PNFD already exists.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public String onboardPnfd(OnboardPnfdRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, FailedOperationException;
	
	/**
	 * Method to update a PNFD, creating a new version of already on-boarded PNFD. 
	 * The operation can also be used to update the userDefinedData of an existing 
	 * PnfInfo information element without creating a new version of the PNFD.
	 * 
	 * The previous versions of the PNFDs are not modified.
	 * 
	 * 
	 * @param request request to update a PNFD 
	 * @return the ID of the updated PNFD.
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the original PNFD does not exist.
	 * @throws AlreadyExistingEntityException if the new PNFD already exists.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public String updatePnfd(UpdatePnfdRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, AlreadyExistingEntityException, FailedOperationException;
	
	/**
	 * Method to delete one or more PNFDs. 
	 * 
	 * A PNFD can only be deleted when there is no NS (in the active or NOT_INSTANTIATED state) using it.
	 * It is not possible to instantiate NSs that include a PNFD in deletion pending state.
	 * 
	 * @param request request to delete the PNFDs
	 * @return the IDs of the deleted PNFDs
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the PNFD does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public DeletePnfdResponse deletePnfd(DeletePnfdRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * Method to query the NFVO concerning details of one or more PNFDs.
	 * 
	 * @param request query
	 * @return PNFD query response
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the PNFD does not exist.
	 * @throws FailedOperationException if the operation has failed.
	 */
	public QueryPnfdResponse queryPnfd(GeneralizedQueryRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
}
