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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The NsScalingAspect information element describes the details 
 * of an NS scaling aspect. 
 * An NS scaling aspect is an abstraction representing a particular 
 * "dimension" or "property" along which a given NS can be scaled. 
 * Defining NS levels, in this context also known as NS scale levels, 
 * within an NS scaling aspect allows to scale NS instances "by steps", 
 * i.e. to increase/decrease their capacity in a discrete manner moving 
 * from one NS scale level to another. 
 * Scaling by a single step does not imply that exactly one instance of 
 * each entity involved in the NS scale level is created or removed.
 * 
 * Ref. IFA 014 v2.3.1 - 6.7.2
 * 
 * @author nextworks
 *
 */
@Entity
public class NsScalingAspect implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsDf nsDf;
	
	private String nsScalingAspectId;
	private String name;
	private String description;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "nsScale", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<NsLevel> nsScaleLevel = new ArrayList<>();
	
	public NsScalingAspect() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsDf NS deployment flavour this scaling aspect refers to
	 * @param nsScalingAspectId Identifier of this NsScalingAspect information element. It Uniquely identifies the NS scaling aspect in an NSD.
	 * @param name Provides a human readable name of the NS scaling aspect.
	 * @param description Provides a human readable description of the NS scaling aspect.
	 */
	public NsScalingAspect(NsDf nsDf,
			String nsScalingAspectId,
			String name,
			String description) {
		this.nsDf = nsDf;
		this.nsScalingAspectId = nsScalingAspectId;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsDf NS deployment flavour this scaling aspect refers to
	 * @param nsScalingAspectId Identifier of this NsScalingAspect information element. It Uniquely identifies the NS scaling aspect in an NSD.
	 * @param name Provides a human readable name of the NS scaling aspect.
	 * @param description Provides a human readable description of the NS scaling aspect.
	 * @param nsScaleLevel Describes the details of an NS level.
	 */
	public NsScalingAspect(NsDf nsDf,
			String nsScalingAspectId,
			String name,
			String description,
			List<NsLevel> nsScaleLevel) {
		this.nsDf = nsDf;
		this.nsScalingAspectId = nsScalingAspectId;
		this.name = name;
		this.description = description;
		if (nsScaleLevel != null) this.nsScaleLevel = nsScaleLevel;
	}
	
	

	/**
	 * @return the nsScalingAspectId
	 */
	@JsonProperty("nsScalingAspectId")
	public String getNsScalingAspectId() {
		return nsScalingAspectId;
	}

	/**
	 * @return the name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * @return the nsScaleLevel
	 */
	@JsonProperty("nsScaleLevel")
	public List<NsLevel> getNsScaleLevel() {
		return nsScaleLevel;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.nsScalingAspectId == null) throw new MalformattedElementException("NS Scaling Aspect without ID");
		if (this.name == null) throw new MalformattedElementException("NS Scaling Aspect without name");
		if (this.description == null) throw new MalformattedElementException("NS Scaling Aspect without description");
		if ((this.nsScaleLevel == null) || (this.nsScaleLevel.isEmpty())) {
			throw new MalformattedElementException("NS Scaling Aspect without NS level");
		} else {
			for (NsLevel l : this.nsScaleLevel) l.isValid();
		}
	}

	public void setNsDf(NsDf nsDf) {
		this.nsDf = nsDf;
	}

	public void setNsScalingAspectId(String nsScalingAspectId) {
		this.nsScalingAspectId = nsScalingAspectId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setNsScaleLevel(List<NsLevel> nsScaleLevel) {
		this.nsScaleLevel = nsScaleLevel;
	}
}
