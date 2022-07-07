# NetworkSliceCollectionOfChunksApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addChunkSlic3**](NetworkSliceCollectionOfChunksApi.md#addChunkSlic3) | **PUT** /slic3/{slic3_id}/add_chunks | Add chunks to individual Slic3
[**delChunkSlic3**](NetworkSliceCollectionOfChunksApi.md#delChunkSlic3) | **PUT** /slic3/{slic3_id}/del_chunks | Remove chunks from individual Slic3
[**deleteSlic3**](NetworkSliceCollectionOfChunksApi.md#deleteSlic3) | **DELETE** /slic3/{slic3_id} | Delete a slice
[**getSlic3**](NetworkSliceCollectionOfChunksApi.md#getSlic3) | **GET** /slic3/{slic3_id} | Get individual slice information
[**getSlic3s**](NetworkSliceCollectionOfChunksApi.md#getSlic3s) | **GET** /slic3 | Get slice(s) information
[**postSlic3**](NetworkSliceCollectionOfChunksApi.md#postSlic3) | **POST** /slic3 | Create a new slice




<a name="addChunkSlic3"></a>
# **addChunkSlic3**
> PostSlic3 addChunkSlic3(slic3put, slic3Id)

Add chunks to individual Slic3

Slic3 update method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceCollectionOfChunksApi;



NetworkSliceCollectionOfChunksApi apiInstance = new NetworkSliceCollectionOfChunksApi();

Slic3Put slic3put = ; // Slic3Put | 

String slic3Id = Arrays.asList("slic3Id_example"); // String | slic3_id

try {
    PostSlic3 result = apiInstance.addChunkSlic3(slic3put, slic3Id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceCollectionOfChunksApi#addChunkSlic3");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3put** | [**Slic3Put**](.md)|  |
 **slic3Id** | **String**| slic3_id |


### Return type

[**PostSlic3**](PostSlic3.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


<a name="delChunkSlic3"></a>
# **delChunkSlic3**
> PostSlic3 delChunkSlic3(slic3put, slic3Id)

Remove chunks from individual Slic3

Slic3 update method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceCollectionOfChunksApi;



NetworkSliceCollectionOfChunksApi apiInstance = new NetworkSliceCollectionOfChunksApi();

Slic3Put slic3put = ; // Slic3Put | 

String slic3Id = Arrays.asList("slic3Id_example"); // String | slic3_id

try {
    PostSlic3 result = apiInstance.delChunkSlic3(slic3put, slic3Id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceCollectionOfChunksApi#delChunkSlic3");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3put** | [**Slic3Put**](.md)|  |
 **slic3Id** | **String**| slic3_id |


### Return type

[**PostSlic3**](PostSlic3.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


<a name="deleteSlic3"></a>
# **deleteSlic3**
> deleteSlic3(slic3Id)

Delete a slice

Slice deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceCollectionOfChunksApi;



NetworkSliceCollectionOfChunksApi apiInstance = new NetworkSliceCollectionOfChunksApi();

String slic3Id = Arrays.asList("slic3Id_example"); // String | 

try {
    apiInstance.deleteSlic3(slic3Id);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceCollectionOfChunksApi#deleteSlic3");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3Id** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getSlic3"></a>
# **getSlic3**
> PostSlic3 getSlic3(slic3Id)

Get individual slice information

Slice retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceCollectionOfChunksApi;



NetworkSliceCollectionOfChunksApi apiInstance = new NetworkSliceCollectionOfChunksApi();

String slic3Id = Arrays.asList("slic3Id_example"); // String | 

try {
    PostSlic3 result = apiInstance.getSlic3(slic3Id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceCollectionOfChunksApi#getSlic3");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3Id** | **String**|  |


### Return type

[**PostSlic3**](PostSlic3.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getSlic3s"></a>
# **getSlic3s**
> List getSlic3s(userId, phyId)

Get slice(s) information

Slice retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceCollectionOfChunksApi;



NetworkSliceCollectionOfChunksApi apiInstance = new NetworkSliceCollectionOfChunksApi();

String userId = Arrays.asList("userId_example"); // String | 

String phyId = Arrays.asList("phyId_example"); // String | 

try {
    List result = apiInstance.getSlic3s(userId, phyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceCollectionOfChunksApi#getSlic3s");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**|  | [optional]
 **phyId** | **String**|  | [optional]


### Return type

[**List**](List.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="postSlic3"></a>
# **postSlic3**
> PostSlic3 postSlic3(slic3input)

Create a new slice

Slice registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceCollectionOfChunksApi;



NetworkSliceCollectionOfChunksApi apiInstance = new NetworkSliceCollectionOfChunksApi();

Slic3Input slic3input = ; // Slic3Input | 

try {
    PostSlic3 result = apiInstance.postSlic3(slic3input);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceCollectionOfChunksApi#postSlic3");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3input** | [**Slic3Input**](.md)|  |


### Return type

[**PostSlic3**](PostSlic3.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



