# TestCaseBlueprintCatalogueApiApi

All URIs are relative to *https://localhost:8082*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllTestCaseBlueprintsUsingGET**](TestCaseBlueprintCatalogueApiApi.md#getAllTestCaseBlueprintsUsingGET) | **GET** /portal/catalogue/testcaseblueprint | Get ALL the Test Case Service Blueprints
[**getTcBlueprintUsingGET**](TestCaseBlueprintCatalogueApiApi.md#getTcBlueprintUsingGET) | **GET** /portal/catalogue/testcaseblueprint/{tcbId} | Get a Test Case Blueprint with a given ID


<a name="getAllTestCaseBlueprintsUsingGET"></a>
# **getAllTestCaseBlueprintsUsingGET**
> List&lt;TestCaseBlueprintInfo&gt; getAllTestCaseBlueprintsUsingGET(authenticated, authorities0Authority)

Get ALL the Test Case Service Blueprints

### Example
```java
// Import classes:
//import ApiException;
//import TestCaseBlueprintCatalogueApiApi;


TestCaseBlueprintCatalogueApiApi apiInstance = new TestCaseBlueprintCatalogueApiApi();
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    List<TestCaseBlueprintInfo> result = apiInstance.getAllTestCaseBlueprintsUsingGET(authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TestCaseBlueprintCatalogueApiApi#getAllTestCaseBlueprintsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

[**List&lt;TestCaseBlueprintInfo&gt;**](TestCaseBlueprintInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getTcBlueprintUsingGET"></a>
# **getTcBlueprintUsingGET**
> TestCaseBlueprintInfo getTcBlueprintUsingGET(tcbId, authenticated, authorities0Authority)

Get a Test Case Blueprint with a given ID

### Example
```java
// Import classes:
//import ApiException;
//import TestCaseBlueprintCatalogueApiApi;


TestCaseBlueprintCatalogueApiApi apiInstance = new TestCaseBlueprintCatalogueApiApi();
String tcbId = "tcbId_example"; // String | tcbId
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    TestCaseBlueprintInfo result = apiInstance.getTcBlueprintUsingGET(tcbId, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TestCaseBlueprintCatalogueApiApi#getTcBlueprintUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **tcbId** | **String**| tcbId |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

[**TestCaseBlueprintInfo**](TestCaseBlueprintInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

