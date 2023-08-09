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
 * The ChangeNsFlavourData specifies a new DF to be applied to the NS instance.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.27
 * 
 * @author nextworks
 *
 */
public class ChangeNsFlavourData implements InterfaceInformationElement {

	private String newFlavourId;
	private String nsInstantiationLevelId;
	
	public ChangeNsFlavourData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param newFlavourId Identifier of the new NS DF to be applied to this NS instance.
	 * @param nsInstantiationLevelId Identifier of the NS instantiation level of the DF to be used. If not present, the default NS instantiation level as declared in the NSD shall be used.
	 */
	public ChangeNsFlavourData(String newFlavourId,
			String nsInstantiationLevelId) {
		this.newFlavourId = newFlavourId;
		this.nsInstantiationLevelId = nsInstantiationLevelId;
	}
	
	

	/**
	 * @return the newFlavourId
	 */
	public String getNewFlavourId() {
		return newFlavourId;
	}

	/**
	 * @return the nsInstantiationLevelId
	 */
	public String getNsInstantiationLevelId() {
		return nsInstantiationLevelId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (newFlavourId == null) throw new MalformattedElementException("Change NS flavour data without new flavour ID");
	}

}
