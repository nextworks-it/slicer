package io.swagger.client.slice_manager.v2.api;

import io.swagger.client.slice_manager.v2.invoker.ApiClient;

import io.swagger.client.slice_manager.v2.model.PostRadioService;
import io.swagger.client.slice_manager.v2.model.RadioServiceInput;


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
@Component("io.swagger.client.slice_manager.v2.api.RadioServiceApi")

public class RadioServiceApi {
    private ApiClient apiClient;

    public RadioServiceApi() {
        this(new ApiClient());
    }

    @Autowired
    public RadioServiceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Delete a radio service
     * Radio Service deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>501</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param radioChunkId The radioChunkId parameter
     * @param radioServiceId The radioServiceId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteRadioService(String ranInfrastructureId, String radioChunkId, String radioServiceId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling deleteRadioService");
        }
        
        // verify the required parameter 'radioChunkId' is set
        if (radioChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioChunkId' when calling deleteRadioService");
        }
        
        // verify the required parameter 'radioServiceId' is set
        if (radioServiceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioServiceId' when calling deleteRadioService");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("radio_chunk_id", radioChunkId);
        uriVariables.put("radio_service_id", radioServiceId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service/{radio_service_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get individual radio service information
     * Radio service retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param radioChunkId The radioChunkId parameter
     * @param radioServiceId The radioServiceId parameter
     * @param userId The userId parameter
     * @return PostRadioService
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostRadioService getRadioService(String ranInfrastructureId, String radioChunkId, String radioServiceId, String userId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling getRadioService");
        }
        
        // verify the required parameter 'radioChunkId' is set
        if (radioChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioChunkId' when calling getRadioService");
        }
        
        // verify the required parameter 'radioServiceId' is set
        if (radioServiceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioServiceId' when calling getRadioService");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("radio_chunk_id", radioChunkId);
        uriVariables.put("radio_service_id", radioServiceId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service/{radio_service_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "user_id", userId));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostRadioService> returnType = new ParameterizedTypeReference<PostRadioService>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get radio services information
     * Radio service retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param radioChunkId The radioChunkId parameter
     * @param userId The userId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getRadioServices(String ranInfrastructureId, String radioChunkId, String userId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling getRadioServices");
        }
        
        // verify the required parameter 'radioChunkId' is set
        if (radioChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioChunkId' when calling getRadioServices");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("radio_chunk_id", radioChunkId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service").buildAndExpand(uriVariables).toUriString();
        
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
     * Create a new radio service
     * Radio service registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param radioserviceinput The radioserviceinput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param radioChunkId The radioChunkId parameter
     * @return PostRadioService
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostRadioService postRadioService(RadioServiceInput radioserviceinput, String ranInfrastructureId, String radioChunkId) throws RestClientException {
        Object postBody = radioserviceinput;
        
        // verify the required parameter 'radioserviceinput' is set
        if (radioserviceinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioserviceinput' when calling postRadioService");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling postRadioService");
        }
        
        // verify the required parameter 'radioChunkId' is set
        if (radioChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioChunkId' when calling postRadioService");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("radio_chunk_id", radioChunkId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostRadioService> returnType = new ParameterizedTypeReference<PostRadioService>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Edit individual radio service interfaces
     * Radio service interfaces update method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param radioserviceinput The radioserviceinput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param radioChunkId The radioChunkId parameter
     * @param radioServiceId The radioServiceId parameter
     * @return PostRadioService
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostRadioService putRadioService(RadioServiceInput radioserviceinput, String ranInfrastructureId, String radioChunkId, String radioServiceId) throws RestClientException {
        Object postBody = radioserviceinput;
        
        // verify the required parameter 'radioserviceinput' is set
        if (radioserviceinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioserviceinput' when calling putRadioService");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling putRadioService");
        }
        
        // verify the required parameter 'radioChunkId' is set
        if (radioChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioChunkId' when calling putRadioService");
        }
        
        // verify the required parameter 'radioServiceId' is set
        if (radioServiceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioServiceId' when calling putRadioService");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("radio_chunk_id", radioChunkId);
        uriVariables.put("radio_service_id", radioServiceId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service/{radio_service_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostRadioService> returnType = new ParameterizedTypeReference<PostRadioService>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
}

