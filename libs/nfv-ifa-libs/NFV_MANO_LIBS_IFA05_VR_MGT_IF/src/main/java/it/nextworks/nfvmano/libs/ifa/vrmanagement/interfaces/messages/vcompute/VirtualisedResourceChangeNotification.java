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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This notification informs the receiver of changes in the virtualised resources 
 * that are allocated.
 * 
 * REF IFA 005 v2.3.1 - sect. 8.4.9
 * 
 * @author nextworks
 *
 */
public class VirtualisedResourceChangeNotification implements InterfaceMessage {

	private String changeId;
	private String virtualisedResourceId;
	private String vimId;
	private String changeType;
	private String changedResourceData;
	
	public VirtualisedResourceChangeNotification() {	}
	
	/**
	 * Constructor
	 * 
	 * @param changeId Unique identifier of the change on the virtualised resource.
	 * @param virtualisedResourceId Identifier of the instantiated virtualised resource for which the change notification is issued.
	 * @param vimId Identifier of the VIM reporting the change.
	 * @param changeType It categorizes the type of change.
	 * @param changedResourceData Details of the changes of the resource
	 */
	public VirtualisedResourceChangeNotification(String changeId,
			String virtualisedResourceId,
			String vimId,
			String changeType,
			String changedResourceData) {	
		this.changeId = changeId;
		this.virtualisedResourceId = virtualisedResourceId;
		this.vimId = vimId;
		this.changeType = changeType;
		this.changedResourceData = changedResourceData;
	}

	
	
	/**
	 * @return the changeId
	 */
	public String getChangeId() {
		return changeId;
	}

	/**
	 * @return the virtualisedResourceId
	 */
	public String getVirtualisedResourceId() {
		return virtualisedResourceId;
	}

	/**
	 * @return the vimId
	 */
	public String getVimId() {
		return vimId;
	}

	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}

	/**
	 * @return the changedResourceData
	 */
	public String getChangedResourceData() {
		return changedResourceData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (changeId == null) throw new MalformattedElementException("Virtual resource change notification without change ID");
		if (virtualisedResourceId == null) throw new MalformattedElementException("Virtual resource change notification without resource ID");
		if (vimId == null) throw new MalformattedElementException("Virtual resource change notification without VIM ID");
		if (changeType == null) throw new MalformattedElementException("Virtual resource change notification without change type");
	}

}
