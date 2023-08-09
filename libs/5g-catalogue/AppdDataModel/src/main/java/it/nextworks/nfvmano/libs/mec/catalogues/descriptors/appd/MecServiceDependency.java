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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.elements.CategoryRef;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The ServiceDependency data type supports the specification of requirements 
 * of a service-consuming ME application related to a ME service.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.17
 * 
 * @author nextworks
 *
 */
@Entity
public class MecServiceDependency implements DescriptorInformationElement {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Appd appdRequired;
	
	@JsonIgnore
	@ManyToOne
	private Appd appdOptional;
	
	private String serName;
	
	@Embedded
	private CategoryRef serCategory;
	
	private String serviceVersion;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "msd", cascade= CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TransportDependency> serTransportDependencies = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> requestedPermissions = new ArrayList<>();
	
	public MecServiceDependency() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param appdRequired appd for which this service dependency is required
	 * @param appdOptional appd for which this service dependency is optional
	 * @param serName The name of the service, for example, RNIS, LocationService, etc.
	 * @param serCategory A Category reference of the service.
	 * @param serviceVersion The version of the service.
	 * @param requestedPermissions Requested permissions regarding the access of the application to the service.
	 */
	public MecServiceDependency(Appd appdRequired,
								Appd appdOptional,
								String serName,
								CategoryRef serCategory,
								String serviceVersion,
								List<String> requestedPermissions) {
		this.appdRequired = appdRequired;
		this.appdOptional = appdOptional;
		this.serName = serName;
		this.serCategory = serCategory;
		this.serviceVersion = serviceVersion;
		if (requestedPermissions != null) this.requestedPermissions = requestedPermissions;
	}
	
	

	/**
	 * @return the serName
	 */
	@JsonProperty("serName")
	public String getSerName() {
		return serName;
	}

	/**
	 * @return the serCategory
	 */
	@JsonProperty("serCategory")
	public CategoryRef getSerCategory() {
		return serCategory;
	}

	/**
	 * @return the version
	 */
	@JsonProperty("version")
	public String getServiceVersion() {
		return serviceVersion;
	}

	/**
	 * @return the serTransportDependencies
	 */
	@JsonProperty("serTransportDependencies")
	public List<TransportDependency> getSerTransportDependencies() {
		return serTransportDependencies;
	}

	/**
	 * @return the requestedPermissions
	 */
	@JsonProperty("requestedPermissions")
	public List<String> getRequestedPermissions() {
		return requestedPermissions;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (serName == null) throw new MalformattedElementException("MEC service dependency without service name.");
		if (serCategory != null) this.serCategory.isValid();
		if (serviceVersion == null) throw new MalformattedElementException("MEC service dependency without version.");
		for (TransportDependency td : serTransportDependencies) td.isValid();
	}
	
}
