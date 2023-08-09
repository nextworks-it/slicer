package it.nextworks.nfvmano.catalogue.plugins.siteInventory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VimAccount {

  // This is the id used by the NVFO for this VIM account
  @NotNull
  @JsonProperty("vimAccountNfvoId")
  private String vimAccountNfvoId;

  public String getVimAccountNfvoId() {
    return vimAccountNfvoId;
  }

  public void setVimAccountNfvoId(String vimAccountNfvoId) {
    this.vimAccountNfvoId = vimAccountNfvoId;
  }
}
