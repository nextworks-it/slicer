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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet;


import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * QoS options to be supported on the virtualised network, e.g. latency, jitter, etc.
 * REF IFA 005 v2.3.1 - 8.4.4.3
 * 
 * @author nextworks
 *
 */
public class NetworkQoS implements InterfaceInformationElement {

	private String qosName;
	private int qosValue;
	
	public NetworkQoS() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param qosName Name given to the QoS parameter.
	 * @param qosValue Value of the QoS parameter.
	 */
	public NetworkQoS(String qosName, int qosValue) {
		this.qosName = qosName;
		this.qosValue = qosValue;
	}
	
	

	/**
	 * @return the qosName
	 */
	public String getQosName() {
		return qosName;
	}

	/**
	 * @return the qosValue
	 */
	public int getQosValue() {
		return qosValue;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (qosName == null) throw new MalformattedElementException("Network QoS without name");
	}

}
