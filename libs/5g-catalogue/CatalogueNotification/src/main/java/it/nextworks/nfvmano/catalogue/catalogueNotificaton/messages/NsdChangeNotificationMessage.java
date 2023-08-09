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
package it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.CatalogueMessageType;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.ScopeType;
import it.nextworks.nfvmano.libs.common.elements.KeyValuePair;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;


import java.util.*;

public class NsdChangeNotificationMessage extends CatalogueMessage {


    private final String nsdInfoId;
    private final String nsdId;
    private final String nsdVersion;
    private final KeyValuePair packagePath;
    private final String project;

    @JsonInclude(Include.NON_EMPTY)
    private List<String> siteOrManoIds = new ArrayList<>();

    @JsonInclude(Include.NON_NULL)
    private String pluginId;

    @JsonInclude(Include.NON_EMPTY)
    private Map<String, KeyValuePair> includedVnfds = new HashMap<>();

    @JsonInclude(Include.NON_EMPTY)
    private Map<String, KeyValuePair> includedPnfds = new HashMap<>();

    @JsonCreator
    public NsdChangeNotificationMessage(
            @JsonProperty("nsdInfoId") String nsdInfoId,
            @JsonProperty("nsdId") String nsdId,
            @JsonProperty("nsdVersion") String nsdVersion,
            @JsonProperty("project") String project,
            @JsonProperty("operationId") UUID operationId,
            @JsonProperty("scope") ScopeType scope,
            @JsonProperty("operationStatus") OperationStatus opStatus,
            @JsonProperty("pluginId") String pluginId,
            @JsonProperty("siteOrManoIds") List<String> siteOrManoIds,
            @JsonProperty("packagePath") KeyValuePair packagePath
    ) {
        super(CatalogueMessageType.NSD_CHANGE_NOTIFICATION, operationId, scope, opStatus);
        this.nsdInfoId = nsdInfoId;
        this.nsdId = nsdId;
        this.nsdVersion = nsdVersion;
        this.pluginId = pluginId;
        this.packagePath = packagePath;
        this.project = project;
        if(siteOrManoIds != null)
            this.siteOrManoIds.addAll(siteOrManoIds);
        else
            this.siteOrManoIds = null;
    }

    public NsdChangeNotificationMessage(
            @JsonProperty("nsdInfoId") String nsdInfoId,
            @JsonProperty("nsdId") String nsdId,
            @JsonProperty("nsdVersion") String nsdVersion,
            @JsonProperty("project") String project,
            @JsonProperty("operationId") UUID operationId,
            @JsonProperty("scope") ScopeType scope,
            @JsonProperty("operationStatus") OperationStatus opStatus,
            @JsonProperty("siteOrManoIds") List<String> siteOrManoIds,
            @JsonProperty("packagePath") KeyValuePair packagePath
    ) {
        super(CatalogueMessageType.NSD_CHANGE_NOTIFICATION, operationId, scope, opStatus);
        this.nsdInfoId = nsdInfoId;
        this.nsdId = nsdId;
        this.nsdVersion = nsdVersion;
        this.packagePath = packagePath;
        this.project = project;
        if(siteOrManoIds != null)
            this.siteOrManoIds.addAll(siteOrManoIds);
        else
            this.siteOrManoIds = null;
    }

    @JsonProperty("nsdInfoId")
    public String getNsdInfoId() {
        return nsdInfoId;
    }

    @JsonProperty("nsdId")
    public String getNsdId() {
        return nsdId;
    }

    @JsonProperty("nsdVersion")
    public String getNsdVersion() {
        return nsdVersion;
    }

    @JsonProperty("project")
    public String getProject() {
        return project;
    }

    @JsonProperty("pluginId")
    public String getPluginId() {
        return pluginId;
    }

    @JsonProperty("packagePath")
    public KeyValuePair getPackagePath() {
        return packagePath;
    }

    @JsonProperty("siteOrManoIds")
    public List<String> getSiteOrManoIds() {
        return siteOrManoIds;
    }

    public Map<String, KeyValuePair> getIncludedVnfds() {
        return includedVnfds;
    }

    public void setIncludedVnfds(Map<String, KeyValuePair> includedVnfds) {
        this.includedVnfds = includedVnfds;
    }

    public Map<String, KeyValuePair> getIncludedPnfds() {
        return includedPnfds;
    }

    public void setIncludedPnfds(Map<String, KeyValuePair> includedPnfds) {
        this.includedPnfds = includedPnfds;
    }
}
