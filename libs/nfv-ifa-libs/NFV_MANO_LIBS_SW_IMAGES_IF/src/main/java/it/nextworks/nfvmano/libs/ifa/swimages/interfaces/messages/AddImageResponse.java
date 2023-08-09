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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.swimages.interfaces.elements.SoftwareImageInformation;

/**
 * Response to an addImage request
 * 
 * REF IFA 006 - v2.3.1 - 7.2.2
 * 
 * @author nextworks
 *
 */
public class AddImageResponse implements InterfaceMessage {

	private SoftwareImageInformation softwareImageMetadata;
	
	public AddImageResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param softwareImageMetadata Metadata about the Software Image that has been added or updated
	 */
	public AddImageResponse(SoftwareImageInformation softwareImageMetadata) {
		this.softwareImageMetadata = softwareImageMetadata;
	}

	
	
	/**
	 * @return the softwareImageMetadata
	 */
	public SoftwareImageInformation getSoftwareImageMetadata() {
		return softwareImageMetadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (softwareImageMetadata == null) throw new MalformattedElementException("Add sw image response without metadata");
		else softwareImageMetadata.isValid();

	}

}
