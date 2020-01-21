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
package it.nextworks.nfvmano.sebastian.nsmf.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

public class NetworkSliceStatusChangeNotification implements InterfaceMessage {

	private String nsiId;
	private NetworkSliceStatusChange statusChange;
	private boolean successful;
	private String tenantId;
	
	
	/**
	 * Constructor
	 * 
	 * @param nsiId ID of the network slice instance this notification refers to
	 * @param statusChange type of notified status change
	 * @param successful result of the change
	 */
	public NetworkSliceStatusChangeNotification(String nsiId, NetworkSliceStatusChange statusChange,
			boolean successful) {
		this.nsiId = nsiId;
		this.statusChange = statusChange;
		this.successful = successful;
	}

	public NetworkSliceStatusChangeNotification(String nsiId, NetworkSliceStatusChange statusChange,
												boolean successful, String tenantId) {
		this.nsiId = nsiId;
		this.statusChange = statusChange;
		this.successful = successful;
		this.tenantId = tenantId;
	}

	public NetworkSliceStatusChangeNotification(){}

	public String getNsiId() {
		return nsiId;
	}



	public NetworkSliceStatusChange getStatusChange() {
		return statusChange;
	}



	public boolean isSuccessful() {
		return successful;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (nsiId == null) throw new MalformattedElementException("Network Slice status change notification without slice ID");
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		return tenantId;
	}
}
