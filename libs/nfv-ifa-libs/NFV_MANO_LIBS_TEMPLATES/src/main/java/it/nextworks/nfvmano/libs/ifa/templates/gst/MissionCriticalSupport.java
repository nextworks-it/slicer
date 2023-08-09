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

@Entity
public class MissionCriticalSupport {

    @Id
    @GeneratedValue
    @JsonIgnore
    private long Id;

    private Boolean missionCriticalSupport=false;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<MCCSValue> mcCapabilitySupport;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<MCSSValue> mcServiceSupport;

    public MissionCriticalSupport(){}

    public MissionCriticalSupport(Boolean missionCriticalSupport, List<MCCSValue> mcCapabilitySupport, List<MCSSValue> mcServiceSupport){
        this.missionCriticalSupport=missionCriticalSupport;
        this.mcCapabilitySupport=mcCapabilitySupport;
        this.mcServiceSupport=mcServiceSupport;
    }

    public void setMissionCriticalSupport(Boolean missionCriticalSupport) {
        this.missionCriticalSupport = missionCriticalSupport;
    }

    public void setMcCapabilitySupport(List<MCCSValue> mcCapabilitySupport){
        this.mcCapabilitySupport=mcCapabilitySupport;
    }

    public void setMcServiceSupport(List<MCSSValue> mcServiceSupport) {
        this.mcServiceSupport = mcServiceSupport;
    }

    public void isValid() throws MalformattedElementException {
        if(this.missionCriticalSupport && (this.mcCapabilitySupport==null || this.mcServiceSupport==null))
            throw new MalformattedElementException("Invalid argoument");

        if(!this.missionCriticalSupport && (this.mcCapabilitySupport!=null || this.mcServiceSupport!=null))
            throw new MalformattedElementException("Setting error, no allowed value");
    }

    public Boolean getMissionCriticalSupport() {
        return missionCriticalSupport;
    }

    public List<MCCSValue> getMcCapabilitySupport() {
        return mcCapabilitySupport;
    }

    public List<MCSSValue> getMcServiceSupport() {
        return mcServiceSupport;
    }
}


enum MCCSValue{
    Interuser_prioritization,
    Preemption,
    Local_control
}


enum MCSSValue{
    MCPTT,
    MCData,
    MCVideo,
    MCinterworking
}