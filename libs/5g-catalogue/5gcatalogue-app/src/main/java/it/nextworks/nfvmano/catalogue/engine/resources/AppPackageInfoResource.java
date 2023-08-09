package it.nextworks.nfvmano.catalogue.engine.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import it.nextworks.nfvmano.libs.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.common.enums.UsageState;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd.Appd;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class AppPackageInfoResource {

    @Id
    @GeneratedValue
    private UUID id;

    private String project;

    //AppPackageInfo
    private String appPackageInfoId;
    private String appdId;
    private String version;
    private String provider;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Appd appd;
    private OperationalState operationalState;
    private UsageState usageState;
    private boolean deletionPending;
    @ElementCollection(
            fetch = FetchType.EAGER
    )
    @Fetch(FetchMode.SELECT)
    @Cascade({CascadeType.ALL})
    @JsonIgnore
    private List<String> nsInstanceId = new ArrayList();

    public AppPackageInfoResource() {
    }

    public AppPackageInfoResource(String appPackageInfoId, String appdId, String version, String provider, String name, Appd appd, OperationalState operationalState, UsageState usageState, boolean deletionPending, String project) {
        this.appPackageInfoId = appPackageInfoId;
        this.appdId = appdId;
        this.version = version;
        this.provider = provider;
        this.name = name;
        this.appd = appd;
        this.operationalState = operationalState;
        this.usageState = usageState;
        this.deletionPending = deletionPending;
        this.project = project;
    }

    public String getAppPackageInfoId() {
        return this.appPackageInfoId;
    }

    public String getAppdId() {
        return this.appdId;
    }

    public String getVersion() {
        return this.version;
    }

    public Appd getAppd() {
        return this.appd;
    }

    public OperationalState getOperationalState() {
        return this.operationalState;
    }

    public UsageState getUsageState() {
        return this.usageState;
    }

    public boolean isDeletionPending() {
        return this.deletionPending;
    }

    public void setAppd(Appd appd) {
        this.appd = appd;
    }

    public void addNsInstanceId(String nsId) {
        this.nsInstanceId.add(nsId);
    }

    public String getName() {
        return this.name;
    }

    public List<String> getNsInstanceId() {
        return this.nsInstanceId;
    }

    public void removeNsInstanceId(String nsId) {
        this.nsInstanceId.remove(nsId);
    }

    public UUID getId() {
        return this.id;
    }

    public void setOperationalState(OperationalState operationalState) {
        this.operationalState = operationalState;
    }

    public void setUsageState(UsageState usageState) {
        this.usageState = usageState;
    }

    public void setDeletionPending(boolean deletionPending) {
        this.deletionPending = deletionPending;
    }

    public void setAppPackageInfoId(String appPackageInfoId) {
        this.appPackageInfoId = appPackageInfoId;
    }

    public String getProvider() {
        return this.provider;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void isValid() throws MalformattedElementException {
        if (this.appPackageInfoId == null) {
            throw new MalformattedElementException("Application package info without ID");
        } else if (this.appdId == null) {
            throw new MalformattedElementException("Application package info without AppD ID");
        } else if (this.version == null) {
            throw new MalformattedElementException("Application package info without version");
        } else if (this.provider == null) {
            throw new MalformattedElementException("Application package info without provider");
        } else if (this.appd == null) {
            throw new MalformattedElementException("Application package info without AppD");
        }
    }
}
