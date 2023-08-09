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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The VirtualComputeDesc information element supports 
 * the specification of requirements related to virtual compute resources.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.9.2.2
 * 
 * @author nextworks
 *
 */
@Entity
public class VirtualComputeDesc implements InterfaceMessage {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

	@OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="appd_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	Appd appd;
	
	private String virtualComputeDescId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> logicalNode = new HashMap<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<RequestedAdditionalCapabilityData> requestAdditionalCapabilities = new ArrayList<>();
	
	@Embedded
	private VirtualMemoryData virtualMemory;
	 
	@Embedded
	private VirtualCpuData virtualCpu;
	
	public VirtualComputeDesc() { }
	
	/**
	 * Constructor
	 *
	 * @param virtualComputeDescId Unique identifier of this VirtualComputeDesc in the VNFD.
	 * @param logicalNode The logical Node requirements.
	 * @param requestAdditionalCapabilities Specifies requirements for additional capabilities.
	 * @param virtualCpu The virtual CPU(s) of the virtualised compute.
	 * @param virtualMemory The virtual memory of the virtualised compute.
	 */
	public VirtualComputeDesc(String virtualComputeDescId,
                              Map<String, String> logicalNode,
                              List<RequestedAdditionalCapabilityData> requestAdditionalCapabilities,
                              VirtualCpuData virtualCpu,
                              VirtualMemoryData virtualMemory) {
		this.virtualComputeDescId = virtualComputeDescId;
		if (logicalNode != null) this.logicalNode = logicalNode;
		if (requestAdditionalCapabilities != null) this.requestAdditionalCapabilities = requestAdditionalCapabilities;
		this.virtualCpu = virtualCpu;
		this.virtualMemory = virtualMemory;
	}
	
	/**
	 * Constructor
	 * 
	 * @param appd AppD this virtual compute descriptor belongs to
	 * @param virtualComputeDescId Unique identifier of this VirtualComputeDesc in the VNFD.
	 * @param logicalNode The logical Node requirements.
	 * @param requestAdditionalCapabilities Specifies requirements for additional capabilities.
	 * @param virtualCpu The virtual CPU(s) of the virtualised compute.
	 * @param virtualMemory The virtual memory of the virtualised compute.
	 */
	public VirtualComputeDesc(Appd appd,
                              String virtualComputeDescId,
                              Map<String, String> logicalNode,
                              List<RequestedAdditionalCapabilityData> requestAdditionalCapabilities,
                              VirtualCpuData virtualCpu,
                              VirtualMemoryData virtualMemory) {
		this.appd = appd;
		this.virtualComputeDescId = virtualComputeDescId;
		if (logicalNode != null) this.logicalNode = logicalNode;
		if (requestAdditionalCapabilities != null) this.requestAdditionalCapabilities = requestAdditionalCapabilities;
		this.virtualCpu = virtualCpu;
		this.virtualMemory = virtualMemory;
	}
	
	

	/**
	 * @return the virtualComputeDescId
	 */
	@JsonProperty("virtualComputeDescId")
	public String getVirtualComputeDescId() {
		return virtualComputeDescId;
	}

	
	/**
	 * @return the logicalNode
	 */
	@JsonProperty("logicalNode")
	public Map<String, String> getLogicalNode() {
		return logicalNode;
	}

	/**
	 * @return the requestAdditionalCapabilities
	 */
	@JsonProperty("requestAdditionalCapabilities")
	public List<RequestedAdditionalCapabilityData> getRequestAdditionalCapabilities() {
		return requestAdditionalCapabilities;
	}

	/**
	 * @return the virtualMemory
	 */
	@JsonProperty("virtualMemory")
	public VirtualMemoryData getVirtualMemory() {
		return virtualMemory;
	}

	/**
	 * @return the virtualCpu
	 */
	@JsonProperty("virtualCpu")
	public VirtualCpuData getVirtualCpu() {
		return virtualCpu;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (virtualComputeDescId == null) throw new MalformattedElementException("Virtual compute descriptor without ID");
		if (virtualCpu == null) throw new MalformattedElementException("Virtual compute descriptor without vCPU data");
		else
			virtualCpu.isValid();
		if (virtualMemory == null) throw new MalformattedElementException("Virtual compute descriptor without virtual memory data");
		else
			virtualMemory.isValid();
	}

}
