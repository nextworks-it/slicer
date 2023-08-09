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
package it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

/**
 * PkmgSubscription
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-04T17:03:57.388+02:00")

public class PkmgSubscription {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("filter")
    private PkgmNotificationsFilter filter = null;

    @JsonProperty("callbackUri")
    private String callbackUri = null;

    @JsonProperty("_links")
    private String links = null;

    @JsonProperty("self")
    private String self = null;

    public PkmgSubscription id(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")

    @Valid

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PkmgSubscription filter(PkgmNotificationsFilter filter) {
        this.filter = filter;
        return this;
    }

    /**
     * Get filter
     *
     * @return filter
     **/
    @ApiModelProperty(value = "")

    @Valid

    public PkgmNotificationsFilter getFilter() {
        return filter;
    }

    public void setFilter(PkgmNotificationsFilter filter) {
        this.filter = filter;
    }

    public PkmgSubscription callbackUri(String callbackUri) {
        this.callbackUri = callbackUri;
        return this;
    }

    /**
     * Get callbackUri
     *
     * @return callbackUri
     **/
    @ApiModelProperty(value = "")


    public String getCallbackUri() {
        return callbackUri;
    }

    public void setCallbackUri(String callbackUri) {
        this.callbackUri = callbackUri;
    }

    public PkmgSubscription links(String links) {
        this.links = links;
        return this;
    }

    /**
     * Get links
     *
     * @return links
     **/
    @ApiModelProperty(value = "")


    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public PkmgSubscription self(String self) {
        this.self = self;
        return this;
    }

    /**
     * Get self
     *
     * @return self
     **/
    @ApiModelProperty(value = "")


    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PkmgSubscription pkmgSubscription = (PkmgSubscription) o;
        return Objects.equals(this.id, pkmgSubscription.id) &&
                Objects.equals(this.filter, pkmgSubscription.filter) &&
                Objects.equals(this.callbackUri, pkmgSubscription.callbackUri) &&
                Objects.equals(this.links, pkmgSubscription.links) &&
                Objects.equals(this.self, pkmgSubscription.self);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filter, callbackUri, links, self);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PkmgSubscription {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
        sb.append("    callbackUri: ").append(toIndentedString(callbackUri)).append("\n");
        sb.append("    links: ").append(toIndentedString(links)).append("\n");
        sb.append("    self: ").append(toIndentedString(self)).append("\n");
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

