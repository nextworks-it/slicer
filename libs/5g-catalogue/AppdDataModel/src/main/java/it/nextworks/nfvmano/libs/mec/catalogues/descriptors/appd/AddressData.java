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
package it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.AddressType;
import it.nextworks.nfvmano.libs.common.enums.IpVersion;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

/**
 * The AddressData information element supports providing information 
 * about the addressing scheme and parameters applicable to a CP.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.3.3
 * 
 * @author nextworks
 *
 */
@Embeddable
public class AddressData implements DescriptorInformationElement {

	private AddressType addressType;
	
	//TODO: add L2 address data when available
	
	private boolean iPAddressAssignment;
	private boolean floatingIpActivated;
	private boolean management;
	private IpVersion iPAddressType;
	private int numberOfIpAddress;
	
	
	public AddressData() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param addressType Describes the type of the address to be assigned to the CP instantiated from the parent CPD.
	 * @param iPAddressAssignment Specify if the address assignment is the responsibility of management and orchestration function or not.
	 * @param floatingIpActivated Specify if the floating IP scheme is activated on the CP or not.
	 * @param management Specify if the address is to be used for management access to the VNF
	 * @param iPAddressType Define address type.
	 * @param numberOfIpAddress Minimum number of IP addresses to be assigned based on this L3AddressData information element.
	 */
	public AddressData(AddressType addressType,
			boolean iPAddressAssignment,
			boolean floatingIpActivated,
			boolean management,
			IpVersion iPAddressType,
			int numberOfIpAddress) {
		this.addressType = addressType;
		this.iPAddressAssignment = iPAddressAssignment;
		this.floatingIpActivated = floatingIpActivated;
		this.iPAddressType = iPAddressType;
		this.management = management;
		this.numberOfIpAddress = numberOfIpAddress;
	}
	
	

	/**
	 * @return the addressType
	 */
	@JsonProperty("addressType")
	public AddressType getAddressType() {
		return addressType;
	}

	/**
	 * @return the iPAddressAssignment
	 */
	@JsonProperty("iPAddressAssignment")
	public boolean isiPAddressAssignment() {
		return iPAddressAssignment;
	}

	/**
	 * @return the floatingIpActivated
	 */
	@JsonProperty("floatingIpActivated")
	public boolean isFloatingIpActivated() {
		return floatingIpActivated;
	}

	/**
	 * @return the iPAddressType
	 */
	@JsonProperty("iPAddressType")
	public IpVersion getiPAddressType() {
		return iPAddressType;
	}

	/**
	 * @return the numberOfIpAddress
	 */
	@JsonProperty("numberOfIpAddress")
	public int getNumberOfIpAddress() {
		return numberOfIpAddress;
	}
	
	

	/**
	 * @return the management
	 */
	@JsonProperty("management")
	public boolean isManagement() {
		return management;
	}

	@Override
	public void isValid() throws MalformattedElementException {

	}

}
