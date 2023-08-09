package it.nextworks.nfvmano.libs.osmr4PlusClient.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class OSMHttpConnection {

    /**
     * OSM HTTP methods
     */
    public enum OSMHttpMethod {

        GET("GET"), POST("POST"), DELETE("DELETE"), PUT("PUT");

        private String method;

        OSMHttpMethod(String method) { this.method = method; }

        @Override
        public String toString() { return method; }
    }

    /**
     * Establishes an HTTP connection
     * @param url Connection URL
     * @param method HTTP request method
     * @param headers HTTP headers
     * @param body HTTP body
     * @return HttpResponse message
     */
    public static OSMHttpResponse establishOSMHttpConnection(String url, OSMHttpMethod method, List<Header> headers, Object body, String storagePath) {

        URL url_;
        HttpURLConnection conn = null;
        OSMHttpResponse response;
        ObjectMapper mapper = new ObjectMapper();

        try {
            url_ = new URL(url);

            conn = (HttpURLConnection)url_.openConnection();
            conn.setRequestMethod(method.toString());
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Set the headers
            for (Header header : headers)
                conn.setRequestProperty(header.getName(), header.getValue());

            conn.connect();

            if(body != null) {
                OutputStream out = conn.getOutputStream();
                if(body instanceof File) {
                    File file = (File) body;
                    // Write the actual file contents
                    FileInputStream inputStreamToLogFile = new FileInputStream(file);
                    int bytesRead;
                    byte[] dataBuffer = new byte[1024];
                    while ((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
                        out.write(dataBuffer, 0, bytesRead);
                    }
                }
                else
                    mapper.writeValue(out, body);

                out.flush();
                // Close the streams
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        response = OSMHttpResponse.getResponseFromOSMHttpConnection(conn, storagePath);
        return response;
    }

    /**
     * Disables SSL verification
     */
    public static void disableSslVerification() {

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
    }
}
