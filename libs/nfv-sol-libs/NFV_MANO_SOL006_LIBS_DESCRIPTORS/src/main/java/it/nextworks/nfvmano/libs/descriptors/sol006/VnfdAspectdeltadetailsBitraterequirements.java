package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * Bitrate requirements for an instantiation level or bitrate delta for a scaling step.
 */
//@Schema(description = "Bitrate requirements for an instantiation level or bitrate delta for a scaling step.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class VnfdAspectdeltadetailsBitraterequirements {

  @JsonProperty("leaf")
  private String leaf = null;

  @JsonProperty("root")
  private String root = null;

  public VnfdAspectdeltadetailsBitraterequirements leaf(String leaf) {
    this.leaf = leaf;
    return this;
  }

  /**
   * Throughput requirement of leaf connections to the link when applicable to the connectivity type (e.g. for E-Tree and E-LAN branches).
   * @return leaf
   **/
  //@Schema(description = "Throughput requirement of leaf connections to the link when applicable to the connectivity type (e.g. for E-Tree and E-LAN branches).")
  
    public String getLeaf() {
    return leaf;
  }

  public void setLeaf(String leaf) {
    this.leaf = leaf;
  }

  public VnfdAspectdeltadetailsBitraterequirements root(String root) {
    this.root = root;
    return this;
  }

  /**
   * Throughput requirement of the link (e.g. bitrate of E-Line, root bitrate of E-Tree, aggregate capacity of E-LAN).
   * @return root
   **/
  //@Schema(description = "Throughput requirement of the link (e.g. bitrate of E-Line, root bitrate of E-Tree, aggregate capacity of E-LAN).")
  
    public String getRoot() {
    return root;
  }

  public void setRoot(String root) {
    this.root = root;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdAspectdeltadetailsBitraterequirements vnfdAspectdeltadetailsBitraterequirements = (VnfdAspectdeltadetailsBitraterequirements) o;
    return Objects.equals(this.leaf, vnfdAspectdeltadetailsBitraterequirements.leaf) &&
        Objects.equals(this.root, vnfdAspectdeltadetailsBitraterequirements.root);
  }

  @Override
  public int hashCode() {
    return Objects.hash(leaf, root);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdAspectdeltadetailsBitraterequirements {\n");
    
    sb.append("    leaf: ").append(toIndentedString(leaf)).append("\n");
    sb.append("    root: ").append(toIndentedString(root)).append("\n");
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
