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
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.enums.VirtualStorageChangeType;

/**
 * This information element provides information about added, deleted, modified and temporary virtual storage resources.
 * 
 * REF IFA 007 v2.3.1 - 8.6.5
 * 
 * @author nextworks
 *
 */
public class AffectedVirtualStorage implements InterfaceInformationElement {

	private String virtualStorageInstanceId;
	private String virtualStorageDescId;
	private VirtualStorageChangeType changeType;
	private ResourceHandle storageResource;
	
	public AffectedVirtualStorage() { }
	
	/**
	 * Constructor
	 * 
	 * @param virtualStorageInstanceId Identifier of the virtual storage instance.
	 * @param virtualStorageDescId Identifier of the VirtualStorageDesc in the VNFD.
	 * @param changeType Signals the type of change
	 * @param storageResource Reference to the VirtualStorage resource. Detailed information is (for new and modified resources) or has been (for removed resources) available from the Virtualised Storage Resource Management interface.
	 */
	public AffectedVirtualStorage(String virtualStorageInstanceId,
			String virtualStorageDescId,
			VirtualStorageChangeType changeType,
			ResourceHandle storageResource) {
		this.virtualStorageInstanceId = virtualStorageInstanceId;
		this.virtualStorageDescId = virtualStorageDescId;
		this.changeType = changeType;
		this.storageResource = storageResource;
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
	 * @return the changeType
	 */
	public VirtualStorageChangeType getChangeType() {
		return changeType;
	}

	/**
	 * @return the storageResource
	 */
	public ResourceHandle getStorageResource() {
		return storageResource;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (virtualStorageInstanceId == null) throw new MalformattedElementException("Affected virtual storage without ID");
		if (virtualStorageDescId == null) throw new MalformattedElementException("Affected virtual storage without descriptor ID");
		if (storageResource == null) throw new MalformattedElementException("Affected virtual storage without resource");
	}

}
