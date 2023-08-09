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
package it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class NSProperties implements DescriptorInformationElement {

    private String descriptorId;
    private String designer;
    private String version;
    private String name;
    private String invariantId;

    public NSProperties() {

    }

    public NSProperties(String descriptorId, String designer, String version, String name, String invariantId) {
        this.descriptorId = descriptorId;
        this.designer = designer;
        this.version = version;
        this.name = name;
        this.invariantId = invariantId;
    }

    @JsonProperty("descriptorId")
    public String getDescriptorId() {
        return descriptorId;
    }

    @JsonProperty("designer")
    public String getDesigner() {
        return designer;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("invariantId")
    public String getInvariantId() {
        return invariantId;
    }

    public void setDescriptorId(String descriptorId) {
        this.descriptorId = descriptorId;
    }

    public void setInvariantId(String invariantId) {
        this.invariantId = invariantId;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.descriptorId == null)
            throw new MalformattedElementException("NSD without NSD ID");
        if (this.designer == null)
            throw new MalformattedElementException("NSD without designer");
        if (this.version == null)
            throw new MalformattedElementException("NSD without version");
        if (this.name == null)
            throw new MalformattedElementException("NSD without NSD name");
        if (this.invariantId == null)
            throw new MalformattedElementException("NSD without NSD Invariant ID");
    }
}
