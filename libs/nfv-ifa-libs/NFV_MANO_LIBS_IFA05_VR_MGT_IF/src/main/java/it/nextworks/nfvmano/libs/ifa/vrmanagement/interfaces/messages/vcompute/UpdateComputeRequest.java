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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualNetworkInterface;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualNetworkInterfaceData;


/**
 * Request to update a compute resource
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.1.4
 * 
 * @author nextworks
 *
 */
public class UpdateComputeRequest implements InterfaceMessage {

	private String computeId;
	private List<VirtualNetworkInterfaceData> networkInterfaceNew = new ArrayList<>();
	private List<VirtualNetworkInterface> networkInterfaceUpdate = new ArrayList<>();
	private Map<String, String> metadata = new HashMap<>();
	
	public UpdateComputeRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param computeId Identifier of the virtualised compute resource to update.
	 * @param networkInterfaceNew The new virtual network interface(s) to add to the compute resource.
	 * @param networkInterfaceUpdate The virtual network interface(s) to update on the compute resource.
	 * @param metadata List of metadata key-value pairs, used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public UpdateComputeRequest(String computeId,
			List<VirtualNetworkInterfaceData> networkInterfaceNew,
			List<VirtualNetworkInterface> networkInterfaceUpdate,
			Map<String, String> metadata) {	
		this.computeId = computeId;
		if (networkInterfaceNew != null) this.networkInterfaceNew = networkInterfaceNew;
		if (networkInterfaceUpdate != null) this.networkInterfaceUpdate = networkInterfaceUpdate;
		if (metadata != null) this.metadata = metadata;
	}
	
	

	/**
	 * @return the computeId
	 */
	public String getComputeId() {
		return computeId;
	}

	/**
	 * @return the networkInterfaceNew
	 */
	public List<VirtualNetworkInterfaceData> getNetworkInterfaceNew() {
		return networkInterfaceNew;
	}

	/**
	 * @return the networkInterfaceUpdate
	 */
	public List<VirtualNetworkInterface> getNetworkInterfaceUpdate() {
		return networkInterfaceUpdate;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (computeId == null) throw new MalformattedElementException("Update compute request without compute ID");
		for (VirtualNetworkInterfaceData vnic : networkInterfaceNew) vnic.isValid();
		for (VirtualNetworkInterface vnic : networkInterfaceUpdate) vnic.isValid();
	}

}
