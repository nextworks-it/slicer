# EdgeCloudNetworkResourceApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deletePhysicalNetwork**](EdgeCloudNetworkResourceApi.md#deletePhysicalNetwork) | **DELETE** /physical_network/{physical_network_id} | Delete a edge/cloud network
[**getPhysicalNetwork**](EdgeCloudNetworkResourceApi.md#getPhysicalNetwork) | **GET** /physical_network/{physical_network_id} | Get individual edge/cloud network information
[**getPhysicalNetworks**](EdgeCloudNetworkResourceApi.md#getPhysicalNetworks) | **GET** /physical_network | Get edge/cloud networks information
[**postPhysicalNetwork**](EdgeCloudNetworkResourceApi.md#postPhysicalNetwork) | **POST** /physical_network | Register a new edge/cloud network resource




<a name="deletePhysicalNetwork"></a>
# **deletePhysicalNetwork**
> deletePhysicalNetwork(physicalNetworkId)

Delete a edge/cloud network

Edge/Cloud Network deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudNetworkResourceApi;



EdgeCloudNetworkResourceApi apiInstance = new EdgeCloudNetworkResourceApi();

String physicalNetworkId = Arrays.asList("physicalNetworkId_example"); // String | 

try {
    apiInstance.deletePhysicalNetwork(physicalNetworkId);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudNetworkResourceApi#deletePhysicalNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **physicalNetworkId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getPhysicalNetwork"></a>
# **getPhysicalNetwork**
> PostPhysicalNetwork getPhysicalNetwork(physicalNetworkId)

Get individual edge/cloud network information

Edge/Cloud Network retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudNetworkResourceApi;



EdgeCloudNetworkResourceApi apiInstance = new EdgeCloudNetworkResourceApi();

String physicalNetworkId = Arrays.asList("physicalNetworkId_example"); // String | 

try {
    PostPhysicalNetwork result = apiInstance.getPhysicalNetwork(physicalNetworkId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudNetworkResourceApi#getPhysicalNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **physicalNetworkId** | **String**|  |


### Return type

[**PostPhysicalNetwork**](PostPhysicalNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getPhysicalNetworks"></a>
# **getPhysicalNetworks**
> List getPhysicalNetworks(userId)

Get edge/cloud networks information

Edge/Cloud Network retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudNetworkResourceApi;



EdgeCloudNetworkResourceApi apiInstance = new EdgeCloudNetworkResourceApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    List result = apiInstance.getPhysicalNetworks(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudNetworkResourceApi#getPhysicalNetworks");
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


<a name="postPhysicalNetwork"></a>
# **postPhysicalNetwork**
> PostPhysicalNetwork postPhysicalNetwork(physicalnetworkinput)

Register a new edge/cloud network resource

Edge/Cloud Network registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.EdgeCloudNetworkResourceApi;



EdgeCloudNetworkResourceApi apiInstance = new EdgeCloudNetworkResourceApi();

PhysicalNetworkInput physicalnetworkinput = ; // PhysicalNetworkInput | 

try {
    PostPhysicalNetwork result = apiInstance.postPhysicalNetwork(physicalnetworkinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EdgeCloudNetworkResourceApi#postPhysicalNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **physicalnetworkinput** | [**PhysicalNetworkInput**](.md)|  |


### Return type

[**PostPhysicalNetwork**](PostPhysicalNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



