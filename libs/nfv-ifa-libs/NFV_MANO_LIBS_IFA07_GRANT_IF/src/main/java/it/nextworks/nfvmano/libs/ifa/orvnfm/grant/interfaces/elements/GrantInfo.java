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

/**
 * This information element contains information about a Compute, 
 * storage or network resource whose addition/update/deletion was 
 * granted in a GrantVnfLifecycleOperationResponse.
 * 
 * REF IFA 007 v2.3.1 - 8.3.3
 * 
 * @author nextworks
 *
 */
public class GrantInfo implements InterfaceInformationElement {

	private String resourceDefinitionId;
	private String reservationId;
	private String vimId;
	private String resourceProviderId;
	private String zoneId;
	private String resourceGroupId;
	
	public GrantInfo() { }
	
	/**
	 * Constructor
	 * 
	 * @param resourceDefinitionId Identifier of the related ResourceDefinition information element from the grant request.
	 * @param reservationId The reservation identifier applicable to the VNFC/VirtualLink/VirtualStorage.
	 * @param vimId Reference to the identifier of the VimInfo information element defining the VIM under whose control this resource is to be placed.
	 * @param resourceProviderId Identifies the entity responsible for the management of the virtualised resource.
	 * @param zoneId Reference to the identifier of the ZoneInfo information element defining the resource zone into which this resource is to be placed.
	 * @param resourceGroupId Identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain, to be provided when allocating the resource.
	 */
	public GrantInfo(String resourceDefinitionId,
			String reservationId,
			String vimId,
			String resourceProviderId,
			String zoneId,
			String resourceGroupId) { 
		this.resourceDefinitionId = resourceDefinitionId;
		this.reservationId = reservationId;
		this.vimId = vimId;
		this.resourceProviderId = resourceProviderId;
		this.zoneId = zoneId;
		this.resourceGroupId = resourceGroupId;
	}

	
	
	/**
	 * @return the resourceDefinitionId
	 */
	public String getResourceDefinitionId() {
		return resourceDefinitionId;
	}

	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
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

	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	/**
	 * @return the resourceGroupId
	 */
	public String getResourceGroupId() {
		return resourceGroupId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceDefinitionId == null) throw new MalformattedElementException("Grant info without resource definition ID");

	}

}
