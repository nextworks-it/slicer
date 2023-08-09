package it.nextworks.nfvmano.catalogue.plugins.siteInventory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NfvOrchestrator {

  enum OperationalState {
    ENABLED,
    DISABLED
  }

  @NotNull
  @JsonProperty("id")
  private Long id;

  @NotNull
  @JsonProperty("name")
  private String name;

  @NotNull
  @JsonProperty("type")
  private String type;

  @NotNull
  @JsonProperty("version")
  private String version;

  @NotNull
  @JsonProperty("operationalState")
  private String operationalState;

  @JsonProperty("uri")
  private String uri;

  @JsonProperty("createdAt")
  private Date createdAt;

  @JsonProperty("updatedAt")
  private Date updatedAt;

  @JsonProperty("credentials")
  private Credentials credentials;

  @JsonProperty("vimAccounts")
  private List<VimAccount> vimAccounts;

  @JsonProperty("site")
  private Site site;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getOperationalState() {
    return operationalState;
  }

  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  public List<VimAccount> getVimAccounts() {
    return vimAccounts;
  }

  public void setVimAccounts(List<VimAccount> vimAccounts) {
    this.vimAccounts = vimAccounts;
  }

  public Site getSite() {
    return site;
  }

  public void setSite(Site site) {
    this.site = site;
  }
}
