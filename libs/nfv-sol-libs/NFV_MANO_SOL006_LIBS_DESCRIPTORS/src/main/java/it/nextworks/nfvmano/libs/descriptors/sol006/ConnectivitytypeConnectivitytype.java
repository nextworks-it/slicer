package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;



/**
 * ConnectivitytypeConnectivitytype
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class ConnectivitytypeConnectivitytype   {
  /**
   * Identifies the flow pattern of the connectivity (Line, Tree, Mesh).
   */
  public enum FlowPatternEnum {
    LINE("line"),
    
    TREE("tree"),
    
    MESH("mesh");

    private String value;

    FlowPatternEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FlowPatternEnum fromValue(String text) {
      for (FlowPatternEnum b : FlowPatternEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("flow-pattern")
  private FlowPatternEnum flowPattern = null;

  @JsonProperty("layer-protocol")

  private List<String> layerProtocol = null;

  public ConnectivitytypeConnectivitytype flowPattern(FlowPatternEnum flowPattern) {
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

  public ConnectivitytypeConnectivitytype layerProtocol(List<String> layerProtocol) {
    this.layerProtocol = layerProtocol;
    return this;
  }

  public ConnectivitytypeConnectivitytype addLayerProtocolItem(String layerProtocolItem) {
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
    ConnectivitytypeConnectivitytype connectivitytypeConnectivitytype = (ConnectivitytypeConnectivitytype) o;
    return Objects.equals(this.flowPattern, connectivitytypeConnectivitytype.flowPattern) &&
        Objects.equals(this.layerProtocol, connectivitytypeConnectivitytype.layerProtocol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flowPattern, layerProtocol);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConnectivitytypeConnectivitytype {\n");
    
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
