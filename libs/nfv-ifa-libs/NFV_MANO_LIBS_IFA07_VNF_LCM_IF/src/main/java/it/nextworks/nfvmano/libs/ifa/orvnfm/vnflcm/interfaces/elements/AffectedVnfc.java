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

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.ResourceHandle;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.enums.VnfcChangeType;

/**
 * This information element provides information about added, deleted modified and temporary VNFCs.
 * 
 * REF IFA 007 v2.3.1 - 8.6.3
 * 
 * @author nextworks
 *
 */
public class AffectedVnfc implements InterfaceInformationElement {

	private String vnfcInstanceId;
	private String vduId;
	private VnfcChangeType changeType;
	private ResourceHandle computeResource;
	private List<String> addedStorageResourceIds = new ArrayList<>();
	private List<String> removedStorageResourceIds = new ArrayList<>();
	
	public AffectedVnfc() {	}

	/**
	 * Constructor
	 * 
	 * @param vnfcInstanceId Identifier of the VNFC instance.
	 * @param vduId Identifier of the VDU in the VNFD.
	 * @param changeType  Signals the type of change
	 * @param computeResource Reference to the VirtualCompute resource. Detailed information is (for new and modified resources) or has been (for removed resources) available from the Virtualised Compute Resource Management interface.
	 * @param addedStorageResourceIds Reference(s) to VirtualStorage resource(s) that were added. Each value refers to a VirtualStorageResourceInfo item in the VnfInfo that was added to the VNFC.
	 * @param removedStorageResourceIds Reference(s) to VirtualStorage resource(s) that were removed. The value contains the identifier of a VirtualStorageResourceInfo item that has been removed from the VNFC, and might no longer exist in the VnfInfo.
	 */
	public AffectedVnfc(String vnfcInstanceId,
			String vduId,
			VnfcChangeType changeType,
			ResourceHandle computeResource,
			List<String> addedStorageResourceIds,
			List<String> removedStorageResourceIds) {	
		this.vnfcInstanceId = vnfcInstanceId;
		this.vduId = vduId;
		this.changeType = changeType;
		this.computeResource = computeResource;
		if (addedStorageResourceIds != null) this.addedStorageResourceIds = addedStorageResourceIds;
		if (removedStorageResourceIds != null) this.removedStorageResourceIds = removedStorageResourceIds;
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
	 * @return the changeType
	 */
	public VnfcChangeType getChangeType() {
		return changeType;
	}

	/**
	 * @return the computeResource
	 */
	public ResourceHandle getComputeResource() {
		return computeResource;
	}

	/**
	 * @return the addedStorageResourceIds
	 */
	public List<String> getAddedStorageResourceIds() {
		return addedStorageResourceIds;
	}

	/**
	 * @return the removedStorageResourceIds
	 */
	public List<String> getRemovedStorageResourceIds() {
		return removedStorageResourceIds;
	}

	@Override
	public void isValid() throws MalformattedElementException {	
		if (vnfcInstanceId == null) throw new MalformattedElementException("Affected VNFC without VNFC instance ID");
		if (vduId == null) throw new MalformattedElementException("Affected VNFC without VDU ID");
		if (computeResource == null) throw new MalformattedElementException("Affected VNFC without VNFC instance ID");
		else computeResource.isValid();
	}

}
