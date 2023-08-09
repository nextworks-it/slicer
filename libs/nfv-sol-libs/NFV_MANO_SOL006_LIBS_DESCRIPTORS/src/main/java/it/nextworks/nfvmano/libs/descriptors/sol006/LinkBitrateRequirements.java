package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * LinkBitrateRequirements
 */

@Entity
public class LinkBitrateRequirements {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("leaf")
  private String leaf = null;

  @JsonProperty("root")
  private String root = null;

  public LinkBitrateRequirements leaf(String leaf) {
    this.leaf = leaf;
    return this;
  }

  /**
   * Specifies the throughput requirement of leaf connections to the link when applicable to the connectivity type (e.g. for E-Tree and E-LAN branches).
   * @return leaf
   **/

  
    public String getLeaf() {
    return leaf;
  }

  public void setLeaf(String leaf) {
    this.leaf = leaf;
  }

  public LinkBitrateRequirements root(String root) {
    this.root = root;
    return this;
  }

  /**
   * Specifies the throughput requirement of the link (e.g. bitrate of E-Line, root bitrate of E-Tree, aggregate capacity of E-LAN).
   * @return root
   **/

  
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
    LinkBitrateRequirements linkBitrateRequirements = (LinkBitrateRequirements) o;
    return Objects.equals(this.leaf, linkBitrateRequirements.leaf) &&
        Objects.equals(this.root, linkBitrateRequirements.root);
  }

  @Override
  public int hashCode() {
    return Objects.hash(leaf, root);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LinkBitrateRequirements {\n");
    
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
