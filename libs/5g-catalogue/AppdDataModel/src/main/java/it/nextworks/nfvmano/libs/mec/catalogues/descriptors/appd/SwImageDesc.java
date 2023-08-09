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
package it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

/**
 * This information element describes requested additional capability 
 * for a particular VDU. Such a capability may be for acceleration or specific tasks.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.6.5
 * 
 * @author nextworks
 *
 */
@Embeddable
public class SwImageDesc implements DescriptorInformationElement {

	private String swImageId;
	private String name;
	private String version;
	private String checksum;
	private String containerFormat;
	private String diskFormat;
	private int minDisk;
	private int minRam;
	private int size;
	private String swImage;
	private String operatingSystem;
	private String supportedVirtualisationEnvironment;
	
	public SwImageDesc() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param swImageId The identifier of this software image.
	 * @param name The name of this software image.
	 * @param version The version of this software image.
	 * @param checksum The checksum of the software image file.
	 * @param containerFormat The container format describes the container file format in which software image is provided.
	 * @param diskFormat The disk format of a software image is the format of the underlying disk image.
	 * @param minDisk The minimal disk size requirement for this software image
	 * @param minRam The minimal RAM requirement for this software image.
	 * @param size The size of this software image.
	 * @param swImage This is a reference to the actual software image. The reference can be relative to the root of the VNF Package or can be a URL. 
	 * @param operatingSystem Identifies the operating system used in the software image. This attribute may also identify if a 32 bit or 64 bit software image is used.
	 * @param supportedVirtualisationEnvironment Identifies the virtualisation environments (e.g. hypervisor) compatible with this software image.
	 */
	public SwImageDesc(String swImageId,
			String name,
			String version,
			String checksum,
			String containerFormat,
			String diskFormat,
			int minDisk,
			int minRam,
			int size,
			String swImage,
			String operatingSystem,
			String supportedVirtualisationEnvironment) {
		this.swImageId = swImageId;
		this.name = name;
		this.version = version;
		this.checksum = checksum;
		this.containerFormat = containerFormat;
		this.diskFormat = diskFormat;
		this.minDisk = minDisk;
		this.minRam = minRam;
		this.size = size;
		this.swImage = swImage;
		this.operatingSystem = operatingSystem;
		this. supportedVirtualisationEnvironment = supportedVirtualisationEnvironment;
	}
	
	

	/**
	 * @return the swImageId
	 */
	@JsonProperty("id")
	public String getSwImageId() {
		return swImageId;
	}

	/**
	 * @return the name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
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

	/**
	 * @return the swImage
	 */
	@JsonProperty("swImage")
	public String getSwImage() {
		return swImage;
	}

	/**
	 * @return the operatingSystem
	 */
	@JsonProperty("operatingSystem")
	public String getOperatingSystem() {
		return operatingSystem;
	}

	/**
	 * @return the supportedVirtualisationEnvironment
	 */
	@JsonProperty("supportedVirtualisationEnvironment")
	public String getSupportedVirtualisationEnvironment() {
		return supportedVirtualisationEnvironment;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (swImageId == null) throw new MalformattedElementException("Sw Image Descriptor without ID");
		if (name == null) throw new MalformattedElementException("Sw Image Descriptor without name");
		if (version == null) throw new MalformattedElementException("Sw Image Descriptor without version");
		if (checksum == null) throw new MalformattedElementException("Sw Image Descriptor without checksum");
		if (swImage == null) throw new MalformattedElementException("Sw Image Descriptor without sw image");
		if (minDisk <= 0) throw new MalformattedElementException("Sw Image Descriptor with a not valid value for minDisk");
		if (minRam <= 0) throw new MalformattedElementException("Sw Image Descriptor with a not valid value for minRam");
	}

}
