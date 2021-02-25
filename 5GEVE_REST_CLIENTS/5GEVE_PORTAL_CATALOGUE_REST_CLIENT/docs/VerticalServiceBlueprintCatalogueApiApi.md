# VerticalServiceBlueprintCatalogueApiApi

All URIs are relative to *https://localhost:8082*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllVsBlueprintsUsingGET**](VerticalServiceBlueprintCatalogueApiApi.md#getAllVsBlueprintsUsingGET) | **GET** /portal/catalogue/vsblueprint | Get ALL the Vertical Service Blueprints
[**getVsBlueprintUsingGET**](VerticalServiceBlueprintCatalogueApiApi.md#getVsBlueprintUsingGET) | **GET** /portal/catalogue/vsblueprint/{vsbId} | Get a Vertical Service Blueprint with a given ID


<a name="getAllVsBlueprintsUsingGET"></a>
# **getAllVsBlueprintsUsingGET**
> List&lt;VsBlueprintInfo&gt; getAllVsBlueprintsUsingGET(authenticated, authorities0Authority, id, site)

Get ALL the Vertical Service Blueprints

### Example
```java
// Import classes:
//import ApiException;
//import VerticalServiceBlueprintCatalogueApiApi;


VerticalServiceBlueprintCatalogueApiApi apiInstance = new VerticalServiceBlueprintCatalogueApiApi();
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
String id = "id_example"; // String | id
String site = "site_example"; // String | site
try {
    List<VsBlueprintInfo> result = apiInstance.getAllVsBlueprintsUsingGET(authenticated, authorities0Authority, id, site);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VerticalServiceBlueprintCatalogueApiApi#getAllVsBlueprintsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]
 **id** | **String**| id | [optional]
 **site** | **String**| site | [optional]

### Return type

[**List&lt;VsBlueprintInfo&gt;**](VsBlueprintInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getVsBlueprintUsingGET"></a>
# **getVsBlueprintUsingGET**
> VsBlueprintInfo getVsBlueprintUsingGET(vsbId, authenticated, authorities0Authority)

Get a Vertical Service Blueprint with a given ID

### Example
```java
// Import classes:
//import ApiException;
//import VerticalServiceBlueprintCatalogueApiApi;


VerticalServiceBlueprintCatalogueApiApi apiInstance = new VerticalServiceBlueprintCatalogueApiApi();
String vsbId = "vsbId_example"; // String | vsbId
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    VsBlueprintInfo result = apiInstance.getVsBlueprintUsingGET(vsbId, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VerticalServiceBlueprintCatalogueApiApi#getVsBlueprintUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **vsbId** | **String**| vsbId |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

[**VsBlueprintInfo**](VsBlueprintInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

