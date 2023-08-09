package it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.CatalogueMessageType;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.ScopeType;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;

import java.util.UUID;

public class VnfImgUploadingNotificationMessage extends CatalogueMessage {

    private final String vnfPkgInfoId;
    private final String vnfdId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pluginId;

    @JsonCreator
    public VnfImgUploadingNotificationMessage(
            @JsonProperty("vnfPkgInfoId") String vnfPkgInfoId,
            @JsonProperty("vnfdId") String vnfdId,
            @JsonProperty("operationId") UUID operationId,
            @JsonProperty("scope") ScopeType scope,
            @JsonProperty("operationStatus") OperationStatus opStatus,
            @JsonProperty("pluginId") String pluginId
    ) {
        super(CatalogueMessageType.VNFIMG_UPLOADING_NOTIFICATION, operationId, scope, opStatus);
        this.vnfPkgInfoId = vnfPkgInfoId;
        this.vnfdId = vnfdId;
        this.pluginId = pluginId;
    }

    public VnfImgUploadingNotificationMessage(
            @JsonProperty("vnfPkgInfoId") String vnfPkgInfoId,
            @JsonProperty("vnfdId") String vnfdId,
            @JsonProperty("operationId") UUID operationId,
            @JsonProperty("scope") ScopeType scope,
            @JsonProperty("operationStatus") OperationStatus opStatus
    ) {
        super(CatalogueMessageType.VNFIMG_UPLOADING_NOTIFICATION, operationId, scope, opStatus);
        this.vnfPkgInfoId = vnfPkgInfoId;
        this.vnfdId = vnfdId;
        this.pluginId = null;
    }

    public String getVnfPkgInfoId() {
        return vnfPkgInfoId;
    }

    public String getVnfdId() {
        return vnfdId;
    }

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }
}
