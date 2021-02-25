# TestCaseDescriptorCatalogueApiApi

All URIs are relative to *https://localhost:8082*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllTestCaseDescriptorsUsingGET**](TestCaseDescriptorCatalogueApiApi.md#getAllTestCaseDescriptorsUsingGET) | **GET** /portal/catalogue/testcasedescriptor | Query ALL the Test Case Descriptors
[**getTcDescriptorUsingGET**](TestCaseDescriptorCatalogueApiApi.md#getTcDescriptorUsingGET) | **GET** /portal/catalogue/testcasedescriptor/{tcdId} | Query a Test Case Descriptor with a given ID


<a name="getAllTestCaseDescriptorsUsingGET"></a>
# **getAllTestCaseDescriptorsUsingGET**
> List&lt;TestCaseDescriptor&gt; getAllTestCaseDescriptorsUsingGET(authenticated, authorities0Authority)

Query ALL the Test Case Descriptors

### Example
```java
// Import classes:
//import ApiException;
//import TestCaseDescriptorCatalogueApiApi;


TestCaseDescriptorCatalogueApiApi apiInstance = new TestCaseDescriptorCatalogueApiApi();
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    List<TestCaseDescriptor> result = apiInstance.getAllTestCaseDescriptorsUsingGET(authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TestCaseDescriptorCatalogueApiApi#getAllTestCaseDescriptorsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

[**List&lt;TestCaseDescriptor&gt;**](TestCaseDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getTcDescriptorUsingGET"></a>
# **getTcDescriptorUsingGET**
> TestCaseDescriptor getTcDescriptorUsingGET(tcdId, authenticated, authorities0Authority)

Query a Test Case Descriptor with a given ID

### Example
```java
// Import classes:
//import ApiException;
//import TestCaseDescriptorCatalogueApiApi;


TestCaseDescriptorCatalogueApiApi apiInstance = new TestCaseDescriptorCatalogueApiApi();
String tcdId = "tcdId_example"; // String | tcdId
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    TestCaseDescriptor result = apiInstance.getTcDescriptorUsingGET(tcdId, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TestCaseDescriptorCatalogueApiApi#getTcDescriptorUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **tcdId** | **String**| tcdId |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

[**TestCaseDescriptor**](TestCaseDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

