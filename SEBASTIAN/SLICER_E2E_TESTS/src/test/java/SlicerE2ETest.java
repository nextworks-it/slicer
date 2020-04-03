import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.blueprint.elements.*;
import it.nextworks.nfvmano.catalogue.blueprint.messages.OnBoardVsBlueprintRequest;
import it.nextworks.nfvmano.catalogue.blueprint.messages.OnboardVsDescriptorRequest;
import it.nextworks.nfvmano.catalogue.domainLayer.*;
import it.nextworks.nfvmano.sebastian.common.ActuationRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.ModifyVsRequest;
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
import java.util.*;

import static org.junit.Assert.assertTrue;

public class SlicerE2ETest {

    private String nstUuid;
    private String vsiUuid;

    private final String VSMF_HOST = "http://127.0.0.1:8081";
    EndPointInteraction dspInteraction = new EndPointInteraction(VSMF_HOST, "DSP");

    private final String NSMF_HOST= "http://127.0.0.1:8082";
    EndPointInteraction nspInteraction = new EndPointInteraction(NSMF_HOST, "NSP A");

    private final String NSMF_HOST2= "http://10.30.8.76:8082";
    EndPointInteraction nspInteraction2 = new EndPointInteraction(NSMF_HOST2, "NSP B");




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


    public String onBoardVsbWithNstTransRules(String dspHost, String vsblueprintFilename,String nstUuid) {
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
        return (String) responseEntity.getBody();
    }



    public String testVSDOnBoarding(String vsbId, String filename) {

        final String ONBOARD_VSD_URL = "/portal/catalogue/vsdescriptor";

        OnboardVsDescriptorRequest onboardVsDescriptorRequestFromJSON = (OnboardVsDescriptorRequest) getObjectFromFile(OnboardVsDescriptorRequest.class, filename);

        //field isPublic missing in the JSON, but if added JACKSON gives error.
        onboardVsDescriptorRequestFromJSON.getVsd().setVsBlueprintId(vsbId);

        OnboardVsDescriptorRequest onboardVsDescriptorRequest = new OnboardVsDescriptorRequest(onboardVsDescriptorRequestFromJSON.getVsd(), dspInteraction.getTenant().getUsername(), true);
        log.info("Going to request VSD on boarding with tenantID: " + onboardVsDescriptorRequest.getTenantId()+ " and VSB ID "+onboardVsDescriptorRequest.getVsd().getVsBlueprintId());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, onboardVsDescriptorRequest, VSMF_HOST + ONBOARD_VSD_URL, HttpMethod.POST, dspInteraction.getCookiesTenant());

