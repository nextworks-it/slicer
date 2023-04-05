# VerticalServiceDescriptorCatalogueApiApi

All URIs are relative to *https://localhost:8082*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createVsDescriptorUsingPOST**](VerticalServiceDescriptorCatalogueApiApi.md#createVsDescriptorUsingPOST) | **POST** /portal/catalogue/vsdescriptor | Onboard a new Vertical Service Descriptor
[**getAllVsDescriptorsUsingGET**](VerticalServiceDescriptorCatalogueApiApi.md#getAllVsDescriptorsUsingGET) | **GET** /portal/catalogue/vsdescriptor | Query ALL the Vertical Service Descriptor
[**getVsDescriptorUsingGET**](VerticalServiceDescriptorCatalogueApiApi.md#getVsDescriptorUsingGET) | **GET** /portal/catalogue/vsdescriptor/{vsdId} | Query a Vertical Service Descriptor with a given ID


<a name="createVsDescriptorUsingPOST"></a>
# **createVsDescriptorUsingPOST**
> String createVsDescriptorUsingPOST(request, authenticated, authorities0Authority)

Onboard a new Vertical Service Descriptor

### Example
```java
// Import classes:
//import ApiException;
//import VerticalServiceDescriptorCatalogueApiApi;


VerticalServiceDescriptorCatalogueApiApi apiInstance = new VerticalServiceDescriptorCatalogueApiApi();
OnboardVsDescriptorRequest request = new OnboardVsDescriptorRequest(); // OnboardVsDescriptorRequest | request
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    String result = apiInstance.createVsDescriptorUsingPOST(request, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VerticalServiceDescriptorCatalogueApiApi#createVsDescriptorUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **request** | [**OnboardVsDescriptorRequest**](OnboardVsDescriptorRequest.md)| request |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getAllVsDescriptorsUsingGET"></a>
# **getAllVsDescriptorsUsingGET**
> List&lt;VsDescriptor&gt; getAllVsDescriptorsUsingGET(authenticated, authorities0Authority)

Query ALL the Vertical Service Descriptor

### Example
```java
// Import classes:
//import ApiException;
//import VerticalServiceDescriptorCatalogueApiApi;


VerticalServiceDescriptorCatalogueApiApi apiInstance = new VerticalServiceDescriptorCatalogueApiApi();
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    List<VsDescriptor> result = apiInstance.getAllVsDescriptorsUsingGET(authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VerticalServiceDescriptorCatalogueApiApi#getAllVsDescriptorsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

[**List&lt;VsDescriptor&gt;**](VsDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getVsDescriptorUsingGET"></a>
# **getVsDescriptorUsingGET**
> VsDescriptor getVsDescriptorUsingGET(vsdId, authenticated, authorities0Authority)

Query a Vertical Service Descriptor with a given ID

### Example
```java
// Import classes:
//import ApiException;
//import VerticalServiceDescriptorCatalogueApiApi;


VerticalServiceDescriptorCatalogueApiApi apiInstance = new VerticalServiceDescriptorCatalogueApiApi();
String vsdId = "vsdId_example"; // String | vsdId
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    VsDescriptor result = apiInstance.getVsDescriptorUsingGET(vsdId, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VerticalServiceDescriptorCatalogueApiApi#getVsDescriptorUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **vsdId** | **String**| vsdId |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

[**VsDescriptor**](VsDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

