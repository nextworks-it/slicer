# RadioAccessNetworkResourceApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteCell**](RadioAccessNetworkResourceApi.md#deleteCell) | **DELETE** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id} | Delete a cell
[**deleteCellRelationship**](RadioAccessNetworkResourceApi.md#deleteCellRelationship) | **DELETE** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id}/nsa_relationship/{nsa_cell_id} | Delete a cell relationship
[**deleteRANInfrastructure**](RadioAccessNetworkResourceApi.md#deleteRANInfrastructure) | **DELETE** /ran_infrastructure/{ran_infrastructure_id} | Delete a RAN infrastructure
[**getConfiguredRadioPhys**](RadioAccessNetworkResourceApi.md#getConfiguredRadioPhys) | **GET** /ran_infrastructure/{ran_infrastructure_id}/configured_ran_topology | Get RAN infrastructures configured radio interfaces
[**getRANInfrastructure**](RadioAccessNetworkResourceApi.md#getRANInfrastructure) | **GET** /ran_infrastructure/{ran_infrastructure_id} | Get individual RAN infrastructure information
[**getRANInfrastructures**](RadioAccessNetworkResourceApi.md#getRANInfrastructures) | **GET** /ran_infrastructure | Get RAN infrastructures information
[**getRANTopology**](RadioAccessNetworkResourceApi.md#getRANTopology) | **GET** /ran_infrastructure/{ran_infrastructure_id}/ran_topology | Get individual RAN infrastructure topology information
[**getRANboxes**](RadioAccessNetworkResourceApi.md#getRANboxes) | **GET** /ran_infrastructure/{ran_infrastructure_id}/box | Get RAN boxes information
[**getRFports**](RadioAccessNetworkResourceApi.md#getRFports) | **GET** /ran_infrastructure/{ran_infrastructure_id}/box/{box_id}/rf_port | Get RF ports information
[**postBoxCell**](RadioAccessNetworkResourceApi.md#postBoxCell) | **POST** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/{box_id}/cell | Creates a new cell
[**postRANInfrastructure**](RadioAccessNetworkResourceApi.md#postRANInfrastructure) | **POST** /ran_infrastructure | Register a new RAN infrastructure resource
[**putBoxCellConfig**](RadioAccessNetworkResourceApi.md#putBoxCellConfig) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id} | Set cell config
[**putBoxCellRelationship**](RadioAccessNetworkResourceApi.md#putBoxCellRelationship) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id}/nsa_relationship/{nsa_cell_id} | Set cell relationship
[**putBoxConfig**](RadioAccessNetworkResourceApi.md#putBoxConfig) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/box/{box_id} | Set cellular box config
[**putInterfaceType**](RadioAccessNetworkResourceApi.md#putInterfaceType) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/{interface_id}/type | Set physical interface type
[**putLTEConfiguration**](RadioAccessNetworkResourceApi.md#putLTEConfiguration) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/{interface_id}/lte_config | Set physical interface LTE configuration
[**putRFPortConfig**](RadioAccessNetworkResourceApi.md#putRFPortConfig) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/box/{box_id}/rf_port/{rf_port_id} | Set RF port config
[**putWirelessConfiguration**](RadioAccessNetworkResourceApi.md#putWirelessConfiguration) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/{interface_id}/wifi_config | Set physical interface WiFi configuration




<a name="deleteCell"></a>
# **deleteCell**
> deleteCell(ranInfrastructureId, cellId)

Delete a cell

Cell deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String cellId = Arrays.asList("cellId_example"); // String | 

try {
    apiInstance.deleteCell(ranInfrastructureId, cellId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#deleteCell");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **cellId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="deleteCellRelationship"></a>
# **deleteCellRelationship**
> deleteCellRelationship(ranInfrastructureId, cellId, nsaCellId)

Delete a cell relationship

Cell relationship deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String cellId = Arrays.asList("cellId_example"); // String | 

String nsaCellId = Arrays.asList("nsaCellId_example"); // String | 

try {
    apiInstance.deleteCellRelationship(ranInfrastructureId, cellId, nsaCellId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#deleteCellRelationship");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **cellId** | **String**|  |
 **nsaCellId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="deleteRANInfrastructure"></a>
# **deleteRANInfrastructure**
> deleteRANInfrastructure(ranInfrastructureId)

Delete a RAN infrastructure

RAN Infrastructure deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

try {
    apiInstance.deleteRANInfrastructure(ranInfrastructureId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#deleteRANInfrastructure");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getConfiguredRadioPhys"></a>
# **getConfiguredRadioPhys**
> GetRANInfrastructureTopo getConfiguredRadioPhys(ranInfrastructureId)

Get RAN infrastructures configured radio interfaces

RAN Infrastructure configured radio interfaces retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

try {
    GetRANInfrastructureTopo result = apiInstance.getConfiguredRadioPhys(ranInfrastructureId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#getConfiguredRadioPhys");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |


### Return type

[**GetRANInfrastructureTopo**](GetRANInfrastructureTopo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getRANInfrastructure"></a>
# **getRANInfrastructure**
> PostRANInfrastructure getRANInfrastructure(ranInfrastructureId)

Get individual RAN infrastructure information

RAN Infrastructure retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

try {
    PostRANInfrastructure result = apiInstance.getRANInfrastructure(ranInfrastructureId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#getRANInfrastructure");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |


### Return type

[**PostRANInfrastructure**](PostRANInfrastructure.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getRANInfrastructures"></a>
# **getRANInfrastructures**
> List getRANInfrastructures(userId)

Get RAN infrastructures information

RAN Infrastructure retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    List result = apiInstance.getRANInfrastructures(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#getRANInfrastructures");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**|  | [optional]


### Return type

[**List**](List.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getRANTopology"></a>
# **getRANTopology**
> GetRANInfrastructureTopo getRANTopology(ranInfrastructureId)

Get individual RAN infrastructure topology information

RAN Infrastructure topology retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

try {
    GetRANInfrastructureTopo result = apiInstance.getRANTopology(ranInfrastructureId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#getRANTopology");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |


### Return type

[**GetRANInfrastructureTopo**](GetRANInfrastructureTopo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getRANboxes"></a>
# **getRANboxes**
> List getRANboxes(ranInfrastructureId)

Get RAN boxes information

RAN boxes retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

try {
    List result = apiInstance.getRANboxes(ranInfrastructureId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#getRANboxes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |


### Return type

[**List**](List.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getRFports"></a>
# **getRFports**
> List getRFports(ranInfrastructureId, boxId)

Get RF ports information

RF ports retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String boxId = Arrays.asList("boxId_example"); // String | 

try {
    List result = apiInstance.getRFports(ranInfrastructureId, boxId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#getRFports");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **boxId** | **String**|  |


### Return type

[**List**](List.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="postBoxCell"></a>
# **postBoxCell**
> PostBoxCell postBoxCell(boxcellinput, ranInfrastructureId, boxId)

Creates a new cell

Cell creation method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

BoxCellInput boxcellinput = ; // BoxCellInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String boxId = Arrays.asList("boxId_example"); // String | 

try {
    PostBoxCell result = apiInstance.postBoxCell(boxcellinput, ranInfrastructureId, boxId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#postBoxCell");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **boxcellinput** | [**BoxCellInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |
 **boxId** | **String**|  |


### Return type

[**PostBoxCell**](PostBoxCell.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


<a name="postRANInfrastructure"></a>
# **postRANInfrastructure**
> PostRANInfrastructure postRANInfrastructure(raninfrastructureinput)

Register a new RAN infrastructure resource

RAN Infrastructure registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

RANInfrastructureInput raninfrastructureinput = ; // RANInfrastructureInput | 

try {
    PostRANInfrastructure result = apiInstance.postRANInfrastructure(raninfrastructureinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#postRANInfrastructure");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **raninfrastructureinput** | [**RANInfrastructureInput**](.md)|  |


### Return type

[**PostRANInfrastructure**](PostRANInfrastructure.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


<a name="putBoxCellConfig"></a>
# **putBoxCellConfig**
> putBoxCellConfig(putboxcellconfiginput, ranInfrastructureId, cellId)

Set cell config

Cell config

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

PutBoxCellConfigInput putboxcellconfiginput = ; // PutBoxCellConfigInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String cellId = Arrays.asList("cellId_example"); // String | 

try {
    apiInstance.putBoxCellConfig(putboxcellconfiginput, ranInfrastructureId, cellId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#putBoxCellConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **putboxcellconfiginput** | [**PutBoxCellConfigInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |
 **cellId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="putBoxCellRelationship"></a>
# **putBoxCellRelationship**
> putBoxCellRelationship(ranInfrastructureId, cellId, nsaCellId)

Set cell relationship

Cell relationship config

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String cellId = Arrays.asList("cellId_example"); // String | 

String nsaCellId = Arrays.asList("nsaCellId_example"); // String | 

try {
    apiInstance.putBoxCellRelationship(ranInfrastructureId, cellId, nsaCellId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#putBoxCellRelationship");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **cellId** | **String**|  |
 **nsaCellId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="putBoxConfig"></a>
# **putBoxConfig**
> putBoxConfig(putboxconfiginput, ranInfrastructureId, boxId)

Set cellular box config

Cellular Box Config

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

PutBoxConfigInput putboxconfiginput = ; // PutBoxConfigInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String boxId = Arrays.asList("boxId_example"); // String | 

try {
    apiInstance.putBoxConfig(putboxconfiginput, ranInfrastructureId, boxId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#putBoxConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **putboxconfiginput** | [**PutBoxConfigInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |
 **boxId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="putInterfaceType"></a>
# **putInterfaceType**
> putInterfaceType(ranInfrastructureId, interfaceId, type)

Set physical interface type

Physical Interface Type

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String interfaceId = Arrays.asList("interfaceId_example"); // String | 

String type = Arrays.asList("type_example"); // String | 

try {
    apiInstance.putInterfaceType(ranInfrastructureId, interfaceId, type);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#putInterfaceType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **interfaceId** | **String**|  |
 **type** | **String**|  | [enum: WIRED, WIRED_ROOT, WIRED_ACCESS]


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="putLTEConfiguration"></a>
# **putLTEConfiguration**
> putLTEConfiguration(putlteconfigurationinput, ranInfrastructureId, interfaceId)

Set physical interface LTE configuration

Physical Interface LTE configuration

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

PutLTEConfigurationInput putlteconfigurationinput = ; // PutLTEConfigurationInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String interfaceId = Arrays.asList("interfaceId_example"); // String | 

try {
    apiInstance.putLTEConfiguration(putlteconfigurationinput, ranInfrastructureId, interfaceId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#putLTEConfiguration");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **putlteconfigurationinput** | [**PutLTEConfigurationInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |
 **interfaceId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="putRFPortConfig"></a>
# **putRFPortConfig**
> putRFPortConfig(putrfportconfiginput, ranInfrastructureId, boxId, rfPortId)

Set RF port config

RF Port Config

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

PutRFPortConfigInput putrfportconfiginput = ; // PutRFPortConfigInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String boxId = Arrays.asList("boxId_example"); // String | 

String rfPortId = Arrays.asList("rfPortId_example"); // String | 

try {
    apiInstance.putRFPortConfig(putrfportconfiginput, ranInfrastructureId, boxId, rfPortId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#putRFPortConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **putrfportconfiginput** | [**PutRFPortConfigInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |
 **boxId** | **String**|  |
 **rfPortId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="putWirelessConfiguration"></a>
# **putWirelessConfiguration**
> putWirelessConfiguration(putwificonfigurationinput, ranInfrastructureId, interfaceId)

Set physical interface WiFi configuration

Physical Interface WiFi configuration

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi;



RadioAccessNetworkResourceApi apiInstance = new RadioAccessNetworkResourceApi();

PutWifiConfigurationInput putwificonfigurationinput = ; // PutWifiConfigurationInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String interfaceId = Arrays.asList("interfaceId_example"); // String | 

try {
    apiInstance.putWirelessConfiguration(putwificonfigurationinput, ranInfrastructureId, interfaceId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkResourceApi#putWirelessConfiguration");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **putwificonfigurationinput** | [**PutWifiConfigurationInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |
 **interfaceId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined



