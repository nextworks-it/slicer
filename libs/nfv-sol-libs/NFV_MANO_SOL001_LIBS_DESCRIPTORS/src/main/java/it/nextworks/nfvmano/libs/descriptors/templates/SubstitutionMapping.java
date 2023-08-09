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
package it.nextworks.nfvmano.libs.descriptors.templates;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Entity
public class SubstitutionMapping implements DescriptorInformationElement {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	Long id;
	
	@OneToOne
	@JsonIgnore
	private TopologyTemplate topologyTemplate;
	
	private String nodeType;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "subMapping", cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VirtualLinkPairs> requirements = new ArrayList<>();

	public SubstitutionMapping() {
		
	}
	
	public SubstitutionMapping(String nodeType, List<VirtualLinkPairs> requirements) {
		this.nodeType = nodeType;
		this.requirements = requirements;
	}

	public SubstitutionMapping(TopologyTemplate topologyTemplate, String nodeType, List<VirtualLinkPairs> requirements) {
		this.topologyTemplate= topologyTemplate;
		this.nodeType = nodeType;
		this.requirements = requirements;
	}
		
	public Long getId() {
		return id;
	}

	public TopologyTemplate getTopologyTemplate() {
		return topologyTemplate;
	}

	@JsonProperty("nodeType")
	public String getNodeType() {
		return nodeType;
	}

	@JsonProperty("requirements")
	public List<VirtualLinkPairs> getRequirements() {
		return requirements;
	}

	@Override
	public void isValid() throws MalformattedElementException {

	}
}
