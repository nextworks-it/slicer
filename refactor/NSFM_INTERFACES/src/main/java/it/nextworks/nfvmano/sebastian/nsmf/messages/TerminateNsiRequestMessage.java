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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class TerminateNsiRequestMessage extends EngineMessage {

	@JsonProperty("nsiId")
	private String nsiId;

	
	
	/**
	 * @param nsiId
	 */
	@JsonCreator
	public TerminateNsiRequestMessage(@JsonProperty("nsiId") String nsiId) {
		this.type = EngineMessageType.TERMINATE_NSI_REQUEST;
		this.nsiId = nsiId;
	}



	/**
	 * @return the nsiId
	 */
	public String getNsiId() {
		return nsiId;
	}
	
	
	
}
