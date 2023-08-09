package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * AdditionalConfigurablePropertySchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class AdditionalConfigurablePropertySchema {

  @JsonProperty("key")
  private String key = null;

  @JsonProperty("value")
  private String value = null;

  public AdditionalConfigurablePropertySchema key(String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   * @return key
   **/
  //@Schema(description = "")
  
    public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public AdditionalConfigurablePropertySchema value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   **/
  //@Schema(description = "")
  
    public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdditionalConfigurablePropertySchema additionalConfigurablePropertySchema = (AdditionalConfigurablePropertySchema) o;
    return Objects.equals(this.key, additionalConfigurablePropertySchema.key) &&
        Objects.equals(this.value, additionalConfigurablePropertySchema.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdditionalConfigurablePropertySchema {\n");
    
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
