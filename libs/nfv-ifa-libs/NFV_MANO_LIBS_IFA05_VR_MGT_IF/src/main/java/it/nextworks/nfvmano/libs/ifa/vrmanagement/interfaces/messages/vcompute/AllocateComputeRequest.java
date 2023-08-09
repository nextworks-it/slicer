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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vcompute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.AffinityConstraint;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.UserData;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualInterfaceData;

/**
 * Request to allocate a VM
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.1.2
 * 
 * @author nextworks
 *
 */
public class AllocateComputeRequest implements InterfaceMessage {

	private String computeName;
	private String reservationId;
	private String computeFlavourId;
	private List<AffinityConstraint> affinityConstraint = new ArrayList<>();
	private List<VirtualInterfaceData> interfaceData = new ArrayList<>();
	private String vcImageId;
	private Map<String, String> metadata = new HashMap<>();
	private String resourceGroupId;
	private String locationConstraints;
	private UserData userData;
	
	public AllocateComputeRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param computeName Name provided by the consumer for the virtualised compute resource to allocate. It can be used for identifying resources from consumer side.
	 * @param reservationId Identifier of the resource reservation applicable to this virtualised resource management operation.
	 * @param computeFlavourId Identifier of the Compute Flavour that provides information about the particular memory, CPU and disk resources for virtualised compute resource to allocate.
	 * @param affinityConstraint Element with affinity information of the virtualised compute resource to allocate.
	 * @param vcImageId Identifier of the virtualisation container software image (e.g. a virtual machine image).
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 * @param resourceGroupId Unique identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
	 * @param locationConstraints If present, it defines location constraints for the resource(s) is (are) requested to be allocated, e.g. in what particular Resource Zone.
	 * @param userData Element containing user data to customize the virtualised compute resource at boot-time.
	 */
	public AllocateComputeRequest(String computeName,
			String reservationId,
			String computeFlavourId,
			List<AffinityConstraint> affinityConstraint,
			List<VirtualInterfaceData> interfaceData,
			String vcImageId,
			Map<String, String> metadata,
			String resourceGroupId,
			String locationConstraints,
			UserData userData) { 
		this.computeName = computeName;
		this.reservationId = reservationId;
		this.computeFlavourId = computeFlavourId;
		if (affinityConstraint != null) this.affinityConstraint = affinityConstraint;
		if (interfaceData != null) this.interfaceData = interfaceData;
		this.vcImageId = vcImageId;
		if (metadata != null) this.metadata = metadata;
		this.resourceGroupId = resourceGroupId;
		this.locationConstraints = locationConstraints;
		this.userData = userData;
	}
	
	

	/**
	 * @return the computeName
	 */
	public String getComputeName() {
		return computeName;
	}

	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
	}

	/**
	 * @return the affinityConstraint
	 */
	@JsonProperty("affinityOrAntiAffinityConstraints")
	public List<AffinityConstraint> getAffinityConstraint() {
		return affinityConstraint;
	}

	/**
	 * @return the vcImageId
	 */
	public String getVcImageId() {
		return vcImageId;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * @return the resourceGroupId
	 */
	public String getResourceGroupId() {
		return resourceGroupId;
	}

	/**
	 * @return the locationConstraints
	 */
	public String getLocationConstraints() {
		return locationConstraints;
	}
	
	

	/**
	 * @return the computeFlavourId
	 */
	public String getComputeFlavourId() {
		return computeFlavourId;
	}

	/**
	 * @return the interfaceData
	 */
	public List<VirtualInterfaceData> getInterfaceData() {
		return interfaceData;
	}

	/**
	 * @return the userData
	 */
	public UserData getUserData() {
		return userData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (computeFlavourId == null) throw new MalformattedElementException("Allocate Compute Request without flavour ID");
		for (AffinityConstraint ac : affinityConstraint) ac.isValid();
		for (VirtualInterfaceData vid : interfaceData) vid.isValid();
		if (resourceGroupId == null) throw new MalformattedElementException("Allocate Compute Request without resource group ID");
		if (userData != null) userData.isValid();
	}

}
