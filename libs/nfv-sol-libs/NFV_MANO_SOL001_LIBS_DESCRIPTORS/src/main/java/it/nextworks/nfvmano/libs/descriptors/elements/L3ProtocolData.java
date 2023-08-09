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
import it.nextworks.nfvmano.libs.common.enums.IpVersion;
import it.nextworks.nfvmano.libs.common.enums.Ipv6AddressMode;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class L3ProtocolData implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    Long id;

    @OneToOne
    @JsonIgnore
    private VirtualLinkProtocolData vlProtocolData;

    private String name;
    private IpVersion ipVersion;
    private String cidr;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<IpAllocationPool> ipAllocationPools = new ArrayList<>();
    private String gatewayIp;
    private boolean dhcpEnabled;
    private Ipv6AddressMode ipv6AddressMode;

    public L3ProtocolData() {

    }

    public L3ProtocolData(String name, IpVersion ipVersion, String cidr, List<IpAllocationPool> ipAllocationPools,
                          String gatewayIp, boolean dhcpEnabled, Ipv6AddressMode ipv6AddressMode) {
        this.name = name;
        this.ipVersion = ipVersion;
        this.cidr = cidr;
        this.ipAllocationPools = ipAllocationPools;
        this.gatewayIp = gatewayIp;
        this.dhcpEnabled = dhcpEnabled;
        this.ipv6AddressMode = ipv6AddressMode;
    }

    public L3ProtocolData(VirtualLinkProtocolData vlProtocolData, String name, IpVersion ipVersion, String cidr,
                          List<IpAllocationPool> ipAllocationPools, String gatewayIp, boolean dhcpEnabled,
                          Ipv6AddressMode ipv6AddressMode) {
        this.vlProtocolData = vlProtocolData;
        this.name = name;
        this.ipVersion = ipVersion;
        this.cidr = cidr;
        this.ipAllocationPools = ipAllocationPools;
        this.gatewayIp = gatewayIp;
        this.dhcpEnabled = dhcpEnabled;
        this.ipv6AddressMode = ipv6AddressMode;
    }

    public Long getId() {
        return id;
    }

    public VirtualLinkProtocolData getVlProtocolData() {
        return vlProtocolData;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("ipVersion")
    public IpVersion getIpVersion() {
        return ipVersion;
    }

    @JsonProperty("cidr")
    public String getCidr() {
        return cidr;
    }

    @JsonProperty("ipAllocationPools")
    public List<IpAllocationPool> getIpAllocationPools() {
        return ipAllocationPools;
    }

    @JsonProperty("gatewayIp")
    public String getGatewayIp() {
        return gatewayIp;
    }

    @JsonProperty("dhcpEnabled")
    public boolean isDhcpEnabled() {
        return dhcpEnabled;
    }

    @JsonProperty("ipv6AddressMode")
    public Ipv6AddressMode getIpv6AddressMode() {
        return ipv6AddressMode;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.ipVersion == null)
            throw new MalformattedElementException("L3ProtocolData without ipVersion");
        if (this.cidr == null)
            throw new MalformattedElementException("L3ProtocolData without cidr");
        if (this.ipAllocationPools != null && !this.ipAllocationPools.isEmpty())
            for (IpAllocationPool pool : this.ipAllocationPools) {
                pool.isValid();
            }
    }
}
