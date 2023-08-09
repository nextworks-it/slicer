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
package it.nextworks.nfvmano.libs.vs.common.topology;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This entity models a network path to interconnect two VMs
 * attached to the same virtual link.
 * 
 * @author nextworks
 *
 */
public class NetworkPath {
	
	private String networkPathId;
	private List<NetworkPathEndPoint> endPoints = new ArrayList<>();
	
//	@Embedded
//	private NetworkPathEdges endPoints;

	private List<NetworkPathHop> hops = new ArrayList<>();

	private String nsVirtualLinkDescriptorId;
	
	private boolean backup;

	public NetworkPath() {
		//JPA only
	}
	
	/**
	 * Constructor
	 *
	 * @param networkPathId unique ID of the network path
	 * @param endPoints source and destination of the path
	 * @param hops list of hops along the path
	 * @param nsVirtualLinkDescriptorId ID of the VL interconnecting source and destination
	 * @param isBackup true if it is a backup path
	 */
	public NetworkPath(String networkPathId,
			List<NetworkPathEndPoint> endPoints,
			List<NetworkPathHop> hops,
			String nsVirtualLinkDescriptorId,
			boolean isBackup) {
		this.networkPathId = networkPathId;
		if (endPoints != null) this.endPoints = endPoints;
		this.nsVirtualLinkDescriptorId = nsVirtualLinkDescriptorId;
		if (hops != null) this.hops = hops;
		this.backup = isBackup;
	}
	
	/**
	 * Constructor
	 * 
	 * @param networkPathId unique ID of the network path
	 * @param endPoints source and destination of the path
	 * @param nsVirtualLinkDescriptorId ID of the VL interconnecting source and destination
	 * @param isBackup true if it is a backup path
	 */
	public NetworkPath(String networkPathId,
			List<NetworkPathEndPoint> endPoints,
			String nsVirtualLinkDescriptorId,
			boolean isBackup) {
		this.networkPathId = networkPathId;
		if (endPoints != null) this.endPoints = endPoints;
		this.nsVirtualLinkDescriptorId = nsVirtualLinkDescriptorId;
		this.backup = isBackup;
	}

	/**
	 * @return the networkPathId
	 */
	public String getNetworkPathId() {
		return networkPathId;
	}

	/**
	 * @return the endPoints
	 */
	public List<NetworkPathEndPoint> getEndPoints() {
		return endPoints;
	}

	/**
	 * @return the hops
	 */
	public List<NetworkPathHop> getHops() {
		return hops;
	}

	/**
	 * @return the nsVirtualLinkDescriptorId
	 */
	public String getNsVirtualLinkDescriptorId() {
		return nsVirtualLinkDescriptorId;
	}

	/**
	 * @return the isBackup
	 */
	@JsonProperty("backup")
	public boolean isBackup() {
		return backup;
	}
}
