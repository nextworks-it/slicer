# ContextDescriptorCatalogueApiApi

All URIs are relative to *https://localhost:8082*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllCtxDescriptorsUsingGET**](ContextDescriptorCatalogueApiApi.md#getAllCtxDescriptorsUsingGET) | **GET** /portal/catalogue/ctxdescriptor | getAllCtxDescriptors
[**getCtxDescriptorUsingGET**](ContextDescriptorCatalogueApiApi.md#getCtxDescriptorUsingGET) | **GET** /portal/catalogue/ctxdescriptor/{ctxdId} | getCtxDescriptor


<a name="getAllCtxDescriptorsUsingGET"></a>
# **getAllCtxDescriptorsUsingGET**
> Object getAllCtxDescriptorsUsingGET(authenticated, authorities0Authority)

getAllCtxDescriptors

### Example
```java
// Import classes:
//import ApiException;
//import ContextDescriptorCatalogueApiApi;


ContextDescriptorCatalogueApiApi apiInstance = new ContextDescriptorCatalogueApiApi();
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    Object result = apiInstance.getAllCtxDescriptorsUsingGET(authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextDescriptorCatalogueApiApi#getAllCtxDescriptorsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getCtxDescriptorUsingGET"></a>
# **getCtxDescriptorUsingGET**
> Object getCtxDescriptorUsingGET(ctxdId, authenticated, authorities0Authority)

getCtxDescriptor

### Example
```java
// Import classes:
//import ApiException;
//import ContextDescriptorCatalogueApiApi;


ContextDescriptorCatalogueApiApi apiInstance = new ContextDescriptorCatalogueApiApi();
String ctxdId = "ctxdId_example"; // String | ctxdId
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    Object result = apiInstance.getCtxDescriptorUsingGET(ctxdId, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextDescriptorCatalogueApiApi#getCtxDescriptorUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ctxdId** | **String**| ctxdId |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

