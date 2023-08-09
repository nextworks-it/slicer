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

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.VnfLcmOperation;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.elements.PlacementConstraint;
import it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.elements.ResourceDefinition;
import it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.elements.VimConstraint;

/**
 * Grant request from the VNFM to the NFVO.
 * 
 * REF IFA 007 v2.3.1 - 6.3.2
 * 
 * @author nextworks
 *
 */
public class GrantVnfLifecycleOperationRequest implements InterfaceMessage {

	private String vnfInstanceId;
	private String vnfdId;
	private String flavourId;
	private VnfLcmOperation lifecycleOperation;
	private boolean isAutomaticInvocation;
	private String lifecycleOperationOccurrenceId;
	private String instantiationLevelId;
	private List<ResourceDefinition> addResource = new ArrayList<ResourceDefinition>();
	private List<ResourceDefinition> tempResource = new ArrayList<ResourceDefinition>();
	private List<ResourceDefinition> removeResource = new ArrayList<ResourceDefinition>();
	private List<ResourceDefinition> updateResource = new ArrayList<ResourceDefinition>();
	private List<PlacementConstraint> placementConstraint = new ArrayList<>();
	private List<VimConstraint> vimConstraint = new ArrayList<>();
	private Map<String, String> additionalParam = new HashMap<>();
	
	public GrantVnfLifecycleOperationRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance which this grant request is related to.
	 * @param vnfdId Identifier of the VNFD that defines the VNF for which the LCM operation is to be granted.
	 * @param flavourId Identifier of the VNF deployment flavour (DF) of the VNFD that defines the VNF for which the LCM operation is to be granted.
	 * @param lifecycleOperation The lifecycle management operation for which granting is requested.
	 * @param isAutomaticInvocation Set to true if this VNF LCM operation occurrence has been triggered by an automated procedure inside the VNFM, false otherwise.
	 * @param lifecycleOperationOccurrenceId The identifier of the VNF lifecycle operation occurrence associated to the GrantVnfLifecycleOperationRequest.
	 * @param instantiationLevelId If the granting request is requested for InstantiateVNF, the identifier of the instantiation level may be provided as an alternative way to define the resources to be added.
	 * @param addResource List of resource definitions in the VNFD that will be added by the LCM operation which is related to this grant request, with one entry per resource.
	 * @param tempResource List of resource definitions in the VNFD that will be temporarily instantiated during the runtime of the LCM operation which is related to this grant request, with one entry per resource
	 * @param removeResource List of resource definitions that will be removed by the LCM operation which is related to this grant request, with one entry per resource.
	 * @param updateResource List of resource definitions that will be modified by the LCM operation which is related to this grant request, with one entry per resource.
	 * @param placementConstraint Placement constraints that the VNFM may send to the NFVO in order to influence the resource placement decision.
	 * @param vimConstraint Used by the VNFM to require specify that multiple resources shall be are managed by the same VIM.
	 * @param additionalParam Additional parameters passed by the VNFM, specific to the VNF and the LCM operation.
	 */
	public GrantVnfLifecycleOperationRequest(String vnfInstanceId,
			String vnfdId,
			String flavourId,
			VnfLcmOperation lifecycleOperation,
			boolean isAutomaticInvocation,
			String lifecycleOperationOccurrenceId,
			String instantiationLevelId,
			List<ResourceDefinition> addResource,
			List<ResourceDefinition> tempResource,
			List<ResourceDefinition> removeResource,
			List<ResourceDefinition> updateResource,
			List<PlacementConstraint> placementConstraint,
			List<VimConstraint> vimConstraint,
			Map<String, String> additionalParam) { 
		this.vnfInstanceId = vnfInstanceId;
		this.vnfdId = vnfdId;
		this.flavourId = flavourId;
		this.lifecycleOperation = lifecycleOperation;
		this.isAutomaticInvocation = isAutomaticInvocation;
		this.lifecycleOperationOccurrenceId = lifecycleOperationOccurrenceId;
		this.instantiationLevelId = instantiationLevelId;
		if (additionalParam != null) this.additionalParam = additionalParam;
		if (addResource != null) this.addResource = addResource;
		if (tempResource != null) this.tempResource = tempResource;
		if (removeResource != null) this.removeResource = removeResource;
		if (updateResource != null) this.updateResource = updateResource;
		if (placementConstraint != null) this.placementConstraint = placementConstraint;
		if (vimConstraint != null) this.vimConstraint = vimConstraint;
	}
	
	

	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the vnfdId
	 */
	public String getVnfdId() {
		return vnfdId;
	}

	/**
	 * @return the flavourId
	 */
	public String getFlavourId() {
		return flavourId;
	}

	/**
	 * @return the lifecycleOperation
	 */
	public VnfLcmOperation getLifecycleOperation() {
		return lifecycleOperation;
	}

	
	
	/**
	 * @return the isAutomaticInvocation
	 */
	@JsonProperty("isAutomaticInvocation")
	public boolean isAutomaticInvocation() {
		return isAutomaticInvocation;
	}

	/**
	 * @return the lifecycleOperationOccurrenceId
	 */
	public String getLifecycleOperationOccurrenceId() {
		return lifecycleOperationOccurrenceId;
	}

	/**
	 * @return the instantiationLevelId
	 */
	public String getInstantiationLevelId() {
		return instantiationLevelId;
	}

	/**
	 * @return the addResource
	 */
	public List<ResourceDefinition> getAddResource() {
		return addResource;
	}

	/**
	 * @return the tempResource
	 */
	public List<ResourceDefinition> getTempResource() {
		return tempResource;
	}

	/**
	 * @return the removeResource
	 */
	public List<ResourceDefinition> getRemoveResource() {
		return removeResource;
	}

	/**
	 * @return the updateResource
	 */
	public List<ResourceDefinition> getUpdateResource() {
		return updateResource;
	}

	/**
	 * @return the placementConstraint
	 */
	public List<PlacementConstraint> getPlacementConstraint() {
		return placementConstraint;
	}

	/**
	 * @return the vimConstraint
	 */
	public List<VimConstraint> getVimConstraint() {
		return vimConstraint;
	}

	/**
	 * @return the additionalParam
	 */
	public Map<String, String> getAdditionalParam() {
		return additionalParam;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("Grant request without VNF instance ID");
		if (vnfdId == null) throw new MalformattedElementException("Grant request without VNFD ID");
		if (lifecycleOperationOccurrenceId == null) throw new MalformattedElementException("Grant request without operation ID");
		for (ResourceDefinition rd : addResource) rd.isValid();
		for (ResourceDefinition rd : tempResource) rd.isValid();
		for (ResourceDefinition rd : removeResource) rd.isValid();
		for (ResourceDefinition rd : updateResource) rd.isValid();
		for (VimConstraint vc : vimConstraint) vc.isValid();
		for (PlacementConstraint pc : placementConstraint) pc.isValid();
	}

}
