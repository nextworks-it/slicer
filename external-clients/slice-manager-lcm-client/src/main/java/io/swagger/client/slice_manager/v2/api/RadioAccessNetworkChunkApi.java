package io.swagger.client.slice_manager.v2.api;

import io.swagger.client.slice_manager.v2.invoker.ApiClient;

import io.swagger.client.slice_manager.v2.model.GetRadioChunkTopo;
import io.swagger.client.slice_manager.v2.model.PostRadioChunk;
import io.swagger.client.slice_manager.v2.model.RadioChunkInput;


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
@Component("io.swagger.client.slice_manager.v2.api.RadioAccessNetworkChunkApi")

public class RadioAccessNetworkChunkApi {
    private ApiClient apiClient;

    public RadioAccessNetworkChunkApi() {
        this(new ApiClient());
    }

    @Autowired
    public RadioAccessNetworkChunkApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Delete a radio chunk
     * Radio chunk deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>501</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param radioChunkId The radioChunkId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteRadioChunk(String ranInfrastructureId, String radioChunkId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling deleteRadioChunk");
        }
        
        // verify the required parameter 'radioChunkId' is set
        if (radioChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioChunkId' when calling deleteRadioChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("radio_chunk_id", radioChunkId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get individual radio chunk information
     * Radio chunk retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param radioChunkId The radioChunkId parameter
     * @return PostRadioChunk
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostRadioChunk getRadioChunk(String ranInfrastructureId, String radioChunkId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling getRadioChunk");
        }
        
        // verify the required parameter 'radioChunkId' is set
        if (radioChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioChunkId' when calling getRadioChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("radio_chunk_id", radioChunkId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostRadioChunk> returnType = new ParameterizedTypeReference<PostRadioChunk>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get radio chunks information
     * Radio Chunk retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param userId The userId parameter
     * @return PostRadioChunk
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostRadioChunk getRadioChunks(String ranInfrastructureId, String userId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling getRadioChunks");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "user_id", userId));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostRadioChunk> returnType = new ParameterizedTypeReference<PostRadioChunk>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get individual radio chunk topology information
     * Radio chunk topology retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param radioChunkId The radioChunkId parameter
     * @return GetRadioChunkTopo
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public GetRadioChunkTopo getadioChunkTopology(String ranInfrastructureId, String radioChunkId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling getadioChunkTopology");
        }
        
        // verify the required parameter 'radioChunkId' is set
        if (radioChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioChunkId' when calling getadioChunkTopology");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("radio_chunk_id", radioChunkId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/chunk_topology").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GetRadioChunkTopo> returnType = new ParameterizedTypeReference<GetRadioChunkTopo>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Create a new radio chunk
     * Radio Chunk registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param radiochunkinput The radiochunkinput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @return PostRadioChunk
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostRadioChunk postRadioChunk(RadioChunkInput radiochunkinput, String ranInfrastructureId) throws RestClientException {
        Object postBody = radiochunkinput;
        
        // verify the required parameter 'radiochunkinput' is set
        if (radiochunkinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radiochunkinput' when calling postRadioChunk");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling postRadioChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostRadioChunk> returnType = new ParameterizedTypeReference<PostRadioChunk>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Edit individual radio chunk
     * Radio chunk update method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param radiochunkinput The radiochunkinput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param radioChunkId The radioChunkId parameter
     * @return PostRadioChunk
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostRadioChunk putRadioChunk(RadioChunkInput radiochunkinput, String ranInfrastructureId, String radioChunkId) throws RestClientException {
        Object postBody = radiochunkinput;
        
        // verify the required parameter 'radiochunkinput' is set
        if (radiochunkinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radiochunkinput' when calling putRadioChunk");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling putRadioChunk");
        }
        
        // verify the required parameter 'radioChunkId' is set
        if (radioChunkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radioChunkId' when calling putRadioChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("radio_chunk_id", radioChunkId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostRadioChunk> returnType = new ParameterizedTypeReference<PostRadioChunk>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Validate radio chunk topology
     * Radio Chunk validation method
     * <p><b>204</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param radiochunkinput The radiochunkinput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void validateRadioChunk(RadioChunkInput radiochunkinput, String ranInfrastructureId) throws RestClientException {
        Object postBody = radiochunkinput;
        
        // verify the required parameter 'radiochunkinput' is set
        if (radiochunkinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'radiochunkinput' when calling validateRadioChunk");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling validateRadioChunk");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/radio_chunk/validate").buildAndExpand(uriVariables).toUriString();
        
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

