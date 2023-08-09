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
package it.nextworks.nfvmano.libs.descriptors.templates;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class Metadata implements DescriptorInformationElement {

    private String descriptorId;
    private String vendor;
    private String version;

    public Metadata() {

    }

    public Metadata(String descriptorId, String vendor, String version) {
        this.descriptorId = descriptorId;
        this.vendor = vendor;
        this.version = version;
    }

    @JsonProperty("descriptorId")
    public String getDescriptorId() {
        return descriptorId;
    }

    @JsonProperty("vendor")
    public String getVendor() {
        return vendor;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    public void setDescriptorId(String descriptorId) {
        this.descriptorId = descriptorId;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.descriptorId == null)
            throw new MalformattedElementException("DescriptorTemplate without descriptorId");
        if (this.vendor == null)
            throw new MalformattedElementException("DescriptorTemplate without vendor");
        if (this.version == null)
            throw new MalformattedElementException("DescriptorTemplate without version");
    }
}
