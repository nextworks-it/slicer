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
import it.nextworks.nfvmano.libs.common.enums.VnfIndicatorSource;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class VnfIndicator implements DescriptorInformationElement {

    private String indicatorId;
    private String name;
    private String indicatorValue;
    private VnfIndicatorSource source;

    public VnfIndicator() {
    }

    public VnfIndicator(String indicatorId, String name, String indicatorValue, VnfIndicatorSource source) {
        this.indicatorId = indicatorId;
        this.name = name;
        this.indicatorValue = indicatorValue;
        this.source = source;
    }

    @JsonProperty("id")
    public String getIndicatorId() {
        return indicatorId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("indicatorValue")
    public String getIndicatorValue() {
        return indicatorValue;
    }

    @JsonProperty("source")
    public VnfIndicatorSource getSource() {
        return source;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (indicatorId == null)
            throw new MalformattedElementException("VNF indicator without ID");
    }

}
