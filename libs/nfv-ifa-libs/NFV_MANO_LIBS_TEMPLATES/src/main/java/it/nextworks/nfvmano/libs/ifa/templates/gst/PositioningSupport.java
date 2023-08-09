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

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class PositioningSupport {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Embedded
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PosSupValue> availability;

    @Enumerated(EnumType.STRING)
    private FrequencyValue frequency;
    private float accuracy=-1;

    public PositioningSupport(){}

    public PositioningSupport(List<PosSupValue> availability, FrequencyValue frequency){
        this.availability=availability;
        this.frequency=frequency;
    }

    public void setAvailability(List<PosSupValue> availability) {
        this.availability = availability;
    }

    public void setFrequency(FrequencyValue frequency) {
        this.frequency = frequency;
    }

    public void setAccuracy(float accuracy) throws MalformattedElementException {
        if(this.availability!=null)
            this.accuracy = accuracy;
        else
            throw new MalformattedElementException("Availability value required");
    }

    public void isValid() throws MalformattedElementException{
        //If availability value is not set, it is meaningless the presence of frequency and accuracy
        if(this.availability==null && this.frequency!=null)
            throw new MalformattedElementException("Availability value required");

        //If availability is set, we think that a specification of frequency value is required, instead accuracy value is optional
        if(this.availability!=null && this.frequency==null)
            throw new MalformattedElementException("Frequency value required");
    }

    public List<PosSupValue> getAvailability() {
        return availability;
    }

    public FrequencyValue getFrequency() {
        return frequency;
    }

    public float getAccuracy() {
        return accuracy;
    }
}


enum PosSupValue{
    CIDECID,
    OTDOA,
    RFfingerprinting,
    AECID,
    HybridPositioning,
    NETRTK
}
