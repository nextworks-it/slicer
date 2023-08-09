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
import it.nextworks.nfvmano.libs.descriptors.elements.ScalingAspect;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class ScalingAspectsProperties implements DescriptorInformationElement {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "scalingAspectsProperties", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    Map<String, ScalingAspect> aspects = new HashMap<>();

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private ScalingAspects scalingAspects;

    public ScalingAspectsProperties() {
    }

    public ScalingAspectsProperties(Map<String, ScalingAspect> aspects) {
        this.aspects = aspects;
    }

    public ScalingAspectsProperties(ScalingAspects scalingAspects, Map<String, ScalingAspect> aspects) {
        this.scalingAspects = scalingAspects;
        this.aspects = aspects;
    }

    public Long getId() {
        return id;
    }

    public ScalingAspects getScalingAspects() {
        return scalingAspects;
    }

    @JsonProperty("aspects")
    public Map<String, ScalingAspect> getAspects() {
        return aspects;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.aspects == null || this.aspects.isEmpty())
            throw new MalformattedElementException("ScalingAspectsProperties without aspects");
        else
            for (Map.Entry<String, ScalingAspect> aspect : this.aspects.entrySet()) {
                aspect.getValue().isValid();
            }
    }
}
