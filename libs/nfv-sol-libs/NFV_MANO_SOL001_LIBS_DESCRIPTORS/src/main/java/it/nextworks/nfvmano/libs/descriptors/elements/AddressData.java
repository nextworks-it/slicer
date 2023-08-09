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
import it.nextworks.nfvmano.libs.common.enums.AddressType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.*;

@Entity
public class AddressData implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private CpProtocolData cpProtocolData;

    private AddressType addressType;

    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private L2AddressData l2AddressData;

    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private L3AddressData l3AddressData;

    public AddressData() {

    }

    public AddressData(AddressType addressType, L2AddressData l2AddressData,
                       L3AddressData l3AddressData) {
        this.addressType = addressType;
        this.l2AddressData = l2AddressData;
        this.l3AddressData = l3AddressData;
    }

    public AddressData(CpProtocolData cpProtocolData, AddressType addressType, L2AddressData l2AddressData,
                       L3AddressData l3AddressData) {
        this.cpProtocolData = cpProtocolData;
        this.addressType = addressType;
        this.l2AddressData = l2AddressData;
        this.l3AddressData = l3AddressData;
    }

    public Long getId() {
        return id;
    }

    public CpProtocolData getCpProtocolData() {
        return cpProtocolData;
    }

    @JsonProperty("addressType")
    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    @JsonProperty("l2AddressData")
    public L2AddressData getL2AddressData() {
        return l2AddressData;
    }

    public void setL2AddressData(L2AddressData l2AddressData) {
        this.l2AddressData = l2AddressData;
    }

    @JsonProperty("l3AddressData")
    public L3AddressData getL3AddressData() {
        return l3AddressData;
    }

    public void setL3AddressData(L3AddressData l3AddressData) {
        this.l3AddressData = l3AddressData;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.addressType == null)
            throw new MalformattedElementException("AddressData without addressType");
    }

}
