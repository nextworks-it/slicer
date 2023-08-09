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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.AddVnffgData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.AssocNewNsdVersionData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.ChangeExtVnfConnectivityData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.ChangeNsFlavourData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.ChangeVnfFlavourData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.InstantiateVnfData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.ModifyVnfInfoData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.MoveVnfInstanceData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.OperateVnfData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.SapData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.UpdateVnffgData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.VnfInstanceData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.enums.NsUpdateType;


/**
 * Message to request the update of a Network Service instance
 * 
 * REF IFA 013 v2.3.1 - 7.3.5
 * 
 * @author nextworks
 *
 */
public class UpdateNsRequest implements InterfaceMessage {

	private String nsInstanceId;
	private NsUpdateType updateType;
	private List<VnfInstanceData> addVnfInstance = new ArrayList<>();
	private List<String> removeVnfInstanceId = new ArrayList<>();
	private List<InstantiateVnfData> instantiateVnfData = new ArrayList<>();
	private List<ChangeVnfFlavourData> changeVnfFlavourData = new ArrayList<>();
	private List<OperateVnfData> operateVnfData = new ArrayList<>();
	private List<ModifyVnfInfoData> modifyVnfInfoData = new ArrayList<>();
	private List<ChangeExtVnfConnectivityData> changeExtVnfConnectivityData = new ArrayList<>();
	private List<SapData> addSap = new ArrayList<>();
	private List<String> removeSapId = new ArrayList<>();
	private List<String> addNestedNsId = new ArrayList<>();
	private List<String> removeNestedNsId = new ArrayList<>();
	private AssocNewNsdVersionData assocNewNsdVersionData;
	private List<MoveVnfInstanceData> moveVnfInstanceData = new ArrayList<>();
	private List<AddVnffgData> addVnffg = new ArrayList<>();
	private List<String> removeVnffgId = new ArrayList<>();
	private List<UpdateVnffgData> updateVnffg = new ArrayList<>();
	private ChangeNsFlavourData changeNsFlavourData;
	private Date updateTime;
	
	public UpdateNsRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInstanceId Identifier of the NS instance being updated.
	 * @param updateType Specifies the type of update.
	 * @param addVnfInstance Specify an existing VNF instance to be added to the NS instance.
	 * @param removeVnfInstanceId Specify an existing VNF instance to be removed from the NS instance. The parameter contains the identifier(s) of the VNF instances to be removed.
	 * @param instantiateVnfData Specify the new VNF to be instantiated. This parameter can be used e.g. for the bottom-up NS creation.
	 * @param changeVnfFlavourData Specify the new DF of the VNF instance to be changed to.
	 * @param operateVnfData Specify the state of the VNF instance to be changed.
	 * @param modifyVnfInfoData Specify the VNF Information parameter of VNF instance to be modified.
	 * @param changeExtVnfConnectivityData Specify the new external connectivity data of the VNF instance to be changed.
	 * @param addSap Specify a new SAP to be added to the NS instance.
	 * @param removeSapId Specify an existing SAP to be removed from the NS instance.
	 * @param addNestedNsId Specify an existing nested NS instance to be added to (nested within) the NS instance.
	 * @param removeNestedNsId Specify an existing nested NS instance to be removed from the NS instance.
	 * @param assocNewNsdVersionData Specify the new NSD to be used for the NS instance.
	 * @param moveVnfInstanceData Specify existing VNF instance to be moved from one NS instance to another NS instance.
	 * @param addVnffg Specify the new VNFFG to be created to the NS Instance
	 * @param removeVnffgId Identifier of an existing VNFFG to be removed from the NS Instance.
	 * @param updateVnffg Specify the new VNFFG Information data to be updated for a VNFFG of the NS Instance
	 * @param changeNsFlavourData Specifies the new DF to be applied to the NS instance.
	 * @param updateTime Timestamp indicating the update time of the NS, i.e. the NS will be updated at this timestamp.
	 */
	public UpdateNsRequest(String nsInstanceId,
			NsUpdateType updateType,
			List<VnfInstanceData> addVnfInstance,
			List<String> removeVnfInstanceId,
			List<InstantiateVnfData> instantiateVnfData,
			List<ChangeVnfFlavourData> changeVnfFlavourData,
			List<OperateVnfData> operateVnfData,
			List<ModifyVnfInfoData> modifyVnfInfoData,
			List<ChangeExtVnfConnectivityData> changeExtVnfConnectivityData,
			List<SapData> addSap,
			List<String> removeSapId,
			List<String> addNestedNsId,
			List<String> removeNestedNsId,
			AssocNewNsdVersionData assocNewNsdVersionData,
			List<MoveVnfInstanceData> moveVnfInstanceData,
			List<AddVnffgData> addVnffg,
			List<String> removeVnffgId,
			List<UpdateVnffgData> updateVnffg,
			ChangeNsFlavourData changeNsFlavourData,
			Date updateTime) {
		this.nsInstanceId = nsInstanceId;
		this.updateTime = updateTime;
		this.updateType = updateType;
		if (addVnfInstance != null) this.addVnfInstance = addVnfInstance;
		if (removeVnfInstanceId != null) this.removeVnfInstanceId = removeVnfInstanceId;
		if (instantiateVnfData != null) this.instantiateVnfData = instantiateVnfData;
		if (changeNsFlavourData != null) this.changeNsFlavourData = changeNsFlavourData;
		if (changeVnfFlavourData != null) this.changeVnfFlavourData = changeVnfFlavourData;
		if (operateVnfData != null) this.operateVnfData = operateVnfData;
		if (modifyVnfInfoData != null) this.modifyVnfInfoData = modifyVnfInfoData;
		if (changeExtVnfConnectivityData != null) this.changeExtVnfConnectivityData = changeExtVnfConnectivityData;
		if (addSap != null) this.addSap = addSap;
		if (removeSapId != null) this.removeSapId = removeSapId;
		if (addNestedNsId != null) this.addNestedNsId = addNestedNsId;
		if (removeNestedNsId != null) this.removeNestedNsId = removeNestedNsId;
		this.assocNewNsdVersionData = assocNewNsdVersionData;
		if (moveVnfInstanceData != null) this.moveVnfInstanceData = moveVnfInstanceData;
		if (addVnffg != null) this.addVnffg = addVnffg;
		if (removeVnffgId != null) this.removeVnffgId = removeVnffgId;
		if (updateVnffg != null) this.updateVnffg = updateVnffg;
		this.changeNsFlavourData = changeNsFlavourData;
	}
	
	

