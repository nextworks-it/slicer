package it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.api.nsd;

import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.*;
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.Catalogue;
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.invoker.nsd.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-21T15:01:43.121+01:00")
public class DefaultApi {

    private static final Logger log = LoggerFactory.getLogger(DefaultApi.class);

    private ApiClient apiClient;

    public DefaultApi(Catalogue catalogue) {
        this(new ApiClient(catalogue));
    }

    public DefaultApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public NsdInfo createNsdInfo(CreateNsdInfoRequest body) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'body' when calling createNsdInfo");
        }

        String path = UriComponentsBuilder.fromPath("/nsd/v1/ns_descriptors").build().toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {"application/json"};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<NsdInfo> returnType = new ParameterizedTypeReference<NsdInfo>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public PnfdInfo createPNFDInfo(CreatePnfdInfoRequest body) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'body' when calling createPNFDInfo");
        }

        String path = UriComponentsBuilder.fromPath("/nsd/v1/pnf_descriptors").build().toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {"application/json"};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<PnfdInfo> returnType = new ParameterizedTypeReference<PnfdInfo>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public void deleteNSDInfo(String nsdInfoId) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'nsdInfoId' is set
        if (nsdInfoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'nsdInfoId' when calling deleteNSDInfo");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("nsdInfoId", nsdInfoId);
        String path = UriComponentsBuilder.fromPath("/nsd/v1/ns_descriptors/{nsdInfoId}").buildAndExpand(uriVariables)
                .toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {
        };
        apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public void deletePNFDInfo(String pnfdInfoId) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'pnfdInfoId' is set
        if (pnfdInfoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'pnfdInfoId' when calling deletePNFDInfo");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("pnfdInfoId", pnfdInfoId);
        String path = UriComponentsBuilder.fromPath("/nsd/v1/pnf_descriptors/{pnfdInfoId}").buildAndExpand(uriVariables)
                .toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {
        };
        apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public Object getNSD(String nsdInfoId, String range) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'nsdInfoId' is set
        if (nsdInfoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'nsdInfoId' when calling getNSD");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("nsdInfoId", nsdInfoId);
        String path = UriComponentsBuilder.fromPath("/nsd/v1/ns_descriptors/{nsdInfoId}/nsd_content")
                .buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (range != null)
            headerParams.add("Range", apiClient.parameterToString(range));

        final String[] accepts = {"application/json", "application/yaml", "application/zip"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Resource> returnType = new ParameterizedTypeReference<Resource>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public NsdInfo getNSDInfo(String nsdInfoId) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'nsdInfoId' is set
        if (nsdInfoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'nsdInfoId' when calling getNSDInfo");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("nsdInfoId", nsdInfoId);
        String path = UriComponentsBuilder.fromPath("/nsd/v1/ns_descriptors/{nsdInfoId}").buildAndExpand(uriVariables)
                .toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<NsdInfo> returnType = new ParameterizedTypeReference<NsdInfo>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public List<NsdInfo> getNSDsInfo() throws RestClientException {
        Object postBody = null;

        String path = UriComponentsBuilder.fromPath("/nsd/v1/ns_descriptors").build().toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<List<NsdInfo>> returnType = new ParameterizedTypeReference<List<NsdInfo>>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public Object getPNFD(String pnfdInfoId) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'pnfdInfoId' is set
        if (pnfdInfoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'pnfdInfoId' when calling getPNFD");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("pnfdInfoId", pnfdInfoId);
        String path = UriComponentsBuilder.fromPath("/nsd/v1/pnf_descriptors/{pnfdInfoId}/pnfd_content")
                .buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"text/plain", "application/yaml", "application/json"}; //{"application/json", "application/yaml", "application/zip"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Resource> returnType = new ParameterizedTypeReference<Resource>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public PnfdInfo getPNFDInfo(String pnfdInfoId) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'pnfdInfoId' is set
        if (pnfdInfoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'pnfdInfoId' when calling getPNFDInfo");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("pnfdInfoId", pnfdInfoId);
        String path = UriComponentsBuilder.fromPath("/nsd/v1/pnf_descriptors/{pnfdInfoId}").buildAndExpand(uriVariables)
                .toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<PnfdInfo> returnType = new ParameterizedTypeReference<PnfdInfo>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public List<PnfdInfo> getPNFDsInfo() throws RestClientException {
        Object postBody = null;

        String path = UriComponentsBuilder.fromPath("/nsd/v1/pnf_descriptors").build().toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<List<PnfdInfo>> returnType = new ParameterizedTypeReference<List<PnfdInfo>>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public NsdInfoModifications updateNSDInfo(String nsdInfoId, NsdInfoModifications body) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'nsdInfoId' is set
        if (nsdInfoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'nsdInfoId' when calling updateNSDInfo");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'body' when calling updateNSDInfo");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("nsdInfoId", nsdInfoId);
        String path = UriComponentsBuilder.fromPath("/nsd/v1/ns_descriptors/{nsdInfoId}").buildAndExpand(uriVariables)
                .toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {"application/json"};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<NsdInfoModifications> returnType = new ParameterizedTypeReference<NsdInfoModifications>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.PATCH, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public PnfdInfoModifications updatePNFDInfo(String pnfdInfoId, PnfdInfoModifications body)
            throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'pnfdInfoId' is set
        if (pnfdInfoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'pnfdInfoId' when calling updatePNFDInfo");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'body' when calling updatePNFDInfo");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("pnfdInfoId", pnfdInfoId);
        String path = UriComponentsBuilder.fromPath("/nsd/v1/pnf_descriptors/{pnfdInfoId}").buildAndExpand(uriVariables)
                .toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {"application/json"};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<PnfdInfoModifications> returnType = new ParameterizedTypeReference<PnfdInfoModifications>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.PATCH, queryParams, postBody, headerParams, formParams, accept,
                contentType, authNames, returnType);
    }

    public Object uploadNSD(String nsdInfoId, Object body, String contentType) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'nsdInfoId' is set
        if (nsdInfoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'nsdInfoId' when calling uploadNSD");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'body' when calling uploadNSD");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("nsdInfoId", nsdInfoId);
        String path = UriComponentsBuilder.fromPath("/nsd/v1/ns_descriptors/{nsdInfoId}/nsd_content")
                .buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (contentType != null)
            headerParams.add("Content-Type", apiClient.parameterToString(contentType));

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {"application/json", "application/yaml", "application/zip"};
        final MediaType finalContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {
        };
        if (contentType.equalsIgnoreCase("multipart/form-data")) {
            log.debug("executing modified invoker");
            return apiClient.invokeApi(path, HttpMethod.PUT, body);
        } else {
            return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept,
                    finalContentType, authNames, returnType);
        }
    }

    public Object uploadPNFD(String pnfdInfoId, Object body, String contentType) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'pnfdInfoId' is set
        if (pnfdInfoId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'pnfdInfoId' when calling uploadPNFD");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'body' when calling uploadPNFD");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("pnfdInfoId", pnfdInfoId);
        String path = UriComponentsBuilder.fromPath("/nsd/v1/pnf_descriptors/{pnfdInfoId}/pnfd_content")
                .buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (contentType != null)
            headerParams.add("Content-Type", apiClient.parameterToString(contentType));

        final String[] accepts = {"application/json", "application/yaml"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {"application/json", "application/yaml", "application/zip"};
        final MediaType finalContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {
        };
        if (contentType.equalsIgnoreCase("multipart/form-data")) {
            log.debug("executing modified invoker");
            return apiClient.invokeApi(path, HttpMethod.PUT, body);
        } else {
            return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept,
                    finalContentType, authNames, returnType);
        }
    }
}
