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
import it.nextworks.nfvmano.libs.ifa.common.elements.ScaleInfo;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The InstantiationLevel information element describes a given level 
 * of resources to be instantiated within a DF in term of the number of 
 * VNFC instances to be created from each VDU.
 * 
 * All the VDUs referenced in the level shall be part of the corresponding 
 * DF and their number shall be within the range (min/max) for this DF.
 * 
 *  REF. IFA-011 v2.3.1 - section 7.1.8.7
 * 
 * @author nextworks
 *
 */
@Entity
public class InstantiationLevel implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private VnfDf vnfDf;
	
	private String levelId;
	private String description;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<VduLevel> vduLevel = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<ScaleInfo> scaleInfo = new ArrayList<>();
	
	public InstantiationLevel() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfDf VNF deployment flavour this instantiation level belongs to
	 * @param levelId Uniquely identifies a level with the DF.
	 * @param description Human readable description of the level.
	 * @param vduLevel Indicates the number of instance of this VDU to deploy for this level.
	 * @param scaleInfo Represents for each aspect the scale level that corresponds to this instantiation level.
	 */
	public InstantiationLevel(VnfDf vnfDf,
			String levelId,
			String description,
			List<VduLevel> vduLevel,
			List<ScaleInfo> scaleInfo) {
		this.vnfDf = vnfDf;
		this.levelId = levelId;
		this.description = description;
		if (vduLevel != null) this.vduLevel = vduLevel;
		if (scaleInfo != null) this.scaleInfo = scaleInfo;
	}
	
	

	/**
	 * @return the levelId
	 */
	@JsonProperty("levelId")
	public String getLevelId() {
		return levelId;
	}


	/**
	 * @return the description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}


	/**
	 * @return the vduLevel
	 */
	@JsonProperty("vduLevel")
	public List<VduLevel> getVduLevel() {
		return vduLevel;
	}


	/**
	 * @return the scaleInfo
	 */
	@JsonProperty("scaleInfo")
	public List<ScaleInfo> getScaleInfo() {
		return scaleInfo;
	}


	@Override
	public void isValid() throws MalformattedElementException {
		if (levelId == null) throw new MalformattedElementException("Instantiation level without ID");
		if (description == null) throw new MalformattedElementException("Instantiation level without description");
		if ((vduLevel == null) || (vduLevel.isEmpty())) {
			throw new MalformattedElementException("Instantiation level without VDU levels");
		} else {
			for (VduLevel l : vduLevel) l.isValid();
		}
		for (ScaleInfo s: scaleInfo) s.isValid();
	}

}
