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
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

import javax.persistence.*;

@Entity
public class Isolation {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.STRING)
    private IsolationValue isolationLevel=IsolationValue.NoIsolation;
    @Enumerated(EnumType.STRING)
    private PhyIsoValue physicalIsolation;
    @Enumerated(EnumType.STRING)
    private LogIsoValue logicalIsolation;

    public Isolation() {}

    public Isolation(IsolationValue isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public void setIsolationLevel(IsolationValue isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public void setPhysicalIsolation(PhyIsoValue physicalIsolation) {
        this.physicalIsolation = physicalIsolation;
    }

    public void setLogicalIsolation(LogIsoValue logicalIsolation) {
        this.logicalIsolation = logicalIsolation;
    }

    public void isValid() throws MalformattedElementException {
        if ((this.isolationLevel == IsolationValue.Logical && this.physicalIsolation != null) ||
                (this.isolationLevel == IsolationValue.Physical && this.logicalIsolation != null))
            throw new MalformattedElementException("Invalid isolation method");

        if ((this.isolationLevel == IsolationValue.Logical && this.logicalIsolation == null) || (
                this.isolationLevel == IsolationValue.Physical && this.physicalIsolation == null))
            throw new MalformattedElementException("Value of isolation level is required");
    }

    public IsolationValue getIsolationLevel() {
        return this.isolationLevel;
    }

    public PhyIsoValue getPhysicalIsolation() {
        return this.physicalIsolation;
    }

    public LogIsoValue getLogicalIsolation() {
        return this.logicalIsolation;
    }
}