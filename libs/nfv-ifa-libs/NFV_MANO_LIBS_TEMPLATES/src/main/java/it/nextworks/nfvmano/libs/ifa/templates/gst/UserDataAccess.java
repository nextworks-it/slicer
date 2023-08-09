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

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

@Entity
public class UserDataAccess {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private DataAccessValue dataAccess;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ThunnelingMechanism> thunnelingMechanisms;

    public UserDataAccess(){}

    public void setDataAccess(DataAccessValue dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void setThunnelingMechanisms(List<ThunnelingMechanism> thunnelingMechanisms) {
        this.thunnelingMechanisms = thunnelingMechanisms;
    }

    public void isValid() throws MalformattedElementException {
        if(this.dataAccess==DataAccessValue.TerminationInPrivateNetwork && this.thunnelingMechanisms==null)
            throw new MalformattedElementException("Thunneling mechanisms value required");
    }

    public DataAccessValue getDataAccess() {
        return dataAccess;
    }

    public List<ThunnelingMechanism> getThunnelingMechanisms() {
        return thunnelingMechanisms;
    }
}


enum ThunnelingMechanism{
    L2TPTunnel,
    GRETunnel,
    VPNTunnel,
    LabelBasesRouting,
    Other
}
