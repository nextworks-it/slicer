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
package it.nextworks.nfvmano.libs.descriptors.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.capabilities.VirtualComputeCapabilityProperties;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class VirtualCpu implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VirtualComputeCapabilityProperties properties;

    private String cpuArchitecture;
    private Integer numVirtualCpu;
    private Integer virtualCpuClock;

    private String virtualCpuOversubscriptionPolicy;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, String> vduCpuRequirements = new HashMap<String, String>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "virtualCpu", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VirtualCpuPinning virtualCpuPinning;

    public VirtualCpu() {
    }

    public VirtualCpu(VirtualComputeCapabilityProperties properties, String cpuArchitecture, Integer numVirtualCpu,
                      Integer virtualCpuClock, String virtualCpuOversubscriptionPolicy, Map<String, String> vduCpuRequirements,
                      VirtualCpuPinning virtualCpuPinning) {
        this.properties = properties;
        this.cpuArchitecture = cpuArchitecture;
        this.numVirtualCpu = numVirtualCpu;
        this.virtualCpuClock = virtualCpuClock;
        this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
        this.vduCpuRequirements = vduCpuRequirements;
        this.virtualCpuPinning = virtualCpuPinning;
    }

    @JsonProperty("cpuArchitecture")
    public String getCpuArchitecture() {
        return cpuArchitecture;
    }

    @JsonProperty("numVirtualCpu")
    public Integer getNumVirtualCpu() {
        return numVirtualCpu;
    }

    @JsonProperty("virtualCpuClock")
    public Integer getVirtualCpuClock() {
        return virtualCpuClock;
    }

    @JsonProperty("virtualCpuOversubscriptionPolicy")
    public String getVirtualCpuOversubscriptionPolicy() {
        return virtualCpuOversubscriptionPolicy;
    }

    @JsonProperty("vduCpuRequirements")
    public Map<String, String> getVduCpuRequirements() {
        return vduCpuRequirements;
    }

    @JsonProperty("virtualCpuPinning")
    public VirtualCpuPinning getVirtualCpuPinning() {
        return virtualCpuPinning;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.numVirtualCpu == null)
            throw new MalformattedElementException("NumVirtualCpu is missing in VDU VirtualCpu");
    }

}
