package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class JsonQueryParams {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("json-path")
    private String jsonPath;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("object-path")
    private String objectPath;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public String getObjectPath() {
        return objectPath;
    }

    public void setObjectPath(String objectPath) {
        this.objectPath = objectPath;
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
        return "JsonQueryParams{" +
                "jsonPath='" + jsonPath + '\'' +
                ", objectPath='" + objectPath + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
