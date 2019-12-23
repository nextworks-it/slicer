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
package it.nextworks.nfvmano.sebastian.nsmf.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

public class CreateNsiIdRequest implements InterfaceMessage {

	private String nstId;
	private String name;
	private String description;
	
	/**
	 * Constructor
	 * 
	 * @param nstId ID of the network slice template
	 * @param name name of the network slice instance
	 * @param description description of the network slice instance
	 */
	public CreateNsiIdRequest(String nstId, String name, String description) {
		this.nstId = nstId;
		this.name = name;
		this.description = description;
	}

	public String getNstId() {
		return nstId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (nstId == null) throw new MalformattedElementException("Create network slice ID request without NST ID");
		if (name == null) throw new MalformattedElementException("Create network slice ID request without name");
	}
	
}
