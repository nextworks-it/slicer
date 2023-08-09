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

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.NetworkSegmentType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * REF IFA 005 v2.3.1 - 8.8.4.4
 * 
 * @author nextworks
 *
 */
public class VirtualNetworkAttributesReservationData implements InterfaceInformationElement {

	private int bandwidth;
	private NetworkSegmentType networkType;
	private String segmentType;
	private boolean isShared;
	private Map<String, String> metadata = new HashMap<>();
	
	public VirtualNetworkAttributesReservationData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param bandwidth Minimum network bitrate (in Mbps).
	 * @param networkType The type of network that maps to the virtualised network to be reserved
	 * @param segmentType The isolated segment for the virtualised network to be reserved.
	 * @param isShared It defines whether the virtualised network to be reserved is shared among consumers.
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public VirtualNetworkAttributesReservationData(int bandwidth,
			NetworkSegmentType networkType,
			String segmentType,
			boolean isShared,
			Map<String, String> metadata) {
		this.bandwidth = bandwidth;
		this.networkType = networkType;
		this.segmentType = segmentType;
		this.isShared = isShared;
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
	 * @return the isShared
	 */
	public boolean isShared() {
		return isShared;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
