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
package it.nextworks.nfvmano.catalogue.engine.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.nextworks.nfvmano.catalogue.engine.elements.ContentType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.NsdOnboardingStateType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.NsdOperationalStateType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.NsdUsageStateType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.PackageOnboardingStateType;
import it.nextworks.nfvmano.libs.common.exceptions.NotPermittedOperationException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
public class NsdInfoResource {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID nsdId;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<UUID> vnfPkgIds = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<UUID> pnfdInfoIds = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<UUID> nestedNsdInfoIds = new ArrayList<>();

    private NsdOnboardingStateType nsdOnboardingState;

    private NsdOperationalStateType nsdOperationalState;

    private NsdUsageStateType nsdUsageState;

    private String nsdName;

    private String nsdVersion;

    private String nsdDesigner;

    private UUID nsdInvariantId;

    private ContentType contentType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<String> nsdFilename = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<String> nsdPkgFilename = new ArrayList<>();

    private String metaFilename;

    private String manifestFilename;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Map<String, String> userDefinedData = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, NotificationResource> acknowledgedOnboardOpConsumers = new HashMap<>();

    private String projectId;

    private boolean isPublished;

    private boolean isRetrievedFromMANO;

    private boolean multiSite;

    private String description;

    public NsdInfoResource() {
    }

    public NsdInfoResource(UUID nsdId, List<UUID> vnfPkgIds, List<UUID> pnfdInfoIds, List<UUID> nestedNsdInfoIds, NsdOnboardingStateType nsdOnboardingState, NsdOperationalStateType nsdOperationalState, NsdUsageStateType nsdUsageState, String nsdName, String nsdVersion, String nsdDesigner, UUID nsdInvariantId, Map<String, String> userDefinedData) {
        this.nsdId = nsdId;
        this.vnfPkgIds = vnfPkgIds;
        this.pnfdInfoIds = pnfdInfoIds;
        this.nestedNsdInfoIds = nestedNsdInfoIds;
        this.nsdOnboardingState = nsdOnboardingState;
        this.nsdOperationalState = nsdOperationalState;
        this.nsdUsageState = nsdUsageState;
        this.nsdName = nsdName;
        this.nsdVersion = nsdVersion;
        this.nsdDesigner = nsdDesigner;
        this.nsdInvariantId = nsdInvariantId;
        this.userDefinedData = userDefinedData;
    }

    public NsdInfoResource(Map<String, String> userDefinedData) {
        if (userDefinedData != null) this.userDefinedData = userDefinedData;
        nsdOnboardingState = NsdOnboardingStateType.CREATED;
        nsdOperationalState = NsdOperationalStateType.DISABLED;
        nsdUsageState = NsdUsageStateType.NOT_IN_USE;
        contentType = ContentType.UNSPECIFIED;
    }

    public UUID getNsdId() {
        return nsdId;
    }

    public void setNsdId(UUID nsdId) {
        this.nsdId = nsdId;
    }

    public List<UUID> getVnfPkgIds() {
        return vnfPkgIds;
    }

    public void setVnfPkgIds(List<UUID> vnfPkgIds) {
        this.vnfPkgIds = vnfPkgIds;
    }

    public List<UUID> getPnfdInfoIds() {
        return pnfdInfoIds;
    }

    public void setPnfdInfoIds(List<UUID> pnfdInfoIds) {
        this.pnfdInfoIds = pnfdInfoIds;
    }

    public List<UUID> getNestedNsdInfoIds() {
        return nestedNsdInfoIds;
    }

    public void setNestedNsdInfoIds(List<UUID> nestedNsdInfoIds) {
        this.nestedNsdInfoIds = nestedNsdInfoIds;
    }

    public NsdOnboardingStateType getNsdOnboardingState() {
        return nsdOnboardingState;
    }

    public void setNsdOnboardingState(NsdOnboardingStateType nsdOnboardingState) {
        this.nsdOnboardingState = nsdOnboardingState;
    }

    public NsdOperationalStateType getNsdOperationalState() {
        return nsdOperationalState;
    }

    public void setNsdOperationalState(NsdOperationalStateType nsdOperationalState) {
        this.nsdOperationalState = nsdOperationalState;
    }

    public NsdUsageStateType getNsdUsageState() {
        return nsdUsageState;
    }

    public void setNsdUsageState(NsdUsageStateType nsdUsageState) {
        this.nsdUsageState = nsdUsageState;
    }

    public Map<String, String> getUserDefinedData() {
        return userDefinedData;
    }

    public void setUserDefinedData(Map<String, String> userDefinedData) {
        this.userDefinedData = userDefinedData;
    }

    public UUID getId() {
        return id;
    }

    public String getNsdName() {
        return nsdName;
    }

    public void setNsdName(String nsdName) {
        this.nsdName = nsdName;
    }

    public String getNsdVersion() {
        return nsdVersion;
    }

    public void setNsdVersion(String nsdVersion) {
        this.nsdVersion = nsdVersion;
    }

    public String getNsdDesigner() {
        return nsdDesigner;
    }

    public void setNsdDesigner(String nsdDesigner) {
        this.nsdDesigner = nsdDesigner;
    }

    public UUID getNsdInvariantId() {
        return nsdInvariantId;
    }

    public void setNsdInvariantId(UUID nsdInvariantId) {
        this.nsdInvariantId = nsdInvariantId;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public List<String> getNsdFilename() {
        return nsdFilename;
    }

    public void setNsdFilename(List<String> nsdFilename) {
        this.nsdFilename = nsdFilename;
    }

    public void addNsdFilename(String filename) {
        this.nsdFilename.add(filename);
    }

    public List<String> getNsdPkgFilename() {
        return nsdPkgFilename;
    }

    public void setNsdPkgFilename(List<String> nsdPkgFilename) {
        this.nsdPkgFilename = nsdPkgFilename;
    }

    public void addNsdPkgFilename(String nsdPkgFilename) {
        this.nsdPkgFilename.add(nsdPkgFilename);
    }

    public String getManifestFilename() { return manifestFilename; }

    public void setManifestFilename(String manifestFilename) { this.manifestFilename = manifestFilename; }

    public String getMetaFilename() { return metaFilename; }

    public void setMetaFilename(String metaFilename) { this.metaFilename = metaFilename; }

    public Map<String, NotificationResource> getAcknowledgedOnboardOpConsumers() {
        return acknowledgedOnboardOpConsumers;
    }

    public void setAcknowledgedOnboardOpConsumers(Map<String, NotificationResource> acknowledgedOnboardOpConsumers) {
        this.acknowledgedOnboardOpConsumers = acknowledgedOnboardOpConsumers;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public boolean isRetrievedFromMANO() {
        return isRetrievedFromMANO;
    }

    public void setRetrievedFromMANO(boolean retrievedFromMANO) {
        isRetrievedFromMANO = retrievedFromMANO;
    }

    public boolean isMultiSite() {
        return multiSite;
    }

    public void setMultiSite(boolean multiSite) {
        this.multiSite = multiSite;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public void isDeletable() throws NotPermittedOperationException {
        if (nsdOnboardingState != NsdOnboardingStateType.FAILED && nsdOperationalState != NsdOperationalStateType.DISABLED)
            throw new NotPermittedOperationException("NSD info " + this.id + " cannot be deleted because not DISABLED");
        if (nsdUsageState != NsdUsageStateType.NOT_IN_USE)
            throw new NotPermittedOperationException("NSD info " + this.id + " cannot be deleted because IN USE");
    }
}
