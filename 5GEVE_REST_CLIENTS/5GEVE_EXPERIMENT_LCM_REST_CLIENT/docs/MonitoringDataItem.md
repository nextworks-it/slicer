
# MonitoringDataItem

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**dataItemString** | **String** |  |  [optional]
**expId** | **String** |  |  [optional]
**mdName** | **String** |  |  [optional]
**mdt** | [**MdtEnum**](#MdtEnum) |  |  [optional]
**metricCollectionType** | [**MetricCollectionTypeEnum**](#MetricCollectionTypeEnum) |  |  [optional]
**metricGraphType** | [**MetricGraphTypeEnum**](#MetricGraphTypeEnum) |  |  [optional]
**metricInterval** | **String** |  |  [optional]
**metricName** | **String** |  |  [optional]
**metricUnit** | **String** |  |  [optional]
**site** | [**SiteEnum**](#SiteEnum) |  |  [optional]


<a name="MdtEnum"></a>
## Enum: MdtEnum
Name | Value
---- | -----
APPLICATION_METRIC | &quot;APPLICATION_METRIC&quot;
INFRASTRUCTURE_METRIC | &quot;INFRASTRUCTURE_METRIC&quot;
KPI | &quot;KPI&quot;
RESULT | &quot;RESULT&quot;


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


<a name="SiteEnum"></a>
## Enum: SiteEnum
Name | Value
---- | -----
ITALY_TURIN | &quot;ITALY_TURIN&quot;
SPAIN_5TONIC | &quot;SPAIN_5TONIC&quot;
FRANCE_PARIS | &quot;FRANCE_PARIS&quot;
FRANCE_NICE | &quot;FRANCE_NICE&quot;
FRANCE_RENNES | &quot;FRANCE_RENNES&quot;
GREECE_ATHENS | &quot;GREECE_ATHENS&quot;



