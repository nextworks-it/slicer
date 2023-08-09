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
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.C2COnboardingStateType;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * This type represents a response for the query PNFD operation.
 */
@ApiModel(description = "This type represents a response for the query PNFD operation.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-23T16:31:35.952+02:00")

public class PnfdInfo {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("pnfdId")
    private UUID pnfdId = null;

    @JsonProperty("pnfdName")
    private String pnfdName = null;

    @JsonProperty("pnfdVersion")
    private String pnfdVersion = null;

    @JsonProperty("pnfdProvider")
    private String pnfdProvider = null;

    @JsonProperty("pnfdInvariantId")
    private UUID pnfdInvariantId = null;

    @JsonProperty("pnfdOnboardingState")
    private PnfdOnboardingStateType pnfdOnboardingState = null;

    @JsonProperty("onboardingFailureDetails")
    private ProblemDetails onboardingFailureDetails = null;

    @JsonProperty("pnfdUsageState")
    private PnfdUsageStateType pnfdUsageState = null;

    @JsonProperty("userDefinedData")
    private KeyValuePairs userDefinedData = null;

    @JsonProperty("_links")
    private PnfdLinksType links = null;

    @JsonProperty("manosOnboardingStatus")
    private Map<String, PnfdOnboardingStateType> manoIdToOnboardingStatus = new HashMap<>();

    @JsonProperty("c2cOnboardingState")
    private C2COnboardingStateType c2cOnboardingState;

    @JsonProperty("projectId")
    private String projectId;

    @JsonProperty("description")
    private String description = null;

