# EdgeCloudComputeResourceApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteCompute**](EdgeCloudComputeResourceApi.md#deleteCompute) | **DELETE** /compute/{compute_id} | Delete a compute
[**getCompute**](EdgeCloudComputeResourceApi.md#getCompute) | **GET** /compute/{compute_id} | Get individual compute information
[**getComputes**](EdgeCloudComputeResourceApi.md#getComputes) | **GET** /compute | Get computes information
[**postCompute**](EdgeCloudComputeResourceApi.md#postCompute) | **POST** /compute | Register a new compute resource




<a name="deleteCompute"></a>
# **deleteCompute**
> deleteCompute(computeId)

Delete a compute

Compute deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeResourceApi;



EdgeCloudComputeResourceApi apiInstance = new EdgeCloudComputeResourceApi();

String computeId = Arrays.asList("computeId_example"); // String | 

try {
    apiInstance.deleteCompute(computeId);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeResourceApi#deleteCompute");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computeId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getCompute"></a>
# **getCompute**
> PostCompute getCompute(computeId)

Get individual compute information

Compute retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeResourceApi;



EdgeCloudComputeResourceApi apiInstance = new EdgeCloudComputeResourceApi();

String computeId = Arrays.asList("computeId_example"); // String | 

try {
    PostCompute result = apiInstance.getCompute(computeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeResourceApi#getCompute");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computeId** | **String**|  |


### Return type

[**PostCompute**](PostCompute.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getComputes"></a>
# **getComputes**
> List getComputes(userId)

Get computes information

Compute retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeResourceApi;



EdgeCloudComputeResourceApi apiInstance = new EdgeCloudComputeResourceApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    List result = apiInstance.getComputes(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeResourceApi#getComputes");
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


<a name="postCompute"></a>
# **postCompute**
> PostCompute postCompute(computeinput)

Register a new compute resource

Compute registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudComputeResourceApi;



EdgeCloudComputeResourceApi apiInstance = new EdgeCloudComputeResourceApi();

ComputeInput computeinput = ; // ComputeInput | 

try {
    PostCompute result = apiInstance.postCompute(computeinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudComputeResourceApi#postCompute");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computeinput** | [**ComputeInput**](.md)|  |


### Return type

[**PostCompute**](PostCompute.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



