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
public class InstantiateNsRequestInternal {

	private String nsName;
	private String nsdId;
	private String nsInstanceId;



	private Map<String, String> additionalParamForNs = new HashMap<>();


	//out of the standard
	private List<VnfAllocation> vnfAllocations = new ArrayList<>();
	private List<VlAllocation> vlAllocations = new ArrayList<>();


	public InstantiateNsRequestInternal() {
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





	public InstantiateNsRequestInternal(String nsName, String nsdId, String nsInstanceId,  Map<String, String> additionalParamForNs) {
		this.nsInstanceId = nsInstanceId;
		this.nsName = nsName;

		this.additionalParamForNs = additionalParamForNs;
		this.nsdId=nsdId;
		if(vnfAllocations!=null) this.vnfAllocations = vnfAllocations;

	}

	public String getNsName() {
		return nsName;
	}

	public List<VnfAllocation> getVnfAllocations() {
		return vnfAllocations;
	}

	public List<VlAllocation> getVlAllocations() {
		return vlAllocations;
	}

	public String getNsdId() {
		return nsdId;
	}

	public void setVnfAllocations(List<VnfAllocation> vnfAllocations) {
		this.vnfAllocations = vnfAllocations;
	}

	public void setVlAllocations(List<VlAllocation> vlAllocations) {
		this.vlAllocations = vlAllocations;
	}
}
