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
package it.nextworks.nfvmano.libs.descriptors.capabilities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.LogicalNodeData;
import it.nextworks.nfvmano.libs.descriptors.elements.RequestedAdditionalCapability;
import it.nextworks.nfvmano.libs.descriptors.elements.VirtualCpu;
import it.nextworks.nfvmano.libs.descriptors.elements.VirtualMemory;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class VirtualComputeCapabilityProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    @OneToOne
    @JsonIgnore
    private VirtualComputeCapability virtualComputeCapability;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "virtualComputeCapability", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, LogicalNodeData> logicalNode = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "virtualComputeCapability", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, RequestedAdditionalCapability> requestedAdditionalCapabilities = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, String> computeRequirements = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "properties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VirtualMemory virtualMemory;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "properties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VirtualCpu virtualCpu;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> virtualLocalStorage = new ArrayList<>();

    public VirtualComputeCapabilityProperties() {

    }

    public VirtualComputeCapabilityProperties(Map<String, LogicalNodeData> logicalNode, Map<String, RequestedAdditionalCapability> requestedAdditionalCapabilities, Map<String, String> computeRequirements, VirtualMemory virtualMemory, VirtualCpu virtualCpu, List<String> virtualLocalStorage) {
        this.logicalNode = logicalNode;
        this.requestedAdditionalCapabilities = requestedAdditionalCapabilities;
        this.computeRequirements = computeRequirements;
        this.virtualMemory = virtualMemory;
        this.virtualCpu = virtualCpu;
        this.virtualLocalStorage = virtualLocalStorage;
    }

    public VirtualComputeCapabilityProperties(VirtualComputeCapability virtualComputeCapability, Map<String, LogicalNodeData> logicalNode, Map<String, RequestedAdditionalCapability> requestedAdditionalCapabilities, Map<String, String> computeRequirements, VirtualMemory virtualMemory, VirtualCpu virtualCpu, List<String> virtualLocalStorage) {
        this.virtualComputeCapability = virtualComputeCapability;
        this.logicalNode = logicalNode;
        this.requestedAdditionalCapabilities = requestedAdditionalCapabilities;
        this.computeRequirements = computeRequirements;
        this.virtualMemory = virtualMemory;
        this.virtualCpu = virtualCpu;
        this.virtualLocalStorage = virtualLocalStorage;
    }

    public Long getId() {
        return id;
    }

    public VirtualComputeCapability getVirtualComputeCapability() {
        return virtualComputeCapability;
    }

    @JsonProperty("logicalNode")
    public Map<String, LogicalNodeData> getLogicalNode() {
        return logicalNode;
    }

    @JsonProperty("requestedAdditionalCapability")
    public Map<String, RequestedAdditionalCapability> getRequestedAdditionalCapabilities() {
        return requestedAdditionalCapabilities;
    }

    @JsonProperty("computeRequirements")
    public Map<String, String> getComputeRequirements() {
        return computeRequirements;
    }

    @JsonProperty("virtualMemory")
    public VirtualMemory getVirtualMemory() {
        return virtualMemory;
    }

    @JsonProperty("virtualCpu")
    public VirtualCpu getVirtualCpu() {
        return virtualCpu;
    }

    @JsonProperty("virtualLocalStorage")
    public List<String> getVirtualLocalStorage() {
        return virtualLocalStorage;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.virtualMemory == null)
            throw new MalformattedElementException("VirtualComputeCapabilityProperties without virtualMemory");
        else
            this.virtualMemory.isValid();
        if (this.virtualCpu == null)
            throw new MalformattedElementException("VirtualComputeCapabilityProperties without virtualCpu");
        else
            this.virtualCpu.isValid();
    }

}
