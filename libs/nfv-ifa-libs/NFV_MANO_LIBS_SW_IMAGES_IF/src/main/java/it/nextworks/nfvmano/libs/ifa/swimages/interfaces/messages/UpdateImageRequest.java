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
package it.nextworks.nfvmano.libs.ifa.swimages.interfaces.messages;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Update sw image request
 * 
 * REF IFA 006 - v2.3.1 - 7.2.5.2
 * 
 * @author nextworks
 *
 */
public class UpdateImageRequest implements InterfaceMessage {

	private String id;
	private Map<String, String> userMetadata = new HashMap<>();
	
	public UpdateImageRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param id The identifier of the software image to be updated.
	 * @param userMetadata User-defined metadata for the software image.
	 */
	public UpdateImageRequest(String id,
			Map<String, String> userMetadata) {	
		this.id = id;
		if (userMetadata != null) this.userMetadata = userMetadata;
	}

	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the userMetadata
	 */
	public Map<String, String> getUserMetadata() {
		return userMetadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (id == null) throw new MalformattedElementException("Update sw image request without image ID");
	}

}
