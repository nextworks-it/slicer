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
package it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.templates.Node;
import it.nextworks.nfvmano.libs.descriptors.templates.TopologyTemplate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@JsonTypeName("NSNode")
public class NSNode extends Node implements DescriptorInformationElement {

    @Embedded
    private NSProperties properties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nsNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private NSRequirements requirements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nsNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private NSInterfaces interfaces;

    public NSNode() {

    }

    public NSNode(String type, NSProperties properties, NSRequirements requirements) {
        super(type);
        this.properties = properties;
        this.requirements = requirements;
    }

    public NSNode(TopologyTemplate topologyTemplate, String type, NSProperties properties,
                  NSRequirements requirements) {
        super(topologyTemplate, type);
        this.properties = properties;
        this.requirements = requirements;
    }

    @JsonProperty("properties")
    public NSProperties getProperties() {
        return properties;
    }

    @JsonProperty("requirements")
    public NSRequirements getRequirements() {
        return requirements;
    }

    @JsonProperty("interfaces")
    public NSInterfaces getInterfaces() {
        return interfaces;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.properties == null)
            throw new MalformattedElementException("NS Node without properties");
        else
            this.properties.isValid();
        if (this.requirements != null) {
            this.requirements.isValid();
        }
    }


    public void setInterfaces(NSInterfaces nsInterfaces){ this.interfaces = nsInterfaces;}
    public void setProperties(NSProperties nsProperties) {
        this.properties=nsProperties;
    }

    public void setRequirements(NSRequirements nsRequirements) {
        this.requirements=nsRequirements;
    }
}
