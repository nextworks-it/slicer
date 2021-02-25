# ContextBlueprintCatalogueApiApi

All URIs are relative to *https://localhost:8082*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllCtxBlueprintsUsingGET**](ContextBlueprintCatalogueApiApi.md#getAllCtxBlueprintsUsingGET) | **GET** /portal/catalogue/ctxblueprint | Get ALL CtxBlueprints
[**getCtxBlueprintUsingGET**](ContextBlueprintCatalogueApiApi.md#getCtxBlueprintUsingGET) | **GET** /portal/catalogue/ctxblueprint/{ctxbId} | Get CtxBlueprint


<a name="getAllCtxBlueprintsUsingGET"></a>
# **getAllCtxBlueprintsUsingGET**
> List&lt;CtxBlueprintInfo&gt; getAllCtxBlueprintsUsingGET(authenticated, authorities0Authority)

Get ALL CtxBlueprints

### Example
```java
// Import classes:
//import ApiException;
//import ContextBlueprintCatalogueApiApi;


ContextBlueprintCatalogueApiApi apiInstance = new ContextBlueprintCatalogueApiApi();
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    List<CtxBlueprintInfo> result = apiInstance.getAllCtxBlueprintsUsingGET(authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextBlueprintCatalogueApiApi#getAllCtxBlueprintsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

[**List&lt;CtxBlueprintInfo&gt;**](CtxBlueprintInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getCtxBlueprintUsingGET"></a>
# **getCtxBlueprintUsingGET**
> CtxBlueprintInfo getCtxBlueprintUsingGET(ctxbId, authenticated, authorities0Authority)

Get CtxBlueprint

### Example
```java
// Import classes:
//import ApiException;
//import ContextBlueprintCatalogueApiApi;


ContextBlueprintCatalogueApiApi apiInstance = new ContextBlueprintCatalogueApiApi();
String ctxbId = "ctxbId_example"; // String | ctxbId
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    CtxBlueprintInfo result = apiInstance.getCtxBlueprintUsingGET(ctxbId, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextBlueprintCatalogueApiApi#getCtxBlueprintUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ctxbId** | **String**| ctxbId |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

[**CtxBlueprintInfo**](CtxBlueprintInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

