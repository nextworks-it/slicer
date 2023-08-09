package it.nextworks.nfvmano.libs.osmr4PlusClient.osmManagement.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Token {

    @JsonProperty("admin")
    private boolean admin;
    @JsonProperty("username")
    private String username;
    @JsonProperty("project_id")
    private String project_id;
    @JsonProperty("id")
    private String id;
    @JsonProperty("issued_at")
    private String issuedAt;
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("remote_port")
    private String remote_port;
    @JsonProperty("remote_host")
    private String remote_host;
    @JsonProperty("expires")
    private String expires;
    @JsonProperty("user_id")
    private String user_id;
    @JsonProperty("project_name")
    private String project_name;
    @JsonProperty("roles")
    private List<Role> roles = new ArrayList<>();

    public boolean isAdmin() { return admin; }

    public void setAdmin(boolean admin) { this.admin = admin; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getProject_id() { return project_id; }

    public void setProject_id(String project_id) { this.project_id = project_id; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getIssuedAt() { return issuedAt; }

    public void setIssuedAt(String issuedAt) { this.issuedAt = issuedAt; }

    public String get_id() { return _id; }

    public void set_id(String _id) { this._id = _id; }

    public String getRemote_port() { return remote_port; }

    public void setRemote_port(String remote_port) { this.remote_port = remote_port; }

    public String getRemote_host() { return remote_host; }

    public void setRemote_host(String remote_host) { this.remote_host = remote_host; }

    public String getExpires() { return expires; }

    public void setExpires(String expires) { this.expires = expires; }

    public String getUser_id() { return user_id; }

    public void setUser_id(String user_id) { this.user_id = user_id; }

    public String getProject_name() { return project_name; }

    public void setProject_name(String project_name) { this.project_name = project_name; }

    public List<Role> getRoles() { return roles; }

    public void setRoles(List<Role> roles) { this.roles = roles; }
}
