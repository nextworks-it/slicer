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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements;


import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.AffinityScope;
import it.nextworks.nfvmano.libs.ifa.common.enums.AffinityType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Two ways of specifying affinity or anti-affinity can be distinguished: 
 * Explicit resource lists and named resource groups.
 * 
 * In case of an explicit resource list, the consumer manages the list 
 * of resources the actual resource is requested to be
 * affine or anti-affine with, and builds the list as more resources are created. 
 * 
 * In case of a named resource group, the consumer needs to create the group 
 * first by invoking the appropriate operation to create a Compute/Storage/Network
 * resource affinity or anti-affinity constraints group. Subsequently, as part
 * of resource creation, the consumer passes the name(s) or identifier(s) of the 
 * group(s) to the producer which manages and builds the group of resources.
 * 
 *  REF IFA 005 v2.3.1 - 8.4.8.2
 * 
 * @author nextworks
 *
 */
public class AffinityConstraint implements InterfaceInformationElement {

	private AffinityType type;
	private AffinityScope scope;
	private String affinityAntiAffinityResourceGroup;
	private AffinityResourceList affinityAntiAffinityResourceList;
	
	
	public AffinityConstraint() {	}
	
	/**
	 * Constructor
	 * 
	 * @param type Indicates whether this is an affinity or anti-affinity constraint.
	 * @param scope Qualifies the scope of the constraint.
	 * @param affinityAntiAffinityResourceGroup Consumer-managed list of identifiers of virtualised resources with which the actual resource is requested to be affine or anti-affine.
	 * @param affinityAntiAffinityResourceList Identifier of the producer-managed group of virtualised resources with which the actual resource is requested to be affine or anti-affine.
	 */
	public AffinityConstraint(AffinityType type,
			AffinityScope scope,
			String affinityAntiAffinityResourceGroup,
			AffinityResourceList affinityAntiAffinityResourceList) {
		this.type = type;
		this.scope = scope;
		this.affinityAntiAffinityResourceGroup = affinityAntiAffinityResourceGroup;
		if (affinityAntiAffinityResourceList != null) this.affinityAntiAffinityResourceList = affinityAntiAffinityResourceList;
	}

	
	
	/**
	 * @return the scope
	 */
	public AffinityScope getScope() {
		return scope;
	}

	/**
	 * @return the type
	 */
	public AffinityType getType() {
		return type;
	}

	/**
	 * @return the affinityAntiAffinityResourceGroup
	 */
	public String getAffinityAntiAffinityResourceGroup() {
		return affinityAntiAffinityResourceGroup;
	}

	/**
	 * @return the affinityAntiAffinityResourceList
	 */
	public AffinityResourceList getAffinityAntiAffinityResourceList() {
		return affinityAntiAffinityResourceList;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (affinityAntiAffinityResourceList != null) {
			affinityAntiAffinityResourceList.isValid();
			if (affinityAntiAffinityResourceGroup != null) throw new MalformattedElementException("Affinity constraints with both list and group");
		}
		if ((affinityAntiAffinityResourceGroup == null) && (affinityAntiAffinityResourceList == null))
			throw new MalformattedElementException("Affinity constraints without group and without list");
	}

}
