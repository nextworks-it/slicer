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
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.SubscriptionAuthentication;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * PkgmSubscriptionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-05T11:50:31.473+02:00")

public class PkgmSubscriptionRequest {
    @JsonProperty("filter")
    private PkgmNotificationsFilter filter = null;

    @JsonProperty("callbackUri")
    private String callbackUri = null;

    @JsonProperty("authentication")
    private SubscriptionAuthentication authentication = null;

    public PkgmSubscriptionRequest filter(PkgmNotificationsFilter filter) {
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

    public PkgmSubscriptionRequest callbackUri(String callbackUri) {
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

    public PkgmSubscriptionRequest authentication(SubscriptionAuthentication authentication) {
        this.authentication = authentication;
        return this;
    }

    /**
     * Get authentication
     *
     * @return authentication
     **/
    @ApiModelProperty(value = "")

    @Valid

    public SubscriptionAuthentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(SubscriptionAuthentication authentication) {
        this.authentication = authentication;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PkgmSubscriptionRequest pkgmSubscriptionRequest = (PkgmSubscriptionRequest) o;
        return Objects.equals(this.filter, pkgmSubscriptionRequest.filter) &&
                Objects.equals(this.callbackUri, pkgmSubscriptionRequest.callbackUri) &&
                Objects.equals(this.authentication, pkgmSubscriptionRequest.authentication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter, callbackUri, authentication);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PkgmSubscriptionRequest {\n");

        sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
        sb.append("    callbackUri: ").append(toIndentedString(callbackUri)).append("\n");
        sb.append("    authentication: ").append(toIndentedString(authentication)).append("\n");
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

