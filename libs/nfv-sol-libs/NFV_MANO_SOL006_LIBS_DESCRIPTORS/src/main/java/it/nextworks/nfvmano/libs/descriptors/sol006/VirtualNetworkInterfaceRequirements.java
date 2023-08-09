package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * VirtualNetworkInterfaceRequirements
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class VirtualNetworkInterfaceRequirements   {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("virtual-network-interface-requirement")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "virtual_network_interface_requirements_fk", referencedColumnName = "uuid")
  private List<VirtualNetworkInterfaceRequirementSchema> virtualNetworkInterfaceRequirement = null;

  public VirtualNetworkInterfaceRequirements virtualNetworkInterfaceRequirement(List<VirtualNetworkInterfaceRequirementSchema> virtualNetworkInterfaceRequirement) {
    this.virtualNetworkInterfaceRequirement = virtualNetworkInterfaceRequirement;
    return this;
  }

  public VirtualNetworkInterfaceRequirements addVirtualNetworkInterfaceRequirementItem(VirtualNetworkInterfaceRequirementSchema virtualNetworkInterfaceRequirementItem) {
    if (this.virtualNetworkInterfaceRequirement == null) {
      this.virtualNetworkInterfaceRequirement = new ArrayList<VirtualNetworkInterfaceRequirementSchema>();
    }
    this.virtualNetworkInterfaceRequirement.add(virtualNetworkInterfaceRequirementItem);
    return this;
  }

  /**
   * Specifies requirements on a virtual network interface realising the CPs instantiated from this CPD.
   * @return virtualNetworkInterfaceRequirement
   **/

    public List<VirtualNetworkInterfaceRequirementSchema> getVirtualNetworkInterfaceRequirement() {
    return virtualNetworkInterfaceRequirement;
  }

  public void setVirtualNetworkInterfaceRequirement(List<VirtualNetworkInterfaceRequirementSchema> virtualNetworkInterfaceRequirement) {
    this.virtualNetworkInterfaceRequirement = virtualNetworkInterfaceRequirement;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualNetworkInterfaceRequirements virtualNetworkInterfaceRequirements = (VirtualNetworkInterfaceRequirements) o;
    return Objects.equals(this.virtualNetworkInterfaceRequirement, virtualNetworkInterfaceRequirements.virtualNetworkInterfaceRequirement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualNetworkInterfaceRequirement);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualNetworkInterfaceRequirements {\n");
    
    sb.append("    virtualNetworkInterfaceRequirement: ").append(toIndentedString(virtualNetworkInterfaceRequirement)).append("\n");
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
