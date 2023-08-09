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
package it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.SwImageData;
import it.nextworks.nfvmano.libs.descriptors.elements.VduProfile;
import it.nextworks.nfvmano.libs.descriptors.elements.VnfMonitoringParameter;
import it.nextworks.nfvmano.libs.descriptors.elements.VnfcConfigurableProperties;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class VDUComputeProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VDUComputeNode vduComputeNode;

    private String name;
    private String description;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> bootOrder = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> nfviConstraints = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<VnfMonitoringParameter> monitoringParameters = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "properties", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, VnfcConfigurableProperties> configurableProperties = new HashMap<>();

    private String bootData;

    @Embedded
    private VduProfile vduProfile;

    @Embedded
    private SwImageData swImageData;

    public VDUComputeProperties() {

    }

    public VDUComputeProperties(String name, String description, List<String> bootOrder, List<String> nfviConstraints, List<VnfMonitoringParameter> monitoringParameters, Map<String, VnfcConfigurableProperties> configurableProperties, String bootData, VduProfile vduProfile, SwImageData swImageData) {
        this.name = name;
        this.description = description;
        this.bootOrder = bootOrder;
        this.nfviConstraints = nfviConstraints;
        this.monitoringParameters = monitoringParameters;
        this.configurableProperties = configurableProperties;
        this.bootData = bootData;
        this.vduProfile = vduProfile;
        this.swImageData = swImageData;
    }

    public VDUComputeProperties(VDUComputeNode vduComputeNode, String name, String description,
                                ArrayList<String> bootOrder, ArrayList<String> nfviConstraints,
                                ArrayList<VnfMonitoringParameter> monitoringParameters,
                                Map<String, VnfcConfigurableProperties> configurableProperties, String bootData,
                                VduProfile vduProfile, SwImageData swImageData) {
        this.vduComputeNode = vduComputeNode;
        this.name = name;
        this.description = description;
        this.bootOrder = bootOrder;
        this.nfviConstraints = nfviConstraints;
        this.monitoringParameters = monitoringParameters;
        this.configurableProperties = configurableProperties;
        this.bootData = bootData;
        this.vduProfile = vduProfile;
        this.swImageData = swImageData;
    }

    public Long getId() {
        return id;
    }

    public VDUComputeNode getVduComputeNode() {
        return vduComputeNode;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("bootOrder")
    public List<String> getBootOrder() {
        return bootOrder;
    }

    @JsonProperty("nfviConstraints")
    public List<String> getNfviConstraints() {
        return nfviConstraints;
    }

    @JsonProperty("monitoringParameters")
    public List<VnfMonitoringParameter> getMonitoringParameters() {
        return monitoringParameters;
    }

    @JsonProperty("configurableProperties")
    public Map<String, VnfcConfigurableProperties> getConfigurableProperties() {
        return configurableProperties;
    }

    @JsonProperty("bootData")
    public String getBootData() {
        return bootData;
    }

    @JsonProperty("vduProfile")
    public VduProfile getVduProfile() {
        return vduProfile;
    }

    @JsonProperty("swImageData")
    public SwImageData getSwImageData() {
        return swImageData;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.name == null)
            throw new MalformattedElementException("VDUCompute properties without name");
        if (this.description == null)
            throw new MalformattedElementException("VDUCompute Node properties without description");
        if (this.configurableProperties == null || this.configurableProperties.isEmpty())
            throw new MalformattedElementException("VDUCompute Node properties without configurableProperties");
        if (this.vduProfile == null)
            throw new MalformattedElementException("VDUCompute Node properties without vduProfile");
    }

}
