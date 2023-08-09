package it.nextworks.nfvmano.sbi.cnc.rest;

import it.nextworks.nfvmano.libs.vs.common.utils.BaseRestClient;
import it.nextworks.nfvmano.sbi.cnc.GNbConfiguration;
import it.nextworks.nfvmano.sbi.cnc.UPFsliceAssociationCnc.UpfSliceAssociationCnc;
import it.nextworks.nfvmano.sbi.cnc.messages.NetworkSliceCNC;
import it.nextworks.nfvmano.sbi.cnc.operator.Operator;
import it.nextworks.nfvmano.sbi.cnc.subProfile.SubProfile;
import it.nextworks.nfvmano.sbi.cnc.subscriberGroup.SubscriberGroup;
import it.nextworks.nfvmano.sbi.cnc.subscribersManagement.SubscriberInfo;
import it.nextworks.nfvmano.sbi.cnc.subscribersManagement.SubscriberInfoNew;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;

public class CNCrestClient{

    //TODO use rest client of common libs
    private static final Logger LOG = LoggerFactory.getLogger(CNCrestClient.class);

    private String ipCNC;
    private int portCNC;
    private final String basePathSliceManagement = "/api/v1.0/network-slice/slice-instance";

    private final String fullPathSliceManagement;

    private final String basePathSubscribersManagement = "/api/v1.0/cnc-subscriber";
    private final String fullPathSubscribersManagement;


    private final String basePathSubscribersManagementNew = "/api/v1.0/cnc-subscriber-management";

    private final String fullPathSubscribersManagementNew;

    private final String basePathOperatorManagement = "/api/v1.0/cnc-operator";
    private final String fullPathOperatorManagement;

    private final String gnbManagement = "/api/v1.0/gnb-default-config";

    private final String fullgnbManagement;

    private final String upfDnnList = "/api/v1.0/network-slice/upf-dnn-list";

    private final String fullUpfDnnList;

    private final String upfProfleUpdate = "/api/v1.0/cnc-config/upf-profile-update";

    private final String fullUpfProfleUpdate;

    private final String subscriptionProfile = "/api/v1.0/cnc-configuration/cnc-subscription-profile";

    private final String fullSubscriptionProfile;

    private final String subscriberGroup ="/api/v1.0/cnc-configuration/cnc-user-group";

    private final String fullSubscriberGroup;

    private String cookies;
    private RestTemplate restTemplate;

    private BaseRestClient baseRestClient;

