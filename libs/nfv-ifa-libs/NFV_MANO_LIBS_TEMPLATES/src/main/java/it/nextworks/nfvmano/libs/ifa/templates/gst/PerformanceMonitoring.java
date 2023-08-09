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


package it.nextworks.nfvmano.libs.ifa.templates.gst;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

@Entity
public class PerformanceMonitoring {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ElementCollection(targetClass = Float.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Float> availability;
    @Enumerated(EnumType.STRING)
    private FrequencyValue frequency;

    public PerformanceMonitoring(){}

    public PerformanceMonitoring(List<Float> availability, FrequencyValue frequency){
        this.availability=availability;
        this.frequency=frequency;
    }

    public void setAvailability(List<Float> availability) {
        this.availability = availability;
    }

    public void setFrequency(FrequencyValue frequency) {
        this.frequency = frequency;
    }

    //It is meaningless a monitoring frequency if performance monitoring availability is not set
    //I set -1 as default value to recognise if availability is set or not
    public void isValid() throws MalformattedElementException {
        if (this.availability==null && this.frequency!=null)
            throw new MalformattedElementException("Availability value required");
    }

    public List<Float> getAvailability() {
        return availability;
    }

    public FrequencyValue getFrequency() {
        return frequency;
    }
}
