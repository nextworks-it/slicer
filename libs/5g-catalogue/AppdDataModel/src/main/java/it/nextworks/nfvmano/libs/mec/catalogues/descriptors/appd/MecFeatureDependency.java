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
 * The FeatureDependency data type supports the specification of requirements 
 * of a ME application related to a feature of ME platform.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.8
 * 
 * @author nextworks
 *
 */
@Embeddable
public class MecFeatureDependency implements DescriptorInformationElement {
	
	private String featureName;
	private String version;

	public MecFeatureDependency() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param featureName The name of the feature, for example, UserApps, UEIdentity, etc.
	 * @param version The version of the feature.
	 */
	public MecFeatureDependency(String featureName, String version) {
		this.featureName = featureName;
		this.version = version;
	}
	
	

	/**
	 * @return the featureName
	 */
	@JsonProperty("featureName")
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * @return the version
	 */
	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (featureName == null) throw new MalformattedElementException("MEC feature dependency without name");
		if (version == null) throw new MalformattedElementException("MEC feature dependency without version");
	}
}
