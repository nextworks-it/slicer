# NetworkSliceInstanceNestBasedApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteSlic3Instance**](NetworkSliceInstanceNestBasedApi.md#deleteSlic3Instance) | **DELETE** /slic3_instance/{slic3_instance_id} | Delete a slice instance
[**getSlic3Instance**](NetworkSliceInstanceNestBasedApi.md#getSlic3Instance) | **GET** /slic3_instance/{slic3_instance_id} | Get individual slice instance information
[**getSlic3Instances**](NetworkSliceInstanceNestBasedApi.md#getSlic3Instances) | **GET** /slic3_instance | Get slice instance(s) information
[**postSlic3Instance**](NetworkSliceInstanceNestBasedApi.md#postSlic3Instance) | **POST** /slic3_instance | Create a new slice instance




<a name="deleteSlic3Instance"></a>
# **deleteSlic3Instance**
> deleteSlic3Instance(slic3InstanceId)

Delete a slice instance

Slice instance deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceInstanceNestBasedApi;



NetworkSliceInstanceNestBasedApi apiInstance = new NetworkSliceInstanceNestBasedApi();

String slic3InstanceId = Arrays.asList("slic3InstanceId_example"); // String | 

try {
    apiInstance.deleteSlic3Instance(slic3InstanceId);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceInstanceNestBasedApi#deleteSlic3Instance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3InstanceId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getSlic3Instance"></a>
# **getSlic3Instance**
> PostSlic3Instance getSlic3Instance(slic3InstanceId)

Get individual slice instance information

Slice instance retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceInstanceNestBasedApi;



NetworkSliceInstanceNestBasedApi apiInstance = new NetworkSliceInstanceNestBasedApi();

String slic3InstanceId = Arrays.asList("slic3InstanceId_example"); // String | 

try {
    PostSlic3Instance result = apiInstance.getSlic3Instance(slic3InstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceInstanceNestBasedApi#getSlic3Instance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3InstanceId** | **String**|  |


### Return type

[**PostSlic3Instance**](PostSlic3Instance.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getSlic3Instances"></a>
# **getSlic3Instances**
> List getSlic3Instances(userId, phyId, slic3TypeId)

Get slice instance(s) information

Slice instance retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceInstanceNestBasedApi;



NetworkSliceInstanceNestBasedApi apiInstance = new NetworkSliceInstanceNestBasedApi();

String userId = Arrays.asList("userId_example"); // String | 

String phyId = Arrays.asList("phyId_example"); // String | 

String slic3TypeId = Arrays.asList("slic3TypeId_example"); // String | 

try {
    List result = apiInstance.getSlic3Instances(userId, phyId, slic3TypeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceInstanceNestBasedApi#getSlic3Instances");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**|  | [optional]
 **phyId** | **String**|  | [optional]
 **slic3TypeId** | **String**|  | [optional]


### Return type

[**List**](List.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="postSlic3Instance"></a>
# **postSlic3Instance**
> PostSlic3Instance postSlic3Instance(slic3instanceinput)

Create a new slice instance

Slice instance registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkSliceInstanceNestBasedApi;



NetworkSliceInstanceNestBasedApi apiInstance = new NetworkSliceInstanceNestBasedApi();

Slic3InstanceInput slic3instanceinput = ; // Slic3InstanceInput | 

try {
    PostSlic3Instance result = apiInstance.postSlic3Instance(slic3instanceinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkSliceInstanceNestBasedApi#postSlic3Instance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3instanceinput** | [**Slic3InstanceInput**](.md)|  |


### Return type

[**PostSlic3Instance**](PostSlic3Instance.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



