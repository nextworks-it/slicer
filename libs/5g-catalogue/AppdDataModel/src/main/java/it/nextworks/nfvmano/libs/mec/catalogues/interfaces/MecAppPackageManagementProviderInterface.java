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
package it.nextworks.nfvmano.libs.mec.catalogues.interfaces;

import it.nextworks.nfvmano.libs.common.exceptions.*;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages.OnboardAppPackageRequest;
import it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages.OnboardAppPackageResponse;
import it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages.QueryOnBoadedAppPkgInfoResponse;

import java.io.File;

/**
 * This interface provides the methods to manage MEC application packages.
 * 
 * It must be implemented by the core of the MEO and invoked by the
 * plugins who manage its external interface (e.g. a REST Controller)
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3
 * 
 * @author nextworks
 *
 */
public interface MecAppPackageManagementProviderInterface {

	/**
	 * This operation enables the MEPM to fetch on-boarded application package.
	 * 
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.1
	 * 
	 * @param onboardedAppPkgId Identifier of the on-boarded application package to be fetched
	 * @return the on-boarded application package
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the package does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public File fetchOnboardedApplicationPackage(String onboardedAppPkgId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This method allows to to query information about the Application Package.
	 * 
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.2/8
	 * 
	 * @param request query
	 * @return the list of queried application package infos
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the package does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryOnBoadedAppPkgInfoResponse queryApplicationPackage(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation enables the MEPM to subscribe with a filter for the notifications 
	 * related to events of application packages sent by the MEO.
	 * 
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.3
	 * 
	 * @param request subscription request
	 * @param consumer consumer of the notifications
	 * @return the subscription ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws FailedOperationException if the operation fails
	 */
	public String subscribeMecAppPackageInfo(SubscribeRequest request, MecAppPackageManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	/**
	 * Method to remove a previous subscription.
	 * 
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.3
	 *
	 * @param subscriptionId ID of the subscription to be removed
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void unsubscribeMecAppPackageInfo(String subscriptionId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * Method to on-board an application package in the MEO.
	 * 
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.5
	 * 
	 * @param request on-board request
	 * @return response on-board response
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws AlreadyExistingEntityException if the app package already exists.
	 * @throws FailedOperationException if the operation fails.
	 * @throws MalformattedElementException if the request is malformatted.
	 */
	public OnboardAppPackageResponse onboardAppPackage(OnboardAppPackageRequest request)
			throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will enable a previously disabled application package, 
	 * allowing again its use for instantiation of new application instances.
	 * 
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.6
	 * 
	 * @param onboardedAppPkgId ID of the app package to be enabled
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the app package does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void enableAppPackage(String onboardedAppPkgId)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will disable a previously enabled application package, 
	 * preventing any further use for instantiation of new network application 
	 * instance with this application package.
	 * 
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.7
	 * 
	 * @param onboardedAppPkgId ID of the app package to be disabled
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the app package does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void disableAppPackage(String onboardedAppPkgId)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will delete one application package.
	 * An application package shall only be deleted when there is no instantiated application instance using it.
	 * An application package in the deletion pending state is no longer enabled or disabled.
	 * 
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.9
	 * 
	 * @param onboardedAppPkgId Identifier of information held by the MEO about the specific on-boarded application package, which is to be deleted. This identifier was allocated by the MEO.
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the app package does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void deleteAppPackage(String onboardedAppPkgId)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS to abort the deletion of an application package that is in deletion pending state.
	 * 
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.10
	 * 
	 * @param onboardedAppPkgId Identifier of the onboarded application package of which the deletion is requested to be aborted.
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the app package does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void abortAppPackageDeletion(String onboardedAppPkgId)
		throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
}
