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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * PkgmNotificationsFilter
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-05T11:50:31.473+02:00")

public class PkgmNotificationsFilter {
    @JsonProperty("notificationTypes")
    private NotificationType notificationTypes = null;

    @JsonProperty("vnfProductsFromProvider")
    @Valid
    private List<String> vnfProductsFromProvider = null;

    @JsonProperty("vnfprovider")
    private String vnfprovider = null;

    @JsonProperty("vnfProducts")
    @Valid
    private List<String> vnfProducts = null;

    @JsonProperty("vfnProductName")
    private String vfnProductName = null;

    @JsonProperty("versions")
    @Valid
    private List<String> versions = null;

    @JsonProperty("vnfSoftwareVersion")
    private String vnfSoftwareVersion = null;

    @JsonProperty("vnfdVersions")
    @Valid
    private List<String> vnfdVersions = null;

    @JsonProperty("vnfdId")
    private UUID vnfdId = null;

    @JsonProperty("vnfpkgId")
    private UUID vnfpkgId = null;

    @JsonProperty("operationalState")
    private PackageOperationalStateType operationalState = null;

    @JsonProperty("usageState")
    private PackageUsageStateType usageState = null;

    public PkgmNotificationsFilter notificationTypes(NotificationType notificationTypes) {
        this.notificationTypes = notificationTypes;
        return this;
    }

    /**
     * Get notificationTypes
     *
     * @return notificationTypes
     **/
    @ApiModelProperty(value = "")

    @Valid

    public NotificationType getNotificationTypes() {
        return notificationTypes;
    }

    public void setNotificationTypes(NotificationType notificationTypes) {
        this.notificationTypes = notificationTypes;
    }

    public PkgmNotificationsFilter vnfProductsFromProvider(List<String> vnfProductsFromProvider) {
        this.vnfProductsFromProvider = vnfProductsFromProvider;
        return this;
    }

    public PkgmNotificationsFilter addVnfProductsFromProviderItem(String vnfProductsFromProviderItem) {
        if (this.vnfProductsFromProvider == null) {
            this.vnfProductsFromProvider = new ArrayList<String>();
        }
        this.vnfProductsFromProvider.add(vnfProductsFromProviderItem);
        return this;
    }

    /**
     * Get vnfProductsFromProvider
     *
     * @return vnfProductsFromProvider
     **/
    @ApiModelProperty(value = "")


    public List<String> getVnfProductsFromProvider() {
        return vnfProductsFromProvider;
    }

    public void setVnfProductsFromProvider(List<String> vnfProductsFromProvider) {
        this.vnfProductsFromProvider = vnfProductsFromProvider;
    }

    public PkgmNotificationsFilter vnfprovider(String vnfprovider) {
        this.vnfprovider = vnfprovider;
        return this;
    }

    /**
     * Get vnfprovider
     *
     * @return vnfprovider
     **/
    @ApiModelProperty(value = "")


    public String getVnfprovider() {
        return vnfprovider;
    }

    public void setVnfprovider(String vnfprovider) {
        this.vnfprovider = vnfprovider;
    }

    public PkgmNotificationsFilter vnfProducts(List<String> vnfProducts) {
        this.vnfProducts = vnfProducts;
        return this;
    }

    public PkgmNotificationsFilter addVnfProductsItem(String vnfProductsItem) {
        if (this.vnfProducts == null) {
            this.vnfProducts = new ArrayList<String>();
        }
        this.vnfProducts.add(vnfProductsItem);
        return this;
    }

    /**
     * Get vnfProducts
     *
     * @return vnfProducts
     **/
    @ApiModelProperty(value = "")


    public List<String> getVnfProducts() {
        return vnfProducts;
    }

    public void setVnfProducts(List<String> vnfProducts) {
        this.vnfProducts = vnfProducts;
    }

    public PkgmNotificationsFilter vfnProductName(String vfnProductName) {
        this.vfnProductName = vfnProductName;
        return this;
    }

    /**
     * Get vfnProductName
     *
     * @return vfnProductName
     **/
    @ApiModelProperty(value = "")


    public String getVfnProductName() {
        return vfnProductName;
    }

    public void setVfnProductName(String vfnProductName) {
        this.vfnProductName = vfnProductName;
    }

    public PkgmNotificationsFilter versions(List<String> versions) {
        this.versions = versions;
        return this;
    }

    public PkgmNotificationsFilter addVersionsItem(String versionsItem) {
        if (this.versions == null) {
            this.versions = new ArrayList<String>();
        }
        this.versions.add(versionsItem);
        return this;
    }

