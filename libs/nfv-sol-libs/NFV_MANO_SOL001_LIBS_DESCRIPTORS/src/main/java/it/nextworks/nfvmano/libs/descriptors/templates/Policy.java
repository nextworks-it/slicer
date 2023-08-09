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

import com.fasterxml.jackson.annotation.*;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.policies.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = AntiAffinityRule.class, name = "tosca.policies.nfv.AntiAffinityRule"),
        @JsonSubTypes.Type(value = InstantiationLevels.class, name = "tosca.policies.nfv.InstantiationLevels"),
        @JsonSubTypes.Type(value = ScalingAspects.class, name = "tosca.policies.nfv.ScalingAspects"),
        @JsonSubTypes.Type(value = SecurityGroupRule.class, name = "tosca.policies.nfv.SecurityGroupRule"),
        @JsonSubTypes.Type(value = SupportedVnfInterface.class, name = "tosca.policies.nfv.SupportedVnfInterface"),
        @JsonSubTypes.Type(value = VduInitialDelta.class, name = "tosca.policies.nfv.VduInitialDelta"),
        @JsonSubTypes.Type(value = VduScalingAspectDeltas.class, name = "tosca.policies.nfv.VduScalingAspectDeltas"),
        @JsonSubTypes.Type(value = VirtualLinkBitrateInitialDelta.class, name = "tosca.policies.nfv.VirtualLinkBitrateInitialDelta"),
        @JsonSubTypes.Type(value = VirtualLinkInstantiationLevels.class, name = "tosca.policies.nfv.VirtualLinkInstantiationLevels"),
        @JsonSubTypes.Type(value = VirtualLinkScalingAspectDeltas.class, name = "tosca.policies.nfv.VirtualLinkScalingAspectDeltas")})
public abstract class Policy implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private TopologyTemplate topologyTemplate;

    private String type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> targets = new ArrayList<>();

    public Policy() {

    }

    public Policy(String type, List<String> targets) {
        this.type = type;
        this.targets = targets;
    }

    public Policy(TopologyTemplate topologyTemplate, String type, List<String> targets) {
        this.topologyTemplate = topologyTemplate;
        this.type = type;
        this.targets = targets;
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

    @JsonProperty("targets")
    public List<String> getTargets() {
        return targets;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.type == null)
            throw new MalformattedElementException("Policy without type");
        if (this.targets == null || this.targets.isEmpty())
            throw new MalformattedElementException("Policy without targets");
    }
}
