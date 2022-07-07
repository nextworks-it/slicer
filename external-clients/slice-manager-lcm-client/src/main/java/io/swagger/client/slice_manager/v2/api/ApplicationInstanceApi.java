package io.swagger.client.slice_manager.v2.api;

import io.swagger.client.slice_manager.v2.invoker.ApiClient;

import io.swagger.client.slice_manager.v2.model.NetworkServiceInstanceInput;
import io.swagger.client.slice_manager.v2.model.NetworkServiceInstanceReactionInput;
import io.swagger.client.slice_manager.v2.model.NetworkServiceInstanceScaleInput;
import io.swagger.client.slice_manager.v2.model.PostNetworkServiceInstance;


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
@Component("io.swagger.client.slice_manager.v2.api.ApplicationInstanceApi")

public class ApplicationInstanceApi {
    private ApiClient apiClient;

    public ApplicationInstanceApi() {
        this(new ApiClient());
    }

    @Autowired
    public ApplicationInstanceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Delete a network service instance
     * Network service instance deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>501</b>
     * @param networkServiceInstanceId The networkServiceInstanceId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteNetworkServiceInstance(String networkServiceInstanceId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'networkServiceInstanceId' is set
        if (networkServiceInstanceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'networkServiceInstanceId' when calling deleteNetworkServiceInstance");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("network_service_instance_id", networkServiceInstanceId);
        String path = UriComponentsBuilder.fromPath("/network_service_instance/{network_service_instance_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get individual network service instance information
     * Network service instance retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param networkServiceInstanceId The networkServiceInstanceId parameter
     * @return PostNetworkServiceInstance
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostNetworkServiceInstance getNetworkServiceInstance(String networkServiceInstanceId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'networkServiceInstanceId' is set
        if (networkServiceInstanceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'networkServiceInstanceId' when calling getNetworkServiceInstance");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("network_service_instance_id", networkServiceInstanceId);
        String path = UriComponentsBuilder.fromPath("/network_service_instance/{network_service_instance_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostNetworkServiceInstance> returnType = new ParameterizedTypeReference<PostNetworkServiceInstance>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get network service instances information
     * Network service instance retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param userId The userId parameter
     * @param slic3Id The slic3Id parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getNetworkServiceInstances(String userId, String slic3Id) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/network_service_instance").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "user_id", userId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "slic3_id", slic3Id));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List> returnType = new ParameterizedTypeReference<List>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Perform reaction on individual network service instance
     * Network Service Instance reaction method
     * <p><b>204</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param networkserviceinstancereactioninput The networkserviceinstancereactioninput parameter
     * @param networkServiceInstanceId The networkServiceInstanceId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void networkServiceInstanceReaction(NetworkServiceInstanceReactionInput networkserviceinstancereactioninput, String networkServiceInstanceId) throws RestClientException {
        Object postBody = networkserviceinstancereactioninput;
        
        // verify the required parameter 'networkserviceinstancereactioninput' is set
        if (networkserviceinstancereactioninput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'networkserviceinstancereactioninput' when calling networkServiceInstanceReaction");
        }
        
        // verify the required parameter 'networkServiceInstanceId' is set
        if (networkServiceInstanceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'networkServiceInstanceId' when calling networkServiceInstanceReaction");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("network_service_instance_id", networkServiceInstanceId);
        String path = UriComponentsBuilder.fromPath("/network_service_instance/{network_service_instance_id}/reaction").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Create a new network service instance
     * Network service instance registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param networkserviceinstanceinput The networkserviceinstanceinput parameter
     * @return PostNetworkServiceInstance
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostNetworkServiceInstance postNetworkServiceInstance(NetworkServiceInstanceInput networkserviceinstanceinput) throws RestClientException {
        Object postBody = networkserviceinstanceinput;
        
        // verify the required parameter 'networkserviceinstanceinput' is set
        if (networkserviceinstanceinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'networkserviceinstanceinput' when calling postNetworkServiceInstance");
        }
        
        String path = UriComponentsBuilder.fromPath("/network_service_instance").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostNetworkServiceInstance> returnType = new ParameterizedTypeReference<PostNetworkServiceInstance>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Scale individual network service instance
     * Network service instance scaling method
     * <p><b>204</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param networkserviceinstancescaleinput The networkserviceinstancescaleinput parameter
     * @param networkServiceInstanceId The networkServiceInstanceId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void scaleNetworkServiceInstance(NetworkServiceInstanceScaleInput networkserviceinstancescaleinput, String networkServiceInstanceId) throws RestClientException {
        Object postBody = networkserviceinstancescaleinput;
        
        // verify the required parameter 'networkserviceinstancescaleinput' is set
        if (networkserviceinstancescaleinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'networkserviceinstancescaleinput' when calling scaleNetworkServiceInstance");
        }
        
        // verify the required parameter 'networkServiceInstanceId' is set
        if (networkServiceInstanceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'networkServiceInstanceId' when calling scaleNetworkServiceInstance");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("network_service_instance_id", networkServiceInstanceId);
        String path = UriComponentsBuilder.fromPath("/network_service_instance/{network_service_instance_id}/scale").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
}

