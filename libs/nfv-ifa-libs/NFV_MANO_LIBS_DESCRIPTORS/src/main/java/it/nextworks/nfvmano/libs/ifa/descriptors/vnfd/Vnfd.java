/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.libs.ifa.descriptors.vnfd;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.LcmEventType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.LifeCycleManagementScript;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.Rule;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualComputeDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualStorageDesc;
import it.nextworks.nfvmano.libs.ifa.descriptors.onboardedvnfpackage.OnboardedVnfPkgInfo;


/**
 * Class that models a VNFD
 * <p>
 * REF. IFA-011 v2.3.1 - section 7.1.2
 *
 * @author nextworks
 */
@Entity
public class Vnfd implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinColumn(name = "onboarded_vnf_pkg_info_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OnboardedVnfPkgInfo onboardedVnfPkgInfo;

    private String vnfdId;
    private String vnfProvider;
    private String vnfProductName;
    private String vnfSoftwareVersion;
    private String vnfdVersion;
    private String vnfProductInfoName;
    private String vnfProductInfoDescription;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> vnfmInfo = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> localizationLanguage = new ArrayList<>();

    private String defaultLocalizationLanguage;

    @OneToMany(mappedBy = "vnfd", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Vdu> vdu = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "vnfd", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VirtualComputeDesc> virtualComputeDesc = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<VirtualStorageDesc> virtualStorageDesc = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "vnfd", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VnfVirtualLinkDesc> intVirtualLinkDesc = new ArrayList<>();

    @OneToMany(mappedBy = "vnfd", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VnfExtCpd> vnfExtCpd = new ArrayList<>();

    @OneToMany(mappedBy = "vnfd", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VnfDf> deploymentFlavour = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfd", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfConfigurableProperties configurableProperties;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfd", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfInfoModifiableAttributes modifiableAttributes;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "vnfd", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LifeCycleManagementScript> lifeCycleManagementScript = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "vnfd", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VnfdElementGroup> elementGroup = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<VnfIndicator> vnfIndicator = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "vnfd", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Rule> autoScale = new ArrayList<>();

    public Vnfd() {
        //JPA only
    }

    public Vnfd(OnboardedVnfPkgInfo onboardedVnfPkgInfo,
                String vnfdId,
                String vnfProvider,
                String vnfProductName,
                String vnfSoftwareVersion,
                String vnfdVersion,
                String vnfProductInfoName,
                String vnfProductInfoDescription,
                List<String> vnfmInfo,
                List<String> localizationLanguage,
                String defaultLocalizationLanguage,
                List<VirtualStorageDesc> virtualStorageDesc,
                List<VnfIndicator> vnfIndicator) {
        this.onboardedVnfPkgInfo = onboardedVnfPkgInfo;
        this.vnfdId = vnfdId;
        this.vnfProvider = vnfProvider;
        this.vnfProductName = vnfProductName;
        this.vnfSoftwareVersion = vnfSoftwareVersion;
        this.vnfdVersion = vnfdVersion;
        this.vnfProductInfoName = vnfProductInfoName;
        this.vnfProductInfoDescription = vnfProductInfoDescription;
        if (vnfmInfo != null) this.vnfmInfo = vnfmInfo;
        if (localizationLanguage != null) this.localizationLanguage = localizationLanguage;
        this.defaultLocalizationLanguage = defaultLocalizationLanguage;
        if (virtualStorageDesc != null) this.virtualStorageDesc = virtualStorageDesc;
        if (vnfIndicator != null) this.vnfIndicator = vnfIndicator;
    }


    /**
     * @return the id
     */
    @JsonIgnore
    public Long getId() {
        return id;
    }

    /**
     * @return the vnfdId
     */
    @JsonProperty("vnfdId")
    public String getVnfdId() {
        return vnfdId;
    }

    /**
     * @return the vnfProvider
     */
    @JsonProperty("vnfProvider")
    public String getVnfProvider() {
        return vnfProvider;
    }

    /**
     * @return the vnfProductName
     */
    @JsonProperty("vnfProductName")
    public String getVnfProductName() {
        return vnfProductName;
    }

    /**
     * @return the vnfSoftwareVersion
     */
    @JsonProperty("vnfSoftwareVersion")
    public String getVnfSoftwareVersion() {
        return vnfSoftwareVersion;
    }

    /**
     * @return the vnfdVersion
     */
    @JsonProperty("vnfdVersion")
    public String getVnfdVersion() {
        return vnfdVersion;
    }

    /**
     * @return the vnfProductInfoName
     */
    @JsonProperty("vnfProductInfoName")
    public String getVnfProductInfoName() {
        return vnfProductInfoName;
    }

    /**
     * @return the vnfProductInfoDescription
     */
    @JsonProperty("vnfProductInfoDescription")
    public String getVnfProductInfoDescription() {
        return vnfProductInfoDescription;
    }

    /**
     * @return the vnfmInfo
     */
    @JsonProperty("vnfmInfo")
    public List<String> getVnfmInfo() {
        return vnfmInfo;
    }

    /**
     * @return the localizationLanguage
     */
    @JsonProperty("localizationLanguage")
    public List<String> getLocalizationLanguage() {
        return localizationLanguage;
    }

    /**
     * @return the defaultLocalizationLanguage
     */
    @JsonProperty("defaultLocalizationLanguage")
    public String getDefaultLocalizationLanguage() {
        return defaultLocalizationLanguage;
    }

    /**
     * @return the vdu
     */
    @JsonProperty("vdu")
    public List<Vdu> getVdu() {
        return vdu;
    }

    /**
     * @return the virtualComputeDesc
     */
    @JsonProperty("virtualComputeDesc")
    public List<VirtualComputeDesc> getVirtualComputeDesc() {
        return virtualComputeDesc;
    }

    /**
     * @return the virtualStorageDesc
     */
    @JsonProperty("virtualStorageDesc")
    public List<VirtualStorageDesc> getVirtualStorageDesc() {
        return virtualStorageDesc;
    }

    /**
     * @return the intVirtualLinkDesc
     */
    @JsonProperty("intVirtualLinkDesc")
    public List<VnfVirtualLinkDesc> getIntVirtualLinkDesc() {
        return intVirtualLinkDesc;
    }

    /**
     * @return the vnfExtCpd
     */
    @JsonProperty("vnfExtCpd")
    public List<VnfExtCpd> getVnfExtCpd() {
        return vnfExtCpd;
    }

    /**
     * @return the deploymentFlavour
     */
    @JsonProperty("deploymentFlavour")
    public List<VnfDf> getDeploymentFlavour() {
        return deploymentFlavour;
    }

    /**
     * @return the configurableProperties
     */
    @JsonProperty("configurableProperties")
    public VnfConfigurableProperties getConfigurableProperties() {
        return configurableProperties;
    }

    /**
     * @return the modifiableAttributes
     */
    @JsonProperty("modifiableAttributes")
    public VnfInfoModifiableAttributes getModifiableAttributes() {
        return modifiableAttributes;
    }

    /**
     * @return the lifeCycleManagementScript
     */
    @JsonProperty("lifeCycleManagementScript")
    public List<LifeCycleManagementScript> getLifeCycleManagementScript() {
        return lifeCycleManagementScript;
    }

    /**
     * @return the elementGroup
     */
    @JsonProperty("elementGroup")
    public List<VnfdElementGroup> getElementGroup() {
        return elementGroup;
    }

    /**
     * @return the vnfIndicator
     */
    @JsonProperty("vnfIndicator")
    public List<VnfIndicator> getVnfIndicator() {
        return vnfIndicator;
    }

    /**
     * @return the autoScale
     */
    @JsonProperty("autoscale")
    public List<Rule> getAutoScale() {
        return autoScale;
    }
    
    @JsonIgnore
    public VnfDf getVnfDf(String dfId) throws NotExistingEntityException {
    	for (VnfDf df : deploymentFlavour) {
    		if (df.getFlavourId().equals(dfId)) return df;
    	}
    	throw new NotExistingEntityException("Deployment flavour with ID " + dfId + " not found");
    }
    
    @JsonIgnore
    public Vdu getVduFromId(String vduId) throws NotExistingEntityException {
    	for (Vdu v : vdu) {
    		if (v.getVduId().equals(vduId)) return v;
    	}
    	throw new NotExistingEntityException("VDU with ID " + vduId + " not found");
    }
    
    @JsonIgnore
    public VirtualComputeDesc getVirtualComputeDescriptorFromId(String vcdId) throws NotExistingEntityException {
    	for (VirtualComputeDesc vcd : virtualComputeDesc) {
    		if (vcd.getVirtualComputeDescId().equals(vcdId)) return vcd;
    	}
    	throw new NotExistingEntityException("Virtual compute descriptor with ID " + vcdId + " not found");
    }
    
    @JsonIgnore
    public VirtualStorageDesc getVirtualStorageDescriptorFromId(String vsdId) throws NotExistingEntityException {
    	for (VirtualStorageDesc vsd : virtualStorageDesc) {
    		if (vsd.getStorageId().equals(vsdId)) return vsd;
    	}
    	throw new NotExistingEntityException("Virtual storage description with ID " + vsdId + " not found");
    }
    
    @JsonIgnore
    public VnfExtCpd getExternalConnectionPointAssociatedToInternalConnectionPoint(String intCpId) throws NotExistingEntityException {
    	for (VnfExtCpd extCp : vnfExtCpd) {
    		if (extCp.getIntCpd().equals(intCpId)) return extCp;
    	}
    	throw new NotExistingEntityException("External connection point associated to internal cp " + intCpId + " not found");
    }
    
    @JsonIgnore
    public VnfExtCpd getExternalConnectionPointFromId(String extCpdId) throws NotExistingEntityException {
    	for (VnfExtCpd extCp : vnfExtCpd) {
    		if (extCp.getCpdId().equals(extCpdId)) return extCp;
    	}
    	throw new NotExistingEntityException("External connection point with Id " + extCpdId + " not found");
    }
    
    @JsonIgnore
    public List<LifeCycleManagementScript> getLcmScriptForEvent(LcmEventType eventType) {
    	List<LifeCycleManagementScript> result = new ArrayList<>();
    	for (LifeCycleManagementScript s : lifeCycleManagementScript) {
    		if (s.includeEvent(eventType)) result.add(s);
    	}
    	return result;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (vnfdId == null) throw new MalformattedElementException("VNFD without VNFD ID");
        if (vnfProvider == null) throw new MalformattedElementException("VNFD without VNF provider");
        if (vnfProductName == null) throw new MalformattedElementException("VNFD without VNF product name");
        if (vnfSoftwareVersion == null) throw new MalformattedElementException("VNFD without VNF sw version");
        if (vnfdVersion == null) throw new MalformattedElementException("VNFD without VNFD version");
        if ((vnfmInfo == null) || (vnfmInfo.isEmpty()))
            throw new MalformattedElementException("VNFD without VNFM info");
        if ((vdu == null) || (vdu.isEmpty())) {
            throw new MalformattedElementException("VNFD without VDUs");
        } else {
            for (Vdu v : vdu) v.isValid();
        }
        for (VirtualComputeDesc v : virtualComputeDesc) v.isValid();
        for (VirtualStorageDesc v : virtualStorageDesc) v.isValid();
        for (VnfVirtualLinkDesc v : intVirtualLinkDesc) v.isValid();
        if ((vnfExtCpd == null) || (vnfExtCpd.isEmpty())) {
            throw new MalformattedElementException("VNFD without external connection points");
        } else {
            for (VnfExtCpd v : vnfExtCpd) v.isValid();
        }
        if ((deploymentFlavour == null) || (deploymentFlavour.isEmpty())) {
            throw new MalformattedElementException("VNFD without deployment flavours");
        } else {
            for (VnfDf v : deploymentFlavour) v.isValid();
        }
        if (configurableProperties != null) configurableProperties.isValid();
        if (modifiableAttributes == null) {
            throw new MalformattedElementException("VNFD without modifiable attributes");
        } else modifiableAttributes.isValid();
        for (LifeCycleManagementScript s : lifeCycleManagementScript) s.isValid();
        for (VnfdElementGroup v : elementGroup) v.isValid();
        for (VnfIndicator i : vnfIndicator) i.isValid();
        for (Rule r : autoScale) r.isValid();
    }

}
