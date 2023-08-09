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
import it.nextworks.nfvmano.libs.common.enums.NetworkType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

@Embeddable
public class L2ProtocolData implements DescriptorInformationElement {

    private String name;
    private NetworkType networkType;
    private boolean vlanTransparent;
    private int mtu;

    public L2ProtocolData() {

    }

    public L2ProtocolData(String name, NetworkType networkType, boolean vlanTransparent, int mtu) {
        this.name = name;
        this.networkType = networkType;
        this.vlanTransparent = vlanTransparent;
        this.mtu = mtu;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("networkType")
    public NetworkType getNetworkType() {
        return networkType;
    }

    @JsonProperty("vlanTransparent")
    public boolean isVlanTransparent() {
        return vlanTransparent;
    }

    @JsonProperty("mtu")
    public int getMtu() {
        return mtu;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
