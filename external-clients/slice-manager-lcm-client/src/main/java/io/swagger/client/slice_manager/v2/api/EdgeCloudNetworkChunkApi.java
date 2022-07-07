package io.swagger.client.slice_manager.v2.api;

import io.swagger.client.slice_manager.v2.invoker.ApiClient;

import io.swagger.client.slice_manager.v2.model.NetworkChunkInput;
import io.swagger.client.slice_manager.v2.model.PostNetworkChunk;


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
@Component("io.swagger.client.slice_manager.v2.api.EdgeCloudNetworkChunkApi")

public class EdgeCloudNetworkChunkApi {
    private ApiClient apiClient;

    public EdgeCloudNetworkChunkApi() {
        this(new ApiClient());
    }

    @Autowired
    public EdgeCloudNetworkChunkApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Delete a Network Chunk
     * Network Chunk deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>501</b>
     * @param networkChunkId The networkChunkId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteNetworkChunk(String networkChunkId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'networkChunkId' is set
        if (networkChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'networkChunkId' when calling deleteNetworkChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("network_chunk_id", networkChunkId);
        String path = UriComponentsBuilder.fromPath("/network_chunk/{network_chunk_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get individual Network Chunk information
     * Network Chunk retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param networkChunkId The networkChunkId parameter
     * @return PostNetworkChunk
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostNetworkChunk getNetworkChunk(String networkChunkId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'networkChunkId' is set
        if (networkChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'networkChunkId' when calling getNetworkChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("network_chunk_id", networkChunkId);
        String path = UriComponentsBuilder.fromPath("/network_chunk/{network_chunk_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostNetworkChunk> returnType = new ParameterizedTypeReference<PostNetworkChunk>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get Network Chunks information
     * Network Chunks retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param userId The userId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getNetworkChunks(String userId) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/network_chunk").build().toUriString();
        
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
     * Create a new Network Chunk
     * Network Chunk registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * <p><b>504</b>
     * @param networkchunkinput The networkchunkinput parameter
     * @return PostNetworkChunk
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostNetworkChunk postNetworkChunk(NetworkChunkInput networkchunkinput) throws RestClientException {
        Object postBody = networkchunkinput;
        
        // verify the required parameter 'networkchunkinput' is set
        if (networkchunkinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'networkchunkinput' when calling postNetworkChunk");
        }
        
        String path = UriComponentsBuilder.fromPath("/network_chunk").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostNetworkChunk> returnType = new ParameterizedTypeReference<PostNetworkChunk>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
}

