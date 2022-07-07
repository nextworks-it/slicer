# RadioServiceApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteRadioService**](RadioServiceApi.md#deleteRadioService) | **DELETE** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service/{radio_service_id} | Delete a radio service
[**getRadioService**](RadioServiceApi.md#getRadioService) | **GET** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service/{radio_service_id} | Get individual radio service information
[**getRadioServices**](RadioServiceApi.md#getRadioServices) | **GET** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service | Get radio services information
[**postRadioService**](RadioServiceApi.md#postRadioService) | **POST** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service | Create a new radio service
[**putRadioService**](RadioServiceApi.md#putRadioService) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service/{radio_service_id} | Edit individual radio service interfaces




<a name="deleteRadioService"></a>
# **deleteRadioService**
> deleteRadioService(ranInfrastructureId, radioChunkId, radioServiceId)

Delete a radio service

Radio Service deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioServiceApi;



RadioServiceApi apiInstance = new RadioServiceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String radioChunkId = Arrays.asList("radioChunkId_example"); // String | 

String radioServiceId = Arrays.asList("radioServiceId_example"); // String | 

try {
    apiInstance.deleteRadioService(ranInfrastructureId, radioChunkId, radioServiceId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioServiceApi#deleteRadioService");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **radioChunkId** | **String**|  |
 **radioServiceId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getRadioService"></a>
# **getRadioService**
> PostRadioService getRadioService(ranInfrastructureId, radioChunkId, radioServiceId, userId)

Get individual radio service information

Radio service retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioServiceApi;



RadioServiceApi apiInstance = new RadioServiceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String radioChunkId = Arrays.asList("radioChunkId_example"); // String | 

String radioServiceId = Arrays.asList("radioServiceId_example"); // String | 

String userId = Arrays.asList("userId_example"); // String | 

try {
    PostRadioService result = apiInstance.getRadioService(ranInfrastructureId, radioChunkId, radioServiceId, userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioServiceApi#getRadioService");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **radioChunkId** | **String**|  |
 **radioServiceId** | **String**|  |
 **userId** | **String**|  | [optional]


### Return type

[**PostRadioService**](PostRadioService.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getRadioServices"></a>
# **getRadioServices**
> List getRadioServices(ranInfrastructureId, radioChunkId, userId)

Get radio services information

Radio service retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioServiceApi;



RadioServiceApi apiInstance = new RadioServiceApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String radioChunkId = Arrays.asList("radioChunkId_example"); // String | 

String userId = Arrays.asList("userId_example"); // String | 

try {
    List result = apiInstance.getRadioServices(ranInfrastructureId, radioChunkId, userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioServiceApi#getRadioServices");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **radioChunkId** | **String**|  |
 **userId** | **String**|  | [optional]


### Return type

[**List**](List.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="postRadioService"></a>
# **postRadioService**
> PostRadioService postRadioService(radioserviceinput, ranInfrastructureId, radioChunkId)

Create a new radio service

Radio service registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioServiceApi;



RadioServiceApi apiInstance = new RadioServiceApi();

RadioServiceInput radioserviceinput = ; // RadioServiceInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String radioChunkId = Arrays.asList("radioChunkId_example"); // String | 

try {
    PostRadioService result = apiInstance.postRadioService(radioserviceinput, ranInfrastructureId, radioChunkId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioServiceApi#postRadioService");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **radioserviceinput** | [**RadioServiceInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |
 **radioChunkId** | **String**|  |


### Return type

[**PostRadioService**](PostRadioService.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


<a name="putRadioService"></a>
# **putRadioService**
> PostRadioService putRadioService(radioserviceinput, ranInfrastructureId, radioChunkId, radioServiceId)

Edit individual radio service interfaces

Radio service interfaces update method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioServiceApi;



RadioServiceApi apiInstance = new RadioServiceApi();

RadioServiceInput radioserviceinput = ; // RadioServiceInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String radioChunkId = Arrays.asList("radioChunkId_example"); // String | 

String radioServiceId = Arrays.asList("radioServiceId_example"); // String | 

try {
    PostRadioService result = apiInstance.putRadioService(radioserviceinput, ranInfrastructureId, radioChunkId, radioServiceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioServiceApi#putRadioService");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **radioserviceinput** | [**RadioServiceInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |
 **radioChunkId** | **String**|  |
 **radioServiceId** | **String**|  |


### Return type

[**PostRadioService**](PostRadioService.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



