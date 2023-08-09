package it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class TokenObject {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean admin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("project_id")
    private String projectId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("issued_at")
    private String issuedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String _id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("remote_port")
    private String remotePort;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("remote_host")
    private String remoteHost;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String expires;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public boolean isAdmin() { return admin; }

    public void setAdmin(boolean admin) { this.admin = admin; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getProjectId() { return projectId; }

    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getIssuedAt() { return issuedAt; }

    public void setIssuedAt(String issuedAt) { this.issuedAt = issuedAt; }

    public String get_id() { return _id; }

    public void set_id(String _id) { this._id = _id; }

    public String getRemotePort() { return remotePort; }

    public void setRemotePort(String remotePort) { this.remotePort = remotePort; }

    public String getRemoteHost() { return remoteHost; }

    public void setRemoteHost(String remoteHost) { this.remoteHost = remoteHost; }

    public String getExpires() { return expires; }

    public void setExpires(String expires) { this.expires = expires; }

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
        return "TokenObject{" +
                "admin=" + admin +
                ", username='" + username + '\'' +
                ", projectId='" + projectId + '\'' +
                ", id='" + id + '\'' +
                ", issuedAt='" + issuedAt + '\'' +
                ", _id='" + _id + '\'' +
                ", remotePort='" + remotePort + '\'' +
                ", remoteHost='" + remoteHost + '\'' +
                ", expires='" + expires + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
