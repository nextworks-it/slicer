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

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Entity
public class VsDescriptor implements DescriptorInformationElement {

	/**
	 * @param vsBlueprintId the vsBlueprintId to set
	 */
	public void setVsBlueprintId(String vsBlueprintId) {
		this.vsBlueprintId = vsBlueprintId;
	}



	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String vsDescriptorId;
	private String name;
	private String version;
	private String vsBlueprintId;
	
	private SliceServiceType sst;
	private SliceManagementControlType managementType;
	
	//Key: parameter ID as in the blueprint; value: desired value
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> qosParameters = new HashMap<String, String>();
	
	@JsonIgnore
	private boolean isPublic;
	
	@JsonIgnore
	private String tenantId;
	
	//This is a list because it may refer to specific atomic components
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "vsd", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ServiceConstraints> serviceConstraints = new ArrayList<>();
	
	@Embedded
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private VsdSla sla;
	
	//TODO: add further fields
	
	public VsDescriptor() {	}
	
	

	/**
	 * @param name
	 * @param version
	 * @param vsBlueprintId
	 * @param sst
	 * @param managementType
	 * @param qosParameters
	 * @param sla
	 * @param isPublic
	 * @param tenantId
	 * 
	 */
	public VsDescriptor(String name, String version, String vsBlueprintId, SliceServiceType sst,
			SliceManagementControlType managementType, Map<String, String> qosParameters, VsdSla sla,
			boolean isPublic, String tenantId) {
		this.name = name;
		this.version = version;
		this.vsBlueprintId = vsBlueprintId;
		this.sst = sst;
		this.managementType = managementType;
		this.qosParameters = qosParameters;
		if (sla != null) this.sla = sla;
		else sla = new VsdSla(ServiceCreationTimeRange.UNDEFINED, AvailabilityCoverageRange.UNDEFINED, false);
		this.isPublic = isPublic;
		this.tenantId = tenantId;
	}



	/**
	 * @return the vsDescriptorId
	 */
	public String getVsDescriptorId() {
		return vsDescriptorId;
	}



	/**
	 * @param vsDescriptorId the vsDescriptorId to set
	 */
	public void setVsDescriptorId(String vsDescriptorId) {
		this.vsDescriptorId = vsDescriptorId;
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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}



	/**
	 * @return the vsBlueprintId
	 */
	public String getVsBlueprintId() {
		return vsBlueprintId;
	}



	/**
	 * @return the sst
	 */
	public SliceServiceType getSst() {
		return sst;
	}



	/**
	 * @return the managementType
	 */
	public SliceManagementControlType getManagementType() {
		return managementType;
	}



	/**
	 * @return the qosParameters
	 */
	public Map<String, String> getQosParameters() {
		return qosParameters;
	}



	/**
	 * @return the tenantId
	 */
	@JsonIgnore
	public String getTenantId() {
		return tenantId;
	}



	/**
	 * @return the isPublic
	 */
	@JsonIgnore
	public boolean isPublic() {
		return isPublic;
	}



	/**
	 * @param isPublic the isPublic to set
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}



	/**
	 * @return the serviceConstraints
	 */
	public List<ServiceConstraints> getServiceConstraints() {
		return serviceConstraints;
	}
	
	


	/**
	 * @return the sla
	 */
	public VsdSla getSla() {
		return sla;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (name == null) throw new MalformattedElementException("VSD without name");
		if (version == null) throw new MalformattedElementException("VSD without version");
		if (vsBlueprintId == null) throw new MalformattedElementException("VSD without VS blueprint ID");
		if (serviceConstraints != null) {
			for (ServiceConstraints sc : serviceConstraints) sc.isValid();
		}
		if (sla != null) sla.isValid();
	}

}
