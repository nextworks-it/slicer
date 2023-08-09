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
package it.nextworks.nfvmano.libs.ifa.records.vnfinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.InstantiationState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VimConnectionInfo;

/**
 * Class modelling the information associated to a VNF instance.
 * 
 * REF IFA 007 v2.3.1 - 8.5.2
 * 
 * @author nextworks
 *
 */
@Entity
public class VnfInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String vnfInstanceId;
	private String vnfInstanceName;
	private String vnfInstanceDescription;
	private String vnfdId;
	private String vnfProvider;
	private String vnfProductName;
	private String vnfSoftwareVersion;
	private String vnfdVersion;
	private String onboardedVnfPkgInfoId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> vnfConfigurableProperty = new HashMap<>();
	
	@OneToMany(mappedBy = "vnfInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<VimConnectionInfo> vimConnectionInfo = new ArrayList<>();
	
	private InstantiationState instantiationState;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "vnfInfo", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private InstantiatedVnfInfo instantiatedVnfInfo;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> metadata = new HashMap<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> extension = new HashMap<>();
	
	public VnfInfo() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance that is represented by this VnfInfo information element.
	 * @param vnfInstanceName VNF instance name.
	 * @param vnfInstanceDescription Human-readable description of the VNF instance.
	 * @param vnfdId Identifier of the VNFD on which the VNF instance is based.
	 * @param vnfProvider This information is copied from the VNFD
	 * @param vnfProductName This information is copied from the VNFD
	 * @param vnfSoftwareVersion This information is copied from the VNFD
	 * @param vnfdVersion This information is copied from the VNFD
	 * @param onboardedVnfPkgInfoId Identifier of information held by the NFVO about the specific VNF Package on which the VNF is based. This identifier was allocated by the NFVO. 
	 * @param vnfConfigurableProperty Current values of the configurable properties of the VNF instance.
	 * @param vimConnectionInfo Information about VIM connection(s) for managing resources for the VNF instance.
	 * @param instantiationState The instantiation state of the VNF.
	 * @param metadata Additional metadata describing the VNF instance.
	 * @param extension VNF-specific attributes that affect the lifecycle management of this VNF instance by the VNFM, or the lifecycle management scripts.
	 */
	public VnfInfo(String vnfInstanceId,
			String vnfInstanceName,
			String vnfInstanceDescription,
			String vnfdId,
			String vnfProvider,
			String vnfProductName,
			String vnfSoftwareVersion,
			String vnfdVersion,
			String onboardedVnfPkgInfoId,
			Map<String, String> vnfConfigurableProperty,
			List<VimConnectionInfo> vimConnectionInfo,
			InstantiationState instantiationState,
			Map<String, String> metadata,
			Map<String, String> extension) {
		this.vnfInstanceId = vnfInstanceId;
		this.vnfInstanceName = vnfInstanceName;
		this.vnfInstanceDescription = vnfInstanceDescription;
		this.vnfdId = vnfdId;
		this.vnfProvider = vnfProvider;
		this.vnfProductName = vnfProductName;
		this.vnfSoftwareVersion = vnfSoftwareVersion;
		this.vnfdVersion = vnfdVersion;
		this.onboardedVnfPkgInfoId = onboardedVnfPkgInfoId;
		if (vnfConfigurableProperty != null) this.vnfConfigurableProperty = vnfConfigurableProperty;
		this.instantiationState = instantiationState;
		if (metadata != null) this.metadata = metadata;
	}
	
	

	/**
	 * @return the id
	 */
	@JsonIgnore
	public Long getId() {
		return id;
	}

	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the vnfInstanceName
	 */
	public String getVnfInstanceName() {
		return vnfInstanceName;
	}

	/**
	 * @return the vnfInstanceDescription
	 */
	public String getVnfInstanceDescription() {
		return vnfInstanceDescription;
	}

	/**
	 * @return the vnfdId
	 */
	public String getVnfdId() {
		return vnfdId;
	}

	/**
	 * @return the vnfProvider
	 */
	public String getVnfProvider() {
		return vnfProvider;
	}

	/**
	 * @return the vnfProductName
	 */
	public String getVnfProductName() {
		return vnfProductName;
	}

	/**
	 * @return the vnfSoftwareVersion
	 */
	public String getVnfSoftwareVersion() {
		return vnfSoftwareVersion;
	}

	/**
	 * @return the vnfdVersion
	 */
	public String getVnfdVersion() {
		return vnfdVersion;
	}

	/**
	 * @return the onboardedVnfPkgInfoId
	 */
	public String getOnboardedVnfPkgInfoId() {
		return onboardedVnfPkgInfoId;
	}

	/**
	 * @return the vnfConfigurableProperty
	 */
	public Map<String, String> getVnfConfigurableProperty() {
		return vnfConfigurableProperty;
	}

	/**
	 * @return the instantiationState
	 */
	public InstantiationState getInstantiationState() {
		return instantiationState;
	}

	/**
	 * @return the instantiatedVnfInfo
	 */
	public InstantiatedVnfInfo getInstantiatedVnfInfo() {
		return instantiatedVnfInfo;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}
	
	/**
	 * @return the extension
	 */
	public Map<String, String> getExtension() {
		return extension;
	}

	/**
	 * @return the vimConnectionInfo
	 */
	public List<VimConnectionInfo> getVimConnectionInfo() {
		return vimConnectionInfo;
	}

	/**
	 * @param vnfInstanceId the vnfInstanceId to set
	 */
	public void setVnfInstanceId(String vnfInstanceId) {
		this.vnfInstanceId = vnfInstanceId;
	}

	/**
	 * @param instantiationState the instantiationState to set
	 */
	public void setInstantiationState(InstantiationState instantiationState) {
		this.instantiationState = instantiationState;
	}
	
	/**
	 * Adds a configurable parameter to the VNF info
	 * 
	 * @param paramType type of the configurable parameter
	 * @param paramValue value of the configurable parameter
	 */
	@JsonIgnore
	public void addConfigurableParameter(String paramType, String paramValue) {
		this.vnfConfigurableProperty.put(paramType, paramValue);
	}
	
	

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("VNF info without VNF instance ID");
		if (vnfdId == null) throw new MalformattedElementException("VNF info without VNFD ID");
		if (vnfProvider == null) throw new MalformattedElementException("VNF info without VNF provider");
		if (vnfProductName == null) throw new MalformattedElementException("VNF info without VNF product name");
		if (vnfSoftwareVersion == null) throw new MalformattedElementException("VNF info without VNF sw version");
		if (vnfdVersion == null) throw new MalformattedElementException("VNF info without VNFD version");
		if (onboardedVnfPkgInfoId == null) throw new MalformattedElementException("VNF info without onboarded pkg info ID");
		if (instantiatedVnfInfo != null) instantiatedVnfInfo.isValid();
		for (VimConnectionInfo vci : vimConnectionInfo) vci.isValid();
	}

}
