# ExperimentBlueprintCatalogueApiApi

All URIs are relative to *https://localhost:8082*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllExpBlueprintsUsingGET**](ExperimentBlueprintCatalogueApiApi.md#getAllExpBlueprintsUsingGET) | **GET** /portal/catalogue/expblueprint | Get ALL ExpBlueprints
[**getExpBlueprintUsingGET**](ExperimentBlueprintCatalogueApiApi.md#getExpBlueprintUsingGET) | **GET** /portal/catalogue/expblueprint/{expbId} | Get ExpBlueprint


<a name="getAllExpBlueprintsUsingGET"></a>
# **getAllExpBlueprintsUsingGET**
> List&lt;String&gt; getAllExpBlueprintsUsingGET(authenticated, authorities0Authority, id, vsbId)

Get ALL ExpBlueprints

### Example
```java
// Import classes:
//import ApiException;
//import ExperimentBlueprintCatalogueApiApi;


ExperimentBlueprintCatalogueApiApi apiInstance = new ExperimentBlueprintCatalogueApiApi();
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
String id = "id_example"; // String | id
String vsbId = "vsbId_example"; // String | vsbId
try {
    List<String> result = apiInstance.getAllExpBlueprintsUsingGET(authenticated, authorities0Authority, id, vsbId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ExperimentBlueprintCatalogueApiApi#getAllExpBlueprintsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]
 **id** | **String**| id | [optional]
 **vsbId** | **String**| vsbId | [optional]

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getExpBlueprintUsingGET"></a>
# **getExpBlueprintUsingGET**
> ExpBlueprintInfo getExpBlueprintUsingGET(expbId, authenticated, authorities0Authority)

Get ExpBlueprint

### Example
```java
// Import classes:
//import ApiException;
//import ExperimentBlueprintCatalogueApiApi;


ExperimentBlueprintCatalogueApiApi apiInstance = new ExperimentBlueprintCatalogueApiApi();
String expbId = "expbId_example"; // String | expbId
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    ExpBlueprintInfo result = apiInstance.getExpBlueprintUsingGET(expbId, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ExperimentBlueprintCatalogueApiApi#getExpBlueprintUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **expbId** | **String**| expbId |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

[**ExpBlueprintInfo**](ExpBlueprintInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

