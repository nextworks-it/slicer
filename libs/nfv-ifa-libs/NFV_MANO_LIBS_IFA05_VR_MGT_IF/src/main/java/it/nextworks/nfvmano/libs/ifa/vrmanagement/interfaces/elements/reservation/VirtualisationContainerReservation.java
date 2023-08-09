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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualComputeFlavour;

/**
 * This clause describes the attributes for the VirtualisationContainerReservation information element.
 * 
 * REF IFA 005 v2.3.1 - 8.8.5.2
 * 
 * @author nextworks
 *
 */
public class VirtualisationContainerReservation implements InterfaceInformationElement {

	private String containerId;
	private VirtualComputeFlavour containerFlavour;
	
	public VirtualisationContainerReservation() {	}
	
	/**
	 * Constructor
	 * 
	 * @param containerId
	 * @param containerFlavour
	 */
	public VirtualisationContainerReservation(String containerId,
			VirtualComputeFlavour containerFlavour) {
		this.containerId = containerId;
		this.containerFlavour = containerFlavour;
	}

	
	
	/**
	 * @return the containerId
	 */
	public String getContainerId() {
		return containerId;
	}

	/**
	 * @return the containerFlavour
	 */
	public VirtualComputeFlavour getContainerFlavour() {
		return containerFlavour;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (containerId == null) throw new MalformattedElementException("Virtualisation container reservation without container ID");
		if (containerFlavour == null) throw new MalformattedElementException("Virtualisation container reservation without container flavour");
		else containerFlavour.isValid();
	}

}
