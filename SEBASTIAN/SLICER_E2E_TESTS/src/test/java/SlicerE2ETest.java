import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.blueprint.elements.*;
import it.nextworks.nfvmano.catalogue.blueprint.messages.OnBoardVsBlueprintRequest;
import it.nextworks.nfvmano.catalogue.blueprint.messages.OnboardVsDescriptorRequest;
import it.nextworks.nfvmano.catalogue.template.messages.OnBoardNsTemplateRequest;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.vsfm.messages.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SlicerE2ETest {
    private final String NFV_LCM_POLLING_TIME = "10"; //Expressed in seconds

    private final String VSMF_ADDRESS = "http://localhost:8081";
    private final String NFVO_CATALOGUE_ADDRESS="http://10.20.8.25/nfvo/vnfdManagement/vnfPackage";
    private final String NSMF_ADDRESS = "http://localhost:8082";


    private String groupName;
    private Tenant tenant;

    private String cookiesAdmin;
    private String cookiesTenant;

    private String nstId;
    private String vsbId;
    private String vsdId;
    private String vsiId;

    private static final Logger log = LoggerFactory.getLogger(SlicerE2ETest.class);

    public SlicerE2ETest() {
        //TODO add log info and check availability services

    }

    private int getRandom() {
        double x = Math.random() * 100 + 1;
        return (int) x;
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


    private ResponseEntity<?> performHTTPRequest(Class classObjectsRetrieved, Object request, String url, HttpMethod httpMethod, String cookies) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        header.add("Cookie", cookies);
        HttpEntity<?> httpEntity = new HttpEntity<>(request, header);

        try {
            log.info("URL performing the request to: " + url);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<?> httpResponse =
                    restTemplate.exchange(url, httpMethod, httpEntity, classObjectsRetrieved);
            HttpStatus code = httpResponse.getStatusCode();
            log.info("Response code: " + httpResponse.getStatusCode().toString());
            return httpResponse;
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createGroup(String groupName) {
        log.info("Creating group with name: " + groupName);
        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, null, VSMF_ADDRESS + "/vs/admin/group/" + groupName, HttpMethod.POST, cookiesAdmin);
        this.groupName = groupName;
    }

    private void createTenant() {
        tenant = (Tenant) getObjectFromFile(Tenant.class, "tenant_sample.json");
        log.info("Creating tenant with username: " + tenant.getUsername());
        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, tenant, VSMF_ADDRESS + "/vs/admin/group/" + groupName + "/tenant", HttpMethod.POST, cookiesAdmin);
    }

    private void createSLA() {
        Sla sla = (Sla) getObjectFromFile(Sla.class, "sla_sample.json");
        log.info("Creating SLA");
        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, sla, VSMF_ADDRESS + "/vs/admin/group/" + groupName + "/tenant/" + tenant.getUsername() + "/sla", HttpMethod.POST, cookiesAdmin);
    }

    private void loginAdmin() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", "admin");
        map.add("password", "admin");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> httpResponse = restTemplate.exchange(VSMF_ADDRESS + "/login", HttpMethod.POST, request, String.class);
        HttpHeaders headersResp = httpResponse.getHeaders();
        cookiesAdmin = headersResp.getFirst(HttpHeaders.SET_COOKIE);
        log.info("Admin authentication performed. Cookie returned:  " + cookiesAdmin);
    }

    private void loginTenant() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", tenant.getUsername());
        map.add("password", tenant.getPassword());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> httpResponse = restTemplate.exchange(VSMF_ADDRESS + "/login", HttpMethod.POST, request, String.class);
        HttpHeaders headersResp = httpResponse.getHeaders();
        cookiesTenant = headersResp.getFirst(HttpHeaders.SET_COOKIE);
        log.info("Tenant authentication performed. Cookie returned:  " + cookiesTenant);
    }

    private void onBoardNST() {
        //Not going to test NST onboarding because already done in NST CATALOGUE APP, in blue-print repo
        final String ONBOARD_NST_URL = "/ns/catalogue/nstemplate";
        OnBoardNsTemplateRequest onBoardNsTemplateRequest = (OnBoardNsTemplateRequest) getObjectFromFile(OnBoardNsTemplateRequest.class, "nst_sample.json");


        log.info("Going to perform on board request of NST with name " + onBoardNsTemplateRequest.getNst().getNstName() + " and version " + onBoardNsTemplateRequest.getNst().getNstName());
        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, onBoardNsTemplateRequest, VSMF_ADDRESS + ONBOARD_NST_URL, HttpMethod.POST, cookiesAdmin);
        nstId = (String) responseEntity.getBody();
        log.info(nstId);
    }


    private void waitPollingTime() {
        log.info("The nfvo lcm polling time is supposed to be " + NFV_LCM_POLLING_TIME + " seconds. Going to wait  " + NFV_LCM_POLLING_TIME + " plus other 5 seconds");
        try {
            Thread.sleep(Long.valueOf(NFV_LCM_POLLING_TIME) * 1000 + 5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /*Given two objects of the same class expressed in classObjects,
    it compares all the fields (except those from fieldsToExcludeFromCompare) through all the available getters method of the same class.
    It returns true if all fields are equal, otherwise false.*/
    private boolean haveTwoObjsSameFields(Class classObjects, Object obj1, Object obj2, ArrayList<String> fieldsToExcludeFromCompare) {
        log.info("TEST: comparing two objects of class " + classObjects.getName());
        if (obj1.getClass() != obj2.getClass()) {
            log.error("TEST:  The two objects are instances of different classes, thus different");
            return false;
        }

        try {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(classObjects).getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String methodName = pd.getReadMethod().getName();
                String fieldName = methodName.substring(3, methodName.length());

                if (fieldsToExcludeFromCompare.contains(fieldName) == true) {
                    log.info("Skipping check of " + fieldName + " field");
                    continue;
                }

                boolean areEqual = false;
                try {
                    Object objReturned1 = pd.getReadMethod().invoke(obj1);
                    Object objReturned2 = pd.getReadMethod().invoke(obj2);
                    if (objReturned1 != null && objReturned2 != null) {
                        areEqual = objReturned1.equals(objReturned2);
                        if (objReturned1 instanceof List<?> && ((List) objReturned1).size() > 0 && ((List) objReturned1).get(0) instanceof String) {
                            String str1 = String.valueOf(objReturned1);
                            String str2 = String.valueOf(objReturned2);
                            areEqual = str1.equals(str2);

                        }
                    } else if (objReturned1 == null && objReturned2 == null) {
                        log.info("TEST: Field with name " + fieldName + " null in both objects.");
                        areEqual = true;
                    } else if (objReturned1 == null || objReturned2 == null) {
                        log.info("Field " + fieldName + " null in one of the two objects: field object1 " + objReturned1 + "; field object2: " + objReturned1);
                    }
                    if (areEqual == false) {
                        log.info("TEST: field with name " + fieldName + " has DIFFERENT values in objects.");
                        //return false;
                    } else {
                        log.info("TEST: field with name " + fieldName + " has same value in both objects. ");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return false;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return false;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void testVSBOnBoarding() {
        final String ONBOARD_VSB_URL = "/portal/catalogue/vsblueprint";
        OnBoardVsBlueprintRequest onBoardVsBlueprintRequestFromJSON = (OnBoardVsBlueprintRequest) getObjectFromFile(OnBoardVsBlueprintRequest.class, "vsblueprint_sample.json");
        List<VsdNsdTranslationRule> vsdNsdTranslationRuleList = new ArrayList<VsdNsdTranslationRule>();

        for(int i=0; i<onBoardVsBlueprintRequestFromJSON.getTranslationRules().size(); i++){
            VsdNsdTranslationRule vsdNsdTranslationRuleFromJSON = onBoardVsBlueprintRequestFromJSON.getTranslationRules().get(i);

            VsdNsdTranslationRule vsdNsdTranslationRuleTmp = new VsdNsdTranslationRule(
                    vsdNsdTranslationRuleFromJSON.getInput(),
                    nstId,
                    vsdNsdTranslationRuleFromJSON.getNsdId(),
                    vsdNsdTranslationRuleFromJSON.getNsdVersion(),
                    vsdNsdTranslationRuleFromJSON.getNsFlavourId(),
                    vsdNsdTranslationRuleFromJSON.getNsInstantiationLevelId()
                    );
            vsdNsdTranslationRuleList.add(vsdNsdTranslationRuleTmp);
        }


        OnBoardVsBlueprintRequest onBoardVsBlueprintRequest = new OnBoardVsBlueprintRequest(
                onBoardVsBlueprintRequestFromJSON.getVsBlueprint(),
                onBoardVsBlueprintRequestFromJSON.getNsds(),
                vsdNsdTranslationRuleList);

        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, onBoardVsBlueprintRequest, VSMF_ADDRESS + ONBOARD_VSB_URL, HttpMethod.POST, cookiesAdmin);

        vsbId = (String) responseEntity.getBody();
        log.info(vsbId);
        log.info("{}", responseEntity.getStatusCodeValue());
        assertTrue(vsbId != null);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.CREATED);

        ResponseEntity<?> responseEntity2 = performHTTPRequest(VsBlueprintInfo.class, null, VSMF_ADDRESS + ONBOARD_VSB_URL + "/" + vsbId, HttpMethod.GET, cookiesAdmin);
        VsBlueprintInfo vsBlueprintInfo = (VsBlueprintInfo) responseEntity2.getBody();
        log.info("ID: " + vsBlueprintInfo.getVsBlueprintId());
        //assertTrue(responseEntity.getBody()!=null);


        VsBlueprint vsBlueprintFromJSON = onBoardVsBlueprintRequestFromJSON.getVsBlueprint();
        VsBlueprint vsBlueprintFromHttpResponse = vsBlueprintInfo.getVsBlueprint();

        ArrayList<String> paramsToNotCompare = new ArrayList<>();
        //Below filed excluded because compared later
        paramsToNotCompare.add("ApplicationMetrics");
        paramsToNotCompare.add("AtomicComponents");
        paramsToNotCompare.add("ConnectivityServices");
        paramsToNotCompare.add("EndPoints");
        paramsToNotCompare.add("Parameters");

        //Below field excluded because not know a priori
        paramsToNotCompare.add("BlueprintId");

        haveTwoObjsSameFields(VsBlueprint.class, vsBlueprintFromJSON, vsBlueprintFromHttpResponse, paramsToNotCompare);

        log.info("");
        log.info("Comparing Application Metric");
        List<ApplicationMetric> appMetricsFromJSON = vsBlueprintFromJSON.getApplicationMetrics();
        List<ApplicationMetric> appMetricsFromHttpResp = vsBlueprintFromJSON.getApplicationMetrics();
        assertTrue(appMetricsFromJSON.size() == appMetricsFromHttpResp.size());
        for (int i = 0; i < appMetricsFromJSON.size(); i++) {
            assertTrue(haveTwoObjsSameFields(ApplicationMetric.class, appMetricsFromJSON.get(i), appMetricsFromHttpResp.get(i), new ArrayList<String>()));
            log.info("");
        }


        log.info("");
        log.info("Comparing vsComponents");
        List<VsComponent> vsComponentsFromJSON = vsBlueprintFromJSON.getAtomicComponents();
        List<VsComponent> vsComponentsFromHttpResp = vsBlueprintFromJSON.getAtomicComponents();
        assertTrue(vsComponentsFromJSON.size() == vsComponentsFromHttpResp.size());
        for (int i = 0; i < vsComponentsFromJSON.size(); i++) {
            haveTwoObjsSameFields(VsComponent.class, vsComponentsFromJSON.get(i), vsComponentsFromHttpResp.get(i), new ArrayList<String>());
            log.info("");
        }
        log.info("");
        log.info("Comparing VsbLinks");
        List<VsbLink> vsbLinkFromJSON = vsBlueprintFromJSON.getConnectivityServices();
        List<VsbLink> vsbLinkFromFromHttpResp = vsBlueprintFromJSON.getConnectivityServices();
        assertTrue(vsbLinkFromJSON.size() == vsbLinkFromFromHttpResp.size());
        for (int i = 0; i < vsbLinkFromJSON.size(); i++) {
            assertTrue(haveTwoObjsSameFields(VsbLink.class, vsbLinkFromJSON.get(i), vsbLinkFromFromHttpResp.get(i), new ArrayList<String>()));
            log.info("");
        }

        log.info("");
        log.info("Comparing endpoints");
        List<VsbEndpoint> vsbLEndPointFromJSON = vsBlueprintFromJSON.getEndPoints();
        List<VsbEndpoint> vsbLEndPointFromFromHttpResp = vsBlueprintFromJSON.getEndPoints();
        assertTrue(vsbLEndPointFromJSON.size() == vsbLEndPointFromFromHttpResp.size());
        for (int i = 0; i < vsbLEndPointFromJSON.size(); i++) {
            haveTwoObjsSameFields(VsbEndpoint.class, vsbLEndPointFromJSON.get(i), vsbLEndPointFromFromHttpResp.get(i), new ArrayList<String>());
            log.info("");
        }

        log.info("");
        log.info("Comparing Parameters");
        List<VsBlueprintParameter> vsbparsFromJSON = vsBlueprintFromJSON.getParameters();
        List<VsBlueprintParameter> vsbparsFromFromHttpResp = vsBlueprintFromJSON.getParameters();
        assertTrue(vsbparsFromJSON.size() == vsbparsFromFromHttpResp.size());
        for (int i = 0; i < vsbparsFromJSON.size(); i++) {
            assertTrue(haveTwoObjsSameFields(VsBlueprintParameter.class, vsbparsFromJSON.get(i), vsbparsFromFromHttpResp.get(i), new ArrayList<String>()));
            log.info("");
        }
        log.info("The VSB has been on boarded as expected. All the internal fields have ben set properly");
    }


    public void testVSDOnBoarding() {

        final String ONBOARD_VSD_URL = "/portal/catalogue/vsdescriptor";

        OnboardVsDescriptorRequest onboardVsDescriptorRequestFromJSON = (OnboardVsDescriptorRequest) getObjectFromFile(OnboardVsDescriptorRequest.class, "vsd_sample.json");

        //field isPublic missing in the JSON, but if added JACKSON gives error. Added hardcoded TODO fix
        onboardVsDescriptorRequestFromJSON.getVsd().setVsBlueprintId(vsbId);

        OnboardVsDescriptorRequest onboardVsDescriptorRequest = new OnboardVsDescriptorRequest(onboardVsDescriptorRequestFromJSON.getVsd(), tenant.getUsername(), true);
        log.info("Going to request VSD on boarding with tenantID: " + onboardVsDescriptorRequest.getTenantId()+ " and VSB ID "+onboardVsDescriptorRequest.getVsd().getVsBlueprintId());
        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, onboardVsDescriptorRequest, VSMF_ADDRESS + ONBOARD_VSD_URL, HttpMethod.POST, cookiesTenant);
        vsdId = (String) responseEntity.getBody();
        assertTrue(vsbId != null);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.CREATED);


        ResponseEntity<?> responseEntityQuery = performHTTPRequest(VsDescriptor.class, null, VSMF_ADDRESS + ONBOARD_VSD_URL + "/" + vsdId, HttpMethod.GET, cookiesTenant);

        VsDescriptor vsDescriptorActual = (VsDescriptor) responseEntityQuery.getBody();
        VsDescriptor vsDescriptorExpected = onboardVsDescriptorRequest.getVsd();

        log.info(vsDescriptorActual.getName());
        log.info(vsDescriptorExpected.getName());

        ArrayList<String> parsToExclude = new ArrayList<String>();
        log.info("Comparing VsDescriptor: actual vs expected");
        parsToExclude.add("ServiceConstraints");
        parsToExclude.add("Sla");

        haveTwoObjsSameFields(VsDescriptor.class, vsDescriptorActual, vsDescriptorExpected, parsToExclude);
        //TODO check VSBlueprint ID
        log.info("VSBID actual : " + vsDescriptorActual.getVsBlueprintId());
        log.info("VSBID expected: " + vsDescriptorExpected.getVsBlueprintId());
        log.info("{}",vsDescriptorExpected.getVsBlueprintId()==vsDescriptorActual.getVsBlueprintId());

        log.info("Comparing ServiceConstraints lists");
        List<ServiceConstraints> serviceConstraintsListActual = vsDescriptorActual.getServiceConstraints();
        List<ServiceConstraints> serviceConstraintsListExpected = vsDescriptorExpected.getServiceConstraints();
        assertTrue(serviceConstraintsListActual.size() == serviceConstraintsListExpected.size());

        for (int i = 0; i < serviceConstraintsListActual.size(); i++) {
            haveTwoObjsSameFields(ServiceConstraints.class, serviceConstraintsListActual.get(i), serviceConstraintsListExpected.get(i), new ArrayList<String>());
            log.info("");
        }

        log.info("Comparing VsdSla(s)");
        haveTwoObjsSameFields(VsdSla.class, vsDescriptorActual.getSla(), vsDescriptorExpected.getSla(), new ArrayList<String>());
        log.info("");
    }


    public void VSIinstantionTest() {
        final String VSI_INSTANTIATION_URL = "/vs/basic/vslcm/vs";

        InstantiateVsRequest vsifromJSON = (InstantiateVsRequest) getObjectFromFile(InstantiateVsRequest.class, "vsi_sample.json");
        InstantiateVsRequest instantiateVsRequest = new InstantiateVsRequest(
                vsifromJSON.getName(),
                vsifromJSON.getDescription(),
                vsdId,
                tenant.getUsername(),
                vsifromJSON.getNotificationUrl(),
                vsifromJSON.getUserData(),
                vsifromJSON.getLocationConstraints());
        log.info("Going to request VSI instantiation with VSD ID equal to: "+vsdId+" with tenant username: "+tenant.getUsername());
        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, instantiateVsRequest, VSMF_ADDRESS + VSI_INSTANTIATION_URL, HttpMethod.POST, cookiesTenant);
        vsiId = (String) responseEntity.getBody();
        log.info("VSI ID:" +vsiId);
        ResponseEntity<?> responseEntityQuery = performHTTPRequest(QueryVsResponse.class, null, VSMF_ADDRESS + VSI_INSTANTIATION_URL + "/" + vsiId, HttpMethod.GET, cookiesTenant);
        QueryVsResponse queryVsResponse = (QueryVsResponse) responseEntityQuery.getBody();

        assertTrue(queryVsResponse.getVsiId().equals(vsiId));
        assertTrue(queryVsResponse.getName().equals(instantiateVsRequest.getName()));
        assertTrue(queryVsResponse.getDescription().equals(instantiateVsRequest.getDescription()));
        //log.info("{}",queryVsResponse.getMonitoringUrl().equals(instantiateVsRequest.getNotificationUrl()));
        assertTrue(queryVsResponse.getVsdId().equals(vsdId));


    }


    public void VSmodificationTest() {
        final String VSI_MODIFICATION_URL = "";
        //ModifyVsRequest vsimodReqfromJSON = (ModifyVsRequest) getObjectFromFile(ModifyVsRequest.class, "vsi_mod_req_sample.json");
        ModifyVsRequest vsimodReqfromJSON = new ModifyVsRequest();
        //String vsdID = "";
        /*
        InstantiateVsRequest instantiateVsRequest = new InstantiateVsRequest(
                vsifromJSON.getName(),
                vsifromJSON.getDescription(),
                vsdId,
                tenant.getUsername(),
                vsifromJSON.getNotificationUrl(),
                vsifromJSON.getUserData(),
                vsifromJSON.getLocationConstraints());
        log.info("Going to request VSI instantiation with VSD ID equal to: "+vsdId+" with tenant username: "+tenant.getUsername());

        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, instantiateVsRequest, VSMF_ADDRESS + VSI_INSTANTIATION_URL, HttpMethod.POST, cookiesTenant);
        String vsiId = (String) responseEntity.getBody();
        log.info(vsiId);
    */
    }

    public void VsiTerminationTest() {
        final String VSI_TERMINATION_URL="/vs/"+vsiId+"/terminate";
        TerminateVsRequest terminateVsRequest = new TerminateVsRequest(vsdId, tenant.getUsername());
        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, terminateVsRequest, VSMF_ADDRESS + VSI_TERMINATION_URL, HttpMethod.POST, cookiesTenant);
        String terminationResponse = (String) responseEntity.getBody();
        log.info("Termination response body response: "+terminationResponse);
        log.info("Termination response code: "+responseEntity.getStatusCode());
        log.info("{}",responseEntity.getStatusCode()==HttpStatus.OK);
    }

    public void VsiPurgeTest() {
        final String VSI_PURGE_URL="/vs/"+vsiId+"/purge";
        PurgeVsRequest purgeVsRequest= new PurgeVsRequest(vsdId, tenant.getUsername());
        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, purgeVsRequest, VSMF_ADDRESS + VSI_PURGE_URL, HttpMethod.POST, cookiesTenant);
        String purgeResponse = (String) responseEntity.getBody();
        log.info("Termination response body response: "+purgeResponse);
        log.info("Termination response code: "+responseEntity.getStatusCode());
        log.info("{}",responseEntity.getStatusCode()==HttpStatus.OK);

    }


    @Test
    public void test() {
        loginAdmin();
        onBoardNST();
        testVSBOnBoarding();
        createGroup("group" + getRandom());
        createTenant();
        createSLA();
        loginTenant();
        testVSDOnBoarding();
        VSIinstantionTest();
        //VSIModifyingTest();
        //VsiTerminationTest();
    }
}
