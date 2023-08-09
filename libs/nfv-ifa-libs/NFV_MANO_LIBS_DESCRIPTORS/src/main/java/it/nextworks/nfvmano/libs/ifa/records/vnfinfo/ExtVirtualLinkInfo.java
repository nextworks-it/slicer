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
 * This information element provides a reference to an external VL.
 * 
 * REF IFA 007 v2.3.1 - 8.5.9
 * 
 * @author nextworks
 *
 */
@Entity
public class ExtVirtualLinkInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private InstantiatedVnfInfo iVnfInfo;
	
	private String extVirtualLinkId;
	
	@Embedded
	private ResourceHandle resourceHandle;
	
	@OneToMany(mappedBy = "extVlInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ExtLinkPort> linkPort = new ArrayList<>();
	
	public ExtVirtualLinkInfo() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param iVnfInfo Instantiated VNF info this VL belongs to
	 * @param extVirtualLinkId Identifier of this external VL.
	 * @param resourceHandle Identifier of the resource realizing this VL.
	 */
	public ExtVirtualLinkInfo(InstantiatedVnfInfo iVnfInfo,
			String extVirtualLinkId,
			ResourceHandle resourceHandle) {
		this.iVnfInfo = iVnfInfo;
		this.extVirtualLinkId = extVirtualLinkId;
		this.resourceHandle = resourceHandle;
	}
	
	

	/**
	 * @return the extVirtualLinkId
	 */
	public String getExtVirtualLinkId() {
		return extVirtualLinkId;
	}

	/**
	 * @return the resourceHandle
	 */
	public ResourceHandle getResourceHandle() {
		return resourceHandle;
	}

	/**
	 * @return the linkPort
	 */
	public List<ExtLinkPort> getLinkPort() {
		return linkPort;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (extVirtualLinkId == null) throw new MalformattedElementException("External VL info without VL ID");
		if (resourceHandle == null) throw new MalformattedElementException("External VL info without resource");
		else resourceHandle.isValid();
		for (ExtLinkPort p : linkPort) p.isValid();
	}

}
