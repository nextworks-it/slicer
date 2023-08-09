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
package it.nextworks.nfvmano.libs.ifa.vnfindicator.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vnfindicator.interfaces.elements.IndicatorInformation;


/**
 * Message sent to the VNFM (IFA008) or to the NFVO (IFA007) 
 * in reply to a request for a VNF indicator
 * 
 * REF IFA 007 v2.3.1 - 7.7.4
 * 
 * @author nextworks
 *
 */
public class GetIndicatorValueResponse implements InterfaceMessage {
	
	private List<IndicatorInformation> indicatorInformation = new ArrayList<>();
	
	public GetIndicatorValueResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param indicatorInformation  indicator values
	 */
	public GetIndicatorValueResponse(List<IndicatorInformation> indicatorInformation) {
		if (indicatorInformation != null) this.indicatorInformation = indicatorInformation;
	}

	/**
	 * 
	 * @return the list of requested indicators
	 */
	public List<IndicatorInformation> getIndicatorInformation() {
		return indicatorInformation;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (IndicatorInformation ii : indicatorInformation) ii.isValid();
	}
	
}
