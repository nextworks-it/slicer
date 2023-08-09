package it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "osm_admin_object")
public class AdminObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("projects_read")
    private ArrayList<String> projectRead;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("projects_write")
    private ArrayList<String> projectWrite;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long modified;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String operationalState;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String onboardingState;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String usageState;
    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StorageObject storage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long created;
    @ElementCollection(targetClass=String.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> userDefinedData;

    @JsonIgnore
    @OneToOne(mappedBy = "admin")
    private OsmInfoObject osmInfoObject;

    @Transient
    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<String> getProjectRead() {
        return projectRead;
    }

    public void setProjectRead(ArrayList<String> projectRead) {
        this.projectRead = projectRead;
    }

    public ArrayList<String> getProjectWrite() {
        return projectWrite;
    }

    public void setProjectWrite(ArrayList<String> projectWrite) {
        this.projectWrite = projectWrite;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public String getOperationalState() {
        return operationalState;
    }

    public void setOperationalState(String operationalState) {
        this.operationalState = operationalState;
    }

    public String getOnboardingState() {
        return onboardingState;
    }

    public void setOnboardingState(String onboardingState) {
        this.onboardingState = onboardingState;
    }

    public String getUsageState() {
        return usageState;
    }

    public void setUsageState(String usageState) {
        this.usageState = usageState;
    }

    public StorageObject getStorage() {
        return storage;
    }

    public void setStorage(StorageObject storage) {
        this.storage = storage;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Map<String, String> getUserDefinedData() {
        return userDefinedData;
    }

    public void setUserDefinedData(Map<String, String> userDefinedData) {
        this.userDefinedData = userDefinedData;
    }

    public OsmInfoObject getOsmInfoObject() {
        return osmInfoObject;
    }

    public void setOsmInfoObject(OsmInfoObject osmInfoObject) {
        this.osmInfoObject = osmInfoObject;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "AdminObject{" +
                "id=" + id +
                ", projectRead=" + projectRead +
                ", projectWrite=" + projectWrite +
                ", modified='" + modified + '\'' +
                ", operationalState='" + operationalState + '\'' +
                ", onboardingState='" + onboardingState + '\'' +
                ", usageState='" + usageState + '\'' +
                ", storage=" + storage +
                ", created='" + created + '\'' +
                ", userDefinedData=" + userDefinedData +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
