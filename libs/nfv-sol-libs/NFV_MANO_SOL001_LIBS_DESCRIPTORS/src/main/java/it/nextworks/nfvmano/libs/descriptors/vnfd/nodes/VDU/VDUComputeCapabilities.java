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
package it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.capabilities.VirtualComputeCapability;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class VDUComputeCapabilities implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VDUComputeNode vduComputeNode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "capabilities", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VirtualComputeCapability virtualCompute;

    public VDUComputeCapabilities() {

    }

    public VDUComputeCapabilities(VirtualComputeCapability virtualCompute) {
        this.virtualCompute = virtualCompute;
    }

    public VDUComputeCapabilities(VDUComputeNode vduComputeNode, VirtualComputeCapability virtualCompute) {
        this.vduComputeNode = vduComputeNode;
        this.virtualCompute = virtualCompute;
    }

    public Long getId() {
        return id;
    }

    public VDUComputeNode getVduComputeNode() {
        return vduComputeNode;
    }

    @JsonProperty("virtualCompute")
    public VirtualComputeCapability getVirtualCompute() {
        return virtualCompute;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.virtualCompute == null)
            throw new MalformattedElementException("VDUCompute Node capabilities without virtualCompute");
    }

}
