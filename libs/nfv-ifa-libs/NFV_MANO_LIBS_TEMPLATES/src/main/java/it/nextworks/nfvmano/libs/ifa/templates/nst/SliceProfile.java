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
package it.nextworks.nfvmano.libs.ifa.templates.nst;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
public class SliceProfile {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String sliceProfileId;
    //sNSSAIList Omitted
    //it cannot be mapped from GSMA GST
    @ElementCollection(targetClass=String.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> pLMNIdList;

    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<EMBBPerfReq> eMBBPerfReq;

    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<URLLCPerfReq> uRLLCPerfReq;

    private int maxNumberofUEs;
    //it cannot be mapped from GSMA GST
    @ElementCollection(targetClass=String.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> coverageAreaTAList;

    private int latency;
    //it cannot be mapped from GSMA GST
    @Enumerated(EnumType.STRING)
    private UEMobilityLevel uEMobilityLevel;
    private Boolean resourceSharingLevel; // SHARED = true, NOT-SHARED =  false

    public SliceProfile(){}

    /**
     * @param sliceProfileId
     * @param pLMNIdList
     */
    public SliceProfile(String sliceProfileId, List<String> pLMNIdList){
        this.sliceProfileId=sliceProfileId;
        this.pLMNIdList=pLMNIdList;
    }

    public SliceProfile(SliceProfile sliceProfile){
        this.sliceProfileId=sliceProfile.sliceProfileId;
        this.pLMNIdList=sliceProfile.pLMNIdList;
    }

    public void isValid() throws MalformattedElementException {
        if(sliceProfileId==null)
            throw new MalformattedElementException("Slice profile id is not set, required");
        if(uRLLCPerfReq==null && eMBBPerfReq==null)
            throw new MalformattedElementException("URLLC Performance requirements or eMBB performance requirements must be set");
        if (uRLLCPerfReq!=null && eMBBPerfReq!=null)
            throw new MalformattedElementException("Only one between uRLLCPerfReq and eMBBPerfReq can be not null");
    }

    public void setSliceProfileId(String sliceProfileId) {
        this.sliceProfileId = sliceProfileId;
    }

    public String getSliceProfileId() {
        return sliceProfileId;
    }

    public void setpLMNIdList(List<String> pLMNIdList) {
        this.pLMNIdList = pLMNIdList;
    }

    public List<String> getpLMNIdList() {
        return pLMNIdList;
    }

    public List<EMBBPerfReq> geteMBBPerfReq() {
        return eMBBPerfReq;
    }

    public void seteMBBPerfReq(List<EMBBPerfReq> eMBBPerfReq) {
        this.eMBBPerfReq = eMBBPerfReq;
    }

    public List<URLLCPerfReq> getuRLLCPerfReq() {
        return uRLLCPerfReq;
    }

    public void setuRLLCPerfReq(List<URLLCPerfReq> uRLLCPerfReq) {
        this.uRLLCPerfReq = uRLLCPerfReq;
    }

    public void setMaxNumberofUEs(int maxNumberofUEs) {
        this.maxNumberofUEs = maxNumberofUEs;
    }

    public int getMaxNumberofUEs() {
        return maxNumberofUEs;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public int getLatency() {
        return latency;
    }

    public void setCoverageAreaTAList(List<String> coverageAreaTAList) {
        this.coverageAreaTAList = coverageAreaTAList;
    }

    public List<String> getCoverageAreaTAList() {
        return coverageAreaTAList;
    }

    public void setuEMobilityLevel(UEMobilityLevel uEMobilityLevel) {
        this.uEMobilityLevel = uEMobilityLevel;
    }

    public UEMobilityLevel getuEMobilityLevel() {
        return uEMobilityLevel;
    }

    public void setResourceSharingLevel(Boolean resourceSharingLevel) {
        this.resourceSharingLevel = resourceSharingLevel;
    }

    public Boolean getResourceSharingLevel() {
        return resourceSharingLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
