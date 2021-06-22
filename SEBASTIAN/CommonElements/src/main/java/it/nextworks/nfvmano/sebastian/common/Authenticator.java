package it.nextworks.nfvmano.sebastian.common;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.RemoteTenantInfo;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Authenticator {
    private String hostname;

    private String authCookie;
    private boolean isAuthenticated;

    private static final Logger log = LoggerFactory.getLogger(Authenticator.class);

    private AdminService adminService;

    public Authenticator(String hostname, AdminService adminService){
        isAuthenticated=false;
        this.hostname = hostname;
        this.adminService=adminService;
    }

    public void authenticate(String tenantId){
        log.info("Going to perform authentication to "+this.hostname + ". Local tenant ID is "+tenantId);
        //if(isAuthenticated==true){
        //    log.info(tenantId+" already authenticated.");
        //    return;
        //}

        String username="";
        String password="";
        boolean remoteTenantAssociated=false;

        try {
            log.info("Getting remote tenant of local tenant with ID "+tenantId);
            Tenant tenant=adminService.getTenant(tenantId);
            for(RemoteTenantInfo remoteTenantInfo: tenant.getRemoteTenantInfos()){
                log.info(hostname);
                log.info(remoteTenantInfo.getHost());
                log.info(remoteTenantInfo.getRemoteTenantName());
                log.info(remoteTenantInfo.getRemoteTenantPwd());

                if(remoteTenantInfo.getHost().equals(hostname)){
                    username=remoteTenantInfo.getRemoteTenantName();
                    password=remoteTenantInfo.getRemoteTenantPwd();
                    remoteTenantAssociated=true;
                    break;
                }
            }
        } catch (NotExistingEntityException e) {
            log.error("The tenant "+tenantId+" does not exist.");
            isAuthenticated=false;
        }

        if(remoteTenantAssociated==false){
            isAuthenticated=false;
            log.error("No remote tenant(s) associated to "+tenantId);
            return;
        }

        log.info("Building http request to login");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> httpResponse = restTemplate.exchange(hostname + "/login", HttpMethod.POST, request, String.class);
        HttpHeaders headersResp = httpResponse.getHeaders();
        authCookie = headersResp.getFirst(HttpHeaders.SET_COOKIE);
        log.info("Tenant authentication performed on NSP. Cookie returned:  " + authCookie);
        isAuthenticated=true;
    }



    public String getCookie(){
        return authCookie;
    }
    public boolean isAuthenticated(){
        return isAuthenticated;
    }


}
