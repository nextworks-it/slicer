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
package it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.enums.VnfPackageChangeType;
import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;


/**
 * This notification indicates a change of status in a VNF Package. 
 * Only changes in operational state and deletion pending attribute will be reported, 
 * change in usage state is not reported.
 * 
 * REF IFA 013 v2.3.1 - 8.6.8
 * 
 * @author nextworks
 *
 */
public class VnfPackageChangeNotification implements InterfaceMessage {

	private String onboardedVnfPkgInfoId;
	private String vnfdId;
	private VnfPackageChangeType changeType;
	private OperationalState operationalState;
	private boolean deletionPending;
	
	public VnfPackageChangeNotification() {	}
	
	/**
	 * Constructor
	 * 
	 * @param onboardedVnfPkgInfoId Identifier of information held by the NFVO about the specific on-boarded VNF Package. This identifier was allocated by the NFVO.
	 * @param vnfdId Identifier that identifies the VNF Package
	 * @param changeType Type of change
	 * @param operationalState New operational state of the VNF package
	 * @param deletionPending Indicates if the deletion instance of the VNF Package has been requested but the VNF Package still being used by instantiated VNFs.
	 */
	public VnfPackageChangeNotification(String onboardedVnfPkgInfoId,
			String vnfdId,
			VnfPackageChangeType changeType,
			OperationalState operationalState,
			boolean deletionPending) {	
		this.onboardedVnfPkgInfoId = onboardedVnfPkgInfoId;
		this.vnfdId = vnfdId;
		this.changeType = changeType;
		this.operationalState = operationalState;
		this.deletionPending = deletionPending;
	}
	
	

	/**
	 * @return the onboardedVnfPkgInfoId
	 */
	public String getOnboardedVnfPkgInfoId() {
		return onboardedVnfPkgInfoId;
	}

	/**
	 * @return the vnfdId
	 */
	public String getVnfdId() {
		return vnfdId;
	}

	/**
	 * @return the changeType
	 */
	public VnfPackageChangeType getChangeType() {
		return changeType;
	}

	/**
	 * @return the operationalState
	 */
	public OperationalState getOperationalState() {
		return operationalState;
	}

	/**
	 * @return the deletionPending
	 */
	public boolean isDeletionPending() {
		return deletionPending;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (onboardedVnfPkgInfoId == null) throw new MalformattedElementException("VNF package change notification without VNF package ID");
		if (vnfdId == null) throw new MalformattedElementException("VNF package change notification without VNFD ID");
	}

}
