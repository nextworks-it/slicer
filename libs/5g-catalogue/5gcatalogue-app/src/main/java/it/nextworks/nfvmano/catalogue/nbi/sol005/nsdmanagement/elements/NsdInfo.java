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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.C2COnboardingStateType;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * This type represents a response for the query NSD operation.
 */
@ApiModel(description = "This type represents a response for the query NSD operation.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-23T16:31:35.952+02:00")

public class NsdInfo {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("nsdId")
    private UUID nsdId = null;

    @JsonProperty("nsdName")
    private String nsdName = null;

    @JsonProperty("nsdVersion")
    private String nsdVersion = null;

    @JsonProperty("nsdDesigner")
    private String nsdDesigner = null;

    @JsonProperty("nsdInvariantId")
    private UUID nsdInvariantId = null;

    @JsonProperty("vnfPkgIds")
    @Valid
    private List<UUID> vnfPkgIds = null;

    @JsonProperty("pnfdInfoIds")
    @Valid
    private List<UUID> pnfdInfoIds = null;

    @JsonProperty("nestedNsdInfoIds")
    @Valid
    private List<UUID> nestedNsdInfoIds = null;

    @JsonProperty("nsdOnboardingState")
    private NsdOnboardingStateType nsdOnboardingState = null;

    @JsonProperty("onboardingFailureDetails")
    private ProblemDetails onboardingFailureDetails = null;

    @JsonProperty("nsdOperationalState")
    private NsdOperationalStateType nsdOperationalState = null;

    @JsonProperty("nsdUsageState")
    private NsdUsageStateType nsdUsageState = null;

    @JsonProperty("userDefinedData")
    private KeyValuePairs userDefinedData = null;

    @JsonProperty("_links")
    private NsdLinksType links = null;

    @JsonProperty("manosOnboardingStatus")
    private Map<String, NsdOnboardingStateType> manoIdToOnboardingStatus = new HashMap<>();

    @JsonProperty("c2cOnboardingState")
    private C2COnboardingStateType c2cOnboardingState;

    @JsonProperty("projectId")
    private String projectId;

    @JsonProperty("manoInfoIds")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> manoInfoIds = new HashMap<>();

    @JsonProperty("description")
    private String description = null;

