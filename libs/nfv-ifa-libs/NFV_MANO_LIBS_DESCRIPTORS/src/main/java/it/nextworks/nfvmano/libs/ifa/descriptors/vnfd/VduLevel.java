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
package it.nextworks.nfvmano.libs.ifa.descriptors.vnfd;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The VduLevel information element indicates for a given VDU 
 * in a given level the number of instances to deploy.
 * 
 *  REF. IFA-011 v2.3.1 - section 7.1.8.9
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VduLevel implements DescriptorInformationElement {

	private String vduId;
	private int numberOfInstances;
	
	public VduLevel() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vduId Uniquely identifies a VDU.
	 * @param numberOfInstances Number of instances of VNFC based on this VDU to deploy for this level.
	 */
	public VduLevel(String vduId, int numberOfInstances) {	
		this.vduId = vduId;
		this.numberOfInstances = numberOfInstances;
	}
	
	

	/**
	 * @return the vduId
	 */
	@JsonProperty("vduId")
	public String getVduId() {
		return vduId;
	}

	/**
	 * @return the numberOfInstances
	 */
	@JsonProperty("numberOfInstances")
	public int getNumberOfInstances() {
		return numberOfInstances;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vduId == null) throw new MalformattedElementException("VDU level without VDU ID");
	}

}
