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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class PerformancePrediction {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Embedded
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PPValue> availability=new ArrayList<PPValue>();

    @Enumerated(EnumType.STRING)
    private FrequencyValue predictionFrequency;

    public PerformancePrediction(){}

    public PerformancePrediction(List<PPValue> availability, FrequencyValue predictionFrequency){
        this.availability=availability;
        this.predictionFrequency=predictionFrequency;
    }

    public void setAvailability(List<PPValue> availability) {
        this.availability = availability;
    }

    public void setPredictionFrequency(FrequencyValue predictionFrequency) {
        this.predictionFrequency = predictionFrequency;
    }

    //It is meaningless a prediction frequency if performance prediction availability is not set
    public void isValid() throws MalformattedElementException {
        if(this.availability==null && this.predictionFrequency!=null)
            throw new MalformattedElementException("Availability value required");
    }

    public List<PPValue> getAvailability() {
        return availability;
    }

    public FrequencyValue getPredictionFrequency() {
        return predictionFrequency;
    }
}


enum PPValue{
    Throughput,
    Latency,
    SRSR
}
