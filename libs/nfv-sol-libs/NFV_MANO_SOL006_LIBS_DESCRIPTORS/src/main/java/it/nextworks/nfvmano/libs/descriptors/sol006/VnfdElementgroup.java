package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * VnfdElementgroup
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdElementgroup {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("virtual-link-desc")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> virtualLinkDesc = null;

  @JsonProperty("vdu")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> vdu = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("description")
  private String description = null;

  public VnfdElementgroup virtualLinkDesc(List<String> virtualLinkDesc) {
    this.virtualLinkDesc = virtualLinkDesc;
    return this;
  }

  public VnfdElementgroup addVirtualLinkDescItem(String virtualLinkDescItem) {
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

  public VnfdElementgroup vdu(List<String> vdu) {
    this.vdu = vdu;
    return this;
  }

  public VnfdElementgroup addVduItem(String vduItem) {
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

  public VnfdElementgroup id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of this group in the VNFD.
   * @return id
   **/
  //@Schema(description = "Unique identifier of this group in the VNFD.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VnfdElementgroup description(String description) {
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
    VnfdElementgroup vnfdElementgroup = (VnfdElementgroup) o;
    return Objects.equals(this.virtualLinkDesc, vnfdElementgroup.virtualLinkDesc) &&
        Objects.equals(this.vdu, vnfdElementgroup.vdu) &&
        Objects.equals(this.id, vnfdElementgroup.id) &&
        Objects.equals(this.description, vnfdElementgroup.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualLinkDesc, vdu, id, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdElementgroup {\n");
    
    sb.append("    virtualLinkDesc: ").append(toIndentedString(virtualLinkDesc)).append("\n");
    sb.append("    vdu: ").append(toIndentedString(vdu)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
