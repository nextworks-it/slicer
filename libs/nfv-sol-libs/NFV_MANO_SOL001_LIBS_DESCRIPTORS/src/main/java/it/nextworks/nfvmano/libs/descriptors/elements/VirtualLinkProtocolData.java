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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class VirtualLinkProtocolData implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JsonIgnore
    private VlProfile vlProfile;

    private String associatedLayerProtocol;

    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private L2ProtocolData l2ProtocolData;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vlProtocolData", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private L3ProtocolData l3ProtocolData;

    public VirtualLinkProtocolData() {

    }

    public VirtualLinkProtocolData(String associatedLayerProtocol, L2ProtocolData l2ProtocolData,
                                   L3ProtocolData l3ProtocolData) {
        this.associatedLayerProtocol = associatedLayerProtocol;
        this.l2ProtocolData = l2ProtocolData;
        this.l3ProtocolData = l3ProtocolData;
    }

    @JsonProperty("associatedLayerProtocol")
    public String getAssociatedLayerProtocol() {
        return associatedLayerProtocol;
    }

    @JsonProperty("l2ProtocolData")
    public L2ProtocolData getL2ProtocolData() {
        return l2ProtocolData;
    }

    @JsonProperty("l3ProtocolData")
    public L3ProtocolData getL3ProtocolData() {
        return l3ProtocolData;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.associatedLayerProtocol == null)
            throw new MalformattedElementException("VirtuaLinkProtocolData without associatedLayerProtocol");
    }

}
