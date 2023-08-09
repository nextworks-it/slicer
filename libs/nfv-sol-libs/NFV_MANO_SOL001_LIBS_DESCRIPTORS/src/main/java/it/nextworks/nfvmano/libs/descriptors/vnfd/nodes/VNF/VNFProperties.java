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
package it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.VnfConfigurableProperties;
import it.nextworks.nfvmano.libs.descriptors.elements.VnfInfoModifiableAttributes;
import it.nextworks.nfvmano.libs.descriptors.elements.VnfMonitoringParameter;
import it.nextworks.nfvmano.libs.descriptors.elements.VnfProfile;
import it.nextworks.nfvmano.libs.descriptors.elements.lcm.VnfLcmOperationsConfiguration;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class VNFProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VNFNode vnfNode;

    private String descriptorId;
    private String descriptorVersion;
    private String provider;
    private String productName;
    private String softwareVersion;
    private String productInfoName;
    private String productInfoDescription;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> vnfmInfo = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> localizationLanguages = new ArrayList<>();

    private String defaultLocalizationLanguage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfProperties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfConfigurableProperties configurableProperties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfProperties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfInfoModifiableAttributes modifiableAttributes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfProperties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfLcmOperationsConfiguration lcmOperationsConfiguration;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<VnfMonitoringParameter> monitoringParameters = new ArrayList<>();
    private String flavourId;
    private String flavourDescription;

    @Embedded
    private VnfProfile vnfProfile;

    public VNFProperties() {

    }

    public VNFProperties(String descriptorId, String descriptorVersion, String provider, String productName, String softwareVersion, String productInfoName, String productInfoDescription, List<String> vnfmInfo, List<String> localizationLanguages, String defaultLocalizationLanguage, VnfConfigurableProperties configurableProperties, VnfInfoModifiableAttributes modifiableAttributes, VnfLcmOperationsConfiguration lcmOperationsConfiguration, List<VnfMonitoringParameter> monitoringParameters, String flavourId, String flavourDescription, VnfProfile vnfProfile) {
        this.descriptorId = descriptorId;
        this.descriptorVersion = descriptorVersion;
        this.provider = provider;
        this.productName = productName;
        this.softwareVersion = softwareVersion;
        this.productInfoName = productInfoName;
        this.productInfoDescription = productInfoDescription;
        this.vnfmInfo = vnfmInfo;
        this.localizationLanguages = localizationLanguages;
        this.defaultLocalizationLanguage = defaultLocalizationLanguage;
        this.configurableProperties = configurableProperties;
        this.modifiableAttributes = modifiableAttributes;
        this.lcmOperationsConfiguration = lcmOperationsConfiguration;
        this.monitoringParameters = monitoringParameters;
        this.flavourId = flavourId;
        this.flavourDescription = flavourDescription;
        this.vnfProfile = vnfProfile;
    }

    public VNFProperties(VNFNode vnfNode, String descriptorId, String descriptorVersion, String provider, String productName, String softwareVersion, String productInfoName, String productInfoDescription, List<String> vnfmInfo, List<String> localizationLanguages, String defaultLocalizationLanguage, VnfConfigurableProperties configurableProperties, VnfInfoModifiableAttributes modifiableAttributes, VnfLcmOperationsConfiguration lcmOperationsConfiguration, List<VnfMonitoringParameter> monitoringParameters, String flavourId, String flavourDescription, VnfProfile vnfProfile) {
        this.vnfNode = vnfNode;
        this.descriptorId = descriptorId;
        this.descriptorVersion = descriptorVersion;
        this.provider = provider;
        this.productName = productName;
        this.softwareVersion = softwareVersion;
        this.productInfoName = productInfoName;
        this.productInfoDescription = productInfoDescription;
        this.vnfmInfo = vnfmInfo;
        this.localizationLanguages = localizationLanguages;
        this.defaultLocalizationLanguage = defaultLocalizationLanguage;
        this.configurableProperties = configurableProperties;
        this.modifiableAttributes = modifiableAttributes;
        this.lcmOperationsConfiguration = lcmOperationsConfiguration;
        this.monitoringParameters = monitoringParameters;
        this.flavourId = flavourId;
        this.flavourDescription = flavourDescription;
        this.vnfProfile = vnfProfile;
    }

    public Long getId() {
        return id;
    }

    public VNFNode getVnfNode() {
        return vnfNode;
    }

    @JsonProperty("descriptorId")
    public String getDescriptorId() {
        return descriptorId;
    }

    @JsonProperty("descriptorVersion")
    public String getDescriptorVersion() {
        return descriptorVersion;
    }

    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    @JsonProperty("productName")
    public String getProductName() {
        return productName;
    }

    @JsonProperty("softwareVersion")
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    @JsonProperty("productInfoName")
    public String getProductInfoName() {
        return productInfoName;
    }

    @JsonProperty("productInfoDescription")
    public String getProductInfoDescription() {
        return productInfoDescription;
    }

    @JsonProperty("vnfmInfo")
    public List<String> getVnfmInfo() {
        return vnfmInfo;
    }

    @JsonProperty("localizationLanguages")
    public List<String> getLocalizationLanguages() {
        return localizationLanguages;
    }

    @JsonProperty("defaultLocalizationLanguage")
    public String getDefaultLocalizationLanguage() {
        return defaultLocalizationLanguage;
    }

    @JsonProperty("configurableProperties")
    public VnfConfigurableProperties getConfigurableProperties() {
        return configurableProperties;
    }

    @JsonProperty("modifiableAttributes")
    public VnfInfoModifiableAttributes getModifiableAttributes() {
        return modifiableAttributes;
    }

    @JsonProperty("flavourId")
    public String getFlavourId() {
        return flavourId;
    }

    @JsonProperty("flavourDescription")
    public String getFlavourDescription() {
        return flavourDescription;
    }

    @JsonProperty("lcmOperationsConfiguration")
    public VnfLcmOperationsConfiguration getLcmOperationsConfiguration() {
        return lcmOperationsConfiguration;
    }

    @JsonProperty("monitoringParameters")
    public List<VnfMonitoringParameter> getMonitoringParameters() {
        return monitoringParameters;
    }

    @JsonProperty("vnfProfile")
    public VnfProfile getVnfProfile() {
        return vnfProfile;
    }

    public void setDescriptorId(String descriptorId) {
        this.descriptorId = descriptorId;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.descriptorId == null)
            throw new MalformattedElementException("VNF Node Properties without descriptorId");
        if (this.descriptorVersion == null)
            throw new MalformattedElementException("VNF Node Properties without desriptorVersion");
        if (this.provider == null)
            throw new MalformattedElementException("VNF Node Properties without provider");
        if (this.productName == null)
            throw new MalformattedElementException("VNF Node Properties without productName");
        if (this.softwareVersion == null)
            throw new MalformattedElementException("VNF Node Properties without softwareVersion");
        if (this.productInfoName == null)
            throw new MalformattedElementException("VNF Node Properties without productInfoName");
        if (this.vnfmInfo == null || this.vnfmInfo.isEmpty())
            throw new MalformattedElementException("VNF Node Properties without vnfmInfo");
        if (this.flavourId == null)
            throw new MalformattedElementException("VNF Node Properties without flavourId");
        if (this.flavourDescription == null)
            throw new MalformattedElementException("VNF Node Properties without flavourDescriptor");

        if (configurableProperties != null)
            configurableProperties.isValid();
        if (modifiableAttributes != null)
            modifiableAttributes.isValid();
    }

    public void setDescriptorVersion(String version) {
        this.descriptorVersion = version;
    }

    public void setProvider(String designer) {
        this.provider = designer;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
