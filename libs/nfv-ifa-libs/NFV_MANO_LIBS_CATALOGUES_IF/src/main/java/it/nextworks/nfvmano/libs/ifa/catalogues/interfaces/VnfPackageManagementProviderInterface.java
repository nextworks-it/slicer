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

import java.io.File;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.DeleteVnfPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.DisableVnfPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.EnableVnfPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.FetchOnboardedVnfPackageArtifactsRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnBoardVnfPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnBoardVnfPackageResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryOnBoardedVnfPkgInfoResponse;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;

/**
 * This interface allows for the management of VNF Packages.
 * 
 *  It must be implemented by the core of the NFVO and invoked by the
 * plugins who manage the NFVO external interface (e.g. a REST Controller)
 * 
 *  REF IFA 013 v2.3.1 - 7.7
 * 
 * @author nextworks
 *
 */
public interface VnfPackageManagementProviderInterface {

	/**
	 * This operation will on-board a VNF Package in the NFVO.
	 * 
	 * @param request on-board request
	 * @return on-board response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws AlreadyExistingEntityException if the VNF package is already existing in the NFVO catalogue
	 * @throws FailedOperationException if the operation fails internally in the NFVO
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public OnBoardVnfPackageResponse onBoardVnfPackage(OnBoardVnfPackageRequest request) 
			throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will enable a previously disabled VNF Package instance, 
	 * allowing again its use for instantiation of new VNF with this package.
	 * 
	 * @param request enable request
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VNF package info does not exist
	 * @throws FailedOperationException if the operation fails internally in the NFVO
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void enableVnfPackage(EnableVnfPackageRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will disable a previously enabled VNF Package instance, 
	 * preventing further use for instantiation of new VNFs with this package 
	 * (unless and until the VNF Package is re-enabled).
	 * 
	 * @param request enable request
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VNF package info does not exist
	 * @throws FailedOperationException if the operation fails internally in the NFVO
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void disableVnfPackage(DisableVnfPackageRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will delete a VNF Package.
	 * A VNF Package can only be deleted once there are no VNFs using it.
	 * A deletion pending VNF Package can no longer be enabled, disabled or updated. 
	 * It is not possible to instantiate VNFs using a VNF Package in the "deletion pending" state.
	 * 
	 * @param request delete request
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VNF package does not exist
	 * @throws FailedOperationException if the operation fails internally in the NFVO
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void deleteVnfPackage(DeleteVnfPackageRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will enable the OSS/BSS to query from the NFVO 
	 * for information it has stored about one or more VNF Packages.
	 *  
	 * @param request query
	 * @return query response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VNF package info is not found in the NFVO catalogue
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryOnBoardedVnfPkgInfoResponse queryVnfPackageInfo(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS/BSS to subscribe with a filter 
	 * for the notifications related to on-boarding of VNF Packages 
	 * and changes of VNF Packages sent by the NFVO.
	 * 
	 * @param request subscription request
	 * @param consumer	subscriber
	 * @return the subscription ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws FailedOperationException if the subscription fails
	 */
	public String subscribeVnfPackageInfo(SubscribeRequest request, VnfPackageManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	
	/**
	 * Method to remove a previous subscription
	 *
	 * @param subscriptionId ID of the subscription to be removed
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void unsubscribeVnfPackageInfo(String subscriptionId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS to fetch a whole on-boarded VNF Package. 
	 * The package is addressed using an identifier of information held by the 
	 * NFVO about the specific on-boarded VNF Package.
	 * 
	 * @param onboardedVnfPkgInfoId Identifier of information held by the NFVO about the specific on-boarded VNF Package. This identifier was allocated by the NFVO.
	 * @return the VNF package
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VNF package is not found in the NFVO catalogue
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public File fetchOnboardedVnfPackage(String onboardedVnfPkgInfoId) 
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS/BSS to fetch selected artifacts 
	 * contained in an on-boarded VNF package.
	 * 
	 * @param request fetch VNF package artifacts request
	 * @return the requested artifacts
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VNF package is not found in the NFVO catalogue
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public List<File> fetchOnboardedVnfPackageArtifacts(FetchOnboardedVnfPackageArtifactsRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS to abort the deletion 
	 * of a VNF Package that is in deletion pending state.
	 * 
	 * @param onboardedVnfPkgInfoId Identifier of the onboarded VNF Package of which the deletion is requested to be aborted.
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the VNF package is not found in the NFVO catalogue
	 * @throws FailedOperationException if the operation fails internally within the NFVO
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void abortVnfPackageDeletion(String onboardedVnfPkgInfoId)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the consumer to query information about subscriptions.
	 * TODO: still to be defined the format of the request
	 * 
	 * REF IFA 007 v2.3.1 - 6.2.9
	 * 
	 * @param request subscription query
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void queryVnfPackageSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
}
