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
 * VnfPackageArtifactInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-05T11:50:31.473+02:00")

public class VnfPackageArtifactInfo {
    @JsonProperty("artifactPath")
    private String artifactPath = null;

    @JsonProperty("checksum")
    private String checksum = null;

    @JsonProperty("metadata")
    private KeyValuePairs metadata = null;

    public VnfPackageArtifactInfo artifactPath(String artifactPath) {
        this.artifactPath = artifactPath;
        return this;
    }

    /**
     * Get artifactPath
     *
     * @return artifactPath
     **/
    @ApiModelProperty(value = "")


    public String getArtifactPath() {
        return artifactPath;
    }

    public void setArtifactPath(String artifactPath) {
        this.artifactPath = artifactPath;
    }

    public VnfPackageArtifactInfo checksum(String checksum) {
        this.checksum = checksum;
        return this;
    }

    /**
     * Get checksum
     *
     * @return checksum
     **/
    @ApiModelProperty(value = "")


    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public VnfPackageArtifactInfo metadata(KeyValuePairs metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Get metadata
     *
     * @return metadata
     **/
    @ApiModelProperty(value = "")

    @Valid

    public KeyValuePairs getMetadata() {
        return metadata;
    }

    public void setMetadata(KeyValuePairs metadata) {
        this.metadata = metadata;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VnfPackageArtifactInfo vnfPackageArtifactInfo = (VnfPackageArtifactInfo) o;
        return Objects.equals(this.artifactPath, vnfPackageArtifactInfo.artifactPath) &&
                Objects.equals(this.checksum, vnfPackageArtifactInfo.checksum) &&
                Objects.equals(this.metadata, vnfPackageArtifactInfo.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artifactPath, checksum, metadata);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VnfPackageArtifactInfo {\n");

        sb.append("    artifactPath: ").append(toIndentedString(artifactPath)).append("\n");
        sb.append("    checksum: ").append(toIndentedString(checksum)).append("\n");
        sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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

