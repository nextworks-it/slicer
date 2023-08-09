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
package it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.elements;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.ResourceHandle;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.enums.VnfVirtualLinkChangeType;

/**
 * This information element provides information about added, deleted, 
 * modified and temporary VLs, as well as about link port changes.
 * 
 * REF IFA 007 v2.3.1 - 8.6.4
 * 
 * @author nextworks
 *
 */
public class AffectedVirtualLink implements InterfaceInformationElement {

	private String virtualLinkInstanceId;
	private String virtualLinkDescId;
	private VnfVirtualLinkChangeType changeType;
	private ResourceHandle networkResource;
	
	public AffectedVirtualLink() { }
	
	/**
	 * Constructor
	 * 
	 * @param virtualLinkInstanceId Identifier of the VL instance.
	 * @param virtualLinkDescId Identifier of the VLD in the VNFD.
	 * @param changeType Signals the type of change
	 * @param networkResource Reference to the VirtualNetwork resource. Detailed information is (for new and modified resources) or has been (for removed resources) available from the Virtualised Network Resource Management interface.
	 */
	public AffectedVirtualLink(String virtualLinkInstanceId,
			String virtualLinkDescId,
			VnfVirtualLinkChangeType changeType,
			ResourceHandle networkResource) {
		this.virtualLinkInstanceId = virtualLinkInstanceId;
		this.virtualLinkDescId = virtualLinkDescId;
		this.changeType = changeType;
		this.networkResource = networkResource;
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
	 * @return the changeType
	 */
	public VnfVirtualLinkChangeType getChangeType() {
		return changeType;
	}

	/**
	 * @return the networkResource
	 */
	public ResourceHandle getNetworkResource() {
		return networkResource;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (virtualLinkInstanceId == null) throw new MalformattedElementException("Affected virtual link without VL instance ID");
		if (virtualLinkDescId == null) throw new MalformattedElementException("Affected virtual link without VLD ID");
		if (networkResource == null) throw new MalformattedElementException("Affected virtual link without network resource");
		else networkResource.isValid();
	}

}
