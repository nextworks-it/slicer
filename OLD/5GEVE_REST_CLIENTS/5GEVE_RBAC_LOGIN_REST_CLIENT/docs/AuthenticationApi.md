# AuthenticationApi

All URIs are relative to *https://www.5g-eve.eu/portal/rbac*

Method | HTTP request | Description
------------- | ------------- | -------------
[**login**](AuthenticationApi.md#login) | **POST** /login | 
[**logout**](AuthenticationApi.md#logout) | **GET** /logout | 


<a name="login"></a>
# **login**
> login(body)





### Example
```java
// Import classes:
//import ApiException;
//import AuthenticationApi;


AuthenticationApi apiInstance = new AuthenticationApi();
UserLogin body = new UserLogin(); // UserLogin | 
try {
    apiInstance.login(body);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthenticationApi#login");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserLogin**](UserLogin.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="logout"></a>
# **logout**
> logout()





### Example
```java
// Import classes:
//import ApiClient;
//import ApiException;
//import Configuration;
//import io.swagger.client.auth.*;
//import AuthenticationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: api_key
ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
api_key.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//api_key.setApiKeyPrefix("Token");

AuthenticationApi apiInstance = new AuthenticationApi();
try {
    apiInstance.logout();
} catch (ApiException e) {
    System.err.println("Exception when calling AuthenticationApi#logout");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

