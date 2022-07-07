# ResourceTelemetryApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteTelemetryServer**](ResourceTelemetryApi.md#deleteTelemetryServer) | **DELETE** /telemetry/{telemetry_server_id} | Delete a telemetry server
[**deleteTelemetryTarget**](ResourceTelemetryApi.md#deleteTelemetryTarget) | **DELETE** /telemetry/{telemetry_server_id}/target/{target_uid} | Delete a telemetry target
[**getTelemetryServer**](ResourceTelemetryApi.md#getTelemetryServer) | **GET** /telemetry/{telemetry_server_id} | Get individual telemetry server information
[**getTelemetryServers**](ResourceTelemetryApi.md#getTelemetryServers) | **GET** /telemetry | Get telemetry servers information
[**getTelemetryTarget**](ResourceTelemetryApi.md#getTelemetryTarget) | **GET** /telemetry/{telemetry_server_id}/target/{target_uid} | Get individual telemetry target information
[**getTelemetryTargets**](ResourceTelemetryApi.md#getTelemetryTargets) | **GET** /telemetry/{telemetry_server_id}/target | Get telemetry targets information
[**postTelemetryServer**](ResourceTelemetryApi.md#postTelemetryServer) | **POST** /telemetry | Register a new telemetry server resource
[**postTelemetryTarget**](ResourceTelemetryApi.md#postTelemetryTarget) | **POST** /telemetry/{telemetry_server_id}/target | Register a new telemetry target
[**putTelemetryTarget**](ResourceTelemetryApi.md#putTelemetryTarget) | **PUT** /telemetry/{telemetry_server_id}/target/{target_uid} | Modify telemetry target information




<a name="deleteTelemetryServer"></a>
# **deleteTelemetryServer**
> deleteTelemetryServer(telemetryServerId)

Delete a telemetry server

Telemetry server deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ResourceTelemetryApi;



ResourceTelemetryApi apiInstance = new ResourceTelemetryApi();

String telemetryServerId = Arrays.asList("telemetryServerId_example"); // String | 

try {
    apiInstance.deleteTelemetryServer(telemetryServerId);
} catch (ApiException e) {
    System.err.println("Exception when calling ResourceTelemetryApi#deleteTelemetryServer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **telemetryServerId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="deleteTelemetryTarget"></a>
# **deleteTelemetryTarget**
> deleteTelemetryTarget(telemetryServerId, targetUid)

Delete a telemetry target

Telemetry target deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ResourceTelemetryApi;



ResourceTelemetryApi apiInstance = new ResourceTelemetryApi();

String telemetryServerId = Arrays.asList("telemetryServerId_example"); // String | 

String targetUid = Arrays.asList("targetUid_example"); // String | 

try {
    apiInstance.deleteTelemetryTarget(telemetryServerId, targetUid);
} catch (ApiException e) {
    System.err.println("Exception when calling ResourceTelemetryApi#deleteTelemetryTarget");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **telemetryServerId** | **String**|  |
 **targetUid** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getTelemetryServer"></a>
# **getTelemetryServer**
> PostTelemetryServer getTelemetryServer(telemetryServerId)

Get individual telemetry server information

Telemetry server retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ResourceTelemetryApi;



ResourceTelemetryApi apiInstance = new ResourceTelemetryApi();

String telemetryServerId = Arrays.asList("telemetryServerId_example"); // String | 

try {
    PostTelemetryServer result = apiInstance.getTelemetryServer(telemetryServerId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ResourceTelemetryApi#getTelemetryServer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **telemetryServerId** | **String**|  |


### Return type

[**PostTelemetryServer**](PostTelemetryServer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getTelemetryServers"></a>
# **getTelemetryServers**
> List getTelemetryServers(userId)

Get telemetry servers information

Telemetry servers retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ResourceTelemetryApi;



ResourceTelemetryApi apiInstance = new ResourceTelemetryApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    List result = apiInstance.getTelemetryServers(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ResourceTelemetryApi#getTelemetryServers");
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


<a name="getTelemetryTarget"></a>
# **getTelemetryTarget**
> PutTelemetryTargetInput getTelemetryTarget(telemetryServerId, targetUid)

Get individual telemetry target information

Telemetry target retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ResourceTelemetryApi;



ResourceTelemetryApi apiInstance = new ResourceTelemetryApi();

String telemetryServerId = Arrays.asList("telemetryServerId_example"); // String | 

String targetUid = Arrays.asList("targetUid_example"); // String | 

try {
    PutTelemetryTargetInput result = apiInstance.getTelemetryTarget(telemetryServerId, targetUid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ResourceTelemetryApi#getTelemetryTarget");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **telemetryServerId** | **String**|  |
 **targetUid** | **String**|  |


### Return type

[**PutTelemetryTargetInput**](PutTelemetryTargetInput.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getTelemetryTargets"></a>
# **getTelemetryTargets**
> List getTelemetryTargets(telemetryServerId)

Get telemetry targets information

Telemetry targets retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ResourceTelemetryApi;



ResourceTelemetryApi apiInstance = new ResourceTelemetryApi();

String telemetryServerId = Arrays.asList("telemetryServerId_example"); // String | 

try {
    List result = apiInstance.getTelemetryTargets(telemetryServerId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ResourceTelemetryApi#getTelemetryTargets");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **telemetryServerId** | **String**|  |


### Return type

[**List**](List.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="postTelemetryServer"></a>
# **postTelemetryServer**
> PostTelemetryServer postTelemetryServer(telemetryserverinput)

Register a new telemetry server resource

Telemetry server registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ResourceTelemetryApi;



ResourceTelemetryApi apiInstance = new ResourceTelemetryApi();

TelemetryServerInput telemetryserverinput = ; // TelemetryServerInput | 

try {
    PostTelemetryServer result = apiInstance.postTelemetryServer(telemetryserverinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ResourceTelemetryApi#postTelemetryServer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **telemetryserverinput** | [**TelemetryServerInput**](.md)|  |


### Return type

[**PostTelemetryServer**](PostTelemetryServer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


<a name="postTelemetryTarget"></a>
# **postTelemetryTarget**
> PostTelemetryTarget postTelemetryTarget(body, telemetryServerId)

Register a new telemetry target

Telemetry target registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ResourceTelemetryApi;



ResourceTelemetryApi apiInstance = new ResourceTelemetryApi();

Object body = ; // Object | 

String telemetryServerId = Arrays.asList("telemetryServerId_example"); // String | 

try {
    PostTelemetryTarget result = apiInstance.postTelemetryTarget(body, telemetryServerId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ResourceTelemetryApi#postTelemetryTarget");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **telemetryServerId** | **String**|  |


### Return type

[**PostTelemetryTarget**](PostTelemetryTarget.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


<a name="putTelemetryTarget"></a>
# **putTelemetryTarget**
> PutTelemetryTargetInput putTelemetryTarget(puttelemetrytargetinput, telemetryServerId, targetUid)

Modify telemetry target information

Telemetry target information modification

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ResourceTelemetryApi;



ResourceTelemetryApi apiInstance = new ResourceTelemetryApi();

PutTelemetryTargetInput puttelemetrytargetinput = ; // PutTelemetryTargetInput | 

String telemetryServerId = Arrays.asList("telemetryServerId_example"); // String | 

String targetUid = Arrays.asList("targetUid_example"); // String | 

try {
    PutTelemetryTargetInput result = apiInstance.putTelemetryTarget(puttelemetrytargetinput, telemetryServerId, targetUid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ResourceTelemetryApi#putTelemetryTarget");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **puttelemetrytargetinput** | [**PutTelemetryTargetInput**](.md)|  |
 **telemetryServerId** | **String**|  |
 **targetUid** | **String**|  |


### Return type

[**PutTelemetryTargetInput**](PutTelemetryTargetInput.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



