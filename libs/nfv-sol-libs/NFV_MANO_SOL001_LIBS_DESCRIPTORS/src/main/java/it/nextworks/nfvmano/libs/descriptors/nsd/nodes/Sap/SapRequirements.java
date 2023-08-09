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
package it.nextworks.nfvmano.libs.descriptors.nsd.nodes.Sap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SapRequirements implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private SapNode sapNode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> internalVirtualLink = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> externalVirtualLink = new ArrayList<>();

    public SapRequirements() {
    }

    public SapRequirements(List<String> internalVirtualLink, List<String> externalVirtualLink) {
        this.internalVirtualLink = internalVirtualLink;
        this.externalVirtualLink = externalVirtualLink;
    }

    public SapRequirements(SapNode sapNode, List<String> internalVirtualLink, List<String> externalVirtualLink) {
        this.sapNode = sapNode;
        this.internalVirtualLink = internalVirtualLink;
        this.externalVirtualLink = externalVirtualLink;
    }

    public Long getId() {
        return id;
    }

    public SapNode getSapNode() {
        return sapNode;
    }

    @JsonProperty("internalVirtualLink")
    public List<String> getInternalVirtualLink() {
        return internalVirtualLink;
    }

    @JsonProperty("externalVirtualLink")
    public List<String> getExternalVirtualLink() {
        return externalVirtualLink;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.internalVirtualLink == null || this.internalVirtualLink.isEmpty())
            throw new MalformattedElementException("SAP Node requirements without internalVirtualLink");
    }
}
