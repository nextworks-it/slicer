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

import java.util.HashMap;
import java.util.Map;

public class ConfigureNsiRequest implements InterfaceMessage {

	@JsonProperty("nsiId")
	private String nsiId;

	private String nsstId;

	private HashMap<String, String> parameters;


	/**
	 * Constructor
	 *
	 * @param nsiId ID of the network slice instance to be configured
	 */
	public ConfigureNsiRequest(String nsiId, String nsstId, HashMap<String, String> parameters) {
		this.nsiId = nsiId;
		this.nsstId = nsstId;
		this.parameters = parameters;
	}


	public String getNsiId() {
		return nsiId;
	}

	public String getNsstId() { return nsstId; }

	public Map<String,String> getParameters(){
		return parameters;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsiId == null) throw new MalformattedElementException("Day1 configuration NSI request without Network Slice instance ID");
	}

}
