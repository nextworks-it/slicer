# NetworkSliceTypeNestApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteSlic3Type**](NetworkSliceTypeNestApi.md#deleteSlic3Type) | **DELETE** /slic3_type/{slic3_type_id} | Delete a network slice type
[**getSlic3Type**](NetworkSliceTypeNestApi.md#getSlic3Type) | **GET** /slic3_type/{slic3_type_id} | Get individual network slice type information
[**getSlic3TypeChunks**](NetworkSliceTypeNestApi.md#getSlic3TypeChunks) | **GET** /slic3_type/{slic3_type_id}/slice_blueprint | Get individual network slice type blueprint information
[**getSlic3Types**](NetworkSliceTypeNestApi.md#getSlic3Types) | **GET** /slic3_type | Get network slice types information
[**postSlic3Type**](NetworkSliceTypeNestApi.md#postSlic3Type) | **POST** /slic3_type | Create a new network slice type




<a name="deleteSlic3Type"></a>
# **deleteSlic3Type**
> deleteSlic3Type(slic3TypeId)

Delete a network slice type

Network slice type deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceTypeNestApi;



NetworkSliceTypeNestApi apiInstance = new NetworkSliceTypeNestApi();

String slic3TypeId = Arrays.asList("slic3TypeId_example"); // String | 

try {
    apiInstance.deleteSlic3Type(slic3TypeId);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceTypeNestApi#deleteSlic3Type");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3TypeId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getSlic3Type"></a>
# **getSlic3Type**
> PostSlic3Type getSlic3Type(slic3TypeId)

Get individual network slice type information

Network slice type retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceTypeNestApi;



NetworkSliceTypeNestApi apiInstance = new NetworkSliceTypeNestApi();

String slic3TypeId = Arrays.asList("slic3TypeId_example"); // String | 

try {
    PostSlic3Type result = apiInstance.getSlic3Type(slic3TypeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceTypeNestApi#getSlic3Type");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3TypeId** | **String**|  |


### Return type

[**PostSlic3Type**](PostSlic3Type.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getSlic3TypeChunks"></a>
# **getSlic3TypeChunks**
> PostSlic3TypeChunks getSlic3TypeChunks(slic3TypeId)

Get individual network slice type blueprint information

Network slice type atomic chunks retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceTypeNestApi;



NetworkSliceTypeNestApi apiInstance = new NetworkSliceTypeNestApi();

String slic3TypeId = Arrays.asList("slic3TypeId_example"); // String | 

try {
    PostSlic3TypeChunks result = apiInstance.getSlic3TypeChunks(slic3TypeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceTypeNestApi#getSlic3TypeChunks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3TypeId** | **String**|  |


### Return type

[**PostSlic3TypeChunks**](PostSlic3TypeChunks.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getSlic3Types"></a>
# **getSlic3Types**
> List getSlic3Types(userId)

Get network slice types information

Network slice types retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceTypeNestApi;



NetworkSliceTypeNestApi apiInstance = new NetworkSliceTypeNestApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    List result = apiInstance.getSlic3Types(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceTypeNestApi#getSlic3Types");
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


<a name="postSlic3Type"></a>
# **postSlic3Type**
> PostSlic3Type postSlic3Type(slic3typeinput)

Create a new network slice type

Network slice type registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceTypeNestApi;



NetworkSliceTypeNestApi apiInstance = new NetworkSliceTypeNestApi();

Slic3TypeInput slic3typeinput = ; // Slic3TypeInput | 

try {
    PostSlic3Type result = apiInstance.postSlic3Type(slic3typeinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceTypeNestApi#postSlic3Type");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3typeinput** | [**Slic3TypeInput**](.md)|  |


### Return type

[**PostSlic3Type**](PostSlic3Type.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



