/*
 * nsmm
 * NorthBound Interface of Network Service Mesh Manager for 5GZORRO project. The NSMM provides API to manage resouces on the VIMs in order to establish secure intra-domain connections between services. In details: - a set of endpoints, called network-resources, is used to manage network resources on the selected vim to provide an external point of connectivity with a VPN server (wireguard). These network-resources considering OpenStack as a VIM include:   - networks and subnets   - routers and interfaces toward a floating network, to allow the creation of service-access-points   - configuration of the gateway VM included in the NSD, which provides the VPN service - a set of endpoints, called vpn-connections, is design to manage the VPN connections between remote peers.  The NSMM manages resources on a single domain and it is invoked by the slicer of the same domain to create all the network resources before the network service instantiation. After the creation of the network service, it is invoked to configure the gateway. Finally, the ISSM request to the slicer of each domain the creation of a secure channel which is forwarded to the NSMM that creates the VPN connection between the two gateways
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.api;

import com.google.gson.reflect.TypeToken;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.*;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.model.Connection;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.model.PostConnection;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VpnConnectionsApi {
    private ApiClient apiClient;

    public VpnConnectionsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public VpnConnectionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Build call for netResourcesIdGatewayConnectionsCidDelete
     * @param cid unique identifier of the VPN connection (required)
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
        
     */
    public com.squareup.okhttp.Call netResourcesIdGatewayConnectionsCidDeleteCall(Integer cid, Integer id, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/net-resources/{id}/gateway/connections/{cid}"
            .replaceAll("\\{" + "cid" + "\\}", apiClient.escapeString(cid.toString()))
            .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call netResourcesIdGatewayConnectionsCidDeleteValidateBeforeCall(Integer cid, Integer id, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {


        // verify the required parameter 'cid' is set
        if (cid == null) {
            throw new ApiException("Missing the required parameter 'cid' when calling netResourcesIdGatewayConnectionsCidDelete(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling netResourcesIdGatewayConnectionsCidDelete(Async)");
        }


        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsCidDeleteCall(cid, id, progressListener, progressRequestListener);
        return call;







    }

    /**
     * Removal of a VPN connection
     * It removes an active VPN connection of the gateway with {id} identified by the {cid}
     * @param cid unique identifier of the VPN connection (required)
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body

     */
    public void netResourcesIdGatewayConnectionsCidDelete(Integer cid, Integer id) throws ApiException {
        netResourcesIdGatewayConnectionsCidDeleteWithHttpInfo(cid, id);
    }

    /**
     * Removal of a VPN connection
     * It removes an active VPN connection of the gateway with {id} identified by the {cid}
     * @param cid unique identifier of the VPN connection (required)
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body

     */
    public ApiResponse<Void> netResourcesIdGatewayConnectionsCidDeleteWithHttpInfo(Integer cid, Integer id) throws ApiException {
        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsCidDeleteValidateBeforeCall(cid, id, null, null);
        return apiClient.execute(call);
    }

    /**
     * Removal of a VPN connection (asynchronously)
     * It removes an active VPN connection of the gateway with {id} identified by the {cid}
     * @param cid unique identifier of the VPN connection (required)
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object

     */
    public com.squareup.okhttp.Call netResourcesIdGatewayConnectionsCidDeleteAsync(Integer cid, Integer id, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsCidDeleteValidateBeforeCall(cid, id, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }

    /**
     * Build call for netResourcesIdGatewayConnectionsCidGet
     * @param cid unique identifier of the VPN connection (required)
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object

     */
    public com.squareup.okhttp.Call netResourcesIdGatewayConnectionsCidGetCall(Integer cid, Integer id, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/net-resources/{id}/gateway/connections/{cid}"
            .replaceAll("\\{" + "cid" + "\\}", apiClient.escapeString(cid.toString()))
            .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call netResourcesIdGatewayConnectionsCidGetValidateBeforeCall(Integer cid, Integer id, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {


        // verify the required parameter 'cid' is set
        if (cid == null) {
            throw new ApiException("Missing the required parameter 'cid' when calling netResourcesIdGatewayConnectionsCidGet(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling netResourcesIdGatewayConnectionsCidGet(Async)");
        }


        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsCidGetCall(cid, id, progressListener, progressRequestListener);
        return call;







    }

    /**
     * Retrieval of the information of a VPN connection
     * It retrieves the current information of the active VPN connection, belonging to the gateway with {id}, idenfied by the {cid} passed as path parameter
     * @param cid unique identifier of the VPN connection (required)
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @return Connection
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body

     */
    public Connection netResourcesIdGatewayConnectionsCidGet(Integer cid, Integer id) throws ApiException {
        ApiResponse<Connection> resp = netResourcesIdGatewayConnectionsCidGetWithHttpInfo(cid, id);
        return resp.getData();
    }

    /**
     * Retrieval of the information of a VPN connection
     * It retrieves the current information of the active VPN connection, belonging to the gateway with {id}, idenfied by the {cid} passed as path parameter
     * @param cid unique identifier of the VPN connection (required)
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @return ApiResponse&lt;Connection&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body

     */
    public ApiResponse<Connection> netResourcesIdGatewayConnectionsCidGetWithHttpInfo(Integer cid, Integer id) throws ApiException {
        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsCidGetValidateBeforeCall(cid, id, null, null);
        Type localVarReturnType = new TypeToken<Connection>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieval of the information of a VPN connection (asynchronously)
     * It retrieves the current information of the active VPN connection, belonging to the gateway with {id}, idenfied by the {cid} passed as path parameter
     * @param cid unique identifier of the VPN connection (required)
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object

     */
    public com.squareup.okhttp.Call netResourcesIdGatewayConnectionsCidGetAsync(Integer cid, Integer id, final ApiCallback<Connection> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsCidGetValidateBeforeCall(cid, id, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Connection>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }

    /**
     * Build call for netResourcesIdGatewayConnectionsGet
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object

     */
    public com.squareup.okhttp.Call netResourcesIdGatewayConnectionsGetCall(Integer id, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/net-resources/{id}/gateway/connections"
            .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call netResourcesIdGatewayConnectionsGetValidateBeforeCall(Integer id, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {


        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling netResourcesIdGatewayConnectionsGet(Async)");
        }


        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsGetCall(id, progressListener, progressRequestListener);
        return call;







    }

    /**
     * Retrieve all the active VPN connection of the gateway
     * It returns all the active VPN connections of the gateway identified by the set of resources with ID passed as path parameter
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @return List&lt;Connection&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body

     */
    public List<Connection> netResourcesIdGatewayConnectionsGet(Integer id) throws ApiException {
        ApiResponse<List<Connection>> resp = netResourcesIdGatewayConnectionsGetWithHttpInfo(id);
        return resp.getData();
    }

    /**
     * Retrieve all the active VPN connection of the gateway
     * It returns all the active VPN connections of the gateway identified by the set of resources with ID passed as path parameter
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @return ApiResponse&lt;List&lt;Connection&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body

     */
    public ApiResponse<List<Connection>> netResourcesIdGatewayConnectionsGetWithHttpInfo(Integer id) throws ApiException {
        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsGetValidateBeforeCall(id, null, null);
        Type localVarReturnType = new TypeToken<List<Connection>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieve all the active VPN connection of the gateway (asynchronously)
     * It returns all the active VPN connections of the gateway identified by the set of resources with ID passed as path parameter
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object

     */
    public com.squareup.okhttp.Call netResourcesIdGatewayConnectionsGetAsync(Integer id, final ApiCallback<List<Connection>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsGetValidateBeforeCall(id, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<Connection>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }

    /**
     * Build call for netResourcesIdGatewayConnectionsPost
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @param body  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object

     */
    public com.squareup.okhttp.Call netResourcesIdGatewayConnectionsPostCall(Integer id, PostConnection body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/net-resources/{id}/gateway/connections"
            .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call netResourcesIdGatewayConnectionsPostValidateBeforeCall(Integer id, PostConnection body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling netResourcesIdGatewayConnectionsPost(Async)");
        }
        
        
        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsPostCall(id, body, progressListener, progressRequestListener);
        return call;

        
        
        
        
        
        
    }

    /**
     * Creation of a new VPN connection
     * It request the creation of a new VPN connection for the gateway identified by the set of resources with ID passed as path parameter
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @param body  (optional)
     * @return Connection
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
        
     */
    public Connection netResourcesIdGatewayConnectionsPost(Integer id, PostConnection body) throws ApiException {
        ApiResponse<Connection> resp = netResourcesIdGatewayConnectionsPostWithHttpInfo(id, body);
        return resp.getData();
    }

    /**
     * Creation of a new VPN connection
     * It request the creation of a new VPN connection for the gateway identified by the set of resources with ID passed as path parameter
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @param body  (optional)
     * @return ApiResponse&lt;Connection&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
        
     */
    public ApiResponse<Connection> netResourcesIdGatewayConnectionsPostWithHttpInfo(Integer id, PostConnection body) throws ApiException {
        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsPostValidateBeforeCall(id, body, null, null);
        Type localVarReturnType = new TypeToken<Connection>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Creation of a new VPN connection (asynchronously)
     * It request the creation of a new VPN connection for the gateway identified by the set of resources with ID passed as path parameter
     * @param id Unique identifier of the network-resource set request for a slice (required)
     * @param body  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
        
     */
    public com.squareup.okhttp.Call netResourcesIdGatewayConnectionsPostAsync(Integer id, PostConnection body, final ApiCallback<Connection> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = netResourcesIdGatewayConnectionsPostValidateBeforeCall(id, body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Connection>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    
}
