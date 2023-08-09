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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.ResourceHandle;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides information on virtualised storage 
 * resources used by a storage instance in a VNF.
 * 
 * REF IFA 007 v2.3.1 - 8.5.6
 * 
 * @author nextworks
 *
 */
@Entity
public class VirtualStorageResourceInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private InstantiatedVnfInfo iVnfInfo;
	
	private String virtualStorageInstanceId;
	private String virtualStorageDescId;
	
	@Embedded
	private ResourceHandle storageResource;
	
	private String reservationId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> metadata = new HashMap<>();
	
	public VirtualStorageResourceInfo() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param iVnfInfo iVnfInfo instantiated VNF info this virtual storage belongs to
	 * @param virtualStorageInstanceId Identifier of this virtual storage resource instance.
	 * @param virtualStorageDescId Identifier of the VirtualStorageDesc in the VNFD.
	 * @param storageResource Reference to the VirtualStorage resource.
	 * @param reservationId The reservation identifier applicable to the resource.
	 * @param metadata Metadata about this resource.
	 */
	public VirtualStorageResourceInfo(InstantiatedVnfInfo iVnfInfo,
			String virtualStorageInstanceId,
			String virtualStorageDescId,
			ResourceHandle storageResource,
			String reservationId,
			Map<String, String> metadata) {
		this.iVnfInfo = iVnfInfo;
		this.virtualStorageInstanceId = virtualStorageInstanceId;
		this.virtualStorageDescId = virtualStorageDescId;
		this.storageResource = storageResource;
		this.reservationId = reservationId;
		if (metadata != null) this.metadata = metadata;
	}
	
	

	/**
	 * @return the virtualStorageInstanceId
	 */
	public String getVirtualStorageInstanceId() {
		return virtualStorageInstanceId;
	}

	/**
	 * @return the virtualStorageDescId
	 */
	public String getVirtualStorageDescId() {
		return virtualStorageDescId;
	}

	/**
	 * @return the storageResource
	 */
	public ResourceHandle getStorageResource() {
		return storageResource;
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

	@Override
	public void isValid() throws MalformattedElementException {
		if (virtualStorageInstanceId == null) throw new MalformattedElementException("Virtual storage info without instance ID");
		if (virtualStorageDescId == null) throw new MalformattedElementException("Virtual storage info without VSD ID");
		if (storageResource == null) throw new MalformattedElementException("Virtual storage resource info without resource");
		else storageResource.isValid();
	}

}
