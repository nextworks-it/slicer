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
package it.nextworks.nfvmano.libs.ifa.descriptors.nsd;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The Pnfd information element is a deployment template 
 * enabling on-boarding PNFs and referencing them from an
 * NSD. It focuses on connectivity aspects only.
 * 
 * Ref. IFA 014 v2.3.1 - 6.6.2
 * 
 * @author nextworks
 *
 */
@Entity
public class Pnfd implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String pnfdId;
	private String provider;
	private String version;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "pnfd", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<PnfExtCpd> pnfExtCp = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Embedded
	private SecurityParameters security;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> configurableProperty = new ArrayList<>();	//this is not standard
	
	public Pnfd() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param pnfdId Identifier of this Pnfd information element. It uniquely identifies the PNFD.
	 * @param provider Identifies the provider of the PNFD.
	 * @param version Identifies the version of the PNFD.
	 * @param security Provides a signature to prevent tampering.
	 */
	public Pnfd(String pnfdId,
			String provider,
			String version,
			SecurityParameters security,
			List<String> configurableProperty) {
		this.pnfdId = pnfdId;
		this.provider = provider;
		this.version = version;
		this.security = security;
		if (configurableProperty != null) this.configurableProperty = configurableProperty;
	}
	
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the pnfdId
	 */
	@JsonProperty("pnfdId")
	public String getPnfdId() {
		return pnfdId;
	}

	/**
	 * @return the provider
	 */
	@JsonProperty("provider")
	public String getProvider() {
		return provider;
	}

	/**
	 * @return the version
	 */
	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	/**
	 * @return the pnfExtCp
	 */
	@JsonProperty("pnfExtCp")
	public List<PnfExtCpd> getPnfExtCp() {
		return pnfExtCp;
	}

	/**
	 * @return the security
	 */
	@JsonProperty("security")
	public SecurityParameters getSecurity() {
		return security;
	}
	
	/**
	 * @return the configurableProperty
	 */
	@JsonProperty("configurableProperty")
	public List<String> getConfigurableProperty() {
		return configurableProperty;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (pnfdId == null) throw new MalformattedElementException("PNFD without ID");
		if (provider == null) throw new MalformattedElementException("PNFD without provider");
		if (version == null) throw new MalformattedElementException("PNFD without version");
		if (security != null) security.isValid();
		if ((pnfExtCp == null) || (pnfExtCp.isEmpty())) {
			throw new MalformattedElementException("PNFD without external connection points");
		} else {
			for (PnfExtCpd cp : this.pnfExtCp) cp.isValid();
		}
	}

}