        assertTrue(vsbId != null);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.CREATED);

        String vsdId= (String)responseEntity.getBody();
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
        return vsdId;
    }


    public String VSIinstantionTest(String vsdId) {
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
        return (String) responseEntity.getBody();

        //ResponseEntity<?> responseEntityQuery = Util.performHttpRequest(QueryVsResponse.class, null, VSMF_HOST + VSI_INSTANTIATION_URL + "/" + vsiUuid, HttpMethod.GET, dspInteraction.getCookiesTenant());
        //QueryVsResponse queryVsResponse = (QueryVsResponse) responseEntityQuery.getBody();

        //assertTrue(queryVsResponse.getVsiId().equals(vsiUuid));
        //assertTrue(queryVsResponse.getName().equals(instantiateVsRequest.getName()));
        //assertTrue(queryVsResponse.getDescription().equals(instantiateVsRequest.getDescription()));
        //assertTrue(queryVsResponse.getVsdId().equals(vsdId));
    }



    public void VSImodificationTest(String vsdId) {
        final String VSI_INSTANTIATION_URL = "/vs/basic/vslcm/vs/"+vsiUuid;

        ModifyVsRequest modifyVsRequest = new ModifyVsRequest(vsiUuid,dspInteraction.getTenant().getUsername(),vsdId);

        log.info("Going to request VSI modification with VSI ID equal to: "+vsiUuid+" with tenant username: "+ dspInteraction.getTenant().getUsername()+" and vsdId equal to "+vsdId);

        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, modifyVsRequest, VSMF_HOST + VSI_INSTANTIATION_URL, HttpMethod.PUT, dspInteraction.getCookiesTenant());
        String statusCode= responseEntity.getStatusCode().toString();
        log.info("Status code modification"+statusCode);
    }



    public void VsiTerminationTest(String vsdId) {
        final String VSI_TERMINATION_URL="/vs/basic/vslcm/vs/"+ vsiUuid +"/terminate";
        TerminateVsRequest terminateVsRequest = new TerminateVsRequest(vsdId, dspInteraction.getTenant().getUsername());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, terminateVsRequest, VSMF_HOST + VSI_TERMINATION_URL, HttpMethod.POST, dspInteraction.getCookiesTenant());
        String terminationResponse = (String) responseEntity.getBody();
        log.info("Termination response body response: "+terminationResponse);
        log.info("Termination response code: "+responseEntity.getStatusCode());
        assertTrue(responseEntity.getStatusCode()==HttpStatus.OK);
    }


    public void VsiPurgeTest(String vsdId) {
        final String VSI_PURGE_URL="/vs/basic/vslcm/vs/"+ vsiUuid;
        PurgeVsRequest purgeVsRequest= new PurgeVsRequest(vsdId, dspInteraction.getTenant().getUsername());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, purgeVsRequest, VSMF_HOST + VSI_PURGE_URL, HttpMethod.DELETE, dspInteraction.getCookiesAdmin());
        String purgeResponse = (String) responseEntity.getBody();
        log.info("Termination response body response: "+purgeResponse);
        log.info("Termination response code: "+responseEntity.getStatusCode());
        assertTrue(responseEntity.getStatusCode()==HttpStatus.OK);

    }

    @Test
    public void onBoardVSBWithNstID(){
        //NSP1 SIDE
        nspInteraction.loginAdmin();
        nspInteraction.createGroup("NSP_group");
        nspInteraction.createTenant("tenant_nsp_sample_a.json");
        nspInteraction.createSLA();
        //nspInteraction.createSLANoResource();
        nspInteraction.loginTenant();


        //NSP2 SIDE
        //nspInteraction2.loginAdmin();
        //nspInteraction2.createGroup("NSP_group");
        //nspInteraction2.createTenant("tenant_nsp_sample_b.json");
        //nspInteraction2.createSLA();
        //nspInteraction.createSLANoResource();
        //nspInteraction2.loginTenant();


        //DSP SIDE
        dspInteraction.loginAdmin();
        dspInteraction.createGroup("DSP_group");
        dspInteraction.createTenant("tenant_sample.json");
        dspInteraction.createTenantExt("tenant_notif_sample.json");
        dspInteraction.createTenantExt2("tenant_notif_sample_2.json");

        dspInteraction.loginTenant();
        dspInteraction.createRemoteTenantInfo(nspInteraction.getTenant(),NSMF_HOST);

        ArrayList<DomainLayer> domainLayerArrayList= new ArrayList<DomainLayer>();
        domainLayerArrayList.add(new NspDomainLayer("nsp_a", NspNbiType.NEUTRAL_HOSTING));
        Domain domain = new Domain("nsp_a",
                "nsp_a","nsp a description",
                "nsp_a owner","nsp_a admin",
                domainLayerArrayList,new HashSet<DomainAgreement>(),
                new DomainInterface("127.0.0.1",
                8082,true, InterfaceType.HTTP));
        domain.setDomainStatus(DomainStatus.ACTIVE);

        ArrayList<DomainLayer> domainLayerArrayList2= new ArrayList<DomainLayer>();
        domainLayerArrayList2.add(new NspDomainLayer("nsp_b", NspNbiType.NEUTRAL_HOSTING));
        Domain domainb = new Domain("nsp_b",
                "nsp_b","nsp_b descr",
                "nsp_b owner","nsp_b admin",
                domainLayerArrayList2,new HashSet<DomainAgreement>(),
                new DomainInterface("10.30.8.76",
                        8082,true, InterfaceType.HTTP));
        domainb.setDomainStatus(DomainStatus.ACTIVE);
        dspInteraction.addDomainInfo(domain);
        //dspInteraction.addDomainInfo(domainb);

        //dspInteraction.createRemoteTenantInfo2(nspInteraction2.getTenant(),NSMF_HOST2);

        dspInteraction.associateLocalTenantWithRemoteTenant();
        //dspInteraction.associateLocalTenantWithRemoteTenant2();

        //On NSP1
        nspInteraction.createRemoteTenantInfo(dspInteraction.getTenantNot(),VSMF_HOST);
        nspInteraction.associateLocalTenantWithRemoteTenant();

        //On NSP2
        //nspInteraction2.createRemoteTenantInfo(dspInteraction.getTenantNot2(),VSMF_HOST);
        // nspInteraction2.associateLocalTenantWithRemoteTenant();


        //ON NSP1
        nstUuid=nspInteraction.onBoardNST("nst_sample.json");
        String nstUuid2 =nspInteraction.onBoardNST("nst_sample2.json");
        String nstUuid3 =nspInteraction.onBoardNST("nst_sample3.json");
        String nstUrlccUuid =nspInteraction.onBoardNST("nst_sample_urlcc.json");


        //ON NSP2
        //String nstUuid_2 =nspInteraction2.onBoardNST("nst_sample.json");
        //String nstUuid2_2 =nspInteraction2.onBoardNST("nst_sample2.json");
        //String nstUuid3_2 =nspInteraction2.onBoardNST("nst_sample3.json");
        //nspInteraction2.removeNST(nstUuid2_2);
        //String nstUrlccUuid_2 =nspInteraction2.onBoardNST("nst_sample_urlcc.json");
        // nspInteraction.removeNST(nstUrlccUuid_2);

        //DSP side
        //onBoardVsbWithNstTransRules(VSMF_HOST, "vsblueprint_osm_sample.json", nstUuid);
        log.info("Testing VSB and VSD streaming on boarding");
        String vsbIdStreaming=onBoardVsbWithNstTransRules(VSMF_HOST, "vsb_samples/vsb_streaming.json", nstUuid);
        String vsdStreamingId= testVSDOnBoarding(vsbIdStreaming,"vsb_samples/vsd_streaming_one.json");

        log.info("Testing VSB and VSDs streaming on boarding");
        String vsbIdUrban=onBoardVsbWithNstTransRules(VSMF_HOST, "vsb_samples/vsb_urban.json", nstUuid);
        String vsdUrbanId1= testVSDOnBoarding(vsbIdUrban,"vsb_samples/vsd_urban_1.json");

        String vsiId = VSIinstantionTest(vsdStreamingId);
        log.info("VSI instantiated. Id is "+vsiId);


        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key1","value1");
        parameters.put("key2","value2");
        ActuationRequest actuationRequest = new ActuationRequest(vsiId, "actuationName", "actiatonDescription",parameters,"");
        log.info("Going to perform actuation request for vsi with ID " + vsiId);
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, actuationRequest, VSMF_HOST + "/vs/basic/vslcm/e2ens/"+vsiId+"/actuate", HttpMethod.POST, dspInteraction.getCookiesTenant());
        // try {
        //    log.info("Waiting vertical service to be instanciated");
        //    Thread.sleep(30000);
        //    //VSImodificationTest();
        //    VsiTerminationTest();
        //} catch (InterruptedException e) {
        //   e.printStackTrace();
        // }


    }
}