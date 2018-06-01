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
package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;

/**
 * This class models a request to the arbitrator.
 * 
 * It includes:
 * 1. the tenant ID --> used to retrieve the tenant SLAs + the used resources + the associated network slices
 * 2. the VSD --> used to get specific constraints (future work)
 * 3. the NSDs + DFs + ILs returned from the translator --> used to check the amount of resources that are needed and evaluate if existing NSIs can be re-used
 * 
 * @author nextworks
 *
 */
public class ArbitratorRequest {

	private String requestId;
	private String tenantId;
	private VsDescriptor vsd;
	private Map<String, NfvNsInstantiationInfo> instantiationNsd = new HashMap<>();
	
	/**
	 * @param tenantId
	 * @param vsd
	 * @param instantiationNsd
	 */
	public ArbitratorRequest(String requestId, String tenantId, VsDescriptor vsd, Map<String, NfvNsInstantiationInfo> instantiationNsd) {
		this.requestId = requestId;
		this.tenantId = tenantId;
		this.vsd = vsd;
		if (instantiationNsd != null) this.instantiationNsd = instantiationNsd;
	}

	
	
	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}



	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}
	
	/**
	 * @return the vsd
	 */
	public VsDescriptor getVsd() {
		return vsd;
	}
	
	/**
	 * @return the instantiationNsd
	 */
	public Map<String, NfvNsInstantiationInfo> getInstantiationNsd() {
		return instantiationNsd;
	}
	
}
