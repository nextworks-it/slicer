package io.swagger.client.slice_manager.v2.api;

import io.swagger.client.slice_manager.v2.invoker.ApiClient;

import io.swagger.client.slice_manager.v2.model.PostSlic3Type;
import io.swagger.client.slice_manager.v2.model.PostSlic3TypeChunks;
import io.swagger.client.slice_manager.v2.model.Slic3TypeInput;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2022-05-17T10:16:59.588+02:00")
@Component("io.swagger.client.slice_manager.v2.api.NetworkSliceTypeNestApi")

public class NetworkSliceTypeNestApi {
    private ApiClient apiClient;

    public NetworkSliceTypeNestApi() {
        this(new ApiClient());
    }

    @Autowired
    public NetworkSliceTypeNestApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Delete a network slice type
     * Network slice type deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>501</b>
     * @param slic3TypeId The slic3TypeId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteSlic3Type(String slic3TypeId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'slic3TypeId' is set
        if (slic3TypeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3TypeId' when calling deleteSlic3Type");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("slic3_type_id", slic3TypeId);
        String path = UriComponentsBuilder.fromPath("/slic3_type/{slic3_type_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get individual network slice type information
     * Network slice type retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param slic3TypeId The slic3TypeId parameter
     * @return PostSlic3Type
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostSlic3Type getSlic3Type(String slic3TypeId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'slic3TypeId' is set
        if (slic3TypeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3TypeId' when calling getSlic3Type");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("slic3_type_id", slic3TypeId);
        String path = UriComponentsBuilder.fromPath("/slic3_type/{slic3_type_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostSlic3Type> returnType = new ParameterizedTypeReference<PostSlic3Type>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get individual network slice type blueprint information
     * Network slice type atomic chunks retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param slic3TypeId The slic3TypeId parameter
     * @return PostSlic3TypeChunks
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostSlic3TypeChunks getSlic3TypeChunks(String slic3TypeId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'slic3TypeId' is set
        if (slic3TypeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3TypeId' when calling getSlic3TypeChunks");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("slic3_type_id", slic3TypeId);
        String path = UriComponentsBuilder.fromPath("/slic3_type/{slic3_type_id}/slice_blueprint").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostSlic3TypeChunks> returnType = new ParameterizedTypeReference<PostSlic3TypeChunks>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get network slice types information
     * Network slice types retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param userId The userId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getSlic3Types(String userId) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/slic3_type").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "user_id", userId));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List> returnType = new ParameterizedTypeReference<List>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Create a new network slice type
     * Network slice type registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param slic3typeinput The slic3typeinput parameter
     * @return PostSlic3Type
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostSlic3Type postSlic3Type(Slic3TypeInput slic3typeinput) throws RestClientException {
        Object postBody = slic3typeinput;
        
        // verify the required parameter 'slic3typeinput' is set
        if (slic3typeinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3typeinput' when calling postSlic3Type");
        }
        
        String path = UriComponentsBuilder.fromPath("/slic3_type").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostSlic3Type> returnType = new ParameterizedTypeReference<PostSlic3Type>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
}

