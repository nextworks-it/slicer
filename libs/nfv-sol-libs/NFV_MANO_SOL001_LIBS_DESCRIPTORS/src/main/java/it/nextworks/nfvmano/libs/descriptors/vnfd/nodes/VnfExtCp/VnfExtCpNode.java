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
package it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp;

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
public class VnfExtCpNode extends Node implements DescriptorInformationElement {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfExtCpNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfExtCpProperties properties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfExtCpNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfExtCpRequirements requirements;

    public VnfExtCpNode() {

    }

    public VnfExtCpNode(String type, VnfExtCpProperties properties,
                        VnfExtCpRequirements requirements) {
        super(type);
        this.properties = properties;
        this.requirements = requirements;
    }

    public VnfExtCpNode(TopologyTemplate topologyTemplate, String type, VnfExtCpProperties properties,
                        VnfExtCpRequirements requirements) {
        super(topologyTemplate, type);
        this.properties = properties;
        this.requirements = requirements;
    }

    @JsonProperty("properties")
    public VnfExtCpProperties getProperties() {
        return properties;
    }

    @JsonProperty("requirements")
    public VnfExtCpRequirements getRequirements() {
        return requirements;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.properties == null)
            throw new MalformattedElementException("VnfExtCp Node without properties");
        else
            this.properties.isValid();
        if (this.requirements == null)
            throw new MalformattedElementException("VnfExtCp Node without requirements");
        else
            this.requirements.isValid();
    }

}
