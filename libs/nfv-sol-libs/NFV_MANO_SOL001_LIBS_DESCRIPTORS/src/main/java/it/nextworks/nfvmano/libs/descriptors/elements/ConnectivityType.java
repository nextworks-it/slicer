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
import it.nextworks.nfvmano.libs.common.enums.FlowPattern;
import it.nextworks.nfvmano.libs.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NsVirtualLink.NsVirtualLinkProperties;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfVirtualLink.VnfVirtualLinkProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ConnectivityType implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JsonIgnore
    private NsVirtualLinkProperties nsVLProperties;

    @OneToOne
    @JsonIgnore
    private VnfVirtualLinkProperties vnfVLProperties;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<LayerProtocol> layerProtocols = new ArrayList<>();
    private FlowPattern flowPattern;

    public ConnectivityType() {

    }

    public ConnectivityType(List<LayerProtocol> layerProtocols, FlowPattern flowPattern) {
        this.layerProtocols = layerProtocols;
        this.flowPattern = flowPattern;
    }

    public ConnectivityType(NsVirtualLinkProperties nsVLProperties, List<LayerProtocol> layerProtocols, FlowPattern flowPattern) {
        this.nsVLProperties = nsVLProperties;
        this.layerProtocols = layerProtocols;
        this.flowPattern = flowPattern;
    }

    public ConnectivityType(VnfVirtualLinkProperties vnfVLProperties, List<LayerProtocol> layerProtocols, FlowPattern flowPattern) {
        this.vnfVLProperties = vnfVLProperties;
        this.layerProtocols = layerProtocols;
        this.flowPattern = flowPattern;
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

    @JsonProperty("layerProtocols")
    public List<LayerProtocol> getLayerProtocols() {
        return layerProtocols;
    }

    @JsonProperty("flowPattern")
    public FlowPattern getFlowPattern() {
        return flowPattern;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.layerProtocols == null || this.layerProtocols.isEmpty())
            throw new MalformattedElementException("ConnectivityType without layerProtocols");
    }

}
