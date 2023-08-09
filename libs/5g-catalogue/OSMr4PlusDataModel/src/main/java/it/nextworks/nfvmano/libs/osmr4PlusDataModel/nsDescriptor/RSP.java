package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RSP {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("vnfd-connection-point-ref")
    private List<ConnectionPointReference> connectionPointReferenceList;

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

    public List<ConnectionPointReference> getConnectionPointReferenceList() {
        return connectionPointReferenceList;
    }

    public void setConnectionPointReferenceList(List<ConnectionPointReference> connectionPointReferenceList) {
        this.connectionPointReferenceList = connectionPointReferenceList;
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
        return "RSP{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", connectionPointReferenceList=" + connectionPointReferenceList +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
