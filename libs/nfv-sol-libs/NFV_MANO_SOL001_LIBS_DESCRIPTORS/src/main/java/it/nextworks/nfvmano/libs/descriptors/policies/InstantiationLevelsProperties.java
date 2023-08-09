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
import it.nextworks.nfvmano.libs.descriptors.elements.InstantiationLevel;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class InstantiationLevelsProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private InstantiationLevels instantiationLevels;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "instantiationLevelsProperties", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, InstantiationLevel> levels = new HashMap<>();
    private String defaultLevel;

    public InstantiationLevelsProperties() {
    }

    public InstantiationLevelsProperties(Map<String, InstantiationLevel> levels, String defaultLevel) {
        this.levels = levels;
        this.defaultLevel = defaultLevel;
    }

    public InstantiationLevelsProperties(InstantiationLevels instantiationLevels, Map<String, InstantiationLevel> levels, String defaultLevel) {
        this.instantiationLevels = instantiationLevels;
        this.levels = levels;
        this.defaultLevel = defaultLevel;
    }

    public Long getId() {
        return id;
    }

    public InstantiationLevels getInstantiationLevels() {
        return instantiationLevels;
    }

    @JsonProperty("levels")
    public Map<String, InstantiationLevel> getLevels() {
        return levels;
    }

    @JsonProperty("defaultLevel")
    public String getDefaultLevel() {
        return defaultLevel;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.levels == null || this.levels.isEmpty())
            throw new MalformattedElementException("InstantiationLevelsProperties without levels");
        else
            for (Map.Entry<String, InstantiationLevel> level : this.levels.entrySet()) {
                level.getValue().isValid();
            }
    }
}
