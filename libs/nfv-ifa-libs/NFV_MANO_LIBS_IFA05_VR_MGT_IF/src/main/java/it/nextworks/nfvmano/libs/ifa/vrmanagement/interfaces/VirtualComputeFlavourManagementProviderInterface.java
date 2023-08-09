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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vcompute.CreateComputeFlavourRequest;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vcompute.QueryComputeFlavourResponse;

/**
 * This interface allows an authorized consumer functional block to request operations 
 * related to flavours. The interface includes operations for allocating, querying, 
 * updating and terminating flavours.
 * 
 * REF IFA 005 v2.3.1 - section 7.3.5
 * 
 * @author nextworks
 *
 */
public interface VirtualComputeFlavourManagementProviderInterface {

	/**
	 * This operation allows requesting the creation of a flavour as 
	 * indicated by the consumer functional block.
	 * 
	 * REF IFA 005 v2.3.1 - 7.3.5.2
	 * 
	 * @param request request to create a new compute flavour
	 * @return the ID of the compute flavour
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws FailedOperationException if the operation fails
	 */
	public String createComputeFlavour(CreateComputeFlavourRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	/**
	 * This operation allows querying information about created Compute Flavours.
	 * 
	 * REF IFA 005 v2.3.1 - 7.3.5.3
	 * 
	 * @param request query for a compute flavour
	 * @return the compute flavour
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws NotExistingEntityException if the requested entity does not exist
	 * @throws FailedOperationException if the operation fails
	 */
	public QueryComputeFlavourResponse queryComputeFlavour(GeneralizedQueryRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * This operation allows deleting a Compute Flavour.
	 * 
	 * REF IFA 005 v2.3.1 - 7.3.5.4
	 * 
	 * @param flavourId Identifier of the Compute Flavour to be deleted.
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws NotExistingEntityException if the target compute flavour does not exist
	 * @throws FailedOperationException if the operation fails
	 */
	public void deleteComputeFlavour(String flavourId)
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;

	
}
