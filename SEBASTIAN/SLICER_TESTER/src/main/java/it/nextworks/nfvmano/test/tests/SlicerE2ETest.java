package it.nextworks.nfvmano.test.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.blueprint.elements.ServiceConstraints;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsdNstTranslationRule;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsdSla;
import it.nextworks.nfvmano.catalogue.blueprint.messages.OnBoardVsBlueprintRequest;
import it.nextworks.nfvmano.catalogue.blueprint.messages.OnboardVsDescriptorRequest;
import it.nextworks.nfvmano.catalogue.domainLayer.*;
import it.nextworks.nfvmano.sebastian.common.ActuationRequest;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.messages.*;
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

    private final String VSMF_IP;
    private final String NSMF_IP;
    private final String NSMF2_IP;

    private final String VSMF_HOST;

    EndPointInteraction dspInteraction;

    private final String NSMF_HOST;
    EndPointInteraction nspInteraction;

    private final String NSMF_HOST2;
    EndPointInteraction nspInteraction2;

    SlicerTestConfiguration slicerTestConfiguration;

    private static final Logger log = LoggerFactory.getLogger(SlicerE2ETest.class);


    public SlicerE2ETest(SlicerTestConfiguration slicerTestConfiguration){
        this.slicerTestConfiguration = slicerTestConfiguration;

        VSMF_IP = slicerTestConfiguration.getDspAddress();
        VSMF_HOST = "http://"+VSMF_IP+":8081";
        dspInteraction = new EndPointInteraction(VSMF_HOST, "DSP");

        NSMF_IP =slicerTestConfiguration.getNspOneAddress();
        NSMF_HOST= "http://"+NSMF_IP+":8082";
        nspInteraction = new EndPointInteraction(NSMF_HOST, "NSP A");

        NSMF2_IP = slicerTestConfiguration.getNspTwoAddress();
        NSMF_HOST2= "http://"+NSMF2_IP+":8082";
        nspInteraction2 = new EndPointInteraction(NSMF_HOST2, "NSP B");
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


    public String onBoardVsbWithNstTransRules(String dspHost, String vsblueprintFilename) {
        final String ONBOARD_VSB_URL = "/portal/catalogue/vsblueprint";
        OnBoardVsBlueprintRequest onBoardVsBlueprintRequestFromJSON = (OnBoardVsBlueprintRequest) getObjectFromFile(OnBoardVsBlueprintRequest.class, vsblueprintFilename);

        List<VsdNstTranslationRule> vsdNstTranslationRuleList = new ArrayList<>();
        log.info("Translation rule found: count  "+onBoardVsBlueprintRequestFromJSON.getNstTranslationRules().size());
        for(int i=0; i<onBoardVsBlueprintRequestFromJSON.getNstTranslationRules().size(); i++){

            VsdNstTranslationRule vsdNstTranslationRuleFromJSON = onBoardVsBlueprintRequestFromJSON.getNstTranslationRules().get(i);
            VsdNstTranslationRule vsdNstTranslationRuleTmp = new VsdNstTranslationRule(
                    vsdNstTranslationRuleFromJSON.getInput(),
                    "nstId fake",null,null,null,
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


    public String VSIinstantionTest(String vsdId, String filename, String vsiName) {
        final String VSI_INSTANTIATION_URL = "/vs/basic/vslcm/vs";


        InstantiateVsRequest vsifromJSON = (InstantiateVsRequest) getObjectFromFile(InstantiateVsRequest.class, filename);
        InstantiateVsRequest instantiateVsRequest = new InstantiateVsRequest(
                vsiName,
                vsifromJSON.getDescription(),
                vsdId,
                dspInteraction.getTenant().getUsername(),
                vsifromJSON.getNotificationUrl(),
                vsifromJSON.getUserData(),
                vsifromJSON.getLocationsConstraints(),
                vsifromJSON.getImsiInfoList());
        log.info("Going to request VSI instantiation with VSD ID equal to: "+vsdId+" with tenant username: "+ dspInteraction.getTenant().getUsername());
        log.info("VSI name is : "+instantiateVsRequest.getName());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, instantiateVsRequest, VSMF_HOST + VSI_INSTANTIATION_URL, HttpMethod.POST, dspInteraction.getCookiesTenant());
        return (String) responseEntity.getBody();

        //ResponseEntity<?> responseEntityQuery = it.nextworks.nfvmano.test.test.Util.performHttpRequest(QueryVsResponse.class, null, VSMF_HOST + VSI_INSTANTIATION_URL + "/" + vsiUuid, HttpMethod.GET, dspInteraction.getCookiesTenant());
        //QueryVsResponse queryVsResponse = (QueryVsResponse) responseEntityQuery.getBody();

        //assertTrue(queryVsResponse.getVsiId().equals(vsiUuid));
        //assertTrue(queryVsResponse.getName().equals(instantiateVsRequest.getName()));
        //assertTrue(queryVsResponse.getDescription().equals(instantiateVsRequest.getDescription()));
        //assertTrue(queryVsResponse.getVsdId().equals(vsdId));
    }



    public void VSImodificationTest(String vsiUuid, String vsdId) {
        final String VSI_INSTANTIATION_URL = "/vs/basic/vslcm/vs/"+vsiUuid;

        ModifyVsRequest modifyVsRequest = new ModifyVsRequest(vsiUuid,dspInteraction.getTenant().getUsername(),vsdId);

        log.info("Going to request VSI modification with VSI ID equal to: "+vsiUuid+" with tenant username: "+ dspInteraction.getTenant().getUsername()+" and vsdId equal to "+vsdId);

        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, modifyVsRequest, VSMF_HOST + VSI_INSTANTIATION_URL, HttpMethod.PUT, dspInteraction.getCookiesTenant());
        String statusCode= responseEntity.getStatusCode().toString();
        log.info("Status code modification"+statusCode);
    }



    public void VsiTerminationTest(String vsiUuid) {
        final String VSI_TERMINATION_URL="/vs/basic/vslcm/vs/"+ vsiUuid +"/terminate";
        TerminateVsRequest terminateVsRequest = new TerminateVsRequest(vsiUuid, dspInteraction.getTenant().getUsername());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, terminateVsRequest, VSMF_HOST + VSI_TERMINATION_URL, HttpMethod.POST, dspInteraction.getCookiesTenant());
        String terminationResponse = (String) responseEntity.getBody();
        log.info("Termination response body response: "+terminationResponse);
        log.info("Termination response code: "+responseEntity.getStatusCode());
        assertTrue(responseEntity.getStatusCode()==HttpStatus.OK);
    }


    public void VsiPurgeTest(String vsiUuid) {
        final String VSI_PURGE_URL="/vs/basic/vslcm/vs/"+ vsiUuid;
        PurgeVsRequest purgeVsRequest= new PurgeVsRequest(vsiUuid, dspInteraction.getTenant().getUsername());
        ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, purgeVsRequest, VSMF_HOST + VSI_PURGE_URL, HttpMethod.DELETE, dspInteraction.getCookiesAdmin());
        String purgeResponse = (String) responseEntity.getBody();
        log.info("Termination response body response: "+purgeResponse);
        log.info("Termination response code: "+responseEntity.getStatusCode());
        assertTrue(responseEntity.getStatusCode()==HttpStatus.OK);

    }


    private String onBoardNstBasedOnInstantiationScenario(InstantiationScenario instantiationScenario, EndPointInteraction endPointInteraction){
        String nstUuid=null;
        switch(instantiationScenario){
            case ONLY_PP:
                nstUuid =endPointInteraction.onBoardNST("./json_test/nst_sample_only_pp.json");
                break;

            case PP_NFV_NO_RAN:
                nstUuid =endPointInteraction.onBoardNST("./json_test/nst/nst_sample_pp_and_nfv_no_ran.json");
                break;

            case PP_NFV_RAN:
                nstUuid =endPointInteraction.onBoardNST("./json_test/nst/nst_sample_pp_nfv_ran.json");
                //nstUuid =endPointInteraction.onBoardNST("./json_test/nst/nst_sample_pp_nfv_ran.json_copy");
                break;
            default:
                log.error("Specify a suitable scenario");
                break;
        }
        return nstUuid;
    }

    public void testVerticalServiceInstanceLifeCycle() {

        //NSP1 SIDE
        nspInteraction.loginAdmin();
        nspInteraction.createGroup("NSP_group");
        nspInteraction.createTenant("./json_test/tenant_nsp_sample_a.json");
        nspInteraction.createSLA();
        //nspInteraction.createSLANoResource();
        nspInteraction.loginTenant();

        boolean multidomain = slicerTestConfiguration.isPerformMultidomainInstantiation();
        //NSP2 SIDE
        if (multidomain) {
            nspInteraction2.loginAdmin();
            nspInteraction2.createGroup("NSP_group");
            nspInteraction2.createTenant("./json_test/tenant_nsp_sample_b.json");
            nspInteraction2.createSLA();
            nspInteraction2.createSLANoResource();
            nspInteraction2.loginTenant();
        }

        //DSP SIDE
        dspInteraction.loginAdmin();
        dspInteraction.createGroup("DSP_group");
        dspInteraction.createTenant("./json_test/tenant_sample.json");
        dspInteraction.createTenantExt("./json_test/tenant_notif_sample.json");
        dspInteraction.createTenantExt2("./json_test/tenant_notif_sample_2.json");

        dspInteraction.loginTenant();
        dspInteraction.createRemoteTenantInfo(nspInteraction.getTenant(), NSMF_HOST);

        ArrayList<DomainLayer> domainLayerArrayList = new ArrayList<DomainLayer>();
        domainLayerArrayList.add(new NspDomainLayer("nsp_a", NspNbiType.NEUTRAL_HOSTING));
        Domain domain = new Domain("nsp_a",
                "nsp_a", "nsp a description",
                "nsp_a owner", "nsp_a admin",
                domainLayerArrayList, new HashSet<DomainAgreement>(),
                new DomainInterface(NSMF_IP,
                        8082, true, InterfaceType.HTTP));
        domain.setDomainStatus(DomainStatus.ACTIVE);

        ArrayList<DomainLayer> domainLayerArrayList2 = new ArrayList<DomainLayer>();
        domainLayerArrayList2.add(new NspDomainLayer("nsp_b", NspNbiType.NEUTRAL_HOSTING));
        Domain domainb = new Domain("nsp_b",
                "nsp_b", "nsp_b descr",
                "nsp_b owner", "nsp_b admin",
                domainLayerArrayList2, new HashSet<DomainAgreement>(),
                new DomainInterface(NSMF2_IP,
                        8082, true, InterfaceType.HTTP));
        domainb.setDomainStatus(DomainStatus.ACTIVE);
        dspInteraction.addDomainInfo(domain);
        dspInteraction.addDomainInfo(domainb);


        if (multidomain)
            dspInteraction.createRemoteTenantInfo2(nspInteraction2.getTenant(), NSMF_HOST2);

        dspInteraction.associateLocalTenantWithRemoteTenant();
        if (multidomain)
            dspInteraction.associateLocalTenantWithRemoteTenant2();

        //On NSP1
        nspInteraction.createRemoteTenantInfo(dspInteraction.getTenantNot(), VSMF_HOST);
        nspInteraction.associateLocalTenantWithRemoteTenant();

        if (multidomain) {
            nspInteraction2.createRemoteTenantInfo(dspInteraction.getTenantNot2(), VSMF_HOST);
            nspInteraction2.associateLocalTenantWithRemoteTenant();
        }

        //ON NSP1
        InstantiationScenario instantiationScenario = slicerTestConfiguration.getInstantiationScenario();
        String nstUuid = onBoardNstBasedOnInstantiationScenario(instantiationScenario, nspInteraction);

        //ON NSP2
        if (multidomain) {
            String nstUuid2 = onBoardNstBasedOnInstantiationScenario(instantiationScenario, nspInteraction2);
        }

        //There is the waiting time after the NST on boarding because in the DELL testbed for networking reasons,
        // it takes few seconds to advertise the just on boarded NST.
        //In case this problem are not present, the below time can be set to a lower value.
        try {
            log.info("Waiting " + slicerTestConfiguration.getWaitingTimeAfterNstOnBoarding() + " second(s) after on boarding.");
            Thread.sleep(slicerTestConfiguration.getWaitingTimeAfterNstOnBoarding() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //DSP side
        log.info("Testing VSB and VSD streaming on boarding");

        String vsdId;
        if (instantiationScenario == InstantiationScenario.ONLY_PP) {
            String vsbOnlyPP = onBoardVsbWithNstTransRules(VSMF_HOST, "./json_test/vsb_samples/vsb_only_pp.json");
            vsdId = testVSDOnBoarding(vsbOnlyPP, "./json_test/vsb_samples/vsd_only_pp.json");
        } else {
            String vsbPpAndQos = onBoardVsbWithNstTransRules(VSMF_HOST, "./json_test/vsb_samples/vsb_streaming_pp.json");
            vsdId = testVSDOnBoarding(vsbPpAndQos, "./json_test/vsb_samples/vsd_streaming_with_pp.json");
        }
        final int instantiationTerminationIterations = slicerTestConfiguration.getNumberOfInstantiateTerminateIterations();
        for (int i = 0; i < instantiationTerminationIterations; i++) {

            String vsiUuid = null;
            //String vsiUuid2 = null;
            if (instantiationScenario == InstantiationScenario.ONLY_PP || instantiationScenario == InstantiationScenario.PP_NFV_NO_RAN)
                vsiUuid = VSIinstantionTest(vsdId, "./json_test/vsb_samples/vsi_sample_only_pp.json","test A");

            else {
                vsiUuid = VSIinstantionTest(vsdId, "./json_test/vsb_samples/vsi_sample_Pisa_San_Piero.json","test A");
             //   vsiUuid2 = VSIinstantionTest(vsdId, "./json_test/vsb_samples/vsi_sample_Pisa_San_Piero.json","test B");
            }
            boolean isInstantiated = false;

            while (!isInstantiated) {
                ResponseEntity<?> responseEntityQuery = Util.performHttpRequest(QueryVsResponse.class, null, VSMF_HOST + "/vs/basic/vslcm/vs/" + vsiUuid, HttpMethod.GET, dspInteraction.getCookiesTenant());
                QueryVsResponse queryVsResponse = (QueryVsResponse) responseEntityQuery.getBody();
                log.info("Vertical service instance Status " + queryVsResponse.getStatus().toString());
                if (queryVsResponse.getStatus() == VerticalServiceStatus.INSTANTIATED) {
                    isInstantiated = true;
                } else {
                    log.info("Vertical service instance not instantiated yet. Next check in 70 seconds");
                    try {
                        Thread.sleep(70000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            //Update Qos
            if (slicerTestConfiguration.isPerformQoSActuation()) {
                log.info("Going to perform QoS actuation");
                Map<String, Object> qosConstraints = new HashMap<>();
                Map<String, Object> ranCoreContraint = new HashMap<>();
                Map<String, Object> ranCoreContraint2 = new HashMap<>();
                ranCoreContraint.put("bandIncDir", "DL");
                ranCoreContraint.put("bandIncVal", "10");
                ranCoreContraint.put("bandUnitScale", "MB");

                ranCoreContraint2.put("bandIncDir", "UL");
                ranCoreContraint2.put("bandIncVal", "10");
                ranCoreContraint2.put("bandUnitScale", "MB");
                List<Map<String, Object>> ranArray = new ArrayList<>();
                ranArray.add(ranCoreContraint);

                ranArray.add(ranCoreContraint2);
                qosConstraints.put("ran_core_constraints", ranArray);
                ActuationRequest actuationRequest = new ActuationRequest(vsiUuid, "updateQoS Actuation", "actuationDescription", qosConstraints, "");
            }


            //Redirect
            if (slicerTestConfiguration.isPerformRedirectActuation()) {
                log.info("Going to perform redirect actuation");
                Map<String, String> qosRedirectParameters = new HashMap<>();
                Map<String, Object> qosRedirectParametersParent = new HashMap<>();
                qosRedirectParameters.put("FromServer", "192.168.100.6");
                qosRedirectParameters.put("ToServer", "192.168.100.7");
                qosRedirectParametersParent.put("routes", qosRedirectParameters);
                qosRedirectParametersParent.put("ueIMSI", "208920100001103");
                ActuationRequest actuationRequest = new ActuationRequest(vsiUuid, "actuationName", "actuationDescription", qosRedirectParametersParent, "");
                ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, actuationRequest, VSMF_HOST + "/vs/basic/vslcm/e2ens/" + vsiUuid + "/actuate", HttpMethod.POST, dspInteraction.getCookiesTenant());
            }

            //Traffic redirect
            boolean trafficRedirect = false;
            if (trafficRedirect) {
                log.info("Going to perform traffic redirect");
                Map<String, Object> trafficRedirectParams = new HashMap<>();
                trafficRedirectParams.put("VDU_NAME", "my_vdu");
                trafficRedirectParams.put("RESOURCE_ID", "208920100001103");
                trafficRedirectParams.put("VNF_MEMBER_INDEX", "9999");
                ActuationRequest actuationRequest = new ActuationRequest(vsiUuid, "trafficRedirection", "Traffic Redirection Actuation", trafficRedirectParams, "");
                ResponseEntity<?> responseEntity = Util.performHttpRequest(String.class, actuationRequest, VSMF_HOST + "/vs/basic/vslcm/e2ens/" + vsiUuid + "/actuate", HttpMethod.POST, dspInteraction.getCookiesTenant());
            }


            //Handover
            if (slicerTestConfiguration.isPerformHandoverActuation()) {
                log.info("Going to perform handover actuation");
                Map<String, Object> handoverParameters = new HashMap<>();
                handoverParameters.put("sid", 1234567);
                handoverParameters.put("ueid", "208950000000003");
                handoverParameters.put("tid", 9876543);
                ActuationRequest actuationRequestHandover = new ActuationRequest(vsiUuid, "handoverActuation", "handoverActuation", handoverParameters, "");
                Util.performHttpRequest(String.class, actuationRequestHandover, VSMF_HOST + "/vs/basic/vslcm/e2ens/" + vsiUuid + "/actuate", HttpMethod.POST, dspInteraction.getCookiesTenant());
            }

            if (slicerTestConfiguration.isPerformSetSliceRanPriorityActuation()) {
                //Set ran slice priority
                log.info("Going to perform Set ran slice priority");
                Map<String, Object> ranSlicePriorityParams = new HashMap<>();
                ranSlicePriorityParams.put("ul", 9);
                ranSlicePriorityParams.put("dl", 1);
                Map<String, Object> ranSlicePriorityParentParams = new HashMap<>();
                ranSlicePriorityParentParams.put("ran_slice_priority", ranSlicePriorityParams);
                ActuationRequest actuationRequestRanPriority = new ActuationRequest(vsiUuid, "ran priority actuation", "ran priority actuation", ranSlicePriorityParentParams, "");
                Util.performHttpRequest(String.class, actuationRequestRanPriority, VSMF_HOST + "/vs/basic/vslcm/e2ens/" + vsiUuid + "/actuate", HttpMethod.POST, dspInteraction.getCookiesTenant());
            }


            if (slicerTestConfiguration.isPerformSetSliceRanPriorityActuation()) {
                //Set ran slice priority
                log.info("Going to perform Set ran slice priority");
                Map<String, Object> ranSlicePriorityParams = new HashMap<>();
                ranSlicePriorityParams.put("ul", 9);
                ranSlicePriorityParams.put("dl", 1);
                Map<String, Object> ranSlicePriorityParentParams = new HashMap<>();
                ranSlicePriorityParentParams.put("ran_slice_priority", ranSlicePriorityParams);
                ActuationRequest actuationRequestRanPr = new ActuationRequest(vsiUuid, "ran priority actuation", "ran priority actuation", ranSlicePriorityParentParams, "");
                Util.performHttpRequest(String.class, actuationRequestRanPr, VSMF_HOST + "/vs/basic/vslcm/e2ens/" + vsiUuid + "/actuate", HttpMethod.POST, dspInteraction.getCookiesTenant());
            }

            if (slicerTestConfiguration.isPerformVerticalServiceTermination()) {
                log.info("Going to terminate VSI");
                VsiTerminationTest(vsiUuid);
                //VsiTerminationTest(vsiUuid2);
                //VSImodificationTest();
                boolean isTerminated = false;
                boolean isTerminated2 = true;

                while (!isTerminated || !isTerminated2) {
                    if (!isTerminated) {
                        ResponseEntity<?> responseEntityQuery = Util.performHttpRequest(QueryVsResponse.class, null, VSMF_HOST + "/vs/basic/vslcm/vs/" + vsiUuid, HttpMethod.GET, dspInteraction.getCookiesTenant());
                        QueryVsResponse queryVsResponse = (QueryVsResponse) responseEntityQuery.getBody();
                        log.info("Vertical service instance Status " + queryVsResponse.getStatus().toString());
                        if (queryVsResponse.getStatus() == VerticalServiceStatus.TERMINATED) {
                            log.info("VSI with UUID "+vsiUuid+"  has been terminated");
                            isTerminated = true;
                        }
                    }
                   /* if (!isTerminated2) {
                        ResponseEntity<?> responseEntityQuery = Util.performHttpRequest(QueryVsResponse.class, null, VSMF_HOST + "/vs/basic/vslcm/vs/" + vsiUuid2, HttpMethod.GET, dspInteraction.getCookiesTenant());
                        QueryVsResponse queryVsResponse = (QueryVsResponse) responseEntityQuery.getBody();
                        log.info("Vertical service instance Status " + queryVsResponse.getStatus().toString());
                        if (queryVsResponse.getStatus() == VerticalServiceStatus.TERMINATED) {
                            log.info("VSI with UUID "+vsiUuid2+"  has been terminated");
                            isTerminated2 = true;
                        }
                    }
                */
                    if (!isTerminated || !isTerminated2) {
                        log.info("One of the Vertical services instance not terminated yet. Next check in 30 seconds");
                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
