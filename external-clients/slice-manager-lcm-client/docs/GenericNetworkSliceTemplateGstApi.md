# GenericNetworkSliceTemplateGstApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteSlic3Temp**](GenericNetworkSliceTemplateGstApi.md#deleteSlic3Temp) | **DELETE** /slic3_template/{slic3_temp_id} | Delete a generic slice template
[**getSlic3Temp**](GenericNetworkSliceTemplateGstApi.md#getSlic3Temp) | **GET** /slic3_template/{slic3_temp_id} | Get individual generic slice template information
[**getSlic3Temps**](GenericNetworkSliceTemplateGstApi.md#getSlic3Temps) | **GET** /slic3_template | Get generic slice templates information
[**postSlic3Temp**](GenericNetworkSliceTemplateGstApi.md#postSlic3Temp) | **POST** /slic3_template | Create a new generic slice template




<a name="deleteSlic3Temp"></a>
# **deleteSlic3Temp**
> deleteSlic3Temp(slic3TempId)

Delete a generic slice template

Generic slice template deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.GenericNetworkSliceTemplateGstApi;



GenericNetworkSliceTemplateGstApi apiInstance = new GenericNetworkSliceTemplateGstApi();

String slic3TempId = Arrays.asList("slic3TempId_example"); // String | 

try {
    apiInstance.deleteSlic3Temp(slic3TempId);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericNetworkSliceTemplateGstApi#deleteSlic3Temp");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3TempId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getSlic3Temp"></a>
# **getSlic3Temp**
> PostSlic3Temp getSlic3Temp(slic3TempId)

Get individual generic slice template information

Generic slice template retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.GenericNetworkSliceTemplateGstApi;



GenericNetworkSliceTemplateGstApi apiInstance = new GenericNetworkSliceTemplateGstApi();

String slic3TempId = Arrays.asList("slic3TempId_example"); // String | 

try {
    PostSlic3Temp result = apiInstance.getSlic3Temp(slic3TempId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericNetworkSliceTemplateGstApi#getSlic3Temp");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3TempId** | **String**|  |


### Return type

[**PostSlic3Temp**](PostSlic3Temp.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getSlic3Temps"></a>
# **getSlic3Temps**
> List getSlic3Temps(userId)

Get generic slice templates information

Generic slice templates retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.GenericNetworkSliceTemplateGstApi;



GenericNetworkSliceTemplateGstApi apiInstance = new GenericNetworkSliceTemplateGstApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    List result = apiInstance.getSlic3Temps(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericNetworkSliceTemplateGstApi#getSlic3Temps");
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


<a name="postSlic3Temp"></a>
# **postSlic3Temp**
> PostSlic3Temp postSlic3Temp(slic3tempinput)

Create a new generic slice template

Generic slice template registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.GenericNetworkSliceTemplateGstApi;



GenericNetworkSliceTemplateGstApi apiInstance = new GenericNetworkSliceTemplateGstApi();

Slic3TempInput slic3tempinput = ; // Slic3TempInput | 

try {
    PostSlic3Temp result = apiInstance.postSlic3Temp(slic3tempinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericNetworkSliceTemplateGstApi#postSlic3Temp");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **slic3tempinput** | [**Slic3TempInput**](.md)|  |


### Return type

[**PostSlic3Temp**](PostSlic3Temp.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



