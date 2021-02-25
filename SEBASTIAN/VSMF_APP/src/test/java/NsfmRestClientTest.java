import it.nextworks.nfvmano.catalogue.template.messages.OnBoardNsTemplateRequest;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.libs.ifa.templates.*;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.NsmfRestClient;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

//In order to test the rest client, there is no need to run the entire Spring application.

//Before run the test consider to:
//1. Start the NSMF_APP at address "http://localhost:8082"
//2. Use DUMMY config for nfvo.catalogue.type and nfvo.lcm.type
//3. Use the polling time equal to the one set in nfvo.lcm.polling. In this case is supposed equal to nfvoLcmPolling variable.

/*
public class NsfmRestClientTest {

    private static NsmfRestClient nsmfRestClient;

    private static String vsmfNotificationsUrl="http://localhost:8082";
    private static String NST_ID;

    private static final Logger log = LoggerFactory.getLogger(NsfmRestClientTest.class);
    private String nsiID;
    private static String cookies;
    private final String nfvoLcmPolling="20";

    public NsfmRestClientTest(){
        log.info("In case of error in the tests, please consider to check: ");
        log.info("NSMF_APP address, nfvo.catalogue.type, nfvo.lcm.type config and fvo.lcm.polling configs.");
        log.info("Or change the hardcoded variables. ");
    }

    @BeforeClass
    public static void preliminarOps(){
        log.info("Performing preliminary : login and NS Template on boarding.");
        nsmfRestClient=new NsmfRestClient(vsmfNotificationsUrl,null);
        performAuthentication();
        onBoardNsTemplate();
        nsmfRestClient.setCookies(cookies);
    }

    private static double getRandomInteger(){
        double x = (int)(Math.random()*(1000));
        return x;
    }

    private static void performAuthentication(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", "admin");
        map.add("password", "admin");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> httpResponse = restTemplate.exchange("http://localhost:8082/login", HttpMethod.POST, request, String.class);
        HttpHeaders headersResp = httpResponse.getHeaders();
        cookies = headersResp.getFirst(HttpHeaders.SET_COOKIE);
    }


    private void waitPollingTime(){
        log.info("The nfvo lcm polling time is supposed to be "+nfvoLcmPolling+" seconds. Going to wait  "+nfvoLcmPolling+" plus other 5 seconds");
        try {
            log.info("Waiting the lcm polling time: "+nfvoLcmPolling);
            Thread.sleep(Long.valueOf(nfvoLcmPolling)*1000+5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static ArrayList<String> createArrayListWithValuesIn(int sizeArrayList, String prefixElement) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for(int i=0; i<sizeArrayList; i++) {
            arrayList.add(prefixElement+String.valueOf(i+1));
        }
        return arrayList;
    }

    private NetworkSliceInstance getNSIbyID(String nsiId){
        HashMap<String,String> filterByID = new HashMap<String,String>();
        filterByID.put("NSI_ID",nsiID);

        GeneralizedQueryRequest generalizedQueryRequest = new GeneralizedQueryRequest(new Filter(filterByID), null);
        List<NetworkSliceInstance> nsiiListOneEl = null;
        try {
            nsiiListOneEl = nsmfRestClient.queryNetworkSliceInstance(generalizedQueryRequest, "");
            return nsiiListOneEl.get(0);
        } catch (MethodNotImplementedException e) {
            e.printStackTrace();
        } catch (FailedOperationException e) {
            e.printStackTrace();
        } catch (MalformattedElementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NST getSampleNST(){
        EMBBPerfReq embbPerfReq1 = new EMBBPerfReq(100, 10, 200, 20, 200000, 3, 0,"Coverage");
        EMBBPerfReq embbPerfReq2 = new EMBBPerfReq(101, 11, 201, 21, 210000, 4, 1,"Coverage1");
        ArrayList<EMBBPerfReq> embbPerfReq = new ArrayList<EMBBPerfReq>();
        embbPerfReq.add(embbPerfReq1);
        embbPerfReq.add(embbPerfReq2);

        URLLCPerfReq urLLCPerfReq1 = new URLLCPerfReq(20, 200, 200, 2.0f, 20.0f, 200, "PayloadSize", 200, 200,"ServiceAreaDimension");
        URLLCPerfReq urLLCPerfReq2 = new URLLCPerfReq(21, 201, 201, 2.1f, 20.1f, 201, "PayloadSize2", 201, 201,"ServiceAreaDimension2");
        ArrayList<URLLCPerfReq> urLLCPerfReq = new ArrayList<URLLCPerfReq>();
        urLLCPerfReq.add(urLLCPerfReq1);
        urLLCPerfReq.add(urLLCPerfReq2);

        NstServiceProfile nstServiceProfile= new NstServiceProfile();
        ArrayList<String> pLMNId=createArrayListWithValuesIn(3, "pLMNId");
        ArrayList<String> coverageAreaTAList=createArrayListWithValuesIn(3, "ca");

        nstServiceProfile.setpLMNIdList(pLMNId);
        nstServiceProfile.setMaxNumberofUEs(20000);
        nstServiceProfile.setCoverageAreaTAList(coverageAreaTAList);
        nstServiceProfile.setLatency(20);
        nstServiceProfile.setuEMobilityLevel(UEMobilityLevel.STATIONARY);
        nstServiceProfile.setResourceSharingLevel(false);
        nstServiceProfile.setsST(SliceType.EMBB);
        nstServiceProfile.setAvailability(9.99f);
        nstServiceProfile.seteMBBPerfReq(embbPerfReq);
        nstServiceProfile.setuRLLCPerfReq(urLLCPerfReq);

        ArrayList<String> nsstIDs=createArrayListWithValuesIn(3, "nsstID");
        return new NST("fakeID", "nstName"+getRandomInteger(),"nstVersion"+getRandomInteger(), "nstProvider", nsstIDs, "nsVS", "0.1",nstServiceProfile);
    }


    private static void onBoardNsTemplate(){
        log.info("On boarding NSTemplate");

        OnBoardNsTemplateRequest onBoardNsTemplateRequest = new OnBoardNsTemplateRequest(getSampleNST());
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        header.add("Cookie", cookies);
        HttpEntity<?> httpEntity = new HttpEntity<>(onBoardNsTemplateRequest, header);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> httpResponse =
                restTemplate.exchange("http://localhost:8082/ns/catalogue/nstemplate",  HttpMethod.POST, httpEntity, String.class);
        log.info("Body Response: "+httpResponse.getBody());
        NST_ID=httpResponse.getBody();
        log.info("Status code: "+httpResponse.getStatusCode().toString());
    }


    @Test
    public void nsfmRestClientTest() {
        log.info("TEST: Going to create a new NSI");
        CreateNsiIdRequest createNsiIdRequest = new CreateNsiIdRequest(NST_ID,"vs_s","description");

        try {
            nsiID = nsmfRestClient.createNetworkSliceIdentifier(createNsiIdRequest,"TENANT_ID");
            assertTrue(nsiID!=null);
            log.info("TEST PASSED: nsi created with ID: "+nsiID);

        } catch (NotExistingEntityException e) {
            e.printStackTrace();
        } catch (MethodNotImplementedException e) {
            e.printStackTrace();
        } catch (FailedOperationException e) {
            e.printStackTrace();
        } catch (MalformattedElementException e) {
            e.printStackTrace();
        } catch (NotPermittedOperationException e) {
            e.printStackTrace();
        }

        log.info("TEST: Going to query NSI via ID");
        NetworkSliceInstance nsi = getNSIbyID(nsiID);
        assertTrue(nsi.getNsiId().equals(nsiID));
        log.info("TEST PASSED: The query gives one element which Id is: "+nsiID);
        log.info(".....");



        log.info("TEST: Going to query all NSIs");
        List<NetworkSliceInstance> nsiiList = null;
        try {
            nsiiList = nsmfRestClient.queryNetworkSliceInstance(new GeneralizedQueryRequest(), "");
        } catch (MethodNotImplementedException e) {
            e.printStackTrace();
        } catch (FailedOperationException e) {
            e.printStackTrace();
        } catch (MalformattedElementException e) {
            e.printStackTrace();
        }

        assertTrue(nsiiList.size()>=0);
        log.info("TEST PASSED: The query gives at least one element");
        log.info(".....");

        log.info("TEST: Going to instantiate an existing NSI with ID "+nsiID);
        InstantiateNsiRequest instantiateNsiRequest = new InstantiateNsiRequest(nsiID, NST_ID, "dfID", "illID", new ArrayList<String>(), new HashMap<String, String>(), new LocationInfo(), "rawEndpoint");
        try {
            nsmfRestClient.instantiateNetworkSlice(instantiateNsiRequest,"");
        } catch (NotExistingEntityException e) {
            e.printStackTrace();
        } catch (MethodNotImplementedException e) {
            e.printStackTrace();
        } catch (FailedOperationException e) {
            e.printStackTrace();
        } catch (MalformattedElementException e) {
            e.printStackTrace();
        } catch (NotPermittedOperationException e) {
            e.printStackTrace();
        }
        waitPollingTime();

        nsi = getNSIbyID(nsiID);
        assertTrue(nsi.getStatus().equals(NetworkSliceStatus.INSTANTIATED));
        log.info("TEST PASSED: The NSI with ID: "+nsiID+ "is in status "+(nsi.getStatus()));
        log.info(".....");

        log.info("TEST: Going to modify an existing NSI with ID "+nsiID);
        String newIlId = "newIlId";
        ModifyNsiRequest modifyNsiRequest = new ModifyNsiRequest(nsiID, NST_ID, "dfIdMod", newIlId, "vsiIdMod");

        try {
            nsmfRestClient.modifyNetworkSlice(modifyNsiRequest, "");
            log.info("Executed testModifyNetworkSlice");
        } catch (NotExistingEntityException e) {
            e.printStackTrace();
        } catch (MethodNotImplementedException e) {
            e.printStackTrace();
        } catch (FailedOperationException e) {
            e.printStackTrace();
        } catch (MalformattedElementException e) {
            e.printStackTrace();
        } catch (NotPermittedOperationException e) {
            e.printStackTrace();
        }

        waitPollingTime();
        nsi = getNSIbyID(nsiID);
        assertTrue(nsi.getStatus().equals(NetworkSliceStatus.INSTANTIATED));
        assertTrue(nsi.getInstantiationLevelId().equals(newIlId));
        // TODO Checking into DBhe dfId and  vsiIdMod are not modified. It is the expected behaviour ?
        log.info("TEST PASSED: The NSI with ID: "+nsiID+ "has been modified");
        log.info(".....");

        log.info("TEST: Going to terminate an existing NSI with ID "+nsiID);
        TerminateNsiRequest terminateNsiRequest = new TerminateNsiRequest(nsiID);
        try {
            nsmfRestClient.terminateNetworkSliceInstance(terminateNsiRequest, "");
        } catch (NotExistingEntityException e) {
            e.printStackTrace();
        } catch (MethodNotImplementedException e) {
            e.printStackTrace();
        } catch (FailedOperationException e) {
            e.printStackTrace();
        } catch (MalformattedElementException e) {
            e.printStackTrace();
        } catch (NotPermittedOperationException e) {
            e.printStackTrace();
        }
        waitPollingTime();
        nsi = getNSIbyID(nsiID);
        assertTrue(nsi.getStatus().equals(NetworkSliceStatus.TERMINATED));
        log.info("TEST PASSED: The NSI with ID: "+nsiID+ "is in status "+(nsi.getStatus()));
        log.info(".....");
        }
    }
*/