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
package it.nextworks.nfvmano.sebastian.vsnbi.messages;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * Request to modify a Vertical Service instance.
 * 
 * @author nextworks
 *
 */
public class ModifyVsRequest implements InterfaceMessage {

	private String vsiId;
	private String tenantId;
	private String vsdId;
	
	public ModifyVsRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the VS instance to be modified
	 * @param tenantId ID of the tenant owning the VS instance to be modified
	 * @param vsdId ID of the target VSD
	 */
	public ModifyVsRequest(String vsiId,
			String tenantId,
			String vsdId) {
		this.vsiId = vsiId;
		this.vsdId = vsdId;
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

	/**
	 * @return the vsdId
	 */
	public String getVsdId() {
		return vsdId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vsiId == null) throw new MalformattedElementException("Modify VS request without VS instance ID");
		if (vsdId == null) throw new MalformattedElementException("Modify VS request without new VSD ID");
	}

}
