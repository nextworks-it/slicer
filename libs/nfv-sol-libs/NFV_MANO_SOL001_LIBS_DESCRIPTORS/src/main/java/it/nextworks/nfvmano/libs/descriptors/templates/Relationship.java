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
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public abstract class Relationship implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private TopologyTemplate topologyTemplate;

    private String type;
    private String description;

    public Relationship() {

    }

    public Relationship(TopologyTemplate topologyTemplate, String type, String description) {
        this.topologyTemplate = topologyTemplate;
        this.type = type;
        this.description = description;
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

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
