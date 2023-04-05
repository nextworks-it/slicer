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
package it.nextworks.nfvmano.sebastian.vsfm.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.sebastian.vsfm.messages.InstantiateVsRequest;

public class InstantiateVsiRequestMessage extends VsmfEngineMessage {

	@JsonProperty("vsiId")
	private String vsiId;
	
	@JsonProperty("request")
	private InstantiateVsRequest request;
	
	@JsonProperty("ranEndpointId")
	private String ranEndpointId;

	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the VS to be instantiated
	 * @param request VSI instantiation request
	 */
	@JsonCreator
	public InstantiateVsiRequestMessage(@JsonProperty("vsiId") String vsiId, 
			@JsonProperty("request") InstantiateVsRequest request,
			@JsonProperty("ranEndpointId") String ranEndpointId) {
		this.type = VsmfEngineMessageType.INSTANTIATE_VSI_REQUEST;
		this.vsiId = vsiId;
		this.request = request;
		this.ranEndpointId = ranEndpointId;
	}

	/**
	 * @return the vsiId
	 */
	public String getVsiId() {
		return vsiId;
	}

	/**
	 * @return the request
	 */
	public InstantiateVsRequest getRequest() {
		return request;
	}

	public String getRanEndpointId() {
		return ranEndpointId;
	}
	
	
	
	
}
