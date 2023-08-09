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
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU.VDUVirtualBlockStorageProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class VirtualBlockStorageData implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VDUVirtualBlockStorageProperties properties;

    private Integer sizeOfStorage;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, String> vduStorageRequirements = new HashMap<>();

    private boolean rdmaEnabled;

    public VirtualBlockStorageData() {
    }

    public VirtualBlockStorageData(Integer sizeOfStorage, Map<String, String> vduStorageRequirements, boolean rdmaEnabled) {
        this.sizeOfStorage = sizeOfStorage;
        this.vduStorageRequirements = vduStorageRequirements;
        this.rdmaEnabled = rdmaEnabled;
    }

    public VirtualBlockStorageData(VDUVirtualBlockStorageProperties properties, Integer sizeOfStorage, Map<String, String> vduStorageRequirements, boolean rdmaEnabled) {
        this.properties = properties;
        this.sizeOfStorage = sizeOfStorage;
        this.vduStorageRequirements = vduStorageRequirements;
        this.rdmaEnabled = rdmaEnabled;
    }

    public Long getId() {
        return id;
    }

    public VDUVirtualBlockStorageProperties getProperties() {
        return properties;
    }

    @JsonProperty("sizeOfStorage")
    public Integer getSizeOfStorage() {
        return sizeOfStorage;
    }

    @JsonProperty("vduStorageRequirements")
    public Map<String, String> getVduStorageRequirements() {
        return vduStorageRequirements;
    }

    @JsonProperty("rdmaEnabled")
    public boolean isRdmaEnabled() {
        return rdmaEnabled;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.sizeOfStorage == null)
            throw new MalformattedElementException("VirtualBlockStorageData without sizeOfStorage");
    }
}