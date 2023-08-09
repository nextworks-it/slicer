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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.enums.UserDataTransportMedia;

/**
 * REF IFA 005 v2.3.1 - sect. 8.4.10
 * 
 * @author nextworks
 *
 */
public class UserData implements InterfaceInformationElement {

	private String content;
	private UserDataTransportMedia method;
	
	public UserData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param content String containing the user data to customize a virtualised compute resource at boot-time.
	 * @param method Method used as transportation media to convey the content of the UserData to the virtualised compute resource.
	 */
	public UserData(String content,
			UserDataTransportMedia method) {
		this.content = content;
		this.method = method;
	}
	
	

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the method
	 */
	public UserDataTransportMedia getMethod() {
		return method;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (content == null) throw new MalformattedElementException("User Data without content");
	}

}
