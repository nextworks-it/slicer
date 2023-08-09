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

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.CatalogueMessageType;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.ScopeType;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "msgType")
@JsonSubTypes({
        @Type(
                value = NsdOnBoardingNotificationMessage.class,
                name = "NSD_ONBOARDING_NOTIFICATION"
        ),
        @Type(
                value = NsdChangeNotificationMessage.class,
                name = "NSD_CHANGE_NOTIFICATION"
        ),
        @Type(
                value = NsdDeletionNotificationMessage.class,
                name = "NSD_DELETION_NOTIFICATION"
        ),
        @Type(
                value = PnfdOnBoardingNotificationMessage.class,
                name = "PNFD_ONBOARDING_NOTIFICATION"
        ),
        @Type(
                value = PnfdDeletionNotificationMessage.class,
                name = "PNFD_DELETION_NOTIFICATION"
        ),
        @Type(
                value = VnfPkgOnBoardingNotificationMessage.class,
                name = "VNFPKG_ONBOARDING_NOTIFICATION"
        ),
        @Type(
                value = VnfPkgChangeNotificationMessage.class,
                name = "VNFPKG_CHANGE_NOTIFICATION"
        ),
        @Type(
                value = VnfPkgDeletionNotificationMessage.class,
                name = "VNFPKG_DELETION_NOTIFICATION"
        ),
        @Type(
                value = VnfImgUploadingNotificationMessage.class,
                name = "VNFIMG_UPLOADING_NOTIFICATION"
        ),
        @Type(
                value = VnfImgChangeNotificationMessage.class,
                name = "VNFIMG_CHANGE_NOTIFICATION"
        ),
        @Type(
                value = VnfImgDeletionNotificationMessage.class,
                name = "VNFIMG_DELETION_NOTIFICATION"
        )
})

public class CatalogueMessage {

    private CatalogueMessageType type;
    private UUID operationId;
    private ScopeType scope;
    private OperationStatus opStatus;

    @JsonCreator
    public CatalogueMessage(
            @JsonProperty("type") CatalogueMessageType type,
            @JsonProperty("operationId") UUID operationId,
            @JsonProperty("scope") ScopeType scope,
            @JsonProperty("opStatus") OperationStatus opStatus
    ) {
        this.type = type;
        this.operationId = operationId;
        this.scope = scope;
        this.opStatus = opStatus;
    }

    @JsonIgnore
    public CatalogueMessageType getType() {
        return type;
    }

    @JsonProperty("operationId")
    public UUID getOperationId() {
        return operationId;
    }

    @JsonProperty("scope")
    public ScopeType getScope() {
        return scope;
    }

    @JsonProperty("opStatus")
    public OperationStatus getOpStatus() {
        return opStatus;
    }
}
