package it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.elements;

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
public class OsmInfoObjectSimplified {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_id")
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    private String descriptorId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String version;

    @Transient
    private Map<String, Object> otherProperties = new HashMap<String, Object>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptorId() {
        return descriptorId;
    }

    public void setDescriptorId(String descriptorId) {
        this.descriptorId = descriptorId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
                "name='" + name + '\'' +
                ", descriptorId='" + descriptorId + '\'' +
                ", descriptorVersion='" + version + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
