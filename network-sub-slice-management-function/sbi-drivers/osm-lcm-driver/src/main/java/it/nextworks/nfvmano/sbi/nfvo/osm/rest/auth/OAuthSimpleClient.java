package it.nextworks.nfvmano.sbi.nfvo.osm.rest.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class OAuthSimpleClient {

    private String basePath;
    private String username;
    private String password;
    private String project;

    public OAuthSimpleClient(String basePath, String username, String password, String project){

        this.basePath= basePath;
        this.username=username;
        this.password=password;
        this.project = project;
    }



    public String getToken() throws FailedOperationException {

        OkHttpClient client =getUnsafeOkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("username", username);
        bodyData.put("password", password);
        bodyData.put("project-id", project);
        ObjectMapper mapper = new ObjectMapper();
        RequestBody body = null;
        try {
            body = RequestBody.create(JSON, mapper.writeValueAsString(bodyData));
        } catch (JsonProcessingException e) {
            throw new FailedOperationException(e.getMessage());
        }

        Request request = new Request.Builder()
                .url(basePath)
                .post(body)
                .header("Accept", "application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            ResponseBody respBody = response.body();
            String json = respBody.string();
            JsonNode map = mapper.readTree(json);
            String token = map.get("id").textValue();
            return token;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FailedOperationException(e.getMessage());
        }
    }

    public static OkHttpClient getUnsafeOkHttpClient() throws FailedOperationException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        return;
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        return;
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session)
            {
                boolean verify = false;
                if (urlHostName.equalsIgnoreCase(session.getPeerHost()))
                {
                    verify = true;
                }
                return verify;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
        OkHttpClient client = new OkHttpClient()
                .setSslSocketFactory(sc.getSocketFactory())
                .setHostnameVerifier(hv);
        return client;
    }
}
