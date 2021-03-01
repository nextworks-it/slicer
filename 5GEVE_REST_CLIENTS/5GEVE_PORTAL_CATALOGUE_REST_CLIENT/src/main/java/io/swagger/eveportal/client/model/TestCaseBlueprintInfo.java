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

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * TestCaseBlueprintInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class TestCaseBlueprintInfo {
  @SerializedName("activeTcdId")
  private List<String> activeTcdId = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("testCaseBlueprint")
  private TestCaseBlueprint testCaseBlueprint = null;

  @SerializedName("testCaseBlueprintId")
  private String testCaseBlueprintId = null;

  @SerializedName("version")
  private String version = null;

  public TestCaseBlueprintInfo activeTcdId(List<String> activeTcdId) {
    this.activeTcdId = activeTcdId;
    return this;
  }

  public TestCaseBlueprintInfo addActiveTcdIdItem(String activeTcdIdItem) {
    if (this.activeTcdId == null) {
      this.activeTcdId = new ArrayList<String>();
    }
    this.activeTcdId.add(activeTcdIdItem);
    return this;
  }

   /**
   * Get activeTcdId
   * @return activeTcdId
  **/
  @ApiModelProperty(value = "")
  public List<String> getActiveTcdId() {
    return activeTcdId;
  }

  public void setActiveTcdId(List<String> activeTcdId) {
    this.activeTcdId = activeTcdId;
  }

  public TestCaseBlueprintInfo name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TestCaseBlueprintInfo testCaseBlueprint(TestCaseBlueprint testCaseBlueprint) {
    this.testCaseBlueprint = testCaseBlueprint;
    return this;
  }

   /**
   * Get testCaseBlueprint
   * @return testCaseBlueprint
  **/
  @ApiModelProperty(value = "")
  public TestCaseBlueprint getTestCaseBlueprint() {
    return testCaseBlueprint;
  }

  public void setTestCaseBlueprint(TestCaseBlueprint testCaseBlueprint) {
    this.testCaseBlueprint = testCaseBlueprint;
  }

  public TestCaseBlueprintInfo testCaseBlueprintId(String testCaseBlueprintId) {
    this.testCaseBlueprintId = testCaseBlueprintId;
    return this;
  }

   /**
   * Get testCaseBlueprintId
   * @return testCaseBlueprintId
  **/
  @ApiModelProperty(value = "")
  public String getTestCaseBlueprintId() {
    return testCaseBlueprintId;
  }

  public void setTestCaseBlueprintId(String testCaseBlueprintId) {
    this.testCaseBlueprintId = testCaseBlueprintId;
  }

  public TestCaseBlueprintInfo version(String version) {
    this.version = version;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(value = "")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestCaseBlueprintInfo testCaseBlueprintInfo = (TestCaseBlueprintInfo) o;
    return Objects.equals(this.activeTcdId, testCaseBlueprintInfo.activeTcdId) &&
        Objects.equals(this.name, testCaseBlueprintInfo.name) &&
        Objects.equals(this.testCaseBlueprint, testCaseBlueprintInfo.testCaseBlueprint) &&
        Objects.equals(this.testCaseBlueprintId, testCaseBlueprintInfo.testCaseBlueprintId) &&
        Objects.equals(this.version, testCaseBlueprintInfo.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(activeTcdId, name, testCaseBlueprint, testCaseBlueprintId, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TestCaseBlueprintInfo {\n");
    
    sb.append("    activeTcdId: ").append(toIndentedString(activeTcdId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    testCaseBlueprint: ").append(toIndentedString(testCaseBlueprint)).append("\n");
    sb.append("    testCaseBlueprintId: ").append(toIndentedString(testCaseBlueprintId)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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
