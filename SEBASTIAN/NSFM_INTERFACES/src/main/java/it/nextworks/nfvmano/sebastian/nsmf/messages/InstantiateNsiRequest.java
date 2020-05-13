/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.nsmf.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.sebastian.record.elements.ImsiInfo;

public class InstantiateNsiRequest implements InterfaceMessage {

	@JsonProperty("nsiId")
	private String nsiId;
	
	@JsonProperty("nstId")
	private String nstId;
	
	@JsonProperty("dfId")
	private String dfId;
	
	@JsonProperty("ilId")
	private String ilId;
	
	@JsonProperty("nsSubnetIds")
	private List<String> nsSubnetIds = new ArrayList<>();
	
	@JsonProperty("userData")
	private Map<String, String> userData = new HashMap<>();
	
	@JsonProperty("locationConstraints")
	private LocationInfo locationConstraints;

	@JsonProperty("ranEndPointId")
	private String ranEndPointId;

	@JsonProperty
	private List<ImsiInfo> imsiInfoList = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param nsiId ID of the network slice to be instantiated
	 * @param nstId ID of the NST associated to the network slice
	 * @param dfId ID of the deployment flavour for the NFV NSD associated to the network slice
	 * @param ilId ID of the instantiation level for the NFV NSD associated to the network slice
	 * @param nsSubnetIds ID of the network slice subnets to be included in the e2e network slice
	 * @param userData user-defined data
	 * @param locationConstraints location constraints
	 * @param ranEndPointId ID of the service access point in the NFV NSD that must be attached to the RAN side
	 */
	public InstantiateNsiRequest(String nsiId, String nstId, String dfId, String ilId,
			List<String> nsSubnetIds, Map<String, String> userData, LocationInfo locationConstraints,
			String ranEndPointId, List<ImsiInfo> imsiInfoList) {
		this.nsiId = nsiId;
		this.nstId = nstId;
		this.dfId = dfId;
		this.ilId = ilId;

		if (nsSubnetIds != null) this.nsSubnetIds = nsSubnetIds;
		if (userData != null) this.userData = userData;
		if(imsiInfoList!=null) this.imsiInfoList=imsiInfoList;

		this.locationConstraints = locationConstraints;
		this.ranEndPointId = ranEndPointId;
	}


	public InstantiateNsiRequest(){}
	public String getNsiId() {
		return nsiId;
	}



	public String getNstId() {
		return nstId;
	}



	public String getDfId() {
		return dfId;
	}



	public String getIlId() {
		return ilId;
	}



	public List<String> getNsSubnetIds() {
		return nsSubnetIds;
	}



	public Map<String, String> getUserData() {
		return userData;
	}



	public LocationInfo getLocationConstraints() {
		return locationConstraints;
	}



	public String getRanEndPointId() {
		return ranEndPointId;
	}


	public List<ImsiInfo> getImsiInfoList() {
		return imsiInfoList;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsiId == null) throw new MalformattedElementException("Instantiate NS request without NS ID.");
	}

}
