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

package it.nextworks.nfvmano.nssmf.record.im.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiErrors;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class NssiBaseRecord {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private UUID nssiId;
    private NssiStatus status;
    private UUID configId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch=FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<NssiErrors, String> errors = new HashMap<NssiErrors, String>();

    @JsonIgnore
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createDate;

    @JsonIgnore
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date modifyDate;

    public NssiBaseRecord() {
    }

    public NssiBaseRecord(UUID nssiId, NssiStatus status) {
        this.nssiId = nssiId;
        this.status = status;
    }

    public NssiBaseRecord(UUID nssiId, NssiStatus status, UUID configId) {
        this.nssiId = nssiId;
        this.status = status;
        this.configId = configId;
    }

    public UUID getNssiId() {
        return nssiId;
    }

    public void setNssiId(UUID nssiId) {
        this.nssiId = nssiId;
    }

    public NssiStatus getStatus() {
        return status;
    }

    public void setStatus(NssiStatus status) {
        this.status = status;
    }

    public UUID getConfigId() {
        return configId;
    }

    public void setConfigId(UUID configId) {
        this.configId = configId;
    }

    public Map<NssiErrors, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<NssiErrors, String> errors) {
        this.errors = errors;
    }
}