    public NsdInfo id(UUID id) {
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

    public NsdInfo nsdId(UUID nsdId) {
        this.nsdId = nsdId;
        return this;
    }

    /**
     * Get nsdId
     *
     * @return nsdId
     **/
    @ApiModelProperty(value = "")

    @Valid

    public UUID getNsdId() {
        return nsdId;
    }

    public void setNsdId(UUID nsdId) {
        this.nsdId = nsdId;
    }

    public NsdInfo nsdName(String nsdName) {
        this.nsdName = nsdName;
        return this;
    }

    /**
     * Name of the onboarded NSD. This information is copied from the NSD content
     * and shall be present after the NSD content is on-boarded.
     *
     * @return nsdName
     **/
    @ApiModelProperty(value = "Name of the onboarded NSD. This information is copied from the NSD content and shall be present after the NSD content is on-boarded.")

    public String getNsdName() {
        return nsdName;
    }

    public void setNsdName(String nsdName) {
        this.nsdName = nsdName;
    }

    public NsdInfo nsdVersion(String nsdVersion) {
        this.nsdVersion = nsdVersion;
        return this;
    }

    /**
     * Get nsdVersion
     *
     * @return nsdVersion
     **/
    @ApiModelProperty(value = "")

    public String getNsdVersion() {
        return nsdVersion;
    }

    public void setNsdVersion(String nsdVersion) {
        this.nsdVersion = nsdVersion;
    }

    public NsdInfo nsdDesigner(String nsdDesigner) {
        this.nsdDesigner = nsdDesigner;
        return this;
    }

    /**
     * Designer of the on-boarded NSD. This information is copied from the NSD
     * content and shall be present after the NSD content is on-boarded.
     *
     * @return nsdDesigner
     **/
    @ApiModelProperty(value = "Designer of the on-boarded NSD. This information is copied from the NSD content and shall be present after the NSD content is on-boarded.")

    public String getNsdDesigner() {
        return nsdDesigner;
    }

    public void setNsdDesigner(String nsdDesigner) {
        this.nsdDesigner = nsdDesigner;
    }

    public NsdInfo nsdInvariantId(UUID nsdInvariantId) {
        this.nsdInvariantId = nsdInvariantId;
        return this;
    }

    /**
     * Get nsdInvariantId
     *
     * @return nsdInvariantId
     **/
    @ApiModelProperty(value = "")

    @Valid

    public UUID getNsdInvariantId() {
        return nsdInvariantId;
    }

    public void setNsdInvariantId(UUID nsdInvariantId) {
        this.nsdInvariantId = nsdInvariantId;
    }

    public NsdInfo vnfPkgIds(List<UUID> vnfPkgIds) {
        this.vnfPkgIds = vnfPkgIds;
        return this;
    }

    public NsdInfo addVnfPkgIdsItem(UUID vnfPkgIdsItem) {
        if (this.vnfPkgIds == null) {
            this.vnfPkgIds = new ArrayList<UUID>();
        }
        this.vnfPkgIds.add(vnfPkgIdsItem);
        return this;
    }

    /**
     * Get vnfPkgIds
     *
     * @return vnfPkgIds
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<UUID> getVnfPkgIds() {
        return vnfPkgIds;
    }

    public void setVnfPkgIds(List<UUID> vnfPkgIds) {
        this.vnfPkgIds = vnfPkgIds;
    }

    public NsdInfo pnfdInfoIds(List<UUID> pnfdInfoIds) {
        this.pnfdInfoIds = pnfdInfoIds;
        return this;
    }

    public NsdInfo addPnfdInfoIdsItem(UUID pnfdInfoIdsItem) {
        if (this.pnfdInfoIds == null) {
            this.pnfdInfoIds = new ArrayList<UUID>();
        }
        this.pnfdInfoIds.add(pnfdInfoIdsItem);
        return this;
    }

    /**
     * Get pnfdInfoIds
     *
     * @return pnfdInfoIds
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<UUID> getPnfdInfoIds() {
        return pnfdInfoIds;
    }

    public void setPnfdInfoIds(List<UUID> pnfdInfoIds) {
        this.pnfdInfoIds = pnfdInfoIds;
    }

    public NsdInfo nestedNsdInfoIds(List<UUID> nestedNsdInfoIds) {
        this.nestedNsdInfoIds = nestedNsdInfoIds;
        return this;
    }

    public NsdInfo addNestedNsdInfoIdsItem(UUID nestedNsdInfoIdsItem) {
        if (this.nestedNsdInfoIds == null) {
            this.nestedNsdInfoIds = new ArrayList<UUID>();
        }
        this.nestedNsdInfoIds.add(nestedNsdInfoIdsItem);
        return this;
    }

    /**
     * Get nestedNsdInfoIds
     *
     * @return nestedNsdInfoIds
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<UUID> getNestedNsdInfoIds() {
        return nestedNsdInfoIds;
    }

    public void setNestedNsdInfoIds(List<UUID> nestedNsdInfoIds) {
        this.nestedNsdInfoIds = nestedNsdInfoIds;
    }

    public NsdInfo nsdOnboardingState(NsdOnboardingStateType nsdOnboardingState) {
        this.nsdOnboardingState = nsdOnboardingState;
        return this;
    }

    /**
     * Get nsdOnboardingState
     *
     * @return nsdOnboardingState
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public NsdOnboardingStateType getNsdOnboardingState() {
        return nsdOnboardingState;
    }

    public void setNsdOnboardingState(NsdOnboardingStateType nsdOnboardingState) {
        this.nsdOnboardingState = nsdOnboardingState;
    }

    public NsdInfo onboardingFailureDetails(ProblemDetails onboardingFailureDetails) {
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

    public NsdInfo nsdOperationalState(NsdOperationalStateType nsdOperationalState) {
        this.nsdOperationalState = nsdOperationalState;
        return this;
    }

    /**
     * Get nsdOperationalState
     *
     * @return nsdOperationalState
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public NsdOperationalStateType getNsdOperationalState() {
        return nsdOperationalState;
    }

    public void setNsdOperationalState(NsdOperationalStateType nsdOperationalState) {
        this.nsdOperationalState = nsdOperationalState;
    }

    public NsdInfo nsdUsageState(NsdUsageStateType nsdUsageState) {
        this.nsdUsageState = nsdUsageState;
        return this;
    }

    /**
     * Get nsdUsageState
     *
     * @return nsdUsageState
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public NsdUsageStateType getNsdUsageState() {
        return nsdUsageState;
    }

    public void setNsdUsageState(NsdUsageStateType nsdUsageState) {
        this.nsdUsageState = nsdUsageState;
    }

    public NsdInfo userDefinedData(KeyValuePairs userDefinedData) {
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

    public NsdInfo links(NsdLinksType links) {
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

    public NsdLinksType getLinks() {
        return links;
    }

    public void setLinks(NsdLinksType links) {
        this.links = links;
    }

    public Map<String, NsdOnboardingStateType> getManoIdToOnboardingStatus() {
        return manoIdToOnboardingStatus;
    }

    public void setManoIdToOnboardingStatus(Map<String, NsdOnboardingStateType> manoIdToOnboardingStatus) {
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

    public Map<String, String> getManoInfoIds() {
        return manoInfoIds;
    }

    public void setManoInfoIds(Map<String, String> manoInfoIds) {
        this.manoInfoIds = manoInfoIds;
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
        NsdInfo nsdInfo = (NsdInfo) o;
        return Objects.equals(this.id, nsdInfo.id) && Objects.equals(this.nsdId, nsdInfo.nsdId)
                && Objects.equals(this.nsdName, nsdInfo.nsdName) && Objects.equals(this.nsdVersion, nsdInfo.nsdVersion)
                && Objects.equals(this.nsdDesigner, nsdInfo.nsdDesigner)
                && Objects.equals(this.nsdInvariantId, nsdInfo.nsdInvariantId)
                && Objects.equals(this.vnfPkgIds, nsdInfo.vnfPkgIds)
                && Objects.equals(this.pnfdInfoIds, nsdInfo.pnfdInfoIds)
                && Objects.equals(this.nestedNsdInfoIds, nsdInfo.nestedNsdInfoIds)
                && Objects.equals(this.nsdOnboardingState, nsdInfo.nsdOnboardingState)
                && Objects.equals(this.onboardingFailureDetails, nsdInfo.onboardingFailureDetails)
                && Objects.equals(this.nsdOperationalState, nsdInfo.nsdOperationalState)
                && Objects.equals(this.nsdUsageState, nsdInfo.nsdUsageState)
                && Objects.equals(this.userDefinedData, nsdInfo.userDefinedData)
                && Objects.equals(this.links, nsdInfo.links)
                && Objects.equals(this.manoInfoIds, nsdInfo.manoInfoIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nsdId, nsdName, nsdVersion, nsdDesigner, nsdInvariantId, vnfPkgIds, pnfdInfoIds,
                nestedNsdInfoIds, nsdOnboardingState, onboardingFailureDetails, nsdOperationalState, nsdUsageState,
                userDefinedData, links, manoInfoIds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NsdInfo {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    nsdId: ").append(toIndentedString(nsdId)).append("\n");
        sb.append("    nsdName: ").append(toIndentedString(nsdName)).append("\n");
        sb.append("    nsdVersion: ").append(toIndentedString(nsdVersion)).append("\n");
        sb.append("    nsdDesigner: ").append(toIndentedString(nsdDesigner)).append("\n");
        sb.append("    nsdInvariantId: ").append(toIndentedString(nsdInvariantId)).append("\n");
        sb.append("    vnfPkgIds: ").append(toIndentedString(vnfPkgIds)).append("\n");
        sb.append("    pnfdInfoIds: ").append(toIndentedString(pnfdInfoIds)).append("\n");
        sb.append("    nestedNsdInfoIds: ").append(toIndentedString(nestedNsdInfoIds)).append("\n");
        sb.append("    nsdOnboardingState: ").append(toIndentedString(nsdOnboardingState)).append("\n");
        sb.append("    onboardingFailureDetails: ").append(toIndentedString(onboardingFailureDetails)).append("\n");
        sb.append("    nsdOperationalState: ").append(toIndentedString(nsdOperationalState)).append("\n");
        sb.append("    nsdUsageState: ").append(toIndentedString(nsdUsageState)).append("\n");
        sb.append("    userDefinedData: ").append(toIndentedString(userDefinedData)).append("\n");
        sb.append("    links: ").append(toIndentedString(links)).append("\n");
        sb.append("    manoInfoIds: ").append(toIndentedString(manoInfoIds)).append("\n");
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
