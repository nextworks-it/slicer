package io.swagger.client.slice_manager.v2.api;

import io.swagger.client.slice_manager.v2.invoker.ApiClient;

import io.swagger.client.slice_manager.v2.model.BoxCellInput;
import io.swagger.client.slice_manager.v2.model.GetRANInfrastructureTopo;
import io.swagger.client.slice_manager.v2.model.PostBoxCell;
import io.swagger.client.slice_manager.v2.model.PostRANInfrastructure;
import io.swagger.client.slice_manager.v2.model.PutBoxCellConfigInput;
import io.swagger.client.slice_manager.v2.model.PutBoxConfigInput;
import io.swagger.client.slice_manager.v2.model.PutLTEConfigurationInput;
import io.swagger.client.slice_manager.v2.model.PutRFPortConfigInput;
import io.swagger.client.slice_manager.v2.model.PutWifiConfigurationInput;
import io.swagger.client.slice_manager.v2.model.RANInfrastructureInput;


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
@Component("io.swagger.client.slice_manager.v2.api.RadioAccessNetworkResourceApi")

public class RadioAccessNetworkResourceApi {
    private ApiClient apiClient;

    public RadioAccessNetworkResourceApi() {
        this(new ApiClient());
    }

    @Autowired
    public RadioAccessNetworkResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Delete a cell
     * Cell deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>501</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param cellId The cellId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteCell(String ranInfrastructureId, String cellId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling deleteCell");
        }
        
