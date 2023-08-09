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

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides identification information 
 * for artifacts (other than Software Images) which are contained in the VNF Package.
 * 
 * REF IFA 013 v2.3.1 - 8.6.6
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VnfPackageArtifactInformation implements DescriptorInformationElement {

	private String selector;
	private String metadata;
	
	public VnfPackageArtifactInformation() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param selector Information that identifies this artifact in the VNF Package.
	 * @param metadata Information (The metadata of the artifact that are available in the VNF Package, such as Content type, size, creation date, etc.) that allows to access a copy of this VNF Package artifact.
	 */
	public VnfPackageArtifactInformation(String selector,
			String metadata) {
		this.selector = selector;
		this.metadata = metadata;
	}
	
	

	/**
	 * @return the selector
	 */
	public String getSelector() {
		return selector;
	}

	/**
	 * @return the metadata
	 */
	public String getMetadata() {
		return metadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (selector == null) throw new MalformattedElementException("VNF package artifact information without selector");
		if (metadata == null) throw new MalformattedElementException("VNF package artifact information without metadata");
	}

}
