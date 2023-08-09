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
 *  
 *  REF IFA 005 v2.3.1 - 8.8.6.3
 * 
 * @author nextworks
 *
 */
public class StoragePoolReservation implements InterfaceInformationElement {

	private int storageSize;
	private int numSnapshots;
	private int numVolumes;
	
	public StoragePoolReservation() {	}
	
	
	/**
	 * Constructor
	 * 
	 * @param storageSize Size of virtualised storage resource (e.g. size of volume, in GB) to be reserved.
	 * @param numSnapshots Number of snapshots to be reserved.
	 * @param numVolumes Number of volumes to be reserved.
	 */
	public StoragePoolReservation(int storageSize, int numSnapshots, int numVolumes) {
		this.storageSize = storageSize;
		this.numSnapshots = numSnapshots;
		this.numVolumes = numVolumes;
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



	@Override
	public void isValid() throws MalformattedElementException {	}

}