        // verify the required parameter 'cellId' is set
        if (cellId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'cellId' when calling deleteCell");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("cell_id", cellId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Delete a cell relationship
     * Cell relationship deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>501</b>
     * <p><b>503</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param cellId The cellId parameter
     * @param nsaCellId The nsaCellId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteCellRelationship(String ranInfrastructureId, String cellId, String nsaCellId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling deleteCellRelationship");
        }
        
        // verify the required parameter 'cellId' is set
        if (cellId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'cellId' when calling deleteCellRelationship");
        }
        
        // verify the required parameter 'nsaCellId' is set
        if (nsaCellId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'nsaCellId' when calling deleteCellRelationship");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("cell_id", cellId);
        uriVariables.put("nsa_cell_id", nsaCellId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id}/nsa_relationship/{nsa_cell_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Delete a RAN infrastructure
     * RAN Infrastructure deletion method
     * <p><b>204</b> - Request succeeded
     * <p><b>404</b>
     * <p><b>501</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void deleteRANInfrastructure(String ranInfrastructureId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling deleteRANInfrastructure");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get RAN infrastructures configured radio interfaces
     * RAN Infrastructure configured radio interfaces retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @return GetRANInfrastructureTopo
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public GetRANInfrastructureTopo getConfiguredRadioPhys(String ranInfrastructureId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling getConfiguredRadioPhys");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/configured_ran_topology").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GetRANInfrastructureTopo> returnType = new ParameterizedTypeReference<GetRANInfrastructureTopo>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get individual RAN infrastructure information
     * RAN Infrastructure retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @return PostRANInfrastructure
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostRANInfrastructure getRANInfrastructure(String ranInfrastructureId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling getRANInfrastructure");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostRANInfrastructure> returnType = new ParameterizedTypeReference<PostRANInfrastructure>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get RAN infrastructures information
     * RAN Infrastructure retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param userId The userId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getRANInfrastructures(String userId) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure").build().toUriString();
        
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
     * Get individual RAN infrastructure topology information
     * RAN Infrastructure topology retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @return GetRANInfrastructureTopo
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public GetRANInfrastructureTopo getRANTopology(String ranInfrastructureId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling getRANTopology");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/ran_topology").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GetRANInfrastructureTopo> returnType = new ParameterizedTypeReference<GetRANInfrastructureTopo>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Get RAN boxes information
     * RAN boxes retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getRANboxes(String ranInfrastructureId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling getRANboxes");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/box").buildAndExpand(uriVariables).toUriString();
        
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
     * Get RF ports information
     * RF ports retrieval method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param boxId The boxId parameter
     * @return List
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public List getRFports(String ranInfrastructureId, String boxId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling getRFports");
        }
        
        // verify the required parameter 'boxId' is set
        if (boxId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'boxId' when calling getRFports");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("box_id", boxId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/box/{box_id}/rf_port").buildAndExpand(uriVariables).toUriString();
        
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
     * Creates a new cell
     * Cell creation method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param boxcellinput The boxcellinput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param boxId The boxId parameter
     * @return PostBoxCell
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostBoxCell postBoxCell(BoxCellInput boxcellinput, String ranInfrastructureId, String boxId) throws RestClientException {
        Object postBody = boxcellinput;
        
        // verify the required parameter 'boxcellinput' is set
        if (boxcellinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'boxcellinput' when calling postBoxCell");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling postBoxCell");
        }
        
        // verify the required parameter 'boxId' is set
        if (boxId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'boxId' when calling postBoxCell");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("box_id", boxId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/ran_topology/{box_id}/cell").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostBoxCell> returnType = new ParameterizedTypeReference<PostBoxCell>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Register a new RAN infrastructure resource
     * RAN Infrastructure registration method
     * <p><b>200</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param raninfrastructureinput The raninfrastructureinput parameter
     * @return PostRANInfrastructure
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public PostRANInfrastructure postRANInfrastructure(RANInfrastructureInput raninfrastructureinput) throws RestClientException {
        Object postBody = raninfrastructureinput;
        
        // verify the required parameter 'raninfrastructureinput' is set
        if (raninfrastructureinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'raninfrastructureinput' when calling postRANInfrastructure");
        }
        
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PostRANInfrastructure> returnType = new ParameterizedTypeReference<PostRANInfrastructure>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Set cell config
     * Cell config
     * <p><b>204</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param putboxcellconfiginput The putboxcellconfiginput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param cellId The cellId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void putBoxCellConfig(PutBoxCellConfigInput putboxcellconfiginput, String ranInfrastructureId, String cellId) throws RestClientException {
        Object postBody = putboxcellconfiginput;
        
        // verify the required parameter 'putboxcellconfiginput' is set
        if (putboxcellconfiginput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'putboxcellconfiginput' when calling putBoxCellConfig");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling putBoxCellConfig");
        }
        
        // verify the required parameter 'cellId' is set
        if (cellId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'cellId' when calling putBoxCellConfig");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("cell_id", cellId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Set cell relationship
     * Cell relationship config
     * <p><b>204</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param cellId The cellId parameter
     * @param nsaCellId The nsaCellId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void putBoxCellRelationship(String ranInfrastructureId, String cellId, String nsaCellId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling putBoxCellRelationship");
        }
        
        // verify the required parameter 'cellId' is set
        if (cellId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'cellId' when calling putBoxCellRelationship");
        }
        
        // verify the required parameter 'nsaCellId' is set
        if (nsaCellId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'nsaCellId' when calling putBoxCellRelationship");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("cell_id", cellId);
        uriVariables.put("nsa_cell_id", nsaCellId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id}/nsa_relationship/{nsa_cell_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Set cellular box config
     * Cellular Box Config
     * <p><b>204</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>503</b>
     * @param putboxconfiginput The putboxconfiginput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param boxId The boxId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void putBoxConfig(PutBoxConfigInput putboxconfiginput, String ranInfrastructureId, String boxId) throws RestClientException {
        Object postBody = putboxconfiginput;
        
        // verify the required parameter 'putboxconfiginput' is set
        if (putboxconfiginput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'putboxconfiginput' when calling putBoxConfig");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling putBoxConfig");
        }
        
        // verify the required parameter 'boxId' is set
        if (boxId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'boxId' when calling putBoxConfig");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("box_id", boxId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/box/{box_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Set physical interface type
     * Physical Interface Type
     * <p><b>204</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param interfaceId The interfaceId parameter
     * @param type The type parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void putInterfaceType(String ranInfrastructureId, String interfaceId, String type) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling putInterfaceType");
        }
        
        // verify the required parameter 'interfaceId' is set
        if (interfaceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'interfaceId' when calling putInterfaceType");
        }
        
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'type' when calling putInterfaceType");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("interface_id", interfaceId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/ran_topology/{interface_id}/type").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "type", type));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    
    /**
     * Set physical interface LTE configuration
     * Physical Interface LTE configuration
     * <p><b>204</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param putlteconfigurationinput The putlteconfigurationinput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param interfaceId The interfaceId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void putLTEConfiguration(PutLTEConfigurationInput putlteconfigurationinput, String ranInfrastructureId, String interfaceId) throws RestClientException {
        Object postBody = putlteconfigurationinput;
        
        // verify the required parameter 'putlteconfigurationinput' is set
        if (putlteconfigurationinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'putlteconfigurationinput' when calling putLTEConfiguration");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling putLTEConfiguration");
        }
        
        // verify the required parameter 'interfaceId' is set
        if (interfaceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'interfaceId' when calling putLTEConfiguration");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("interface_id", interfaceId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/ran_topology/{interface_id}/lte_config").buildAndExpand(uriVariables).toUriString();
        
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
     * Set RF port config
     * RF Port Config
     * <p><b>204</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * <p><b>422</b>
     * <p><b>503</b>
     * @param putrfportconfiginput The putrfportconfiginput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param boxId The boxId parameter
     * @param rfPortId The rfPortId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void putRFPortConfig(PutRFPortConfigInput putrfportconfiginput, String ranInfrastructureId, String boxId, String rfPortId) throws RestClientException {
        Object postBody = putrfportconfiginput;
        
        // verify the required parameter 'putrfportconfiginput' is set
        if (putrfportconfiginput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'putrfportconfiginput' when calling putRFPortConfig");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling putRFPortConfig");
        }
        
        // verify the required parameter 'boxId' is set
        if (boxId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'boxId' when calling putRFPortConfig");
        }
        
        // verify the required parameter 'rfPortId' is set
        if (rfPortId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'rfPortId' when calling putRFPortConfig");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("box_id", boxId);
        uriVariables.put("rf_port_id", rfPortId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/box/{box_id}/rf_port/{rf_port_id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Set physical interface WiFi configuration
     * Physical Interface WiFi configuration
     * <p><b>204</b> - Request succeeded
     * <p><b>400</b>
     * <p><b>404</b>
     * <p><b>409</b>
     * @param putwificonfigurationinput The putwificonfigurationinput parameter
     * @param ranInfrastructureId The ranInfrastructureId parameter
     * @param interfaceId The interfaceId parameter
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public void putWirelessConfiguration(PutWifiConfigurationInput putwificonfigurationinput, String ranInfrastructureId, String interfaceId) throws RestClientException {
        Object postBody = putwificonfigurationinput;
        
        // verify the required parameter 'putwificonfigurationinput' is set
        if (putwificonfigurationinput == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'putwificonfigurationinput' when calling putWirelessConfiguration");
        }
        
        // verify the required parameter 'ranInfrastructureId' is set
        if (ranInfrastructureId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ranInfrastructureId' when calling putWirelessConfiguration");
        }
        
        // verify the required parameter 'interfaceId' is set
        if (interfaceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'interfaceId' when calling putWirelessConfiguration");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ran_infrastructure_id", ranInfrastructureId);
        uriVariables.put("interface_id", interfaceId);
        String path = UriComponentsBuilder.fromPath("/ran_infrastructure/{ran_infrastructure_id}/ran_topology/{interface_id}/wifi_config").buildAndExpand(uriVariables).toUriString();
        
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
    
}

