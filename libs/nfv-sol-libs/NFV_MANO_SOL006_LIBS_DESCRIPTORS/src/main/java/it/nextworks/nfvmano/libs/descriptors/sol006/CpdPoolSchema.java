package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;




/**
 * CpdPoolSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class CpdPoolSchema   {
  @JsonProperty("cpd-pool_id")
  private String cpdPoolId = null;

  @JsonProperty("constituent-cpd-id")
  private NsdConstituentcpdid constituentCpdId = null;

  @JsonProperty("constituent-base-element-id")
  private NsdConstituentBaseElementId constituentBaseElementId = null;

  public CpdPoolSchema cpdPoolId(String cpdPoolId) {
    this.cpdPoolId = cpdPoolId;
    return this;
  }

  /**
   * Get cpdPoolId
   * @return cpdPoolId
   **/
  //@Schema(description = "")
  
    public String getCpdPoolId() {
    return cpdPoolId;
  }

  public void setCpdPoolId(String cpdPoolId) {
    this.cpdPoolId = cpdPoolId;
  }

  public CpdPoolSchema constituentCpdId(NsdConstituentcpdid constituentCpdId) {
    this.constituentCpdId = constituentCpdId;
    return this;
  }

  /**
   * Get constituentCpdId
   * @return constituentCpdId
   **/
  //@Schema(description = "")
  

    public NsdConstituentcpdid getConstituentCpdId() {
    return constituentCpdId;
  }

  public void setConstituentCpdId(NsdConstituentcpdid constituentCpdId) {
    this.constituentCpdId = constituentCpdId;
  }

  public CpdPoolSchema constituentBaseElementId(NsdConstituentBaseElementId constituentBaseElementId) {
    this.constituentBaseElementId = constituentBaseElementId;
    return this;
  }

  /**
   * Get constituentBaseElementId
   * @return constituentBaseElementId
   **/
  //@Schema(description = "")
  

    public NsdConstituentBaseElementId getConstituentBaseElementId() {
    return constituentBaseElementId;
  }

  public void setConstituentBaseElementId(NsdConstituentBaseElementId constituentBaseElementId) {
    this.constituentBaseElementId = constituentBaseElementId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CpdPoolSchema cpdPoolSchema = (CpdPoolSchema) o;
    return Objects.equals(this.cpdPoolId, cpdPoolSchema.cpdPoolId) &&
        Objects.equals(this.constituentCpdId, cpdPoolSchema.constituentCpdId) &&
        Objects.equals(this.constituentBaseElementId, cpdPoolSchema.constituentBaseElementId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpdPoolId, constituentCpdId, constituentBaseElementId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CpdPoolSchema {\n");
    
    sb.append("    cpdPoolId: ").append(toIndentedString(cpdPoolId)).append("\n");
    sb.append("    constituentCpdId: ").append(toIndentedString(constituentCpdId)).append("\n");
    sb.append("    constituentBaseElementId: ").append(toIndentedString(constituentBaseElementId)).append("\n");
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
