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
package it.nextworks.nfvmano.libs.ifa.swimages.interfaces.messages;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.swimages.interfaces.enums.SwImageVisibility;

/**
 * Request to add a new image in the VIM
 * 
 * REF IFA 006 - v2.3.1 - 7.2.2
 * 
 * @author nextworks
 *
 */
public class AddImageRequest implements InterfaceMessage {

	private String name;
	private String provider;
	private String version;
	private Map<String, String> userMetadata = new HashMap<String, String>();
	private String softwareImage;
	private String resourceGroupId;
	private SwImageVisibility visibility;
	
	public AddImageRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param name The name of the software image.
	 * @param provider The provider of the software image.
	 * @param version The version of the software image.
	 * @param userMetadata User-defined metadata
	 * @param softwareImage The URL of the binary of the sw image
	 * @param resourceGroupId Unique identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
	 * @param visibility Controls the visibility of the image.
	 */
	public AddImageRequest(String name,
			String provider,
			String version,
			Map<String, String> userMetadata,
			String softwareImage,
			String resourceGroupId,
			SwImageVisibility visibility) {
		this.name = name;
		this.provider = provider;
		this.version = version;
		if (userMetadata != null) this.userMetadata = userMetadata;
		this.softwareImage = softwareImage;
		this.resourceGroupId = resourceGroupId;
		this.visibility = visibility;
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
	 * @return the userMetadata
	 */
	public Map<String, String> getUserMetadata() {
		return userMetadata;
	}

	/**
	 * @return the softwareImage
	 */
	public String getSoftwareImage() {
		return softwareImage;
	}

	/**
	 * @return the resourceGroupId
	 */
	public String getResourceGroupId() {
		return resourceGroupId;
	}

	/**
	 * @return the visibility
	 */
	public SwImageVisibility getVisibility() {
		return visibility;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (name == null) throw new MalformattedElementException("Add image request without image name.");
		if (provider == null) throw new MalformattedElementException("Add image request without provider.");
		if (version == null) throw new MalformattedElementException("Add image request without version.");
		if (softwareImage == null) throw new MalformattedElementException("Add image request without sw image.");
		if (resourceGroupId == null) throw new MalformattedElementException("Add image request without resource group Id.");
	}

}
