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
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NsVirtualLink.NsVirtualLinkProperties;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfVirtualLink.VnfVirtualLinkProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class VlProfile implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private NsVirtualLinkProperties nsVLProperties;

    @OneToOne
    @JsonIgnore
    private VnfVirtualLinkProperties vnfVLProperties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vlProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LinkBitrateRequirements maxBitrateRequirements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vlProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LinkBitrateRequirements minBitrateRequirements;

    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private QoS qos;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "vlProfile", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VirtualLinkProtocolData> virtualLinkProtocolData = new ArrayList<>();

    public VlProfile() {

    }

    public VlProfile(LinkBitrateRequirements maxBitrateRequirements, LinkBitrateRequirements minBitrateRequirements,
                     QoS qos, List<VirtualLinkProtocolData> virtualLinkProtocolData) {
        this.maxBitrateRequirements = maxBitrateRequirements;
        this.minBitrateRequirements = minBitrateRequirements;
        this.qos = qos;
        this.virtualLinkProtocolData = virtualLinkProtocolData;
    }

    public VlProfile(NsVirtualLinkProperties nsVLProperties, LinkBitrateRequirements maxBitrateRequirements,
                     LinkBitrateRequirements minBitrateRequirements, QoS qos,
                     List<VirtualLinkProtocolData> virtualLinkProtocolData) {
        this.nsVLProperties = nsVLProperties;
        this.maxBitrateRequirements = maxBitrateRequirements;
        this.minBitrateRequirements = minBitrateRequirements;
        this.qos = qos;
        this.virtualLinkProtocolData = virtualLinkProtocolData;
    }

    public VlProfile(VnfVirtualLinkProperties vnfVLProperties, LinkBitrateRequirements maxBitrateRequirements,
                     LinkBitrateRequirements minBitrateRequirements, QoS qos,
                     List<VirtualLinkProtocolData> virtualLinkProtocolData) {
        this.vnfVLProperties = vnfVLProperties;
        this.maxBitrateRequirements = maxBitrateRequirements;
        this.minBitrateRequirements = minBitrateRequirements;
        this.qos = qos;
        this.virtualLinkProtocolData = virtualLinkProtocolData;
    }

    public Long getId() {
        return id;
    }

    public NsVirtualLinkProperties getNsVLProperties() {
        return nsVLProperties;
    }

    public VnfVirtualLinkProperties getVnfVLProperties() {
        return vnfVLProperties;
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

    @JsonProperty("virtualLinkProtocolData")
    public List<VirtualLinkProtocolData> getVirtualLinkProtocolData() {
        return virtualLinkProtocolData;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.minBitrateRequirements == null)
            throw new MalformattedElementException("VL profile without min bitrate requirements");
        else
            this.minBitrateRequirements.isValid();
        if (this.maxBitrateRequirements == null)
            throw new MalformattedElementException("VL profile without max bitrate requirements");
        else
            this.maxBitrateRequirements.isValid();
        if (this.qos != null)
            this.qos.isValid();
        if (this.virtualLinkProtocolData != null && !this.virtualLinkProtocolData.isEmpty())
            for (VirtualLinkProtocolData data : this.virtualLinkProtocolData) {
                data.isValid();
            }
    }
}
