
# AutoscalingRuleCriteria

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  |  [optional]
**nsMonitoringParamRef** | **String** |  |  [optional]
**scaleInRelationalOperation** | [**ScaleInRelationalOperationEnum**](#ScaleInRelationalOperationEnum) |  |  [optional]
**scaleInThreshold** | **Integer** |  |  [optional]
**scaleOutRelationalOperation** | [**ScaleOutRelationalOperationEnum**](#ScaleOutRelationalOperationEnum) |  |  [optional]
**scaleOutThreshold** | **Integer** |  |  [optional]


<a name="ScaleInRelationalOperationEnum"></a>
## Enum: ScaleInRelationalOperationEnum
Name | Value
---- | -----
GE | &quot;GE&quot;
LE | &quot;LE&quot;
GT | &quot;GT&quot;
LT | &quot;LT&quot;
EQ | &quot;EQ&quot;


<a name="ScaleOutRelationalOperationEnum"></a>
## Enum: ScaleOutRelationalOperationEnum
Name | Value
---- | -----
GE | &quot;GE&quot;
LE | &quot;LE&quot;
GT | &quot;GT&quot;
LT | &quot;LT&quot;
EQ | &quot;EQ&quot;



