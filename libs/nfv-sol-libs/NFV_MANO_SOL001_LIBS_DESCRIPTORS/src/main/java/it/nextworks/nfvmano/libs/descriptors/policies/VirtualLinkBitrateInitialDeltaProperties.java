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
package it.nextworks.nfvmano.libs.descriptors.policies;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.VirtualLinkBitrateLevel;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class VirtualLinkBitrateInitialDeltaProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VirtualLinkBitrateInitialDelta virtualLinkBitrateInitialDelta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vlBitrateInitialDelta", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VirtualLinkBitrateLevel initialDelta;

    public VirtualLinkBitrateInitialDeltaProperties() {
    }

    public VirtualLinkBitrateInitialDeltaProperties(VirtualLinkBitrateLevel initialDelta) {
        this.initialDelta = initialDelta;
    }

    public Long getId() {
        return id;
    }

    public VirtualLinkBitrateInitialDelta getVirtualLinkBitrateInitialDelta() {
        return virtualLinkBitrateInitialDelta;
    }

    @JsonProperty("initialDelta")
    public VirtualLinkBitrateLevel getInitialDelta() {
        return initialDelta;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.initialDelta == null)
            throw new MalformattedElementException("VirtualLinkBitrateInitialDeltaProperties without initialDelta");
        else
            this.initialDelta.isValid();
    }
}
