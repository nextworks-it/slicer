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
package it.nextworks.nfvmano.libs.ifa.records.nsinfo;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Ref. IFA 013 v2.3.1 section 8.3.3.16
 * 
 * @author nextworks
 *
 */
@Embeddable
public class NsScaleInfo implements DescriptorInformationElement {

	private String nsScalingAspectId;
	private String nsScaleLevelId;
	
	public NsScaleInfo() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsScalingAspectId Identifier of the NS scaling aspect.
	 * @param nsScaleLevelId Identifier of the NS scale level.
	 */
	public NsScaleInfo(String nsScalingAspectId,
			String nsScaleLevelId) {
		this.nsScalingAspectId = nsScalingAspectId;
		this.nsScaleLevelId = nsScaleLevelId;
	}
	
	

	/**
	 * @return the nsScalingAspectId
	 */
	public String getNsScalingAspectId() {
		return nsScalingAspectId;
	}

	/**
	 * @return the nsScaleLevelId
	 */
	public String getNsScaleLevelId() {
		return nsScaleLevelId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsScaleLevelId == null) throw new MalformattedElementException("NS scale info without scale level ID");
		if (nsScalingAspectId == null) throw new MalformattedElementException("NS scale info without scaling aspect ID");
	}

}
