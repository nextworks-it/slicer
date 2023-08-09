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
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VnfPkgDeletionNotificationMessage extends CatalogueMessage {

    private final String vnfPkgInfoId;
    private final String vnfdId;
    private final String vnfdVersion;
    private final String project;

    private String vnfName;

    @JsonInclude(Include.NON_EMPTY)
    private List<String> siteOrManoIds = new ArrayList<>();

    @JsonInclude(Include.NON_NULL)
    private String pluginId;

    @JsonCreator
    public VnfPkgDeletionNotificationMessage(
            @JsonProperty("vnfPkgInfoId") String vnfPkgInfoId,
            @JsonProperty("vnfdId") String vnfdId,
            @JsonProperty("vnfdVersion") String vnfdVersion,
            @JsonProperty("project") String project,
            @JsonProperty("operationId") UUID operationId,
            @JsonProperty("scope") ScopeType scope,
            @JsonProperty("operationStatus") OperationStatus opStatus,
            @JsonProperty("pluginId") String pluginId,
            @JsonProperty("siteOrManoIds") List<String> siteOrManoIds
    ) {
        super(CatalogueMessageType.VNFPKG_DELETION_NOTIFICATION, operationId, scope, opStatus);
        this.vnfPkgInfoId = vnfPkgInfoId;
        this.vnfdId = vnfdId;
        this.vnfdVersion = vnfdVersion;
        this.pluginId = pluginId;
        this.project = project;
        if(siteOrManoIds != null)
            this.siteOrManoIds.addAll(siteOrManoIds);
        else
            this.siteOrManoIds = null;
    }

    public VnfPkgDeletionNotificationMessage(
            @JsonProperty("vnfPkgInfoId") String vnfPkgInfoId,
            @JsonProperty("vnfdId") String vnfdId,
            @JsonProperty("vnfdVersion") String vnfdVersion,
            @JsonProperty("project") String project,
            @JsonProperty("operationId") UUID operationId,
            @JsonProperty("scope") ScopeType scope,
            @JsonProperty("operationStatus") OperationStatus opStatus,
            @JsonProperty("siteOrManoIds") List<String> siteOrManoIds
    ) {
        super(CatalogueMessageType.VNFPKG_DELETION_NOTIFICATION, operationId, scope, opStatus);
        this.vnfPkgInfoId = vnfPkgInfoId;
        this.vnfdId = vnfdId;
        this.vnfdVersion = vnfdVersion;
        this.project = project;
        if(siteOrManoIds != null)
            this.siteOrManoIds.addAll(siteOrManoIds);
        else
            this.siteOrManoIds = null;
    }

    @JsonProperty("vnfPkgInfoId")
    public String getVnfPkgInfoId() {
        return vnfPkgInfoId;
    }

    @JsonProperty("vnfdId")
    public String getVnfdId() {
        return vnfdId;
    }

    @JsonProperty("vnfdVersion")
    public String getVnfdVersion() {
        return vnfdVersion;
    }

    @JsonProperty("pluginId")
    public String getPluginId() {
        return pluginId;
    }

    @JsonProperty("vnfName")
    public String getVnfName() {
        return vnfName;
    }

    public void setVnfName(String vnfName) {
        this.vnfName = vnfName;
    }

    @JsonProperty("project")
    public String getProject() {
        return project;
    }

    @JsonProperty("siteOrManoIds")
    public List<String> getSiteOrManoIds() {
        return siteOrManoIds;
    }
}
