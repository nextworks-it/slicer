package it.nextworks.nfvmano.sbi.nfvo.osm.rest.api;

import com.google.gson.reflect.TypeToken;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.client.*;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.model.ArrayOfNstInfo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetSliceTemplatesApi {
    private ApiClient apiClient;

    public NetSliceTemplatesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public NetSliceTemplatesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResponse<ArrayOfNstInfo> getNSTsWithHttpInfo() throws ApiException {
        com.squareup.okhttp.Call call = getNSTsValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeToken<ArrayOfNstInfo>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    public ArrayOfNstInfo getNSTs() throws ApiException {
        ApiResponse<ArrayOfNstInfo> resp = getNSTsWithHttpInfo();
        return resp.getData();
    }

    public com.squareup.okhttp.Call getNSTsCall(final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/nst/v1/netslice_templates";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json", "application/yaml"
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

        String[] localVarAuthNames = new String[] { "bearerAuth" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    private com.squareup.okhttp.Call getNSTsValidateBeforeCall(final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        com.squareup.okhttp.Call call = getNSTsCall(progressListener, progressRequestListener);
        return call;





    }


}
