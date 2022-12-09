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
 * PostRadioChunk
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2022-05-17T10:16:59.588+02:00")
public class PostRadioChunk {

  @JsonProperty("name")
  private String name = null;
  
  @JsonProperty("id")
  private String id = null;
  
  @JsonProperty("user_id")
  private String userId = null;
  
  @JsonProperty("ran_infrastructure_id")
  private String ranInfrastructureId = null;
  
  @JsonProperty("ran_controller_id")
  private String ranControllerId = null;
  
  @JsonProperty("chunk_topology")
  private RadioChunkTopology chunkTopology = null;
  
  public PostRadioChunk name(String name) {
    this.name = name;
    return this;
  }

  
  /**
  * Get name
  * @return name
  **/
  @ApiModelProperty(example = "RadioChunk", value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public PostRadioChunk id(String id) {
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
  
  public PostRadioChunk userId(String userId) {
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
  
  public PostRadioChunk ranInfrastructureId(String ranInfrastructureId) {
    this.ranInfrastructureId = ranInfrastructureId;
    return this;
  }

  
  /**
  * Get ranInfrastructureId
  * @return ranInfrastructureId
  **/
  @ApiModelProperty(example = "5b63089158f568073093f70d", value = "")
  public String getRanInfrastructureId() {
    return ranInfrastructureId;
  }
  public void setRanInfrastructureId(String ranInfrastructureId) {
    this.ranInfrastructureId = ranInfrastructureId;
  }
  
  public PostRadioChunk ranControllerId(String ranControllerId) {
    this.ranControllerId = ranControllerId;
    return this;
  }

  
  /**
  * Get ranControllerId
  * @return ranControllerId
  **/
  @ApiModelProperty(example = "3f677970-de73-45ec-a468-d03de0b81fb0", value = "")
  public String getRanControllerId() {
    return ranControllerId;
  }
  public void setRanControllerId(String ranControllerId) {
    this.ranControllerId = ranControllerId;
  }
  
  public PostRadioChunk chunkTopology(RadioChunkTopology chunkTopology) {
    this.chunkTopology = chunkTopology;
    return this;
  }

  
  /**
  * Get chunkTopology
  * @return chunkTopology
  **/
  @ApiModelProperty(value = "")
  public RadioChunkTopology getChunkTopology() {
    return chunkTopology;
  }
  public void setChunkTopology(RadioChunkTopology chunkTopology) {
    this.chunkTopology = chunkTopology;
  }
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostRadioChunk postRadioChunk = (PostRadioChunk) o;
    return Objects.equals(this.name, postRadioChunk.name) &&
        Objects.equals(this.id, postRadioChunk.id) &&
        Objects.equals(this.userId, postRadioChunk.userId) &&
        Objects.equals(this.ranInfrastructureId, postRadioChunk.ranInfrastructureId) &&
        Objects.equals(this.ranControllerId, postRadioChunk.ranControllerId) &&
        Objects.equals(this.chunkTopology, postRadioChunk.chunkTopology);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id, userId, ranInfrastructureId, ranControllerId, chunkTopology);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostRadioChunk {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    ranInfrastructureId: ").append(toIndentedString(ranInfrastructureId)).append("\n");
    sb.append("    ranControllerId: ").append(toIndentedString(ranControllerId)).append("\n");
    sb.append("    chunkTopology: ").append(toIndentedString(chunkTopology)).append("\n");
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



