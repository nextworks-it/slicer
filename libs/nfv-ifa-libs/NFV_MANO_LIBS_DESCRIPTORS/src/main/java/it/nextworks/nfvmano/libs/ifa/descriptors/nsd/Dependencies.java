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
package it.nextworks.nfvmano.libs.ifa.descriptors.nsd;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The Dependencies information element provides 
 * indications on the order in which VNFs associated 
 * to different VNF Profiles and/or nested NSs associated 
 * to different NS Profiles are to be instantiated.
 * 
 * NFV Management and Orchestration functions shall instantiate 
 * VNFs from the VnfProfile and/or nested NSs from the NsProfile
 * referenced in the primary attribute before instantiating VNFs 
 * from the VnfProfile and/or nested NSs from the NsProfile 
 * referenced in the secondary attribute.
 * 
 * Ref. IFA 014 v2.3.1 - 6.3.12
 * 
 * @author nextworks
 *
 */
@Entity
public class Dependencies implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsDf nsDf;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> primaryId = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> secondaryId = new ArrayList<>();
	
	
	public Dependencies() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsDf NS deployment flavour this dependecy element belongs to.
	 * @param primaryId References a VnfProfile or NsProfile.
	 * @param secondaryId References a VnfProfile or NsProfile.
	 */
	public Dependencies(NsDf nsDf,
			List<String> primaryId,
			List<String> secondaryId) {
		this.nsDf = nsDf;
		if (primaryId != null) this.primaryId = primaryId;
		if (secondaryId != null) this.secondaryId = secondaryId;
	}
	
	

	/**
	 * @return the primaryId
	 */
	@JsonProperty("primaryId")
	public List<String> getPrimaryId() {
		return primaryId;
	}

	/**
	 * @return the secondaryId
	 */
	@JsonProperty("secondaryId")
	public List<String> getSecondaryId() {
		return secondaryId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((this.primaryId == null) || (this.primaryId.isEmpty())) throw new MalformattedElementException("Dependency element without primary IDs");
		if ((this.secondaryId == null) || (this.secondaryId.isEmpty())) throw new MalformattedElementException("Dependency element without secondary IDs");
	}

	public void setNsDf(NsDf nsDf) {
		this.nsDf = nsDf;
	}

	public void setPrimaryId(List<String> primaryId) {
		this.primaryId = primaryId;
	}

	public void setSecondaryId(List<String> secondaryId) {
		this.secondaryId = secondaryId;
	}
}
