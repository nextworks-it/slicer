package it.nextworks.nfvmano.sebastian.nsmf.nbi;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.RemoteTenantInfo;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.common.Authenticator;
import org.junit.internal.runners.statements.Fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class VsmfNstAdvertiserRestClient {

    private RestTemplate restTemplate;
    private final String VSMF_NST_ADVERTISMENT_URL;
    private String cookies;
    private Authenticator authenticator;
    private String tenantId;

    private static final Logger log = LoggerFactory.getLogger(VsmfNstAdvertiserRestClient.class);

    public VsmfNstAdvertiserRestClient(String baseUrl, AdminService adminService, String tenantId) {
        this.VSMF_NST_ADVERTISMENT_URL = baseUrl + "/vs/catalogue/nstAdvertising";
        this.restTemplate = new RestTemplate();
        this.authenticator = new Authenticator(baseUrl,adminService);
        this.tenantId=tenantId;
    }

    private void performAuth() throws FailedOperationException {
        if(tenantId==null){
            log.error("No tenant found to make request to "+VSMF_NST_ADVERTISMENT_URL);
            throw new FailedOperationException("No tenant found to make request to "+VSMF_NST_ADVERTISMENT_URL);
        }
        authenticator.authenticate(tenantId);
        if(authenticator.isAuthenticated()){
            cookies=authenticator.getCookie();
        }
    }

    public void advertiseNst(NST nst) throws FailedOperationException,RestClientException {
        performAuth();
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        if(this.cookies!=null){
            header.add("Cookie", this.cookies);
        }
        HttpEntity<?> httpEntity = new HttpEntity<>(nst, header);
        log.debug("Sending HTTP message to advertise NST.");
        ResponseEntity<String> httpResponse =
                    restTemplate.exchange(VSMF_NST_ADVERTISMENT_URL, HttpMethod.POST, httpEntity, String.class);

            log.debug("Response code: " + httpResponse.getStatusCode().toString());
            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.CREATED)) {
                log.debug("NST correctly advertised.");
            } else {
                log.debug("Error while advertising NST");
                throw new FailedOperationException("Error while advertising NST.");
            }

    }


    public void removeNstAdvertised(String nstId) throws FailedOperationException,RestClientException {
        performAuth();
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        if(this.cookies!=null){
            header.add("Cookie", this.cookies);
        }
        HttpEntity<?> httpEntity = new HttpEntity<>(nstId, header);

         log.debug("Sending HTTP message to remove NST previously advertised.");
         ResponseEntity<String> httpResponse =
                    restTemplate.exchange(VSMF_NST_ADVERTISMENT_URL+nstId, HttpMethod.DELETE, httpEntity, String.class);

         log.debug("Response code: " + httpResponse.getStatusCode().toString());
         HttpStatus code = httpResponse.getStatusCode();

         if (code.equals(HttpStatus.OK)) {
             log.debug("NST advertising correctly removed.");
         } else {
             log.debug("Error while removing NST advertised.");
             throw new FailedOperationException("Error while removing NST advertised.");
         }


    }
}
