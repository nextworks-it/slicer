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
package it.nextworks.nfvmano.sbi.nfvo.messages;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request to instantiate a new NS
 * Ref. IFA 013 v2.3.1 section 7.3.3.2
 * 
 * @author nextworks
 *
 */
public class InstantiateNetworkSliceRequestInternal {

	private String nsName;
	private String nstId;
	private String nsInstanceId;
	private String configFile;


	private Map<String, String> additionalParamForNs = new HashMap<>();



	public InstantiateNetworkSliceRequestInternal() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the nsInstanceId
	 */
	public String getNsInstanceId() {
		return nsInstanceId;
	}

	/**
	 * @return the additionalParamForNs
	 */
	public Map<String, String> getAdditionalParamForNs() {
		return additionalParamForNs;
	}





	public InstantiateNetworkSliceRequestInternal(String nsName, String nstId, String nsInstanceId, Map<String, String> additionalParamForNs, String configFile) {
		this.nsInstanceId = nsInstanceId;
		this.nsName = nsName;

		this.additionalParamForNs = additionalParamForNs;
		this.nstId = nstId;
		this.configFile=configFile;


	}

	public String getNsName() {
		return nsName;
	}



	public String getNstId() {
		return nstId;
	}

	public String getConfigFile() {
		return configFile;
	}
}
