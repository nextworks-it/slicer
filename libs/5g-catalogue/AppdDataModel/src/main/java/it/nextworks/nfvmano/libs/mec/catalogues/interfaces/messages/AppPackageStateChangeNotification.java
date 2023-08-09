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
package it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.mec.catalogues.interfaces.enums.AppdChangeType;

/**
 * This notification indicates a change of status in an AppD.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.4
 * Message not yet specified.
 * 
 * @author nextworks
 *
 */
public class AppPackageStateChangeNotification implements InterfaceMessage {

	private String appdInfoId;
	private AppdChangeType changeType;
	private OperationalState operationalState;
	private boolean deletionPending; 
	
	public AppPackageStateChangeNotification() { }
	
	/**
	 * Constructor
	 * 
	 * @param appdInfoId ID of the appd info
	 * @param changeType type of change in the appd info
	 * @param operationalState operational state of the appd
	 * @param deletionPending indicates if the appd is the deletion pending status
	 */
	public AppPackageStateChangeNotification(String appdInfoId,
                                             AppdChangeType changeType,
                                             OperationalState operationalState,
                                             boolean deletionPending) {
		this.appdInfoId = appdInfoId;
		this.changeType = changeType;
		this.operationalState = operationalState;
		this.deletionPending = deletionPending;
	}
	
	

	/**
	 * @return the appdInfoId
	 */
	public String getAppdInfoId() {
		return appdInfoId;
	}

	/**
	 * @return the changeType
	 */
	public AppdChangeType getChangeType() {
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
		if (this.appdInfoId == null) 
			throw new MalformattedElementException("AppD change notification without AppD info ID");
	}

}
