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
package it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.messages.GrantVnfLifecycleOperationRequest;
import it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.messages.GrantVnfLifecycleOperationResponse;

/**
 * This interface defines one operation 
 * that allows the NFVO to grant lifecycle operations.
 * 
 * It must be implemented by the NFVO and invoked by the VNFM
 * to request a grant for allocating resources and, if supported,
 * received information from the NFVO about which resources
 * must be allocated and where (e.g. which server, availability 
 * zone, etc.). 
 * 
 * REF IFA 007 v2.3.1 - 6.3
 * 
 * @author nextworks
 *
 */
public interface VnfLifecycleOperationGrantingInterface {

	/**
	 * This operation allows the VNFM to request a grant for authorization of a VNF lifecycle operation.
	 * 
	 * REF IFA 007 v2.3.1 - 6.3.2
	 * 
	 * @param request request
	 * @return the GRANT response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails for a generic reason
	 * @throws NotExistingEntityException if one of the entities in the request is not found
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public GrantVnfLifecycleOperationResponse grantVnfLifecycleOperation(GrantVnfLifecycleOperationRequest request)
			throws MethodNotImplementedException, FailedOperationException, NotExistingEntityException, MalformattedElementException;
	
}
