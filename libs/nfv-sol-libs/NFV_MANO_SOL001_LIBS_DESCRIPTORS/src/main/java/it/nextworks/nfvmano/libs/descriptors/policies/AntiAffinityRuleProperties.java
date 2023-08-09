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

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.ScopeType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class AntiAffinityRuleProperties implements DescriptorInformationElement {

    private ScopeType scope;

    public AntiAffinityRuleProperties() {
    }

    public AntiAffinityRuleProperties(ScopeType scope) {
        this.scope = scope;
    }

    @JsonProperty("scope")
    public ScopeType getScope() {
        return scope;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.scope == null) {
            throw new MalformattedElementException("AntiAffinityRuleProperties without scope");
        }
    }
}
