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

/**
 * Ref. ETSI GS MEC 011 V1.1.1 (2017-07) - 6.5.2
 * 
 * @author nextworks
 *
 */
@Embeddable
public class CategoryRef implements DescriptorInformationElement {

	private String href;
	private String catRefId;
	private String name;
	private String version;

	public CategoryRef() {
		// JPA only
	}

	/**
	 * Constructor
	 * 
	 * @param href     Reference of the catalogue.
	 * @param catRefId Unique identifier of the category.
	 * @param name     Name of the category.
	 * @param version  Category version.
	 */
	public CategoryRef(String href, String catRefId, String name, String version) {
		this.href = href;
		this.catRefId = catRefId;
		this.name = name;
		this.version = version;
	}

	/**
	 * @return the href
	 */
	@JsonProperty("href")
	public String getHref() {
		return href;
	}

	/**
	 * @return the catRefId
	 */
	@JsonProperty("id")
	public String getCatRefId() {
		return catRefId;
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

	@Override
	public void isValid() throws MalformattedElementException {
		if (href == null)
			throw new MalformattedElementException("Category ref without href");
		if (catRefId == null)
			throw new MalformattedElementException("Category ref without id");
		if (name == null)
			throw new MalformattedElementException("Category ref without name");
		if (version == null)
			throw new MalformattedElementException("Category ref without version");
	}

}
