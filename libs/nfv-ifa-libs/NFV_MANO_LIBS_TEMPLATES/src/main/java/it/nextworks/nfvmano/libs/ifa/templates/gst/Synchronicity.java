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

@Entity
public class Synchronicity {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.STRING)
    private SyncAvailValue availability=SyncAvailValue.NotSupported;
    private float accuracy=-1;

    public Synchronicity(){}

    public Synchronicity(SyncAvailValue availability){
        this.availability=availability;
    }

    public void setAvailability(SyncAvailValue availability) {
        this.availability = availability;
    }

    public void setAccouracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public void isValid()throws MalformattedElementException {
        if(this.availability==null && this.accuracy!=-1)
            throw new MalformattedElementException("availability value required");
    }

    public SyncAvailValue getAvailability() {
        return availability;
    }

    public float getAccuracy() {
        return accuracy;
    }
}


enum SyncAvailValue{
    NotSupported,
    BetweenBSandUE,
    BetweenBSandUE_UEandUE
}
