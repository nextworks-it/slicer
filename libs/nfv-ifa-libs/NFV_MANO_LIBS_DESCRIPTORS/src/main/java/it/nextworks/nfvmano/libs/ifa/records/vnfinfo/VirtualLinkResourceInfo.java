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

/***
 * This information element provides information on virtualised network resources used by a VL instance in a VNF.
 * 
 * REF IFA 007 v2.3.1 - 8.5.5
 * 
 * @author nextworks
 *
 */
@Entity
public class VirtualLinkResourceInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private InstantiatedVnfInfo iVnfInfo;
	
	private String virtualLinkInstanceId;
	private String virtualLinkDescId;
	
	@Embedded
	private ResourceHandle networkResource;
	
	private String reservationId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> metadata = new HashMap<>();
	
	@OneToMany(mappedBy = "vlri", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VnfLinkPort> vnfLinkPort = new ArrayList<>();
	
	@JsonIgnore
	private VimResourceStatus status;
	
	public VirtualLinkResourceInfo() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param iVnfInfo iVnfInfo instantiated VNF info this VL belongs to
	 * @param virtualLinkInstanceId Identifier of this VL instance.
	 * @param virtualLinkDescId Identifier of the Virtual Link Descriptor (VLD) in the VNFD.
	 * @param networkResource Reference to the VirtualNetwork resource.
	 * @param reservationId The reservation identifier applicable to the resource.
	 * @param metadata Metadata about this resource.
	 */
	public VirtualLinkResourceInfo(InstantiatedVnfInfo iVnfInfo,
			String virtualLinkInstanceId,
			String virtualLinkDescId,
			ResourceHandle networkResource,
			String reservationId,
			Map<String, String> metadata) {
		this.iVnfInfo = iVnfInfo;
		this.virtualLinkInstanceId = virtualLinkInstanceId;
		this.virtualLinkDescId = virtualLinkDescId;
		this.networkResource = networkResource;
		this.reservationId = reservationId;
		this.status = VimResourceStatus.INSTANTIATING;
		if (metadata != null) this.metadata = metadata;
	}

	
	
	/**
	 * @return the virtualLinkInstanceId
	 */
	public String getVirtualLinkInstanceId() {
		return virtualLinkInstanceId;
	}

	/**
	 * @return the virtualLinkDescId
	 */
	public String getVirtualLinkDescId() {
		return virtualLinkDescId;
	}

	/**
	 * @return the networkResource
	 */
	public ResourceHandle getNetworkResource() {
		return networkResource;
	}

	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
	}

	/**
	 * @return the vnfLinkPort
	 */
	public List<VnfLinkPort> getVnfLinkPort() {
		return vnfLinkPort;
	}
	
	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
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

	@Override
	public void isValid() throws MalformattedElementException {
		if (virtualLinkInstanceId == null) throw new MalformattedElementException("VL resource info without VL instance ID");
		if (virtualLinkDescId == null) throw new MalformattedElementException("VL resource info without VLD ID");
		if (networkResource == null) throw new MalformattedElementException("VL resource info without resource");
		else networkResource.isValid();
	}

}
