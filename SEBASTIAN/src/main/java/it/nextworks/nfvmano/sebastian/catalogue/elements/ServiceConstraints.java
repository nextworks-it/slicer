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
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Entity
public class ServiceConstraints implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	VsDescriptor vsd;
	
	private boolean sharable;
	private boolean canIncludeSharedElements;
	
	//low prio services can be terminated if no resources are available
	//medium prio services can be scaled down if no resources are available
	private ServicePriorityLevel priority;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> preferredProviders = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> nonPreferredProviders = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> prohibitedProviders = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private String atomicComponentId;
	
	
	public ServiceConstraints() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vsd VSD this service constraints belongs to
	 * @param sharable true if the service can become part of another service
	 * @param canIncludeSharedElements if the service can include components shared with other services
	 * @param priority used to define the behaviour in case of resource shortage
	 * @param preferredProviders preferred infrastructure providers
	 * @param nonPreferredProviders non preferred infrastructure providers
	 * @param prohibitedProviders prohibited infrastructure providers
	 * @param atomicComponentId ID of the atomic component these constraints are referred to. If omitted, it refers to all the service.
	 */
	public ServiceConstraints(VsDescriptor vsd,
			boolean sharable,
			boolean canIncludeSharedElements,
			ServicePriorityLevel priority,
			List<String> preferredProviders,
			List<String> nonPreferredProviders,
			List<String> prohibitedProviders,
			String atomicComponentId) {
		this.vsd = vsd;
		this.sharable = sharable;
		this.canIncludeSharedElements = canIncludeSharedElements;
		this.priority = priority;
		if (preferredProviders != null) this.preferredProviders = preferredProviders;
		if (nonPreferredProviders != null) this.nonPreferredProviders = nonPreferredProviders;
		if (prohibitedProviders != null) this.prohibitedProviders = prohibitedProviders;
		this.atomicComponentId = atomicComponentId;
	}

	
	
	/**
	 * @return the vsd
	 */
	public VsDescriptor getVsd() {
		return vsd;
	}

	/**
	 * @return the sharable
	 */
	public boolean isSharable() {
		return sharable;
	}

	/**
	 * @return the canIncludeSharedElements
	 */
	public boolean isCanIncludeSharedElements() {
		return canIncludeSharedElements;
	}

	/**
	 * @return the priority
	 */
	public ServicePriorityLevel getPriority() {
		return priority;
	}

	/**
	 * @return the preferredProviders
	 */
	public List<String> getPreferredProviders() {
		return preferredProviders;
	}

	/**
	 * @return the nonPreferredProviders
	 */
	public List<String> getNonPreferredProviders() {
		return nonPreferredProviders;
	}

	/**
	 * @return the prohibitedProviders
	 */
	public List<String> getProhibitedProviders() {
		return prohibitedProviders;
	}
	
	

	/**
	 * @return the atomicComponentId
	 */
	public String getAtomicComponentId() {
		return atomicComponentId;
	}

	@Override
	public void isValid() throws MalformattedElementException { }

}
