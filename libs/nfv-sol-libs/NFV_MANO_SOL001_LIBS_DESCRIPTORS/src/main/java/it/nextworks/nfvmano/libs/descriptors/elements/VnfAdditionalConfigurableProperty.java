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
package it.nextworks.nfvmano.libs.descriptors.elements;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Embeddable
public class VnfAdditionalConfigurableProperty implements DescriptorInformationElement {
	
	private String property;

	public VnfAdditionalConfigurableProperty() {

	}

	public VnfAdditionalConfigurableProperty(String property) {
		this.property = property;
	}

	@JsonProperty("property")
	public String getProperty() {
		return property;
	}

	@Override
	public void isValid() throws MalformattedElementException {

	}
}
