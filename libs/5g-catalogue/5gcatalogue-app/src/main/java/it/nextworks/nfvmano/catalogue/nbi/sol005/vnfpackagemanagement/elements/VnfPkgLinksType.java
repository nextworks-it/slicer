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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Links to resources related to this resource.
 */
@ApiModel(description = "Links to resources related to this resource.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-29T11:50:43.237+01:00")

public class VnfPkgLinksType {
    @JsonProperty("self")
    private String self = null;

    @JsonProperty("vnfd")
    private String vnfd = null;

    @JsonProperty("packageContent")
    private String packageContent = null;

    public VnfPkgLinksType self(String self) {
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

    public VnfPkgLinksType vnfd(String vnfd) {
        this.vnfd = vnfd;
        return this;
    }

    /**
     * Get vnfd
     *
     * @return vnfd
     **/
    @ApiModelProperty(value = "")


    public String getVnfd() {
        return vnfd;
    }

    public void setVnfd(String vnfd) {
        this.vnfd = vnfd;
    }

    public VnfPkgLinksType packageContent(String packageContent) {
        this.packageContent = packageContent;
        return this;
    }

    /**
     * Get packageContent
     *
     * @return packageContent
     **/
    @ApiModelProperty(value = "")


    public String getPackageContent() {
        return packageContent;
    }

    public void setPackageContent(String packageContent) {
        this.packageContent = packageContent;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VnfPkgLinksType vnfPkgLinksType = (VnfPkgLinksType) o;
        return Objects.equals(this.self, vnfPkgLinksType.self) &&
                Objects.equals(this.vnfd, vnfPkgLinksType.vnfd) &&
                Objects.equals(this.packageContent, vnfPkgLinksType.packageContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(self, vnfd, packageContent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VnfPkgLinksType {\n");

        sb.append("    self: ").append(toIndentedString(self)).append("\n");
        sb.append("    vnfd: ").append(toIndentedString(vnfd)).append("\n");
        sb.append("    packageContent: ").append(toIndentedString(packageContent)).append("\n");
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

