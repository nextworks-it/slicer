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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.VduLevel;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class VduInitialDeltaProperties implements DescriptorInformationElement {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Embedded
    private VduLevel initialDelta;

    public VduInitialDeltaProperties() {
    }

    public VduInitialDeltaProperties(VduLevel initialDelta) {
        this.initialDelta = initialDelta;
    }

    @JsonProperty("initialDelta")
    public VduLevel getInitialDelta() {
        return initialDelta;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.initialDelta == null)
            throw new MalformattedElementException("VduInitialDeltaProperties without initialDelta");
    }
}