    /**
     * Get versions
     *
     * @return versions
     **/
    @ApiModelProperty(value = "")


    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    public PkgmNotificationsFilter vnfSoftwareVersion(String vnfSoftwareVersion) {
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

    public PkgmNotificationsFilter vnfdVersions(List<String> vnfdVersions) {
        this.vnfdVersions = vnfdVersions;
        return this;
    }

    public PkgmNotificationsFilter addVnfdVersionsItem(String vnfdVersionsItem) {
        if (this.vnfdVersions == null) {
            this.vnfdVersions = new ArrayList<String>();
        }
        this.vnfdVersions.add(vnfdVersionsItem);
        return this;
    }

    /**
     * Get vnfdVersions
     *
     * @return vnfdVersions
     **/
    @ApiModelProperty(value = "")


    public List<String> getVnfdVersions() {
        return vnfdVersions;
    }

    public void setVnfdVersions(List<String> vnfdVersions) {
        this.vnfdVersions = vnfdVersions;
    }

    public PkgmNotificationsFilter vnfdId(UUID vnfdId) {
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

    public PkgmNotificationsFilter vnfpkgId(UUID vnfpkgId) {
        this.vnfpkgId = vnfpkgId;
        return this;
    }

    /**
     * Get vnfpkgId
     *
     * @return vnfpkgId
     **/
    @ApiModelProperty(value = "")

    @Valid

    public UUID getVnfpkgId() {
        return vnfpkgId;
    }

    public void setVnfpkgId(UUID vnfpkgId) {
        this.vnfpkgId = vnfpkgId;
    }

    public PkgmNotificationsFilter operationalState(PackageOperationalStateType operationalState) {
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

    public PkgmNotificationsFilter usageState(PackageUsageStateType usageState) {
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


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PkgmNotificationsFilter pkgmNotificationsFilter = (PkgmNotificationsFilter) o;
        return Objects.equals(this.notificationTypes, pkgmNotificationsFilter.notificationTypes) &&
                Objects.equals(this.vnfProductsFromProvider, pkgmNotificationsFilter.vnfProductsFromProvider) &&
                Objects.equals(this.vnfprovider, pkgmNotificationsFilter.vnfprovider) &&
                Objects.equals(this.vnfProducts, pkgmNotificationsFilter.vnfProducts) &&
                Objects.equals(this.vfnProductName, pkgmNotificationsFilter.vfnProductName) &&
                Objects.equals(this.versions, pkgmNotificationsFilter.versions) &&
                Objects.equals(this.vnfSoftwareVersion, pkgmNotificationsFilter.vnfSoftwareVersion) &&
                Objects.equals(this.vnfdVersions, pkgmNotificationsFilter.vnfdVersions) &&
                Objects.equals(this.vnfdId, pkgmNotificationsFilter.vnfdId) &&
                Objects.equals(this.vnfpkgId, pkgmNotificationsFilter.vnfpkgId) &&
                Objects.equals(this.operationalState, pkgmNotificationsFilter.operationalState) &&
                Objects.equals(this.usageState, pkgmNotificationsFilter.usageState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationTypes, vnfProductsFromProvider, vnfprovider, vnfProducts, vfnProductName, versions, vnfSoftwareVersion, vnfdVersions, vnfdId, vnfpkgId, operationalState, usageState);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PkgmNotificationsFilter {\n");

        sb.append("    notificationTypes: ").append(toIndentedString(notificationTypes)).append("\n");
        sb.append("    vnfProductsFromProvider: ").append(toIndentedString(vnfProductsFromProvider)).append("\n");
        sb.append("    vnfprovider: ").append(toIndentedString(vnfprovider)).append("\n");
        sb.append("    vnfProducts: ").append(toIndentedString(vnfProducts)).append("\n");
        sb.append("    vfnProductName: ").append(toIndentedString(vfnProductName)).append("\n");
        sb.append("    versions: ").append(toIndentedString(versions)).append("\n");
        sb.append("    vnfSoftwareVersion: ").append(toIndentedString(vnfSoftwareVersion)).append("\n");
        sb.append("    vnfdVersions: ").append(toIndentedString(vnfdVersions)).append("\n");
        sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
        sb.append("    vnfpkgId: ").append(toIndentedString(vnfpkgId)).append("\n");
        sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
        sb.append("    usageState: ").append(toIndentedString(usageState)).append("\n");
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

