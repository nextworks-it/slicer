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


package it.nextworks.nfvmano.libs.ifa.templates.gst;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class AreaOfService {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    private String areaOfService;
    @ElementCollection(
            targetClass = String.class
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> regionSpecification = new ArrayList();

    public AreaOfService() {
    }

    public AreaOfService(String areaOfService, List<String> regionSpecification) {
        this.areaOfService = areaOfService;
        this.regionSpecification = regionSpecification;
    }

    public void isValid() throws MalformattedElementException {
        if (this.areaOfService != null && this.regionSpecification == null) {
            throw new MalformattedElementException("regionSpecification argoument required");
        } else if (this.areaOfService == null && this.regionSpecification != null) {
            throw new MalformattedElementException("Non-valid regionSpecification argoument");
        }
    }

    public void setAreaOfService(String areaOfService) {
        this.areaOfService = areaOfService;
    }

    public String getAreaOfService() {
        return this.areaOfService;
    }

    public void setRegionSpecification(List<String> regionSpecification) {
        this.regionSpecification = regionSpecification;
    }

    public List<String> getRegionSpecification() {
        return this.regionSpecification;
    }
}
