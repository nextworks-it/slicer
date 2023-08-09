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
package it.nextworks.nfvmano.libs.common.elements;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import java.security.Key;

@Embeddable
public class KeyValuePair implements DescriptorInformationElement {

	private String key;
	private String value;

	public KeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public KeyValuePair() {
	}

	@JsonProperty("key")
	public String getKey() {
		return key;
	}

	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		
	}

	@Override
	public boolean equals(Object o) {
		KeyValuePair object = (KeyValuePair) o;
		return this.key.equals(object.key) && this.value.equals(object.value);
	}
}
