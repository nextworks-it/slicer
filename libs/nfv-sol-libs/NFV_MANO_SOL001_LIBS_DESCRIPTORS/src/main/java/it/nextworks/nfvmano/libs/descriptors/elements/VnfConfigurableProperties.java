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
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class VnfConfigurableProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VNFProperties vnfProperties;

    private boolean isAutoscaleEnabled = false;
    private boolean isAutohealEnabled = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfConfigurableProperties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnfAdditionalConfigurableProperties additionalConfigurableProperties;

    public VnfConfigurableProperties() {
    }

    public VnfConfigurableProperties(boolean isAutoscaleEnabled, boolean isAutohealEnabled,
                                     VnfAdditionalConfigurableProperties additionalConfigurableProperties) {
        this.isAutohealEnabled = isAutohealEnabled;
        this.isAutoscaleEnabled = isAutoscaleEnabled;
        this.additionalConfigurableProperties = additionalConfigurableProperties;
    }

    public VnfConfigurableProperties(VNFProperties vnfProperties, boolean isAutoscaleEnabled, boolean isAutohealEnabled,
                                     VnfAdditionalConfigurableProperties additionalConfigurableProperties) {
        this.vnfProperties = vnfProperties;
        this.isAutohealEnabled = isAutohealEnabled;
        this.isAutoscaleEnabled = isAutoscaleEnabled;
        this.additionalConfigurableProperties = additionalConfigurableProperties;
    }

    public Long getId() {
        return id;
    }

    public VNFProperties getVnfProperties() {
        return vnfProperties;
    }

    @JsonProperty("isAutoscaleEnabled")
    public boolean getIsAutoscaleEnabled() {
        return isAutoscaleEnabled;
    }

    @JsonProperty("isAutohealEnabled")
    public boolean getIsAutohealEnabled() {
        return isAutohealEnabled;
    }

    @JsonProperty("additionalConfigurableProperties")
    public VnfAdditionalConfigurableProperties getAdditionalConfigurableProperties() {
        return additionalConfigurableProperties;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
