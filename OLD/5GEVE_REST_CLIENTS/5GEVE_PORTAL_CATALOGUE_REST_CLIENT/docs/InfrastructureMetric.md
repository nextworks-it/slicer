
# InfrastructureMetric

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**iMetricType** | [**IMetricTypeEnum**](#IMetricTypeEnum) |  |  [optional]
**interval** | **String** |  |  [optional]
**metricCollectionType** | [**MetricCollectionTypeEnum**](#MetricCollectionTypeEnum) |  |  [optional]
**metricGraphType** | [**MetricGraphTypeEnum**](#MetricGraphTypeEnum) |  |  [optional]
**metricId** | **String** |  |  [optional]
**name** | **String** |  |  [optional]
**unit** | **String** |  |  [optional]


<a name="IMetricTypeEnum"></a>
## Enum: IMetricTypeEnum
Name | Value
---- | -----
USER_DATA_RATE_DOWNLINK | &quot;USER_DATA_RATE_DOWNLINK&quot;
USER_DATA_RATE_UPLINK | &quot;USER_DATA_RATE_UPLINK&quot;
CAPACITY | &quot;CAPACITY&quot;
LATENCY_USERPLANE | &quot;LATENCY_USERPLANE&quot;
LATENCY_CONTROLPLANE | &quot;LATENCY_CONTROLPLANE&quot;
DEVICE_DENSITY | &quot;DEVICE_DENSITY&quot;
MOBILITY | &quot;MOBILITY&quot;


<a name="MetricCollectionTypeEnum"></a>
## Enum: MetricCollectionTypeEnum
Name | Value
---- | -----
CUMULATIVE | &quot;CUMULATIVE&quot;
DELTA | &quot;DELTA&quot;
GAUGE | &quot;GAUGE&quot;


<a name="MetricGraphTypeEnum"></a>
## Enum: MetricGraphTypeEnum
Name | Value
---- | -----
LINE | &quot;LINE&quot;
PIE | &quot;PIE&quot;
COUNTER | &quot;COUNTER&quot;
GAUGE | &quot;GAUGE&quot;



