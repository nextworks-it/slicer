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

public class CreateNsiUuidRequest implements InterfaceMessage {

	private String nstUuid;
	private String name;
	private String description;
	
	/**
	 * Constructor
	 * 
	 * @param nstUuid ID of the network slice template
	 * @param name name of the network slice instance
	 * @param description description of the network slice instance
	 */
	public CreateNsiUuidRequest(String nstUuid, String name, String description) {
		this.nstUuid = nstUuid;
		this.name = name;
		this.description = description;
	}

	public CreateNsiUuidRequest(){ }
	public String getNstUuid() {
		return nstUuid;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (nstUuid == null) throw new MalformattedElementException("Create network slice ID request without NST UUID");
		if (name == null) throw new MalformattedElementException("Create network slice UUID request without name");
	}
	
}
