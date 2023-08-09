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
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class VnfMonitoringParameter implements DescriptorInformationElement {

    private String parameterName;
    private String performanceMetric;
    private int collectionPeriod;

    public VnfMonitoringParameter() {
    }

    public VnfMonitoringParameter(String parameterName, String performanceMetric, int collectionPeriod) {
        this.parameterName = parameterName;
        this.performanceMetric = performanceMetric;
        this.collectionPeriod = collectionPeriod;
    }

    @JsonProperty("parameterName")
    public String getParameterName() {
        return parameterName;
    }

    @JsonProperty("performanceMetric")
    public String getPerformanceMetric() {
        return performanceMetric;
    }

    @JsonProperty("collectionPeriod")
    public int getCollectionPeriod() {
        return collectionPeriod;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.performanceMetric == null)
            throw new MalformattedElementException("VnfMonitoringParameter without performanceMetric");
    }
}
