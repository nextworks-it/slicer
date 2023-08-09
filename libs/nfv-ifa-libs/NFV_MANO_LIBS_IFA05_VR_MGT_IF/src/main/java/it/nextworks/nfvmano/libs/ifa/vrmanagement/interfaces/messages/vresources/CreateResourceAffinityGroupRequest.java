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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vresources;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.AffinityScope;
import it.nextworks.nfvmano.libs.ifa.common.enums.AffinityType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request for the creation of a resource affinity or anti-affinity constraints group
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.1.9
 * REF IFA 005 v2.3.1 - sect. 7.4.1.6
 * 
 * @author nextworks
 *
 */
public class CreateResourceAffinityGroupRequest implements InterfaceMessage {

	private String groupName;
	private AffinityType type;
	private AffinityScope scope;
	
	public CreateResourceAffinityGroupRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param groupName Name of the group, given by the consumer
	 * @param type Indicates whether this is an affinity or anti-affinity group
	 * @param scope If applicable it qualifies the scope of the constraint,
	 */
	public CreateResourceAffinityGroupRequest(String groupName,
			AffinityType type,
			AffinityScope scope) {
		this.groupName = groupName;
		this.type = type; 
		this.scope = scope;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @return the type
	 */
	public AffinityType getType() {
		return type;
	}

	/**
	 * @return the scope
	 */
	public AffinityScope getScope() {
		return scope;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (groupName == null) throw new MalformattedElementException("Create affinity group without group name");
	}

}
