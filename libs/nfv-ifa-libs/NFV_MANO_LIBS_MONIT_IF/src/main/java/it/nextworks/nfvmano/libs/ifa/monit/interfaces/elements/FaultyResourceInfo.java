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
package it.nextworks.nfvmano.libs.ifa.monit.interfaces.elements;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.ResourceHandle;
import it.nextworks.nfvmano.libs.ifa.common.enums.VirtualResourceType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The FaultyResourceInfo information element encapsulates information 
 * about faulty resource that has a negative impact on a VNF.
 * 
 * REF IFA 007 v2.3.1 - 8.8.5
 * 
 * @author nextworks
 *
 */
public class FaultyResourceInfo implements InterfaceInformationElement {

	private ResourceHandle faultyResource;
	private VirtualResourceType faultyResourceType;
	
	public FaultyResourceInfo() { }
	
	/**
	 * Constructor
	 * 
	 * @param faultyResource Information that identifies the faulty resource instance and its managing entity.
	 * @param faultResourceType Type of the faulty resource.
	 */
	public FaultyResourceInfo(ResourceHandle faultyResource,
			VirtualResourceType faultResourceType) { 
		this.faultyResource = faultyResource;
		this.faultyResourceType = faultResourceType;
	}
	
	

	/**
	 * @return the faultyResource
	 */
	public ResourceHandle getFaultyResource() {
		return faultyResource;
	}

	/**
	 * @return the faultyResourceType
	 */
	public VirtualResourceType getFaultyResourceType() {
		return faultyResourceType;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (faultyResource == null) throw new MalformattedElementException("Faulty resource info without resource handle");
		else faultyResource.isValid();
	}

}
