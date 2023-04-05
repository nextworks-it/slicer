
# ExperimentExecution

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**executionId** | **String** |  |  [optional]
**executionName** | **String** |  |  [optional]
**reportUrl** | **String** |  |  [optional]
**state** | [**StateEnum**](#StateEnum) |  |  [optional]
**testCaseDescriptorConfiguration** | [**List&lt;TestCaseExecutionConfiguration&gt;**](TestCaseExecutionConfiguration.md) |  |  [optional]
**testCaseResult** | [**Map&lt;String, ExecutionResult&gt;**](ExecutionResult.md) |  |  [optional]


<a name="StateEnum"></a>
## Enum: StateEnum
Name | Value
---- | -----
INIT | &quot;INIT&quot;
CONFIGURING | &quot;CONFIGURING&quot;
RUNNING | &quot;RUNNING&quot;
RUNNING_STEP | &quot;RUNNING_STEP&quot;
PAUSED | &quot;PAUSED&quot;
VALIDATING | &quot;VALIDATING&quot;
COMPLETED | &quot;COMPLETED&quot;
ABORTING | &quot;ABORTING&quot;
ABORTED | &quot;ABORTED&quot;
FAILED | &quot;FAILED&quot;



