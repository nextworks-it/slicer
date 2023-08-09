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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The ServiceDescriptor data type describes a ME service produced by a service-providing ME application.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.7
 * 
 * @author nextworks
 *
 */
@Entity
public class MecServiceDescriptor implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Appd appd;
	
	private String serName;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Embedded
	private CategoryRef serCategory;
	
	private String serVersion;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "msd", cascade= CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MecServiceTransport> transportsSupported = new ArrayList<>();
	
	public MecServiceDescriptor() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param appd The AppD this MEC service descriptor belongs to.
	 * @param serName The name of the service, for example, RNIS, LocationService, etc.
	 * @param serCategory A Category reference of the service.
	 * @param version The version of the service.
	 */
	public MecServiceDescriptor(Appd appd,
			String serName,
			CategoryRef serCategory,
			String version) {
		this.appd = appd;
		this.serName = serName;
		this.serCategory = serCategory;
		this.serVersion = version;
	}
	
	

	
	/**
	 * @return the serName
	 */
	public String getSerName() {
		return serName;
	}

	/**
	 * @return the serCategory
	 */
	public CategoryRef getSerCategory() {
		return serCategory;
	}

	/**
	 * @return the version
	 */
	@JsonProperty("version")
	public String getSerVersion() {
		return serVersion;
	}

	/**
	 * @return the transportsSupported
	 */
	public List<MecServiceTransport> getTransportsSupported() {
		return transportsSupported;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (serName == null) throw new MalformattedElementException("MEC service descriptor without name");
		if (serCategory != null) serCategory.isValid();
		if (serVersion == null) throw new MalformattedElementException("MEC service descriptor without version");
		for (MecServiceTransport mst : transportsSupported) mst.isValid();
	}
}
