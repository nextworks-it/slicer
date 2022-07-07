# ApplicationInstanceApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteNetworkServiceInstance**](ApplicationInstanceApi.md#deleteNetworkServiceInstance) | **DELETE** /network_service_instance/{network_service_instance_id} | Delete a network service instance
[**getNetworkServiceInstance**](ApplicationInstanceApi.md#getNetworkServiceInstance) | **GET** /network_service_instance/{network_service_instance_id} | Get individual network service instance information
[**getNetworkServiceInstances**](ApplicationInstanceApi.md#getNetworkServiceInstances) | **GET** /network_service_instance | Get network service instances information
[**networkServiceInstanceReaction**](ApplicationInstanceApi.md#networkServiceInstanceReaction) | **POST** /network_service_instance/{network_service_instance_id}/reaction | Perform reaction on individual network service instance
[**postNetworkServiceInstance**](ApplicationInstanceApi.md#postNetworkServiceInstance) | **POST** /network_service_instance | Create a new network service instance
[**scaleNetworkServiceInstance**](ApplicationInstanceApi.md#scaleNetworkServiceInstance) | **POST** /network_service_instance/{network_service_instance_id}/scale | Scale individual network service instance




<a name="deleteNetworkServiceInstance"></a>
# **deleteNetworkServiceInstance**
> deleteNetworkServiceInstance(networkServiceInstanceId)

Delete a network service instance

Network service instance deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ApplicationInstanceApi;



ApplicationInstanceApi apiInstance = new ApplicationInstanceApi();

String networkServiceInstanceId = Arrays.asList("networkServiceInstanceId_example"); // String | 

try {
    apiInstance.deleteNetworkServiceInstance(networkServiceInstanceId);
} catch (ApiException e) {
    System.err.println("Exception when calling ApplicationInstanceApi#deleteNetworkServiceInstance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkServiceInstanceId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getNetworkServiceInstance"></a>
# **getNetworkServiceInstance**
> PostNetworkServiceInstance getNetworkServiceInstance(networkServiceInstanceId)

Get individual network service instance information

Network service instance retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ApplicationInstanceApi;



ApplicationInstanceApi apiInstance = new ApplicationInstanceApi();

String networkServiceInstanceId = Arrays.asList("networkServiceInstanceId_example"); // String | 

try {
    PostNetworkServiceInstance result = apiInstance.getNetworkServiceInstance(networkServiceInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApplicationInstanceApi#getNetworkServiceInstance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkServiceInstanceId** | **String**|  |


### Return type

[**PostNetworkServiceInstance**](PostNetworkServiceInstance.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getNetworkServiceInstances"></a>
# **getNetworkServiceInstances**
> List getNetworkServiceInstances(userId, slic3Id)

Get network service instances information

Network service instance retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ApplicationInstanceApi;



ApplicationInstanceApi apiInstance = new ApplicationInstanceApi();

String userId = Arrays.asList("userId_example"); // String | 

String slic3Id = Arrays.asList("slic3Id_example"); // String | 

try {
    List result = apiInstance.getNetworkServiceInstances(userId, slic3Id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApplicationInstanceApi#getNetworkServiceInstances");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**|  | [optional]
 **slic3Id** | **String**|  | [optional]


### Return type

[**List**](List.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="networkServiceInstanceReaction"></a>
# **networkServiceInstanceReaction**
> networkServiceInstanceReaction(networkserviceinstancereactioninput, networkServiceInstanceId)

Perform reaction on individual network service instance

Network Service Instance reaction method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ApplicationInstanceApi;



ApplicationInstanceApi apiInstance = new ApplicationInstanceApi();

NetworkServiceInstanceReactionInput networkserviceinstancereactioninput = ; // NetworkServiceInstanceReactionInput | 

String networkServiceInstanceId = Arrays.asList("networkServiceInstanceId_example"); // String | 

try {
    apiInstance.networkServiceInstanceReaction(networkserviceinstancereactioninput, networkServiceInstanceId);
} catch (ApiException e) {
    System.err.println("Exception when calling ApplicationInstanceApi#networkServiceInstanceReaction");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkserviceinstancereactioninput** | [**NetworkServiceInstanceReactionInput**](.md)|  |
 **networkServiceInstanceId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="postNetworkServiceInstance"></a>
# **postNetworkServiceInstance**
> PostNetworkServiceInstance postNetworkServiceInstance(networkserviceinstanceinput)

Create a new network service instance

Network service instance registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ApplicationInstanceApi;



ApplicationInstanceApi apiInstance = new ApplicationInstanceApi();

NetworkServiceInstanceInput networkserviceinstanceinput = ; // NetworkServiceInstanceInput | 

try {
    PostNetworkServiceInstance result = apiInstance.postNetworkServiceInstance(networkserviceinstanceinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApplicationInstanceApi#postNetworkServiceInstance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkserviceinstanceinput** | [**NetworkServiceInstanceInput**](.md)|  |


### Return type

[**PostNetworkServiceInstance**](PostNetworkServiceInstance.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


<a name="scaleNetworkServiceInstance"></a>
# **scaleNetworkServiceInstance**
> scaleNetworkServiceInstance(networkserviceinstancescaleinput, networkServiceInstanceId)

Scale individual network service instance

Network service instance scaling method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.ApplicationInstanceApi;



ApplicationInstanceApi apiInstance = new ApplicationInstanceApi();

NetworkServiceInstanceScaleInput networkserviceinstancescaleinput = ; // NetworkServiceInstanceScaleInput | 

String networkServiceInstanceId = Arrays.asList("networkServiceInstanceId_example"); // String | 

try {
    apiInstance.scaleNetworkServiceInstance(networkserviceinstancescaleinput, networkServiceInstanceId);
} catch (ApiException e) {
    System.err.println("Exception when calling ApplicationInstanceApi#scaleNetworkServiceInstance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkserviceinstancescaleinput** | [**NetworkServiceInstanceScaleInput**](.md)|  |
 **networkServiceInstanceId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined



