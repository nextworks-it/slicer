package io.swagger.client.slice_manager.v2.api;

import io.swagger.client.slice_manager.v2.invoker.ApiClient;

import io.swagger.client.slice_manager.v2.model.ComputeChunkInput;
import io.swagger.client.slice_manager.v2.model.ComputeChunkNewCPUInput;
import io.swagger.client.slice_manager.v2.model.ComputeChunkNewRAMInput;
import io.swagger.client.slice_manager.v2.model.ComputeChunkNewStorageInput;
import io.swagger.client.slice_manager.v2.model.PostComputeChunk;


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
@Component("io.swagger.client.slice_manager.v2.api.EdgeCloudComputeChunkApi")

public class EdgeCloudComputeChunkApi {
    private ApiClient apiClient;

    public EdgeCloudComputeChunkApi() {
        this(new ApiClient());
    }

    @Autowired
    public EdgeCloudComputeChunkApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Delete a Compute Chunk
     * Compute Chunk deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>501</b>
     * @param computeChunkId The computeChunkId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteComputeChunk(String computeChunkId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'computeChunkId' is set
        if (computeChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'computeChunkId' when calling deleteComputeChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("compute_chunk_id", computeChunkId);
        String path = UriComponentsBuilder.fromPath("/compute_chunk/{compute_chunk_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get individual Compute Chunk information
     * Compute Chunk retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param computeChunkId The computeChunkId parameter
     * @return PostComputeChunk
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostComputeChunk getComputeChunk(String computeChunkId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'computeChunkId' is set
        if (computeChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'computeChunkId' when calling getComputeChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("compute_chunk_id", computeChunkId);
        String path = UriComponentsBuilder.fromPath("/compute_chunk/{compute_chunk_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostComputeChunk> returnType = new ParameterizedTypeReference<PostComputeChunk>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get Compute Chunks information
     * Compute Chunks retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param userId The userId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getComputeChunks(String userId) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/compute_chunk").build().toUriString();
        
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
     * Modify an OpenStack project CPU quota
     * OpenStack Project CPU quota modify method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>501</b>
     * @param computechunknewcpuinput The computechunknewcpuinput parameter
     * @param computeChunkId The computeChunkId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void modifyCpuComputeChunk(ComputeChunkNewCPUInput computechunknewcpuinput, String computeChunkId) throws RestClientException {
        Object postBody = computechunknewcpuinput;
        
        // verify the required parameter 'computechunknewcpuinput' is set
        if (computechunknewcpuinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'computechunknewcpuinput' when calling modifyCpuComputeChunk");
        }
        
        // verify the required parameter 'computeChunkId' is set
        if (computeChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'computeChunkId' when calling modifyCpuComputeChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("compute_chunk_id", computeChunkId);
        String path = UriComponentsBuilder.fromPath("/compute_chunk/{compute_chunk_id}/cpus").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Modify an OpenStack project RAM quota
     * OpenStack Project RAM quota modify method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>501</b>
     * @param computechunknewraminput The computechunknewraminput parameter
     * @param computeChunkId The computeChunkId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void modifyRamComputeChunk(ComputeChunkNewRAMInput computechunknewraminput, String computeChunkId) throws RestClientException {
        Object postBody = computechunknewraminput;
        
        // verify the required parameter 'computechunknewraminput' is set
        if (computechunknewraminput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'computechunknewraminput' when calling modifyRamComputeChunk");
        }
        
        // verify the required parameter 'computeChunkId' is set
        if (computeChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'computeChunkId' when calling modifyRamComputeChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("compute_chunk_id", computeChunkId);
        String path = UriComponentsBuilder.fromPath("/compute_chunk/{compute_chunk_id}/ram").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Modify an OpenStack project storage quota
     * OpenStack Project Storage quota modify method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>501</b>
     * @param computechunknewstorageinput The computechunknewstorageinput parameter
     * @param computeChunkId The computeChunkId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void modifyStorageComputeChunk(ComputeChunkNewStorageInput computechunknewstorageinput, String computeChunkId) throws RestClientException {
        Object postBody = computechunknewstorageinput;
        
        // verify the required parameter 'computechunknewstorageinput' is set
        if (computechunknewstorageinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'computechunknewstorageinput' when calling modifyStorageComputeChunk");
        }
        
        // verify the required parameter 'computeChunkId' is set
        if (computeChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'computeChunkId' when calling modifyStorageComputeChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("compute_chunk_id", computeChunkId);
        String path = UriComponentsBuilder.fromPath("/compute_chunk/{compute_chunk_id}/storage").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Create a new Compute Chunk
     * Compute Chunk registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * <p><b>504</b>
     * @param computechunkinput The computechunkinput parameter
     * @return PostComputeChunk
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostComputeChunk postComputeChunk(ComputeChunkInput computechunkinput) throws RestClientException {
        Object postBody = computechunkinput;
        
        // verify the required parameter 'computechunkinput' is set
        if (computechunkinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'computechunkinput' when calling postComputeChunk");
        }
        
        String path = UriComponentsBuilder.fromPath("/compute_chunk").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostComputeChunk> returnType = new ParameterizedTypeReference<PostComputeChunk>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
}

