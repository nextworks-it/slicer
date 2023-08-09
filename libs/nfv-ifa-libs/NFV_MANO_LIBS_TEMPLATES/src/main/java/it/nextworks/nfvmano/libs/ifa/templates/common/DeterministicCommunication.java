/*
 * Copyright (c) 2021 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package it.nextworks.nfvmano.libs.ifa.templates.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

@Entity
public class DeterministicCommunication {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    private Boolean detCommAvailability = false;
    @ElementCollection(targetClass = Float.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Float>  detCommPeriodicity;

    public DeterministicCommunication() {
    }

    public DeterministicCommunication(Boolean detCommAvailability, List<Float> detCommPeriodicity) {
        this.detCommAvailability = detCommAvailability;
        this.detCommPeriodicity = detCommPeriodicity;
    }

    public void isValid() throws MalformattedElementException {
        if (this.detCommAvailability && this.detCommPeriodicity == null) {
            throw new MalformattedElementException("Deterministic communication periodicity is required");
        }
    }

    public void setDetCommAvailability(Boolean detCommAvailability) {
        this.detCommAvailability = detCommAvailability;
    }

    public Boolean getDetCommAvailability() {
        return this.detCommAvailability;
    }

    public void setDetCommPeriodicity(List<Float> detCommPeriodicity) {
        this.detCommPeriodicity = detCommPeriodicity;
    }

    public List<Float> getDetCommPeriodicity() {
        return this.detCommPeriodicity;
    }
}
