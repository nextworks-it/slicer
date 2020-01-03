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

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

public class TerminateNsiRequest implements InterfaceMessage {

	@JsonProperty("nsiId")
	private String nsiId;
	
	
	/**
	 * Constructor
	 * 
	 * @param nsiId ID of the network slice instance to be terminated
	 */
	public TerminateNsiRequest(String nsiId) {
		this.nsiId = nsiId;
	}

	public TerminateNsiRequest(){}

	public String getNsiId() {
		return nsiId;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (nsiId == null) throw new MalformattedElementException("Terminate NSI request without Network Slice instance ID");
	}

}
