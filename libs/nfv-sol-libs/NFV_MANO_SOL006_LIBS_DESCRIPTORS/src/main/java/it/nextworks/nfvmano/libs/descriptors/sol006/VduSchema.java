package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * VduSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class VduSchema   {
  @JsonProperty("virtual-storage-desc")

  private List<String> virtualStorageDesc = null;

  @JsonProperty("boot-data")
  private String bootData = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("vdu_id")
  private String vduId = null;

  @JsonProperty("int-cpd")

  private List<Cpd> intCpd = null;

  @JsonProperty("configurable-properties")

  private List<VnfdVdustoragerequirements> configurableProperties = null;

  @JsonProperty("nfvi-constraint")

  private List<String> nfviConstraint = null;

  @JsonProperty("boot-order")

  private List<VnfdBootorder> bootOrder = null;

  @JsonProperty("virtual-compute-desc")
  private String virtualComputeDesc = null;

  @JsonProperty("monitoring-parameter")

  private List<MonitoringParameter> monitoringParameter = null;

  @JsonProperty("sw-image-desc")
  private String swImageDesc = null;

  @JsonProperty("name")
  private String name = null;

  public VduSchema virtualStorageDesc(List<String> virtualStorageDesc) {
    this.virtualStorageDesc = virtualStorageDesc;
    return this;
  }

  public VduSchema addVirtualStorageDescItem(String virtualStorageDescItem) {
    if (this.virtualStorageDesc == null) {
      this.virtualStorageDesc = new ArrayList<String>();
    }
    this.virtualStorageDesc.add(virtualStorageDescItem);
    return this;
  }

  /**
   * Get virtualStorageDesc
   * @return virtualStorageDesc
   **/
  //@Schema(description = "")
  
    public List<String> getVirtualStorageDesc() {
    return virtualStorageDesc;
  }

  public void setVirtualStorageDesc(List<String> virtualStorageDesc) {
    this.virtualStorageDesc = virtualStorageDesc;
  }

  public VduSchema bootData(String bootData) {
    this.bootData = bootData;
    return this;
  }

  /**
   * Contains a string or a URL to a file contained in the VNF package used to customize a virtualised compute resource at boot time. The bootData may contain variable parts that are replaced by deployment specific values before being sent to the VIM.
   * @return bootData
   **/
  //@Schema(description = "Contains a string or a URL to a file contained in the VNF package used to customize a virtualised compute resource at boot time. The bootData may contain variable parts that are replaced by deployment specific values before being sent to the VIM.")
  
    public String getBootData() {
    return bootData;
  }

  public void setBootData(String bootData) {
    this.bootData = bootData;
  }

  public VduSchema description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Human readable description of the VDU.
   * @return description
   **/
  //@Schema(description = "Human readable description of the VDU.")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public VduSchema vduId(String vduId) {
    this.vduId = vduId;
    return this;
  }

  /**
   * Unique identifier of this VDU in VNFD.
   * @return vduId
   **/
  //@Schema(description = "Unique identifier of this VDU in VNFD.")
  
    public String getVduId() {
    return vduId;
  }

  public void setVduId(String vduId) {
    this.vduId = vduId;
  }

  public VduSchema intCpd(List<Cpd> intCpd) {
    this.intCpd = intCpd;
    return this;
  }

  public VduSchema addIntCpdItem(Cpd intCpdItem) {
    if (this.intCpd == null) {
      this.intCpd = new ArrayList<Cpd>();
    }
    this.intCpd.add(intCpdItem);
    return this;
  }

  /**
   * A internal-connection-point element is a type of connection point and describes network connectivity between a VDU instance and an internal Virtual Link or an external connection point.
   * @return intCpd
   **/
  //@Schema(description = "A internal-connection-point element is a type of connection point and describes network connectivity between a VDU instance and an internal Virtual Link or an external connection point.")

    public List<Cpd> getIntCpd() {
    return intCpd;
  }

  public void setIntCpd(List<Cpd> intCpd) {
    this.intCpd = intCpd;
  }

  public VduSchema configurableProperties(List<VnfdVdustoragerequirements> configurableProperties) {
    this.configurableProperties = configurableProperties;
    return this;
  }

  public VduSchema addConfigurablePropertiesItem(VnfdVdustoragerequirements configurablePropertiesItem) {
    if (this.configurableProperties == null) {
      this.configurableProperties = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.configurableProperties.add(configurablePropertiesItem);
    return this;
  }

  /**
   * It provides VNFC configurable properties that can be modified using the ModifyVnfInfo operation.
   * @return configurableProperties
   **/
  //@Schema(description = "It provides VNFC configurable properties that can be modified using the ModifyVnfInfo operation.")

    public List<VnfdVdustoragerequirements> getConfigurableProperties() {
    return configurableProperties;
  }

  public void setConfigurableProperties(List<VnfdVdustoragerequirements> configurableProperties) {
    this.configurableProperties = configurableProperties;
  }

  public VduSchema nfviConstraint(List<String> nfviConstraint) {
    this.nfviConstraint = nfviConstraint;
    return this;
  }

  public VduSchema addNfviConstraintItem(String nfviConstraintItem) {
    if (this.nfviConstraint == null) {
      this.nfviConstraint = new ArrayList<String>();
    }
    this.nfviConstraint.add(nfviConstraintItem);
    return this;
  }

  /**
   * Get nfviConstraint
   * @return nfviConstraint
   **/
  //@Schema(description = "")
  
    public List<String> getNfviConstraint() {
    return nfviConstraint;
  }

  public void setNfviConstraint(List<String> nfviConstraint) {
    this.nfviConstraint = nfviConstraint;
  }

  public VduSchema bootOrder(List<VnfdBootorder> bootOrder) {
    this.bootOrder = bootOrder;
    return this;
  }

  public VduSchema addBootOrderItem(VnfdBootorder bootOrderItem) {
    if (this.bootOrder == null) {
      this.bootOrder = new ArrayList<VnfdBootorder>();
    }
    this.bootOrder.add(bootOrderItem);
    return this;
  }

  /**
   * The key indicates the boot index (lowest index defines highest boot priority). The Value references a descriptor from which a valid boot device is created e.g. VirtualStorageDesc from which a VirtualStorage instance is created.  Editor's note: The boot-order node requires further study.
   * @return bootOrder
   **/
  //@Schema(description = "The key indicates the boot index (lowest index defines highest boot priority). The Value references a descriptor from which a valid boot device is created e.g. VirtualStorageDesc from which a VirtualStorage instance is created.  Editor's note: The boot-order node requires further study.")

    public List<VnfdBootorder> getBootOrder() {
    return bootOrder;
  }

  public void setBootOrder(List<VnfdBootorder> bootOrder) {
    this.bootOrder = bootOrder;
  }

  public VduSchema virtualComputeDesc(String virtualComputeDesc) {
    this.virtualComputeDesc = virtualComputeDesc;
    return this;
  }

  /**
   * Describes CPU, Memory and acceleration requirements of the Virtualisation Container realizing this VDU.
   * @return virtualComputeDesc
   **/
  //@Schema(description = "Describes CPU, Memory and acceleration requirements of the Virtualisation Container realizing this VDU.")
  
    public String getVirtualComputeDesc() {
    return virtualComputeDesc;
  }

  public void setVirtualComputeDesc(String virtualComputeDesc) {
    this.virtualComputeDesc = virtualComputeDesc;
  }

  public VduSchema monitoringParameter(List<MonitoringParameter> monitoringParameter) {
    this.monitoringParameter = monitoringParameter;
    return this;
  }

  public VduSchema addMonitoringParameterItem(MonitoringParameter monitoringParameterItem) {
    if (this.monitoringParameter == null) {
      this.monitoringParameter = new ArrayList<MonitoringParameter>();
    }
    this.monitoringParameter.add(monitoringParameterItem);
    return this;
  }

  /**
   * Get monitoringParameter
   * @return monitoringParameter
   **/
  //@Schema(description = "")

    public List<MonitoringParameter> getMonitoringParameter() {
    return monitoringParameter;
  }

  public void setMonitoringParameter(List<MonitoringParameter> monitoringParameter) {
    this.monitoringParameter = monitoringParameter;
  }

  public VduSchema swImageDesc(String swImageDesc) {
    this.swImageDesc = swImageDesc;
    return this;
  }

  /**
   * Describes the software image which is directly loaded on the virtualisation container realising this Vdu.
   * @return swImageDesc
   **/
  //@Schema(description = "Describes the software image which is directly loaded on the virtualisation container realising this Vdu.")
  
    public String getSwImageDesc() {
    return swImageDesc;
  }

  public void setSwImageDesc(String swImageDesc) {
    this.swImageDesc = swImageDesc;
  }

  public VduSchema name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Human readable name of the VDU.
   * @return name
   **/
  //@Schema(description = "Human readable name of the VDU.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VduSchema vduSchema = (VduSchema) o;
    return Objects.equals(this.virtualStorageDesc, vduSchema.virtualStorageDesc) &&
        Objects.equals(this.bootData, vduSchema.bootData) &&
        Objects.equals(this.description, vduSchema.description) &&
        Objects.equals(this.vduId, vduSchema.vduId) &&
        Objects.equals(this.intCpd, vduSchema.intCpd) &&
        Objects.equals(this.configurableProperties, vduSchema.configurableProperties) &&
        Objects.equals(this.nfviConstraint, vduSchema.nfviConstraint) &&
        Objects.equals(this.bootOrder, vduSchema.bootOrder) &&
        Objects.equals(this.virtualComputeDesc, vduSchema.virtualComputeDesc) &&
        Objects.equals(this.monitoringParameter, vduSchema.monitoringParameter) &&
        Objects.equals(this.swImageDesc, vduSchema.swImageDesc) &&
        Objects.equals(this.name, vduSchema.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualStorageDesc, bootData, description, vduId, intCpd, configurableProperties, nfviConstraint, bootOrder, virtualComputeDesc, monitoringParameter, swImageDesc, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VduSchema {\n");
    
    sb.append("    virtualStorageDesc: ").append(toIndentedString(virtualStorageDesc)).append("\n");
    sb.append("    bootData: ").append(toIndentedString(bootData)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    vduId: ").append(toIndentedString(vduId)).append("\n");
    sb.append("    intCpd: ").append(toIndentedString(intCpd)).append("\n");
    sb.append("    configurableProperties: ").append(toIndentedString(configurableProperties)).append("\n");
    sb.append("    nfviConstraint: ").append(toIndentedString(nfviConstraint)).append("\n");
    sb.append("    bootOrder: ").append(toIndentedString(bootOrder)).append("\n");
    sb.append("    virtualComputeDesc: ").append(toIndentedString(virtualComputeDesc)).append("\n");
    sb.append("    monitoringParameter: ").append(toIndentedString(monitoringParameter)).append("\n");
    sb.append("    swImageDesc: ").append(toIndentedString(swImageDesc)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
