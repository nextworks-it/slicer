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
package it.nextworks.nfvmano.sebastian.vsfm.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to terminate a Vertical Service instance.
 * 
 * 
 * @author nextworks
 *
 */
public class TerminateVsRequest implements InterfaceMessage {

	private String vsiId;
	private String tenantId;
	
	public TerminateVsRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the VS instance to be terminated
	 * @param tenantId ID of the tenant owning the VS instance
	 */
	public TerminateVsRequest(String vsiId, 
			String tenantId) { 
		this.vsiId = vsiId;
		this.tenantId = tenantId;
	}
	
	
	
	/**
	 * @return the vsiId
	 */
	public String getVsiId() {
		return vsiId;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vsiId == null) throw new MalformattedElementException("Terminate VS request without VSI ID");
		if (tenantId == null) throw new MalformattedElementException("Terminate VS request without tenant ID");
	}

}
