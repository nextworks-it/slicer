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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.reservation;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 *  REF IFA 005 v2.3.1 - 8.8.6.4
 * 
 * @author nextworks
 *
 */
public class ReservedStoragePool implements InterfaceInformationElement {

	private int storageSize;
	private int numSnapshots;
	private int numVolumes;
	private String zoneId;
	
	public ReservedStoragePool() {	}

	
	/**
	 * Constructor
	 * 
	 * @param storageSize Size of virtualised storage resource that has been reserved.
	 * @param numSnapshots Number of snapshots that have been reserved.
	 * @param numVolumes Number of volumes that have been reserved.
	 * @param zoneId References the resource zone where the virtual storage resources have been reserved.
	 */
	public ReservedStoragePool(int storageSize, int numSnapshots, int numVolumes, String zoneId) {
		this.storageSize = storageSize;
		this.numSnapshots = numSnapshots;
		this.numVolumes = numVolumes;
		this.zoneId = zoneId;
	}



	/**
	 * @return the storageSize
	 */
	public int getStorageSize() {
		return storageSize;
	}



	/**
	 * @return the numSnapshots
	 */
	public int getNumSnapshots() {
		return numSnapshots;
	}



	/**
	 * @return the numVolumes
	 */
	public int getNumVolumes() {
		return numVolumes;
	}



	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}



	@Override
	public void isValid() throws MalformattedElementException {	}

}
