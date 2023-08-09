package it.nextworks.nfvmano.core.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.core.recordIM.SubscriberGroupInfo;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.core.recordIM.SubscriberListForSlice;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.Subscriber;
import it.nextworks.nfvmano.sbi.cnc.messages.NetworkSliceCNC;
import it.nextworks.nfvmano.sbi.cnc.operator.Operator;
import it.nextworks.nfvmano.sbi.cnc.rest.CNCrestClient;
import it.nextworks.nfvmano.sbi.cnc.subProfile.*;
import it.nextworks.nfvmano.sbi.cnc.subscriberGroup.SubscribedUeAmbr;
import it.nextworks.nfvmano.sbi.cnc.subscriberGroup.SubscriberGroup;
import it.nextworks.nfvmano.sbi.cnc.subscriberGroup.Subscription;
import it.nextworks.nfvmano.sbi.cnc.subscribersManagement.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SubscriberService {
    private static final Logger LOG = LoggerFactory.getLogger(CoreNetworkSliceService.class);

    private HashMap<String,String> imsiIpMap;

    @Value("${nssmf.cnc.ip}")
    private String cncIp;

    @Value("${nssmf.cnc.port}")
    private int cncPort;

    private final String OP ="f964ba9478cdc6c72eaf1e95dbc6674a";

    private final String K  = "000102030405060708090A0B0C0D0E0F";
    @Autowired
    CoreNetworkSliceService coreNetworkSliceService;

    private List<String> subProfileList;

    private HashMap<String, SubscriberGroupInfo> subGroupInfoMap;

    public SubscriberService(){
        subProfileList = new ArrayList<>();
        subGroupInfoMap = new HashMap<>();
        imsiIpMap = new HashMap<>();
        imsiIpMap.put("999991000000001","10.5.3.10");
        imsiIpMap.put("999991000000002","10.5.3.11");
        imsiIpMap.put("999991000000003","10.5.3.12");
        imsiIpMap.put("999991000000004","10.5.3.13");

    }


    private void printObjectJsonFormat(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(object);
            LOG.info("Payload to send");
            LOG.info(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
    public String createSubscriberProfile(String subscriberProfileId){

        CNCrestClient cnCrestClient = new  CNCrestClient(cncIp, cncPort);
        SubProfile subProfile = new SubProfile();
        subProfile._id=subscriberProfileId;
        subProfile.dnn="internet";

        Arp arp = new Arp();
        arp.priorityLevel = 15;
        arp.preemptCap = "MAY_PREEMPT";
        arp.preemptVuln = "NOT_PREEMPTABLE";

        _5gQosProfile qosProfile = new _5gQosProfile();
        qosProfile._5qi = 9;
        qosProfile.arp = arp;
        qosProfile.priorityLevel = 10;

        SessionAmbr sessionAmbr = new SessionAmbr();
        sessionAmbr.uplink = "500 Mbps";
        sessionAmbr.downlink = "500 Mbps";

        ArrayList allowedSessionTypeList = new ArrayList();
        allowedSessionTypeList.add("IPV4V6");
        allowedSessionTypeList.add("UNSTRUCTURED");
        allowedSessionTypeList.add("ETHERNET");
        PduSessionTypes pduSessionTypes = new PduSessionTypes();
        pduSessionTypes.allowedSessionTypes = allowedSessionTypeList;
        pduSessionTypes.defaultSessionType = "IPV4";
        subProfile.pduSessionTypes = pduSessionTypes;

        SscModes sscModes = new SscModes();
        ArrayList allowedSscModes = new ArrayList();
        allowedSscModes.add("SSC_MODE_2");
        allowedSscModes.add("SSC_MODE_3");

        sscModes.allowedSscModes = allowedSscModes;
        sscModes.defaultSscMode ="SSC_MODE_1";

        subProfile.sscModes = sscModes;
        subProfile.sessionAmbr = sessionAmbr;
        subProfile._5gQosProfile = qosProfile;

        printObjectJsonFormat(subProfile);
        String result  = cnCrestClient.createSubscriptionProfile(subProfile);
        if(result!=null)
            subProfileList.add(subscriberProfileId);
        return result;
    }
    private CNCrestClient getCncRestClient(String coreInstanceId) throws NotExistingEntityException {
        CNCrestClient cncRestClient = coreNetworkSliceService.getCncRestClient(coreInstanceId);
        if(cncRestClient==null){
            throw new NotExistingEntityException("Core Instance with ID "+coreInstanceId+" not found");
        }
        return cncRestClient;
    }

    public List<SubscriberListForSlice> getAllSubscribers(String coreInstanceId) throws IOException, NotExistingEntityException {
        LOG.info("Getting all subscriber information");
        List<SubscriberListForSlice> subscriberListForSliceList = new ArrayList<>();
        CNCrestClient cncRestClient = getCncRestClient(coreInstanceId);

        String subscriberListString = cncRestClient.getSubscriberList();
        Map<String, List<Subscriber>> sliceSubsListMap = new HashMap<>();
        if(subscriberListString==null){
            return subscriberListForSliceList;
        }
        ObjectMapper om = new ObjectMapper();
        CoreSubscriberListInfo coreSubscriberListInfo =  om.readValue(subscriberListString, CoreSubscriberListInfo.class);

        for(Data1 data1: coreSubscriberListInfo.data1){
            Subscriber subscriber = new Subscriber(data1.imsi, data1.k);
            if(sliceSubsListMap.get(data1.slice_name)==null){
                List<Subscriber> subscribers = new ArrayList<>();
                subscribers.add(subscriber);
                sliceSubsListMap.put(data1.slice_name, subscribers);
            }
            else{
                sliceSubsListMap.get(data1.slice_name).add(subscriber);
            }
        }
        for(String sliceName: sliceSubsListMap.keySet()){
            List<Subscriber> subscribers = sliceSubsListMap.get(sliceName);
            SubscriberListForSlice subscriberListForSlice = new SubscriberListForSlice();
            subscriberListForSlice.setSliceName(sliceName);
            subscriberListForSlice.setSubscriberList(subscribers);
            subscriberListForSliceList.add(subscriberListForSlice);
        }
        return subscriberListForSliceList;
    }

    public void registerSubscribersForSlice(String coreInstanceId, String sliceName, SubscriberListForSlice subscriberListForSlice) throws MalformattedElementException, NotExistingEntityException {
        LOG.info("Register subscribers for slice");

        if(!coreNetworkSliceService.isCoreSliceExisting(coreInstanceId, sliceName)){
            throw new NotExistingEntityException("Cannot register any subscribers: either core instance or core network slice not found");
        }
        CNCrestClient cncRestClient = getCncRestClient(coreInstanceId);

        for(Subscriber subscriber : subscriberListForSlice.getSubscribers()){
            SubscriberInfo subscriberInfo = fromSubscriberToSubscriberInfo(subscriber, sliceName);
            String bodyResponse = cncRestClient.addSubscriber(subscriberInfo);
            if(bodyResponse==null){
                throw new MalformattedElementException("Error registering subscriber with IMSI" + subscriber.getImsi()+ ". It might be already registered");
            }
        }
    }
    public String registerSubscriber(String imsi, String subscriberGroupName){
        CNCrestClient cnCrestClient = new  CNCrestClient(cncIp, cncPort);

        LOG.info("Trying of creating operator config");
        Operator operator = new Operator();
        operator.mcc = "999";
        operator.mnc = "99";
        operator.amf = "8000";
        operator.name = "operator";
        operator.op = OP;
        printObjectJsonFormat(operator);
        LOG.info("Registering test operator");
        cnCrestClient.addOperator(operator);

        SubscriberInfoNew subscriberInfoNew = new SubscriberInfoNew();
        subscriberInfoNew.imsi = imsi;
        if(imsiIpMap.get(imsi)!=null)
        {
            subscriberInfoNew.ue_static_ipv4_addr = imsiIpMap.get(imsi);
        }
        subscriberInfoNew.k = K;
        subscriberInfoNew.groupName = subscriberGroupName;
        subscriberInfoNew.sqn = "000000000000";
        subscriberInfoNew.msisdn = imsi;
        printObjectJsonFormat(subscriberInfoNew);
        return cnCrestClient.addSubscriberInfoNew(subscriberInfoNew);
    }

    public String updateSubscriber(String imsi, String subscriberGroupName){
        CNCrestClient cnCrestClient = new  CNCrestClient(cncIp, cncPort);


        LOG.info("Trying of creating operator config");
        Operator operator = new Operator();
        operator.mcc = "999";
        operator.mnc = "99";
        operator.amf = "8000";
        operator.name = "operator";
        operator.op = OP;
        printObjectJsonFormat(operator);
        LOG.info("Registering test operator");
        cnCrestClient.addOperator(operator);

        SubscriberInfoNew subscriberInfoNew = new SubscriberInfoNew();
        subscriberInfoNew.imsi = imsi;
        if(imsiIpMap.get(imsi)!=null)
        {
            subscriberInfoNew.ue_static_ipv4_addr = imsiIpMap.get(imsi);
        }
        subscriberInfoNew.k = K;
        subscriberInfoNew.groupName = subscriberGroupName;
        subscriberInfoNew.sqn = "000000000000";
        subscriberInfoNew.msisdn = imsi;
        printObjectJsonFormat(subscriberInfoNew);
        return cnCrestClient.updateSubscriberInfo(subscriberInfoNew);
    }


    private SubscriberInfo fromSubscriberToSubscriberInfo(Subscriber subscriber, String sliceName){
        String imsi = subscriber.getImsi();
        String k = subscriber.getK();
        Subscriberprofile subscriberprofile = new Subscriberprofile();
        subscriberprofile.imsi = imsi;
        subscriberprofile.k = k;
        subscriberprofile.slice_name = sliceName;

        SubscriberInfo subscriberInfo = new SubscriberInfo();
        subscriberInfo.subscriberprofile = subscriberprofile;
        return subscriberInfo;
    }


    public void removeSubscriber(String coreInstanceId, String imsiSubscriber) throws NotExistingEntityException {
        CNCrestClient cncRestClient = getCncRestClient(coreInstanceId);
        String bodyResponse = cncRestClient.removeSubscriber(imsiSubscriber);
        if(bodyResponse==null){
            throw new NotExistingEntityException("Subscriber with IMSI "+imsiSubscriber+ "not found");
        }

    }

    private String fromNssiToNetworkSliceCoreId(String nssiId) throws NotExistingEntityException, IOException {
        List<NetworkSliceCNC> networkSliceCNCList=  coreNetworkSliceService.getAllCoreNetworkSliceInformation(nssiId);
        return networkSliceCNCList.get(0).sliceName;
    }

    boolean isUeAlreadyRegistered(String imsi){
        for(String subGroupId: subGroupInfoMap.keySet()){
            for(String currentImsi: subGroupInfoMap.get(subGroupId).getUeImsis()){
                if(currentImsi.equals(imsi))
                    return true;
            }
        }
        return false;
    }

    public boolean associateUeToSlice(String imsi, String nssiId) throws NotExistingEntityException, IOException {

        boolean ueAlreadyRegistered = isUeAlreadyRegistered(imsi);

        boolean subscriptionProfileForSliceExist = false;
        String networkCoreSliceId = fromNssiToNetworkSliceCoreId(nssiId);
        for(String subscriberGroupId: subGroupInfoMap.keySet()){
            if(subGroupInfoMap.get(subscriberGroupId).getSliceId().equals(networkCoreSliceId)){
                subscriptionProfileForSliceExist = true;
                break;
            }
        }
        final String subscriptionProfileId = "subscriptionProfileForSlice"+networkCoreSliceId;
        final String subscriptionGroupId = "subscriptionGroupForSlice"+networkCoreSliceId;

        if(!subscriptionProfileForSliceExist){
            LOG.info("Core network slice "+networkCoreSliceId+" has no subgroup associated, so no subscription profile. ");

            LOG.info("Creating subscription profile for the slice "+networkCoreSliceId);
            String resultSubProfileCreation = createSubscriberProfile(subscriptionProfileId);

            if(resultSubProfileCreation==null) {
                LOG.error("Error creating subscription profile");
                //return false;
            }
            LOG.info("Creating subscription group for the slice "+networkCoreSliceId);

            String resultSubGroupCreation = createSubscriberGroup(subscriptionProfileId, subscriptionGroupId,networkCoreSliceId);

            if(resultSubGroupCreation==null) {
                LOG.error("Error creating subscription group");
                //return false;
            }

            LOG.info("Registering subscriber with IMSI "+imsi+ " to group with ID "+subscriptionGroupId);
            String resultRegisterSubscriber = registerSubscriber(imsi, subscriptionGroupId);
            if(resultRegisterSubscriber==null) {
                LOG.warn("Error registering subscriber. Trying to update its info");
                if(ueAlreadyRegistered)
                    updateSubscriber(imsi, subscriptionGroupId);
            }
        }
        else{
            LOG.info("Core network slice "+networkCoreSliceId+" has already subgroup associated. going to register the UE within the subscriber group "+subscriptionGroupId);
            String resultRegisterSubscriber = registerSubscriber(imsi, subscriptionGroupId);
            if(resultRegisterSubscriber==null) {
                LOG.warn("Error registering subscriber. Trying to update its info");
                if(ueAlreadyRegistered)
                    updateSubscriber(imsi, subscriptionGroupId);
            }
        }

        SubscriberGroupInfo subscriberGroupInfo = new SubscriberGroupInfo(subscriptionGroupId, subscriptionProfileId,networkCoreSliceId);
        subscriberGroupInfo.addImsi(imsi);
        subGroupInfoMap.put(subscriptionGroupId, subscriberGroupInfo);
        return true;
    }

    public String createSubscriberGroup(String subProfileId, String subGroupId, String coreNetworksliceId) {
        CNCrestClient cnCrestClient = new  CNCrestClient(cncIp, cncPort);
        SubscriberGroup subscriberGroup = new SubscriberGroup();
        subscriberGroup._id = subGroupId;
        subscriberGroup.description = subGroupId;

        SubscribedUeAmbr subscribedUeAmbr = new SubscribedUeAmbr();
        subscribedUeAmbr.downlink = "100 Mbps";
        subscribedUeAmbr.uplink = "100 Mbps";

        subscriberGroup.subscribedUeAmbr = subscribedUeAmbr;

        ArrayList<String> profiles = new ArrayList<>();
        profiles.add(subProfileId);
        Subscription subscription = new Subscription();

        subscription.sliceName = coreNetworksliceId;
        subscription.profiles = profiles;
        ArrayList<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription);
        subscriberGroup.subscription = subscriptions;
        printObjectJsonFormat(subscriberGroup);

        String result = cnCrestClient.createSubscriberGroup(subscriberGroup);
        if(result!=null) {
            subGroupInfoMap.put(subGroupId, new SubscriberGroupInfo(subGroupId, subProfileId, coreNetworksliceId));
        }
        return result;

    }
}
