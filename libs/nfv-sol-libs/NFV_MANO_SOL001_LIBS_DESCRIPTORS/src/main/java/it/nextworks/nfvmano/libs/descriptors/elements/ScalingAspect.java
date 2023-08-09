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
import it.nextworks.nfvmano.libs.descriptors.policies.ScalingAspectsProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ScalingAspect implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private ScalingAspectsProperties scalingAspectsProperties;

    private String name;
    private String description;
    private Integer maxScaleLevel;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> stepDeltas = new ArrayList<>();

    public ScalingAspect() {
    }

    public ScalingAspect(String name, String description, Integer maxScaleLevel, ArrayList<String> stepDeltas) {
        this.description = description;
        this.name = name;
        this.maxScaleLevel = maxScaleLevel;
        this.stepDeltas = stepDeltas;
    }

    public ScalingAspect(ScalingAspectsProperties scalingAspectsProperties, String name, String description, Integer maxScaleLevel, List<String> stepDeltas) {
        this.scalingAspectsProperties = scalingAspectsProperties;
        this.name = name;
        this.description = description;
        this.maxScaleLevel = maxScaleLevel;
        this.stepDeltas = stepDeltas;
    }

    public Long getId() {
        return id;
    }

    public ScalingAspectsProperties getScalingAspectsProperties() {
        return scalingAspectsProperties;
    }

    @JsonProperty("stepDeltas")
    public List<String> getStepDeltas() {
        return stepDeltas;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("maxScaleLevel")
    public Integer getMaxScaleLevel() {
        return maxScaleLevel;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.name == null)
            throw new MalformattedElementException("Scaling aspect without name");
        if (this.description == null)
            throw new MalformattedElementException("Scaling aspect without description");
        if (this.maxScaleLevel == null)
            throw new MalformattedElementException("Scaling aspect without maxScaleLevel");
    }

}
