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
package it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.osm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANO;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANOType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class OSM extends MANO {

    private String ipAddress;
    private String port;
    private String username;
    private String password;
    private String project;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<String> vimAccounts = new ArrayList<>();

    public OSM() {
        // JPA only
    }

    public OSM(String manoId, String ipAddress, String port, String username, String password, String project, MANOType manoType, String manoSite, List<String> vimAccounts) {
        super(manoId, manoType, manoSite);
        this.ipAddress = ipAddress;
        this.port = port;
        this.username = username;
        this.password = password;
        this.project = project;
        this.vimAccounts = vimAccounts;
    }

    @JsonProperty("ipAddress")
    public String getIpAddress() {
        return ipAddress;
    }

    @JsonProperty("port")
    public String getPort() { return port; }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("project")
    public String getProject() {
        return project;
    }

    @JsonProperty("vimAccounts")
    public List<String> getVimAccounts() {
        return vimAccounts;
    }

    public void setVimAccounts(List<String> vimAccounts) {
        this.vimAccounts = vimAccounts;
    }

    //TODO add NFVLiB dep
    public void isValid() throws MalformattedElementException {
        if (this.ipAddress == null)
            throw new MalformattedElementException("OSMMano without ipAddress");
        if (this.port == null)
            throw new MalformattedElementException("OSMMano without port");
        if (this.username == null)
            throw new MalformattedElementException("OSMMano without username");
        if (this.password == null)
            throw new MalformattedElementException("OSMMano without password");
    }
}
