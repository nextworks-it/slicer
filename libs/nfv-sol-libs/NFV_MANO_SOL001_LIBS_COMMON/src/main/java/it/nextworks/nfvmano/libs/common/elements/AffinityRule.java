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
package it.nextworks.nfvmano.libs.common.elements;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.AffinityScope;
import it.nextworks.nfvmano.libs.common.enums.AffinityType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * The LocalAffinityOrAntiAffinityRule information element specifies affinity or
 * anti-affinity rules applicable to VNFs or VLs instantiated from the same VNFD
 * or VLD.
 * 
 * Ref. IFA 014 v2.3.1 - 6.3.8
 * 
 * @author nextworks
 *
 */
@Embeddable
public class AffinityRule implements DescriptorInformationElement {

	private AffinityType affinityType;
	private AffinityScope affinityScope;

	public AffinityRule() {
		// JPA only
	}

	/**
	 * Constructor
	 * 
	 * @param affinityType  Specifies the type of the rule: "affinity" or
	 *                      "anti-affinity".
	 * @param affinityScope Specifies whether the scope of the rule is an NFVI-node,
	 *                      an NFVI-PoP, etc.
	 */
	public AffinityRule(AffinityType affinityType, AffinityScope affinityScope) {
		this.affinityType = affinityType;
		this.affinityScope = affinityScope;
	}

	/**
	 * @return the affinityType
	 */
	@JsonProperty("affinityOrAntiAffinity")
	public AffinityType getAffinityType() {
		return affinityType;
	}

	/**
	 * @return the affinityScope
	 */
	@JsonProperty("scope")
	public AffinityScope getAffinityScope() {
		return affinityScope;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		// TODO Auto-generated method stub

	}
}
