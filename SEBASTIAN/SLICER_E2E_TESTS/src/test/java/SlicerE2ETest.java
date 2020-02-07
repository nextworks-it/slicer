import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.blueprint.elements.*;
import it.nextworks.nfvmano.catalogue.blueprint.messages.OnBoardVsBlueprintRequest;
import it.nextworks.nfvmano.catalogue.blueprint.messages.OnboardVsDescriptorRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.PurgeVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.TerminateVsRequest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    private String nstUuid;
    private String vsbId;
    private String vsdId;
    private String vsiUuid;

    private final String NSMF_HOST= "http://10.30.8.76:8082";
    private final String VSMF_HOST = "http://10.30.8.54:8081";
    EndPointInteraction nspInteraction = new EndPointInteraction(NSMF_HOST, "NSP");
    EndPointInteraction dspInteraction = new EndPointInteraction(VSMF_HOST, "DSP");

    private static final Logger log = LoggerFactory.getLogger(SlicerE2ETest.class);

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



/*
    private void deleteLocalTenantWithRemoteTenantAssociation(){
        log.info("Going to delete association between local tenant and a remote one");
        ResponseEntity<?> responseEntity = performHTTPRequest(String.class, remoteTenantInfoIdOnDSP, VSMF_HOST + "/vs/admin/group/"+ groupNameDSP +"/tenant/"+ tenantDSP.getUsername()+"/remotetenant", HttpMethod.DELETE, cookiesAdminDSP);
    }
*/

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


    private void getVSBbyId(){
        final String ONBOARD_VSB_URL = "/portal/catalogue/vsblueprint";
        ResponseEntity<?> responseEntity = Util.performHttpRequest(VsBlueprintInfo.class, null, VSMF_HOST + ONBOARD_VSB_URL + "/" + vsbId, HttpMethod.GET, dspInteraction.getCookiesAdmin());
        VsBlueprintInfo vsBlueprintInfo = (VsBlueprintInfo) responseEntity.getBody();
    }




    public void onBoardVsbWithNstTransRules(String dspHost, String vsblueprintFilename,String nstUuid) {
        final String ONBOARD_VSB_URL = "/portal/catalogue/vsblueprint";
        log.info("The NST id on NSP side is "+nstUuid);
        OnBoardVsBlueprintRequest onBoardVsBlueprintRequestFromJSON = (OnBoardVsBlueprintRequest) getObjectFromFile(OnBoardVsBlueprintRequest.class, vsblueprintFilename);

        List<VsdNstTranslationRule> vsdNstTranslationRuleList = new ArrayList<>();
        log.info("Translation rule found: count  "+onBoardVsBlueprintRequestFromJSON.getNstTranslationRules().size());
        for(int i=0; i<onBoardVsBlueprintRequestFromJSON.getNstTranslationRules().size(); i++){

            VsdNstTranslationRule vsdNstTranslationRuleFromJSON = onBoardVsBlueprintRequestFromJSON.getNstTranslationRules().get(i);
            VsdNstTranslationRule vsdNstTranslationRuleTmp = new VsdNstTranslationRule(
                    vsdNstTranslationRuleFromJSON.getInput(),
                    nstUuid,null,null,null,
                    vsdNstTranslationRuleFromJSON.getNsInstantiationLevelId()
            );
            vsdNstTranslationRuleList.add(vsdNstTranslationRuleTmp);
        }


        OnBoardVsBlueprintRequest onBoardVsBlueprintRequest = new OnBoardVsBlueprintRequest(
                onBoardVsBlueprintRequestFromJSON.getVsBlueprint(),
                vsdNstTranslationRuleList);

        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, onBoardVsBlueprintRequest, dspHost + ONBOARD_VSB_URL, HttpMethod.POST, dspInteraction.getCookiesAdmin());
        vsbId = (String) responseEntity.getBody();
        log.info(vsbId);
        log.info("{}", responseEntity.getStatusCodeValue());
    }

    /* TODO to be changed because there is no more VSD to NSD translation rule
    public void testVSBOnBoardingWithVsdNSDTranslRules(String dspHost, String vsblueprintFilename) {
        final String ONBOARD_VSB_URL = "/portal/catalogue/vsblueprint";
        OnBoardVsBlueprintRequest onBoardVsBlueprintRequestFromJSON = (OnBoardVsBlueprintRequest) getObjectFromFile(OnBoardVsBlueprintRequest.class, vsblueprintFilename);
        List<VsdNstTranslationRule> vsdNsdTranslationRuleList = new ArrayList<VsdNstTranslationRule>();

        for(int i=0; i<onBoardVsBlueprintRequestFromJSON.getTranslationRules().size(); i++){
            VsdNstTranslationRule vsdNsdTranslationRuleFromJSON = onBoardVsBlueprintRequestFromJSON.getTranslationRules().get(i);

            VsdNstTranslationRule vsdNsdTranslationRuleTmp = new VsdNstTranslationRule(
                    vsdNsdTranslationRuleFromJSON.getInput(),
                    nstUuid,
                    vsdNsdTranslationRuleFromJSON.getNsInstantiationLevelId()
                    );
            vsdNsdTranslationRuleList.add(vsdNsdTranslationRuleTmp);
        }


        OnBoardVsBlueprintRequest onBoardVsBlueprintRequest = new OnBoardVsBlueprintRequest(
                onBoardVsBlueprintRequestFromJSON.getVsBlueprint(),
                onBoardVsBlueprintRequestFromJSON.getTranslationRules(),
                vsdNsdTranslationRuleList);

        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, onBoardVsBlueprintRequest, dspHost + ONBOARD_VSB_URL, HttpMethod.POST, dspInteraction.getCookiesAdmin());

        vsbId = (String) responseEntity.getBody();
        log.info(vsbId);
        log.info("{}", responseEntity.getStatusCodeValue());
        assertTrue(vsbId != null);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.CREATED);

        ResponseEntity<?> responseEntity2 = Util.performHttpRequest(VsBlueprintInfo.class, null, dspHost + ONBOARD_VSB_URL + "/" + vsbId, HttpMethod.GET, dspInteraction.getCookiesAdmin());
        VsBlueprintInfo vsBlueprintInfo = (VsBlueprintInfo) responseEntity2.getBody();
        log.info("ID: " + vsBlueprintInfo.getVsBlueprintId());


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
*/

    public void testVSDOnBoarding() {

        final String ONBOARD_VSD_URL = "/portal/catalogue/vsdescriptor";

        OnboardVsDescriptorRequest onboardVsDescriptorRequestFromJSON = (OnboardVsDescriptorRequest) getObjectFromFile(OnboardVsDescriptorRequest.class, "vsd_sample.json");

        //field isPublic missing in the JSON, but if added JACKSON gives error.
        onboardVsDescriptorRequestFromJSON.getVsd().setVsBlueprintId(vsbId);

        OnboardVsDescriptorRequest onboardVsDescriptorRequest = new OnboardVsDescriptorRequest(onboardVsDescriptorRequestFromJSON.getVsd(), dspInteraction.getTenant().getUsername(), true);
        log.info("Going to request VSD on boarding with tenantID: " + onboardVsDescriptorRequest.getTenantId()+ " and VSB ID "+onboardVsDescriptorRequest.getVsd().getVsBlueprintId());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, onboardVsDescriptorRequest, VSMF_HOST + ONBOARD_VSD_URL, HttpMethod.POST, dspInteraction.getCookiesTenant());
        vsdId = (String) responseEntity.getBody();
        assertTrue(vsbId != null);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.CREATED);


        ResponseEntity<?> responseEntityQuery = Util.performHttpRequest(VsDescriptor.class, null, VSMF_HOST + ONBOARD_VSD_URL + "/" + vsdId, HttpMethod.GET, dspInteraction.getCookiesTenant());

        VsDescriptor vsDescriptorActual = (VsDescriptor) responseEntityQuery.getBody();
        VsDescriptor vsDescriptorExpected = onboardVsDescriptorRequest.getVsd();

        log.info(vsDescriptorActual.getName());
        log.info(vsDescriptorExpected.getName());

        ArrayList<String> parsToExclude = new ArrayList<String>();
        log.info("Comparing VsDescriptor: actual vs expected");
        parsToExclude.add("ServiceConstraints");
        parsToExclude.add("Sla");

        haveTwoObjsSameFields(VsDescriptor.class, vsDescriptorActual, vsDescriptorExpected, parsToExclude);
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
                dspInteraction.getTenant().getUsername(),
                vsifromJSON.getNotificationUrl(),
                vsifromJSON.getUserData(),
                vsifromJSON.getLocationConstraints());
        log.info("Going to request VSI instantiation with VSD ID equal to: "+vsdId+" with tenant username: "+ dspInteraction.getTenant().getUsername());
        log.info("VSI name is : "+instantiateVsRequest.getName());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, instantiateVsRequest, VSMF_HOST + VSI_INSTANTIATION_URL, HttpMethod.POST, dspInteraction.getCookiesTenant());
        vsiUuid = (String) responseEntity.getBody();
        log.info("VSI ID:" + vsiUuid);
        //ResponseEntity<?> responseEntityQuery = Util.performHttpRequest(QueryVsResponse.class, null, VSMF_HOST + VSI_INSTANTIATION_URL + "/" + vsiUuid, HttpMethod.GET, dspInteraction.getCookiesTenant());
        //QueryVsResponse queryVsResponse = (QueryVsResponse) responseEntityQuery.getBody();

        //assertTrue(queryVsResponse.getVsiId().equals(vsiUuid));
        //assertTrue(queryVsResponse.getName().equals(instantiateVsRequest.getName()));
        //assertTrue(queryVsResponse.getDescription().equals(instantiateVsRequest.getDescription()));
        //assertTrue(queryVsResponse.getVsdId().equals(vsdId));


    }


    public void VsiTerminationTest() {
        final String VSI_TERMINATION_URL="/vs/basic/vslcm/vs/"+ vsiUuid +"/terminate";
        TerminateVsRequest terminateVsRequest = new TerminateVsRequest(vsdId, dspInteraction.getTenant().getUsername());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, terminateVsRequest, VSMF_HOST + VSI_TERMINATION_URL, HttpMethod.POST, dspInteraction.getCookiesTenant());
        String terminationResponse = (String) responseEntity.getBody();
        log.info("Termination response body response: "+terminationResponse);
        log.info("Termination response code: "+responseEntity.getStatusCode());
        assertTrue(responseEntity.getStatusCode()==HttpStatus.OK);
    }


    public void VsiPurgeTest() {
        final String VSI_PURGE_URL="/vs/basic/vslcm/vs/"+ vsiUuid;
        PurgeVsRequest purgeVsRequest= new PurgeVsRequest(vsdId, dspInteraction.getTenant().getUsername());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, purgeVsRequest, VSMF_HOST + VSI_PURGE_URL, HttpMethod.DELETE, dspInteraction.getCookiesAdmin());
        String purgeResponse = (String) responseEntity.getBody();
        log.info("Termination response body response: "+purgeResponse);
        log.info("Termination response code: "+responseEntity.getStatusCode());
        assertTrue(responseEntity.getStatusCode()==HttpStatus.OK);

    }


    //@Test//Configuration needed (nfvo.catalogue is TIMEO, nfvo.lcm is DUMMY)
    public void testVSLifecycle() {
        //This test works with:
        //nfvo.lcm.driver=dummy
        //nfvo.catalogue.driver=TIMEO
        init();
        //Vertical service instantiation
        VSIinstantionTest();
        log.info("Pretending to wait net slice to be instantiated");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        VsiTerminationTest();
        log.info("Pretending to wait net slice to be terminated");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VsiPurgeTest();
    }

    @Test
    public void onBoardVSBWithNstID(){
        //NSP SIDE
        nspInteraction.loginAdmin();
        nspInteraction.createGroup("NSP_group");
        nspInteraction.createTenant("tenant_nsp_sample.json");
        nspInteraction.loginTenant();
        nstUuid =nspInteraction.onBoardNST("nst_sample.json");

        //DSP SIDE
        dspInteraction.loginAdmin();
        dspInteraction.createGroup("DSP_group");
        dspInteraction.createTenant("tenant_sample.json");
        dspInteraction.createTenantNotif("tenant_notif_sample.json");
        dspInteraction.createSLA();
        dspInteraction.loginTenant();
        dspInteraction.createRemoteTenantInfo(nspInteraction.getTenant(),NSMF_HOST);
        dspInteraction.associateLocalTenantWithRemoteTenant();

        //On NSP
        nspInteraction.createRemoteTenantInfo(dspInteraction.getTenantNot(),VSMF_HOST);
        nspInteraction.associateLocalTenantWithRemoteTenant();

        //DSP side
        onBoardVsbWithNstTransRules(VSMF_HOST, "vsblueprint_osm_sample.json", nstUuid);
        log.info("VSB on boarded");
        testVSDOnBoarding();
        log.info("VSD on boarded");
        VSIinstantionTest();
        log.info("VSI on boarded");
    }

    //Configuration needed (nfvo.catalogue is DUMMY, nfvo.lcm is NMRO)
    //@Test
    public void testNmroAndOsm(){
        //NSP SIDE
        nspInteraction.loginAdmin();
        nspInteraction.createGroup("NSP_group");
        nspInteraction.createTenant("tenant_nsp_sample.json");
        nspInteraction.loginTenant();


        //DSP SIDE
        dspInteraction.loginAdmin();
        dspInteraction.createGroup("DSP_group");
        dspInteraction.createTenant("tenant_sample.json");
        dspInteraction.createTenantNotif("tenant_notif_sample.json");
        dspInteraction.createSLA();
        dspInteraction.loginTenant();
        dspInteraction.createRemoteTenantInfo(nspInteraction.getTenant(),NSMF_HOST);
        dspInteraction.associateLocalTenantWithRemoteTenant();

        //On NSP
        nspInteraction.createRemoteTenantInfo(dspInteraction.getTenantNot(),VSMF_HOST);
        nspInteraction.associateLocalTenantWithRemoteTenant();
        nstUuid =nspInteraction.onBoardNST("nst_sample.json");

        //DSP SIDE
        //testVSBOnBoardingWithVsdNSDTranslRules(VSMF_HOST,"vsblueprint_osm_sample.json");
        testVSDOnBoarding();
        VSIinstantionTest();
    }



    private void init(){
        //NSP SIDE
        nspInteraction.loginAdmin();
        nspInteraction.createGroup("NSP_group");
        nspInteraction.createTenant("tenant_nsp_sample.json");
        nspInteraction.loginTenant();


        //DSP SIDE
        dspInteraction.loginAdmin();
        dspInteraction.createGroup("DSP_group");
        dspInteraction.createTenant("tenant_sample.json");
        dspInteraction.createTenantNotif("tenant_notif_sample.json");
        dspInteraction.createSLA();
        dspInteraction.loginTenant();
        dspInteraction.createRemoteTenantInfo(nspInteraction.getTenant(),NSMF_HOST);
        dspInteraction.associateLocalTenantWithRemoteTenant();

        //On NSP
        nspInteraction.createRemoteTenantInfo(dspInteraction.getTenantNot(),VSMF_HOST);
        nspInteraction.associateLocalTenantWithRemoteTenant();
        nstUuid =nspInteraction.onBoardNST("nst_sample.json");

        //DSP SIDE
        //testVSBOnBoardingWithVsdNSDTranslRules(VSMF_HOST,"vsblueprint_sample.json");
        //testVSDOnBoarding();
    }
}
