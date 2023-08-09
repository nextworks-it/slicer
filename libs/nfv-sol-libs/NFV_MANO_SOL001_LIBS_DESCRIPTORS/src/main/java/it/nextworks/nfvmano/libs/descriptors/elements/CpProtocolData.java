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
import it.nextworks.nfvmano.libs.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.Cp.CpProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class CpProtocolData implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private CpProperties properties;

    private LayerProtocol associatedLayerProtocol;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "cpProtocolData", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AddressData addressData;

    public CpProtocolData() {

    }

    public CpProtocolData(LayerProtocol associatedLayerProtocol, AddressData addressData) {
        this.associatedLayerProtocol = associatedLayerProtocol;
        this.addressData = addressData;
    }

    public CpProtocolData(CpProperties properties, LayerProtocol associatedLayerProtocol, AddressData addressData) {
        this.properties = properties;
        this.associatedLayerProtocol = associatedLayerProtocol;
        this.addressData = addressData;
    }

    public Long getId() {
        return id;
    }

    public CpProperties getProperties() {
        return properties;
    }

    @JsonProperty("associatedLayerProtocol")
    public LayerProtocol getAssociatedLayerProtocol() {
        return associatedLayerProtocol;
    }

    @JsonProperty("addressData")
    public AddressData getAddressData() {
        return addressData;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.associatedLayerProtocol == null)
            throw new MalformattedElementException("CpProtocolData without associatedLayerProtocol");
    }

}
