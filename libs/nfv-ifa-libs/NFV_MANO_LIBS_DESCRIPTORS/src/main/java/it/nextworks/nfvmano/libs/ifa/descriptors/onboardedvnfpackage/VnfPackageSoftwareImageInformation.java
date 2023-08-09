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
package it.nextworks.nfvmano.libs.ifa.descriptors.onboardedvnfpackage;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element represents an artifact contained in a VNF Package 
 * which represents a Software Image.
 * 
 * REF IFA 013 v2.3.1 - 8.6.4
 * 
 * @author nextworks
 *
 */
@Entity
public class VnfPackageSoftwareImageInformation implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private OnboardedVnfPkgInfo vnfPkgInfo;
	
	private String accessInformation;
		
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "vnfPkgSwImage", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private SoftwareImageInformation softwareImageInformation;
	
	public VnfPackageSoftwareImageInformation() {
		// JPA only
	}
	
	public VnfPackageSoftwareImageInformation(OnboardedVnfPkgInfo vnfPkgInfo,
			String accessInformation) {
		this.vnfPkgInfo = vnfPkgInfo;
		this.accessInformation = accessInformation;
	}
	
	

	/**
	 * @return the accessInformation
	 */
	@JsonProperty("accessInformation")
	public String getAccessInformation() {
		return accessInformation;
	}

	/**
	 * @return the softwareImageInformation
	 */
	@JsonProperty("softwareImageInformation")
	public SoftwareImageInformation getSoftwareImageInformation() {
		return softwareImageInformation;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (accessInformation == null) throw new MalformattedElementException("VNF package sw image info without access information");
		if (softwareImageInformation == null) throw new MalformattedElementException("VNF package sw image info without sw image information");
	}

}
