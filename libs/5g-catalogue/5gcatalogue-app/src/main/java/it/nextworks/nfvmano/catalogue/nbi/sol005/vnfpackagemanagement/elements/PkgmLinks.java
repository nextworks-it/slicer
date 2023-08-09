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

import java.util.Objects;

/**
 * PkgmLinks
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-05T11:50:31.473+02:00")

public class PkgmLinks {
    @JsonProperty("vnfPackage")
    private String vnfPackage = null;

    @JsonProperty("subscription")
    private String subscription = null;

    public PkgmLinks vnfPackage(String vnfPackage) {
        this.vnfPackage = vnfPackage;
        return this;
    }

    /**
     * Get vnfPackage
     *
     * @return vnfPackage
     **/
    @ApiModelProperty(value = "")


    public String getVnfPackage() {
        return vnfPackage;
    }

    public void setVnfPackage(String vnfPackage) {
        this.vnfPackage = vnfPackage;
    }

    public PkgmLinks subscription(String subscription) {
        this.subscription = subscription;
        return this;
    }

    /**
     * Get subscription
     *
     * @return subscription
     **/
    @ApiModelProperty(value = "")


    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PkgmLinks pkgmLinks = (PkgmLinks) o;
        return Objects.equals(this.vnfPackage, pkgmLinks.vnfPackage) &&
                Objects.equals(this.subscription, pkgmLinks.subscription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vnfPackage, subscription);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PkgmLinks {\n");

        sb.append("    vnfPackage: ").append(toIndentedString(vnfPackage)).append("\n");
        sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
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

