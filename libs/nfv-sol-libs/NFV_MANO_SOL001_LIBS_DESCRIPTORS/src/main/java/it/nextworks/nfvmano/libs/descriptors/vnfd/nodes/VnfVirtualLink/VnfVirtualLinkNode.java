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
package it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfVirtualLink;

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
public class VnfVirtualLinkNode extends Node implements DescriptorInformationElement {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfVirtualLinkNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfVirtualLinkProperties properties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfVirtualLinkNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfVirtualLinkCapabilities capabilities;

    public VnfVirtualLinkNode() {

    }

    public VnfVirtualLinkNode(String type, VnfVirtualLinkProperties properties,
                              VnfVirtualLinkCapabilities capabilities) {
        super(type);
        this.properties = properties;
        this.capabilities = capabilities;
    }

    public VnfVirtualLinkNode(TopologyTemplate topologyTemplate, String type, VnfVirtualLinkProperties properties,
                              VnfVirtualLinkCapabilities capabilities) {
        super(topologyTemplate, type);
        this.properties = properties;
        this.capabilities = capabilities;
    }

    @JsonProperty("properties")
    public VnfVirtualLinkProperties getProperties() {
        return properties;
    }

    @JsonProperty("capabilities")
    public VnfVirtualLinkCapabilities getCapabilities() {
        return capabilities;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.properties == null)
            throw new MalformattedElementException("Properties are missing in VNF VL Node");
        if (this.capabilities == null)
            throw new MalformattedElementException("Capabilities are missing in VNF VL Node");
    }

}
