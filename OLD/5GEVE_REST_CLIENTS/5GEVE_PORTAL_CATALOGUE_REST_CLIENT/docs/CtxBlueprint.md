
# CtxBlueprint

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**applicationMetrics** | [**List&lt;ApplicationMetric&gt;**](ApplicationMetric.md) |  |  [optional]
**atomicComponents** | [**List&lt;VsComponent&gt;**](VsComponent.md) |  |  [optional]
**blueprintId** | **String** |  |  [optional]
**compatibleSites** | [**List&lt;CompatibleSitesEnum&gt;**](#List&lt;CompatibleSitesEnum&gt;) |  |  [optional]
**compositionStrategy** | [**CompositionStrategyEnum**](#CompositionStrategyEnum) |  |  [optional]
**configurableParameters** | **List&lt;String&gt;** |  |  [optional]
**connectivityServices** | [**List&lt;VsbLink&gt;**](VsbLink.md) |  |  [optional]
**description** | **String** |  |  [optional]
**endPoints** | [**List&lt;VsbEndpoint&gt;**](VsbEndpoint.md) |  |  [optional]
**name** | **String** |  |  [optional]
**parameters** | [**List&lt;VsBlueprintParameter&gt;**](VsBlueprintParameter.md) |  |  [optional]
**serviceSequence** | [**List&lt;VsbForwardingPathHop&gt;**](VsbForwardingPathHop.md) |  |  [optional]
**version** | **String** |  |  [optional]


<a name="List<CompatibleSitesEnum>"></a>
## Enum: List&lt;CompatibleSitesEnum&gt;
Name | Value
---- | -----
ITALY_TURIN | &quot;ITALY_TURIN&quot;
SPAIN_5TONIC | &quot;SPAIN_5TONIC&quot;
FRANCE_PARIS | &quot;FRANCE_PARIS&quot;
FRANCE_NICE | &quot;FRANCE_NICE&quot;
FRANCE_RENNES | &quot;FRANCE_RENNES&quot;
GREECE_ATHENS | &quot;GREECE_ATHENS&quot;


<a name="CompositionStrategyEnum"></a>
## Enum: CompositionStrategyEnum
Name | Value
---- | -----
PASS_THROUGH | &quot;PASS_THROUGH&quot;
CONNECT | &quot;CONNECT&quot;



