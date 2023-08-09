package it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.api.vnf;

import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.CreateVnfPkgInfoRequest;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.UploadVnfPackageFromUriRequest;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.VnfPkgInfo;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.VnfPkgInfoModifications;
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.Catalogue;
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.invoker.vnf.ApiClient;
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

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-21T15:10:42.557+01:00")
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

    public VnfPkgInfo createVNFPkgInfo(CreateVnfPkgInfoRequest body) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createVNFPkgInfo");
        }

        String path = UriComponentsBuilder.fromPath("/vnfpkgm/v1/vnf_packages").build().toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {
                "application/json", "application/yaml"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {
                "application/json"
        };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<VnfPkgInfo> returnType = new ParameterizedTypeReference<VnfPkgInfo>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    public void deleteVNFPkgInfo(String vnfPkgId) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'vnfPkgId' is set
        if (vnfPkgId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'vnfPkgId' when calling deleteVNFPkgInfo");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("vnfPkgId", vnfPkgId);
        String path = UriComponentsBuilder.fromPath("/vnfpkgm/v1/vnf_packages/{vnfPkgId}").buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {
                "application/json", "application/yaml"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {
        };
        apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    public Object getVNFD(String vnfPkgId) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'vnfPkgId' is set
        if (vnfPkgId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'vnfPkgId' when calling getVNFD");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("vnfPkgId", vnfPkgId);
        String path = UriComponentsBuilder.fromPath("/vnfpkgm/v1/vnf_packages/{vnfPkgId}/vnfd").buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {
                "application/json", "application/yaml", "text/plain", "application/zip"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    public Object getVNFPkg(String vnfPkgId, String range) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'vnfPkgId' is set
        if (vnfPkgId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'vnfPkgId' when calling getVNFPkg");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("vnfPkgId", vnfPkgId);
        String path = UriComponentsBuilder.fromPath("/vnfpkgm/v1/vnf_packages/{vnfPkgId}/package_content").buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (range != null)
            headerParams.add("Range", apiClient.parameterToString(range));

        final String[] accepts = {"multipart/form-data", "application/zip", "application/json"};
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Resource> returnType = new ParameterizedTypeReference<Resource>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    public List<VnfPkgInfo> getVNFPkgsInfo() throws RestClientException {
        Object postBody = null;

        String path = UriComponentsBuilder.fromPath("/vnfpkgm/v1/vnf_packages").build().toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {
                "application/json", "application/yaml"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<List<VnfPkgInfo>> returnType = new ParameterizedTypeReference<List<VnfPkgInfo>>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    public Object queryVNFPkgArtifact(String vnfPkgId, String artifactPath, String range) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'vnfPkgId' is set
        if (vnfPkgId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'vnfPkgId' when calling queryVNFPkgArtifact");
        }

        // verify the required parameter 'artifactPath' is set
        if (artifactPath == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'artifactPath' when calling queryVNFPkgArtifact");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("vnfPkgId", vnfPkgId);
        uriVariables.put("artifactPath", artifactPath);
        String path = UriComponentsBuilder.fromPath("/vnfpkgm/v1/vnf_packages/{vnfPkgId}/artifacts/{artifactPath}").buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (range != null)
            headerParams.add("Range", apiClient.parameterToString(range));

        final String[] accepts = {
                "application/octet-stream"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    public VnfPkgInfo queryVNFPkgInfo(String vnfPkgId) throws RestClientException {
        Object postBody = null;

        // verify the required parameter 'vnfPkgId' is set
        if (vnfPkgId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'vnfPkgId' when calling queryVNFPkgInfo");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("vnfPkgId", vnfPkgId);
        String path = UriComponentsBuilder.fromPath("/vnfpkgm/v1/vnf_packages/{vnfPkgId}").buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {
                "application/json", "application/yaml"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<VnfPkgInfo> returnType = new ParameterizedTypeReference<VnfPkgInfo>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    public VnfPkgInfoModifications updateVNFPkgInfo(String vnfPkgId, VnfPkgInfoModifications body) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'vnfPkgId' is set
        if (vnfPkgId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'vnfPkgId' when calling updateVNFPkgInfo");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateVNFPkgInfo");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("vnfPkgId", vnfPkgId);
        String path = UriComponentsBuilder.fromPath("/vnfpkgm/v1/vnf_packages/{vnfPkgId}").buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {
                "application/json", "application/yaml"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {
                "application/json"
        };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<VnfPkgInfoModifications> returnType = new ParameterizedTypeReference<VnfPkgInfoModifications>() {
        };
        return apiClient.invokeAPI(path, HttpMethod.PATCH, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    public Object uploadVNFPkg(String vnfPkgId, Object body, String contentType) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'vnfPkgId' is set
        if (vnfPkgId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'vnfPkgId' when calling uploadVNFPkg");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling uploadVNFPkg");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("vnfPkgId", vnfPkgId);
        String path = UriComponentsBuilder.fromPath("/vnfpkgm/v1/vnf_packages/{vnfPkgId}/package_content").buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (contentType != null)
            headerParams.add("Content-Type", apiClient.parameterToString(contentType));

        final String[] accepts = {
                "application/json", "application/yaml"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {
                "application/zip"
        };
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

    public void uploadVNFPkgFromURI(String vnfPkgId, UploadVnfPackageFromUriRequest body) throws RestClientException {
        Object postBody = body;

        // verify the required parameter 'vnfPkgId' is set
        if (vnfPkgId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'vnfPkgId' when calling uploadVNFPkgFromURI");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling uploadVNFPkgFromURI");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("vnfPkgId", vnfPkgId);
        String path = UriComponentsBuilder.fromPath("/vnfpkgm/v1/vnf_packages/{vnfPkgId}/package_content/upload_from_uri").buildAndExpand(uriVariables).toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {
                "application/json", "application/yaml"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {
                "application/json"
        };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[]{};

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {
        };
        apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
