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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

/**
 * This type represents a subscription related to notifications about NSD
 * management.
 */
@ApiModel(description = "This type represents a subscription related to notifications about NSD management.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-23T16:31:35.952+02:00")

public class NsdmSubscription {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("filter")
    private NsdmNotificationsFilter filter = null;

    @JsonProperty("callbackUri")
    private String callbackUri = null;

    @JsonProperty("_links")
    private NsdmLinksType links = null;

    public NsdmSubscription id(UUID id) {
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

    public NsdmSubscription filter(NsdmNotificationsFilter filter) {
        this.filter = filter;
        return this;
    }

    /**
     * This type represents a subscription filter related to notifications about NSD
     * management. It shall comply with the provisions defined in Table 5.5.3.2-1 of
     * GS NFV-SOL 005. At a particular nesting level in the filter structure, the
     * following applies: All attributes shall match in order for the filter to
     * match (logical \"and\" between different filter attributes). If an attribute
     * is an array, the attribute shall match if at least one of the values in the
     * array matches (logical \"or\" between the values of one filter attribute).
     *
     * @return filter
     **/
    @ApiModelProperty(value = "This type represents a subscription filter related to notifications about NSD management. It shall comply with the provisions defined in Table 5.5.3.2-1 of GS NFV-SOL 005. At a particular nesting level in the filter structure, the following applies: All attributes shall match in order for the filter to match (logical \"and\" between different filter attributes). If an attribute is an array, the attribute shall match if at least one of the values in the array matches (logical \"or\" between the values of one filter attribute).")

    @Valid

    public NsdmNotificationsFilter getFilter() {
        return filter;
    }

    public void setFilter(NsdmNotificationsFilter filter) {
        this.filter = filter;
    }

    public NsdmSubscription callbackUri(String callbackUri) {
        this.callbackUri = callbackUri;
        return this;
    }

    /**
     * Get callbackUri
     *
     * @return callbackUri
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    public String getCallbackUri() {
        return callbackUri;
    }

    public void setCallbackUri(String callbackUri) {
        this.callbackUri = callbackUri;
    }

    public NsdmSubscription links(NsdmLinksType links) {
        this.links = links;
        return this;
    }

    /**
     * Links to resources related to this resource.
     *
     * @return links
     **/
    @ApiModelProperty(required = true, value = "Links to resources related to this resource.")
    @NotNull

    @Valid

    public NsdmLinksType getLinks() {
        return links;
    }

    public void setLinks(NsdmLinksType links) {
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
        NsdmSubscription nsdmSubscription = (NsdmSubscription) o;
        return Objects.equals(this.id, nsdmSubscription.id) && Objects.equals(this.filter, nsdmSubscription.filter)
                && Objects.equals(this.callbackUri, nsdmSubscription.callbackUri)
                && Objects.equals(this.links, nsdmSubscription.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filter, callbackUri, links);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NsdmSubscription {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
        sb.append("    callbackUri: ").append(toIndentedString(callbackUri)).append("\n");
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
