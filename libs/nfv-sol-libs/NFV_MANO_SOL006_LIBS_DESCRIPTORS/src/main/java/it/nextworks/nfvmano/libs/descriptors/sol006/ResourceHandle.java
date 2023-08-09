package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * ResourceHandle
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class ResourceHandle   {
  @JsonProperty("resource-provider-id")
  private String resourceProviderId = null;

  @JsonProperty("vim-id")
  private String vimId = null;

  @JsonProperty("resource-id")
  private String resourceId = null;

  public ResourceHandle resourceProviderId(String resourceProviderId) {
    this.resourceProviderId = resourceProviderId;
    return this;
  }

  /**
   * Get resourceProviderId
   * @return resourceProviderId
   **/
  //@Schema(description = "")
  
    public String getResourceProviderId() {
    return resourceProviderId;
  }

  public void setResourceProviderId(String resourceProviderId) {
    this.resourceProviderId = resourceProviderId;
  }

  public ResourceHandle vimId(String vimId) {
    this.vimId = vimId;
    return this;
  }

  /**
   * Get vimId
   * @return vimId
   **/
  //@Schema(description = "")
  
    public String getVimId() {
    return vimId;
  }

  public void setVimId(String vimId) {
    this.vimId = vimId;
  }

  public ResourceHandle resourceId(String resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  /**
   * Get resourceId
   * @return resourceId
   **/
  //@Schema(description = "")
  
    public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResourceHandle resourceHandle = (ResourceHandle) o;
    return Objects.equals(this.resourceProviderId, resourceHandle.resourceProviderId) &&
        Objects.equals(this.vimId, resourceHandle.vimId) &&
        Objects.equals(this.resourceId, resourceHandle.resourceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourceProviderId, vimId, resourceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResourceHandle {\n");
    
    sb.append("    resourceProviderId: ").append(toIndentedString(resourceProviderId)).append("\n");
    sb.append("    vimId: ").append(toIndentedString(vimId)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
