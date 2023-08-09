package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;




/**
 * ConnectivityType
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class ConnectivityType   {
  @JsonProperty("connectivity-type")
  private ConnectivitytypeConnectivitytype connectivityType = null;

  public ConnectivityType connectivityType(ConnectivitytypeConnectivitytype connectivityType) {
    this.connectivityType = connectivityType;
    return this;
  }

  /**
   * Get connectivityType
   * @return connectivityType
   **/
  //@Schema(description = "")
  

    public ConnectivitytypeConnectivitytype getConnectivityType() {
    return connectivityType;
  }

  public void setConnectivityType(ConnectivitytypeConnectivitytype connectivityType) {
    this.connectivityType = connectivityType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectivityType connectivityType = (ConnectivityType) o;
    return Objects.equals(this.connectivityType, connectivityType.connectivityType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(connectivityType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConnectivityType {\n");
    
    sb.append("    connectivityType: ").append(toIndentedString(connectivityType)).append("\n");
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
