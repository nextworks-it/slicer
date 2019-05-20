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
package it.nextworks.nfvmano.sebastian.engine.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.elements.LocationInfo;

public class InstantiateNsiRequestMessage extends EngineMessage {

	@JsonProperty("nsiId")
	private String nsiId;
	
	@JsonProperty("nfvNsdId")
	private String nfvNsdId;
	
	@JsonProperty("nfvNsdVersion")
	private String nfvNsdVersion;
	
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

	/**
	 * Constructor
	 * 
	 * @param nsiId
	 * @param nfvNsdId
	 * @param nfvNsdVersion
	 * @param dfId
	 * @param ilId
	 * @param nsSubnetIds
	 * @param userData
	 * @param locationConstraints
	 * @param ranEndPointId
	 */
	@JsonCreator
	public InstantiateNsiRequestMessage(@JsonProperty("nsiId") String nsiId, 
			@JsonProperty("nfvNsdId") String nfvNsdId,
			@JsonProperty("nfvNsdVersion") String nfvNsdVersion,
			@JsonProperty("dfId") String dfId, 
			@JsonProperty("ilId") String ilId, 
			@JsonProperty("nsSubnetIds") List<String> nsSubnetIds,
			@JsonProperty("userData") Map<String, String> userData,
			@JsonProperty("locationConstraints") LocationInfo locationConstraints,
			@JsonProperty("ranEndPointId") String ranEndPointId) {
		this.type = EngineMessageType.INSTANTIATE_NSI_REQUEST;
		this.nsiId = nsiId;
		this.nfvNsdId = nfvNsdId;
		this.nfvNsdVersion = nfvNsdVersion;
		this.dfId = dfId;
		this.ilId = ilId;
		if (nsSubnetIds != null) this.nsSubnetIds = nsSubnetIds;
		if (userData != null) this.userData = userData;
		this.locationConstraints = locationConstraints;
		this.ranEndPointId = ranEndPointId;
	}

	/**
	 * @return the nsiId
	 */
	public String getNsiId() {
		return nsiId;
	}

	/**
	 * @return the nfvNsdId
	 */
	public String getNfvNsdId() {
		return nfvNsdId;
	}

	/**
	 * @return the dfId
	 */
	public String getDfId() {
		return dfId;
	}

	/**
	 * @return the ilId
	 */
	public String getIlId() {
		return ilId;
	}

	/**
	 * @return the nfvNsdVersion
	 */
	public String getNfvNsdVersion() {
		return nfvNsdVersion;
	}

	/**
	 * @return the nsSubnetIds
	 */
	public List<String> getNsSubnetIds() {
		return nsSubnetIds;
	}
	
	/**
	 * @return the userData
	 */
	public Map<String, String> getUserData() {
		return userData;
	}

	/**
	 * @return the locationConstraints
	 */
	public LocationInfo getLocationConstraints() {
		return locationConstraints;
	}

	/**
	 * @return the ranEndPointId
	 */
	public String getRanEndPointId() {
		return ranEndPointId;
	}
	
	
	
}
