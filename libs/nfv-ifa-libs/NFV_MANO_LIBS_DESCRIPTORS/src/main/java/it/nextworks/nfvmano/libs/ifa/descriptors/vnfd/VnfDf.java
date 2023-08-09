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

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
import it.nextworks.nfvmano.libs.ifa.common.elements.MonitoringParameter;
import it.nextworks.nfvmano.libs.ifa.common.enums.VnfLcmOperation;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.AffinityOrAntiAffinityGroup;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualLinkProfile;

/**
 * The VnfDf describes a specific deployment version of a VNF.
 * 
 *  REF. IFA-011 v2.3.1 - section 7.1.8.2
 * 
 * @author nextworks
 *
 */
@Entity
public class VnfDf implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Vnfd vnfd;
	
	private String flavourId;
	private String description;
	
	@OneToMany(mappedBy = "vnfDf", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VduProfile> vduProfile = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "vnfDf", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VirtualLinkProfile> virtualLinkProfile = new ArrayList<>();
	
	@OneToMany(mappedBy = "vnfDf", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<InstantiationLevel> instantiationLevel = new ArrayList<>();
	
	private String defaultInstantiationLevelId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<VnfLcmOperation> supportedOperation = new ArrayList<>();
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "df", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private VnfLcmOperationsConfiguration vnfLcmOperationsConfiguration;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<AffinityOrAntiAffinityGroup> affinityOrAntiAffinityGroup = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<MonitoringParameter> monitoringParameter = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<ScalingAspect> scalingAspect = new ArrayList<>();
	
	public VnfDf() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfd VNFD this deployment flavour belongs to
	 * @param flavourId Identifier of this DF within the VNFD.
	 * @param description Human readable description of the DF.
	 * @param defaultInstantiationLevelId This attribute references the "instantiationLevel" entry which defines the default instantiation level for this DF.
	 * @param supportedOperation Indicates which operations are available for this DF via the VNF LCM interface.
	 * @param affinityOrAntiAffinityGroup Specifies affinity or anti-affinity relationship applicable between the virtualisation containers (e.g. virtual machines) to be created using different VDUs or internal VLs to be created using different VnfVirtualLinkDesc(s) in the same affinity or anti-affinity group.
	 * @param monitoringParameter Defines the virtualised resources monitoring parameters on VNF level.
	 * @param scalingAspect The scaling aspects supported by this DF of the VNF.
	 */
	public VnfDf(Vnfd vnfd,
			String flavourId,
			String description,
			 String defaultInstantiationLevelId,
			 List<VnfLcmOperation> supportedOperation,
			 List<AffinityOrAntiAffinityGroup> affinityOrAntiAffinityGroup,
			 List<MonitoringParameter> monitoringParameter,
			 List<ScalingAspect> scalingAspect) {
		this.vnfd = vnfd;
		this.flavourId = flavourId;
		this.description = description;
		this.defaultInstantiationLevelId = defaultInstantiationLevelId;
		if (supportedOperation != null) this.supportedOperation = supportedOperation;
		if (affinityOrAntiAffinityGroup != null) this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
		if (monitoringParameter != null) this.monitoringParameter = monitoringParameter;
		if (scalingAspect != null) this.scalingAspect = scalingAspect;
	}
	
	

	/**
	 * @return the flavourId
	 */
	@JsonProperty("flavourId")
	public String getFlavourId() {
		return flavourId;
	}

	/**
	 * @return the description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * @return the vduProfile
	 */
	@JsonProperty("vduProfile")
	public List<VduProfile> getVduProfile() {
		return vduProfile;
	}

	/**
	 * @return the virtualLinkProfile
	 */
	@JsonProperty("virtualLinkProfile")
	public List<VirtualLinkProfile> getVirtualLinkProfile() {
		return virtualLinkProfile;
	}

	/**
	 * @return the instantiationLevel
	 */
	@JsonProperty("instantiationLevel")
	public List<InstantiationLevel> getInstantiationLevel() {
		return instantiationLevel;
	}

	/**
	 * @return the defaultInstantiationLevelId
	 */
	@JsonProperty("defaultInstantiationLevelId")
	public String getDefaultInstantiationLevelId() {
		return defaultInstantiationLevelId;
	}

	/**
	 * @return the supportedOperation
	 */
	@JsonProperty("supportedOperation")
	public List<VnfLcmOperation> getSupportedOperation() {
		return supportedOperation;
	}

	/**
	 * @return the vnfLcmOperationsConfiguration
	 */
	@JsonProperty("vnfLcmOperationsConfiguration")
	public VnfLcmOperationsConfiguration getVnfLcmOperationsConfiguration() {
		return vnfLcmOperationsConfiguration;
	}

	/**
	 * @return the affinityOrAntiAffinityGroup
	 */
	@JsonProperty("affinityOrAntiAffinityGroup")
	public List<AffinityOrAntiAffinityGroup> getAffinityOrAntiAffinityGroup() {
		return affinityOrAntiAffinityGroup;
	}

	/**
	 * @return the monitoringParameter
	 */
	@JsonProperty("monitoringParameter")
	public List<MonitoringParameter> getMonitoringParameter() {
		return monitoringParameter;
	}

	/**
	 * @return the scalingAspect
	 */
	@JsonProperty("scalingAspect")
	public List<ScalingAspect> getScalingAspect() {
		return scalingAspect;
	}

	@JsonIgnore
	public InstantiationLevel getInstantiationLevel(String ilId) throws NotExistingEntityException {
		if (ilId == null) return getDefaultInstantiationLevel();
    	for (InstantiationLevel il : instantiationLevel) {
    		if (il.getLevelId().equals(ilId)) return il;
    	}
    	throw new NotExistingEntityException("Instantiation level with ID " + ilId + " not found.");
    }
	
	@JsonIgnore
	public InstantiationLevel getDefaultInstantiationLevel() throws NotExistingEntityException {
		return getInstantiationLevel(defaultInstantiationLevelId);
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (flavourId == null) throw new MalformattedElementException("VNF DF without ID");
		if (description == null) throw new MalformattedElementException("VNF DF without description");
		if ((vduProfile == null) || (vduProfile.isEmpty())) {
			throw new MalformattedElementException("VNF DF without VDU profile");
		} else {
			for (VduProfile p : vduProfile) p.isValid();
		}
		for (VirtualLinkProfile p : virtualLinkProfile) p.isValid();
		if ((instantiationLevel == null) || (instantiationLevel.isEmpty())) {
			throw new MalformattedElementException("VNF DF without instantiation level");
		} else {
			for (InstantiationLevel i : instantiationLevel) i.isValid();
		}
		if (vnfLcmOperationsConfiguration == null) {
			throw new MalformattedElementException("VNF DF without VNF LCM operation configuration");
		} else vnfLcmOperationsConfiguration.isValid();
		for (AffinityOrAntiAffinityGroup a: affinityOrAntiAffinityGroup) a.isValid();
		for (MonitoringParameter mp : monitoringParameter) mp.isValid();
	}

}
