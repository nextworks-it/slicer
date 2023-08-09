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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * A VNFD Element Group is a mechanism for associating elements of a VNFD 
 * (Vdus and VnfVirtualLinkDesc(s)) for a certain purpose, for example, scaling aspects.
 * A given element can belong to multiple groups.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.4
 * 
 * @author nextworks
 *
 */
@Entity
public class VnfdElementGroup implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Vnfd vnfd;
	
	
	private String vnfdElementGroupId;
	private String description;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> vdu = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> virtualLinkDesc = new ArrayList<>();
	
	public VnfdElementGroup() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfd VNFD this element group belongs to
	 * @param vnfdElementGroupId Unique identifier of this group in the VNFD.
	 * @param description Human readable description of the group.
	 * @param vdu References to Vdus that are part of this group.
	 * @param virtualLinkDesc References to VnfVirtualLinkDesc that are part of this group.
	 */
	public VnfdElementGroup(Vnfd vnfd,
			String vnfdElementGroupId, 
			String description,
			List<String> vdu,
			List<String> virtualLinkDesc) {
		this.vnfd = vnfd;
		this.vnfdElementGroupId = vnfdElementGroupId;
		this.description = description;
		if (vdu != null) this.vdu = vdu;
		if (virtualLinkDesc != null) this.virtualLinkDesc = virtualLinkDesc;
	}
	
	

	/**
	 * 
	 * @return the vnfdElementGroupId
	 */
	@JsonProperty("vnfdElementGroupId")
	public String getVnfdElementGroupId() {
		return vnfdElementGroupId;
	}

	/**
	 * @return the description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * @return the vdu
	 */
	@JsonProperty("vdu")
	public List<String> getVdu() {
		return vdu;
	}

	/**
	 * @return the virtualLinkDesc
	 */
	@JsonProperty("virtualLinkDesc")
	public List<String> getVirtualLinkDesc() {
		return virtualLinkDesc;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfdElementGroupId == null) throw new MalformattedElementException("VNFD element group without ID");
		if (description == null) throw new MalformattedElementException("VNFD element group without description");
	}

}
