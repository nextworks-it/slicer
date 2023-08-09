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
 * UploadVnfPackageFromUriRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-05T11:50:31.473+02:00")

public class UploadVnfPackageFromUriRequest {
    @JsonProperty("addressInformation")
    private String addressInformation = null;

    @JsonProperty("userName")
    private String userName = null;

    @JsonProperty("password")
    private String password = null;

    public UploadVnfPackageFromUriRequest addressInformation(String addressInformation) {
        this.addressInformation = addressInformation;
        return this;
    }

    /**
     * Get addressInformation
     *
     * @return addressInformation
     **/
    @ApiModelProperty(value = "")


    public String getAddressInformation() {
        return addressInformation;
    }

    public void setAddressInformation(String addressInformation) {
        this.addressInformation = addressInformation;
    }

    public UploadVnfPackageFromUriRequest userName(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * Get userName
     *
     * @return userName
     **/
    @ApiModelProperty(value = "")


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UploadVnfPackageFromUriRequest password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Get password
     *
     * @return password
     **/
    @ApiModelProperty(value = "")


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UploadVnfPackageFromUriRequest uploadVnfPackageFromUriRequest = (UploadVnfPackageFromUriRequest) o;
        return Objects.equals(this.addressInformation, uploadVnfPackageFromUriRequest.addressInformation) &&
                Objects.equals(this.userName, uploadVnfPackageFromUriRequest.userName) &&
                Objects.equals(this.password, uploadVnfPackageFromUriRequest.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressInformation, userName, password);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UploadVnfPackageFromUriRequest {\n");

        sb.append("    addressInformation: ").append(toIndentedString(addressInformation)).append("\n");
        sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

