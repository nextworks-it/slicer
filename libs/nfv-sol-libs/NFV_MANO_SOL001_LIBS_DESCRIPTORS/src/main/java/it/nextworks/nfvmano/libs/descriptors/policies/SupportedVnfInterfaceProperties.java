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
import it.nextworks.nfvmano.libs.common.enums.VnfInterfaceType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.InterfaceDetails;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class SupportedVnfInterfaceProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private SupportedVnfInterface supportedVnfInterface;

    private VnfInterfaceType interfaceName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "supportedVnfInterfaceProperties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InterfaceDetails details;

    public SupportedVnfInterfaceProperties() {
    }

    public SupportedVnfInterfaceProperties(VnfInterfaceType interfaceName, InterfaceDetails details) {
        this.interfaceName = interfaceName;
        this.details = details;
    }

    public SupportedVnfInterfaceProperties(SupportedVnfInterface supportedVnfInterface, VnfInterfaceType interfaceName, InterfaceDetails details) {
        this.supportedVnfInterface = supportedVnfInterface;
        this.interfaceName = interfaceName;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public SupportedVnfInterface getSupportedVnfInterface() {
        return supportedVnfInterface;
    }

    @JsonProperty("interfaceName")
    public VnfInterfaceType getInterfaceName() {
        return interfaceName;
    }

    @JsonProperty("details")
    public InterfaceDetails getDetails() {
        return details;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.interfaceName == null)
            throw new MalformattedElementException("SupportedVnfInterfaceProperties without interfaceName");
    }
}
