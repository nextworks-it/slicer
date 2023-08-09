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
package it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class NSRequirements implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private NSNode nsNode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, String> virtualLink = new HashMap<>();

    public NSRequirements() {

    }

    public NSRequirements(Map<String,String>  virtualLink) {
        this.virtualLink = virtualLink;
    }

    public NSRequirements(NSNode nsNode, Map<String,String>  virtualLink) {
        this.nsNode = nsNode;
        this.virtualLink = virtualLink;
    }

    public Long getId() {
        return id;
    }

    public NSNode getNsNode() {
        return nsNode;
    }

    @JsonProperty("virtualLink")
    public Map<String,String>  getVirtualLink() {
        return virtualLink;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.virtualLink == null || this.virtualLink.isEmpty())
            throw new MalformattedElementException("NS Node requirements without virtualLink");
    }
}
