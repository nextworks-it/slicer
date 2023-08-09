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
package it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.Cp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.CpRole;
import it.nextworks.nfvmano.libs.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.CpProtocolData;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance
@DiscriminatorColumn(name = "CONNPOINT_TYPE")
public class CpProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private CpNode cpNode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<LayerProtocol> layerProtocols = new ArrayList<>();
    private CpRole role;
    private String description;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "properties", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CpProtocolData> protocol = new ArrayList<>();

    private boolean trunkMode;

    public CpProperties() {

    }

    public CpProperties(List<LayerProtocol> layerProtocols, CpRole role, String description,
                        List<CpProtocolData> protocol, boolean trunkMode) {
        this.layerProtocols = layerProtocols;
        this.role = role;
        this.description = description;
        this.protocol = protocol;
        this.trunkMode = trunkMode;
    }

    public CpProperties(CpNode cpNode, List<LayerProtocol> layerProtocols, CpRole role, String description,
                        List<CpProtocolData> protocol, boolean trunkMode) {
        this.cpNode = cpNode;
        this.layerProtocols = layerProtocols;
        this.role = role;
        this.description = description;
        this.protocol = protocol;
        this.trunkMode = trunkMode;
    }

    public Long getId() {
        return id;
    }

    public CpNode getCpNode() {
        return cpNode;
    }

    @JsonProperty("layerProtocols")
    public List<LayerProtocol> getLayerProtocols() {
        return layerProtocols;
    }

    @JsonProperty("role")
    public CpRole getRole() {
        return role;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("protocol")
    public List<CpProtocolData> getProtocol() {
        return protocol;
    }

    @JsonProperty("trunkMode")
    public boolean isTrunkMode() {
        return trunkMode;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.layerProtocols == null)
            throw new MalformattedElementException("Cp Node properties without layerProtocols");
        if (this.protocol == null || this.protocol.isEmpty()) {
            throw new MalformattedElementException("Cp Node properties without protocol");
        } else {
            for (CpProtocolData cpData : this.protocol) {
                cpData.isValid();
            }
        }
    }
}
