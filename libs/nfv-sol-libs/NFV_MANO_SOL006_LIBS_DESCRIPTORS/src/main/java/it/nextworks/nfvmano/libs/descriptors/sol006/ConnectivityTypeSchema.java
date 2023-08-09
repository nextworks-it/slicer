package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.FlowPatternEnum;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

/**
 * ConnectivityTypeSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class ConnectivityTypeSchema {

  @JsonProperty("flow-pattern")
  private FlowPatternEnum flowPattern = null;

  @JsonProperty("layer-protocol")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> layerProtocol = null;

  public ConnectivityTypeSchema flowPattern(FlowPatternEnum flowPattern) {
    this.flowPattern = flowPattern;
    return this;
  }

  /**
   * Identifies the flow pattern of the connectivity (Line, Tree, Mesh).
   * @return flowPattern
   **/
  //@Schema(description = "Identifies the flow pattern of the connectivity (Line, Tree, Mesh).")
  
    public FlowPatternEnum getFlowPattern() {
    return flowPattern;
  }

  public void setFlowPattern(FlowPatternEnum flowPattern) {
    this.flowPattern = flowPattern;
  }

  public ConnectivityTypeSchema layerProtocol(List<String> layerProtocol) {
    this.layerProtocol = layerProtocol;
    return this;
  }

  public ConnectivityTypeSchema addLayerProtocolItem(String layerProtocolItem) {
    if (this.layerProtocol == null) {
      this.layerProtocol = new ArrayList<String>();
    }
    this.layerProtocol.add(layerProtocolItem);
    return this;
  }

  /**
   * Get layerProtocol
   * @return layerProtocol
   **/
  //@Schema(description = "")
  
    public List<String> getLayerProtocol() {
    return layerProtocol;
  }

  public void setLayerProtocol(List<String> layerProtocol) {
    this.layerProtocol = layerProtocol;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectivityTypeSchema connectivityTypeSchema = (ConnectivityTypeSchema) o;
    return Objects.equals(this.flowPattern, connectivityTypeSchema.flowPattern) &&
        Objects.equals(this.layerProtocol, connectivityTypeSchema.layerProtocol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flowPattern, layerProtocol);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConnectivityTypeSchema {\n");
    
    sb.append("    flowPattern: ").append(toIndentedString(flowPattern)).append("\n");
    sb.append("    layerProtocol: ").append(toIndentedString(layerProtocol)).append("\n");
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
