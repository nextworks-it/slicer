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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements;


import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element specifies the parameters that are needed
 * for the creation of a new VNFFG instance.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.21
 * 
 * @author nextworks
 *
 */
public class AddVnffgData implements InterfaceInformationElement {

	private String vnffgdId;
	private String vnffgName;
	private String description;
	
	public AddVnffgData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnffgdId Identifier of the VNFFGD which defines the VNFFG to be added
	 * @param vnffgName Human readable name for the VNFFG.
	 * @param description Human readable description for the VNFFG.
	 */
	public AddVnffgData(String vnffgdId,
			String vnffgName,
			String description) {
		this.vnffgdId = vnffgdId;
		this.vnffgName = vnffgName;
		this.description = description;
	}
	
	

	/**
	 * @return the vnffgdId
	 */
	public String getVnffgdId() {
		return vnffgdId;
	}

	/**
	 * @return the vnffgName
	 */
	public String getVnffgName() {
		return vnffgName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnffgdId == null) throw new MalformattedElementException("Add VNFFG data without VNFFGD ID");
		if (vnffgName == null) throw new MalformattedElementException("Add VNFFG data without VNFFG name");
		if (description == null) throw new MalformattedElementException("Add VNFFG data without VNFFG description");
	}

}
