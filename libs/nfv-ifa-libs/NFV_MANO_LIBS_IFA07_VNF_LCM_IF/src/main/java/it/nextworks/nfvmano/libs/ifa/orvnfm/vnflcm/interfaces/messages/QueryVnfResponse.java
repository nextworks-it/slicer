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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.records.vnfinfo.VnfInfo;

/**
 * Response to a VNF query
 * 
 * REF IFA 007 v2.3.1 - 7.2.9
 * 
 * @author nextworks
 *
 */
public class QueryVnfResponse implements InterfaceMessage {

	private List<VnfInfo> vnfInfo = new ArrayList<>();
	
	public QueryVnfResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfInfo The information items about the selected VNF instance(s) that are returned.
	 */
	public QueryVnfResponse(List<VnfInfo> vnfInfo) {	
		if (vnfInfo != null) this.vnfInfo = vnfInfo;
	}
	
	

	/**
	 * @return the vnfInfo
	 */
	public List<VnfInfo> getVnfInfo() {
		return vnfInfo;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (VnfInfo vi : vnfInfo) vi.isValid();
	}

}
