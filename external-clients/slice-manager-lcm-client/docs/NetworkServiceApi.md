# NetworkServiceApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteNetworkService**](NetworkServiceApi.md#deleteNetworkService) | **DELETE** /network_service/{network_service_id} | Delete a network service
[**getNetworkService**](NetworkServiceApi.md#getNetworkService) | **GET** /network_service/{network_service_id} | Network service information retrieval
[**getNetworkServices**](NetworkServiceApi.md#getNetworkServices) | **GET** /network_service | Gets network_services information
[**postNetworkService**](NetworkServiceApi.md#postNetworkService) | **POST** /network_service | Register a new network service resource




<a name="deleteNetworkService"></a>
# **deleteNetworkService**
> deleteNetworkService(networkServiceId)

Delete a network service

Network service deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkServiceApi;



NetworkServiceApi apiInstance = new NetworkServiceApi();

String networkServiceId = Arrays.asList("networkServiceId_example"); // String | network_service_id

try {
    apiInstance.deleteNetworkService(networkServiceId);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkServiceApi#deleteNetworkService");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkServiceId** | **String**| network_service_id |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getNetworkService"></a>
# **getNetworkService**
> PostNetworkService getNetworkService(networkServiceId)

Network service information retrieval

Network service retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkServiceApi;



NetworkServiceApi apiInstance = new NetworkServiceApi();

String networkServiceId = Arrays.asList("networkServiceId_example"); // String | network_service_id

try {
    PostNetworkService result = apiInstance.getNetworkService(networkServiceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkServiceApi#getNetworkService");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkServiceId** | **String**| network_service_id |


### Return type

[**PostNetworkService**](PostNetworkService.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getNetworkServices"></a>
# **getNetworkServices**
> List getNetworkServices(userId)

Gets network_services information

Network service retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkServiceApi;



NetworkServiceApi apiInstance = new NetworkServiceApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    List result = apiInstance.getNetworkServices(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkServiceApi#getNetworkServices");
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


<a name="postNetworkService"></a>
# **postNetworkService**
> PostNetworkService postNetworkService(networkserviceinput)

Register a new network service resource

Network service registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.NetworkServiceApi;



NetworkServiceApi apiInstance = new NetworkServiceApi();

NetworkServiceInput networkserviceinput = ; // NetworkServiceInput | 

try {
    PostNetworkService result = apiInstance.postNetworkService(networkserviceinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkServiceApi#postNetworkService");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkserviceinput** | [**NetworkServiceInput**](.md)|  |


### Return type

[**PostNetworkService**](PostNetworkService.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



