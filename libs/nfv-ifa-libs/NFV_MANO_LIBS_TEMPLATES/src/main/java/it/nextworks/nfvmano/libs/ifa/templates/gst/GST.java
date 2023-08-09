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
import it.nextworks.nfvmano.libs.ifa.templates.common.DeterministicCommunication;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;


//GST refers to GSMA version 4.0
@Entity
public class GST {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String gstId;
    private String gstName;
    private String gstVersion;
    private String gstProvider;

    private float availability;

    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AreaOfService> areaOfServices = new ArrayList();

    private Boolean delayTolerance = false;
    @OneToOne(cascade = {CascadeType.ALL})
    private it.nextworks.nfvmano.libs.ifa.templates.common.DeterministicCommunication deterministicCommunication;
    @OneToOne(cascade = {CascadeType.ALL})
    private DownlinkThroughputNS downlinkThroughputNS;
    private int maximumDownlinkThroughputUE;

    @OneToOne(cascade = {CascadeType.ALL})
    private EnergyEfficiency energyEfficiency;
    @Enumerated(EnumType.STRING)
    private GroupCommSuppValue groupCommunicationSupport=GroupCommSuppValue.NotSupported;
    @OneToOne(cascade = {CascadeType.ALL})
    private Isolation isolation= new Isolation(); //it is required to set the default value inside Isolation
    private int maxSuppPacketSize=1500;
    @OneToOne(cascade = {CascadeType.ALL})
    private MissionCriticalSupport missionCriticalSupport;
    private Boolean mmTelSupport=false;
    private Boolean nbIoTSupport=false;

