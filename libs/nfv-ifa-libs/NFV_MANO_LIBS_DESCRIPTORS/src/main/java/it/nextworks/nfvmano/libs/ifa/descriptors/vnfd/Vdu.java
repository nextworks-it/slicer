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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.SwImageDesc;

/**
 * The Virtualisation Deployment Unit (VDU) is a construct 
 * supporting the description of the deployment and operational
 * behaviour of a VNFC.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.6.2
 * 
 * @author nextworks
 *
 */
@Entity
public class Vdu implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Vnfd vnfd;
	
	private String vduId;
	private String vduName;
	private String description;
	
	@OneToMany(mappedBy = "vdu", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VduCpd> intCpd = new ArrayList<>();
	
	private String virtualComputeDesc;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> virtualStorageDesc = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<Integer, String> bootOrder = new HashMap<>();
	
	@Embedded
	private SwImageDesc swImageDesc;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> nfviConstraint = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<MonitoringParameter> monitoringParameter = new ArrayList<>();
	
	@Embedded
	private VnfcConfigurableProperties configurableProperties;
	
	public Vdu() {
		// JPA only
	}

	
	/**
	 * Constructor
	 * 
	 * @param vnfd VNFD this VDU belongs to
	 * @param vduId ID of the VDU 
	 * @param name name of the VDU
	 * @param description description of the VDU 
	 * @param virtualComputeDesc Describes network connectivity between a VNFC instance (based on this Vdu) and an internal Virtual Link (VL).
	 * @param virtualStorageDesc Describes CPU, Memory and acceleration requirements of the Virtualisation Container realizing this Vdu.
	 * @param bootOrder The key indicates the boot index (lowest index defines highest boot priority). The Value references a descriptor from which a valid boot device is created e.g. VirtualStorageDesc from which a VirtualStorage instance is created.
	 * @param swImageDesc Describes the software image which is directly loaded on the virtualisation container realizing this Vdu
	 * @param nfviConstraint Describes constraints on the NFVI for the VNFC instance(s) created from this Vdu.
	 * @param monitoringParameter Defines the virtualised resources monitoring parameters on VDU level.
	 * @param configurableProperties Describes the configurable properties of all VNFC instances based on this VDU.
	 */
	public Vdu(Vnfd vnfd,
			String vduId,
			String name,
			String description,
			String virtualComputeDesc,
			List<String> virtualStorageDesc,
			Map<Integer, String> bootOrder,
			SwImageDesc swImageDesc,
			List<String> nfviConstraint,
			List<MonitoringParameter> monitoringParameter,
			VnfcConfigurableProperties configurableProperties) {
		this.vnfd = vnfd;
		this.vduId = vduId;
		this.vduName = name;
		this.description = description;
		this.virtualComputeDesc = virtualComputeDesc;
		if (virtualStorageDesc != null) this.virtualStorageDesc = virtualStorageDesc;
		if (bootOrder != null) this.bootOrder = bootOrder;
		this.swImageDesc = swImageDesc;
		if (nfviConstraint != null) this.nfviConstraint = nfviConstraint;
		if (monitoringParameter != null) this.monitoringParameter = monitoringParameter;
		this.configurableProperties = configurableProperties;
	} 
	
	
	
	/**
	 * @return the vduId
	 */
	@JsonProperty("vduId")
	public String getVduId() {
		return vduId;
	}


	/**
	 * @return the name
	 */
	@JsonProperty("name")
	public String getVduName() {
		return vduName;
	}


	/**
	 * @return the description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}


	/**
	 * @return the intCpd
	 */
	@JsonProperty("intCpd")
	public List<VduCpd> getIntCpd() {
		return intCpd;
	}


	/**
	 * @return the virtualComputeDesc
	 */
	@JsonProperty("virtualComputeDesc")
	public String getVirtualComputeDesc() {
		return virtualComputeDesc;
	}


	/**
	 * @return the virtualStorageDesc
	 */
	@JsonProperty("virtualStorageDesc")
	public List<String> getVirtualStorageDesc() {
		return virtualStorageDesc;
	}


	/**
	 * @return the bootOrder
	 */
	@JsonProperty("bootOrder")
	public Map<Integer, String> getBootOrder() {
		return bootOrder;
	}


	/**
	 * @return the swImageDesc
	 */
	@JsonProperty("swImageDesc")
	public SwImageDesc getSwImageDesc() {
		return swImageDesc;
	}


	/**
	 * @return the nfviConstraint
	 */
	@JsonProperty("nfviConstraint")
	public List<String> getNfviConstraint() {
		return nfviConstraint;
	}


	/**
	 * @return the monitoringParameter
	 */
	@JsonProperty("monitoringParameter")
	public List<MonitoringParameter> getMonitoringParameter() {
		return monitoringParameter;
	}


	/**
	 * @return the configurableProperties
	 */
	@JsonProperty("configurableProperties")
	public VnfcConfigurableProperties getConfigurableProperties() {
		return configurableProperties;
	}


	@Override
	public void isValid() throws MalformattedElementException {
		if (vduId == null) throw new MalformattedElementException("VDU without ID");
		if (vduName == null) throw new MalformattedElementException("VDU without name");
		if (description == null) throw new MalformattedElementException("VDU without description");
		if ((intCpd == null) || (intCpd.isEmpty())) {
			throw new MalformattedElementException("VDU without internal connection points");
		} else {
			for (VduCpd cp : intCpd) cp.isValid();
		}
		if (virtualComputeDesc == null) throw new MalformattedElementException("VDU without virtual compute descriptor");
	}

}
