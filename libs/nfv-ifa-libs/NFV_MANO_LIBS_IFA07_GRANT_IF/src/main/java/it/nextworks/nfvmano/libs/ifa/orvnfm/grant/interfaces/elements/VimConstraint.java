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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides information regarding a VIM selection constraint. 
 * A set of such constraints may be sent by the VNFM to the NFVO to influence the VIM 
 * selection decisions made by the NFVO as part of the granting process.
 * 
 * REF IFA 007 v2.3.1 - 8.3.7
 * 
 * @author nextworks
 *
 */
public class VimConstraint implements InterfaceInformationElement {

	private boolean sameResourceGroup;
	private List<ConstraintResourceRef> resource = new ArrayList<>();
	
	public VimConstraint() { }
	
	/**
	 * Constructor
	 * 
	 * @param sameResourceGroup If present and set to true, this signals that the constraint applies not only to the same VIM connection, but also to the same infrastructure resource group.
	 * @param resource References to resources in the constraint rule. The NFVO shall ensure that all resources in this list are managed by the same VIM.
	 */
	public VimConstraint(boolean sameResourceGroup,
			List<ConstraintResourceRef> resource) {
		this.sameResourceGroup = sameResourceGroup;
		if (resource != null) this.resource = resource;
	}

	
	
	/**
	 * @return the sameResourceGroup
	 */
	@JsonProperty("sameResourceGroup")
	public boolean isSameResourceGroup() {
		return sameResourceGroup;
	}

	/**
	 * @return the resource
	 */
	public List<ConstraintResourceRef> getResource() {
		return resource;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (resource.isEmpty()) throw new MalformattedElementException("VIM constraint without resources");
		else for (ConstraintResourceRef ref : resource) ref.isValid();
	}

}
