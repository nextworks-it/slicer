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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

@Entity
public class TopologyTemplate implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private DescriptorTemplate descriptorTemplate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "topologyTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubstitutionMappings substituitionMappings;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, String> inputs = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "topologyTemplate", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, Node> nodeTemplates = new LinkedHashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "topologyTemplate", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, Relationship> relationshipTemplates = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "topologyTemplate", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, Policy> policies = new HashMap<>();

    /*@JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "topologyTemplate", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, Group> groups = new HashMap<>();*/

    public TopologyTemplate() {

    }

    public TopologyTemplate(DescriptorTemplate descriptorTemplate) {
        this.descriptorTemplate = descriptorTemplate;
    }

    public TopologyTemplate(DescriptorTemplate descriptorTemplate, SubstitutionMappings substitutionMappings, Map<String, String> inputs, LinkedHashMap<String, Node> nodeTemplates,
                            Map<String, Relationship> relationshipTemplates, Map<String, Policy> policies/*, Map<String, Group> groups*/) {
        this.descriptorTemplate = descriptorTemplate;
        this.substituitionMappings = substitutionMappings;
        this.inputs = inputs;
        this.nodeTemplates = nodeTemplates;
        this.relationshipTemplates = relationshipTemplates;
        this.policies = policies;
        //this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public DescriptorTemplate getDescriptorTemplate() {
        return descriptorTemplate;
    }

    @JsonProperty("substitutionMappings")
    public SubstitutionMappings getSubstituitionMappings() {
        return substituitionMappings;
    }

    @JsonProperty("inputs")
    public Map<String, String> getInputs() {
        return inputs;
    }

    @JsonProperty("nodeTemplates")
    public Map<String, Node> getNodeTemplates() {
        return nodeTemplates;
    }

    @JsonProperty("relationshipTemplates")
    public Map<String, Relationship> getRelationshipTemplates() {
        return relationshipTemplates;
    }

    @JsonProperty("policies")
    public Map<String, Policy> getPolicies() {
        return policies;
    }

    /*@JsonProperty("groups")
    public Map<String, Group> getGroups() {
        return groups;
    }*/

    @JsonIgnore
    public Map<String, CpNode> getCPNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof CpNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (CpNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, SapNode> getSapNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof SapNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (SapNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, VduCpNode> getVduCpNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof VduCpNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (VduCpNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, VnfExtCpNode> getVnfExtCpNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof VnfExtCpNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (VnfExtCpNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, PnfExtCpNode> getPnfExtCpNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof PnfExtCpNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (PnfExtCpNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, VDUComputeNode> getVDUComputeNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof VDUComputeNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (VDUComputeNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, VDUVirtualBlockStorageNode> getVDUBlockStorageNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof VDUVirtualBlockStorageNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (VDUVirtualBlockStorageNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, VDUVirtualFileStorageNode> getVDUFileStorageNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof VDUVirtualFileStorageNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (VDUVirtualFileStorageNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, VDUVirtualObjectStorageNode> getVDUObjectStorageNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof VDUVirtualObjectStorageNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (VDUVirtualObjectStorageNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, VNFNode> getVNFNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof VNFNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (VNFNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, NSNode> getNSNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof NSNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (NSNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, PNFNode> getPNFNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof PNFNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (PNFNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, NsVirtualLinkNode> getNsVirtualLinkNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof NsVirtualLinkNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (NsVirtualLinkNode) e.getValue()), LinkedHashMap::putAll);
    }

    @JsonIgnore
    public Map<String, VnfVirtualLinkNode> getVnfVirtualLinkNodes() throws MalformattedElementException {

        return nodeTemplates.entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof VnfVirtualLinkNode)
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), (VnfVirtualLinkNode) e.getValue()), LinkedHashMap::putAll);
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.getNSNodes().isEmpty() && this.getVNFNodes().isEmpty()) {
            throw new MalformattedElementException(
                    "At least one between NS node and VNF node should be present");
        }

    }

    public void setNodeTemplates(Map<String, Node> nodeTemplates){
        this.nodeTemplates=nodeTemplates;
    }

    public void setSubstituitionMappings(SubstitutionMappings substituitionMappings) {
        this.substituitionMappings = substituitionMappings;
    }
}
