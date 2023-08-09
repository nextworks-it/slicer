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
package it.nextworks.nfvmano.libs.descriptors.relationships;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.templates.Relationship;

import javax.persistence.Embeddable;

@Embeddable
public class VirtualBindsTo implements DescriptorInformationElement {

    private String type;
    private String name;

    public VirtualBindsTo() {

    }

    public VirtualBindsTo(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public VirtualBindsTo(Relationship relationshipTemplate, String relationshipName) {
        this.type = relationshipTemplate.getType();
        this.name = relationshipName;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.type == null)
            throw new MalformattedElementException("VirtualBindsTo relationship without type");
        if (this.name == null)
            throw new MalformattedElementException("VirtualBindsTo relationship without name");
    }
}
