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
package it.nextworks.nfvmano.libs.ifa.descriptors.common.elements;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The LinkBitrateRequirements information element 
 * describes the requirements in terms of bitrate for a VL.
 * 
 * Ref. IFA 014 v2.3.1 - 6.5.5
 * 
 * @author nextworks
 *
 */
@Embeddable
public class LinkBitrateRequirements implements DescriptorInformationElement {

	private String root;
	private String leaf;
	
	public LinkBitrateRequirements() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param root Specifies the throughput requirement of the link (e.g. bitrate of E-Line, root bitrate of E-Tree, aggregate capacity of E-LAN).
	 * @param leaf Specifies the throughput requirement of leaf connections to the link when applicable to the connectivity type (e.g. for E-Tree andE-LAN branches).
	 */
	public LinkBitrateRequirements(String root, String leaf) {
		this.root = root;
		this.leaf = leaf;
	}

	
	
	/**
	 * @return the root
	 */
	@JsonProperty("root")
	public String getRoot() {
		return root;
	}

	/**
	 * @return the leaf
	 */
	@JsonProperty("leaf")
	public String getLeaf() {
		return leaf;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

	public void setRoot(String root) {
		this.root = root;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
}
