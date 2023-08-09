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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.NetworkResourceType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.AffinityConstraint;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet.NetworkSubnetData;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet.VirtualNetworkData;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet.VirtualNetworkPortData;


/**
 * Request to allocate a new virtual network resource on the VIM
 * 
 * REF IFA 005 v2.3.1 - 7.4.1.2
 * 
 * @author nextworks
 *
 */
public class AllocateNetworkRequest implements InterfaceMessage {

	private String networkResourceName;
	private String reservationId;
	private NetworkResourceType networkResourceType;
	private VirtualNetworkData typeNetworkData;
	private VirtualNetworkPortData typeNetworkPortData;
	private NetworkSubnetData typeSubnetData;
	private List<AffinityConstraint> affinityConstraint = new ArrayList<>();
	private Map<String, String> metadata = new HashMap<>();
	private String resourceGroupId;
	private String locationConstraints;
	
	public AllocateNetworkRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param networkResourceName Name provided by the consumer for the virtualised network resource to allocate. It can be used for identifying resources from consumer side.
	 * @param reservationId Identifier of the resource reservation applicable to this virtualised resource management operation.
	 * @param networkResourceType Type of virtualised network resource.
	 * @param typeNetworkData The network data provides information about the particular virtual network resource to create.
	 * @param typeNetworkPortData The network port data provides information about the particular network port to create.
	 * @param typeSubnetData The subnet data provides information about the particular sub-network resource to create.
	 * @param affinityConstraint Element with affinity information of the virtualised network resource to allocate.
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 * @param resourceGroupId Unique identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain. 
	 * @param locationConstraints If present, it defines location constraints for the resource(s) to be allocated, e.g. in what particular resource zone.
	 */
	public AllocateNetworkRequest(String networkResourceName,
			String reservationId,
			NetworkResourceType networkResourceType,
			VirtualNetworkData typeNetworkData,
			VirtualNetworkPortData typeNetworkPortData,
			NetworkSubnetData typeSubnetData,
			List<AffinityConstraint> affinityConstraint,
			Map<String, String> metadata,
			String resourceGroupId,
			String locationConstraints) {
		this.networkResourceName = networkResourceName;
		this.reservationId = reservationId;
		this.networkResourceType = networkResourceType;
		this.typeNetworkData = typeNetworkData;
		this.typeNetworkPortData = typeNetworkPortData;
		this.typeSubnetData = typeSubnetData;
		this.resourceGroupId = resourceGroupId;
		this.locationConstraints = locationConstraints;
		if (affinityConstraint != null) this.affinityConstraint = affinityConstraint;
		if (metadata != null) this.metadata = metadata;
	}

	
	
	/**
	 * @return the networkResourceName
	 */
	public String getNetworkResourceName() {
		return networkResourceName;
	}

	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
	}

	/**
	 * @return the networkResourceType
	 */
	public NetworkResourceType getNetworkResourceType() {
		return networkResourceType;
	}

	/**
	 * @return the typeNetworkData
	 */
	public VirtualNetworkData getTypeNetworkData() {
		return typeNetworkData;
	}

	/**
	 * @return the typeNetworkPortData
	 */
	public VirtualNetworkPortData getTypeNetworkPortData() {
		return typeNetworkPortData;
	}

	/**
	 * @return the typeSubnetData
	 */
	public NetworkSubnetData getTypeSubnetData() {
		return typeSubnetData;
	}

	/**
	 * @return the affinityConstraint
	 */
	@JsonProperty("affinityOrAntiAffinityConstraints")
	public List<AffinityConstraint> getAffinityConstraint() {
		return affinityConstraint;
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

	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceGroupId == null)
			throw new MalformattedElementException("Allocate Network Request without resource group ID");
		if (affinityConstraint != null) {
			for (AffinityConstraint a : affinityConstraint) a.isValid();
		}
		if (typeNetworkData != null) typeNetworkData.isValid();
		if (typeNetworkPortData != null) typeNetworkPortData.isValid();
		if (typeSubnetData != null) typeSubnetData.isValid();
		if ((networkResourceType == NetworkResourceType.NETWORK) && (typeNetworkData == null))
			throw new MalformattedElementException("Allocate network request without network data");
		if ((networkResourceType == NetworkResourceType.PORT) && (typeNetworkPortData == null))
			throw new MalformattedElementException("Allocate network request without network port data");
		if ((networkResourceType == NetworkResourceType.SUBNET) && (typeSubnetData == null))
			throw new MalformattedElementException("Allocate network request without subnet data");
	}

}
