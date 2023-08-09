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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import it.nextworks.nfvmano.libs.ifa.templates.common.DeterministicCommunication;
import it.nextworks.nfvmano.libs.ifa.templates.common.ResTypeValue;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Service Profile for the NST - Defined @3gpp-TS28.541 v.16.06.2 - Clause 6.3.3
 */

@Entity
public class NstServiceProfile {
	
	@Id
    @GeneratedValue
    @JsonIgnore
	private Long id;
	
    private String serviceProfileId;
    //sNSSAIList Omitted
    //it cannot be mapped from GSMA GST
    @ElementCollection(targetClass=String.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> pLMNIdList;
    
    private int maxNumberofUEs;
    private String coverageArea; //uncertain if it has to be change
    private int latency;
    
    @Enumerated(EnumType.STRING)
    private UEMobilityLevel uEMobilityLevel;
    private Boolean resourceSharingLevel; // SHARED = true, NOT-SHARED =  false
    @Enumerated(EnumType.STRING)
    private SliceType sST;
    private float availability;

    //new attribute inserted in this version
    //they can be set only we the setter because they are all optional parameters
    private Boolean delayTolerance=false; //"NOT SUPPORTED"=false, "SUPPORTED"=true
    @OneToOne(cascade = {CascadeType.ALL})
    private DeterministicCommunication deterministicComm;
    private int dLThptPerSlice;
    private int dLThptPerUE;
    private float guaThpt;
    private float maxThpt;
    private int uLThptPerSlice;
    private int uLThptPerUE;
    //for simplicity we use only one attribute instead of creating a new class with only one attribute
    private int maxPktSize;
    private int maxNumberofConns;

    @ElementCollection(targetClass = Float.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Float> kPIMonitoringList;
    private boolean userMgmtOpen=false;//"NOT SUPPORTED"=false, "SUPPORTED"=true
    //for simplicity we use only one attribute instead of creating a new class with only one attribute
    private boolean v2xMode=false; //"NOT SUPPORTED"=false, "SUPPORTED BY NR"=true

    //These attributes might are redundant
    private int termDensity;
    private int uESpeed;

    //These attributes cannot be remapped from the GSMA GST
    private float activityFactor;
    private int jitter;
    private String survivalTime;
    private String reliability;

    //Non-standard attribute, added to facilitate the translation from NST to NEST
    @Enumerated(EnumType.STRING)
    private ResTypeValue resourceType;

    public NstServiceProfile() {
    }

    /**
     * @param serviceProfileId
     * @param pLMNIdList
     * @param maxNumberofUEs
     * @param coverageArea
     * @param latency
     * @param uEMobilityLevel See 6.2.1 of TS 22.261
     * @param resourceSharingLevel
     * @param sST SliceType (e.g. urllc)
     * @param availability in %
     */
    public NstServiceProfile(String serviceProfileId, List<String> pLMNIdList,
                             int maxNumberofUEs, String coverageArea, int latency,
                             UEMobilityLevel uEMobilityLevel, Boolean resourceSharingLevel, SliceType sST,
                             float availability) {
        this.serviceProfileId = serviceProfileId;
        this.pLMNIdList = pLMNIdList;
        this.maxNumberofUEs = maxNumberofUEs;
        this.coverageArea = coverageArea;
        this.latency = latency;
        this.uEMobilityLevel = uEMobilityLevel;
        this.resourceSharingLevel = resourceSharingLevel;
        this.sST = sST;
        this.availability = availability;
    }

    public NstServiceProfile(NstServiceProfile nstServiceProfile) {
        this.serviceProfileId = nstServiceProfile.serviceProfileId;
        this.pLMNIdList = new ArrayList<>();
        if(nstServiceProfile.pLMNIdList!=null && nstServiceProfile.pLMNIdList.size()>0){
            for(String pLMNId: nstServiceProfile.pLMNIdList){
                this.pLMNIdList.add(pLMNId);
            }
        }
        this.maxNumberofUEs = nstServiceProfile.maxNumberofUEs;
        this.coverageArea = nstServiceProfile.coverageArea;
        this.latency = nstServiceProfile.latency;
        this.uEMobilityLevel = nstServiceProfile.uEMobilityLevel;
        this.resourceSharingLevel = nstServiceProfile.resourceSharingLevel;
        this.sST = nstServiceProfile.sST;
        this.availability = nstServiceProfile.availability;
        this.dLThptPerSlice= nstServiceProfile.dLThptPerSlice;
        this.dLThptPerUE= nstServiceProfile.dLThptPerUE;
        this.guaThpt= nstServiceProfile.guaThpt;
        this.maxThpt= nstServiceProfile.maxThpt;
        this.uLThptPerSlice= nstServiceProfile.uLThptPerSlice;
        this.uLThptPerUE= nstServiceProfile.uLThptPerUE;
        this.maxPktSize= nstServiceProfile.maxPktSize;
        this.maxNumberofConns = nstServiceProfile.maxNumberofConns;
        this.kPIMonitoringList = new ArrayList<>();
        if(nstServiceProfile.kPIMonitoringList!=null && nstServiceProfile.kPIMonitoringList.size()>0){
            for(Float kPIMonitoring: nstServiceProfile.kPIMonitoringList){
                this.kPIMonitoringList.add(kPIMonitoring);
            }
        }
        this.jitter = nstServiceProfile.jitter;



    }

    public String getServiceProfileId() {
        return serviceProfileId;
    }

    public void setServiceProfileId(String serviceProfileId) {
        this.serviceProfileId = serviceProfileId;
    }

    public List<String> getpLMNIdList() {
        return pLMNIdList;
    }

    public void setpLMNIdList(List<String> pLMNIdList) {
        this.pLMNIdList = pLMNIdList;
    }

    public int getMaxNumberofUEs() {
        return maxNumberofUEs;
    }

    public void setMaxNumberofUEs(int maxNumberofUEs) {
        this.maxNumberofUEs = maxNumberofUEs;
    }

    public String getCoverageArea() {
        return coverageArea;
    }

    public void setCoverageArea(String coverageArea) {
        this.coverageArea = coverageArea;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public UEMobilityLevel getuEMobilityLevel() {
        return uEMobilityLevel;
    }

    public void setuEMobilityLevel(UEMobilityLevel uEMobilityLevel) {
        this.uEMobilityLevel = uEMobilityLevel;
    }

    public Boolean getResourceSharingLevel() {
        return resourceSharingLevel;
    }

    public void setResourceSharingLevel(Boolean resourceSharingLevel) {
        this.resourceSharingLevel = resourceSharingLevel;
    }

    public SliceType getsST() {
        return sST;
    }

    public void setsST(SliceType sST) {
        this.sST = sST;
    }

    public float getAvailability() {
        return availability;
    }

    public void setAvailability(float availability) {
        this.availability = availability;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public void setDelayTolerance(Boolean delayTolerance) {
        this.delayTolerance = delayTolerance;
    }

    public Boolean getDelayTolerance() {
        return delayTolerance;
    }

    public void setDeterministicComm(DeterministicCommunication deterministicComm) {
        this.deterministicComm = deterministicComm;
    }

    public DeterministicCommunication getDeterministicComm() {
        return deterministicComm;
    }

    public void setdLThptPerSlice(int dLThptPerSlice) {
        this.dLThptPerSlice = dLThptPerSlice;
    }

    public int getdLThptPerSlice() {
        return dLThptPerSlice;
    }

    public void setdLThptPerUE(int dLThptPerUE) {
        this.dLThptPerUE = dLThptPerUE;
    }

    public int getdLThptPerUE() {
        return dLThptPerUE;
    }

    public void setGuaThpt(float guaThpt) {
        this.guaThpt = guaThpt;
    }

    public float getGuaThpt() {
        return guaThpt;
    }

    public void setMaxThpt(float maxThpt) {
        this.maxThpt = maxThpt;
    }

    public float getMaxThpt() {
        return maxThpt;
    }

    public void setuLThptPerSlice(int uLThptPerSlice) {
        this.uLThptPerSlice = uLThptPerSlice;
    }

    public int getuLThptPerSlice() {
        return uLThptPerSlice;
    }

    public void setuLThptPerUE(int uLThptPerUE) {
        this.uLThptPerUE = uLThptPerUE;
    }

    public int getuLThptPerUE() {
        return uLThptPerUE;
    }

    public void setMaxPktSize(int maxPktSize) {
        this.maxPktSize = maxPktSize;
    }

    public int getMaxPktSize() {
        return maxPktSize;
    }

    public void setMaxNumberofConns(int maxNumberofConns) {
        this.maxNumberofConns = maxNumberofConns;
    }

    public int getMaxNumberofConns() {
        return maxNumberofConns;
    }

    public void setkPIMonitoringList(List<Float> kPIMonitoringList) {
        List<Float> tmp=new ArrayList<>();
        for(Float s: kPIMonitoringList)
            tmp.add(s);
        this.kPIMonitoringList = tmp ;
    }

    public List<Float> getkPIMonitoringList() {
        return kPIMonitoringList;
    }

    public void setUserMgmtOpen(boolean userMgmtOpen) {
        this.userMgmtOpen = userMgmtOpen;
    }

    public boolean isUserMgmtOpen() {
        return userMgmtOpen;
    }

    public void setV2xMode(boolean v2xMode) {
        this.v2xMode = v2xMode;
    }

    public boolean isV2xMode() {
        return v2xMode;
    }

    public void setTermDensity(int termDensity) {
        this.termDensity = termDensity;
    }

    public int getTermDensity() {
        return termDensity;
    }

    public void setActivityFactor(float activityFactor) {
        this.activityFactor = activityFactor;
    }

    public float getActivityFactor() {
        return activityFactor;
    }

    public void setuESpeed(int uESpeed) {
        this.uESpeed = uESpeed;
    }

    public int getuESpeed() {
        return uESpeed;
    }

    public void setJitter(int jitter) {
        this.jitter = jitter;
    }

    public int getJitter() {
        return jitter;
    }

    public void setSurvivalTime(String survivalTime) {
        this.survivalTime = survivalTime;
    }

    public String getSurvivalTime() {
        return survivalTime;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    public String getReliability() {
        return reliability;
    }

    public ResTypeValue getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResTypeValue resourceType) {
        this.resourceType = resourceType;
    }
}
