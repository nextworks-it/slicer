package io.swagger.client.slice_manager.v2.api;

import io.swagger.client.slice_manager.v2.invoker.ApiClient;

import io.swagger.client.slice_manager.v2.model.PostSlic3;
import io.swagger.client.slice_manager.v2.model.Slic3Input;
import io.swagger.client.slice_manager.v2.model.Slic3Put;


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
@Component("io.swagger.client.slice_manager.v2.api.NetworkSliceCollectionOfChunksApi")

public class NetworkSliceCollectionOfChunksApi {
    private ApiClient apiClient;

    public NetworkSliceCollectionOfChunksApi() {
        this(new ApiClient());
    }

    @Autowired
    public NetworkSliceCollectionOfChunksApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Add chunks to individual Slic3
     * Slic3 update method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>409</b>
     * @param slic3put The slic3put parameter
     * @param slic3Id slic3_id
     * @return PostSlic3
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostSlic3 addChunkSlic3(Slic3Put slic3put, String slic3Id) throws RestClientException {
        Object postBody = slic3put;
        
        // verify the required parameter 'slic3put' is set
        if (slic3put == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3put' when calling addChunkSlic3");
        }
        
        // verify the required parameter 'slic3Id' is set
        if (slic3Id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3Id' when calling addChunkSlic3");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("slic3_id", slic3Id);
        String path = UriComponentsBuilder.fromPath("/slic3/{slic3_id}/add_chunks").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostSlic3> returnType = new ParameterizedTypeReference<PostSlic3>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Remove chunks from individual Slic3
     * Slic3 update method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>409</b>
     * @param slic3put The slic3put parameter
     * @param slic3Id slic3_id
     * @return PostSlic3
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostSlic3 delChunkSlic3(Slic3Put slic3put, String slic3Id) throws RestClientException {
        Object postBody = slic3put;
        
        // verify the required parameter 'slic3put' is set
        if (slic3put == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3put' when calling delChunkSlic3");
        }
        
        // verify the required parameter 'slic3Id' is set
        if (slic3Id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3Id' when calling delChunkSlic3");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("slic3_id", slic3Id);
        String path = UriComponentsBuilder.fromPath("/slic3/{slic3_id}/del_chunks").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostSlic3> returnType = new ParameterizedTypeReference<PostSlic3>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Delete a slice
     * Slice deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>501</b>
     * @param slic3Id The slic3Id parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteSlic3(String slic3Id) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'slic3Id' is set
        if (slic3Id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3Id' when calling deleteSlic3");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("slic3_id", slic3Id);
        String path = UriComponentsBuilder.fromPath("/slic3/{slic3_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get individual slice information
     * Slice retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param slic3Id The slic3Id parameter
     * @return PostSlic3
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostSlic3 getSlic3(String slic3Id) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'slic3Id' is set
        if (slic3Id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3Id' when calling getSlic3");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("slic3_id", slic3Id);
        String path = UriComponentsBuilder.fromPath("/slic3/{slic3_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostSlic3> returnType = new ParameterizedTypeReference<PostSlic3>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get slice(s) information
     * Slice retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param userId The userId parameter
     * @param phyId The phyId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getSlic3s(String userId, String phyId) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/slic3").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "user_id", userId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "phy_id", phyId));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List> returnType = new ParameterizedTypeReference<List>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Create a new slice
     * Slice registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param slic3input The slic3input parameter
     * @return PostSlic3
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostSlic3 postSlic3(Slic3Input slic3input) throws RestClientException {
        Object postBody = slic3input;
        
        // verify the required parameter 'slic3input' is set
        if (slic3input == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slic3input' when calling postSlic3");
        }
        
        String path = UriComponentsBuilder.fromPath("/slic3").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostSlic3> returnType = new ParameterizedTypeReference<PostSlic3>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
}

