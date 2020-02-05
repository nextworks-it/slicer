import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.template.messages.OnBoardNsTemplateRequest;
import it.nextworks.nfvmano.sebastian.admin.elements.RemoteTenantInfo;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

public class EndPointInteraction {
    private String groupName;
    private Tenant tenant;
    private Tenant tenantNotif;
    private String cookiesAdmin;
    private String cookiesTenant;
    private RemoteTenantInfo remoteTenantInfo;
    private String remoteTenantInfoId;
    private String hostDescriptionToInteractWith;
    private String hostname;
    private static final Logger log = LoggerFactory.getLogger(SlicerE2ETest.class);


    public EndPointInteraction(String hostname, String hostDescriptionToInteractWith){
        this.hostname = hostname;
        this.hostDescriptionToInteractWith=hostDescriptionToInteractWith;
    }


    private Object getObjectFromFile(Class classObjects, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);

        try {
            Object object = objectMapper.readValue(file, classObjects);
            return object;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createGroup(String groupName) {
        this.groupName=groupName;
        log.info("Creating group with name: " + groupName+ " on "+hostDescriptionToInteractWith + " side");
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, null, hostname + "/vs/admin/group/" + groupName, HttpMethod.POST, cookiesAdmin);
    }

    public void createTenant(String filename) {
        tenant = (Tenant) getObjectFromFile(Tenant.class, filename);
        log.info("Creating tenant on "+hostDescriptionToInteractWith+" with username: " + tenant.getUsername());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, tenant, hostname + "/vs/admin/group/" + groupName + "/tenant", HttpMethod.POST, cookiesAdmin);
    }

    public void createTenantNotif(String filename) {
        tenantNotif = (Tenant) getObjectFromFile(Tenant.class, filename);
        log.info("Creating tenantNotif on "+hostDescriptionToInteractWith+" with username: " + tenantNotif.getUsername());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, tenantNotif, hostname + "/vs/admin/group/" + groupName + "/tenant", HttpMethod.POST, cookiesAdmin);
    }


    public void createSLA() {
        Sla sla = (Sla) getObjectFromFile(Sla.class, "sla_sample.json");
        log.info("Creating SLA");
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, sla, hostname + "/vs/admin/group/" + groupName + "/tenant/" + tenant.getUsername() + "/sla", HttpMethod.POST, cookiesAdmin);
    }

    public void loginAdmin() {
        String address= hostname;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", "admin");
        map.add("password", "admin");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> httpResponse = restTemplate.exchange(hostname + "/login", HttpMethod.POST, request, String.class);
        HttpHeaders headersResp = httpResponse.getHeaders();

        cookiesAdmin = headersResp.getFirst(HttpHeaders.SET_COOKIE);
        log.info("Admin authentication on "+hostDescriptionToInteractWith+" side performed. Cookie returned:  " + cookiesAdmin);

    }

    public void createRemoteTenantInfo(Tenant remoteTenant, String remoteHostname){
        remoteTenantInfo = new RemoteTenantInfo(remoteTenant.getUsername(),remoteTenant.getPassword(),remoteHostname);
        log.info("Creating new remote tenant info on "+hostDescriptionToInteractWith);
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, remoteTenantInfo, hostname + "/vs/admin/remotetenant", HttpMethod.POST, cookiesAdmin);
        remoteTenantInfoId = (String)responseEntity.getBody();
    }



    public void loginTenant() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", tenant.getUsername());
        map.add("password", tenant.getPassword());


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> httpResponse = restTemplate.exchange(hostname + "/login", HttpMethod.POST, request, String.class);
        HttpHeaders headersResp = httpResponse.getHeaders();
        cookiesTenant = headersResp.getFirst(HttpHeaders.SET_COOKIE);
        log.info("Tenant authentication performed on "+hostDescriptionToInteractWith+". Cookie returned:  " + cookiesTenant);
    }

    public  String onBoardNST(String filename) {
        //Not going to test NST onboarding because already done in NST CATALOGUE APP, in blue-print repo
        final String ONBOARD_NST_URL = "/ns/catalogue/nstemplate";
        OnBoardNsTemplateRequest onBoardNsTemplateRequest = (OnBoardNsTemplateRequest) getObjectFromFile(OnBoardNsTemplateRequest.class, filename);

        log.info("Going to perform on board request of NST with name " + onBoardNsTemplateRequest.getNst().getNstName() + " and version " + onBoardNsTemplateRequest.getNst().getNstName());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, onBoardNsTemplateRequest, hostname + ONBOARD_NST_URL, HttpMethod.POST, cookiesAdmin);
        if(responseEntity!=null)
             return (String) responseEntity.getBody();
       return null;
    }

    public void associateLocalTenantWithRemoteTenant(){
        log.info("Going to associate local tenant with a remote one on "+this.hostDescriptionToInteractWith);
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, remoteTenantInfoId, hostname + "/vs/admin/group/"+ groupName +"/tenant/"+ tenant.getUsername(), HttpMethod.PUT, cookiesAdmin);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public Tenant getTenantNot() {
        return tenantNotif;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getCookiesAdmin() {
        return cookiesAdmin;
    }

    public void setCookiesAdmin(String cookiesAdmin) {
        this.cookiesAdmin = cookiesAdmin;
    }

    public String getCookiesTenant() {
        return cookiesTenant;
    }

    public void setCookiesTenant(String cookiesTenant) {
        this.cookiesTenant = cookiesTenant;
    }

    public RemoteTenantInfo getRemoteTenantInfo() {
        return remoteTenantInfo;
    }

    public void setRemoteTenantInfo(RemoteTenantInfo remoteTenantInfo) {
        this.remoteTenantInfo = remoteTenantInfo;
    }

    public String getRemoteTenantInfoId() {
        return remoteTenantInfoId;
    }

    public void setRemoteTenantInfoId(String remoteTenantInfoId) {
        this.remoteTenantInfoId = remoteTenantInfoId;
    }
}
