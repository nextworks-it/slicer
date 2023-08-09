package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

public class DashboardParameters {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean https;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer port;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) { this.path = path; }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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
        return "DashboardParameters{" +
                "path='" + path + '\'' +
                ", https=" + https +
                ", port=" + port +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
