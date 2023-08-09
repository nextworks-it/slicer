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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.elements.QoS;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class NsVlProfile implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nsVlProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LinkBitrateRequirements maxBitrateRequirements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nsVlProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LinkBitrateRequirements minBitrateRequirements;

    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private QoS qos;

    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ServiceAvailability serviceAvailability;

    public NsVlProfile() {
    }

    public NsVlProfile(LinkBitrateRequirements maxBitrateRequirements, LinkBitrateRequirements minBitrateRequirements, QoS qos, ServiceAvailability serviceAvailability) {
        this.maxBitrateRequirements = maxBitrateRequirements;
        this.minBitrateRequirements = minBitrateRequirements;
        this.qos = qos;
        this.serviceAvailability = serviceAvailability;
    }

    public Long getId() {
        return id;
    }

    @JsonProperty("maxBitrateRequirements")
    public LinkBitrateRequirements getMaxBitrateRequirements() {
        return maxBitrateRequirements;
    }

    @JsonProperty("minBitrateRequirements")
    public LinkBitrateRequirements getMinBitrateRequirements() {
        return minBitrateRequirements;
    }

    @JsonProperty("qos")
    public QoS getQos() {
        return qos;
    }

    @JsonProperty("serviceAvailability")
    public ServiceAvailability getServiceAvailability() {
        return serviceAvailability;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.minBitrateRequirements == null)
            throw new MalformattedElementException("NS VL profile without min bitrate requirements");
        else
            this.minBitrateRequirements.isValid();
    }
}
