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
package it.nextworks.nfvmano.libs.ifa.descriptors.vnfd;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element defines the configurable properties of a VNFC. 
 * For a VNFC instance, the value of these properties can be modified through the VNFM.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.6.7
 * 
 * Just a placeholder for the moment.
 * Format still to be defined in the standard.
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VnfcConfigurableProperties implements DescriptorInformationElement {

	public VnfcConfigurableProperties() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void isValid() throws MalformattedElementException {
		// TODO Auto-generated method stub

	}

}
