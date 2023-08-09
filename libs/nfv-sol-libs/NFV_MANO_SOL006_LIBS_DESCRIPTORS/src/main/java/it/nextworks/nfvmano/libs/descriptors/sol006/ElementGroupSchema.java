package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;



/**
 * ElementGroupSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class ElementGroupSchema   {
  @JsonProperty("virtual-link-desc")

  private List<String> virtualLinkDesc = null;

  @JsonProperty("element-group_id")
  private String elementGroupId = null;

  @JsonProperty("vdu")

  private List<String> vdu = null;

  @JsonProperty("description")
  private String description = null;

  public ElementGroupSchema virtualLinkDesc(List<String> virtualLinkDesc) {
    this.virtualLinkDesc = virtualLinkDesc;
    return this;
  }

  public ElementGroupSchema addVirtualLinkDescItem(String virtualLinkDescItem) {
    if (this.virtualLinkDesc == null) {
      this.virtualLinkDesc = new ArrayList<String>();
    }
    this.virtualLinkDesc.add(virtualLinkDescItem);
    return this;
  }

  /**
   * Get virtualLinkDesc
   * @return virtualLinkDesc
   **/
  //@Schema(description = "")
  
    public List<String> getVirtualLinkDesc() {
    return virtualLinkDesc;
  }

  public void setVirtualLinkDesc(List<String> virtualLinkDesc) {
    this.virtualLinkDesc = virtualLinkDesc;
  }

  public ElementGroupSchema elementGroupId(String elementGroupId) {
    this.elementGroupId = elementGroupId;
    return this;
  }

  /**
   * Unique identifier of this group in the VNFD.
   * @return elementGroupId
   **/
  //@Schema(description = "Unique identifier of this group in the VNFD.")
  
    public String getElementGroupId() {
    return elementGroupId;
  }

  public void setElementGroupId(String elementGroupId) {
    this.elementGroupId = elementGroupId;
  }

  public ElementGroupSchema vdu(List<String> vdu) {
    this.vdu = vdu;
    return this;
  }

  public ElementGroupSchema addVduItem(String vduItem) {
    if (this.vdu == null) {
      this.vdu = new ArrayList<String>();
    }
    this.vdu.add(vduItem);
    return this;
  }

  /**
   * Get vdu
   * @return vdu
   **/
  //@Schema(description = "")
  
    public List<String> getVdu() {
    return vdu;
  }

  public void setVdu(List<String> vdu) {
    this.vdu = vdu;
  }

  public ElementGroupSchema description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Human readable description of the group.
   * @return description
   **/
  //@Schema(description = "Human readable description of the group.")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ElementGroupSchema elementGroupSchema = (ElementGroupSchema) o;
    return Objects.equals(this.virtualLinkDesc, elementGroupSchema.virtualLinkDesc) &&
        Objects.equals(this.elementGroupId, elementGroupSchema.elementGroupId) &&
        Objects.equals(this.vdu, elementGroupSchema.vdu) &&
        Objects.equals(this.description, elementGroupSchema.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualLinkDesc, elementGroupId, vdu, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ElementGroupSchema {\n");
    
    sb.append("    virtualLinkDesc: ").append(toIndentedString(virtualLinkDesc)).append("\n");
    sb.append("    elementGroupId: ").append(toIndentedString(elementGroupId)).append("\n");
    sb.append("    vdu: ").append(toIndentedString(vdu)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
