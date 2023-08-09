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
package it.nextworks.nfvmano.libs.ifa.records.vnfinfo;

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

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.ResourceHandle;
import it.nextworks.nfvmano.libs.ifa.common.enums.VimResourceStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides information on virtualised compute 
 * and storage resources used by a VNFC in a VNF instance.
 * 
 * REF IFA 007 v2.3.1 - 8.5.4
 * 
 * @author nextworks
 *
 */
@Entity
public class VnfcResourceInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private InstantiatedVnfInfo iVnfInfo;
	
	private String vnfcInstanceId;
	private String vduId;
	
	@Embedded
	private ResourceHandle computeResource;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> storageResourceId = new ArrayList<>();
	
	private String reservationId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> metadata = new HashMap<>();
	
	@OneToMany(mappedBy = "vri", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VnfcCpInfo> vnfcCpInfo = new ArrayList<>();
	
	@JsonIgnore
	private VimResourceStatus status;
	
	@JsonIgnore
	private String hostId;
	
	@JsonIgnore
	private String hostname;
	
	public VnfcResourceInfo() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param iVnfInfo instantiated VNF info this VNFC belongs to
	 * @param vnfcInstanceId Identifier of this VNFC instance.
	 * @param vduId Reference to the applicable Vdu information element in the VNFD.
	 * @param computeResource Reference to the VirtualCompute resource.
	 * @param storageResourceId Reference(s) to the VirtualStorage resource(s).
	 * @param reservationId The reservation identifier applicable to the resource.
	 * @param metadata Metadata about this resource.
	 */
	public VnfcResourceInfo(InstantiatedVnfInfo iVnfInfo,
			String vnfcInstanceId,
			String vduId,
			ResourceHandle computeResource,
			List<String> storageResourceId,
			String reservationId,
			Map<String, String> metadata) {
		this.iVnfInfo = iVnfInfo;
		this.vnfcInstanceId = vnfcInstanceId;
		this.vduId = vduId;
		this.computeResource = computeResource;
		if (storageResourceId != null) this.storageResourceId = storageResourceId;
		this.reservationId = reservationId;
		if (metadata != null) this.metadata = metadata;
		this.status = VimResourceStatus.INSTANTIATING;
	}

	
	
	/**
	 * @return the vnfcInstanceId
	 */
	public String getVnfcInstanceId() {
		return vnfcInstanceId;
	}

	/**
	 * @return the vduId
	 */
	public String getVduId() {
		return vduId;
	}

	/**
	 * @return the computeResource
	 */
	public ResourceHandle getComputeResource() {
		return computeResource;
	}

	/**
	 * @return the storageResourceId
	 */
	public List<String> getStorageResourceId() {
		return storageResourceId;
	}

	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
	}
	
	

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}
	
	

	/**
	 * @return the vnfcCpInfo
	 */
	public List<VnfcCpInfo> getVnfcCpInfo() {
		return vnfcCpInfo;
	}

	/**
	 * @return the status
	 */
	@JsonIgnore
	public VimResourceStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	@JsonIgnore
	public void setStatus(VimResourceStatus status) {
		this.status = status;
	}
	
	

	/**
	 * @return the hostname
	 */
	@JsonIgnore
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname the hostname to set
	 */
	@JsonIgnore
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * @return the hostId
	 */
	@JsonIgnore
	public String getHostId() {
		return hostId;
	}

	/**
	 * @param hostId the hostId to set
	 */
	@JsonIgnore
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfcInstanceId == null) throw new MalformattedElementException("VNFC resource info without VNFC instance ID");
		if (vduId == null) throw new MalformattedElementException("VNFC resource info without VDU ID");
		if (computeResource == null) throw new MalformattedElementException("VNFC resource info without resource");
		else computeResource.isValid();
		for (VnfcCpInfo vci : vnfcCpInfo) vci.isValid();
	}

}
