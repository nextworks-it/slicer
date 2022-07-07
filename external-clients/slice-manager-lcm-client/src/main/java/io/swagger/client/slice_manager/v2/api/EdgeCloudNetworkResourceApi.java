package io.swagger.client.slice_manager.v2.api;

import io.swagger.client.slice_manager.v2.invoker.ApiClient;

import io.swagger.client.slice_manager.v2.model.PhysicalNetworkInput;
import io.swagger.client.slice_manager.v2.model.PostPhysicalNetwork;


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
@Component("io.swagger.client.slice_manager.v2.api.EdgeCloudNetworkResourceApi")

public class EdgeCloudNetworkResourceApi {
    private ApiClient apiClient;

    public EdgeCloudNetworkResourceApi() {
        this(new ApiClient());
    }

    @Autowired
    public EdgeCloudNetworkResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Delete a edge/cloud network
     * Edge/Cloud Network deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>501</b>
     * @param physicalNetworkId The physicalNetworkId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deletePhysicalNetwork(String physicalNetworkId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'physicalNetworkId' is set
        if (physicalNetworkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'physicalNetworkId' when calling deletePhysicalNetwork");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("physical_network_id", physicalNetworkId);
        String path = UriComponentsBuilder.fromPath("/physical_network/{physical_network_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get individual edge/cloud network information
     * Edge/Cloud Network retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param physicalNetworkId The physicalNetworkId parameter
     * @return PostPhysicalNetwork
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostPhysicalNetwork getPhysicalNetwork(String physicalNetworkId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'physicalNetworkId' is set
        if (physicalNetworkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'physicalNetworkId' when calling getPhysicalNetwork");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("physical_network_id", physicalNetworkId);
        String path = UriComponentsBuilder.fromPath("/physical_network/{physical_network_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostPhysicalNetwork> returnType = new ParameterizedTypeReference<PostPhysicalNetwork>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get edge/cloud networks information
     * Edge/Cloud Network retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param userId The userId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getPhysicalNetworks(String userId) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/physical_network").build().toUriString();
        
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
     * Register a new edge/cloud network resource
     * Edge/Cloud Network registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>422</b>
     * <p><b>503</b>
     * <p><b>504</b>
     * @param physicalnetworkinput The physicalnetworkinput parameter
     * @return PostPhysicalNetwork
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostPhysicalNetwork postPhysicalNetwork(PhysicalNetworkInput physicalnetworkinput) throws RestClientException {
        Object postBody = physicalnetworkinput;
        
        // verify the required parameter 'physicalnetworkinput' is set
        if (physicalnetworkinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'physicalnetworkinput' when calling postPhysicalNetwork");
        }
        
        String path = UriComponentsBuilder.fromPath("/physical_network").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostPhysicalNetwork> returnType = new ParameterizedTypeReference<PostPhysicalNetwork>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
}

