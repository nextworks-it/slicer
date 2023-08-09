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
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NsdInfo {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String nsdId;
    private String nsdName;
    private String nsdDescription;
    private String nsdVersion;

    public NsdInfo(){

    }

    public NsdInfo(String nsdId, String nsdName, String nsdDescription, String nsdVersion){
        this.nsdId=nsdId;
        this.nsdDescription=nsdDescription;
        this.nsdName=nsdName;
        this.nsdVersion=nsdVersion;
    }

    public NsdInfo(NsdInfo nsdInfo){
        this.nsdId=nsdInfo.nsdId;
        this.nsdDescription=nsdInfo.nsdDescription;
        this.nsdName=nsdInfo.nsdName;
        this.nsdVersion=nsdInfo.nsdVersion;
    }

    public void setNsdId(String nsdId) {
        this.nsdId = nsdId;
    }

    public String getNsdId() {
        return nsdId;
    }

    public void setNsdName(String nsdName) {
        this.nsdName = nsdName;
    }

    public String getNsdName() {
        return nsdName;
    }

    public void setNsdDescription(String nsdDescription) {
        this.nsdDescription = nsdDescription;
    }

    public String getNsdDescription() {
        return nsdDescription;
    }

    public void setNsdVersion(String nsdVersion) {
        this.nsdVersion = nsdVersion;
    }

    public String getNsdVersion() {
        return nsdVersion;
    }

    public void isValid() throws MalformattedElementException{
        if(nsdId==null)
            throw new MalformattedElementException("nsdId is required");
    }
}
