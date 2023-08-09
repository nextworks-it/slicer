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
package it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

/**
 * This type represents an NSD management notification, which informs the
 * receiver of the successful on-boarding of an NSD. It shall comply with the
 * provisions defined in Table 5.5.2.9-1. The support of this notification is
 * mandatory. The notification shall be triggered by the NFVO when the
 * \&quot;nsdOnboardingState\&quot; attribute of a new NSD has changed to
 * \&quot;ONBOARDED\&quot;.
 */
@ApiModel(description = "This type represents an NSD management notification, which informs the receiver of the successful on-boarding of an NSD. It shall comply with the provisions defined in Table 5.5.2.9-1. The support of this notification is mandatory. The notification shall be triggered by the NFVO when the \"nsdOnboardingState\" attribute of a new NSD has changed to \"ONBOARDED\".")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-23T16:31:35.952+02:00")

public class NsdOnboardingNotification {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("notificationType")
    private NotificationType notificationType = null;

    @JsonProperty("subscriptionId")
    private UUID subscriptionId = null;

    @JsonProperty("timeStamp")
    private OffsetDateTime timeStamp = null;

    @JsonProperty("nsdInfoId")
    private UUID nsdInfoId = null;

    @JsonProperty("nsdId")
    private UUID nsdId = null;

    @JsonProperty("_links")
    private NsdmLinks links = null;

    public NsdOnboardingNotification id(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public NsdOnboardingNotification notificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
        return this;
    }

    /**
     * Discriminator for the different notification types. Shall be set to
     * \"NsdOnboardingNotification\" for this notification type.
     *
     * @return notificationType
     **/
    @ApiModelProperty(required = true, value = "Discriminator for the different notification types. Shall be set to \"NsdOnboardingNotification\" for this notification type.")
    @NotNull

    @Valid

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public NsdOnboardingNotification subscriptionId(UUID subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /**
     * Get subscriptionId
     *
     * @return subscriptionId
     **/
    @ApiModelProperty(value = "")

    @Valid

    public UUID getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(UUID subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public NsdOnboardingNotification timeStamp(OffsetDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    /**
     * Date-time of the generation of the notification.
     *
     * @return timeStamp
     **/
    @ApiModelProperty(required = true, value = "Date-time of the generation of the notification.")
    @NotNull

    @Valid

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(OffsetDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public NsdOnboardingNotification nsdInfoId(UUID nsdInfoId) {
        this.nsdInfoId = nsdInfoId;
        return this;
    }

    /**
     * Get nsdInfoId
     *
     * @return nsdInfoId
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public UUID getNsdInfoId() {
        return nsdInfoId;
    }

    public void setNsdInfoId(UUID nsdInfoId) {
        this.nsdInfoId = nsdInfoId;
    }

    public NsdOnboardingNotification nsdId(UUID nsdId) {
        this.nsdId = nsdId;
        return this;
    }

    /**
     * Get nsdId
     *
     * @return nsdId
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public UUID getNsdId() {
        return nsdId;
    }

    public void setNsdId(UUID nsdId) {
        this.nsdId = nsdId;
    }

    public NsdOnboardingNotification links(NsdmLinks links) {
        this.links = links;
        return this;
    }

    /**
     * This type represents the links to resources that an NSD management
     * notification can contain.
     *
     * @return links
     **/
    @ApiModelProperty(required = true, value = "This type represents the links to resources that an NSD management notification can contain.")
    @NotNull

    @Valid

    public NsdmLinks getLinks() {
        return links;
    }

    public void setLinks(NsdmLinks links) {
        this.links = links;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NsdOnboardingNotification nsdOnboardingNotification = (NsdOnboardingNotification) o;
        return Objects.equals(this.id, nsdOnboardingNotification.id)
                && Objects.equals(this.notificationType, nsdOnboardingNotification.notificationType)
                && Objects.equals(this.subscriptionId, nsdOnboardingNotification.subscriptionId)
                && Objects.equals(this.timeStamp, nsdOnboardingNotification.timeStamp)
                && Objects.equals(this.nsdInfoId, nsdOnboardingNotification.nsdInfoId)
                && Objects.equals(this.nsdId, nsdOnboardingNotification.nsdId)
                && Objects.equals(this.links, nsdOnboardingNotification.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, notificationType, subscriptionId, timeStamp, nsdInfoId, nsdId, links);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NsdOnboardingNotification {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    notificationType: ").append(toIndentedString(notificationType)).append("\n");
        sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
        sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
        sb.append("    nsdInfoId: ").append(toIndentedString(nsdInfoId)).append("\n");
        sb.append("    nsdId: ").append(toIndentedString(nsdId)).append("\n");
        sb.append("    links: ").append(toIndentedString(links)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
