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
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VduCp.VduCpProperties;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpProperties;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class VirtualNetworkInterfaceRequirements implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    private VduCpProperties vduCpProperties;

    @ManyToOne
    @JsonIgnore
    private VnfExtCpProperties vnfExtCpProperties;

    private String name;
    private String description;
    private boolean supportMandatory;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, String> networkInterfaceRequirements = new HashMap<String, String>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "requirements", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LogicalNodeData nicIoRequirements;

    public VirtualNetworkInterfaceRequirements() {
    }

    public VirtualNetworkInterfaceRequirements(String name, String description,
                                               boolean supportMandatory, Map<String, String> networkInterfaceRequirements, LogicalNodeData nicIoRequirements) {
        this.name = name;
        this.description = description;
        this.supportMandatory = supportMandatory;
        this.networkInterfaceRequirements = networkInterfaceRequirements;
        this.nicIoRequirements = nicIoRequirements;
    }

    public VirtualNetworkInterfaceRequirements(VduCpProperties vduCpProperties, String name, String description,
                                               boolean supportMandatory, Map<String, String> networkInterfaceRequirements, LogicalNodeData nicIoRequirements) {
        this.vduCpProperties = vduCpProperties;
        this.name = name;
        this.description = description;
        this.supportMandatory = supportMandatory;
        this.networkInterfaceRequirements = networkInterfaceRequirements;
        this.nicIoRequirements = nicIoRequirements;
    }

    public VirtualNetworkInterfaceRequirements(VnfExtCpProperties vnfExtCpProperties, String name, String description,
                                               boolean supportMandatory, Map<String, String> networkInterfaceRequirements, LogicalNodeData nicIoRequirements) {
        this.vnfExtCpProperties = vnfExtCpProperties;
        this.name = name;
        this.description = description;
        this.supportMandatory = supportMandatory;
        this.networkInterfaceRequirements = networkInterfaceRequirements;
        this.nicIoRequirements = nicIoRequirements;
    }

    public Long getId() {
        return id;
    }

    public VduCpProperties getVduCpProperties() {
        return vduCpProperties;
    }

    public VnfExtCpProperties getVnfExtCpProperties() {
        return vnfExtCpProperties;
    }

    public Map<String, String> getNetworkInterfaceRequirements() {
        return networkInterfaceRequirements;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("supportMandatory")
    public boolean isSupportMandatory() {
        return supportMandatory;
    }

    @JsonProperty("networkInterfaceRequirements")
    public Map<String, String> getNetRequirement() {
        return networkInterfaceRequirements;
    }

    @JsonProperty("nicIoRequirements")
    public LogicalNodeData getNicIoRequirements() {
        return nicIoRequirements;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (Boolean.valueOf(this.supportMandatory) == null)
            throw new MalformattedElementException("VirtualNetworkInterfaceRequirements without supportMandatory");
        if (this.networkInterfaceRequirements == null || this.networkInterfaceRequirements.isEmpty())
            throw new MalformattedElementException("VirtualNetworkInterfaceRequirements without networkInterfaceRequirements");
    }

}