    public PnfdInfo id(UUID id) {
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

    public PnfdInfo pnfdId(UUID pnfdId) {
        this.pnfdId = pnfdId;
        return this;
    }

    /**
     * Get pnfdId
     *
     * @return pnfdId
     **/
    @ApiModelProperty(value = "")

    @Valid

    public UUID getPnfdId() {
        return pnfdId;
    }

    public void setPnfdId(UUID pnfdId) {
        this.pnfdId = pnfdId;
    }

    public PnfdInfo pnfdName(String pnfdName) {
        this.pnfdName = pnfdName;
        return this;
    }

    /**
     * Name of the onboarded PNFD. This information is copied from the PNFD content
     * and shall be present after the PNFD content is on-boarded.
     *
     * @return pnfdName
     **/
    @ApiModelProperty(value = "Name of the onboarded PNFD. This information is copied from the PNFD content and shall be present after the PNFD content is on-boarded.")

    public String getPnfdName() {
        return pnfdName;
    }

    public void setPnfdName(String pnfdName) {
        this.pnfdName = pnfdName;
    }

    public PnfdInfo pnfdVersion(String pnfdVersion) {
        this.pnfdVersion = pnfdVersion;
        return this;
    }

    /**
     * Get pnfdVersion
     *
     * @return pnfdVersion
     **/
    @ApiModelProperty(value = "")

    public String getPnfdVersion() {
        return pnfdVersion;
    }

    public void setPnfdVersion(String pnfdVersion) {
        this.pnfdVersion = pnfdVersion;
    }

    public PnfdInfo pnfdProvider(String pnfdProvider) {
        this.pnfdProvider = pnfdProvider;
        return this;
    }

    /**
     * Provider of the onboarded PNFD. This information is copied from the PNFD
     * content and shall be present after the PNFD content is onboarded.
     *
     * @return pnfdProvider
     **/
    @ApiModelProperty(value = "Provider of the onboarded PNFD. This information is copied from the PNFD content and shall be present after the PNFD content is onboarded.")

    public String getPnfdProvider() {
        return pnfdProvider;
    }

    public void setPnfdProvider(String pnfdProvider) {
        this.pnfdProvider = pnfdProvider;
    }

    public PnfdInfo pnfdInvariantId(UUID pnfdInvariantId) {
        this.pnfdInvariantId = pnfdInvariantId;
        return this;
    }

    /**
     * Get pnfdInvariantId
     *
     * @return pnfdInvariantId
     **/
    @ApiModelProperty(value = "")

    @Valid

    public UUID getPnfdInvariantId() {
        return pnfdInvariantId;
    }

    public void setPnfdInvariantId(UUID pnfdInvariantId) {
        this.pnfdInvariantId = pnfdInvariantId;
    }

    public PnfdInfo pnfdOnboardingState(PnfdOnboardingStateType pnfdOnboardingState) {
        this.pnfdOnboardingState = pnfdOnboardingState;
        return this;
    }

    /**
     * Get pnfdOnboardingState
     *
     * @return pnfdOnboardingState
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public PnfdOnboardingStateType getPnfdOnboardingState() {
        return pnfdOnboardingState;
    }

    public void setPnfdOnboardingState(PnfdOnboardingStateType pnfdOnboardingState) {
        this.pnfdOnboardingState = pnfdOnboardingState;
    }

    public PnfdInfo onboardingFailureDetails(ProblemDetails onboardingFailureDetails) {
        this.onboardingFailureDetails = onboardingFailureDetails;
        return this;
    }

    /**
     * Get onboardingFailureDetails
     *
     * @return onboardingFailureDetails
     **/
    @ApiModelProperty(value = "")

    @Valid

    public ProblemDetails getOnboardingFailureDetails() {
        return onboardingFailureDetails;
    }

    public void setOnboardingFailureDetails(ProblemDetails onboardingFailureDetails) {
        this.onboardingFailureDetails = onboardingFailureDetails;
    }

    public PnfdInfo pnfdUsageState(PnfdUsageStateType pnfdUsageState) {
        this.pnfdUsageState = pnfdUsageState;
        return this;
    }

    /**
     * Get pnfdUsageState
     *
     * @return pnfdUsageState
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public PnfdUsageStateType getPnfdUsageState() {
        return pnfdUsageState;
    }

    public void setPnfdUsageState(PnfdUsageStateType pnfdUsageState) {
        this.pnfdUsageState = pnfdUsageState;
    }

    public PnfdInfo userDefinedData(KeyValuePairs userDefinedData) {
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

    public PnfdInfo links(PnfdLinksType links) {
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

    public PnfdLinksType getLinks() {
        return links;
    }

    public void setLinks(PnfdLinksType links) {
        this.links = links;
    }

    public Map<String, PnfdOnboardingStateType> getManoIdToOnboardingStatus() {
        return manoIdToOnboardingStatus;
    }

    public void setManoIdToOnboardingStatus(Map<String, PnfdOnboardingStateType> manoIdToOnboardingStatus) {
        this.manoIdToOnboardingStatus = manoIdToOnboardingStatus;
    }

    public C2COnboardingStateType getC2cOnboardingState() {
        return c2cOnboardingState;
    }

    public void setC2cOnboardingState(C2COnboardingStateType c2cOnboardingState) {
        this.c2cOnboardingState = c2cOnboardingState;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PnfdInfo pnfdInfo = (PnfdInfo) o;
        return Objects.equals(this.id, pnfdInfo.id) && Objects.equals(this.pnfdId, pnfdInfo.pnfdId)
                && Objects.equals(this.pnfdName, pnfdInfo.pnfdName)
                && Objects.equals(this.pnfdVersion, pnfdInfo.pnfdVersion)
                && Objects.equals(this.pnfdProvider, pnfdInfo.pnfdProvider)
                && Objects.equals(this.pnfdInvariantId, pnfdInfo.pnfdInvariantId)
                && Objects.equals(this.pnfdOnboardingState, pnfdInfo.pnfdOnboardingState)
                && Objects.equals(this.onboardingFailureDetails, pnfdInfo.onboardingFailureDetails)
                && Objects.equals(this.pnfdUsageState, pnfdInfo.pnfdUsageState)
                && Objects.equals(this.userDefinedData, pnfdInfo.userDefinedData)
                && Objects.equals(this.links, pnfdInfo.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pnfdId, pnfdName, pnfdVersion, pnfdProvider, pnfdInvariantId, pnfdOnboardingState,
                onboardingFailureDetails, pnfdUsageState, userDefinedData, links);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PnfdInfo {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    pnfdId: ").append(toIndentedString(pnfdId)).append("\n");
        sb.append("    pnfdName: ").append(toIndentedString(pnfdName)).append("\n");
        sb.append("    pnfdVersion: ").append(toIndentedString(pnfdVersion)).append("\n");
        sb.append("    pnfdProvider: ").append(toIndentedString(pnfdProvider)).append("\n");
        sb.append("    pnfdInvariantId: ").append(toIndentedString(pnfdInvariantId)).append("\n");
        sb.append("    pnfdOnboardingState: ").append(toIndentedString(pnfdOnboardingState)).append("\n");
        sb.append("    onboardingFailureDetails: ").append(toIndentedString(onboardingFailureDetails)).append("\n");
        sb.append("    pnfdUsageState: ").append(toIndentedString(pnfdUsageState)).append("\n");
        sb.append("    userDefinedData: ").append(toIndentedString(userDefinedData)).append("\n");
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
