# EdgeCloudComputeChunkApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteComputeChunk**](EdgeCloudComputeChunkApi.md#deleteComputeChunk) | **DELETE** /compute_chunk/{compute_chunk_id} | Delete a Compute Chunk
[**getComputeChunk**](EdgeCloudComputeChunkApi.md#getComputeChunk) | **GET** /compute_chunk/{compute_chunk_id} | Get individual Compute Chunk information
[**getComputeChunks**](EdgeCloudComputeChunkApi.md#getComputeChunks) | **GET** /compute_chunk | Get Compute Chunks information
[**modifyCpuComputeChunk**](EdgeCloudComputeChunkApi.md#modifyCpuComputeChunk) | **PUT** /compute_chunk/{compute_chunk_id}/cpus | Modify an OpenStack project CPU quota
[**modifyRamComputeChunk**](EdgeCloudComputeChunkApi.md#modifyRamComputeChunk) | **PUT** /compute_chunk/{compute_chunk_id}/ram | Modify an OpenStack project RAM quota
[**modifyStorageComputeChunk**](EdgeCloudComputeChunkApi.md#modifyStorageComputeChunk) | **PUT** /compute_chunk/{compute_chunk_id}/storage | Modify an OpenStack project storage quota
[**postComputeChunk**](EdgeCloudComputeChunkApi.md#postComputeChunk) | **POST** /compute_chunk | Create a new Compute Chunk




<a name="deleteComputeChunk"></a>
# **deleteComputeChunk**
> deleteComputeChunk(computeChunkId)

Delete a Compute Chunk

Compute Chunk deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeChunkApi;



EdgeCloudComputeChunkApi apiInstance = new EdgeCloudComputeChunkApi();

String computeChunkId = Arrays.asList("computeChunkId_example"); // String | 

try {
    apiInstance.deleteComputeChunk(computeChunkId);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeChunkApi#deleteComputeChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computeChunkId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getComputeChunk"></a>
# **getComputeChunk**
> PostComputeChunk getComputeChunk(computeChunkId)

Get individual Compute Chunk information

Compute Chunk retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeChunkApi;



EdgeCloudComputeChunkApi apiInstance = new EdgeCloudComputeChunkApi();

String computeChunkId = Arrays.asList("computeChunkId_example"); // String | 

try {
    PostComputeChunk result = apiInstance.getComputeChunk(computeChunkId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeChunkApi#getComputeChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computeChunkId** | **String**|  |


### Return type

[**PostComputeChunk**](PostComputeChunk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getComputeChunks"></a>
# **getComputeChunks**
> List getComputeChunks(userId)

Get Compute Chunks information

Compute Chunks retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeChunkApi;



EdgeCloudComputeChunkApi apiInstance = new EdgeCloudComputeChunkApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    List result = apiInstance.getComputeChunks(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeChunkApi#getComputeChunks");
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


<a name="modifyCpuComputeChunk"></a>
# **modifyCpuComputeChunk**
> modifyCpuComputeChunk(computechunknewcpuinput, computeChunkId)

Modify an OpenStack project CPU quota

OpenStack Project CPU quota modify method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeChunkApi;



EdgeCloudComputeChunkApi apiInstance = new EdgeCloudComputeChunkApi();

ComputeChunkNewCPUInput computechunknewcpuinput = ; // ComputeChunkNewCPUInput | 

String computeChunkId = Arrays.asList("computeChunkId_example"); // String | 

try {
    apiInstance.modifyCpuComputeChunk(computechunknewcpuinput, computeChunkId);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeChunkApi#modifyCpuComputeChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computechunknewcpuinput** | [**ComputeChunkNewCPUInput**](.md)|  |
 **computeChunkId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="modifyRamComputeChunk"></a>
# **modifyRamComputeChunk**
> modifyRamComputeChunk(computechunknewraminput, computeChunkId)

Modify an OpenStack project RAM quota

OpenStack Project RAM quota modify method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeChunkApi;



EdgeCloudComputeChunkApi apiInstance = new EdgeCloudComputeChunkApi();

ComputeChunkNewRAMInput computechunknewraminput = ; // ComputeChunkNewRAMInput | 

String computeChunkId = Arrays.asList("computeChunkId_example"); // String | 

try {
    apiInstance.modifyRamComputeChunk(computechunknewraminput, computeChunkId);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeChunkApi#modifyRamComputeChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computechunknewraminput** | [**ComputeChunkNewRAMInput**](.md)|  |
 **computeChunkId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="modifyStorageComputeChunk"></a>
# **modifyStorageComputeChunk**
> modifyStorageComputeChunk(computechunknewstorageinput, computeChunkId)

Modify an OpenStack project storage quota

OpenStack Project Storage quota modify method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeChunkApi;



EdgeCloudComputeChunkApi apiInstance = new EdgeCloudComputeChunkApi();

ComputeChunkNewStorageInput computechunknewstorageinput = ; // ComputeChunkNewStorageInput | 

String computeChunkId = Arrays.asList("computeChunkId_example"); // String | 

try {
    apiInstance.modifyStorageComputeChunk(computechunknewstorageinput, computeChunkId);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeChunkApi#modifyStorageComputeChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computechunknewstorageinput** | [**ComputeChunkNewStorageInput**](.md)|  |
 **computeChunkId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="postComputeChunk"></a>
# **postComputeChunk**
> PostComputeChunk postComputeChunk(computechunkinput)

Create a new Compute Chunk

Compute Chunk registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeChunkApi;



EdgeCloudComputeChunkApi apiInstance = new EdgeCloudComputeChunkApi();

ComputeChunkInput computechunkinput = ; // ComputeChunkInput | 

try {
    PostComputeChunk result = apiInstance.postComputeChunk(computechunkinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeChunkApi#postComputeChunk");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computechunkinput** | [**ComputeChunkInput**](.md)|  |


### Return type

[**PostComputeChunk**](PostComputeChunk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



