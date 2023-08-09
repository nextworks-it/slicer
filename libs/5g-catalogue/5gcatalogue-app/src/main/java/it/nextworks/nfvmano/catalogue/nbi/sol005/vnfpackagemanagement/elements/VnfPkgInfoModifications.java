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
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.KeyValuePairs;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * VnfPkgInfoModifications
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-05T11:50:31.473+02:00")

public class VnfPkgInfoModifications {
    @JsonProperty("userDefinedData")
    private KeyValuePairs userDefinedData = null;

    @JsonProperty("operationalState")
    private PackageOperationalStateType operationalState = null;

    public VnfPkgInfoModifications userDefinedData(KeyValuePairs userDefinedData) {
        this.userDefinedData = userDefinedData;
        return this;
    }

    /**
     * Get userDefinedData
     *
     * @return userDefinedData
     **/
    @ApiModelProperty(value = "")

    @Valid

    public KeyValuePairs getUserDefinedData() {
        return userDefinedData;
    }

    public void setUserDefinedData(KeyValuePairs userDefinedData) {
        this.userDefinedData = userDefinedData;
    }

    public VnfPkgInfoModifications operationalState(PackageOperationalStateType operationalState) {
        this.operationalState = operationalState;
        return this;
    }

    /**
     * Get operationalState
     *
     * @return operationalState
     **/
    @ApiModelProperty(value = "")

    @Valid

    public PackageOperationalStateType getOperationalState() {
        return operationalState;
    }

    public void setOperationalState(PackageOperationalStateType operationalState) {
        this.operationalState = operationalState;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VnfPkgInfoModifications vnfPkgInfoModifications = (VnfPkgInfoModifications) o;
        return Objects.equals(this.userDefinedData, vnfPkgInfoModifications.userDefinedData) &&
                Objects.equals(this.operationalState, vnfPkgInfoModifications.operationalState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDefinedData, operationalState);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VnfPkgInfoModifications {\n");

        sb.append("    userDefinedData: ").append(toIndentedString(userDefinedData)).append("\n");
        sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
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

