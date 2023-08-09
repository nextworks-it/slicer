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
package it.nextworks.nfvmano.libs.ifa.records.nsinfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
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
 * This information element provides run-time information about an NS VL instance.
 * Ref. IFA 013 v2.3.1 section 8.3.3.10
 * 
 * @author nextworks
 *
 */
@Entity
public class NsVirtualLinkInfo implements DescriptorInformationElement {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsInfo nsInfo;
	
	private String nsVirtualLinkInstanceId;

	private String nsVirtualLinkDescId;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<ResourceHandle> resourceHandle = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "vlInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<NsLinkPort> linkPort = new ArrayList<>();
	
	@JsonIgnore
	private VimResourceStatus status;
	
	@JsonIgnore
	private String resourceId;		//at the VIM - used for internal check
	
	@JsonIgnore
	private int segmentId;
	
	@JsonIgnore
	private String subnetId;
	
	@JsonIgnore
	private VimResourceStatus subnetStatus;
	
	public NsVirtualLinkInfo() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInfo NS info this element belongs to
	 * @param nsVirtualLinkInstanceId Identifier of this NsVirtualLinkInfo information element, identifying the NS VL instance.
	 * @param nsVirtualLinkDescId Identifier of the VLD in the NSD for this VL.
	 * @param resourceHandle Identifier(s) of the virtualised network resource(s) realizing this VL.
	 * 
	 */
	public NsVirtualLinkInfo(NsInfo nsInfo,
			String nsVirtualLinkInstanceId,
			String nsVirtualLinkDescId,
			List<ResourceHandle> resourceHandle) {
		this.nsInfo = nsInfo;
		this.nsVirtualLinkInstanceId = nsVirtualLinkInstanceId;
		this.nsVirtualLinkDescId = nsVirtualLinkDescId;
		if (resourceHandle != null) this.resourceHandle = resourceHandle;
		this.status = VimResourceStatus.INSTANTIATING;
		this.subnetStatus = VimResourceStatus.INSTANTIATING;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInfo NS info this element belongs to
	 * @param nsVirtualLinkInstanceId Identifier of this NsVirtualLinkInfo information element, identifying the NS VL instance.
	 * @param nsVirtualLinkDescId Identifier of the VLD in the NSD for this VL.
	 * @param resourceId Resource ID at the VIM
	 * @param resourceHandle Identifier(s) of the virtualised network resource(s) realizing this VL.
	 * 
	 */
	public NsVirtualLinkInfo(NsInfo nsInfo,
			String nsVirtualLinkInstanceId,
			String nsVirtualLinkDescId,
			String resourceId,
			List<ResourceHandle> resourceHandle) {
		this.nsInfo = nsInfo;
		this.nsVirtualLinkInstanceId = nsVirtualLinkInstanceId;
		this.nsVirtualLinkDescId = nsVirtualLinkDescId;
		this.resourceId = resourceId;
		if (resourceHandle != null) this.resourceHandle = resourceHandle;
		this.status = VimResourceStatus.INSTANTIATING;
		this.subnetStatus = VimResourceStatus.INSTANTIATING;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInfo NS info this element belongs to
	 * @param nsVirtualLinkInstanceId Identifier of this NsVirtualLinkInfo information element, identifying the NS VL instance.
	 * @param nsVirtualLinkDescId Identifier of the VLD in the NSD for this VL.
	 * @param resourceHandle Identifier(s) of the virtualised network resource(s) realizing this VL.
	 * @param linkPort Link ports of this VL. Cardinality of zero indicates that no port have yet been created for this VL.
	 */
	public NsVirtualLinkInfo(NsInfo nsInfo,
			String nsVirtualLinkInstanceId,
			String nsVirtualLinkDescId,
			List<ResourceHandle> resourceHandle,
			List<NsLinkPort> linkPort) {
		this.nsInfo = nsInfo;
		this.nsVirtualLinkInstanceId = nsVirtualLinkInstanceId;
		this.nsVirtualLinkDescId = nsVirtualLinkDescId;
		if (resourceHandle != null) this.resourceHandle = resourceHandle;
		if (linkPort != null) this.linkPort = linkPort;
		this.status = VimResourceStatus.INSTANTIATING;
		this.subnetStatus = VimResourceStatus.INSTANTIATING;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInfo NS info this element belongs to
	 * @param nsVirtualLinkInstanceId Identifier of this NsVirtualLinkInfo information element, identifying the NS VL instance.
	 * @param nsVirtualLinkDescId Identifier of the VLD in the NSD for this VL.
	 * @param resourceId Resource ID at the VIM
	 * @param resourceHandle Identifier(s) of the virtualised network resource(s) realizing this VL.
	 * @param segmentId ID associated by the VIM to the virtualised network resource
	 * 
	 */
	public NsVirtualLinkInfo(NsInfo nsInfo,
			String nsVirtualLinkInstanceId,
			String nsVirtualLinkDescId,
			String resourceId,
			List<ResourceHandle> resourceHandle,
			int segmentId) {
		this.nsInfo = nsInfo;
		this.nsVirtualLinkInstanceId = nsVirtualLinkInstanceId;
		this.nsVirtualLinkDescId = nsVirtualLinkDescId;
		this.resourceId = resourceId;
		if (resourceHandle != null) this.resourceHandle = resourceHandle;
		this.status = VimResourceStatus.INSTANTIATING;
		this.segmentId = segmentId;
		this.subnetStatus = VimResourceStatus.INSTANTIATING;
	}
	
	

	/**
	 * @return the nsVirtualLinkInstanceId
	 */
	public String getNsVirtualLinkInstanceId() {
		return nsVirtualLinkInstanceId;
	}

	/**
	 * @return the segmentId
	 */
	public int getSegmentId() {
		return segmentId;
	}

	/**
	 * @param segmentId the segmentId to set
	 */
	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}

	/**
	 * @return the nsVirtualLinkDescId
	 */
	public String getNsVirtualLinkDescId() {
		return nsVirtualLinkDescId;
	}

	/**
	 * @return the resourceHandle
	 */
	public List<ResourceHandle> getResourceHandle() {
		return resourceHandle;
	}

	/**
	 * @return the linkPort
	 */
	public List<NsLinkPort> getLinkPort() {
		return linkPort;
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
	 * @return the resourceId
	 */
	@JsonIgnore
	public String getResourceId() {
		return resourceId;
	}
	
	

	/**
	 * @return the subnetId
	 */
	@JsonIgnore
	public String getSubnetId() {
		return subnetId;
	}

	/**
	 * @param subnetId the subnetId to set
	 */
	@JsonIgnore
	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}
	
	

	/**
	 * @return the subnetStatus
	 */
	@JsonIgnore
	public VimResourceStatus getSubnetStatus() {
		return subnetStatus;
	}

	/**
	 * @param subnetStatus the subnetStatus to set
	 */
	@JsonIgnore
	public void setSubnetStatus(VimResourceStatus subnetStatus) {
		this.subnetStatus = subnetStatus;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.nsVirtualLinkDescId == null) throw new MalformattedElementException("NS VL info without VLD ID");
		if (this.nsVirtualLinkInstanceId == null) throw new MalformattedElementException("NS VL info without VL instance ID");
		if ((this.resourceHandle == null) || (this.resourceHandle.isEmpty())) {
			throw new MalformattedElementException("NS VL info without resource handle");
		} else {
			for (ResourceHandle r: this.resourceHandle) r.isValid();
		}
		if (linkPort != null) {
			for (NsLinkPort lp : linkPort) lp.isValid();
		}
	}

}
