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
import java.util.HashMap;
import java.util.Map;

@Entity
public class SubstitutionMappings implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    @OneToOne
    @JsonIgnore
    private TopologyTemplate topologyTemplate;

    private String nodeType;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, String> properties = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "subMapping", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubstitutionMappingsRequirements requirements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "subMapping", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubstitutionMappingsCapabilities capabilities;

    public SubstitutionMappings() {

    }

    public SubstitutionMappings(TopologyTemplate topologyTemplate, String nodeType, Map<String, String> properties,
                                SubstitutionMappingsRequirements requirements, SubstitutionMappingsCapabilities capabilities) {
        this.topologyTemplate = topologyTemplate;
        this.nodeType = nodeType;
        this.properties = properties;
        this.requirements = requirements;
        this.capabilities = capabilities;
    }

    public Long getId() {
        return id;
    }

    public TopologyTemplate getTopologyTemplate() {
        return topologyTemplate;
    }

    @JsonProperty("nodeType")
    public String getNodeType() {
        return nodeType;
    }

    @JsonProperty("properties")
    public Map<String, String> getProperties() {
        return properties;
    }

    @JsonProperty("requirements")
    public SubstitutionMappingsRequirements getRequirements() {
        return requirements;
    }

    @JsonProperty("capabilities")
    public SubstitutionMappingsCapabilities getCapabilities() {
        return capabilities;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }


    public void setNodeType(String s) {

        this.nodeType = s;
    }

    public void setRequirements(SubstitutionMappingsRequirements requirements) {
        this.requirements = requirements;
    }
}
