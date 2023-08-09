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
package it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.KeyValuePairs;
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.C2COnboardingStateType;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.*;

/**
 * VnfPkgInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-29T11:50:43.237+01:00")

public class VnfPkgInfo {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("vnfdId")
    private UUID vnfdId = null;

    @JsonProperty("vnfProvider")
    private String vnfProvider = null;

    @JsonProperty("vnfProductName")
    private String vnfProductName = null;

    @JsonProperty("vnfSoftwareVersion")
    private String vnfSoftwareVersion = null;

    @JsonProperty("vnfdVersion")
    private String vnfdVersion = null;

    @JsonProperty("checksum")
    private String checksum = null;

    @JsonProperty("softwareImages")
    @Valid
    private List<VnfPackageSoftwareImageInfo> softwareImages = null;

    @JsonProperty("additionalArtifacts")
    @Valid
    private List<VnfPackageArtifactInfo> additionalArtifacts = null;

    @JsonProperty("onboardingState")
    private PackageOnboardingStateType onboardingState = null;

    @JsonProperty("operationalState")
    private PackageOperationalStateType operationalState = null;

    @JsonProperty("usageState")
    private PackageUsageStateType usageState = null;

    @JsonProperty("userDefinedData")
    private KeyValuePairs userDefinedData = null;

    @JsonProperty("_links")
    private VnfPkgLinksType links = null;

    @JsonProperty("manosOnboardingStatus")
    private Map<String, PackageOnboardingStateType> manoIdToOnboardingStatus = new HashMap<>();

    @JsonProperty("c2cOnboardingState")
    private C2COnboardingStateType c2cOnboardingState;

    @JsonProperty("projectId")
    private String projectId;

    @JsonProperty("manoInfoIds")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> manoInfoIds = new HashMap<>();

    @JsonProperty("description")
    private String description = null;

    public VnfPkgInfo id(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")

    @Valid

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public VnfPkgInfo vnfdId(UUID vnfdId) {
        this.vnfdId = vnfdId;
        return this;
    }

    /**
     * Get vnfdId
     *
     * @return vnfdId
     **/
    @ApiModelProperty(value = "")

    @Valid

    public UUID getVnfdId() {
        return vnfdId;
    }

    public void setVnfdId(UUID vnfdId) {
        this.vnfdId = vnfdId;
    }

    public VnfPkgInfo vnfProvider(String vnfProvider) {
        this.vnfProvider = vnfProvider;
        return this;
    }

    /**
     * Get vnfProvider
     *
     * @return vnfProvider
     **/
    @ApiModelProperty(value = "")


    public String getVnfProvider() {
        return vnfProvider;
    }

    public void setVnfProvider(String vnfProvider) {
        this.vnfProvider = vnfProvider;
    }

    public VnfPkgInfo vnfProductName(String vnfProductName) {
        this.vnfProductName = vnfProductName;
        return this;
    }

    /**
     * Get vnfProductName
     *
     * @return vnfProductName
     **/
    @ApiModelProperty(value = "")


    public String getVnfProductName() {
        return vnfProductName;
    }

    public void setVnfProductName(String vnfProductName) {
        this.vnfProductName = vnfProductName;
    }

    public VnfPkgInfo vnfSoftwareVersion(String vnfSoftwareVersion) {
        this.vnfSoftwareVersion = vnfSoftwareVersion;
        return this;
    }

    /**
     * Get vnfSoftwareVersion
     *
     * @return vnfSoftwareVersion
     **/
    @ApiModelProperty(value = "")


    public String getVnfSoftwareVersion() {
        return vnfSoftwareVersion;
    }

    public void setVnfSoftwareVersion(String vnfSoftwareVersion) {
        this.vnfSoftwareVersion = vnfSoftwareVersion;
    }

    public VnfPkgInfo vnfdVersion(String vnfdVersion) {
        this.vnfdVersion = vnfdVersion;
        return this;
    }

    /**
     * Get vnfdVersion
     *
     * @return vnfdVersion
     **/
    @ApiModelProperty(value = "")


    public String getVnfdVersion() {
        return vnfdVersion;
    }

    public void setVnfdVersion(String vnfdVersion) {
        this.vnfdVersion = vnfdVersion;
    }

    public VnfPkgInfo checksum(String checksum) {
        this.checksum = checksum;
        return this;
    }

    /**
     * Get checksum
     *
     * @return checksum
     **/
    @ApiModelProperty(value = "")


    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public VnfPkgInfo softwareImages(List<VnfPackageSoftwareImageInfo> softwareImages) {
        this.softwareImages = softwareImages;
        return this;
    }

    public VnfPkgInfo addSoftwareImagesItem(VnfPackageSoftwareImageInfo softwareImagesItem) {
        if (this.softwareImages == null) {
            this.softwareImages = new ArrayList<VnfPackageSoftwareImageInfo>();
        }
        this.softwareImages.add(softwareImagesItem);
        return this;
    }

    /**
     * Get softwareImages
     *
     * @return softwareImages
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<VnfPackageSoftwareImageInfo> getSoftwareImages() {
        return softwareImages;
    }

    public void setSoftwareImages(List<VnfPackageSoftwareImageInfo> softwareImages) {
        this.softwareImages = softwareImages;
    }

    public VnfPkgInfo additionalArtifacts(List<VnfPackageArtifactInfo> additionalArtifacts) {
        this.additionalArtifacts = additionalArtifacts;
        return this;
    }

    public VnfPkgInfo addAdditionalArtifactsItem(VnfPackageArtifactInfo additionalArtifactsItem) {
        if (this.additionalArtifacts == null) {
            this.additionalArtifacts = new ArrayList<VnfPackageArtifactInfo>();
        }
        this.additionalArtifacts.add(additionalArtifactsItem);
        return this;
    }

    /**
     * Get additionalArtifacts
     *
     * @return additionalArtifacts
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<VnfPackageArtifactInfo> getAdditionalArtifacts() {
        return additionalArtifacts;
    }

    public void setAdditionalArtifacts(List<VnfPackageArtifactInfo> additionalArtifacts) {
        this.additionalArtifacts = additionalArtifacts;
    }

    public VnfPkgInfo onboardingState(PackageOnboardingStateType onboardingState) {
        this.onboardingState = onboardingState;
        return this;
    }

    /**
     * Get onboardingState
     *
     * @return onboardingState
     **/
    @ApiModelProperty(value = "")

    @Valid

    public PackageOnboardingStateType getOnboardingState() {
        return onboardingState;
    }

    public void setOnboardingState(PackageOnboardingStateType onboardingState) {
        this.onboardingState = onboardingState;
    }

    public VnfPkgInfo operationalState(PackageOperationalStateType operationalState) {
        this.operationalState = operationalState;
        return this;
    }

    /**
     * Get operationalState
     *
     * @return operationalState
     **/
    @ApiModelProperty(value = "")

    @Valid

    public PackageOperationalStateType getOperationalState() {
        return operationalState;
    }

    public void setOperationalState(PackageOperationalStateType operationalState) {
        this.operationalState = operationalState;
    }

    public VnfPkgInfo usageState(PackageUsageStateType usageState) {
        this.usageState = usageState;
        return this;
    }

    /**
     * Get usageState
     *
     * @return usageState
     **/
    @ApiModelProperty(value = "")

    @Valid

    public PackageUsageStateType getUsageState() {
        return usageState;
    }

    public void setUsageState(PackageUsageStateType usageState) {
        this.usageState = usageState;
    }

    public VnfPkgInfo userDefinedData(KeyValuePairs userDefinedData) {
        this.userDefinedData = userDefinedData;
        return this;
    }

    /**
     * Get userDefinedData
     *
     * @return userDefinedData
     **/
    @ApiModelProperty(value = "")

    @Valid

    public KeyValuePairs getUserDefinedData() {
        return userDefinedData;
    }

    public void setUserDefinedData(KeyValuePairs userDefinedData) {
        this.userDefinedData = userDefinedData;
    }

    public VnfPkgInfo links(VnfPkgLinksType links) {
        this.links = links;
        return this;
    }

    /**
     * Get links
     *
     * @return links
     **/
    @ApiModelProperty(value = "")

    @Valid

    public VnfPkgLinksType getLinks() {
        return links;
    }

    public void setLinks(VnfPkgLinksType links) {
        this.links = links;
    }

    public Map<String, PackageOnboardingStateType> getManoIdToOnboardingStatus() {
        return manoIdToOnboardingStatus;
    }

    public void setManoIdToOnboardingStatus(Map<String, PackageOnboardingStateType> manoIdToOnboardingStatus) {
        this.manoIdToOnboardingStatus = manoIdToOnboardingStatus;
    }

    public C2COnboardingStateType getC2cOnboardingState() {
        return c2cOnboardingState;
    }

    public void setC2cOnboardingState(C2COnboardingStateType c2cOnboardingState) {
        this.c2cOnboardingState = c2cOnboardingState;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Map<String, String> getManoInfoIds() {
        return manoInfoIds;
    }

    public void setManoInfoIds(Map<String, String> manoInfoIds) {
        this.manoInfoIds = manoInfoIds;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VnfPkgInfo vnfPkgInfo = (VnfPkgInfo) o;
        return Objects.equals(this.id, vnfPkgInfo.id) &&
                Objects.equals(this.vnfdId, vnfPkgInfo.vnfdId) &&
                Objects.equals(this.vnfProvider, vnfPkgInfo.vnfProvider) &&
                Objects.equals(this.vnfProductName, vnfPkgInfo.vnfProductName) &&
                Objects.equals(this.vnfSoftwareVersion, vnfPkgInfo.vnfSoftwareVersion) &&
                Objects.equals(this.vnfdVersion, vnfPkgInfo.vnfdVersion) &&
                Objects.equals(this.checksum, vnfPkgInfo.checksum) &&
                Objects.equals(this.softwareImages, vnfPkgInfo.softwareImages) &&
                Objects.equals(this.additionalArtifacts, vnfPkgInfo.additionalArtifacts) &&
                Objects.equals(this.onboardingState, vnfPkgInfo.onboardingState) &&
                Objects.equals(this.operationalState, vnfPkgInfo.operationalState) &&
                Objects.equals(this.usageState, vnfPkgInfo.usageState) &&
                Objects.equals(this.userDefinedData, vnfPkgInfo.userDefinedData) &&
                Objects.equals(this.links, vnfPkgInfo.links)
                && Objects.equals(this.manoInfoIds, vnfPkgInfo.manoInfoIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vnfdId, vnfProvider, vnfProductName, vnfSoftwareVersion, vnfdVersion, checksum, softwareImages, additionalArtifacts, onboardingState, operationalState, usageState, userDefinedData, links, manoInfoIds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VnfPkgInfo {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
        sb.append("    vnfProvider: ").append(toIndentedString(vnfProvider)).append("\n");
        sb.append("    vnfProductName: ").append(toIndentedString(vnfProductName)).append("\n");
        sb.append("    vnfSoftwareVersion: ").append(toIndentedString(vnfSoftwareVersion)).append("\n");
        sb.append("    vnfdVersion: ").append(toIndentedString(vnfdVersion)).append("\n");
        sb.append("    checksum: ").append(toIndentedString(checksum)).append("\n");
        sb.append("    softwareImages: ").append(toIndentedString(softwareImages)).append("\n");
        sb.append("    additionalArtifacts: ").append(toIndentedString(additionalArtifacts)).append("\n");
        sb.append("    onboardingState: ").append(toIndentedString(onboardingState)).append("\n");
        sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
        sb.append("    usageState: ").append(toIndentedString(usageState)).append("\n");
        sb.append("    userDefinedData: ").append(toIndentedString(userDefinedData)).append("\n");
        sb.append("    links: ").append(toIndentedString(links)).append("\n");
        sb.append("    manoInfoIds: ").append(toIndentedString(manoInfoIds)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

