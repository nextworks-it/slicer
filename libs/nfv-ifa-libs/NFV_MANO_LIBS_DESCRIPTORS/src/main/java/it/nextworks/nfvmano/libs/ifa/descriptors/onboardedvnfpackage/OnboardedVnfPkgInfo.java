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
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.enums.UsageState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.Vnfd;


/**
 * This information element provides the details of an on-boarded VNF Package, 
 * which the NFVO creates and stores as part of the on-boarding and ongoing 
 * operational management process.
 * 
 *  REF IFA 013 v2.3.1 - 8.6.2
 * 
 * @author nextworks
 *
 */
@Entity
public class OnboardedVnfPkgInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String onboardedVnfPkgInfoId;
	private String vnfdId;
	private String vnfProvider;
	private String vnfProductName;
	private String vnfSoftwareVersion;
	private String vnfdVersion;
	private String checksum;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "onboardedVnfPkgInfo", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Vnfd vnfd;
	
	@OneToMany(mappedBy = "vnfPkgInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VnfPackageSoftwareImageInformation> softwareImage = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<VnfPackageArtifactInformation> additionalArtifact = new ArrayList<>();
	
	private OperationalState operationalState;
	private UsageState usageState;
	private boolean deletionPending;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> userDefinedData = new HashMap<>();
	
	@JsonIgnore
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> vnfId = new ArrayList<>();
	
	public OnboardedVnfPkgInfo() {	}
	
	/**
	 * Constructor
	 * 
	 * @param onboardedVnfPkgInfoId Identifier of information held by the NFVO about the specific on-boarded VNF Package. This is allocated by the NFVO.
	 * @param vnfdId Identifier that identifies the VNF Package.
	 * @param vnfProvider VNF provider
	 * @param vnfProductName VNF product name
	 * @param vnfSoftwareVersion VNF software version
	 * @param vnfdVersion VNFD version 
	 * @param checksum Checksum of the on-boarded VNF Package.
	 * @param vnfd VNFD contained in the on-boarded VNF Package.
	 * @param softwareImage Information about VNF Package artifacts that are software images.
	 * @param additionalArtifact Information about VNF Package artifacts contained in the VNF Package that are not software images.
	 * @param operationalState Operational state of the on-boarded instance of the VNF Package.
	 * @param usageState Usage state of the on-boarded instance of the VNF Package.
	 * @param deletionPending Indicates if deletion of this instance of the VNF Package has been requested  but the VNF Package is still being used by instantiated VNFs.
	 * @param userDefinedData User defined data for the VNF Package.
	 */
	public OnboardedVnfPkgInfo(String onboardedVnfPkgInfoId,
			String vnfdId,
			String vnfProvider,
			String vnfProductName,
			String vnfSoftwareVersion,
			String vnfdVersion,
			String checksum,
			Vnfd vnfd,
			List<VnfPackageSoftwareImageInformation> softwareImage,
			List<VnfPackageArtifactInformation> additionalArtifact,
			OperationalState operationalState,
			UsageState usageState,
			boolean deletionPending,
			Map<String, String> userDefinedData) {	
		this.onboardedVnfPkgInfoId = onboardedVnfPkgInfoId;
		this.vnfdId = vnfdId;
		this.vnfProvider = vnfProvider;
		this.vnfProductName = vnfProductName;
		this.vnfSoftwareVersion = vnfSoftwareVersion;
		this.vnfdVersion = vnfdVersion;
		this.checksum = checksum;
		this.vnfd = vnfd;
		if (softwareImage != null) this.softwareImage = softwareImage;
		if (additionalArtifact != null) this.additionalArtifact = additionalArtifact;
		this.operationalState = operationalState;
		this.usageState = usageState;
		this.deletionPending = deletionPending;
		if (userDefinedData != null) this.userDefinedData = userDefinedData;
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param onboardedVnfPkgInfoId Identifier of information held by the NFVO about the specific on-boarded VNF Package. This is allocated by the NFVO.
	 * @param vnfdId Identifier that identifies the VNF Package.
	 * @param vnfProvider VNF provider
	 * @param vnfProductName VNF product name
	 * @param vnfSoftwareVersion VNF software version
	 * @param vnfdVersion VNFD version 
	 * @param checksum Checksum of the on-boarded VNF Package.
	 * @param additionalArtifact Information about VNF Package artifacts contained in the VNF Package that are not software images.
	 * @param operationalState Operational state of the on-boarded instance of the VNF Package.
	 * @param usageState Usage state of the on-boarded instance of the VNF Package.
	 * @param deletionPending Indicates if deletion of this instance of the VNF Package has been requested  but the VNF Package is still being used by instantiated VNFs.
	 * @param userDefinedData User defined data for the VNF Package.
	 */
	public OnboardedVnfPkgInfo(String onboardedVnfPkgInfoId,
			String vnfdId,
			String vnfProvider,
			String vnfProductName,
			String vnfSoftwareVersion,
			String vnfdVersion,
			String checksum,
			List<VnfPackageArtifactInformation> additionalArtifact,
			OperationalState operationalState,
			UsageState usageState,
			boolean deletionPending,
			Map<String, String> userDefinedData) {	
		this.onboardedVnfPkgInfoId = onboardedVnfPkgInfoId;
		this.vnfdId = vnfdId;
		this.vnfProvider = vnfProvider;
		this.vnfProductName = vnfProductName;
		this.vnfSoftwareVersion = vnfSoftwareVersion;
		this.vnfdVersion = vnfdVersion;
		this.checksum = checksum;
		if (additionalArtifact != null) this.additionalArtifact = additionalArtifact;
		this.operationalState = operationalState;
		this.usageState = usageState;
		this.deletionPending = deletionPending;
		if (userDefinedData != null) this.userDefinedData = userDefinedData;
	}
	
	/**
	 * 
	 * @return the Id
	 */
	@JsonIgnore
	public Long getId() {
		return id;
	}
	
	/**
	 * @return the onboardedVnfPkgInfoId
	 */
	public String getOnboardedVnfPkgInfoId() {
		return onboardedVnfPkgInfoId;
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
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @return the vnfd
	 */
	public Vnfd getVnfd() {
		return vnfd;
	}

	/**
	 * @return the softwareImage
	 */
	public List<VnfPackageSoftwareImageInformation> getSoftwareImage() {
		return softwareImage;
	}

	/**
	 * @return the additionalArtifact
	 */
	public List<VnfPackageArtifactInformation> getAdditionalArtifact() {
		return additionalArtifact;
	}

	/**
	 * @return the operationalState
	 */
	public OperationalState getOperationalState() {
		return operationalState;
	}

	/**
	 * @return the usageState
	 */
	public UsageState getUsageState() {
		return usageState;
	}

	/**
	 * @return the deletionPending
	 */
	public boolean isDeletionPending() {
		return deletionPending;
	}

	/**
	 * @return the userDefinedData
	 */
	public Map<String, String> getUserDefinedData() {
		return userDefinedData;
	}
	
	

	/**
	 * @param onboardedVnfPkgInfoId the onboardedVnfPkgInfoId to set
	 */
	public void setOnboardedVnfPkgInfoId(String onboardedVnfPkgInfoId) {
		this.onboardedVnfPkgInfoId = onboardedVnfPkgInfoId;
	}

	
	
	/**
	 * @param operationalState the operationalState to set
	 */
	public void setOperationalState(OperationalState operationalState) {
		this.operationalState = operationalState;
	}

	/**
	 * @param usageState the usageState to set
	 */
	public void setUsageState(UsageState usageState) {
		this.usageState = usageState;
	}

	/**
	 * @param deletionPending the deletionPending to set
	 */
	public void setDeletionPending(boolean deletionPending) {
		this.deletionPending = deletionPending;
	}
	
	/**
	 * Adds a new VNF instance
	 * 
	 * @param vnfId ID of the VNF instantiated for the given VNF package
	 */
	public void addVnfInstance(String vnfId) {
		this.vnfId.add(vnfId);
	}
	
	/**
	 * Remove a VNF instance
	 * 
	 * @param vnfId ID of the VNF to be removed from the list of VNFs instantiated for the given VNF package
	 */
	public void removeVnfInstance(String vnfId) {
		this.vnfId.remove(vnfId);
	}
	
	

	/**
	 * @return the vnfId
	 */
	public List<String> getVnfId() {
		return vnfId;
	}

	/**
	 * 
	 * @return true if the VNF package can be used to instantiate a new VNF
	 */
	public boolean canBeInstantiated() {
		boolean result = true;
		if (operationalState == OperationalState.DISABLED) result = false;
		if (isDeletionPending()) result = false;
		return result;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (onboardedVnfPkgInfoId == null) throw new MalformattedElementException("Onboarded VNF package info without ID");
		if (vnfdId == null) throw new MalformattedElementException("Onboarded VNF package info without VNFD ID");
		if (vnfProvider == null) throw new MalformattedElementException("Onboarded VNF package info without VNF provider");
		if (vnfProductName == null) throw new MalformattedElementException("Onboarded VNF package info without VNF product name");
		if (vnfSoftwareVersion == null) throw new MalformattedElementException("Onboarded VNF package info without VNF sw version");
		if (vnfdVersion == null) throw new MalformattedElementException("Onboarded VNF package info without VNFD version");
		if (checksum == null) throw new MalformattedElementException("Onboarded VNF package info without checksum");
		if (vnfd == null) {
			throw new MalformattedElementException("Onboarded VNF package info without VNFD");
		} else {
			vnfd.isValid();
		}
		if ((softwareImage == null) || (softwareImage.isEmpty())) {
			throw new MalformattedElementException("Onboarded VNF package info without software images");
		} else {
			for (VnfPackageSoftwareImageInformation sw : softwareImage) sw.isValid();
		}
		if (additionalArtifact != null) {
			for (VnfPackageArtifactInformation a : additionalArtifact) a.isValid();
		}
	}

}
