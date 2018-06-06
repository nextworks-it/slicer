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
package it.nextworks.nfvmano.sebastian.catalogue.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;

public class QueryVsDescriptorResponse implements InterfaceMessage {

	private List<VsDescriptor> vsDescriptors = new ArrayList<>();
	
	public QueryVsDescriptorResponse() { }
	
	public QueryVsDescriptorResponse(List<VsDescriptor> vsDescriptors) {
		if (vsDescriptors != null) this.vsDescriptors = vsDescriptors;
	}
	
	/**
	 * @return the vsDescriptors
	 */
	public List<VsDescriptor> getVsDescriptors() {
		return vsDescriptors;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (VsDescriptor vsd : vsDescriptors) vsd.isValid();
	}

}
