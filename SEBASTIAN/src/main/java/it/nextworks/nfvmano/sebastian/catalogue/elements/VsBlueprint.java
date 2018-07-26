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
package it.nextworks.nfvmano.sebastian.catalogue.elements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Entity
public class VsBlueprint implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String vsBlueprintId;
	private String version;
	private String name;
	private String description;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imgUrl;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<VsBlueprintParameter> parameters = new ArrayList<>();
	
	//TODO: add further fields
	
	public VsBlueprint() { }

	public VsBlueprint(String vsBlueprinId,
			String version,
			String name,
			String description,
			String imgUrl,
			List<VsBlueprintParameter> parameters) {
		this.vsBlueprintId = vsBlueprinId;
		this.version = version;
		this.name = name;
		this.description = description;
		this.imgUrl = imgUrl;
		if (parameters != null) this.parameters = parameters;
	}

	
	/**
	 * @return the vsBlueprintId
	 */
	public String getVsBlueprintId() {
		return vsBlueprintId;
	}

	/**
	 * @param vsBlueprintId the vsBlueprintId to set
	 */
	public void setVsBlueprintId(String vsBlueprintId) {
		this.vsBlueprintId = vsBlueprintId;
	}
	
	

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the parameters
	 */
	public List<VsBlueprintParameter> getParameters() {
		return parameters;
	}

	/**
	 *
	 * @return the URL of the image describing the VSB
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (VsBlueprintParameter p : parameters) p.isValid();
		if (version == null) throw new MalformattedElementException("VS blueprint without version");
		if (name == null) throw new MalformattedElementException("VS blueprint without name");
	}

}
