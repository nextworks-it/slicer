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
 * NsAutoscalingRule
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class NsAutoscalingRule {
  @SerializedName("ruleActions")
  private List<AutoscalingAction> ruleActions = null;

  @SerializedName("ruleCondition")
  private AutoscalingRuleCondition ruleCondition = null;

  @SerializedName("ruleId")
  private String ruleId = null;

  public NsAutoscalingRule ruleActions(List<AutoscalingAction> ruleActions) {
    this.ruleActions = ruleActions;
    return this;
  }

  public NsAutoscalingRule addRuleActionsItem(AutoscalingAction ruleActionsItem) {
    if (this.ruleActions == null) {
      this.ruleActions = new ArrayList<AutoscalingAction>();
    }
    this.ruleActions.add(ruleActionsItem);
    return this;
  }

   /**
   * Get ruleActions
   * @return ruleActions
  **/
  @ApiModelProperty(value = "")
  public List<AutoscalingAction> getRuleActions() {
    return ruleActions;
  }

  public void setRuleActions(List<AutoscalingAction> ruleActions) {
    this.ruleActions = ruleActions;
  }

  public NsAutoscalingRule ruleCondition(AutoscalingRuleCondition ruleCondition) {
    this.ruleCondition = ruleCondition;
    return this;
  }

   /**
   * Get ruleCondition
   * @return ruleCondition
  **/
  @ApiModelProperty(value = "")
  public AutoscalingRuleCondition getRuleCondition() {
    return ruleCondition;
  }

  public void setRuleCondition(AutoscalingRuleCondition ruleCondition) {
    this.ruleCondition = ruleCondition;
  }

  public NsAutoscalingRule ruleId(String ruleId) {
    this.ruleId = ruleId;
    return this;
  }

   /**
   * Get ruleId
   * @return ruleId
  **/
  @ApiModelProperty(value = "")
  public String getRuleId() {
    return ruleId;
  }

  public void setRuleId(String ruleId) {
    this.ruleId = ruleId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsAutoscalingRule nsAutoscalingRule = (NsAutoscalingRule) o;
    return Objects.equals(this.ruleActions, nsAutoscalingRule.ruleActions) &&
        Objects.equals(this.ruleCondition, nsAutoscalingRule.ruleCondition) &&
        Objects.equals(this.ruleId, nsAutoscalingRule.ruleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ruleActions, ruleCondition, ruleId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsAutoscalingRule {\n");
    
    sb.append("    ruleActions: ").append(toIndentedString(ruleActions)).append("\n");
    sb.append("    ruleCondition: ").append(toIndentedString(ruleCondition)).append("\n");
    sb.append("    ruleId: ").append(toIndentedString(ruleId)).append("\n");
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

