# ElmRestControllerApi

All URIs are relative to *https://localhost:8083*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createExperimentUsingPOST**](ElmRestControllerApi.md#createExperimentUsingPOST) | **POST** /portal/elm/experiment | Request experiment schedule
[**deleteExperimentUsingDELETE**](ElmRestControllerApi.md#deleteExperimentUsingDELETE) | **DELETE** /portal/elm/experiment/{expId} | Delete experiment
[**getAllExperimentsUsingGET**](ElmRestControllerApi.md#getAllExperimentsUsingGET) | **GET** /portal/elm/experiment | Retrieve list of experiments
[**requestExperimentActionUsingPOST**](ElmRestControllerApi.md#requestExperimentActionUsingPOST) | **POST** /portal/elm/experiment/{expId}/action/{action} | Request experiment action
[**updateExperimentStatusUsingPUT**](ElmRestControllerApi.md#updateExperimentStatusUsingPUT) | **PUT** /portal/elm/experiment/{expId}/status | Update experiment status
[**updateExperimentTimeslotUsingPUT**](ElmRestControllerApi.md#updateExperimentTimeslotUsingPUT) | **PUT** /portal/elm/experiment/{expId}/timeslot | Update experiment timeslot


<a name="createExperimentUsingPOST"></a>
# **createExperimentUsingPOST**
> String createExperimentUsingPOST(request, authenticated, authorities0Authority)

Request experiment schedule

### Example
```java
// Import classes:
//import ApiException;
//import ElmRestControllerApi;


ElmRestControllerApi apiInstance = new ElmRestControllerApi();
ExperimentSchedulingRequest request = new ExperimentSchedulingRequest(); // ExperimentSchedulingRequest | request
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    String result = apiInstance.createExperimentUsingPOST(request, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ElmRestControllerApi#createExperimentUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **request** | [**ExperimentSchedulingRequest**](ExperimentSchedulingRequest.md)| request |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="deleteExperimentUsingDELETE"></a>
# **deleteExperimentUsingDELETE**
> Object deleteExperimentUsingDELETE(expId, authenticated, authorities0Authority)

Delete experiment

### Example
```java
// Import classes:
//import ApiException;
//import ElmRestControllerApi;


ElmRestControllerApi apiInstance = new ElmRestControllerApi();
String expId = "expId_example"; // String | expId
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    Object result = apiInstance.deleteExperimentUsingDELETE(expId, authenticated, authorities0Authority);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ElmRestControllerApi#deleteExperimentUsingDELETE");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **expId** | **String**| expId |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getAllExperimentsUsingGET"></a>
# **getAllExperimentsUsingGET**
> List&lt;Experiment&gt; getAllExperimentsUsingGET(authenticated, authorities0Authority, expDId, expId)

Retrieve list of experiments

### Example
```java
// Import classes:
//import ApiException;
//import ElmRestControllerApi;


ElmRestControllerApi apiInstance = new ElmRestControllerApi();
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
String expDId = "expDId_example"; // String | expDId
String expId = "expId_example"; // String | expId
try {
    List<Experiment> result = apiInstance.getAllExperimentsUsingGET(authenticated, authorities0Authority, expDId, expId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ElmRestControllerApi#getAllExperimentsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]
 **expDId** | **String**| expDId | [optional]
 **expId** | **String**| expId | [optional]

### Return type

[**List&lt;Experiment&gt;**](Experiment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="requestExperimentActionUsingPOST"></a>
# **requestExperimentActionUsingPOST**
> requestExperimentActionUsingPOST(action, expId, authenticated, authorities0Authority, request)

Request experiment action

### Example
```java
// Import classes:
//import ApiException;
//import ElmRestControllerApi;


ElmRestControllerApi apiInstance = new ElmRestControllerApi();
String action = "action_example"; // String | action
String expId = "expId_example"; // String | expId
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
ExecuteExperimentRequest request = new ExecuteExperimentRequest(); // ExecuteExperimentRequest | request
try {
    apiInstance.requestExperimentActionUsingPOST(action, expId, authenticated, authorities0Authority, request);
} catch (ApiException e) {
    System.err.println("Exception when calling ElmRestControllerApi#requestExperimentActionUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **action** | **String**| action |
 **expId** | **String**| expId |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]
 **request** | [**ExecuteExperimentRequest**](ExecuteExperimentRequest.md)| request | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updateExperimentStatusUsingPUT"></a>
# **updateExperimentStatusUsingPUT**
> updateExperimentStatusUsingPUT(expId, request, authenticated, authorities0Authority)

Update experiment status

### Example
```java
// Import classes:
//import ApiException;
//import ElmRestControllerApi;


ElmRestControllerApi apiInstance = new ElmRestControllerApi();
String expId = "expId_example"; // String | expId
UpdateExperimentStatusRequest request = new UpdateExperimentStatusRequest(); // UpdateExperimentStatusRequest | request
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    apiInstance.updateExperimentStatusUsingPUT(expId, request, authenticated, authorities0Authority);
} catch (ApiException e) {
    System.err.println("Exception when calling ElmRestControllerApi#updateExperimentStatusUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **expId** | **String**| expId |
 **request** | [**UpdateExperimentStatusRequest**](UpdateExperimentStatusRequest.md)| request |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updateExperimentTimeslotUsingPUT"></a>
# **updateExperimentTimeslotUsingPUT**
> updateExperimentTimeslotUsingPUT(expId, request, authenticated, authorities0Authority)

Update experiment timeslot

### Example
```java
// Import classes:
//import ApiException;
//import ElmRestControllerApi;


ElmRestControllerApi apiInstance = new ElmRestControllerApi();
String expId = "expId_example"; // String | expId
ModifyExperimentTimeslotRequest request = new ModifyExperimentTimeslotRequest(); // ModifyExperimentTimeslotRequest | request
Boolean authenticated = true; // Boolean | 
String authorities0Authority = "authorities0Authority_example"; // String | 
try {
    apiInstance.updateExperimentTimeslotUsingPUT(expId, request, authenticated, authorities0Authority);
} catch (ApiException e) {
    System.err.println("Exception when calling ElmRestControllerApi#updateExperimentTimeslotUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **expId** | **String**| expId |
 **request** | [**ModifyExperimentTimeslotRequest**](ModifyExperimentTimeslotRequest.md)| request |
 **authenticated** | **Boolean**|  | [optional]
 **authorities0Authority** | **String**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

