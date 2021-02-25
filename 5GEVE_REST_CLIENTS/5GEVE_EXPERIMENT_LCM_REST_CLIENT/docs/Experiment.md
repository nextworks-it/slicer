
# Experiment

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**currentExecutionId** | **String** |  |  [optional]
**errorMessage** | **String** |  |  [optional]
**executions** | [**List&lt;ExperimentExecution&gt;**](ExperimentExecution.md) |  |  [optional]
**experimentDescriptorId** | **String** |  |  [optional]
**experimentId** | **String** |  |  [optional]
**monitoringKpis** | [**List&lt;MonitoringDataItem&gt;**](MonitoringDataItem.md) |  |  [optional]
**monitoringMetrics** | [**List&lt;MonitoringDataItem&gt;**](MonitoringDataItem.md) |  |  [optional]
**name** | **String** |  |  [optional]
**nfvNsInstanceId** | **String** |  |  [optional]
**openTicketIds** | **List&lt;String&gt;** |  |  [optional]
**sapInfo** | [**List&lt;ExperimentSapInfo&gt;**](ExperimentSapInfo.md) |  |  [optional]
**status** | [**StatusEnum**](#StatusEnum) |  |  [optional]
**targetSites** | [**List&lt;TargetSitesEnum&gt;**](#List&lt;TargetSitesEnum&gt;) |  |  [optional]
**timeslot** | [**ExperimentExecutionTimeslot**](ExperimentExecutionTimeslot.md) |  |  [optional]
**useCase** | **String** |  |  [optional]


<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
SCHEDULING | &quot;SCHEDULING&quot;
ACCEPTED | &quot;ACCEPTED&quot;
READY | &quot;READY&quot;
INSTANTIATING | &quot;INSTANTIATING&quot;
INSTANTIATED | &quot;INSTANTIATED&quot;
RUNNING_EXECUTION | &quot;RUNNING_EXECUTION&quot;
TERMINATING | &quot;TERMINATING&quot;
TERMINATED | &quot;TERMINATED&quot;
FAILED | &quot;FAILED&quot;
REFUSED | &quot;REFUSED&quot;
ABORTED | &quot;ABORTED&quot;


<a name="List<TargetSitesEnum>"></a>
## Enum: List&lt;TargetSitesEnum&gt;
Name | Value
---- | -----
ITALY_TURIN | &quot;ITALY_TURIN&quot;
SPAIN_5TONIC | &quot;SPAIN_5TONIC&quot;
FRANCE_PARIS | &quot;FRANCE_PARIS&quot;
FRANCE_NICE | &quot;FRANCE_NICE&quot;
FRANCE_RENNES | &quot;FRANCE_RENNES&quot;
GREECE_ATHENS | &quot;GREECE_ATHENS&quot;



