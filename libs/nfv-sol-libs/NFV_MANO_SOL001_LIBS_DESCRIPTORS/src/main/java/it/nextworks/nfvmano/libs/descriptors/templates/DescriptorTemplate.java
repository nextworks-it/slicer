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
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.*;

@Entity
public class DescriptorTemplate implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    private String toscaDefinitionsVersion;
    private String toscaDefaultNamespace;
    private String description;

    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Metadata metadata;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "descriptorTemplate", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, DataType> dataTypes = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "descriptorTemplate", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, NodeType> nodeTypes = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> imports = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "descriptorTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TopologyTemplate topologyTemplate;

    public DescriptorTemplate() {

    }

    public DescriptorTemplate(String toscaDefinitionsVersion, String toscaDefaultNamespace, String description,
                              Metadata metadata) {
        this.toscaDefinitionsVersion = toscaDefinitionsVersion;
        this.toscaDefaultNamespace = toscaDefaultNamespace;
        this.description = description;
        this.metadata = metadata;
    }

    public DescriptorTemplate(String toscaDefinitionsVersion, String toscaDefaultNamespace, String description,
                              Metadata metadata, Map<String, DataType> dataTypes, Map<String, NodeType> nodeTypes,
                              List<String> imports, TopologyTemplate topologyTemplate) {
        this.toscaDefinitionsVersion = toscaDefinitionsVersion;
        this.toscaDefaultNamespace = toscaDefaultNamespace;
        this.description = description;
        this.metadata = metadata;
        this.dataTypes = dataTypes;
        this.nodeTypes = nodeTypes;
        this.imports = imports;
        this.topologyTemplate = topologyTemplate;
    }

    public DescriptorTemplate(String toscaDefinitionsVersion, String toscaDefaultNamespace, String description,
                              Metadata metadata, TopologyTemplate topologyTemplate) {
        this.toscaDefinitionsVersion = toscaDefinitionsVersion;
        this.toscaDefaultNamespace = toscaDefaultNamespace;
        this.description = description;
        this.metadata = metadata;
        this.topologyTemplate = topologyTemplate;
    }

    public UUID getId() {
        return id;
    }

    @JsonProperty("toscaDefinitionsVersion")
    public String getToscaDefinitionsVersion() {
        return toscaDefinitionsVersion;
    }

    @JsonProperty("toscaDefaultNamespace")
    public String getToscaDefaultNamespace() {
        return toscaDefaultNamespace;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("dataTypes")
    public Map<String, DataType> getDataTypes() {
        return dataTypes;
    }

    @JsonProperty("nodeTypes")
    public Map<String, NodeType> getNodeTypes() {
        return nodeTypes;
    }

    @JsonProperty("imports")
    public List<String> getImports() {
        return imports;
    }

    @JsonProperty("topologyTemplate")
    public TopologyTemplate getTopologyTemplate() {
        return topologyTemplate;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.toscaDefinitionsVersion == null)
            throw new MalformattedElementException("DescriptorTemplate without tosca_definitions_version");
        // if (this.toscaDefaultNamespace == null) throw new
        // MalformattedElementException("DescriptorTemplate without
        // tosca_default_namespace");
        if (this.metadata == null)
            throw new MalformattedElementException("DescriptorTemplate without metadata");
        else
            this.metadata.isValid();
        if (this.topologyTemplate == null)
            throw new MalformattedElementException("DescriptorTemplate without topology_template");
    }
}
