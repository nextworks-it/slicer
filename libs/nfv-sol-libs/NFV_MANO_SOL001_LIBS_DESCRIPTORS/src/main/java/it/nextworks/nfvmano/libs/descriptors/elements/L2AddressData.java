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
public class L2AddressData implements DescriptorInformationElement {

    private boolean macAddressAssignement;

    public L2AddressData() {

    }

    public L2AddressData(boolean macAddressAssignement) {
        this.macAddressAssignement = macAddressAssignement;
    }

    @JsonProperty("macAddressAssignement")
    public boolean getMacAddressAssignement() {
        return macAddressAssignement;
    }

    public void setMacAddressAssignement(Boolean macAddressAssignement) {
        this.macAddressAssignement = macAddressAssignement;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (Boolean.valueOf(this.macAddressAssignement) == null)
            throw new MalformattedElementException("L2AddressData without macAddressAssignment");
    }
}
