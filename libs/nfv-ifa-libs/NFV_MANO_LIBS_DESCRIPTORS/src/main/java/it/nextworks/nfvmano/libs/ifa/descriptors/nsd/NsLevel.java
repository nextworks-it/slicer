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
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualLinkDf;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The NsLevel information element describes the details of an NS level. 
 * 
 * An NS level consists of a list of involved entities, i.e. VNFs, VLs and/or nested NSs. 
 * For each involved VNF/nested NS, the number of instances required by the NS level is specified. 
 * For each involved VL, the bitrate requirements corresponding to the NS level are specified.
 * 
 * Ref. IFA 014 v2.3.1 - 6.3.9
 * 
 * 
 * @author nextworks
 *
 */
@Entity
public class NsLevel implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsDf nsDf;
	
	@JsonIgnore
	@ManyToOne
	private NsScalingAspect nsScale;
	
	private String nsLevelId;
	private String description;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<VnfToLevelMapping> vnfToLevelMapping = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<NsToLevelMapping> nsToLevelMapping = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsLevel", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VirtualLinkToLevelMapping> virtualLinkToLevelMapping = new ArrayList<>();
	
	public NsLevel() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsScale NS scaling aspect this NS level belongs to
	 * @param nsLevelId Identifier of this NsLevel information element. It uniquely identifies an NS level within the DF.
	 * @param description Human readable description of the NS level.
	 * @param vnfToLevelMapping Specifies the profile of the VNFs involved in this NS level and, for each of them, the required number of instances.
	 * @param nsToLevelMapping Specifies the profile of the nested NSes involved in this NS level
	 * 
	 */
	public NsLevel(NsScalingAspect nsScale,
			String nsLevelId,
			String description,
			List<VnfToLevelMapping> vnfToLevelMapping,
			List<NsToLevelMapping> nsToLevelMapping) {
		this.nsScale = nsScale;
		this.nsLevelId = nsLevelId;
		this.description = description;
		if (vnfToLevelMapping != null) this.vnfToLevelMapping = vnfToLevelMapping;
		if (nsToLevelMapping != null) this.nsToLevelMapping = nsToLevelMapping;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsDf NS deployment flavour this NS level belongs to
	 * @param nsLevelId Identifier of this NsLevel information element. It uniquely identifies an NS level within the DF.
	 * @param description Human readable description of the NS level.
	 * @param vnfToLevelMapping Specifies the profile of the VNFs involved in this NS level and, for each of them, the required number of instances.
	 * @param nsToLevelMapping Specifies the profile of the nested NSes involved in this NS level
	 * 
	 */
	public NsLevel(NsDf nsDf,
			String nsLevelId,
			String description,
			List<VnfToLevelMapping> vnfToLevelMapping,
			List<NsToLevelMapping> nsToLevelMapping) {
		this.nsDf = nsDf;
		this.nsLevelId = nsLevelId;
		this.description = description;
		if (vnfToLevelMapping != null) this.vnfToLevelMapping = vnfToLevelMapping;
		if (nsToLevelMapping != null) this.nsToLevelMapping = nsToLevelMapping;
	}

	
	
	/**
	 * @return the nsLevelId
	 */
	@JsonProperty("nsLevelId")
	public String getNsLevelId() {
		return nsLevelId;
	}

	/**
	 * @return the description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * @return the vnfToLevelMapping
	 */
	@JsonProperty("vnfToLevelMapping")
	public List<VnfToLevelMapping> getVnfToLevelMapping() {
		return vnfToLevelMapping;
	}

	/**
	 * @return the nsToLevelMapping
	 */
	@JsonProperty("nsToLevelMapping")
	public List<NsToLevelMapping> getNsToLevelMapping() {
		return nsToLevelMapping;
	}

	/**
	 * @return the virtualLinkToLevelMapping
	 */
	@JsonProperty("virtualLinkToLevelMapping")
	public List<VirtualLinkToLevelMapping> getVirtualLinkToLevelMapping() {
		return virtualLinkToLevelMapping;
	}

	@JsonIgnore
	public void addVirtualLinkToLevelMapping(VirtualLinkToLevelMapping mapping) {
		this.virtualLinkToLevelMapping.add(mapping);
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.nsLevelId == null) throw new MalformattedElementException("NS level without ID");
		if (this.description == null) throw new MalformattedElementException("NS level without description");
		boolean noVnfMap = false;
		boolean noNsMap = false;
		boolean noVlMap = false;
		if ((this.vnfToLevelMapping == null) || (this.vnfToLevelMapping.isEmpty())) {
			noVnfMap = true;
		} else {
			for (VnfToLevelMapping map : this.vnfToLevelMapping) map.isValid();
		}
		if ((this.nsToLevelMapping == null) || (this.nsToLevelMapping.isEmpty())) {
			noNsMap = true;
		} else {
			for (NsToLevelMapping map : this.nsToLevelMapping) map.isValid();
		}
		if ((this.virtualLinkToLevelMapping == null) || (this.virtualLinkToLevelMapping.isEmpty())) {
			noVlMap = true;
		} else {
			for (VirtualLinkToLevelMapping map : this.virtualLinkToLevelMapping) map.isValid();
		}
		if (noNsMap && noVnfMap && noVlMap) throw new MalformattedElementException("NS level without any mapping declared");
	}

	public void setNsDf(NsDf nsDf) {
		this.nsDf = nsDf;
	}

	public void setNsScale(NsScalingAspect nsScale) {
		this.nsScale = nsScale;
	}

	public void setNsLevelId(String nsLevelId) {
		this.nsLevelId = nsLevelId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setVnfToLevelMapping(List<VnfToLevelMapping> vnfToLevelMapping) {
		this.vnfToLevelMapping = vnfToLevelMapping;
	}

	public void setNsToLevelMapping(List<NsToLevelMapping> nsToLevelMapping) {
		this.nsToLevelMapping = nsToLevelMapping;
	}

	public void setVirtualLinkToLevelMapping(List<VirtualLinkToLevelMapping> virtualLinkToLevelMapping) {
		this.virtualLinkToLevelMapping = virtualLinkToLevelMapping;
	}
}
