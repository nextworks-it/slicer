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
import io.swagger.client.slice_manager.v2.model.PostRadioService;

/**
 * PostSlic3
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2022-05-17T10:16:59.588+02:00")
public class PostSlic3 {

  @JsonProperty("name")
  private String name = null;
  
  @JsonProperty("id")
  private String id = null;
  
  @JsonProperty("user_id")
  private String userId = null;
  
  @JsonProperty("chunks")
  private Object chunks = null;
  
  @JsonProperty("activation_status")
  private String activationStatus = null;
  
  @JsonProperty("radio_service")
  private PostRadioService radioService = null;
  
  public PostSlic3 name(String name) {
    this.name = name;
    return this;
  }

  
  /**
  * Get name
  * @return name
  **/
  @ApiModelProperty(example = "TestSlic3", value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public PostSlic3 id(String id) {
    this.id = id;
    return this;
  }

  
  /**
  * Get id
  * @return id
  **/
  @ApiModelProperty(example = "5b63089158f568073093f70d", value = "")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  
  public PostSlic3 userId(String userId) {
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
  
  public PostSlic3 chunks(Object chunks) {
    this.chunks = chunks;
    return this;
  }

  
  /**
  * Get chunks
  * @return chunks
  **/
  @ApiModelProperty(value = "")
  public Object getChunks() {
    return chunks;
  }
  public void setChunks(Object chunks) {
    this.chunks = chunks;
  }
  
  public PostSlic3 activationStatus(String activationStatus) {
    this.activationStatus = activationStatus;
    return this;
  }

  
  /**
  * Get activationStatus
  * @return activationStatus
  **/
  @ApiModelProperty(example = "pending|not-needed|configured", value = "")
  public String getActivationStatus() {
    return activationStatus;
  }
  public void setActivationStatus(String activationStatus) {
    this.activationStatus = activationStatus;
  }
  
  public PostSlic3 radioService(PostRadioService radioService) {
    this.radioService = radioService;
    return this;
  }

  
  /**
  * Get radioService
  * @return radioService
  **/
  @ApiModelProperty(value = "")
  public PostRadioService getRadioService() {
    return radioService;
  }
  public void setRadioService(PostRadioService radioService) {
    this.radioService = radioService;
  }
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostSlic3 postSlic3 = (PostSlic3) o;
    return Objects.equals(this.name, postSlic3.name) &&
        Objects.equals(this.id, postSlic3.id) &&
        Objects.equals(this.userId, postSlic3.userId) &&
        Objects.equals(this.chunks, postSlic3.chunks) &&
        Objects.equals(this.activationStatus, postSlic3.activationStatus) &&
        Objects.equals(this.radioService, postSlic3.radioService);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id, userId, chunks, activationStatus, radioService);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostSlic3 {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    chunks: ").append(toIndentedString(chunks)).append("\n");
    sb.append("    activationStatus: ").append(toIndentedString(activationStatus)).append("\n");
    sb.append("    radioService: ").append(toIndentedString(radioService)).append("\n");
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



