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
package it.nextworks.nfvmano.sebastian.catalogue.elements;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Embeddable
public class VsbForwardingGraphEntry implements DescriptorInformationElement {

	private String vsComponentId;
	private String endPointId;
	
	public VsbForwardingGraphEntry() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vsComponentId ID of the VS atomic component
	 * @param endPointId ID of the end point within the atomic component
	 */
	public VsbForwardingGraphEntry(String vsComponentId, String endPointId) {
		this.vsComponentId = vsComponentId;
		this.endPointId = endPointId;
	}
	
	

	/**
	 * @return the vsComponentId
	 */
	public String getVsComponentId() {
		return vsComponentId;
	}

	/**
	 * @return the endPointId
	 */
	public String getEndPointId() {
		return endPointId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vsComponentId == null) throw new MalformattedElementException("VS Forwarding Graph element without VS component");
		if (endPointId == null) throw new MalformattedElementException("VS Forwarding Graph element without end point");
	}

}
