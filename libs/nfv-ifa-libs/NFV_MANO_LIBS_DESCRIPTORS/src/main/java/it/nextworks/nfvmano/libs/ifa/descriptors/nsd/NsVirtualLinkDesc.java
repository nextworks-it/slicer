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
import javax.persistence.ManyToOne;
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
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.ConnectivityType;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualLinkDf;

/**
 * The NsVirtualLinkDesc information element provides general 
 * information enabling the instantiation of virtual links.
 * 
 * Ref. IFA 014 v2.3.1 - 6.5.2
 * 
 * @author nextworks
 *
 */
@Entity
public class NsVirtualLinkDesc implements DescriptorInformationElement{

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Nsd nsd;
	
	private String virtualLinkDescId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String virtualLinkDescProvider;
	
	private String virtuaLinkDescVersion;
	
	@Embedded
	private ConnectivityType connectivityType;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "nsVld", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<VirtualLinkDf> virtualLinkDf = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> testAccess = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String description;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Embedded
	private SecurityParameters security;
	
	public NsVirtualLinkDesc() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsd NSD this VLD belongs to
	 * @param virtualLinkDescId Identifier of the NsVirtualLinkDesc information element. It uniquely identifies a VLD.
	 * @param virtualLinkDescProvider Defines the organization generating the VLD.
	 * @param virtuaLinkDescVersion Specifies the version of the VLD.
	 * @param connectivityType type of VLD connectivity
	 * @param testAccess Specifies test access facilities expected on the VL
	 * @param description Provides human-readable information on the purpose of the virtual link
	 * @param security Provides a signature to prevent tampering.
	 */
	public NsVirtualLinkDesc(Nsd nsd,
			String virtualLinkDescId,
			String virtualLinkDescProvider,
			String virtuaLinkDescVersion,
			ConnectivityType connectivityType,
			List<String> testAccess,
			String description,
			SecurityParameters security) {
		this.nsd = nsd;
		this.virtualLinkDescId = virtualLinkDescId;
		this.virtualLinkDescProvider = virtualLinkDescProvider;
		this.virtuaLinkDescVersion = virtuaLinkDescVersion;
		this.connectivityType = connectivityType;
		if (testAccess != null) this.testAccess = testAccess;
		this.description = description;
		this.security = security;
	}
	
	
	
	/**
	 * @return the virtualLinkDescId
	 */
	@JsonProperty("virtualLinkDescId")
	public String getVirtualLinkDescId() {
		return virtualLinkDescId;
	}

	/**
	 * @return the virtualLinkDescProvider
	 */
	@JsonProperty("virtualLinkDescProvider")
	public String getVirtualLinkDescProvider() {
		return virtualLinkDescProvider;
	}

	/**
	 * @return the virtuaLinkDescVersion
	 */
	@JsonProperty("virtuaLinkDescVersion")
	public String getVirtuaLinkDescVersion() {
		return virtuaLinkDescVersion;
	}

	/**
	 * @return the connectivityType
	 */
	@JsonProperty("connectivityType")
	public ConnectivityType getConnectivityType() {
		return connectivityType;
	}

	/**
	 * @return the virtualLinkDf
	 */
	@JsonProperty("virtualLinkDf")
	public List<VirtualLinkDf> getVirtualLinkDf() {
		return virtualLinkDf;
	}

	/**
	 * @return the testAccess
	 */
	@JsonProperty("testAccess")
	public List<String> getTestAccess() {
		return testAccess;
	}

	/**
	 * @return the description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * @return the security
	 */
	@JsonProperty("security")
	public SecurityParameters getSecurity() {
		return security;
	}
	
	@JsonIgnore
	public void addDeploymentFlavour(VirtualLinkDf df) {
		this.virtualLinkDf.add(df);
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.virtualLinkDescId == null) throw new MalformattedElementException("VLD without ID");
		if (this.virtuaLinkDescVersion == null) throw new MalformattedElementException("VLD without version");
		if ((this.virtualLinkDf == null) || (this.virtualLinkDf.isEmpty())) {
			throw new MalformattedElementException("VLD without deployment flavour");
		} else {
			for (VirtualLinkDf df : this.virtualLinkDf) df.isValid();
		}
		if (this.security != null) this.security.isValid();
		if (this.connectivityType == null) {
			throw new MalformattedElementException("VLD without connectivity type");
		} else this.connectivityType.isValid();
	}

	public void setNsd(Nsd nsd) {
		this.nsd = nsd;
	}

	public void setVirtualLinkDescId(String virtualLinkDescId) {
		this.virtualLinkDescId = virtualLinkDescId;
	}

	public void setVirtualLinkDescProvider(String virtualLinkDescProvider) {
		this.virtualLinkDescProvider = virtualLinkDescProvider;
	}

	public void setVirtuaLinkDescVersion(String virtuaLinkDescVersion) {
		this.virtuaLinkDescVersion = virtuaLinkDescVersion;
	}

	public void setConnectivityType(ConnectivityType connectivityType) {
		this.connectivityType = connectivityType;
	}

	public void setVirtualLinkDf(List<VirtualLinkDf> virtualLinkDf) {
		this.virtualLinkDf = virtualLinkDf;
	}

	public void setTestAccess(List<String> testAccess) {
		this.testAccess = testAccess;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSecurity(SecurityParameters security) {
		this.security = security;
	}
}
