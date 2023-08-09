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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.quotas.VirtualStorageQuota;

/**
 * Response to a create storage quota request.
 * REF IFA 005 v2.3.1 - 7.9.3.2
 * 
 * @author nextworks
 *
 */
public class CreateStorageResourceQuotaResponse implements InterfaceMessage {

	private VirtualStorageQuota quotaData;
	
	public CreateStorageResourceQuotaResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param quotaData Element containing information about the quota resource.
	 */
	public CreateStorageResourceQuotaResponse(VirtualStorageQuota quotaData) {
		this.quotaData = quotaData;
	}
	
	

	/**
	 * @return the quotaData
	 */
	public VirtualStorageQuota getQuotaData() {
		return quotaData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (quotaData == null) throw new MalformattedElementException("Create storage quota request without data.");
		else quotaData.isValid();
	}

}
