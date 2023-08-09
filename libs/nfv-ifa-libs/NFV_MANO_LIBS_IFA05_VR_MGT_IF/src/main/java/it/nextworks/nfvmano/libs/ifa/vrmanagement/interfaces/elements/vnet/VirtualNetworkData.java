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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.NetworkSegmentType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 *  REF IFA 005 v2.3.1 - 8.4.4.2
 *  
 * 
 * @author nextworks
 *
 */
public class VirtualNetworkData implements InterfaceInformationElement {

	private int bandwidth; //in Mbps
	private NetworkSegmentType networkType;
	private String segmentType;
	private List<NetworkQoS> networkQoS = new ArrayList<>();
	private boolean isShared;
	private List<String> sharingCriteria = new ArrayList<>();
	private List<NetworkSubnetData> layer3Attributes = new ArrayList<>();
	private Map<String, String> metadata = new HashMap<>();
	
	public VirtualNetworkData() { }

	/**
	 * Constructor
	 * 
	 * @param bandwidth Minimum network bandwidth (in Mbps).
	 * @param networkType The type of network that maps to the virtualised network.
	 * @param segmentType The isolated segment for the virtualised network.
	 * @param networkQoS Element providing information about Quality of Service attributes that the network shall support.
	 * @param isShared It defines whether the virtualised network is shared among consumers.
	 * @param sharingCriteria Only present for shared networks. Indicate the sharing criteria for this network. This criteria might be a list of authorized consumers.
	 * @param layer3Attributes The attribute allows setting up a network providing defined layer 3 connectivity.
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public VirtualNetworkData(int bandwidth,
			NetworkSegmentType networkType,
			String segmentType,
			List<NetworkQoS> networkQoS,
			boolean isShared,
			List<String> sharingCriteria,
			List<NetworkSubnetData> layer3Attributes,
			Map<String, String> metadata) {
		this.bandwidth = bandwidth;
		this.networkType = networkType;
		this.segmentType = segmentType;
		this.isShared = isShared;
		if (networkQoS != null) this.networkQoS = networkQoS;
		if (sharingCriteria != null) this.sharingCriteria = sharingCriteria;
		if (layer3Attributes != null) this.layer3Attributes = layer3Attributes;
		if (metadata != null) this.metadata = metadata;
	}
	
	/**
	 * @return the bandwidth
	 */
	public int getBandwidth() {
		return bandwidth;
	}



	/**
	 * @return the networkType
	 */
	public NetworkSegmentType getNetworkType() {
		return networkType;
	}



	/**
	 * @return the segmentType
	 */
	public String getSegmentType() {
		return segmentType;
	}



	/**
	 * @return the networkQoS
	 */
	public List<NetworkQoS> getNetworkQoS() {
		return networkQoS;
	}



	/**
	 * @return the isShared
	 */
	public boolean isShared() {
		return isShared;
	}



	/**
	 * @return the sharingCriteria
	 */
	public List<String> getSharingCriteria() {
		return sharingCriteria;
	}



	/**
	 * @return the layer3Attributes
	 */
	public List<NetworkSubnetData> getLayer3Attributes() {
		return layer3Attributes;
	}



	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (networkQoS != null) {
			for (NetworkQoS q : networkQoS) q.isValid();
		}
		if (layer3Attributes != null) {
			for (NetworkSubnetData l : layer3Attributes) l.isValid();
		}
	}

}
