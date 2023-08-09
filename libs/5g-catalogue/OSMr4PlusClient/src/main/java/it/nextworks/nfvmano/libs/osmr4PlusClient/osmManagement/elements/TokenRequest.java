package it.nextworks.nfvmano.libs.osmr4PlusClient.osmManagement.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenRequest {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("project_id")
    private String project;

    public TokenRequest(String username, String password, String project) {
        this.username = username;
        this.password = password;
        this.project = project;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getProject() { return project; }

    public void setProject(String project) { this.project = project; }
}
