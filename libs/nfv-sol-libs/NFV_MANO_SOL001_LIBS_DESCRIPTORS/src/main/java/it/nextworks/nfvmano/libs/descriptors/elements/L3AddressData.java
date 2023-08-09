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
import it.nextworks.nfvmano.libs.common.enums.IpVersion;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class L3AddressData implements DescriptorInformationElement {

    private boolean ipAddressAssignment;
    private boolean floatingIpActivated;
    private IpVersion ipAddressType;
    private int numberOfIpAddress;

    public L3AddressData() {

    }

    public L3AddressData(boolean ipAddressAssignment, boolean floatingIpActivated, IpVersion ipAddressType,
                         int numberOfIpAddress) {
        this.ipAddressAssignment = ipAddressAssignment;
        this.floatingIpActivated = floatingIpActivated;
        this.ipAddressType = ipAddressType;
        this.numberOfIpAddress = numberOfIpAddress;
    }

    @JsonProperty("ipAddressAssignment")
    public boolean getIpAddressAssignment() {
        return ipAddressAssignment;
    }

    public void setIpAddressAssignment(Boolean ipAddressAssignment) {
        this.ipAddressAssignment = ipAddressAssignment;
    }

    @JsonProperty("floatingIpActivated")
    public boolean getFloatingIpActivated() {
        return floatingIpActivated;
    }

    public void setFloatingIpActivated(Boolean floatingIpActivated) {
        this.floatingIpActivated = floatingIpActivated;
    }

    @JsonProperty("ipAddressType")
    public IpVersion getIpAddressType() {
        return ipAddressType;
    }

    public void setIpAddressType(IpVersion ipAddressType) {
        this.ipAddressType = ipAddressType;
    }

    @JsonProperty("numberOfIpAddress")
    public int getNumberOfIpAddress() {
        return numberOfIpAddress;
    }

    public void setNumberOfIpAddress(int numberOfIpAddress) {
        this.numberOfIpAddress = numberOfIpAddress;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (Boolean.valueOf(this.ipAddressAssignment) == null)
            throw new MalformattedElementException("L3AddressData without ipAddressAssignment");
        if (Boolean.valueOf(this.floatingIpActivated) == null)
            throw new MalformattedElementException("L3AddressData without floatingIpActivated");
    }
}
