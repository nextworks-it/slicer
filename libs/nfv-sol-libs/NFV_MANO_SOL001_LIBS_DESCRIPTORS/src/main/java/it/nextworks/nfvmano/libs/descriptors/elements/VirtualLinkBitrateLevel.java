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
package it.nextworks.nfvmano.libs.descriptors.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.policies.VirtualLinkBitrateInitialDeltaProperties;
import it.nextworks.nfvmano.libs.descriptors.policies.VirtualLinkInstantiationLevelsProperties;
import it.nextworks.nfvmano.libs.descriptors.policies.VirtualLinkScalingAspectDeltasProperties;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class VirtualLinkBitrateLevel implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private VnfExtCpProperties vnfExtCpProperties;

    @ManyToOne
    @JsonIgnore
    private VirtualLinkInstantiationLevelsProperties vlInstantiationLevels;

    @ManyToOne
    @JsonIgnore
    private VirtualLinkScalingAspectDeltasProperties vlScalingAspectDeltas;

    @OneToOne
    @JsonIgnore
    private VirtualLinkBitrateInitialDeltaProperties vlBitrateInitialDelta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vlBitrateLevel", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LinkBitrateRequirements bitrateRequirements;

    public VirtualLinkBitrateLevel() {

    }

    public VirtualLinkBitrateLevel(LinkBitrateRequirements bitrateRequirements) {
        this.bitrateRequirements = bitrateRequirements;
    }

    public VirtualLinkBitrateLevel(VnfExtCpProperties vnfExtCpProperties, LinkBitrateRequirements bitrateRequirements) {
        this.vnfExtCpProperties = vnfExtCpProperties;
        this.bitrateRequirements = bitrateRequirements;
    }

    public VirtualLinkBitrateLevel(VirtualLinkInstantiationLevelsProperties vlInstantiationLevels, LinkBitrateRequirements bitrateRequirements) {
        this.vlInstantiationLevels = vlInstantiationLevels;
        this.bitrateRequirements = bitrateRequirements;
    }

    public VirtualLinkBitrateLevel(VirtualLinkScalingAspectDeltasProperties vlScalingAspectDeltas, LinkBitrateRequirements bitrateRequirements) {
        this.vlScalingAspectDeltas = vlScalingAspectDeltas;
        this.bitrateRequirements = bitrateRequirements;
    }

    public VirtualLinkBitrateLevel(VirtualLinkBitrateInitialDeltaProperties vlBitrateInitialDelta, LinkBitrateRequirements bitrateRequirements) {
        this.vlBitrateInitialDelta = vlBitrateInitialDelta;
        this.bitrateRequirements = bitrateRequirements;
    }

    public Long getId() {
        return id;
    }

    public VnfExtCpProperties getVnfExtCpProperties() {
        return vnfExtCpProperties;
    }

    public VirtualLinkInstantiationLevelsProperties getVlInstantiationLevels() {
        return vlInstantiationLevels;
    }

    public VirtualLinkScalingAspectDeltasProperties getVlScalingAspectDeltas() {
        return vlScalingAspectDeltas;
    }

    public VirtualLinkBitrateInitialDeltaProperties getVlBitrateInitialDelta() {
        return vlBitrateInitialDelta;
    }

    @JsonProperty("bitrateRequirements")
    public LinkBitrateRequirements getBitrateRequirements() {
        return bitrateRequirements;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.bitrateRequirements == null)
            throw new MalformattedElementException("VirtualLinkBitRateLevel without bitrateRequirements");
        else
            this.bitrateRequirements.isValid();
    }
}
