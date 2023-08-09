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

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element represents Software Image Information.
 * 
 *  REF IFA 013 v2.3.1 - 8.6.5
 * 
 * @author nextworks
 *
 */
@Entity
public class SoftwareImageInformation implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="software_image_information_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private VnfPackageSoftwareImageInformation vnfPkgSwImage;
	
	private String softwareImageId;
	private String name;
	private String provider;
	private String version;
	private String checksum;
	private String containerFormat;
	private String diskFormat;
	private Date createdAt;
	
	// Removed from v2.3.1
	//private Date updatedAt;
	
	private int minDisk;
	private int minRam;
	private int size;
	
	// Removed from v2.3.1
	//private String status;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> userMetadata = new HashMap<String, String>();
	
	
	public SoftwareImageInformation() {
		//JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfPkgSwImage VNF package software image this software image belongs to
	 * @param softwareImageId The identifier of this software image.
	 * @param name The name of this software image.
	 * @param provider The provider of this software image.
	 * @param version The version of this software image.
	 * @param checksum The checksum of the software image file.
	 * @param containerFormat The container format indicates whether the software image is in a file format that also contains metadata about the actual software.
	 * @param diskFormat The disk format of a software image is the format of the underlying disk image.
	 * @param createdAt The created time of this software image.
	 * @param minDisk The minimal Disk for this software image.
	 * @param minRam The minimal RAM for this software image.
	 * @param size The size of this software image.
	 * @param userMetadata User-defined metadata.
	 */
	public SoftwareImageInformation(VnfPackageSoftwareImageInformation vnfPkgSwImage,
			String softwareImageId,
			String name,
			String provider,
			String version,
			String checksum,
			String containerFormat,
			String diskFormat,
			Date createdAt,
			//Date updatedAt,
			int minDisk,
			int minRam,
			int size,
			//String status,
			Map<String, String> userMetadata) {
		this.vnfPkgSwImage = vnfPkgSwImage;
		this.softwareImageId = softwareImageId;
		this.name = name;
		this.provider = provider;
		this.version = version;
		this.checksum = checksum;
		this.containerFormat = containerFormat;
		this.diskFormat = diskFormat;
		this.createdAt = createdAt;
		//this.updatedAt = updatedAt;
		this.minDisk = minDisk;
		this.minRam = minRam;
		this.size = size;
		//this.status = status;
		if (userMetadata != null) this.userMetadata = userMetadata;
	}
	
	

	/**
	 * @return the softwareImageId
	 */
	@JsonProperty("id")
	public String getSoftwareImageId() {
		return softwareImageId;
	}

	
	
	/**
	 * @return the name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * @return the provider
	 */
	@JsonProperty("provider")
	public String getProvider() {
		return provider;
	}

	/**
	 * @return the version
	 */
	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	/**
	 * @return the checksum
	 */
	@JsonProperty("checksum")
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @return the containerFormat
	 */
	@JsonProperty("containerFormat")
	public String getContainerFormat() {
		return containerFormat;
	}

	/**
	 * @return the diskFormat
	 */
	@JsonProperty("diskFormat")
	public String getDiskFormat() {
		return diskFormat;
	}

	/**
	 * @return the createdAt
	 */
	@JsonProperty("createdAt")
	public Date getCreatedAt() {
		return createdAt;
	}

	
	//Removed from v2.3.1
//	/**
//	 * @return the updatedAt
//	 */
//	@JsonProperty("updatedAt")
//	public Date getUpdatedAt() {
//		return updatedAt;
//	}

	/**
	 * @return the minDisk
	 */
	@JsonProperty("minDisk")
	public int getMinDisk() {
		return minDisk;
	}

	/**
	 * @return the minRam
	 */
	@JsonProperty("minRam")
	public int getMinRam() {
		return minRam;
	}

	/**
	 * @return the size
	 */
	@JsonProperty("size")
	public int getSize() {
		return size;
	}

//	Removed from v2.3.1	
//	/**
//	 * @return the status
//	 */
//	@JsonProperty("status")
//	public String getStatus() {
//		return status;
//	}

	/**
	 * @return the userMetadata
	 */
	@JsonProperty("userMetadata")
	public Map<String, String> getUserMetadata() {
		return userMetadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (softwareImageId == null) throw new MalformattedElementException("Sw image info without ID.");
		if (name == null) throw new MalformattedElementException("Sw image info without name.");
	}

}
