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
package it.nextworks.nfvmano.libs.descriptors.elements.lcm;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class TerminateVnfOpConfig implements DescriptorInformationElement {

    private String minGracefulTerminationTimeout;
    private String maxRecommendedGracefulTerminationTimeout;

    public TerminateVnfOpConfig() {
    }

    public TerminateVnfOpConfig(String minGracefulStopTimeout, String maxRecommendedGracefulStopTimeout) {
        this.maxRecommendedGracefulTerminationTimeout = maxRecommendedGracefulStopTimeout;
        this.minGracefulTerminationTimeout = minGracefulStopTimeout;
    }

    @JsonProperty("minGracefulTerminationTimeout")
    public String getMinGracefulTerminationTimeout() {
        return minGracefulTerminationTimeout;
    }

    @JsonProperty("maxRecommendedGracefulTerminationTimeout")
    public String getMaxRecommendedGracefulTerminationTimeout() {
        return maxRecommendedGracefulTerminationTimeout;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (minGracefulTerminationTimeout == null)
            throw new MalformattedElementException("Operate VNF config data without minimum graceful termination timeout");
    }

}
