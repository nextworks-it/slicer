/*
 * Copyright (c) 2021 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.oran.recordservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class OranNetworkSliceSubnetInstance {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private UUID nssiId;

    /**
     * Map that contains the mapping between a slice profile ID (key)
     * and the corresponding pair <PolicyTypeId, policy ID> (value) obtained from the translation
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER, targetClass= AbstractMap.SimpleEntry.class)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, Map.Entry<Integer,String>> sliceProfileToPolicy=new HashMap<>();

    public OranNetworkSliceSubnetInstance() {
    }

    public OranNetworkSliceSubnetInstance(UUID nssiId, Map<String, Map.Entry<Integer,String>> sliceProfileToPolicy, NssiStatus status) {
        this.nssiId = nssiId;
        this.sliceProfileToPolicy = sliceProfileToPolicy;
    }

    public UUID getNssiId() {
        return nssiId;
    }

    public void setNssiId(UUID nssiId) {
        this.nssiId = nssiId;
    }

    public Map<String, Map.Entry<Integer,String>> getSliceProfileToPolicy() {
        return sliceProfileToPolicy;
    }

    public void setSliceProfileToPolicy(Map<String, Map.Entry<Integer,String>> sliceProfileToPolicy) {
        this.sliceProfileToPolicy = sliceProfileToPolicy;
    }
}
