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

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The AffinityOrAntiAffinityResourceList information element 
 * defines an explicit list of resources to express affinity or
 * anti-affinity between these resources and a current resource.
 * 
 *  REF IFA 005 v2.3.1 - 8.4.8.3
 * 
 * @author nextworks
 *
 */
public class AffinityResourceList  implements InterfaceInformationElement {

	private List<String> resource = new ArrayList<>();
	
	public AffinityResourceList() {	}

	/**
	 * Constructor
	 * 
	 * @param resource List of identifiers of virtualised resources
	 */
	public AffinityResourceList(List<String> resource) {
		if (resource != null) this.resource = resource;
	}
	
	/**
	 * @return the resource
	 */
	public List<String> getResource() {
		return resource;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((resource == null) || (resource.isEmpty())) 
				throw new MalformattedElementException("Affinity resource list without resources");
	}
	
}
