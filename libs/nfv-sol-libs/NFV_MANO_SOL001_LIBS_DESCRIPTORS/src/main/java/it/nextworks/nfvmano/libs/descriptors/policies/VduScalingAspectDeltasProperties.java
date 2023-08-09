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
import it.nextworks.nfvmano.libs.descriptors.elements.VduLevel;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class VduScalingAspectDeltasProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VduScalingAspectDeltas vduScalingAspectDeltas;

    private String aspect;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, VduLevel> deltas = new HashMap<>();

    public VduScalingAspectDeltasProperties() {
    }

    public VduScalingAspectDeltasProperties(String aspect, Map<String, VduLevel> deltas) {
        this.aspect = aspect;
        this.deltas = deltas;
    }

    public VduScalingAspectDeltasProperties(VduScalingAspectDeltas vduScalingAspectDeltas, String aspect, Map<String, VduLevel> deltas) {
        this.vduScalingAspectDeltas = vduScalingAspectDeltas;
        this.aspect = aspect;
        this.deltas = deltas;
    }

    public Long getId() {
        return id;
    }

    public VduScalingAspectDeltas getVduScalingAspectDeltas() {
        return vduScalingAspectDeltas;
    }

    @JsonProperty("aspect")
    public String getAspect() {
        return aspect;
    }

    @JsonProperty("deltas")
    public Map<String, VduLevel> getDeltas() {
        return deltas;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.aspect == null)
            throw new MalformattedElementException("VduScalingAspectDeltasProperties without aspect");
        if (this.deltas == null || this.deltas.isEmpty())
            throw new MalformattedElementException("VduScalingAspectDeltasProperties without deltas");
        else
            for (Map.Entry<String, VduLevel> delta : this.deltas.entrySet()) {
                delta.getValue().isValid();
            }
    }
}
