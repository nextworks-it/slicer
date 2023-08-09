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
package it.nextworks.nfvmano.libs.descriptors.templates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS.NSNode;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NsVirtualLink.NsVirtualLinkNode;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.Sap.SapNode;
import it.nextworks.nfvmano.libs.descriptors.pnfd.nodes.PNF.PNFNode;
import it.nextworks.nfvmano.libs.descriptors.pnfd.nodes.PnfExtCp.PnfExtCpNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.Cp.CpNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU.VDUComputeNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU.VDUVirtualBlockStorageNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU.VDUVirtualFileStorageNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU.VDUVirtualObjectStorageNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VduCp.VduCpNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfVirtualLink.VnfVirtualLinkNode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = NSNode.class, name = "tosca.nodes.nfv.NS"),
        @JsonSubTypes.Type(value = PNFNode.class, name = "tosca.nodes.nfv.PNF"),
        @JsonSubTypes.Type(value = VNFNode.class, name = "tosca.nodes.nfv.VNF"),
        @JsonSubTypes.Type(value = NsVirtualLinkNode.class, name = "tosca.nodes.nfv.NsVirtualLink"),
        @JsonSubTypes.Type(value = VnfVirtualLinkNode.class, name = "tosca.nodes.nfv.VnfVirtualLink"),
        @JsonSubTypes.Type(value = CpNode.class, name = "tosca.nodes.nfv.Cp"),
        @JsonSubTypes.Type(value = SapNode.class, name = "tosca.nodes.nfv.Sap"),
        @JsonSubTypes.Type(value = VnfExtCpNode.class, name = "tosca.nodes.nfv.VnfExtCp"),
        @JsonSubTypes.Type(value = PnfExtCpNode.class, name = "tosca.nodes.nfv.PnfExtCp"),
        @JsonSubTypes.Type(value = VduCpNode.class, name = "tosca.nodes.nfv.VduCp"),
        @JsonSubTypes.Type(value = VDUComputeNode.class, name = "tosca.nodes.nfv.Vdu.Compute"),
        @JsonSubTypes.Type(value = VDUVirtualBlockStorageNode.class, name = "tosca.nodes.nfv.Vdu.VirtualBlockStorage"),
        @JsonSubTypes.Type(value = VDUVirtualObjectStorageNode.class, name = "tosca.nodes.nfv.Vdu.VirtualObjectStorage"),
        @JsonSubTypes.Type(value = VDUVirtualFileStorageNode.class, name = "tosca.nodes.nfv.Vdu.VirtualFileStorage")})
public abstract class Node implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private TopologyTemplate topologyTemplate;

    private String type;

    public Node() {

    }

    public Node(String type) {
        this.type = type;
    }

    public Node(TopologyTemplate topologyTemplate, String type) {
        this.topologyTemplate = topologyTemplate;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public TopologyTemplate getTopologyTemplate() {
        return topologyTemplate;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.type == null)
            throw new MalformattedElementException("Node without type");
    }
}
