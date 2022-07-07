package io.swagger.client.slice_manager.v2.api;

import io.swagger.client.slice_manager.v2.invoker.ApiClient;

import io.swagger.client.slice_manager.v2.model.PostTelemetryServer;
import io.swagger.client.slice_manager.v2.model.PostTelemetryTarget;
import io.swagger.client.slice_manager.v2.model.PutTelemetryTargetInput;
import io.swagger.client.slice_manager.v2.model.TelemetryServerInput;


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
@Component("io.swagger.client.slice_manager.v2.api.ResourceTelemetryApi")

public class ResourceTelemetryApi {
    private ApiClient apiClient;

    public ResourceTelemetryApi() {
        this(new ApiClient());
    }

    @Autowired
    public ResourceTelemetryApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Delete a telemetry server
     * Telemetry server deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>501</b>
     * @param telemetryServerId The telemetryServerId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteTelemetryServer(String telemetryServerId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'telemetryServerId' is set
        if (telemetryServerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'telemetryServerId' when calling deleteTelemetryServer");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("telemetry_server_id", telemetryServerId);
        String path = UriComponentsBuilder.fromPath("/telemetry/{telemetry_server_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Delete a telemetry target
     * Telemetry target deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>501</b>
     * @param telemetryServerId The telemetryServerId parameter
     * @param targetUid The targetUid parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteTelemetryTarget(String telemetryServerId, String targetUid) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'telemetryServerId' is set
        if (telemetryServerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'telemetryServerId' when calling deleteTelemetryTarget");
        }
        
        // verify the required parameter 'targetUid' is set
        if (targetUid == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'targetUid' when calling deleteTelemetryTarget");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("telemetry_server_id", telemetryServerId);
        uriVariables.put("target_uid", targetUid);
        String path = UriComponentsBuilder.fromPath("/telemetry/{telemetry_server_id}/target/{target_uid}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get individual telemetry server information
     * Telemetry server retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param telemetryServerId The telemetryServerId parameter
     * @return PostTelemetryServer
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostTelemetryServer getTelemetryServer(String telemetryServerId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'telemetryServerId' is set
        if (telemetryServerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'telemetryServerId' when calling getTelemetryServer");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("telemetry_server_id", telemetryServerId);
        String path = UriComponentsBuilder.fromPath("/telemetry/{telemetry_server_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostTelemetryServer> returnType = new ParameterizedTypeReference<PostTelemetryServer>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get telemetry servers information
     * Telemetry servers retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param userId The userId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getTelemetryServers(String userId) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/telemetry").build().toUriString();
        
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
     * Get individual telemetry target information
     * Telemetry target retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * @param telemetryServerId The telemetryServerId parameter
     * @param targetUid The targetUid parameter
     * @return PutTelemetryTargetInput
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PutTelemetryTargetInput getTelemetryTarget(String telemetryServerId, String targetUid) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'telemetryServerId' is set
        if (telemetryServerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'telemetryServerId' when calling getTelemetryTarget");
        }
        
        // verify the required parameter 'targetUid' is set
        if (targetUid == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'targetUid' when calling getTelemetryTarget");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("telemetry_server_id", telemetryServerId);
        uriVariables.put("target_uid", targetUid);
        String path = UriComponentsBuilder.fromPath("/telemetry/{telemetry_server_id}/target/{target_uid}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PutTelemetryTargetInput> returnType = new ParameterizedTypeReference<PutTelemetryTargetInput>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get telemetry targets information
     * Telemetry targets retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * @param telemetryServerId The telemetryServerId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getTelemetryTargets(String telemetryServerId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'telemetryServerId' is set
        if (telemetryServerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'telemetryServerId' when calling getTelemetryTargets");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("telemetry_server_id", telemetryServerId);
        String path = UriComponentsBuilder.fromPath("/telemetry/{telemetry_server_id}/target").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List> returnType = new ParameterizedTypeReference<List>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Register a new telemetry server resource
     * Telemetry server registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param telemetryserverinput The telemetryserverinput parameter
     * @return PostTelemetryServer
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostTelemetryServer postTelemetryServer(TelemetryServerInput telemetryserverinput) throws RestClientException {
        Object postBody = telemetryserverinput;
        
        // verify the required parameter 'telemetryserverinput' is set
        if (telemetryserverinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'telemetryserverinput' when calling postTelemetryServer");
        }
        
        String path = UriComponentsBuilder.fromPath("/telemetry").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostTelemetryServer> returnType = new ParameterizedTypeReference<PostTelemetryServer>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Register a new telemetry target
     * Telemetry target registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param body The body parameter
     * @param telemetryServerId The telemetryServerId parameter
     * @return PostTelemetryTarget
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostTelemetryTarget postTelemetryTarget(Object body, String telemetryServerId) throws RestClientException {
        Object postBody = body;
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling postTelemetryTarget");
        }
        
        // verify the required parameter 'telemetryServerId' is set
        if (telemetryServerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'telemetryServerId' when calling postTelemetryTarget");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("telemetry_server_id", telemetryServerId);
        String path = UriComponentsBuilder.fromPath("/telemetry/{telemetry_server_id}/target").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostTelemetryTarget> returnType = new ParameterizedTypeReference<PostTelemetryTarget>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Modify telemetry target information
     * Telemetry target information modification
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * @param puttelemetrytargetinput The puttelemetrytargetinput parameter
     * @param telemetryServerId The telemetryServerId parameter
     * @param targetUid The targetUid parameter
     * @return PutTelemetryTargetInput
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PutTelemetryTargetInput putTelemetryTarget(PutTelemetryTargetInput puttelemetrytargetinput, String telemetryServerId, String targetUid) throws RestClientException {
        Object postBody = puttelemetrytargetinput;
        
        // verify the required parameter 'puttelemetrytargetinput' is set
        if (puttelemetrytargetinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'puttelemetrytargetinput' when calling putTelemetryTarget");
        }
        
        // verify the required parameter 'telemetryServerId' is set
        if (telemetryServerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'telemetryServerId' when calling putTelemetryTarget");
        }
        
        // verify the required parameter 'targetUid' is set
        if (targetUid == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'targetUid' when calling putTelemetryTarget");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("telemetry_server_id", telemetryServerId);
        uriVariables.put("target_uid", targetUid);
        String path = UriComponentsBuilder.fromPath("/telemetry/{telemetry_server_id}/target/{target_uid}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PutTelemetryTargetInput> returnType = new ParameterizedTypeReference<PutTelemetryTargetInput>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
}

