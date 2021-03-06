/*
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.eveportal.client.model;

import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * AffinityOrAntiAffinityGroup
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class AffinityOrAntiAffinityGroup {
  /**
   * Gets or Sets affinityOrAntiAffinity
   */
  @JsonAdapter(AffinityOrAntiAffinityEnum.Adapter.class)
  public enum AffinityOrAntiAffinityEnum {
    AFFINITY("AFFINITY"),
    
    ANTI_AFFINITY("ANTI_AFFINITY");

    private String value;

    AffinityOrAntiAffinityEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AffinityOrAntiAffinityEnum fromValue(String text) {
      for (AffinityOrAntiAffinityEnum b : AffinityOrAntiAffinityEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<AffinityOrAntiAffinityEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AffinityOrAntiAffinityEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AffinityOrAntiAffinityEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return AffinityOrAntiAffinityEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("affinityOrAntiAffinity")
  private AffinityOrAntiAffinityEnum affinityOrAntiAffinity = null;

  @SerializedName("groupId")
  private String groupId = null;

  /**
   * Gets or Sets scope
   */
  @JsonAdapter(ScopeEnum.Adapter.class)
  public enum ScopeEnum {
    NFVI_NODE("NFVI_NODE"),
    
    NFVI_POP("NFVI_POP"),
    
    ZONE("ZONE"),
    
    ZONE_GROUP("ZONE_GROUP");

    private String value;

    ScopeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ScopeEnum fromValue(String text) {
      for (ScopeEnum b : ScopeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ScopeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ScopeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ScopeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ScopeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("scope")
  private ScopeEnum scope = null;

  public AffinityOrAntiAffinityGroup affinityOrAntiAffinity(AffinityOrAntiAffinityEnum affinityOrAntiAffinity) {
    this.affinityOrAntiAffinity = affinityOrAntiAffinity;
    return this;
  }

   /**
   * Get affinityOrAntiAffinity
   * @return affinityOrAntiAffinity
  **/
  @ApiModelProperty(value = "")
  public AffinityOrAntiAffinityEnum getAffinityOrAntiAffinity() {
    return affinityOrAntiAffinity;
  }

  public void setAffinityOrAntiAffinity(AffinityOrAntiAffinityEnum affinityOrAntiAffinity) {
    this.affinityOrAntiAffinity = affinityOrAntiAffinity;
  }

  public AffinityOrAntiAffinityGroup groupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

   /**
   * Get groupId
   * @return groupId
  **/
  @ApiModelProperty(value = "")
  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public AffinityOrAntiAffinityGroup scope(ScopeEnum scope) {
    this.scope = scope;
    return this;
  }

   /**
   * Get scope
   * @return scope
  **/
  @ApiModelProperty(value = "")
  public ScopeEnum getScope() {
    return scope;
  }

  public void setScope(ScopeEnum scope) {
    this.scope = scope;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AffinityOrAntiAffinityGroup affinityOrAntiAffinityGroup = (AffinityOrAntiAffinityGroup) o;
    return Objects.equals(this.affinityOrAntiAffinity, affinityOrAntiAffinityGroup.affinityOrAntiAffinity) &&
        Objects.equals(this.groupId, affinityOrAntiAffinityGroup.groupId) &&
        Objects.equals(this.scope, affinityOrAntiAffinityGroup.scope);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityOrAntiAffinity, groupId, scope);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AffinityOrAntiAffinityGroup {\n");
    
    sb.append("    affinityOrAntiAffinity: ").append(toIndentedString(affinityOrAntiAffinity)).append("\n");
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
    sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
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

