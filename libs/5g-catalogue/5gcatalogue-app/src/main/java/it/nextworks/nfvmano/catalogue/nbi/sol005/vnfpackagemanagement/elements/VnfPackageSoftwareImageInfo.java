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
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.KeyValuePairs;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

/**
 * VnfPackageSoftwareImageInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-05T11:50:31.473+02:00")

public class VnfPackageSoftwareImageInfo {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("provider")
    private String provider = null;

    @JsonProperty("version")
    private String version = null;

    @JsonProperty("checksum")
    private String checksum = null;

    @JsonProperty("containerFormat")
    private ContainerFormat containerFormat = null;

    @JsonProperty("diskFormat")
    private DiskFormat diskFormat = null;

    @JsonProperty("createdAt")
    private String createdAt = null;

    @JsonProperty("minDisk")
    private Integer minDisk = null;

    @JsonProperty("minRam")
    private Integer minRam = null;

    @JsonProperty("size")
    private Integer size = null;

    @JsonProperty("userMetadata")
    private KeyValuePairs userMetadata = null;

    @JsonProperty("imagePath")
    private String imagePath = null;

    public VnfPackageSoftwareImageInfo id(UUID id) {
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

    public VnfPackageSoftwareImageInfo name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(value = "")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VnfPackageSoftwareImageInfo provider(String provider) {
        this.provider = provider;
        return this;
    }

    /**
     * Get provider
     *
     * @return provider
     **/
    @ApiModelProperty(value = "")


    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public VnfPackageSoftwareImageInfo version(String version) {
        this.version = version;
        return this;
    }

    /**
     * Get version
     *
     * @return version
     **/
    @ApiModelProperty(value = "")


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public VnfPackageSoftwareImageInfo checksum(String checksum) {
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

    public VnfPackageSoftwareImageInfo containerFormat(ContainerFormat containerFormat) {
        this.containerFormat = containerFormat;
        return this;
    }

    /**
     * Get containerFormat
     *
     * @return containerFormat
     **/
    @ApiModelProperty(value = "")

    @Valid

    public ContainerFormat getContainerFormat() {
        return containerFormat;
    }

    public void setContainerFormat(ContainerFormat containerFormat) {
        this.containerFormat = containerFormat;
    }

    public VnfPackageSoftwareImageInfo diskFormat(DiskFormat diskFormat) {
        this.diskFormat = diskFormat;
        return this;
    }

    /**
     * Get diskFormat
     *
     * @return diskFormat
     **/
    @ApiModelProperty(value = "")

    @Valid

    public DiskFormat getDiskFormat() {
        return diskFormat;
    }

    public void setDiskFormat(DiskFormat diskFormat) {
        this.diskFormat = diskFormat;
    }

    public VnfPackageSoftwareImageInfo createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Get createdAt
     *
     * @return createdAt
     **/
    @ApiModelProperty(value = "")


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public VnfPackageSoftwareImageInfo minDisk(Integer minDisk) {
        this.minDisk = minDisk;
        return this;
    }

    /**
     * Get minDisk
     *
     * @return minDisk
     **/
    @ApiModelProperty(value = "")


    public Integer getMinDisk() {
        return minDisk;
    }

    public void setMinDisk(Integer minDisk) {
        this.minDisk = minDisk;
    }

    public VnfPackageSoftwareImageInfo minRam(Integer minRam) {
        this.minRam = minRam;
        return this;
    }

    /**
     * Get minRam
     *
     * @return minRam
     **/
    @ApiModelProperty(value = "")


    public Integer getMinRam() {
        return minRam;
    }

    public void setMinRam(Integer minRam) {
        this.minRam = minRam;
    }

    public VnfPackageSoftwareImageInfo size(Integer size) {
        this.size = size;
        return this;
    }

    /**
     * Get size
     *
     * @return size
     **/
    @ApiModelProperty(value = "")


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public VnfPackageSoftwareImageInfo userMetadata(KeyValuePairs userMetadata) {
        this.userMetadata = userMetadata;
        return this;
    }

    /**
     * Get userMetadata
     *
     * @return userMetadata
     **/
    @ApiModelProperty(value = "")

    @Valid

    public KeyValuePairs getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(KeyValuePairs userMetadata) {
        this.userMetadata = userMetadata;
    }

    public VnfPackageSoftwareImageInfo imagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    /**
     * Get imagePath
     *
     * @return imagePath
     **/
    @ApiModelProperty(value = "")


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VnfPackageSoftwareImageInfo vnfPackageSoftwareImageInfo = (VnfPackageSoftwareImageInfo) o;
        return Objects.equals(this.id, vnfPackageSoftwareImageInfo.id) &&
                Objects.equals(this.name, vnfPackageSoftwareImageInfo.name) &&
                Objects.equals(this.provider, vnfPackageSoftwareImageInfo.provider) &&
                Objects.equals(this.version, vnfPackageSoftwareImageInfo.version) &&
                Objects.equals(this.checksum, vnfPackageSoftwareImageInfo.checksum) &&
                Objects.equals(this.containerFormat, vnfPackageSoftwareImageInfo.containerFormat) &&
                Objects.equals(this.diskFormat, vnfPackageSoftwareImageInfo.diskFormat) &&
                Objects.equals(this.createdAt, vnfPackageSoftwareImageInfo.createdAt) &&
                Objects.equals(this.minDisk, vnfPackageSoftwareImageInfo.minDisk) &&
                Objects.equals(this.minRam, vnfPackageSoftwareImageInfo.minRam) &&
                Objects.equals(this.size, vnfPackageSoftwareImageInfo.size) &&
                Objects.equals(this.userMetadata, vnfPackageSoftwareImageInfo.userMetadata) &&
                Objects.equals(this.imagePath, vnfPackageSoftwareImageInfo.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, provider, version, checksum, containerFormat, diskFormat, createdAt, minDisk, minRam, size, userMetadata, imagePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VnfPackageSoftwareImageInfo {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    checksum: ").append(toIndentedString(checksum)).append("\n");
        sb.append("    containerFormat: ").append(toIndentedString(containerFormat)).append("\n");
        sb.append("    diskFormat: ").append(toIndentedString(diskFormat)).append("\n");
        sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
        sb.append("    minDisk: ").append(toIndentedString(minDisk)).append("\n");
        sb.append("    minRam: ").append(toIndentedString(minRam)).append("\n");
        sb.append("    size: ").append(toIndentedString(size)).append("\n");
        sb.append("    userMetadata: ").append(toIndentedString(userMetadata)).append("\n");
        sb.append("    imagePath: ").append(toIndentedString(imagePath)).append("\n");
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

