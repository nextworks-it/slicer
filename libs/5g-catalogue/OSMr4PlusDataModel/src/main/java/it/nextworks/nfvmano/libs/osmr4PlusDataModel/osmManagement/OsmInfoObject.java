package it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class OsmInfoObject {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_id")
    private String id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_admin")
    private AdminObject admin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String vendor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    private String descriptorId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String version;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    private Long epoch;
    private String osmId;
    private OsmObjectType type;
    private RecordState state = RecordState.NEW;

    @Transient
    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public AdminObject getAdmin() {
        return admin;
    }

    public void setAdmin(AdminObject admin) {
        this.admin = admin;
        this.admin.setOsmInfoObject(this);
    }

    public String getVendor() { return vendor; }

    public void setVendor(String vendor) { this.vendor = vendor; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getDescriptorId() { return descriptorId; }

    public void setDescriptorId(String descriptorId) { this.descriptorId = descriptorId; }

    public String getVersion() { return version; }

    public void setVersion(String version) { this.version = version; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Long getEpoch() {
        return epoch;
    }

    public void setEpoch(Long epoch) {
        this.epoch = epoch;
    }

    public String getOsmId() {
        return osmId;
    }

    public void setOsmId(String osmId) {
        this.osmId = osmId;
    }

    public OsmObjectType getType() {
        return type;
    }

    public void setType(OsmObjectType type) {
        this.type = type;
    }

    public RecordState getState() {
        return state;
    }

    public void setState(RecordState state) {
        this.state = state;
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
        return "OsmInfoObject{" +
                "id='" + id + '\'' +
                ", admin=" + admin +
                ", vendor='" + vendor + '\'' +
                ", name='" + name + '\'' +
                ", descriptorId='" + descriptorId + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", epoch=" + epoch +
                ", osmId='" + osmId + '\'' +
                ", type=" + type +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
