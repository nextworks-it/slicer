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
package it.nextworks.nfvmano.libs.descriptors.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.ContainerFormat;
import it.nextworks.nfvmano.libs.common.enums.DiskFormat;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class SwImageData implements DescriptorInformationElement {

    private String imageName;
    private String version;
    private String checksum;
    private ContainerFormat containerFormat;
    private DiskFormat diskFormat;
    private Integer minDisk;
    private Integer minRam;
    private Integer size;
    private String operatingSystem;
    private String supportedVirtualisationEnvironment;

    public SwImageData() {

    }

    public SwImageData(String imageName, String version, String checksum, ContainerFormat containerFormat,
                       DiskFormat diskFormat, Integer minDisk, Integer minRam, Integer size, String operatingSystem,
                       String supportedVirtualisationEnvironment) {
        this.imageName = imageName;
        this.version = version;
        this.checksum = checksum;
        this.containerFormat = containerFormat;
        this.diskFormat = diskFormat;
        this.minDisk = minDisk;
        this.minRam = minRam;
        this.size = size;
        this.operatingSystem = operatingSystem;
        this.supportedVirtualisationEnvironment = supportedVirtualisationEnvironment;
    }

    @JsonProperty("imageName")
    public String getImageName() {
        return imageName;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("checksum")
    public String getChecksum() {
        return checksum;
    }

    @JsonProperty("containerFormat")
    public ContainerFormat getContainerFormat() {
        return containerFormat;
    }

    @JsonProperty("diskFormat")
    public DiskFormat getDiskFormat() {
        return diskFormat;
    }

    @JsonProperty("minDisk")
    public Integer getMinDisk() {
        return minDisk;
    }

    @JsonProperty("minRam")
    public Integer getMinRam() {
        return minRam;
    }

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("operatingSystem")
    public String getOperatingSystem() {
        return operatingSystem;
    }

    @JsonProperty("supportedVirtualisationEnvironment")
    public String getSupportedVirtualisationEnvironment() {
        return supportedVirtualisationEnvironment;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (imageName == null)
            throw new MalformattedElementException("SwImageData without without name");
        if (version == null)
            throw new MalformattedElementException("SwImageData without version");
        if (checksum == null)
            throw new MalformattedElementException("SwImageData without checksum");
        if (this.containerFormat == null)
            throw new MalformattedElementException("SwImageData without containerFormat");
        if (this.diskFormat == null)
            throw new MalformattedElementException("SwImageData without diskFormat");
        if (this.size == null)
            throw new MalformattedElementException("SwImageData without size");
    }
}
