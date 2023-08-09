package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

/**
 * LifecycleManagementScriptSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class LifecycleManagementScriptSchema   {
  @JsonProperty("event")
  private String event = null;

  @JsonProperty("script")
  private String script = null;

  public LifecycleManagementScriptSchema event(String event) {
    this.event = event;
    return this;
  }

  /**
   * Get event
   * @return event
   **/
  //@Schema(description = "")
  
    public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public LifecycleManagementScriptSchema script(String script) {
    this.script = script;
    return this;
  }

  /**
   * Get script
   * @return script
   **/
  //@Schema(description = "")
  
    public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LifecycleManagementScriptSchema lifecycleManagementScriptSchema = (LifecycleManagementScriptSchema) o;
    return Objects.equals(this.event, lifecycleManagementScriptSchema.event) &&
        Objects.equals(this.script, lifecycleManagementScriptSchema.script);
  }

  @Override
  public int hashCode() {
    return Objects.hash(event, script);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LifecycleManagementScriptSchema {\n");
    
    sb.append("    event: ").append(toIndentedString(event)).append("\n");
    sb.append("    script: ").append(toIndentedString(script)).append("\n");
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
