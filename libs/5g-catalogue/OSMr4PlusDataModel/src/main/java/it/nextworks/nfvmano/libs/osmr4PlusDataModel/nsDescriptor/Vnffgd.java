package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vnffgd {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("short-name")
    private String shortName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String vendor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String version;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RSP> rsp;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Classifier> classifier;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RSP> getRsp() {
        return rsp;
    }

    public void setRsp(List<RSP> rsp) {
        this.rsp = rsp;
    }

    public List<Classifier> getClassifier() {
        return classifier;
    }

    public void setClassifier(List<Classifier> classifier) {
        this.classifier = classifier;
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
        return "Vnffgd{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", vendor='" + vendor + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", rsp=" + rsp +
                ", classifier=" + classifier +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
