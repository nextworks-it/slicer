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
package it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.LcmNotificationType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.elements.AffectedVirtualLink;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.elements.AffectedVirtualStorage;
import it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.elements.AffectedVnfc;
import it.nextworks.nfvmano.libs.ifa.records.vnfinfo.ExtVirtualLinkInfo;

/**
 * This notification informs the receiver of changes in the VNF lifecycle 
 * caused by VNF lifecycle management operation occurrences.
 * 
 * REF IFA 007 v2.3.1 - 8.6.2
 * 
 * @author nextworks
 *
 */
public class VnfLcmOperationOccurrenceNotification implements InterfaceMessage {

	private LcmNotificationType status;
	private String vnfInstanceId;
	private String operation;
	private boolean isAutomaticInvocation;
	private String lifecycleOperationOccurrenceId;
	private List<AffectedVnfc> affectedVnfc = new ArrayList<>();
	private List<AffectedVirtualLink> affectedVirtualLink = new ArrayList<>();
	private List<AffectedVirtualStorage> affectedVirtualStorage = new ArrayList<>();
	private String changedInfo;
	private List<ExtVirtualLinkInfo> changedExtConnectivity = new ArrayList<>();
	
	
	public VnfLcmOperationOccurrenceNotification() { }
	
	/**
	 * Constructor
	 * 
	 * @param status Indicates whether this notification reports about the start of a lifecycle management operation occurrence or the result of a lifecycle management operation occurrence.
	 * @param vnfInstanceId The identifier of the VNF instance affected.
	 * @param operation The lifecycle management operation.
	 * @param isAutomaticInvocation Set to true if this VNF LCM operation occurrence has been triggered by an automated procedure inside the VNFM
	 * @param lifecycleOperationOccurrenceId The identifier of the VNF lifecycle management operation occurrence associated to the notification.
	 * @param affectedVnfc Information about VNFC instances that were affected during the execution of the lifecycle management operation, if this notification represents the result of a lifecycle management operation occurrence.
	 * @param affectedVirtualLink Information about VL instances that were affected during the execution of the lifecycle management operation, if this notification represents the result of a lifecycle management operation occurrence.
	 * @param affectedVirtualStorage Information about virtualised storage instances that were affected during the execution of the lifecycle management operation, if this notification represents the result of a lifecycle management operation occurrence.
	 * @param changedInfo Information about the changed VNF information, including changed VNF configurable properties, if this notification represents the result of a lifecycle management operation occurrence.
	 * @param changedExtConnectivity Information about changed external connectivity, if this notification represents the result of a lifecycle management operation occurrence. Only relevant for the "Change External VNF Connectivity" operation.
	 */
	public VnfLcmOperationOccurrenceNotification(LcmNotificationType status,
			String vnfInstanceId,
			String operation,
			boolean isAutomaticInvocation,
			String lifecycleOperationOccurrenceId,
			List<AffectedVnfc> affectedVnfc,
			List<AffectedVirtualLink> affectedVirtualLink,
			List<AffectedVirtualStorage> affectedVirtualStorage,
			String changedInfo,
			List<ExtVirtualLinkInfo> changedExtConnectivity) { 
		this.status = status;
		this.vnfInstanceId = vnfInstanceId;
		this.operation = operation;
		this.isAutomaticInvocation = isAutomaticInvocation;
		this.lifecycleOperationOccurrenceId = lifecycleOperationOccurrenceId;
		if (affectedVnfc != null) this.affectedVnfc = affectedVnfc;
		if (affectedVirtualLink != null) this.affectedVirtualLink = affectedVirtualLink;
		if (affectedVirtualStorage != null) this.affectedVirtualStorage = affectedVirtualStorage;
		this.changedInfo = changedInfo;
		if (changedExtConnectivity != null) this.changedExtConnectivity = changedExtConnectivity;
	}
	
	

	/**
	 * @return the status
	 */
	public LcmNotificationType getStatus() {
		return status;
	}

	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
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
	 * @return the affectedVnfc
	 */
	public List<AffectedVnfc> getAffectedVnfc() {
		return affectedVnfc;
	}

	/**
	 * @return the affectedVirtualLink
	 */
	public List<AffectedVirtualLink> getAffectedVirtualLink() {
		return affectedVirtualLink;
	}

	/**
	 * @return the affectedVirtualStorage
	 */
	public List<AffectedVirtualStorage> getAffectedVirtualStorage() {
		return affectedVirtualStorage;
	}

	/**
	 * @return the changedInfo
	 */
	public String getChangedInfo() {
		return changedInfo;
	}

	/**
	 * @return the changedExtConnectivity
	 */
	public List<ExtVirtualLinkInfo> getChangedExtConnectivity() {
		return changedExtConnectivity;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("VNF LCM operation notification without VNF instance ID");
		if (operation == null) throw new MalformattedElementException("VNF LCM operation notification without operation");
		if (lifecycleOperationOccurrenceId == null) throw new MalformattedElementException("VNF LCM operation notification without operation ID");
		for (AffectedVnfc vnfc:affectedVnfc) vnfc.isValid();
		for (AffectedVirtualLink vl:affectedVirtualLink) vl.isValid();
		for (AffectedVirtualStorage s : affectedVirtualStorage) s.isValid();
		for (ExtVirtualLinkInfo vli:changedExtConnectivity) vli.isValid();
	}
	
}
