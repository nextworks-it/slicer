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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The MoveVnfInstanceData specifies existing VNF instances that needs 
 * to be moved from one NS instance (source) to another NS instance (destination).
 * 
 *  REF IFA 013 v2.3.1 - 8.3.4.20
 * 
 * @author nextworks
 *
 */
public class MoveVnfInstanceData implements InterfaceInformationElement {

	private String targetNsInstanceId;
	private List<String> vnfInstanceId = new ArrayList<>();
	
	public MoveVnfInstanceData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param targetNsInstanceId Specify the target NS instance where the VNF instances are moved to.
	 * @param vnfInstanceId Specify the VNF instance that is moved.
	 */
	public MoveVnfInstanceData(String targetNsInstanceId,
			List<String> vnfInstanceId) {
		this.targetNsInstanceId = targetNsInstanceId;
		if (vnfInstanceId != null) this.vnfInstanceId = vnfInstanceId;
	}
	
	

	/**
	 * @return the targetNsInstanceId
	 */
	public String getTargetNsInstanceId() {
		return targetNsInstanceId;
	}

	/**
	 * @return the vnfInstanceId
	 */
	public List<String> getVnfInstanceId() {
		return vnfInstanceId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (targetNsInstanceId == null) throw new MalformattedElementException("Move VNF instance data without target NS instance ID");
		if ((vnfInstanceId == null) || (vnfInstanceId.isEmpty())) throw new MalformattedElementException("Move VNF instance data without VNF instance ID");
	}

}
