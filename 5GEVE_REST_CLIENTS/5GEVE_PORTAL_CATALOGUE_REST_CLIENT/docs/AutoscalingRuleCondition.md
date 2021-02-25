
# AutoscalingRuleCondition

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**cooldownTime** | **Integer** |  |  [optional]
**enabled** | **Boolean** |  |  [optional]
**initialInstantiationLevel** | **String** |  |  [optional]
**name** | **String** |  |  [optional]
**scaleInOperationType** | [**ScaleInOperationTypeEnum**](#ScaleInOperationTypeEnum) |  |  [optional]
**scaleOutOperationType** | [**ScaleOutOperationTypeEnum**](#ScaleOutOperationTypeEnum) |  |  [optional]
**scalingCriteria** | [**List&lt;AutoscalingRuleCriteria&gt;**](AutoscalingRuleCriteria.md) |  |  [optional]
**scalingType** | [**ScalingTypeEnum**](#ScalingTypeEnum) |  |  [optional]
**thresholdTime** | **Integer** |  |  [optional]


<a name="ScaleInOperationTypeEnum"></a>
## Enum: ScaleInOperationTypeEnum
Name | Value
---- | -----
AND | &quot;AND&quot;
OR | &quot;OR&quot;


<a name="ScaleOutOperationTypeEnum"></a>
## Enum: ScaleOutOperationTypeEnum
Name | Value
---- | -----
AND | &quot;AND&quot;
OR | &quot;OR&quot;


<a name="ScalingTypeEnum"></a>
## Enum: ScalingTypeEnum
Name | Value
---- | -----
MANUAL_SCALING | &quot;MANUAL_SCALING&quot;
AUTOMATED_SCALING | &quot;AUTOMATED_SCALING&quot;



