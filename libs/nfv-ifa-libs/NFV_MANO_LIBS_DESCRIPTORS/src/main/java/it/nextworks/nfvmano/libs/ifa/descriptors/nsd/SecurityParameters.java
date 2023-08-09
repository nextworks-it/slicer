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
package it.nextworks.nfvmano.libs.ifa.descriptors.nsd;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The SecurityParameters information element contains 
 * the signature of a NSD, VLD, PNFD or VNFFGD instance 
 * together with information required to validate the 
 * signature.
 * 
 * REF. IFA-014 v2.3.1 - section 6.2.5
 * 
 * @author nextworks
 *
 */
@Embeddable
public class SecurityParameters implements DescriptorInformationElement {

	
	private String signature;
	private String algorithm;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String certificate;
	
	public SecurityParameters() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param signature	Provides the signature of the signed part of the descriptor.
	 * @param algorithm Identifies the algorithm used to compute the signature.
	 * @param certificate Provides a certificate or a reference to a certificate to validate the signature.
	 */
	public SecurityParameters(String signature,
			String algorithm,
			String certificate) {
		this.signature = signature;
		this.algorithm = algorithm;
		this.certificate = certificate;
	}
	
	
	
	/**
	 * @return the signature
	 */
	@JsonProperty("signature")
	public String getSignature() {
		return signature;
	}

	/**
	 * @return the algorithm
	 */
	@JsonProperty("algorithm")
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * @return the certificate
	 */
	@JsonProperty("certificate")
	public String getCertificate() {
		return certificate;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.signature == null) throw new MalformattedElementException("Security Parameters without signature");
		if (this.algorithm == null) throw new MalformattedElementException("Security Parameters without algorithm");
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
}
