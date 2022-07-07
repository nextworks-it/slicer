# EdgeCloudNetworkChunkApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteNetworkChunk**](EdgeCloudNetworkChunkApi.md#deleteNetworkChunk) | **DELETE** /network_chunk/{network_chunk_id} | Delete a Network Chunk
[**getNetworkChunk**](EdgeCloudNetworkChunkApi.md#getNetworkChunk) | **GET** /network_chunk/{network_chunk_id} | Get individual Network Chunk information
[**getNetworkChunks**](EdgeCloudNetworkChunkApi.md#getNetworkChunks) | **GET** /network_chunk | Get Network Chunks information
[**postNetworkChunk**](EdgeCloudNetworkChunkApi.md#postNetworkChunk) | **POST** /network_chunk | Create a new Network Chunk




<a name="deleteNetworkChunk"></a>
# **deleteNetworkChunk**
> deleteNetworkChunk(networkChunkId)

Delete a Network Chunk

Network Chunk deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudNetworkChunkApi;



EdgeCloudNetworkChunkApi apiInstance = new EdgeCloudNetworkChunkApi();

String networkChunkId = Arrays.asList("networkChunkId_example"); // String | 

try {
    apiInstance.deleteNetworkChunk(networkChunkId);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudNetworkChunkApi#deleteNetworkChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkChunkId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getNetworkChunk"></a>
# **getNetworkChunk**
> PostNetworkChunk getNetworkChunk(networkChunkId)

Get individual Network Chunk information

Network Chunk retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudNetworkChunkApi;



EdgeCloudNetworkChunkApi apiInstance = new EdgeCloudNetworkChunkApi();

String networkChunkId = Arrays.asList("networkChunkId_example"); // String | 

try {
    PostNetworkChunk result = apiInstance.getNetworkChunk(networkChunkId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudNetworkChunkApi#getNetworkChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkChunkId** | **String**|  |


### Return type

[**PostNetworkChunk**](PostNetworkChunk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getNetworkChunks"></a>
# **getNetworkChunks**
> List getNetworkChunks(userId)

Get Network Chunks information

Network Chunks retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudNetworkChunkApi;



EdgeCloudNetworkChunkApi apiInstance = new EdgeCloudNetworkChunkApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    List result = apiInstance.getNetworkChunks(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudNetworkChunkApi#getNetworkChunks");
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


<a name="postNetworkChunk"></a>
# **postNetworkChunk**
> PostNetworkChunk postNetworkChunk(networkchunkinput)

Create a new Network Chunk

Network Chunk registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudNetworkChunkApi;



EdgeCloudNetworkChunkApi apiInstance = new EdgeCloudNetworkChunkApi();

NetworkChunkInput networkchunkinput = ; // NetworkChunkInput | 

try {
    PostNetworkChunk result = apiInstance.postNetworkChunk(networkchunkinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudNetworkChunkApi#postNetworkChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkchunkinput** | [**NetworkChunkInput**](.md)|  |


### Return type

[**PostNetworkChunk**](PostNetworkChunk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