    @ElementCollection(targetClass = String.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> nfOwned=new ArrayList<String>();

    @OneToOne(cascade = {CascadeType.ALL})
    private MaxNPDUsess maxNPDUsess;
    @OneToOne(cascade = {CascadeType.ALL})
    private MaxNUE maxNUE;

    @OneToOne(cascade = {CascadeType.ALL})
    private PerformanceMonitoring performanceMonitoring;
    @OneToOne(cascade = {CascadeType.ALL})
    private PerformancePrediction performancePrediction;

    @OneToOne(cascade = {CascadeType.ALL})
    private PositioningSupport positioningSupport;
    @OneToOne(cascade = {CascadeType.ALL})
    private RadioSpectrum radioSpectrum;

    @Enumerated(EnumType.STRING)
    private RCIValue rootCauseInvestigation=RCIValue.NotSupported;

    @Enumerated(EnumType.STRING)
    private SSCSupportValue sscSupport;
    @Enumerated(EnumType.STRING)
    private SUNSliceValue simultUseNSlice=SUNSliceValue.AnyNetSlice;

    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SliceQoSparams> sliceQoSparams;

    private Boolean supportNonIpTraffic=false;
    private int supportedDeviceVelocity;
    @OneToOne(cascade = {CascadeType.ALL})
    private Synchronicity synchronicity;
    private int ueDensity=0;
    @OneToOne(cascade = {CascadeType.ALL})
    private UplinkThroughputNS uplinkThroughputNS;
    private int maxUplinkThroughputUE;
    private Boolean userManagementOpeness=false;
    @OneToOne(cascade = {CascadeType.ALL})
    private UserDataAccess userDataAccess;

    @Enumerated(EnumType.STRING)
    private V2xCommValue v2xCommunicationMode=V2xCommValue.NotSupported;
    private float latencyApplicationServer;
    private Boolean nssaaRequired=false;
    @OneToOne(cascade = {CascadeType.ALL})
    private MultimediaPriorityServiceSupp mpsSupport;

    public GST() {}

    /**
     * Constructor
     *
     * @param gstId ID of the GST
     * @param gstName name of the GST
     * @param gstProvider provider of the GST
     * @param gstVersion Version of the GST
     */
    public GST(String gstId, String gstName, String gstProvider, String gstVersion){
        this.gstId=gstId;
        this.gstName=gstName;
        this.gstProvider=gstProvider;
        this.gstVersion=gstVersion;
    }

    public void setGstId(String gstId) {
        this.gstId = gstId;
    }

    public void setGstName(String gstName) {
        this.gstName = gstName;
    }

    public void setGstProvider(String gstProvider){
        this.gstProvider=gstProvider;
    }

    public void setGstVersion(String gstVersion) {
        this.gstVersion = gstVersion;
    }

    public void setAvailability(float availability) {
        this.availability = availability;
    }

    public void setAreaOfServices(List<AreaOfService> areaOfServices) throws MalformattedElementException {
        this.areaOfServices = areaOfServices;
        for(AreaOfService area: areaOfServices)
            area.isValid();
    }

    public void setDelayTolerance(Boolean delayTolerance) {
        this.delayTolerance = delayTolerance;
    }

    public void setDeterministicCommunication(it.nextworks.nfvmano.libs.ifa.templates.common.DeterministicCommunication deterministicCommunication) throws MalformattedElementException {
        this.deterministicCommunication = deterministicCommunication;
        this.deterministicCommunication.isValid();
    }

    public void setDownlinkThroughputNS(DownlinkThroughputNS downlinkThroughputNS) {
        this.downlinkThroughputNS = downlinkThroughputNS;
    }

    public void setMaximumDownlinkThroughputUE(int maximumDownlinkThoughputUE) {
        this.maximumDownlinkThroughputUE = maximumDownlinkThoughputUE;
    }

    public void setEnergyEfficiency(EnergyEfficiency energyEfficiency) throws MalformattedElementException {
        this.energyEfficiency = energyEfficiency;
        this.energyEfficiency.isValid();
    }

    public void setGroupCommunicationSupport(GroupCommSuppValue groupCommunicationSupport) {
        this.groupCommunicationSupport = groupCommunicationSupport;
    }

    public void setIsolation(Isolation isolation) {
        this.isolation = isolation;
    }

    public void setMaxSuppPacketSize(int maxSuppPacketSize) {
        this.maxSuppPacketSize = maxSuppPacketSize;
    }

    public void setMissionCriticalSupport(MissionCriticalSupport missionCriticalSupport) throws MalformattedElementException {
        this.missionCriticalSupport = missionCriticalSupport;
        this.missionCriticalSupport.isValid();
    }

    public void setMmTelSupport(Boolean mmTelSupport) {
        this.mmTelSupport = mmTelSupport;
    }

    public void setNbIoTSupport(Boolean nbIoTSupport) {
        this.nbIoTSupport = nbIoTSupport;
    }

    public void setNfOwned(List<String> nfOwned) {
        this.nfOwned = nfOwned;
    }

    public void setMaxNPDUsess(MaxNPDUsess maxNPDUsess) {
        this.maxNPDUsess = maxNPDUsess;
    }

    public void setMaxNUE(MaxNUE maxNUE) {
        this.maxNUE = maxNUE;
    }

    public void setPerformanceMonitoring(PerformanceMonitoring performanceMonitoring) throws MalformattedElementException {
        this.performanceMonitoring = performanceMonitoring;
        this.performanceMonitoring.isValid();
    }

    public void setPerformancePrediction(PerformancePrediction performancePrediction) throws MalformattedElementException {
        this.performancePrediction = performancePrediction;
        this.performancePrediction.isValid();
    }

    public void setPositioningSupport(PositioningSupport positioningSupport) throws MalformattedElementException {
        this.positioningSupport = positioningSupport;
        this.positioningSupport.isValid();
    }

    public void setRadioSpectrum(RadioSpectrum radioSpectrum) throws MalformattedElementException {
        this.radioSpectrum = radioSpectrum;
        this.radioSpectrum.isValid();
    }

    public void setRootCauseInvestigation(RCIValue rootCauseInvestigation) {
        this.rootCauseInvestigation = rootCauseInvestigation;
    }

    public void setSscSupport(SSCSupportValue sscSupport) {
        this.sscSupport = sscSupport;
    }

    public void setSimultUseNSlice(SUNSliceValue simultUseNSlice) {
        this.simultUseNSlice = simultUseNSlice;
    }

    public void setSliceQoSparams(List<SliceQoSparams> sliceQoSparams) throws MalformattedElementException {
        this.sliceQoSparams = sliceQoSparams;
        for(SliceQoSparams qoSparams: sliceQoSparams)
            qoSparams.isValid();
    }

    public void setSupportNonIpTraffic(Boolean supportNonIpTraffic) {
        this.supportNonIpTraffic = supportNonIpTraffic;
    }

    public void setSupportedDeviceVelocity(int supportedDeviceVelocity) {
        this.supportedDeviceVelocity = supportedDeviceVelocity;
    }

    public void setSynchronicity(Synchronicity synchronicity) throws MalformattedElementException {
        this.synchronicity = synchronicity;
        this.synchronicity.isValid();
    }

    public void setUeDensity(int ueDensity) {
        this.ueDensity = ueDensity;
    }

    public void setUplinkThroughputNS(UplinkThroughputNS uplinkThroughputNS) {
        this.uplinkThroughputNS = uplinkThroughputNS;
    }

    public void setMaxUplinkThroughputUE(int maxUplinkThroughputUE) {
        this.maxUplinkThroughputUE = maxUplinkThroughputUE;
    }

    public void setUserManagementOpeness(Boolean userManagementOpeness) {
        this.userManagementOpeness = userManagementOpeness;
    }

    public void setUserDataAccess(UserDataAccess userDataAccess) throws MalformattedElementException {
        this.userDataAccess = userDataAccess;
        this.userDataAccess.isValid();
    }

    public void setV2xCommunicationMode(V2xCommValue v2xCommunicationMode) {
        this.v2xCommunicationMode = v2xCommunicationMode;
    }

    public void setLatencyApplicationServer(float latencyApplicationServer) {
        this.latencyApplicationServer = latencyApplicationServer;
    }

    public void setNSSAARequired(Boolean nssaaRequired) {
        this.nssaaRequired = nssaaRequired;
    }

    public void setMpsSupport(MultimediaPriorityServiceSupp mpsSupport) {
        this.mpsSupport = mpsSupport;
    }

    public void isValid() throws MalformattedElementException{
    }

    public String getGstId() {
        return gstId;
    }

    public String getGstName() {
        return gstName;
    }

    public String getGstProvider() {
        return gstProvider;
    }

    public String getGstVersion() {
        return gstVersion;
    }

    public float getAvailability() {
        return availability;
    }

    public List<AreaOfService> getAreaOfServices() {
        return areaOfServices;
    }

    public Boolean getDelayTolerance() {
        return delayTolerance;
    }

    public DeterministicCommunication getDeterministicCommunication() {
        return deterministicCommunication;
    }

    public DownlinkThroughputNS getDownlinkThroughputNS() {
        return downlinkThroughputNS;
    }

    public int getMaximumDownlinkThroughputUE() {
        return maximumDownlinkThroughputUE;
    }

    public EnergyEfficiency getEnergyEfficiency() {
        return energyEfficiency;
    }

    public GroupCommSuppValue getGroupCommunicationSupport() {
        return groupCommunicationSupport;
    }

    public Isolation getIsolation() {
        return isolation;
    }

    public int getMaxSuppPacketSize() {
        return maxSuppPacketSize;
    }

    public MissionCriticalSupport getMissionCriticalSupport() {
        return missionCriticalSupport;
    }

    public Boolean getMmTelSupport() {
        return mmTelSupport;
    }

    public Boolean getNbIoTSupport() {
        return nbIoTSupport;
    }

    public List<String> getNfOwned() {
        return nfOwned;
    }

    public MaxNPDUsess getMaxNPDUsess() {
        return maxNPDUsess;
    }

    public MaxNUE getMaxNUE() {
        return maxNUE;
    }

    public PerformanceMonitoring getPerformanceMonitoring() {
        return performanceMonitoring;
    }

    public PerformancePrediction getPerformancePrediction() {
        return performancePrediction;
    }

    public PositioningSupport getPositioningSupport() {
        return positioningSupport;
    }

    public RadioSpectrum getRadioSpectrum() {
        return radioSpectrum;
    }

    public RCIValue getRootCauseInvestigation() {
        return rootCauseInvestigation;
    }

    public SSCSupportValue getSscSupport() {
        return sscSupport;
    }

    public SUNSliceValue getSimultUseNSlice() {
        return simultUseNSlice;
    }

    public List<SliceQoSparams> getSliceQoSparams() {
        return sliceQoSparams;
    }

    public Boolean getSupportNonIpTraffic() {
        return supportNonIpTraffic;
    }

    public int getSupportedDeviceVelocity() {
        return supportedDeviceVelocity;
    }

    public Synchronicity getSynchronicity() {
        return synchronicity;
    }

    public int getUeDensity() {
        return ueDensity;
    }

    public UplinkThroughputNS getUplinkThroughputNS() {
        return uplinkThroughputNS;
    }

    public int getMaxUplinkThroughputUE() {
        return maxUplinkThroughputUE;
    }

    public Boolean getUserManagementOpeness() {
        return userManagementOpeness;
    }

    public UserDataAccess getUserDataAccess() {
        return userDataAccess;
    }

    public V2xCommValue getV2xCommunicationMode() {
        return v2xCommunicationMode;
    }

    public float getLatencyApplicationServer() {
        return latencyApplicationServer;
    }

    public Boolean getNSSAARequired() {
        return nssaaRequired;
    }

    public MultimediaPriorityServiceSupp getMpsSupport() {
        return mpsSupport;
    }
}