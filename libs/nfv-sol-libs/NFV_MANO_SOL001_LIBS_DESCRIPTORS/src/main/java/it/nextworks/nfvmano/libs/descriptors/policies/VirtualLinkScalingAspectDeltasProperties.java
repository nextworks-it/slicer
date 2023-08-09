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
package it.nextworks.nfvmano.libs.descriptors.policies;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.VirtualLinkBitrateLevel;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class VirtualLinkScalingAspectDeltasProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VirtualLinkScalingAspectDeltas virtualLinkScalingAspectDeltas;

    private String aspect;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "vlScalingAspectDeltas", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, VirtualLinkBitrateLevel> aspectDeltas = new HashMap<>();

    public VirtualLinkScalingAspectDeltasProperties() {
    }

    public VirtualLinkScalingAspectDeltasProperties(String aspect, Map<String, VirtualLinkBitrateLevel> aspectDeltas) {
        this.aspect = aspect;
        this.aspectDeltas = aspectDeltas;
    }

    public VirtualLinkScalingAspectDeltasProperties(VirtualLinkScalingAspectDeltas virtualLinkScalingAspectDeltas, String aspect, Map<String, VirtualLinkBitrateLevel> aspectDeltas) {
        this.virtualLinkScalingAspectDeltas = virtualLinkScalingAspectDeltas;
        this.aspect = aspect;
        this.aspectDeltas = aspectDeltas;
    }

    public Long getId() {
        return id;
    }

    public VirtualLinkScalingAspectDeltas getVirtualLinkScalingAspectDeltas() {
        return virtualLinkScalingAspectDeltas;
    }

    @JsonProperty("aspect")
    public String getAspect() {
        return aspect;
    }

    @JsonProperty("aspectDeltas")
    public Map<String, VirtualLinkBitrateLevel> getAspectDeltas() {
        return aspectDeltas;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.aspect == null)
            throw new MalformattedElementException("VirtualLinkScalingAspectDeltasProperties without aspect");
        if (this.aspectDeltas == null || this.aspectDeltas.isEmpty())
            throw new MalformattedElementException("VirtualLinkScalingAspectDeltasProperties without deltas");
        else
            for (Map.Entry<String, VirtualLinkBitrateLevel> delta : this.aspectDeltas.entrySet()) {
                delta.getValue().isValid();
            }
    }
}
