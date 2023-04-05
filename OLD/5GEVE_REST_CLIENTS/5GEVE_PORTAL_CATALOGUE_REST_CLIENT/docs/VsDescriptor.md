
# VsDescriptor

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**managementType** | [**ManagementTypeEnum**](#ManagementTypeEnum) |  |  [optional]
**name** | **String** |  |  [optional]
**qosParameters** | **Map&lt;String, String&gt;** |  |  [optional]
**serviceConstraints** | [**List&lt;ServiceConstraints&gt;**](ServiceConstraints.md) |  |  [optional]
**sla** | [**VsdSla**](VsdSla.md) |  |  [optional]
**sst** | [**SstEnum**](#SstEnum) |  |  [optional]
**version** | **String** |  |  [optional]
**vsBlueprintId** | **String** |  |  [optional]
**vsDescriptorId** | **String** |  |  [optional]


<a name="ManagementTypeEnum"></a>
## Enum: ManagementTypeEnum
Name | Value
---- | -----
PROVIDER_MANAGED | &quot;PROVIDER_MANAGED&quot;
TENANT_MANAGED | &quot;TENANT_MANAGED&quot;


<a name="SstEnum"></a>
## Enum: SstEnum
Name | Value
---- | -----
NONE | &quot;NONE&quot;
EMBB | &quot;EMBB&quot;
URLLC | &quot;URLLC&quot;
M_IOT | &quot;M_IOT&quot;
ENTERPRISE | &quot;ENTERPRISE&quot;
NFV_IAAS | &quot;NFV_IAAS&quot;



