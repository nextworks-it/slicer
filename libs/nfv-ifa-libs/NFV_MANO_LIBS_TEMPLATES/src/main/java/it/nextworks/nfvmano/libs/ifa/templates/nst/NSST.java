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
public class NSST {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String nsstId;
    private String nsstName;
    private String nsstVersion;
    private String nsstProvider;
    //it cannot be mapped from GSMA GST
    private boolean operationalState=false; //"ENABLED"=true, "DISABLED"=false
    //it cannot be mapped from GSMA GST
    @Enumerated(EnumType.STRING)
    private AdministrativeStateValue administrativeState;

    //it cannot be mapped from GSMA GST
    @OneToOne(cascade = {CascadeType.ALL})
    private NsInfo nsInfo;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SliceProfile> sliceProfileList;

    //it cannot be mapped from GSMA GST
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<NSST> nsstList;

    //This attribute does not belong to the 3GPP standard
    @OneToOne(cascade = {CascadeType.ALL})
    private NsdInfo nsdInfo;

    @Enumerated(EnumType.STRING)
    private SliceSubnetType type;

    public NSST(){}

    /**
     *
     * @param nsstId the Network Slice Subnet Template id
     * @param operationalState Operational state value (default set to false)
     * @param administrativeState Administrative state value
     * @param nsInfo NsInfo of the NS instance corresponding to the NSSI
     * @param sliceProfileList A list of Slice profile
     * @param nsstList a network slice subnet template reference
     * @param nsdInfo network slice descriptor information
     */
    public NSST(String nsstId, boolean operationalState, AdministrativeStateValue administrativeState,
                NsInfo nsInfo, List<SliceProfile> sliceProfileList, List<NSST> nsstList, NsdInfo nsdInfo, SliceSubnetType type){
        this.nsstId=nsstId;
        this.operationalState=operationalState;
        this.administrativeState=administrativeState;
        this.nsInfo=nsInfo;
        this.sliceProfileList=sliceProfileList;
        this.nsstList = nsstList;
        this.nsdInfo=nsdInfo;
        this.type=type;
    }

    public NSST(NSST nsst){
        this.nsstId=nsst.nsstId;
        this.operationalState=nsst.operationalState;
        this.administrativeState=nsst.administrativeState;
        this.nsInfo=nsst.nsInfo;
        this.sliceProfileList=nsst.sliceProfileList;
        this.nsstList = nsst.nsstList;
        this.nsdInfo=nsst.nsdInfo;
        this.type=nsst.type;
    }

    public void setNsstId(String nsstId) {
        this.nsstId = nsstId;
    }

    public String getNsstId() {
        return nsstId;
    }

    public void setOperationalState(boolean operationalState) {
        this.operationalState = operationalState;
    }

    public boolean isOperationalState() {
        return operationalState;
    }

    public void setAdministrativeState(AdministrativeStateValue administrativeState) {
        this.administrativeState = administrativeState;
    }

    public AdministrativeStateValue getAdministrativeState() {
        return administrativeState;
    }

    public void setNsInfo(NsInfo nsInfo) {
        this.nsInfo = nsInfo;
    }

    public NsInfo getNsInfo() {
        return nsInfo;
    }

    public void setSliceProfileList(List<SliceProfile> sliceProfileList) {
        this.sliceProfileList = sliceProfileList;
    }

    public List<SliceProfile> getSliceProfileList() {
        return sliceProfileList;
    }

    public void setNsstList(List<NSST> nsstList) {
        this.nsstList = nsstList;
    }

    public List<NSST> getNsstList() {
        return nsstList;
    }

    public void setNsdInfo(NsdInfo nsdInfo) {
        this.nsdInfo = nsdInfo;
    }

    public NsdInfo getNsdInfo() {
        return nsdInfo;
    }

    public void isValid() throws MalformattedElementException{
        if(nsstId==null)
            throw new MalformattedElementException("Nsst ID is not set");
        if(nsstName==null)
            throw new MalformattedElementException("Nsst name is not set");
        if(nsstVersion==null)
            throw new MalformattedElementException("Nsst version is not set");
        if(nsstProvider==null)
            throw new MalformattedElementException("Nsst provider is not set");
    }

    public String getNsstName() {
        return nsstName;
    }

    public void setNsstName(String nsstName) {
        this.nsstName = nsstName;
    }

    public String getNsstVersion() {
        return nsstVersion;
    }

    public void setNsstVersion(String nsstVersion) {
        this.nsstVersion = nsstVersion;
    }

    public String getNsstProvider() {
        return nsstProvider;
    }

    public void setNsstProvider(String nsstProvider) {
        this.nsstProvider = nsstProvider;
    }

    public SliceSubnetType getType() {
        return type;
    }

    public void setType(SliceSubnetType type) {
        this.type = type;
    }
}
