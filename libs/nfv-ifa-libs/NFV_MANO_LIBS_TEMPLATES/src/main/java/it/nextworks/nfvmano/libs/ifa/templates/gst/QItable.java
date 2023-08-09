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
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.ifa.templates.common.ResTypeValue;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashMap;
import java.util.Map;

@Entity
public class QItable {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    public Map<QosValue, SliceQoSparams> defaultValue=new HashMap<QosValue, SliceQoSparams>();

    public QItable(){    }

    //table for QoS default value, reference to 3GPP TS 23.501 g70, released on December 2020
    public void setTable(){
        this.defaultValue.put(QosValue.qi1, new SliceQoSparams(QosValue.qi1, ResTypeValue.GBR, 20, 100, (float) 0.01));
        this.defaultValue.put(QosValue.qi2, new SliceQoSparams(QosValue.qi2, ResTypeValue.GBR, 40, 150, (float) 0.001));
        this.defaultValue.put(QosValue.qi3, new SliceQoSparams(QosValue.qi3, ResTypeValue.GBR, 30, 50, (float) 0.001));
        this.defaultValue.put(QosValue.qi4, new SliceQoSparams(QosValue.qi4, ResTypeValue.GBR, 50, 300, (float) 0.000001));
        this.defaultValue.put(QosValue.qi65, new SliceQoSparams(QosValue.qi65, ResTypeValue.GBR, 7, 75, (float) 0.01));
        this.defaultValue.put(QosValue.qi66, new SliceQoSparams(QosValue.qi66, ResTypeValue.GBR, 20, 100, (float) 0.01));
        this.defaultValue.put(QosValue.qi67, new SliceQoSparams(QosValue.qi67, ResTypeValue.GBR, 15, 100, (float) 0.001));
        this.defaultValue.put(QosValue.qi75, new SliceQoSparams(QosValue.qi75, ResTypeValue.GBR, 25, 50, (float) 0.01));
        this.defaultValue.put(QosValue.qi71, new SliceQoSparams(QosValue.qi71, ResTypeValue.GBR, 56, 150, (float) 0.000001));
        this.defaultValue.put(QosValue.qi72, new SliceQoSparams(QosValue.qi72, ResTypeValue.GBR, 56, 300, (float) 0.0001));
        this.defaultValue.put(QosValue.qi73, new SliceQoSparams(QosValue.qi73, ResTypeValue.GBR, 56, 300, (float) 0.00000001));
        this.defaultValue.put(QosValue.qi74, new SliceQoSparams(QosValue.qi74, ResTypeValue.GBR, 56, 500, (float) 0.00000001));
        this.defaultValue.put(QosValue.qi76, new SliceQoSparams(QosValue.qi76, ResTypeValue.GBR, 56, 500, (float) 0.0001));
        this.defaultValue.put(QosValue.qi5, new SliceQoSparams(QosValue.qi5, ResTypeValue.NonGBR, 10, 100, (float) 0.000001));
        this.defaultValue.put(QosValue.qi6, new SliceQoSparams(QosValue.qi6, ResTypeValue.NonGBR, 60, 300, (float) 0.000001));
        this.defaultValue.put(QosValue.qi7, new SliceQoSparams(QosValue.qi7, ResTypeValue.NonGBR, 70, 100, (float) 0.001));
        this.defaultValue.put(QosValue.qi8, new SliceQoSparams(QosValue.qi8, ResTypeValue.NonGBR, 80, 300, (float) 0.000001));
        this.defaultValue.put(QosValue.qi9, new SliceQoSparams(QosValue.qi9, ResTypeValue.NonGBR, 90, 300, (float) 0.000001));
        this.defaultValue.put(QosValue.qi69, new SliceQoSparams(QosValue.qi69, ResTypeValue.NonGBR, 5, 60, (float) 0.000001));
        this.defaultValue.put(QosValue.qi70, new SliceQoSparams(QosValue.qi70, ResTypeValue.NonGBR, 55, 200, (float) 0.000001));
        this.defaultValue.put(QosValue.qi79, new SliceQoSparams(QosValue.qi79, ResTypeValue.NonGBR, 65, 50, (float) 0.01));
        this.defaultValue.put(QosValue.qi80, new SliceQoSparams(QosValue.qi80, ResTypeValue.NonGBR, 68, 10, (float) 0.000001));
        this.defaultValue.put(QosValue.qi82, new SliceQoSparams(QosValue.qi82, ResTypeValue.DelayCriticalGBR, 19, 10, (float) 0.0001));
        this.defaultValue.put(QosValue.qi83, new SliceQoSparams(QosValue.qi83, ResTypeValue.DelayCriticalGBR, 22, 10, (float) 0.0001));
        this.defaultValue.put(QosValue.qi84, new SliceQoSparams(QosValue.qi84, ResTypeValue.DelayCriticalGBR, 24, 30, (float) 0.00001));
        this.defaultValue.put(QosValue.qi85, new SliceQoSparams(QosValue.qi85, ResTypeValue.DelayCriticalGBR, 21, 5, (float) 0.00001));
        this.defaultValue.put(QosValue.qi86, new SliceQoSparams(QosValue.qi86, ResTypeValue.DelayCriticalGBR, 18, 5, (float) 0.0001));
    }
}
