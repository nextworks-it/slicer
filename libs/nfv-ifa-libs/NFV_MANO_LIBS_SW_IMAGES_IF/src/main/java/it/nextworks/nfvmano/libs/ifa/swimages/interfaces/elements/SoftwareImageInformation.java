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
package it.nextworks.nfvmano.libs.ifa.swimages.interfaces.elements;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element represents Software Image Information.
 * 
 * REF IFA 006 - v2.3.1 - 8.2.2
 * 
 * @author nextworks
 *
 */
public class SoftwareImageInformation implements InterfaceInformationElement {

	private String id;
	private String name;
	private String provider;
	private String version;
	private String checksum;
	private String containerFormat;
	private String diskFormat;
	private Date createdAt;
	private Date updatedAt;
	private int minDisk;
	private int minRam;
	private int size;
	private String status;
	private Map<String, String> userMetadata = new HashMap<>();
	
	public SoftwareImageInformation() {	}
	
	/**
	 * Constructor
	 * 
	 * @param id The identifier of this software image.
	 * @param name The name of this software image.
	 * @param provider The provider of this software image.
	 * @param version The version of this software image.
	 * @param checksum The checksum of the software image file.
	 * @param containerFormat The container format indicates whether the software image is in a file format that also contains metadata about the actual software.
	 * @param diskFormat The disk format of a software image is the format of the underlying disk image.
	 * @param createdAt The created time of this software image.
	 * @param updatedAt The updated time of this software image.
	 * @param minDisk The minimal Disk for this software image.
	 * @param minRam The minimal RAM for this software image.
	 * @param size The size of this software image.
	 * @param status The status of this software image.
	 * @param userMetadata User-defined metadata.
	 */
	public SoftwareImageInformation(String id,
			String name,
			String provider,
			String version,
			String checksum,
			String containerFormat,
			String diskFormat,
			Date createdAt,
			Date updatedAt,
			int minDisk,
			int minRam,
			int size,
			String status,
			Map<String, String> userMetadata) {	
		this.id = id;
		this.name = name;
		this.provider = provider;
		this.version = version;
		this.checksum = checksum;
		this.containerFormat = containerFormat;
		this.diskFormat = diskFormat;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.minDisk = minDisk;
		this.minRam = minRam;
		this.size = size;
		this.status = status;
		if (userMetadata != null) this.userMetadata = userMetadata;
	}

	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @return the containerFormat
	 */
	public String getContainerFormat() {
		return containerFormat;
	}

	/**
	 * @return the diskFormat
	 */
	public String getDiskFormat() {
		return diskFormat;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @return the minDisk
	 */
	public int getMinDisk() {
		return minDisk;
	}

	/**
	 * @return the minRam
	 */
	public int getMinRam() {
		return minRam;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the userMetadata
	 */
	public Map<String, String> getUserMetadata() {
		return userMetadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (id == null) throw new MalformattedElementException("Sw image info without ID.");
		if (name == null) throw new MalformattedElementException("Sw image info without name.");
		if (provider == null) throw new MalformattedElementException("Sw image info without provider.");
		if (version == null) throw new MalformattedElementException("Sw image info without version.");
		if (checksum == null) throw new MalformattedElementException("Sw image info without checksum.");
		if (containerFormat == null) throw new MalformattedElementException("Sw image info without containerFormat.");
		if (diskFormat == null) throw new MalformattedElementException("Sw image info without diskFormat.");
		if (status == null) throw new MalformattedElementException("Sw image info without status.");

	}

}
