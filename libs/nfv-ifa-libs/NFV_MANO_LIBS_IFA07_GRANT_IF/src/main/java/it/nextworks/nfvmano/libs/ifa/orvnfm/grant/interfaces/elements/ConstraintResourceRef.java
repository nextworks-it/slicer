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
package it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.elements;


import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.enums.GrantIdType;

/**
 * This information element references a resource either 
 * by its VIM-level identifier for existing resources, or by the
 * identifier of a resourceDefinition information element 
 * in the grant request for new resources.
 * 
 * REF IFA 007 v2.3.1 - 8.3.8
 * 
 * @author nextworks
 *
 */
public class ConstraintResourceRef implements InterfaceInformationElement {

	private GrantIdType idType;
	private String resourceId;
	private String vimId;
	private String resourceProviderId;
	
	public ConstraintResourceRef() { }
	
	/**
	 * Constructor
	 * 
	 * @param idType The type of the identifier: "ResMgmt" (Resource-management-level identifier; this identifier is managed by the VIM in direct mode and is managed by the NFVO in indirect mode) or "Grant" (reference to identifier in the ResourceDefinition in the grant request).
	 * @param resourceId An actual resource-management-level identifier (idType=ResMgmt), or an identifier that references the ResourceDefinition in the grant request/response (idType=Grant).
	 * @param vimId Identifier of the VIM connection. It shall only be present when idType = ResMgmt.
	 * @param resourceProviderId Identifier of the resource provider. It shall only be present when idType = ResMgmt.
	 */
	public ConstraintResourceRef(GrantIdType idType,
			String resourceId,
			String vimId,
			String resourceProviderId) {
		this.idType = idType;
		this.resourceId = resourceId;
		this.vimId = vimId;
		this.resourceProviderId = resourceProviderId;
	}
	
	
	
	/**
	 * @return the idType
	 */
	public GrantIdType getIdType() {
		return idType;
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @return the vimId
	 */
	@JsonProperty("vimConnectionId")
	public String getVimId() {
		return vimId;
	}

	/**
	 * @return the resourceProviderId
	 */
	public String getResourceProviderId() {
		return resourceProviderId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceId == null) throw new MalformattedElementException("Constraint resource ref without resource ID");
	}

}
