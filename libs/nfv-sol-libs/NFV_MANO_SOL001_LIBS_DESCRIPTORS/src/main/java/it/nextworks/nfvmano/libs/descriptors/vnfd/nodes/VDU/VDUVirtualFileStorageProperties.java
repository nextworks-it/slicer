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
import it.nextworks.nfvmano.libs.descriptors.elements.VirtualFileStorageData;

import javax.persistence.*;

@Entity
public class VDUVirtualFileStorageProperties implements DescriptorInformationElement {


    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VDUVirtualFileStorageNode vduStorageNode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Embedded
    private VirtualFileStorageData virtualFileStorageData;

    public VDUVirtualFileStorageProperties() {

    }

    public VDUVirtualFileStorageProperties(VDUVirtualFileStorageNode vduStorageNode,
                                           VirtualFileStorageData virtualFileStorageData) {
        this.vduStorageNode = vduStorageNode;
        this.virtualFileStorageData = virtualFileStorageData;
    }

    public Long getId() {
        return id;
    }

    public VDUVirtualFileStorageNode getVduStorageNode() {
        return vduStorageNode;
    }

    @JsonProperty("virtualFileStorageData")
    public VirtualFileStorageData getVirtualFileStorageData() {
        return virtualFileStorageData;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.virtualFileStorageData == null) throw new
                MalformattedElementException("VDUVirtualFileStorageNode Node properties without virtualFileStorageData");

    }

}
