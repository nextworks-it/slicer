package it.nextworks.nfvmano.catalogue.engine.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.nextworks.nfvmano.catalogue.engine.elements.ContentType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.KeyValuePairs;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.PackageOnboardingStateType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.PackageOperationalStateType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.PackageUsageStateType;
import it.nextworks.nfvmano.libs.common.exceptions.NotPermittedOperationException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
public class VnfPkgInfoResource {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID vnfdId;
    private String vnfProvider;
    private String vnfProductName;
    private String vnfSoftwareVersion;
    private String vnfdVersion;
    private String checksum;
    private PackageOnboardingStateType onboardingState;
    private PackageOperationalStateType operationalState;
    private PackageUsageStateType usageState;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Map<String, String> userDefinedData = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, NotificationResource> acknowledgedOnboardOpConsumers = new HashMap<>();

    private ContentType contentType;

    private String vnfPkgFilename;

    private String vnfdFilename;

    private String metaFilename;

    private String manifestFilename;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<String> parentNsds = new ArrayList<>();

    private String projectId;

    private boolean isPublished;

    private boolean isRetrievedFromMANO;

    private String description;

    public VnfPkgInfoResource() {
    }

    public VnfPkgInfoResource(Map<String, String> userDefinedData) {
        if (userDefinedData != null) this.userDefinedData = userDefinedData;
        onboardingState = PackageOnboardingStateType.CREATED;
        operationalState = PackageOperationalStateType.DISABLED;
        usageState = PackageUsageStateType.NOT_IN_USE;
        contentType = ContentType.UNSPECIFIED;
    }

    public VnfPkgInfoResource(UUID vnfdId, String vnfProvider, String vnfProductName, String vnfSoftwareVersion, String vnfdVersion, PackageOnboardingStateType onboardingState, PackageOperationalStateType operationalState, PackageUsageStateType usageState, KeyValuePairs userDefinedData) {
        this.vnfdId = vnfdId;
        this.vnfProvider = vnfProvider;
        this.vnfProductName = vnfProductName;
        this.vnfSoftwareVersion = vnfSoftwareVersion;
        this.vnfdVersion = vnfdVersion;
        this.onboardingState = onboardingState;
        this.operationalState = operationalState;
        this.usageState = usageState;
        this.userDefinedData = userDefinedData;
    }

    public VnfPkgInfoResource(UUID vnfdId, String vnfProvider, String vnfProductName, String vnfSoftwareVersion, String vnfdVersion, String checksum, PackageOnboardingStateType onboardingState, PackageOperationalStateType operationalState, PackageUsageStateType usageState, KeyValuePairs userDefinedData, Map<String, NotificationResource> acknowledgedOnboardOpConsumers) {
        this.vnfdId = vnfdId;
        this.vnfProvider = vnfProvider;
        this.vnfProductName = vnfProductName;
        this.vnfSoftwareVersion = vnfSoftwareVersion;
        this.vnfdVersion = vnfdVersion;
        this.checksum = checksum;
        this.onboardingState = onboardingState;
        this.operationalState = operationalState;
        this.usageState = usageState;
        this.userDefinedData = userDefinedData;
        this.acknowledgedOnboardOpConsumers = acknowledgedOnboardOpConsumers;
    }

    public UUID getId() {
        return id;
    }

    public UUID getVnfdId() {
        return vnfdId;
    }

    public void setVnfdId(UUID vnfdId) {
        this.vnfdId = vnfdId;
    }

    public String getVnfProvider() {
        return vnfProvider;
    }

    public void setVnfProvider(String vnfProvider) {
        this.vnfProvider = vnfProvider;
    }

    public String getVnfProductName() {
        return vnfProductName;
    }

    public void setVnfProductName(String vnfProductName) {
        this.vnfProductName = vnfProductName;
    }

    public String getVnfSoftwareVersion() {
        return vnfSoftwareVersion;
    }

    public void setVnfSoftwareVersion(String vnfSoftwareVersion) {
        this.vnfSoftwareVersion = vnfSoftwareVersion;
    }

    public String getVnfdVersion() {
        return vnfdVersion;
    }

    public void setVnfdVersion(String vnfdVersion) {
        this.vnfdVersion = vnfdVersion;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public PackageOnboardingStateType getOnboardingState() {
        return onboardingState;
    }

    public void setOnboardingState(PackageOnboardingStateType onboardingState) {
        this.onboardingState = onboardingState;
    }

    public PackageOperationalStateType getOperationalState() {
        return operationalState;
    }

    public void setOperationalState(PackageOperationalStateType operationalState) {
        this.operationalState = operationalState;
    }

    public PackageUsageStateType getUsageState() {
        return usageState;
    }

    public void setUsageState(PackageUsageStateType usageState) {
        this.usageState = usageState;
    }

    public Map<String, String> getUserDefinedData() {
        return userDefinedData;
    }

    public void setUserDefinedData(Map<String, String> userDefinedData) {
        this.userDefinedData = userDefinedData;
    }

    public Map<String, NotificationResource> getAcknowledgedOnboardOpConsumers() {
        return acknowledgedOnboardOpConsumers;
    }

    public void setAcknowledgedOnboardOpConsumers(Map<String, NotificationResource> acknowledgedOnboardOpConsumers) {
        this.acknowledgedOnboardOpConsumers = acknowledgedOnboardOpConsumers;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getVnfPkgFilename() {
        return vnfPkgFilename;
    }

    public void setVnfPkgFilename(String vnfPkgFilename) {
        this.vnfPkgFilename = vnfPkgFilename;
    }

    public String getVnfdFilename() {
        return vnfdFilename;
    }

    public void setVnfdFilename(String vnfdFilename) {
        this.vnfdFilename = vnfdFilename;
    }

    public String getMetaFilename() {
        return metaFilename;
    }

    public void setMetaFilename(String metaFilename) {
        this.metaFilename = metaFilename;
    }

    public String getManifestFilename() {
        return manifestFilename;
    }

    public void setManifestFilename(String manifestFilename) {
        this.manifestFilename = manifestFilename;
    }

    public List<String> getParentNsds() {
        return parentNsds;
    }

    public void setParentNsds(List<String> parentNsds) {
        this.parentNsds = parentNsds;
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

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public void isDeletable() throws NotPermittedOperationException {
        if (onboardingState != PackageOnboardingStateType.FAILED && operationalState != PackageOperationalStateType.DISABLED)
            throw new NotPermittedOperationException("VNF Pkg info " + this.id + " cannot be deleted because not DISABLED");
        if (usageState != PackageUsageStateType.NOT_IN_USE)
            throw new NotPermittedOperationException("VNF Pkg info " + this.id + " cannot be deleted because IN USE");
        if (!parentNsds.isEmpty())
            throw new NotPermittedOperationException("VNF Pkg info " + this.id + " cannot be deleted because there is at least one NS referencing it");
    }
}
