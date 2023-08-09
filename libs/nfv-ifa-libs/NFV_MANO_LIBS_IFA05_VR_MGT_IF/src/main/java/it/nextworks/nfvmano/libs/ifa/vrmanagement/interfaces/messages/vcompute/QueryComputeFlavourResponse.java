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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vcompute;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualComputeFlavour;

/**
 * Response to a query compute flavour
 * 
 * REF IFA 005 v2.3.1 - 7.3.5.3
 * 
 * @author nextworks
 *
 */
public class QueryComputeFlavourResponse implements InterfaceMessage {

	private List<VirtualComputeFlavour> flavours = new ArrayList<>();
	
	public QueryComputeFlavourResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param flavours A list of Compute Flavours matching the query.
	 */
	public QueryComputeFlavourResponse(List<VirtualComputeFlavour> flavours) {
		if (flavours != null) this.flavours = flavours;
	}

	
	
	/**
	 * @return the flavours
	 */
	public List<VirtualComputeFlavour> getFlavours() {
		return flavours;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (VirtualComputeFlavour vcf : flavours) vcf.isValid();
	}

}
