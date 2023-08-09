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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.LcmNotificationType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.AffectedNs;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.AffectedPnf;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.AffectedSap;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.AffectedVirtualLink;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.AffectedVnf;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.AffectedVnffg;

/**
 * This notification informs the receiver of changes in the NS lifecycle.
 * 
 * REF IFA 013 v2.3.1 - 8.3.2.2
 * 
 * @author nextworks
 *
 */
public class NsLifecycleChangeNotification implements InterfaceMessage {
	
	private String nsInstanceId;
	private String lifecycleOperationOccurrenceId;
	private String operation;
	private LcmNotificationType status;
	private List<AffectedVnf> affectedVnf = new ArrayList<>();
	private List<AffectedPnf> affectedPnf = new ArrayList<>();
	private List<AffectedVirtualLink> affectedVl = new ArrayList<>();
	private List<AffectedVnffg> affectedVnffg = new ArrayList<>();
	private List<AffectedNs> affectedNs = new ArrayList<>();
	private List<AffectedSap> affectedSap = new ArrayList<>();
	

	public NsLifecycleChangeNotification() { }
	
	/**
	 * Constructor
	 * 
	 * @param nsInstanceId Identifier of the NS instance affected.
	 * @param lifecycleOperationOccurrenceId Identifier of the NS lifecycle operation occurrence associated to the notification.
	 * @param operation The lifecycle operation.
	 * @param status Indicates whether this notification reports about the start of a lifecycle operation occurrence or the result of a lifecycle operation occurence.
	 * @param affectedVnf Information about the VNF instances that were affected during the lifecycle operation, if this notification represents the result of a lifecycle operation.
	 * @param affectedPnf Information about the PNF instances that were affected during the lifecycle operation, if this notification represents the result of a lifecycle operation.
	 * @param affectedVl Information about the VL instances that were affected during the lifecycle operation, if this notification represents the result of a lifecycle operation.
	 * @param affectedVnffg Information about the VNFFG instances that were affected during the lifecycle operation, if this notification represents the result of a lifecycle operation.
	 * @param affectedNs Information about the nested NS instances that were affected during the lifecycle operation, if this notification represents the result of a lifecycle operation.
	 * @param affectedSap Information about the SAP instances that were affected during the lifecycle operation, if this notification represents the result of a lifecycle operation.
	 */
	public NsLifecycleChangeNotification(String nsInstanceId,
			String lifecycleOperationOccurrenceId,
			String operation,
			LcmNotificationType status,
			List<AffectedVnf> affectedVnf,
			List<AffectedPnf> affectedPnf,
			List<AffectedVirtualLink> affectedVl,
			List<AffectedVnffg> affectedVnffg,
			List<AffectedNs> affectedNs,
			List<AffectedSap> affectedSap) { 
		this.nsInstanceId = nsInstanceId;
		this.lifecycleOperationOccurrenceId = lifecycleOperationOccurrenceId;
		this.operation = operation;
		this.status = status;
		if (affectedVnf != null) this.affectedVnf = affectedVnf;
		if (affectedPnf != null) this.affectedPnf = affectedPnf;
		if (affectedVnffg != null) this.affectedVnffg = affectedVnffg;
		if (affectedVl != null) this.affectedVl = affectedVl;
		if (affectedNs != null) this.affectedNs = affectedNs;
		if (affectedSap != null) this.affectedSap = affectedSap;
	}
	
	

	/**
	 * @return the nsInstanceId
	 */
	@JsonProperty("nsInstanceId")
	public String getNsInstanceId() {
		return nsInstanceId;
	}

	/**
	 * @return the lifecycleOperationOccurrenceId
	 */
	@JsonProperty("lifecycleOperationOccurrenceId")
	public String getLifecycleOperationOccurrenceId() {
		return lifecycleOperationOccurrenceId;
	}

	/**
	 * @return the operation
	 */
	@JsonProperty("operation")
	public String getOperation() {
		return operation;
	}

	/**
	 * @return the status
	 */
	@JsonProperty("status")
	public LcmNotificationType getStatus() {
		return status;
	}

	/**
	 * @return the affectedVnf
	 */
	@JsonProperty("affectedVnf")
	public List<AffectedVnf> getAffectedVnf() {
		return affectedVnf;
	}

	/**
	 * @return the affectedPnf
	 */
	@JsonProperty("affectedPnf")
	public List<AffectedPnf> getAffectedPnf() {
		return affectedPnf;
	}

	/**
	 * @return the affectedVl
	 */
	@JsonProperty("affectedVl")
	public List<AffectedVirtualLink> getAffectedVl() {
		return affectedVl;
	}

	/**
	 * @return the affectedVnffg
	 */
	@JsonProperty("affectedVnffg")
	public List<AffectedVnffg> getAffectedVnffg() {
		return affectedVnffg;
	}

	/**
	 * @return the affectedNs
	 */
	@JsonProperty("affectedNs")
	public List<AffectedNs> getAffectedNs() {
		return affectedNs;
	}

	/**
	 * @return the affectedSap
	 */
	@JsonProperty("affectedSap")
	public List<AffectedSap> getAffectedSap() {
		return affectedSap;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsInstanceId == null) throw new MalformattedElementException("NS lifecycle change notification without NS instance ID");
		if (lifecycleOperationOccurrenceId == null) throw new MalformattedElementException("NS lifecycle change notification without operation ID");
		if (operation == null) throw new MalformattedElementException("NS lifecycle change notification without operation");
		for (AffectedVnf vnf : affectedVnf) vnf.isValid();
		for (AffectedPnf pnf : affectedPnf) pnf.isValid();
		for (AffectedVirtualLink vl : affectedVl) vl.isValid();
		for (AffectedVnffg vnffg : affectedVnffg) vnffg.isValid();
		for (AffectedNs ns : affectedNs) ns.isValid();
		for (AffectedSap sap : affectedSap) sap.isValid();
	}

}
