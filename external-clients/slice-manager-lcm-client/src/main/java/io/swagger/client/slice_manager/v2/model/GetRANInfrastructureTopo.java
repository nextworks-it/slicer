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
import java.util.ArrayList;
import java.util.List;

/**
 * GetRANInfrastructureTopo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2022-05-17T10:16:59.588+02:00")
public class GetRANInfrastructureTopo {

  @JsonProperty("links")
  private List<Object> links = null;
  
  @JsonProperty("boxes")
  private List<Object> boxes = null;
  
  public GetRANInfrastructureTopo links(List<Object> links) {
    this.links = links;
    return this;
  }

  public GetRANInfrastructureTopo addLinksItem(Object linksItem) {
    
    if (this.links == null) {
      this.links = new ArrayList<Object>();
    }
    
    this.links.add(linksItem);
    return this;
  }
  
  /**
  * Get links
  * @return links
  **/
  @ApiModelProperty(value = "")
  public List<Object> getLinks() {
    return links;
  }
  public void setLinks(List<Object> links) {
    this.links = links;
  }
  
  public GetRANInfrastructureTopo boxes(List<Object> boxes) {
    this.boxes = boxes;
    return this;
  }

  public GetRANInfrastructureTopo addBoxesItem(Object boxesItem) {
    
    if (this.boxes == null) {
      this.boxes = new ArrayList<Object>();
    }
    
    this.boxes.add(boxesItem);
    return this;
  }
  
  /**
  * Get boxes
  * @return boxes
  **/
  @ApiModelProperty(value = "")
  public List<Object> getBoxes() {
    return boxes;
  }
  public void setBoxes(List<Object> boxes) {
    this.boxes = boxes;
  }
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetRANInfrastructureTopo getRANInfrastructureTopo = (GetRANInfrastructureTopo) o;
    return Objects.equals(this.links, getRANInfrastructureTopo.links) &&
        Objects.equals(this.boxes, getRANInfrastructureTopo.boxes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, boxes);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetRANInfrastructureTopo {\n");
    
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    boxes: ").append(toIndentedString(boxes)).append("\n");
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



