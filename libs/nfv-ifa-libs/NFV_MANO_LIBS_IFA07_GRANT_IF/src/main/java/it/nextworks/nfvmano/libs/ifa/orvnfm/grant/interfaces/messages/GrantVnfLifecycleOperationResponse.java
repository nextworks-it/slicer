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
package it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.elements.ExtManagedVirtualLinkData;
import it.nextworks.nfvmano.libs.ifa.common.elements.ExtVirtualLinkData;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VimConnectionInfo;
import it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.elements.GrantInfo;
import it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.elements.VimAssets;
import it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.elements.ZoneGroupInfo;
import it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.elements.ZoneInfo;

/**
 * Grant response from the NFVO to the VNFM.
 * 
 * REF IFA 007 v2.3.1 - 6.3.3
 * 
 * @author nextworks
 *
 */
public class GrantVnfLifecycleOperationResponse implements InterfaceMessage {

	private List<VimConnectionInfo> vim = new ArrayList<>();
	private List<ZoneInfo> zone = new ArrayList<>();
	private List<ZoneGroupInfo> zoneGroup = new ArrayList<>();
	private String computeReservationId;
	private String networkReservationId;
	private String storageReservationId;
	private List<GrantInfo> addResource = new ArrayList<>();
	private List<GrantInfo> removeResource = new ArrayList<>();
	private List<GrantInfo> tempResource = new ArrayList<>();
	private List<GrantInfo> updateResource = new ArrayList<>();
	private VimAssets vimAssets;
	private List<ExtVirtualLinkData> extVirtualLink = new ArrayList<>();
	private List<ExtManagedVirtualLinkData> extManagedVirtualLink = new ArrayList<>();
	private Map<String, String> additionalParam = new HashMap<>();
	
	public GrantVnfLifecycleOperationResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param vim Provides information regarding VIM instances where the resources are approved to be allocated by the VNFM, and provides parameters to access these VIM instances.
	 * @param zone Identifies resource zones where the resources are approved to be allocated by the VNFM.
	 * @param zoneGroup Information about groups of resource zones that are related and that the NFVO has chosen to fulfil a zoneGroup constraint in the GrantVnfLifecycleOperation request.
	 * @param computeReservationId Information that identifies a reservation applicable to the compute resource requirements of the corresponding grant request
	 * @param networkReservationId Information that identifies a reservation applicable to the network resource requirements of the corresponding grant request
	 * @param storageReservationId Information that identifies a reservation applicable to the storage resource requirements of the corresponding grant request
	 * @param addResource List of resources that are approved to be added, with one entry per resource.
	 * @param tempResource List of resources that are approved to be temporarily instantiated during the runtime of the lifecycle operation, with one entry per resource.
	 * @param removeResource List of resources that are approved to be removed, with one entry per resource.
	 * @param updateResource List of resources that are approved to be modified, with one entry per resource.
	 * @param vimAssets Information about assets for the VNF that are managed by the NFVO in the VIM, such as software images and virtualised compute resource flavours.
	 * @param extVirtualLink Information about external VLs to connect the VNF to
	 * @param extManagedVirtualLink Information about internal VLs that are managed by other entities than the VNFM
	 * @param additionalParam Additional parameters passed by the NFVO, specific to the VNF and the LCM operation.
	 */
	public GrantVnfLifecycleOperationResponse(List<VimConnectionInfo> vim,
			List<ZoneInfo> zone,
			List<ZoneGroupInfo> zoneGroup,
			String computeReservationId,
			String networkReservationId,
			String storageReservationId,
			List<GrantInfo> addResource,
			List<GrantInfo> tempResource,
			List<GrantInfo> removeResource,
			List<GrantInfo> updateResource,
			VimAssets vimAssets,
			List<ExtVirtualLinkData> extVirtualLink,
			List<ExtManagedVirtualLinkData> extManagedVirtualLink,
			Map<String, String> additionalParam) { 
		if (vim != null) this.vim = vim;
		if (zone != null) this.zone = zone;
		if (zoneGroup != null) this.zoneGroup = zoneGroup;
		this.computeReservationId = computeReservationId;
		this.networkReservationId = networkReservationId;
		this.storageReservationId = storageReservationId;
		if (addResource != null) this.addResource = addResource;
		if (tempResource != null) this.tempResource = tempResource;
		if (removeResource != null) this.removeResource = removeResource;
		if (updateResource != null) this.updateResource = updateResource;
		this.vimAssets = vimAssets;
		if (extVirtualLink != null) this.extVirtualLink = extVirtualLink;
		if (extManagedVirtualLink != null) this.extManagedVirtualLink = extManagedVirtualLink;
		if (additionalParam != null) this.additionalParam = additionalParam;
	}

	
	
	/**
	 * @return the vim
	 */
	public List<VimConnectionInfo> getVim() {
		return vim;
	}

	/**
	 * @return the zone
	 */
	public List<ZoneInfo> getZone() {
		return zone;
	}

	/**
	 * @return the zoneGroup
	 */
	public List<ZoneGroupInfo> getZoneGroup() {
		return zoneGroup;
	}

	/**
	 * @return the computeReservationId
	 */
	public String getComputeReservationId() {
		return computeReservationId;
	}

	/**
	 * @return the networkReservationId
	 */
	public String getNetworkReservationId() {
		return networkReservationId;
	}

	/**
	 * @return the storageReservationId
	 */
	public String getStorageReservationId() {
		return storageReservationId;
	}

	/**
	 * @return the addResource
	 */
	public List<GrantInfo> getAddResource() {
		return addResource;
	}

	/**
	 * @return the removeResource
	 */
	public List<GrantInfo> getRemoveResource() {
		return removeResource;
	}

	/**
	 * @return the tempResource
	 */
	public List<GrantInfo> getTempResource() {
		return tempResource;
	}

	/**
	 * @return the updateResource
	 */
	public List<GrantInfo> getUpdateResource() {
		return updateResource;
	}

	/**
	 * @return the vimAssets
	 */
	public VimAssets getVimAssets() {
		return vimAssets;
	}

	/**
	 * @return the extVirtualLink
	 */
	public List<ExtVirtualLinkData> getExtVirtualLink() {
		return extVirtualLink;
	}

	/**
	 * @return the extManagedVirtualLink
	 */
	public List<ExtManagedVirtualLinkData> getExtManagedVirtualLink() {
		return extManagedVirtualLink;
	}

	/**
	 * @return the additionalParam
	 */
	public Map<String, String> getAdditionalParam() {
		return additionalParam;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (VimConnectionInfo v : vim) v.isValid();
		for (ZoneInfo z : zone) z.isValid();
		for (ZoneGroupInfo z : zoneGroup) z.isValid();
		for (GrantInfo gi : addResource) gi.isValid();
		for (GrantInfo gi : tempResource) gi.isValid();
		for (GrantInfo gi : removeResource) gi.isValid();
		for (GrantInfo gi : updateResource) gi.isValid();
		if (vimAssets != null) vimAssets.isValid();
		for (ExtVirtualLinkData vl : extVirtualLink) vl.isValid();
		for (ExtManagedVirtualLinkData vl : extManagedVirtualLink) vl.isValid();
	}

}
