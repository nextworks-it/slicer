/*
 * Copyright (c) 2021 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.libs.ifa.templates.nst;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class NsInfo {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Column(name="nsinstance_id")
    private String nSInstanceId;
    private String nsName;
    private String description;

    public NsInfo(NsInfo nsInfo){
        if(nsInfo!=null){
            nsInfo.setnSInstanceId(nsInfo.getnSInstanceId());
            nsInfo.setDescription(nsInfo.getDescription());
            nsInfo.setNsName(nsInfo.getNsName());
        }
    }
    public NsInfo(){}

    public NsInfo(String nSInstanceId){
        this.nSInstanceId=nSInstanceId;
    }

    public void setnSInstanceId(String nSInstanceId) {
        this.nSInstanceId = nSInstanceId;
    }

    public String getnSInstanceId() {
        return nSInstanceId;
    }

    public void setNsName(String nsName) {
        this.nsName = nsName;
    }

    public String getNsName() {
        return nsName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
