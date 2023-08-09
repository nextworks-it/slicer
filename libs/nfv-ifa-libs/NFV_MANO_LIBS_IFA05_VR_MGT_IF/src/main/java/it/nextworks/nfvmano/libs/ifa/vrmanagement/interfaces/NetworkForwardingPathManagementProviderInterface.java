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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp.ChangeNfpStateRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp.ChangeNfpStateResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp.CreateNfpRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp.CreateNfpResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp.DeleteNfpRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp.DeleteNfpResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp.QueryNfpResponse;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp.UpdateNfpRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp.UpdateNfpResponse;

/**
 * This interface is implemented by a VIM plugin and invoked by the NFVO. 
 * The interface enables, for instance, sending an NFP representation to the VIM so
 * that the VIM can set-up necessary network connections and paths in the related NFVI.
 * 
 * An NFP is an ordered list of Connection Points with the associated classifying policy to be applied.
 * 
 * REF IFA 005 v2.3.1 - 7.4.5
 * 
 * @author nextworks
 *
 */
public interface NetworkForwardingPathManagementProviderInterface {

	/**
	 * This operation is used to set-up an NFP in the NFVI.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.5.2
	 * 
	 * @param request request
	 * @return the operation response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation has failed
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public CreateNfpResponse createNfp(CreateNfpRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation is used to query a single or multiple NFPs.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.5.3
	 * 
	 * @param request request
	 * @return the operation response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation has failed
	 * @throws NotExistingEntityException if the resource has not been found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryNfpResponse queryNfp(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation is used to remove an existing NFP in the NFVI.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.5.4
	 * 
	 * @param request request
	 * @return the operation response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation has failed
	 * @throws NotExistingEntityException if the resource has not been found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public DeleteNfpResponse deleteNfp(DeleteNfpRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation is used to request changing the state (enable or disable) of an NFP.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.5.5
	 * 
	 * @param request request
	 * @return the operation response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation has failed
	 * @throws NotExistingEntityException if the resource has not been found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public ChangeNfpStateResponse changeNfpStatus(ChangeNfpStateRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
	/**
	 * This operation is used to update or create the classification and selection rule 
	 * for an existing NFP instance in the NFVI.
	 * 
	 * REF IFA 005 v2.3.1 - 7.4.5.6
	 * 
	 * @param request request
	 * @return the operation response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws NotExistingEntityException if the NFP has not been found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public UpdateNfpResponse updateNfp (UpdateNfpRequest request)
		throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
}