    public CNCrestClient(String ipCNC, int portCNC){
        this.ipCNC = ipCNC;
        this.portCNC = portCNC;
        this.fullPathSliceManagement = "https://"+ipCNC+":"+portCNC+ basePathSliceManagement;
        this.fullPathSubscribersManagement = "https://"+ipCNC+":"+portCNC+ basePathSubscribersManagement;
        this.fullPathOperatorManagement = "https://"+ipCNC+":"+portCNC+ basePathOperatorManagement;
        this.fullgnbManagement = "https://"+ipCNC+":"+portCNC+ gnbManagement;
        this.fullUpfDnnList = "https://"+ipCNC+":"+portCNC+ upfDnnList;
        this.fullUpfProfleUpdate = "https://"+ipCNC+":"+portCNC+ upfProfleUpdate;
        this.fullSubscriptionProfile = "https://"+ipCNC+":"+portCNC+ subscriptionProfile;
        this.fullSubscriberGroup = "https://"+ipCNC+":"+portCNC+ subscriberGroup;
        this.fullPathSubscribersManagementNew = "https://"+ipCNC+":"+portCNC+ basePathSubscribersManagementNew;

        baseRestClient = new BaseRestClient();
        try {
            this.restTemplate = getRestTemplateNoCertificateCheck();
            baseRestClient = new BaseRestClient(null, this.restTemplate);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        cookies = null; //No need for cookies because neither no auth required no session required. Keep it if the CNC server will be updated
    }


    public String getIpCNC(){
        return ipCNC;
    }

    public int getPortCNC(){
        return portCNC;
    }
    //The CNC server uses the certificate. This method is used for getting a rest template without checking the certificate
    public RestTemplate getRestTemplateNoCertificateCheck() throws GeneralSecurityException {

        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();

        BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(
                socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                .setConnectionManager(connectionManager).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        return restTemplate;
    }

    public boolean createNetworkSlice(NetworkSliceCNC networkSliceCNC){
        String sliceName = networkSliceCNC.sliceName;
        ResponseEntity<String> httpResponse= baseRestClient.performHTTPRequest(networkSliceCNC, fullPathSliceManagement, HttpMethod.POST);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error creating Network Slice "+ sliceName, "Network Slice "+ sliceName +" correctly created", HttpStatus.OK);
        return bodyResponse!=null;
    }


    public boolean deleteNetworkSlice(String networkSliceId){
        ResponseEntity<String> httpResponse=baseRestClient.performHTTPRequest(null, fullPathSliceManagement +"/"+networkSliceId, HttpMethod.DELETE);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error deleting Network Slice "+networkSliceId, "Network Slice "+networkSliceId+" correctly deleting", HttpStatus.OK);
        return bodyResponse!=null;
    }

    public boolean updateNetworkSlice(NetworkSliceCNC networkSliceCNC){
        String sliceName = networkSliceCNC.sliceName;
        ResponseEntity<String> httpResponse= baseRestClient.performHTTPRequest(networkSliceCNC, fullPathSliceManagement+"/"+sliceName, HttpMethod.PUT);

        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error updating Network Slice", "Network Slice "+ sliceName +" correctly updated", HttpStatus.OK);
        return bodyResponse!=null;
    }

    public String getNetworkSlice(String networkSliceId){
        ResponseEntity<String> httpResponse= baseRestClient.performHTTPRequest(null, fullPathSliceManagement +"/"+networkSliceId, HttpMethod.GET);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error getting Network Slice "+networkSliceId, "Network Slice "+networkSliceId+" correctly get", HttpStatus.OK);
        return bodyResponse;
    }

    public String getNetworkSlices(){
        ResponseEntity<String> httpResponse= baseRestClient.performHTTPRequest(null, fullPathSliceManagement, HttpMethod.GET);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error getting Network Slices", "Network Slices correctly get", HttpStatus.OK);
        return bodyResponse;
    }

    public String addSubscriber(SubscriberInfo subscriberInfo){
        ResponseEntity<String> httpResponse=  baseRestClient.performHTTPRequest(subscriberInfo, fullPathSubscribersManagement, HttpMethod.POST);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error registering subscriber", "Subscriber correctly registered", HttpStatus.ACCEPTED);
        return bodyResponse;
    }

    public String addSubscriberInfoNew(SubscriberInfoNew subscriberInfoNew){
        ResponseEntity<String> httpResponse=  baseRestClient.performHTTPRequest(subscriberInfoNew, fullPathSubscribersManagementNew, HttpMethod.POST);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error registering subscriber", "Subscriber correctly registered", HttpStatus.ACCEPTED);
        return bodyResponse;
    }

    public String updateSubscriberInfo(SubscriberInfoNew subscriberInfoNew){
        ;
        ResponseEntity<String> httpResponse=  baseRestClient.performHTTPRequest(subscriberInfoNew, fullPathSubscribersManagementNew+"/"+subscriberInfoNew.imsi, HttpMethod.PUT);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error updating subscriber info", "Subscriber info correctly updated", HttpStatus.ACCEPTED);
        return bodyResponse;
    }

    public String getSubscriberList(){
        ResponseEntity<String> httpResponse=  baseRestClient.performHTTPRequest(null, fullPathSubscribersManagement, HttpMethod.GET);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error getting subscriber list", "Subscriber list correctly get", HttpStatus.OK);
        return bodyResponse;
    }

    public String removeSubscriber(String imsiSubscriber){
        ResponseEntity<String> httpResponse=  baseRestClient.performHTTPRequest(null, fullPathSubscribersManagement+"/"+imsiSubscriber, HttpMethod.DELETE);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error removing subscriber with IMSI "+imsiSubscriber, "Subscriber correctly removed", HttpStatus.OK);
        return bodyResponse;
    }

    public String addOperator(Operator operator){
        ResponseEntity<String> httpResponse=  baseRestClient.performHTTPRequest(operator, fullPathOperatorManagement, HttpMethod.POST);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error adding operator", "Operator successfully added", HttpStatus.OK);
        return bodyResponse;
    }

    public String getOperators(){
        ResponseEntity<String> httpResponse=  baseRestClient.performHTTPRequest(null, fullPathOperatorManagement, HttpMethod.GET);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error getting operators", "Operators successfully get", HttpStatus.OK);
        return bodyResponse;
    }

    public String addgNB(GNbConfiguration gNbConfiguration){
        ResponseEntity<String> httpResponse=  baseRestClient.performHTTPRequest(gNbConfiguration, fullgnbManagement, HttpMethod.POST);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error configuring gNB", "configuring gNB successfully", HttpStatus.OK);
        return bodyResponse;
    }

    public String getUpfsInfo(){
        ResponseEntity<String> httpResponse= baseRestClient.performHTTPRequest(null, fullUpfDnnList, HttpMethod.GET);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error getting UPF info", "UPF info correctly got", HttpStatus.OK);
        return bodyResponse;
    }

    public String associateUpfToSlice(String upfIdentifier, UpfSliceAssociationCnc upfSliceAssociationCnc){

        ResponseEntity<String> httpResponse= baseRestClient.performHTTPRequest(upfSliceAssociationCnc, fullUpfProfleUpdate+"/"+upfIdentifier, HttpMethod.PUT);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error updating UPF-slice association", "Successful update UPF-slice association", HttpStatus.OK);
        return bodyResponse;
    }

    public String createSubscriptionProfile(SubProfile subProfile){
        ResponseEntity<String> httpResponse= baseRestClient.performHTTPRequest(subProfile, fullSubscriptionProfile, HttpMethod.POST);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error creating Subscription profile", "Successful creating Subscription profile", HttpStatus.OK);
        return bodyResponse;
    }


    public String createSubscriberGroup(SubscriberGroup subscriberGroup){
        ResponseEntity<String> httpResponse= baseRestClient.performHTTPRequest(subscriberGroup, fullSubscriberGroup, HttpMethod.POST);
        String bodyResponse =  baseRestClient.manageHTTPResponse(httpResponse, "Error creating Subscriber group", "Successful creating  Subscriber group", HttpStatus.OK);
        return bodyResponse;
    }

}
