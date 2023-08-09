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
 * If the VIM requires the use of virtual compute resource flavours 
 * during compute resource instantiation, it is assumed
 * that such flavours are selected or created by the NFVO 
 * based on the information in the VirtualComputeDesc
 * information elements defined in the VNFD.
 * 
 * This information element defines the mapping between a 
 * VirtualComputeDesc in the VNFD and the corresponding compute 
 * resource flavour managed by the NFVO in the VIM.
 * 
 * REF IFA 007 v2.3.1 - 8.3.10
 * 
 * @author nextworks
 *
 */
public class VimComputeResourceFlavour implements InterfaceInformationElement {

	private String vimId;
	private String resourceProviderId;
	private String vnfdVirtualComputeDescId;
	private String vimFlavourId;
	
	public VimComputeResourceFlavour() { }
	
	/**
	 * Constructor
	 * 
	 * @param vimId Identifier of the VIM that manages the assets listed in this information element. Shall be supported and present if VNF-related resource management in direct mode is  applicable.
	 * @param resourceProviderId Identifies the entity responsible for the management of the virtualised resource.
	 * @param vnfdVirtualComputeDescId Identifier which references the VirtualComputeDesc in the VNFD that maps to this flavour.
	 * @param vimFlavourId Identifier of the compute resource flavour in the resource management layer (i.e. VIM).
	 */
	public VimComputeResourceFlavour(String vimId,
			String resourceProviderId,
			String vnfdVirtualComputeDescId,
			String vimFlavourId) { 
		this.vimId = vimId;
		this.resourceProviderId = resourceProviderId;
		this.vnfdVirtualComputeDescId = vnfdVirtualComputeDescId;
		this.vimFlavourId = vimFlavourId;
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
	 * @return the vnfdVirtualComputeDescId
	 */
	public String getVnfdVirtualComputeDescId() {
		return vnfdVirtualComputeDescId;
	}

	/**
	 * @return the vimFlavourId
	 */
	public String getVimFlavourId() {
		return vimFlavourId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfdVirtualComputeDescId == null) throw new MalformattedElementException("VIM compute resource flavour without VNFD VCD ID");
		if (vimFlavourId == null) throw new MalformattedElementException("VIM compute resource flavour without VIM flavour ID");
	}

}
