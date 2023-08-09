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
package it.nextworks.nfvmano.libs.ifa.descriptors.vnfd;

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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.MonitoringParameter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.ConnectivityType;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualLinkDf;

/**
 * The VnfVirtualLinkDesc information element supports providing information about an internal VNF VL.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.7.2
 * 
 * @author nextworks
 *
 */
@Entity
public class VnfVirtualLinkDesc implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	Vnfd vnfd;
	
	private String virtualLinkDescId;
	
	@Embedded
	private ConnectivityType connectivityType;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> testAccess = new ArrayList<>();
	
	private String description;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<MonitoringParameter> monitoringParameter = new ArrayList<>();
	
	@OneToMany(mappedBy = "vnfVld", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VirtualLinkDf> virtualLinkDescFlavour = new ArrayList<>();
	
	public VnfVirtualLinkDesc() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfd VNFD this VLD belongs to
	 * @param virtualLinkDescId Unique identifier of this internal VLD in VNFD.
	 * @param connectivityType Connectivity type
	 * @param testAccess Specifies test access facilities expected on the VL (e.g. none, passive monitoring, or active (intrusive) loopbacks at endpoints.
	 * @param description Provides human-readable information on the purpose of the VL (e.g. control plane traffic).
	 * @param monitoringParameter Defines the virtualised resources monitoring parameters on VLD level.
	 */
	public VnfVirtualLinkDesc(Vnfd vnfd,
			String virtualLinkDescId,
			ConnectivityType connectivityType,
			List<String> testAccess,
			String description,
			List<MonitoringParameter> monitoringParameter) {
		this.vnfd = vnfd;
		this.virtualLinkDescId = virtualLinkDescId;
		this.connectivityType = connectivityType;
		if (testAccess != null) this.testAccess = testAccess;
		this.description = description;
		if (monitoringParameter != null) this.monitoringParameter = monitoringParameter;
	}

	
	
	/**
	 * @return the virtualLinkDescId
	 */
	@JsonProperty("virtualLinkDescId")
	public String getVirtualLinkDescId() {
		return virtualLinkDescId;
	}

	/**
	 * @return the connectivityType
	 */
	@JsonProperty("connectivityType")
	public ConnectivityType getConnectivityType() {
		return connectivityType;
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
	 * @return the monitoringParameter
	 */
	@JsonProperty("monitoringParameter")
	public List<MonitoringParameter> getMonitoringParameter() {
		return monitoringParameter;
	}
	
	

	/**
	 * @return the virtualLinkDescFlavour
	 */
	@JsonProperty("virtualLinkDescFlavour")
	public List<VirtualLinkDf> getVirtualLinkDescFlavour() {
		return virtualLinkDescFlavour;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (virtualLinkDescId == null) throw new MalformattedElementException("VNF VLD without ID");
		if (connectivityType == null) throw new MalformattedElementException("VNF VLD without connectivity type"); 
			else connectivityType.isValid();
		for (MonitoringParameter mp : monitoringParameter) mp.isValid();
		if ((virtualLinkDescFlavour == null) || (virtualLinkDescFlavour.isEmpty())) {
			throw new MalformattedElementException("VNF VLD without deployment flavour");
		} else {
			for (VirtualLinkDf df : virtualLinkDescFlavour) df.isValid(); 
		}
	}

	public void setVnfd(Vnfd vnfd) {
		this.vnfd = vnfd;
	}

	public void setVirtualLinkDescId(String virtualLinkDescId) {
		this.virtualLinkDescId = virtualLinkDescId;
	}

	public void setConnectivityType(ConnectivityType connectivityType) {
		this.connectivityType = connectivityType;
	}

	public void setTestAccess(List<String> testAccess) {
		this.testAccess = testAccess;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMonitoringParameter(List<MonitoringParameter> monitoringParameter) {
		this.monitoringParameter = monitoringParameter;
	}

	public void setVirtualLinkDescFlavour(List<VirtualLinkDf> virtualLinkDescFlavour) {
		this.virtualLinkDescFlavour = virtualLinkDescFlavour;
	}
}
