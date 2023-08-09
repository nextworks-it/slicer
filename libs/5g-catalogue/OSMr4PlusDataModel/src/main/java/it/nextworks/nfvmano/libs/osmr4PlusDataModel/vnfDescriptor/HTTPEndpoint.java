package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTPEndpoint {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean https;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer port;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("polling-interval-secs")
    private Integer pollingIntervalSecs;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String method;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Headers> headers;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPollingIntervalSecs() {
        return pollingIntervalSecs;
    }

    public void setPollingIntervalSecs(Integer pollingIntervalSecs) {
        this.pollingIntervalSecs = pollingIntervalSecs;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Headers> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Headers> headers) {
        this.headers = headers;
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
        return "HTTPEndpoint{" +
                "path='" + path + '\'' +
                ", https=" + https +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pollingIntervalSecs=" + pollingIntervalSecs +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
