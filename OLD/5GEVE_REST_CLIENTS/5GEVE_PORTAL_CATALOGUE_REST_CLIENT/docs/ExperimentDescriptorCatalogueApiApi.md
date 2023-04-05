# ExperimentDescriptorCatalogueApiApi

All URIs are relative to *https://localhost:8082*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createExpDescriptorUsingPOST**](ExperimentDescriptorCatalogueApiApi.md#createExpDescriptorUsingPOST) | **POST** /portal/catalogue/expdescriptor | Onboard Experiment Descriptor
[**getAllExpDescriptorsUsingGET**](ExperimentDescriptorCatalogueApiApi.md#getAllExpDescriptorsUsingGET) | **GET** /portal/catalogue/expdescriptor | getAllExpDescriptors
[**getExpDescriptorUsingGET**](ExperimentDescriptorCatalogueApiApi.md#getExpDescriptorUsingGET) | **GET** /portal/catalogue/expdescriptor/{expdId} | getExpDescriptor


<a name="createExpDescriptorUsingPOST"></a>
# **createExpDescriptorUsingPOST**
> String createExpDescriptorUsingPOST(request, authenticated, authorities0Authority)

Onboard Experiment Descriptor

### Example
```java
// Import classes:
//import ApiException;
//import ExperimentDescriptorCatalogueApiApi;


ExperimentDescriptorCatalogueApiApi apiInstance = new ExperimentDescriptorCatalogueApiApi();
OnboardExpDescriptorRequest request = new OnboardExpDescriptorRequest(); // OnboardExpDescriptorRequest | request
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    String result = apiInstance.createExpDescriptorUsingPOST(request, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ExperimentDescriptorCatalogueApiApi#createExpDescriptorUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **request** | [**OnboardExpDescriptorRequest**](OnboardExpDescriptorRequest.md)| request |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getAllExpDescriptorsUsingGET"></a>
# **getAllExpDescriptorsUsingGET**
> Object getAllExpDescriptorsUsingGET(authenticated, authorities0Authority)

getAllExpDescriptors

### Example
```java
// Import classes:
//import ApiException;
//import ExperimentDescriptorCatalogueApiApi;


ExperimentDescriptorCatalogueApiApi apiInstance = new ExperimentDescriptorCatalogueApiApi();
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    Object result = apiInstance.getAllExpDescriptorsUsingGET(authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ExperimentDescriptorCatalogueApiApi#getAllExpDescriptorsUsingGET");
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

<a name="getExpDescriptorUsingGET"></a>
# **getExpDescriptorUsingGET**
> Object getExpDescriptorUsingGET(expdId, authenticated, authorities0Authority)

getExpDescriptor

### Example
```java
// Import classes:
//import ApiException;
//import ExperimentDescriptorCatalogueApiApi;


ExperimentDescriptorCatalogueApiApi apiInstance = new ExperimentDescriptorCatalogueApiApi();
String expdId = "expdId_example"; // String | expdId
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    Object result = apiInstance.getExpDescriptorUsingGET(expdId, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ExperimentDescriptorCatalogueApiApi#getExpDescriptorUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **expdId** | **String**| expdId |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

