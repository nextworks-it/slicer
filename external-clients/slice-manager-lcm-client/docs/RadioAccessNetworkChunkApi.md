# RadioAccessNetworkChunkApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteRadioChunk**](RadioAccessNetworkChunkApi.md#deleteRadioChunk) | **DELETE** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id} | Delete a radio chunk
[**getRadioChunk**](RadioAccessNetworkChunkApi.md#getRadioChunk) | **GET** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id} | Get individual radio chunk information
[**getRadioChunks**](RadioAccessNetworkChunkApi.md#getRadioChunks) | **GET** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk | Get radio chunks information
[**getadioChunkTopology**](RadioAccessNetworkChunkApi.md#getadioChunkTopology) | **GET** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/chunk_topology | Get individual radio chunk topology information
[**postRadioChunk**](RadioAccessNetworkChunkApi.md#postRadioChunk) | **POST** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk | Create a new radio chunk
[**putRadioChunk**](RadioAccessNetworkChunkApi.md#putRadioChunk) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id} | Edit individual radio chunk
[**validateRadioChunk**](RadioAccessNetworkChunkApi.md#validateRadioChunk) | **POST** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/validate | Validate radio chunk topology




<a name="deleteRadioChunk"></a>
# **deleteRadioChunk**
> deleteRadioChunk(ranInfrastructureId, radioChunkId)

Delete a radio chunk

Radio chunk deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkChunkApi;



RadioAccessNetworkChunkApi apiInstance = new RadioAccessNetworkChunkApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String radioChunkId = Arrays.asList("radioChunkId_example"); // String | 

try {
    apiInstance.deleteRadioChunk(ranInfrastructureId, radioChunkId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkChunkApi#deleteRadioChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **radioChunkId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getRadioChunk"></a>
# **getRadioChunk**
> PostRadioChunk getRadioChunk(ranInfrastructureId, radioChunkId)

Get individual radio chunk information

Radio chunk retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkChunkApi;



RadioAccessNetworkChunkApi apiInstance = new RadioAccessNetworkChunkApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String radioChunkId = Arrays.asList("radioChunkId_example"); // String | 

try {
    PostRadioChunk result = apiInstance.getRadioChunk(ranInfrastructureId, radioChunkId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkChunkApi#getRadioChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **radioChunkId** | **String**|  |


### Return type

[**PostRadioChunk**](PostRadioChunk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getRadioChunks"></a>
# **getRadioChunks**
> PostRadioChunk getRadioChunks(ranInfrastructureId, userId)

Get radio chunks information

Radio Chunk retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkChunkApi;



RadioAccessNetworkChunkApi apiInstance = new RadioAccessNetworkChunkApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String userId = Arrays.asList("userId_example"); // String | 

try {
    PostRadioChunk result = apiInstance.getRadioChunks(ranInfrastructureId, userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkChunkApi#getRadioChunks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **userId** | **String**|  | [optional]


### Return type

[**PostRadioChunk**](PostRadioChunk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getadioChunkTopology"></a>
# **getadioChunkTopology**
> GetRadioChunkTopo getadioChunkTopology(ranInfrastructureId, radioChunkId)

Get individual radio chunk topology information

Radio chunk topology retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkChunkApi;



RadioAccessNetworkChunkApi apiInstance = new RadioAccessNetworkChunkApi();

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String radioChunkId = Arrays.asList("radioChunkId_example"); // String | 

try {
    GetRadioChunkTopo result = apiInstance.getadioChunkTopology(ranInfrastructureId, radioChunkId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkChunkApi#getadioChunkTopology");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ranInfrastructureId** | **String**|  |
 **radioChunkId** | **String**|  |


### Return type

[**GetRadioChunkTopo**](GetRadioChunkTopo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="postRadioChunk"></a>
# **postRadioChunk**
> PostRadioChunk postRadioChunk(radiochunkinput, ranInfrastructureId)

Create a new radio chunk

Radio Chunk registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkChunkApi;



RadioAccessNetworkChunkApi apiInstance = new RadioAccessNetworkChunkApi();

RadioChunkInput radiochunkinput = ; // RadioChunkInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

try {
    PostRadioChunk result = apiInstance.postRadioChunk(radiochunkinput, ranInfrastructureId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkChunkApi#postRadioChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **radiochunkinput** | [**RadioChunkInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |


### Return type

[**PostRadioChunk**](PostRadioChunk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


<a name="putRadioChunk"></a>
# **putRadioChunk**
> PostRadioChunk putRadioChunk(radiochunkinput, ranInfrastructureId, radioChunkId)

Edit individual radio chunk

Radio chunk update method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkChunkApi;



RadioAccessNetworkChunkApi apiInstance = new RadioAccessNetworkChunkApi();

RadioChunkInput radiochunkinput = ; // RadioChunkInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

String radioChunkId = Arrays.asList("radioChunkId_example"); // String | 

try {
    PostRadioChunk result = apiInstance.putRadioChunk(radiochunkinput, ranInfrastructureId, radioChunkId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkChunkApi#putRadioChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **radiochunkinput** | [**RadioChunkInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |
 **radioChunkId** | **String**|  |


### Return type

[**PostRadioChunk**](PostRadioChunk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


<a name="validateRadioChunk"></a>
# **validateRadioChunk**
> validateRadioChunk(radiochunkinput, ranInfrastructureId)

Validate radio chunk topology

Radio Chunk validation method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.RadioAccessNetworkChunkApi;



RadioAccessNetworkChunkApi apiInstance = new RadioAccessNetworkChunkApi();

RadioChunkInput radiochunkinput = ; // RadioChunkInput | 

String ranInfrastructureId = Arrays.asList("ranInfrastructureId_example"); // String | 

try {
    apiInstance.validateRadioChunk(radiochunkinput, ranInfrastructureId);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioAccessNetworkChunkApi#validateRadioChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **radiochunkinput** | [**RadioChunkInput**](.md)|  |
 **ranInfrastructureId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined



