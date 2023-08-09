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
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LinkBitrateRequirements implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VlProfile vlProfile;

    @OneToOne
    @JsonIgnore
    private NsVlProfile nsVlProfile;

    @OneToOne
    @JsonIgnore
    private VirtualLinkBitrateLevel vlBitrateLevel;

    private Integer root;
    private int leaf;

    public LinkBitrateRequirements() {
        // JPA only
    }

    public LinkBitrateRequirements(Integer root, Integer leaf) {
        this.root = root;
        this.leaf = leaf;
    }

    public LinkBitrateRequirements(VlProfile vlProfile, Integer root, Integer leaf) {
        this.vlProfile = vlProfile;
        this.root = root;
        this.leaf = leaf;
    }

    public LinkBitrateRequirements(NsVlProfile nsVlProfile, Integer root, Integer leaf) {
        this.nsVlProfile = nsVlProfile;
        this.root = root;
        this.leaf = leaf;
    }

    public LinkBitrateRequirements(VirtualLinkBitrateLevel vlBitrateLevel, Integer root, Integer leaf) {
        this.vlBitrateLevel = vlBitrateLevel;
        this.root = root;
        this.leaf = leaf;
    }

    public Long getId() {
        return id;
    }

    public VlProfile getVlProfile() {
        return vlProfile;
    }

    public NsVlProfile getNsVlProfile() {
        return nsVlProfile;
    }

    public VirtualLinkBitrateLevel getVlBitrateLevel() {
        return vlBitrateLevel;
    }

    @JsonProperty("root")
    public Integer getRoot() {
        return root;
    }

    @JsonProperty("leaf")
    public Integer getLeaf() {
        return leaf;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.root == null)
            throw new MalformattedElementException("LinkBitrateRequirements without root");
    }
}
