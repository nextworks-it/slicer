# UserApi

All URIs are relative to */api/v1.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUser**](UserApi.md#deleteUser) | **DELETE** /user/{user_id} | Delete an user
[**getUser**](UserApi.md#getUser) | **GET** /user/{user_id} | Get individual user information
[**getUsers**](UserApi.md#getUsers) | **GET** /user | Get users information
[**postUser**](UserApi.md#postUser) | **POST** /user | Register a new user




<a name="deleteUser"></a>
# **deleteUser**
> deleteUser(userId)

Delete an user

User deletion method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.UserApi;



UserApi apiInstance = new UserApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    apiInstance.deleteUser(userId);
} catch (ApiException e) {
    System.err.println("Exception when calling UserApi#deleteUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**|  |


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getUser"></a>
# **getUser**
> GetUser getUser(userId)

Get individual user information

User retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.UserApi;



UserApi apiInstance = new UserApi();

String userId = Arrays.asList("userId_example"); // String | 

try {
    GetUser result = apiInstance.getUser(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserApi#getUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**|  |


### Return type

[**GetUser**](GetUser.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="getUsers"></a>
# **getUsers**
> List getUsers(name, email)

Get users information

User retrieval method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.UserApi;



UserApi apiInstance = new UserApi();

String name = Arrays.asList("name_example"); // String | 

String email = Arrays.asList("email_example"); // String | 

try {
    List result = apiInstance.getUsers(name, email);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserApi#getUsers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [optional]
 **email** | **String**|  | [optional]


### Return type

[**List**](List.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


<a name="postUser"></a>
# **postUser**
> PostUser postUser(userinput)

Register a new user

User registration method

### Example
```java
// Import classes:
//import io.swagger.client.slice_manager.v2.invoker.ApiException;
//import io.swagger.client.slice_manager.v2.api.UserApi;



UserApi apiInstance = new UserApi();

UserInput userinput = ; // UserInput | 

try {
    PostUser result = apiInstance.postUser(userinput);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserApi#postUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userinput** | [**UserInput**](.md)|  |


### Return type

[**PostUser**](PostUser.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json



