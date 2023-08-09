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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.templates.Node;
import it.nextworks.nfvmano.libs.descriptors.templates.TopologyTemplate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class VDUComputeNode extends Node implements DescriptorInformationElement {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vduComputeNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VDUComputeProperties properties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vduComputeNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VDUComputeCapabilities capabilities;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vduComputeNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VDUComputeRequirements requirements;

    public VDUComputeNode() {

    }

    public VDUComputeNode(String type, VDUComputeProperties properties,
                          VDUComputeCapabilities capabilities, VDUComputeRequirements requirements) {
        super(type);
        this.properties = properties;
        this.capabilities = capabilities;
        this.requirements = requirements;
    }

    public VDUComputeNode(TopologyTemplate topologyTemplate, String type, VDUComputeProperties properties,
                          VDUComputeCapabilities capabilities, VDUComputeRequirements requirements) {
        super(topologyTemplate, type);
        this.properties = properties;
        this.capabilities = capabilities;
        this.requirements = requirements;
    }

    @JsonProperty("properties")
    public VDUComputeProperties getProperties() {
        return properties;
    }

    @JsonProperty("capabilities")
    public VDUComputeCapabilities getCapabilities() {
        return capabilities;
    }

    @JsonProperty("requirements")
    public VDUComputeRequirements getRequirements() {
        return requirements;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.properties == null)
            throw new MalformattedElementException("VDUCompute Node without properties");
        else
            this.properties.isValid();
        if (this.requirements == null)
            throw new MalformattedElementException("VDUCompute Node without requirements");
        else
            this.requirements.isValid();
    }

}