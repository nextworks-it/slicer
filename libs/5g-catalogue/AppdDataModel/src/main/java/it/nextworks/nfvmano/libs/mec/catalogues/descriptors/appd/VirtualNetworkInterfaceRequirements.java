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
package it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

/**
 * This information element specifies requirements on a virtual network interface.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.6.6
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VirtualNetworkInterfaceRequirements implements DescriptorInformationElement {

	private String name;
	private String description;
	private boolean supportMandatory;
	private String netRequirement;
	private String nicIoRequirements;
	
	public VirtualNetworkInterfaceRequirements() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param name human readable name for the requirement.
	 * @param description human readable description of the requirement
	 * @param supportMandatory Indicates whether fulfilling the constraint is mandatory (TRUE) for successful operation or desirable (FALSE).
	 * @param netRequirement Specifies a requirement such as the support of SR-IOV, a particular data plane acceleration library, an API to be exposed by a NIC, etc.
	 * @param nicIoRequirements This references (couples) the CPD with any logical node I/O requirements (for network logicalNodeData) devices) that may have been created.
	 */
	public VirtualNetworkInterfaceRequirements(String name,
			String description,
			boolean supportMandatory,
			String netRequirement,
			String nicIoRequirements) {
		this.name = name;
		this.description = description;
		this.supportMandatory = supportMandatory;
		this.netRequirement = netRequirement;
		this.nicIoRequirements = nicIoRequirements;
	}
	
	

	/**
	 * @return the name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * @return the supportMandatory
	 */
	@JsonProperty("supportMandatory")
	public boolean isSupportMandatory() {
		return supportMandatory;
	}

	/**
	 * @return the requirement
	 */
	@JsonProperty("networkInterfaceRequirements")
	public String getNetRequirement() {
		return netRequirement;
	}
	
	

	/**
	 * @return the nicIoRequirements
	 */
	@JsonProperty("nicIoRequirements")
	public String getNicIoRequirements() {
		return nicIoRequirements;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (netRequirement == null) throw new MalformattedElementException("Virtual network interface requirement without requirement");
	}

}