	/**
	 * @return the nsInstanceId
	 */
	public String getNsInstanceId() {
		return nsInstanceId;
	}

	/**
	 * @return the updateType
	 */
	public NsUpdateType getUpdateType() {
		return updateType;
	}

	/**
	 * @return the addVnfInstance
	 */
	public List<VnfInstanceData> getAddVnfInstance() {
		return addVnfInstance;
	}

	/**
	 * @return the removeVnfInstanceId
	 */
	public List<String> getRemoveVnfInstanceId() {
		return removeVnfInstanceId;
	}

	/**
	 * @return the instantiateVnfData
	 */
	public List<InstantiateVnfData> getInstantiateVnfData() {
		return instantiateVnfData;
	}

	/**
	 * @return the changeVnfFlavourData
	 */
	public List<ChangeVnfFlavourData> getChangeVnfFlavourData() {
		return changeVnfFlavourData;
	}

	/**
	 * @return the operateVnfData
	 */
	public List<OperateVnfData> getOperateVnfData() {
		return operateVnfData;
	}

	/**
	 * @return the modifyVnfInfoData
	 */
	public List<ModifyVnfInfoData> getModifyVnfInfoData() {
		return modifyVnfInfoData;
	}
	
	/**
	 * @return the changeExtVnfConnectivityData
	 */
	public List<ChangeExtVnfConnectivityData> getChangeExtVnfConnectivityData() {
		return changeExtVnfConnectivityData;
	}

	/**
	 * @return the addSap
	 */
	public List<SapData> getAddSap() {
		return addSap;
	}

	/**
	 * @return the removeSapId
	 */
	public List<String> getRemoveSapId() {
		return removeSapId;
	}

	/**
	 * @return the addNestedNsId
	 */
	public List<String> getAddNestedNsId() {
		return addNestedNsId;
	}

	/**
	 * @return the removeNestedNsId
	 */
	public List<String> getRemoveNestedNsId() {
		return removeNestedNsId;
	}

	/**
	 * @return the assocNewNsdVersionData
	 */
	public AssocNewNsdVersionData getAssocNewNsdVersionData() {
		return assocNewNsdVersionData;
	}

	/**
	 * @return the moveVnfInstanceData
	 */
	public List<MoveVnfInstanceData> getMoveVnfInstanceData() {
		return moveVnfInstanceData;
	}

	/**
	 * @return the addVnffg
	 */
	public List<AddVnffgData> getAddVnffg() {
		return addVnffg;
	}

	/**
	 * @return the removeVnffgId
	 */
	public List<String> getRemoveVnffgId() {
		return removeVnffgId;
	}

	/**
	 * @return the updateVnffg
	 */
	public List<UpdateVnffgData> getUpdateVnffg() {
		return updateVnffg;
	}

	/**
	 * @return the changeNsFlavourData
	 */
	public ChangeNsFlavourData getChangeNsFlavourData() {
		return changeNsFlavourData;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsInstanceId == null) throw new MalformattedElementException("Update NS request without NS instance ID");
		if (updateType == null) throw new MalformattedElementException("Update NS request without update type");
		if (addVnfInstance != null) {
			for (VnfInstanceData i : addVnfInstance) i.isValid();
		}
		if (instantiateVnfData != null) {
			for (InstantiateVnfData i : instantiateVnfData) i.isValid();
		}
		if (changeVnfFlavourData != null) {
			for (ChangeVnfFlavourData i : changeVnfFlavourData) i.isValid();
		}
		if (operateVnfData != null) {
			for (OperateVnfData i : operateVnfData) i.isValid();
		}
		if (modifyVnfInfoData != null) {
			for (ModifyVnfInfoData i : modifyVnfInfoData) i.isValid();
		}
		if (changeExtVnfConnectivityData != null) {
			for (ChangeExtVnfConnectivityData i : changeExtVnfConnectivityData) i.isValid();
		}
		if (addSap != null) {
			for (SapData i : addSap) i.isValid();
		}
		if (assocNewNsdVersionData != null) assocNewNsdVersionData.isValid();
		if (moveVnfInstanceData != null) {
			for (MoveVnfInstanceData i : moveVnfInstanceData) i.isValid();
		}
		if (addVnffg != null) {
			for (AddVnffgData i : addVnffg) i.isValid();
		}
		if (updateVnffg != null) {
			for (UpdateVnffgData i : updateVnffg) i.isValid();
		}
		if (changeNsFlavourData != null) changeNsFlavourData.isValid();
	}

}
