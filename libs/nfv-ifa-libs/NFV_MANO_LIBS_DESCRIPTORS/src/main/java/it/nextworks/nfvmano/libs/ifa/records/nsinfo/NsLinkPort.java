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

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.ResourceHandle;
import it.nextworks.nfvmano.libs.ifa.common.enums.VimResourceStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides information about a port of an NS VL.
 * 
 * Ref. IFA 013 v2.3.1 section 8.3.3.11
 * 
 * @author nextworks
 *
 */
@Entity
public class NsLinkPort implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsVirtualLinkInfo vlInfo;
	
	@Embedded
	private ResourceHandle resourceHandle;
	
	private String cpId;
	
	@JsonIgnore
	private VimResourceStatus status;
	
	@JsonIgnore
	private String vimResourceId;		//at the VIM - used for internal check
	
	@JsonIgnore
	private boolean isSap;
	
	public NsLinkPort() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param vlInfo VL info this element belongs to
	 * @param resourceHandle Identifier(s) of the virtualised network resource(s) realizing this link port.
	 * @param cpId CP connected to this link port (reference to VnfExtCpInfo or PnfExtCpInfo or SapInfo)
	 * @param isSap true if the link port is a Service Access Point
	 */
	public NsLinkPort(NsVirtualLinkInfo vlInfo,
			ResourceHandle resourceHandle,
			String cpId,
			boolean isSap) {
		this.vlInfo = vlInfo;
		this.resourceHandle = resourceHandle;
		this.cpId = cpId;
		this.vimResourceId = resourceHandle.getResourceId();
		this.status = VimResourceStatus.INSTANTIATING;
		this.isSap = isSap;
	}

	
	
	/**
	 * @return the resourceHandle
	 */
	public ResourceHandle getResourceHandle() {
		return resourceHandle;
	}

	/**
	 * @return the cpId
	 */
	public String getCpId() {
		return cpId;
	}
	
	

	/**
	 * @return the status
	 */
	public VimResourceStatus getStatus() {
		return status;
	}

	/**
	 * @return the resourceId
	 */
	@JsonIgnore
	public String getResourceId() {
		return vimResourceId;
	}

	/**
	 * @return the isSap
	 */
	public boolean isSap() {
		return isSap;
	}
	
	

	/**
	 * @param status the status to set
	 */
	public void setStatus(VimResourceStatus status) {
		this.status = status;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceHandle == null) {
			throw new MalformattedElementException("NS Link Port without resource handle");
		} else resourceHandle.isValid();
	}
	
}
