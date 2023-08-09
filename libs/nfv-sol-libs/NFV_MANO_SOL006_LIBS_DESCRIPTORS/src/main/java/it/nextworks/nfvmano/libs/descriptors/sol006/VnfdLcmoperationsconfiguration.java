package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * This information element is a container for all attributes that affect the invocation of the VNF Lifecycle Management operations, structured by operation.
 */
//@Schema(description = "This information element is a container for all attributes that affect the invocation of the VNF Lifecycle Management operations, structured by operation.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdLcmoperationsconfiguration {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("instantiate-vnf-op-config")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_lcm_ops_config_instantiate_vnf_op_config_fk", referencedColumnName = "uuid")
  private VnfdLcmoperationsconfigurationInstantiatevnfopconfig instantiateVnfOpConfig = null;

  @JsonProperty("terminate-vnf-op-config")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_lcm_ops_config_terminate_vnf_op_config_fk", referencedColumnName = "uuid")
  private VnfdLcmoperationsconfigurationTerminatevnfopconfig terminateVnfOpConfig = null;

  @JsonProperty("scale-vnf-to-level-op-config")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_lcm_ops_config_scale_vnf_to_level_op_config_fk", referencedColumnName = "uuid")
  private VnfdLcmoperationsconfigurationScalevnftolevelopconfig scaleVnfToLevelOpConfig = null;

  @JsonProperty("operate-vnf-op-config")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_lcm_ops_config_operate_vnf_op_config_fk", referencedColumnName = "uuid")
  private VnfdLcmoperationsconfigurationOperatevnfopconfig operateVnfOpConfig = null;

  @JsonProperty("scale-vnf-op-config")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_lcm_ops_config_scale_vnf_op_config_fk", referencedColumnName = "uuid")
  private VnfdLcmoperationsconfigurationScalevnfopconfig scaleVnfOpConfig = null;

  @JsonProperty("change-vnf-flavour-op-config")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_lcm_ops_config_change_vnf_flavour_op_config_fk", referencedColumnName = "uuid")
  private VnfdLcmoperationsconfigurationChangevnfflavouropconfig changeVnfFlavourOpConfig = null;

  @JsonProperty("heal-vnf-op-config")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_lcm_ops_config_heal_vnf_op_config_fk", referencedColumnName = "uuid")
  private VnfdLcmoperationsconfigurationHealvnfopconfig healVnfOpConfig = null;

  @JsonProperty("change-ext-vnf-connectivity-op-config")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_lcm_ops_config_change_ext_vnf_conn_op_config_fk", referencedColumnName = "uuid")
  private VnfdLcmoperationsconfigurationChangeextvnfconnectivityopconfig changeExtVnfConnectivityOpConfig = null;

  public VnfdLcmoperationsconfiguration instantiateVnfOpConfig(VnfdLcmoperationsconfigurationInstantiatevnfopconfig instantiateVnfOpConfig) {
    this.instantiateVnfOpConfig = instantiateVnfOpConfig;
    return this;
  }

  /**
   * Get instantiateVnfOpConfig
   * @return instantiateVnfOpConfig
   **/
  //@Schema(description = "")
  

    public VnfdLcmoperationsconfigurationInstantiatevnfopconfig getInstantiateVnfOpConfig() {
    return instantiateVnfOpConfig;
  }

  public void setInstantiateVnfOpConfig(VnfdLcmoperationsconfigurationInstantiatevnfopconfig instantiateVnfOpConfig) {
    this.instantiateVnfOpConfig = instantiateVnfOpConfig;
  }

  public VnfdLcmoperationsconfiguration terminateVnfOpConfig(VnfdLcmoperationsconfigurationTerminatevnfopconfig terminateVnfOpConfig) {
    this.terminateVnfOpConfig = terminateVnfOpConfig;
    return this;
  }

  /**
   * Get terminateVnfOpConfig
   * @return terminateVnfOpConfig
   **/
  //@Schema(description = "")
  

    public VnfdLcmoperationsconfigurationTerminatevnfopconfig getTerminateVnfOpConfig() {
    return terminateVnfOpConfig;
  }

  public void setTerminateVnfOpConfig(VnfdLcmoperationsconfigurationTerminatevnfopconfig terminateVnfOpConfig) {
    this.terminateVnfOpConfig = terminateVnfOpConfig;
  }

  public VnfdLcmoperationsconfiguration scaleVnfToLevelOpConfig(VnfdLcmoperationsconfigurationScalevnftolevelopconfig scaleVnfToLevelOpConfig) {
    this.scaleVnfToLevelOpConfig = scaleVnfToLevelOpConfig;
    return this;
  }

  /**
   * Get scaleVnfToLevelOpConfig
   * @return scaleVnfToLevelOpConfig
   **/
  //@Schema(description = "")
  

    public VnfdLcmoperationsconfigurationScalevnftolevelopconfig getScaleVnfToLevelOpConfig() {
    return scaleVnfToLevelOpConfig;
  }

  public void setScaleVnfToLevelOpConfig(VnfdLcmoperationsconfigurationScalevnftolevelopconfig scaleVnfToLevelOpConfig) {
    this.scaleVnfToLevelOpConfig = scaleVnfToLevelOpConfig;
  }

  public VnfdLcmoperationsconfiguration operateVnfOpConfig(VnfdLcmoperationsconfigurationOperatevnfopconfig operateVnfOpConfig) {
    this.operateVnfOpConfig = operateVnfOpConfig;
    return this;
  }

  /**
   * Get operateVnfOpConfig
   * @return operateVnfOpConfig
   **/
  //@Schema(description = "")
  

    public VnfdLcmoperationsconfigurationOperatevnfopconfig getOperateVnfOpConfig() {
    return operateVnfOpConfig;
  }

  public void setOperateVnfOpConfig(VnfdLcmoperationsconfigurationOperatevnfopconfig operateVnfOpConfig) {
    this.operateVnfOpConfig = operateVnfOpConfig;
  }

  public VnfdLcmoperationsconfiguration scaleVnfOpConfig(VnfdLcmoperationsconfigurationScalevnfopconfig scaleVnfOpConfig) {
    this.scaleVnfOpConfig = scaleVnfOpConfig;
    return this;
  }

  /**
   * Get scaleVnfOpConfig
   * @return scaleVnfOpConfig
   **/
  //@Schema(description = "")
  

    public VnfdLcmoperationsconfigurationScalevnfopconfig getScaleVnfOpConfig() {
    return scaleVnfOpConfig;
  }

  public void setScaleVnfOpConfig(VnfdLcmoperationsconfigurationScalevnfopconfig scaleVnfOpConfig) {
    this.scaleVnfOpConfig = scaleVnfOpConfig;
  }

  public VnfdLcmoperationsconfiguration changeVnfFlavourOpConfig(VnfdLcmoperationsconfigurationChangevnfflavouropconfig changeVnfFlavourOpConfig) {
    this.changeVnfFlavourOpConfig = changeVnfFlavourOpConfig;
    return this;
  }

  /**
   * Get changeVnfFlavourOpConfig
   * @return changeVnfFlavourOpConfig
   **/
  //@Schema(description = "")
  

    public VnfdLcmoperationsconfigurationChangevnfflavouropconfig getChangeVnfFlavourOpConfig() {
    return changeVnfFlavourOpConfig;
  }

  public void setChangeVnfFlavourOpConfig(VnfdLcmoperationsconfigurationChangevnfflavouropconfig changeVnfFlavourOpConfig) {
    this.changeVnfFlavourOpConfig = changeVnfFlavourOpConfig;
  }

  public VnfdLcmoperationsconfiguration healVnfOpConfig(VnfdLcmoperationsconfigurationHealvnfopconfig healVnfOpConfig) {
    this.healVnfOpConfig = healVnfOpConfig;
    return this;
  }

  /**
   * Get healVnfOpConfig
   * @return healVnfOpConfig
   **/
  //@Schema(description = "")
  

    public VnfdLcmoperationsconfigurationHealvnfopconfig getHealVnfOpConfig() {
    return healVnfOpConfig;
  }

  public void setHealVnfOpConfig(VnfdLcmoperationsconfigurationHealvnfopconfig healVnfOpConfig) {
    this.healVnfOpConfig = healVnfOpConfig;
  }

  public VnfdLcmoperationsconfiguration changeExtVnfConnectivityOpConfig(VnfdLcmoperationsconfigurationChangeextvnfconnectivityopconfig changeExtVnfConnectivityOpConfig) {
    this.changeExtVnfConnectivityOpConfig = changeExtVnfConnectivityOpConfig;
    return this;
  }

  /**
   * Get changeExtVnfConnectivityOpConfig
   * @return changeExtVnfConnectivityOpConfig
   **/
  //@Schema(description = "")
  

    public VnfdLcmoperationsconfigurationChangeextvnfconnectivityopconfig getChangeExtVnfConnectivityOpConfig() {
    return changeExtVnfConnectivityOpConfig;
  }

  public void setChangeExtVnfConnectivityOpConfig(VnfdLcmoperationsconfigurationChangeextvnfconnectivityopconfig changeExtVnfConnectivityOpConfig) {
    this.changeExtVnfConnectivityOpConfig = changeExtVnfConnectivityOpConfig;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdLcmoperationsconfiguration vnfdLcmoperationsconfiguration = (VnfdLcmoperationsconfiguration) o;
    return Objects.equals(this.instantiateVnfOpConfig, vnfdLcmoperationsconfiguration.instantiateVnfOpConfig) &&
        Objects.equals(this.terminateVnfOpConfig, vnfdLcmoperationsconfiguration.terminateVnfOpConfig) &&
        Objects.equals(this.scaleVnfToLevelOpConfig, vnfdLcmoperationsconfiguration.scaleVnfToLevelOpConfig) &&
        Objects.equals(this.operateVnfOpConfig, vnfdLcmoperationsconfiguration.operateVnfOpConfig) &&
        Objects.equals(this.scaleVnfOpConfig, vnfdLcmoperationsconfiguration.scaleVnfOpConfig) &&
        Objects.equals(this.changeVnfFlavourOpConfig, vnfdLcmoperationsconfiguration.changeVnfFlavourOpConfig) &&
        Objects.equals(this.healVnfOpConfig, vnfdLcmoperationsconfiguration.healVnfOpConfig) &&
        Objects.equals(this.changeExtVnfConnectivityOpConfig, vnfdLcmoperationsconfiguration.changeExtVnfConnectivityOpConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instantiateVnfOpConfig, terminateVnfOpConfig, scaleVnfToLevelOpConfig, operateVnfOpConfig, scaleVnfOpConfig, changeVnfFlavourOpConfig, healVnfOpConfig, changeExtVnfConnectivityOpConfig);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdLcmoperationsconfiguration {\n");
    
    sb.append("    instantiateVnfOpConfig: ").append(toIndentedString(instantiateVnfOpConfig)).append("\n");
    sb.append("    terminateVnfOpConfig: ").append(toIndentedString(terminateVnfOpConfig)).append("\n");
    sb.append("    scaleVnfToLevelOpConfig: ").append(toIndentedString(scaleVnfToLevelOpConfig)).append("\n");
    sb.append("    operateVnfOpConfig: ").append(toIndentedString(operateVnfOpConfig)).append("\n");
    sb.append("    scaleVnfOpConfig: ").append(toIndentedString(scaleVnfOpConfig)).append("\n");
    sb.append("    changeVnfFlavourOpConfig: ").append(toIndentedString(changeVnfFlavourOpConfig)).append("\n");
    sb.append("    healVnfOpConfig: ").append(toIndentedString(healVnfOpConfig)).append("\n");
    sb.append("    changeExtVnfConnectivityOpConfig: ").append(toIndentedString(changeExtVnfConnectivityOpConfig)).append("\n");
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
