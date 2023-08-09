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
import it.nextworks.nfvmano.libs.ifa.templates.common.ResTypeValue;

@Entity
public class SliceQoSparams {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.STRING)
    private QosValue qosIndicator;
    @Enumerated(EnumType.STRING)
    private ResTypeValue resourceType;
    private int priorityLevel=-1;
    private float packetDelayBudget=-1;
    private float packerErrorRate=-1;
    private float averagingWindow=-1;
    private int maxDataBurstVolume=-1;
    private int maxPacketLossRate=-1;

    @Transient
    private final QItable qItable=new QItable();

    public SliceQoSparams(){}

    public SliceQoSparams(QosValue qosIndicator, ResTypeValue resourceType, int priorityLevel, float packetDelayBudget, float packerErrorRate){
        this.qosIndicator=qosIndicator;
        this.resourceType=resourceType;
        this.priorityLevel=priorityLevel;
        this.packetDelayBudget=packetDelayBudget;
        this.packerErrorRate=packerErrorRate;
    }

    //The setter of qosIndicator, set also all the other value to default value referenced in 3GPP TS 23.501 g70
    public void setQosIndicator(QosValue qosIndicator) {
        this.qosIndicator=qosIndicator;
        this.qItable.setTable();
        SliceQoSparams tmp=this.qItable.defaultValue.get(qosIndicator);
        this.resourceType=tmp.getResourceType();
        this.priorityLevel=tmp.getPriorityLevel();
        this.packetDelayBudget=tmp.getPacketDelayBudget();
        this.packerErrorRate=tmp.getPackerErrorRate();
    }

    public void setResourceType(ResTypeValue resourceType) {
        this.resourceType = resourceType;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setPacketDelayBudget(float packetDelayBudget) {
        this.packetDelayBudget = packetDelayBudget;
    }

    public void setPackerErrorRate(float packerErrorRate) {
        this.packerErrorRate = packerErrorRate;
    }

    public void setAveragingWindow(float averagingWindow) {
        this.averagingWindow = averagingWindow;
    }

    public void setMaxDataBurstVolume(int maxDataBurstVolume) {
        this.maxDataBurstVolume = maxDataBurstVolume;
    }

    public void setMaxPacketLossRate(int maxPacketLossRate) {
        this.maxPacketLossRate = maxPacketLossRate;
    }

    public void isValid() throws MalformattedElementException {
        this.qItable.setTable();
        if(this.qosIndicator!=null && (this.priorityLevel==-1 || this.resourceType==null || this.packerErrorRate==-1 ||
                this.packetDelayBudget==-1))
            throw new MalformattedElementException("required value missing");

        if(this.qosIndicator==null && (this.priorityLevel!=-1 || this.resourceType!=null || this.packerErrorRate!=-1 ||
                this.packetDelayBudget!=-1 || this.averagingWindow!=-1 || this.maxDataBurstVolume!=-1 ||
                this.maxPacketLossRate!=-1))
            throw new MalformattedElementException("QoS Indicator required");

        //5QI resource type control, every 5QI must have the right resource type
        SliceQoSparams tmp=this.qItable.defaultValue.get(this.qosIndicator);
        if(this.resourceType!=tmp.getResourceType())
            throw new MalformattedElementException("Invalid resource type set");
    }

    public QosValue getQosIndicator() {
        return qosIndicator;
    }

    public ResTypeValue getResourceType() {
        return resourceType;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public float getPackerErrorRate() {
        return packerErrorRate;
    }

    public float getPacketDelayBudget() {
        return packetDelayBudget;
    }

    public float getAveragingWindow() {
        return averagingWindow;
    }

    public int getMaxDataBurstVolume() {
        return maxDataBurstVolume;
    }

    public int getMaxPacketLossRate() {
        return maxPacketLossRate;
    }
}

