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

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;


/**
 * Network Service Template definition
 */
@Entity
public class NST {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
    private String nstId;
    private String nstName;
    private String nstVersion;
    private String nstProvider;

    //it cannot be mapped from GSMA GST
    private boolean operationalState=false; //"ENABLED"=true, "DISABLED"=false
    @Enumerated(EnumType.STRING)
    //it cannot be mapped from GSMA GST
    private AdministrativeStateValue administrativeState;

    /* This attribute is not present into the  @3gpp-TS28.541 v.16.06.2
    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GeographicalAreaInfo> geographicalAreaInfoList;*/

    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<NstServiceProfile> nstServiceProfileList;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "nsst_id", referencedColumnName = "id")
    private NSST nsst;

    public NST(){}

    /**
     * Constructor
     * 
     * @param nstId ID of the NST
     * @param nstName name of the NST
     * @param nstVersion version of the NST
     * @param nstProvider provider of the NST
     * @param nstServiceProfileList service profile associated to the NST
     */
    public NST(String nstId, String nstName, String nstVersion, String nstProvider, AdministrativeStateValue administrativeState,
               List<NstServiceProfile> nstServiceProfileList, NSST nsst) {
		this.nstId = nstId;
		this.nstName = nstName;
		this.nstVersion = nstVersion;
		this.nstProvider = nstProvider;
		this.administrativeState=administrativeState;
		this.nstServiceProfileList = nstServiceProfileList;
		this.nsst=nsst;
	}

    public NST(NST nst) {
        this.nstId = nst.nstId;
        this.nstName = nst.nstName;
        this.nstVersion = nst.nstVersion;
        this.nstProvider = nst.nstProvider;
        this.administrativeState=nst.administrativeState;
        this.nstServiceProfileList = nst.nstServiceProfileList;
        this.nsst=nst.nsst;
    }

	public String getNstId() {
        return nstId;
    }

    public void setNstId(String nstId) {
        this.nstId = nstId;
    }

    public Long getId(){
    			return id;
    }
    	
    public String getNstVersion() {
        return nstVersion;
    }

    public void setNstVersion(String nstVersion) {
        this.nstVersion = nstVersion;
    }

    public String getNstProvider() {
        return nstProvider;
    }

    public void setNstProvider(String nstProvider) {
        this.nstProvider = nstProvider;
    }

    public List<NstServiceProfile> getNstServiceProfileList() {
        return nstServiceProfileList;
    }

    public void setNstServiceProfileList(List<NstServiceProfile> nstServiceProfileList) {
        this.nstServiceProfileList = nstServiceProfileList;
    }

    public void isValid()  throws MalformattedElementException {
        if(this.nstId == null) throw new MalformattedElementException("NST id not set");
        if(this.nstVersion == null) throw new MalformattedElementException("NST version not set");
        if(this.nstProvider == null) throw new MalformattedElementException("NST provider not set");

        //if (this.nsdId == null) throw new MalformattedElementException("NFV NSD ID associated to NST not set");
        //if (this.nsdVersion == null) throw new MalformattedElementException("NFV NSD version associated to NST not set");
    }

	public String getNstName() {
		return nstName;
	}

    /*public List<GeographicalAreaInfo> getGeographicalAreaInfoList() {
        return geographicalAreaInfoList;
    }

    public void setGeographicalAreaInfoList(List<GeographicalAreaInfo> geographicalAreaInfoList) {
        this.geographicalAreaInfoList = geographicalAreaInfoList;
    }*/

    public void setNstName(String nstName) {
        this.nstName = nstName;
    }

    public NSST getNsst() {
        return nsst;
    }

    public void setNsst(NSST nsst) {
        this.nsst = nsst;
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
}
