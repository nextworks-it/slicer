package it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTokenRequest {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("project_id")
    private String project;

    public CreateTokenRequest(String username, String password, String project) {
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

    @Override
    public String toString() {
        return "CreateTokenRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", project='" + project + '\'' +
                '}';
    }
}
