/*
 * Slice Manager API
 * This API provides interaction between the N-NaaS platform and the Slice Manager.
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.slice_manager.v2.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * RadioServiceInput
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2022-05-17T10:16:59.588+02:00")
public class RadioServiceInput {

  @JsonProperty("name")
  private String name = null;
  
  @JsonProperty("user_id")
  private String userId = null;
  
  @JsonProperty("slic3_id")
  private String slic3Id = null;
  
  @JsonProperty("wifi_config")
  private Object wifiConfig = null;
  
  @JsonProperty("selected_root_phy")
  private String selectedRootPhy = null;
  
  @JsonProperty("cellular_config")
  private Object cellularConfig = null;
  
  public RadioServiceInput name(String name) {
    this.name = name;
    return this;
  }

  
  /**
  * Get name
  * @return name
  **/
  @ApiModelProperty(example = "SS_02", value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public RadioServiceInput userId(String userId) {
    this.userId = userId;
    return this;
  }

  
  /**
  * Get userId
  * @return userId
  **/
  @ApiModelProperty(example = "5b63089158f568073093f70d", value = "")
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public RadioServiceInput slic3Id(String slic3Id) {
    this.slic3Id = slic3Id;
    return this;
  }

  
  /**
  * Get slic3Id
  * @return slic3Id
  **/
  @ApiModelProperty(example = "5d63089158f568073093f71e", value = "")
  public String getSlic3Id() {
    return slic3Id;
  }
  public void setSlic3Id(String slic3Id) {
    this.slic3Id = slic3Id;
  }
  
  public RadioServiceInput wifiConfig(Object wifiConfig) {
    this.wifiConfig = wifiConfig;
    return this;
  }

  
  /**
  * Get wifiConfig
  * @return wifiConfig
  **/
  @ApiModelProperty(value = "")
  public Object getWifiConfig() {
    return wifiConfig;
  }
  public void setWifiConfig(Object wifiConfig) {
    this.wifiConfig = wifiConfig;
  }
  
  public RadioServiceInput selectedRootPhy(String selectedRootPhy) {
    this.selectedRootPhy = selectedRootPhy;
    return this;
  }

  
  /**
  * Get selectedRootPhy
  * @return selectedRootPhy
  **/
  @ApiModelProperty(example = "f9af122a-c641-4084-ad61-2cdd9353fbc0", value = "")
  public String getSelectedRootPhy() {
    return selectedRootPhy;
  }
  public void setSelectedRootPhy(String selectedRootPhy) {
    this.selectedRootPhy = selectedRootPhy;
  }
  
  public RadioServiceInput cellularConfig(Object cellularConfig) {
    this.cellularConfig = cellularConfig;
    return this;
  }

  
  /**
  * Get cellularConfig
  * @return cellularConfig
  **/
  @ApiModelProperty(value = "")
  public Object getCellularConfig() {
    return cellularConfig;
  }
  public void setCellularConfig(Object cellularConfig) {
    this.cellularConfig = cellularConfig;
  }
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RadioServiceInput radioServiceInput = (RadioServiceInput) o;
    return Objects.equals(this.name, radioServiceInput.name) &&
        Objects.equals(this.userId, radioServiceInput.userId) &&
        Objects.equals(this.slic3Id, radioServiceInput.slic3Id) &&
        Objects.equals(this.wifiConfig, radioServiceInput.wifiConfig) &&
        Objects.equals(this.selectedRootPhy, radioServiceInput.selectedRootPhy) &&
        Objects.equals(this.cellularConfig, radioServiceInput.cellularConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, userId, slic3Id, wifiConfig, selectedRootPhy, cellularConfig);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RadioServiceInput {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    slic3Id: ").append(toIndentedString(slic3Id)).append("\n");
    sb.append("    wifiConfig: ").append(toIndentedString(wifiConfig)).append("\n");
    sb.append("    selectedRootPhy: ").append(toIndentedString(selectedRootPhy)).append("\n");
    sb.append("    cellularConfig: ").append(toIndentedString(cellularConfig)).append("\n");
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



