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
package it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.PluginOperationalState;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.dummy.DummyMano;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.fivegrowth.FIVEGROWTH;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.onap.ONAP;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.osm.OSM;

import javax.persistence.*;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "manoType", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = OSM.class, name = "OSMR3"),
        @JsonSubTypes.Type(value = OSM.class, name = "OSMR4"),
        @JsonSubTypes.Type(value = OSM.class, name = "OSMR5"),
        @JsonSubTypes.Type(value = OSM.class, name = "OSMR6"),
        @JsonSubTypes.Type(value = OSM.class, name = "OSMR7"),
        @JsonSubTypes.Type(value = OSM.class, name = "OSMR8"),
        @JsonSubTypes.Type(value = OSM.class, name = "OSMR9"),
        @JsonSubTypes.Type(value = OSM.class, name = "OSMR10"),
        @JsonSubTypes.Type(value = ONAP.class, name = "ONAP"),
        @JsonSubTypes.Type(value = FIVEGROWTH.class, name = "SO_5GROWTH"),
        @JsonSubTypes.Type(value = DummyMano.class, name = "DUMMY")})
public abstract class MANO {

    @Id
    @GeneratedValue
    private Long id;

    private String manoId;
    private MANOType manoType;
    private String manoSite;
    private PluginOperationalState pluginOperationalState;

    /*
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Map<String, String> infoIdToPkgPath;
    */

    public MANO() {
        // JPA only
    }

    public MANO(String manoId) {
        this.manoId = manoId;
    }

    public MANO(String manoId, MANOType manoType) {
        this.manoId = manoId;
        this.manoType = manoType;
    }

    public MANO(String manoId, MANOType manoType, String manoSite) {
        this.manoId = manoId;
        this.manoType = manoType;
        this.manoSite = manoSite;
        //this.infoIdToPkgPath = new HashMap<>();
    }

    public MANO(String manoId, PluginOperationalState pluginOperationalState) {
        this.manoId = manoId;
        this.pluginOperationalState = pluginOperationalState;
        //this.infoIdToPkgPath = new HashMap<>();
    }

    public MANO(String manoId, MANOType manoType, PluginOperationalState pluginOperationalState) {
        this.manoId = manoId;
        this.manoType = manoType;
        this.pluginOperationalState = pluginOperationalState;
        //this.infoIdToPkgPath = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    @JsonProperty("manoId")
    public String getManoId() {
        return manoId;
    }

    @JsonProperty("manoType")
    public MANOType getManoType() {
        return manoType;
    }

    @JsonProperty("pluginOperationalState")
    public PluginOperationalState getPluginOperationalState() {
        return pluginOperationalState;
    }

    @JsonProperty("manoSite")
    public String getManoSite() {
        return manoSite;
    }

    public void setPluginOperationalState(PluginOperationalState pluginOperationalState) {
        this.pluginOperationalState = pluginOperationalState;
    }

    /*
    public Map<String, String> getInfoIdToPkgPath() {
        return infoIdToPkgPath;
    }

    public void setInfoIdToPkgPath(Map<String, String> infoIdToPkgPath) {
        this.infoIdToPkgPath = infoIdToPkgPath;
    }
     */
}
