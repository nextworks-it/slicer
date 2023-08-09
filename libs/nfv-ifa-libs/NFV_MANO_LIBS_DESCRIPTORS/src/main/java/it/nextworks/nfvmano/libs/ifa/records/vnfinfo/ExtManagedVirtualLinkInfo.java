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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.ResourceHandle;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides a reference to an externally-managed internal VL.
 * 
 * REF IFA 007 v2.3.1 - 8.5.10
 * 
 * @author nextworks
 *
 */
@Entity
public class ExtManagedVirtualLinkInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private InstantiatedVnfInfo iVnfInfo;
	
	private String extManagedVirtualLinkId;
	private String vnfVirtualLinkDescId;
	
	@Embedded
	private ResourceHandle networkResource;
	
	@OneToMany(mappedBy = "extMngVlInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VnfLinkPort> linkPort = new ArrayList<>();
	
	public ExtManagedVirtualLinkInfo() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param iVnfInfo Instantiated VNF info this externally managed VL info belongs to
	 * @param extManagedVirtualLinkId Identifier of this externally-managed internal VL.
	 * @param vnfVirtualLinkDescId Identifier of the VNF Virtual Link Descriptor (VLD) in the VNFD.
	 * @param networkResource Reference to the VirtualNetwork resource.
	 */
	public ExtManagedVirtualLinkInfo(InstantiatedVnfInfo iVnfInfo,
			String extManagedVirtualLinkId,
			String vnfVirtualLinkDescId,
			ResourceHandle networkResource) {
		this.iVnfInfo = iVnfInfo;
		this.extManagedVirtualLinkId = extManagedVirtualLinkId;
		this.vnfVirtualLinkDescId = vnfVirtualLinkDescId;
		this.networkResource = networkResource;
	}
	
	

	/**
	 * @return the extManagedVirtualLinkId
	 */
	public String getExtManagedVirtualLinkId() {
		return extManagedVirtualLinkId;
	}

	
	

	/**
	 * @return the vnfVirtualLinkDescId
	 */
	public String getVnfVirtualLinkDescId() {
		return vnfVirtualLinkDescId;
	}

	/**
	 * @return the networkResource
	 */
	public ResourceHandle getNetworkResource() {
		return networkResource;
	}

	/**
	 * @return the linkPort
	 */
	public List<VnfLinkPort> getLinkPort() {
		return linkPort;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (extManagedVirtualLinkId == null) throw new MalformattedElementException("External managed VL info without VL ID");
		if (vnfVirtualLinkDescId == null) throw new MalformattedElementException("External managed VL info without VNF VLD ID");
		if (networkResource == null) {
			throw new MalformattedElementException("External managed VL info without network resource");
		} else networkResource.isValid();
		for (VnfLinkPort p : linkPort) p.isValid();
	}

}
