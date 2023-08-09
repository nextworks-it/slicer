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
import it.nextworks.nfvmano.libs.descriptors.elements.VirtualBlockStorageData;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class VDUVirtualBlockStorageProperties implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VDUVirtualBlockStorageNode vduBlockStorageNode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "properties", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VirtualBlockStorageData virtualBlockStorageData;

    @Embedded
    private SwImageData swImageData;

    public VDUVirtualBlockStorageProperties() {

    }

    public VDUVirtualBlockStorageProperties(VirtualBlockStorageData virtualBlockStorageData, SwImageData swImageData) {
        this.virtualBlockStorageData = virtualBlockStorageData;
        this.swImageData = swImageData;
    }

    public VDUVirtualBlockStorageProperties(VDUVirtualBlockStorageNode vduBlockStorageNode,
                                            VirtualBlockStorageData virtualBlockStorageData, SwImageData swImageData) {
        this.vduBlockStorageNode = vduBlockStorageNode;
        this.virtualBlockStorageData = virtualBlockStorageData;
        this.swImageData = swImageData;
    }

    public Long getId() {
        return id;
    }

    public VDUVirtualBlockStorageNode getVduBlockStorageNode() {
        return vduBlockStorageNode;
    }

    @JsonProperty("virtualBlockStorageData")
    public VirtualBlockStorageData getVirtualBlockStorageData() {
        return virtualBlockStorageData;
    }

    @JsonProperty("swImageData")
    public SwImageData getSwImageData() {
        return swImageData;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.virtualBlockStorageData == null) throw new
                MalformattedElementException("VDUStorage Node properties without virtualBlockStorageData"
        );

    }

}
