package it.nextworks.nfvmano.catalogue.plugins.siteInventory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Credentials {

  @NotNull
  @JsonProperty("host")
  private String host;

  @NotNull
  @PositiveOrZero
  @Max(65535)
  @JsonProperty("port")
  private long port;

  @NotNull
  @JsonProperty("project")
  private String project;

  @NotNull
  @JsonProperty("username")
  private String username;

  @NotNull
  @JsonProperty("password")
  private String password;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public long getPort() {
    return port;
  }

  public void setPort(long port) {
    this.port = port;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
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
}
