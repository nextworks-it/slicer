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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.ResourceHandle;
import it.nextworks.nfvmano.libs.ifa.common.enums.VimResourceType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides information 
 * of an existing or proposed resource used by the VNF.
 * 
 * REF IFA 007 v2.3.1 - 8.3.2
 * 
 * @author nextworks
 *
 */
public class ResourceDefinition implements InterfaceInformationElement {

	private String resourceDefinitionId;
	private VimResourceType type;
	private String vduId;
	private String resourceTemplateId;
	private ResourceHandle resourceHandle;
	
	public ResourceDefinition() { }
	
	/**
	 * Constructor
	 * 
	 * @param resourceDefinitionId Identifier of this ResourceDefinition information element, unique at least within the scope of the grant request.
	 * @param type Type of the resource definition referenced
	 * @param vduId Reference to the related Vdu applicable to this resource in the VNFD.
	 * @param resourceTemplateId Reference to a resource template (VnfVirtualLinkDesc, VirtualComputeDesc, VnfExtCpd, VirtualStorageDesc) in the VNFD.
	 * @param resourceHandle Resource information for an existing resource. Shall be present for resources that are planned to be deleted or modified. Shall be absent otherwise.
	 */
	public ResourceDefinition(String resourceDefinitionId,
			VimResourceType type,
			String vduId,
			String resourceTemplateId,
			ResourceHandle resourceHandle) { 
		this.resourceDefinitionId = resourceDefinitionId;
		this.type = type;
		this.vduId = vduId;
		this.resourceTemplateId = resourceTemplateId;
		this.resourceHandle = resourceHandle;
	}
	
	

	/**
	 * @return the resourceDefinitionId
	 */
	public String getResourceDefinitionId() {
		return resourceDefinitionId;
	}

	/**
	 * @return the type
	 */
	public VimResourceType getType() {
		return type;
	}

	/**
	 * @return the vduId
	 */
	public String getVduId() {
		return vduId;
	}

	/**
	 * @return the resourceTemplateId
	 */
	public String getResourceTemplateId() {
		return resourceTemplateId;
	}

	/**
	 * @return the resourceHandle
	 */
	public ResourceHandle getResourceHandle() {
		return resourceHandle;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceDefinitionId == null) throw new MalformattedElementException("Resource definition without ID");
		if (resourceHandle != null) resourceHandle.isValid();
	}

}
