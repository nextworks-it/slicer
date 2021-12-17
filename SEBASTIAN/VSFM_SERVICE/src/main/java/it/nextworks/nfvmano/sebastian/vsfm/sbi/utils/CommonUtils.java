/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.vsfm.sbi.utils;

import it.nextworks.nfvmano.catalogue.template.elements.NsTemplateInfo;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class
 *
 * @author nextworks
 */
@Service
public class CommonUtils {

    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    @Autowired
    private NsTemplateCatalogueService nsTemplateCatalogueService;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate = getRestTemplateWithSSLDisabled();
    }

    public NsTemplateInfo getNsTemplateInfoFromCatalogue(String nstId) throws NotExistingEntityException {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("NST_ID", nstId);
        Filter filter = new Filter(parameters);
        GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
        try {
            List<NsTemplateInfo> nstInfos = nsTemplateCatalogueService.queryNsTemplate(query).getNsTemplateInfos();
            if (nstInfos.size() != 1)
                throw new NotExistingEntityException("Unable to find unique NST with ID " + nstId + " in catalogue");
            return nstInfos.get(0);
        } catch (Exception e) {
            log.error("Unable to retrieve Network Slice Template from Catalogue: " + e.getMessage());
            throw new NotExistingEntityException("Unable to retrieve Network Slice Template from Catalogue: " + e.getMessage());
        }
    }

    private RestTemplate getRestTemplateWithSSLDisabled() {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
        SSLContext sslContext;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
            log.error("Cannot disable SSL verification");
            return null;
        }
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }

    public String manageHTTPResponse(ResponseEntity<?> httpResponse, String errMsg, String okCodeMsg, HttpStatus httpStatusExpected) {
        if (httpResponse == null) {
            log.error(errMsg);
            return null;
        }
        if (httpResponse.getStatusCode().equals(httpStatusExpected))
            log.debug(okCodeMsg);
        else {
            log.error(errMsg);
            return null;
        }
        log.debug("Response code: " + httpResponse.getStatusCode().toString());
        if (httpResponse.getBody() == null)
            return "No body in the response";
        log.debug(("Body response: " + httpResponse.getBody().toString()));
        return httpResponse.getBody().toString();
    }

    public ResponseEntity<String> performHTTPRequest(Object request, String url, HttpMethod httpMethod, Map<String, String> additionalHeaders, String token) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        header.add("Accept", "application/json");
        if(additionalHeaders != null)
            additionalHeaders.forEach((key, value) -> header.add(key, value));
        if (token != null)
            header.add("Authorization", "Bearer " + token);
        HttpEntity<?> httpEntity = new HttpEntity<>(request, header);
        ResponseEntity<String> httpResponse = null;
        try {
            log.debug("URL performing the request to: " + url);
            httpResponse = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            HttpStatus code = httpResponse.getStatusCode();
            log.debug("Response code: " + code.toString());
        } catch (RestClientException e) {
            log.error("Message received: " + e.getMessage());
        }
        return httpResponse;//In case of error httpResponse is null
    }
}
