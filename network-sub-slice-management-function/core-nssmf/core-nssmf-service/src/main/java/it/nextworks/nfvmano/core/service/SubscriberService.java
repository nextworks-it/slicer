package it.nextworks.nfvmano.core.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.core.recordIM.SubscriberListForSlice;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.Subscriber;
import it.nextworks.nfvmano.sbi.cnc.rest.CNCrestClient;
import it.nextworks.nfvmano.sbi.cnc.subscribersManagement.CoreSubscriberListInfo;
import it.nextworks.nfvmano.sbi.cnc.subscribersManagement.Data1;
import it.nextworks.nfvmano.sbi.cnc.subscribersManagement.SubscriberInfo;
import it.nextworks.nfvmano.sbi.cnc.subscribersManagement.Subscriberprofile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscriberService {
    private static final Logger LOG = LoggerFactory.getLogger(CoreNetworkSliceService.class);

    @Autowired
    CoreNetworkSliceService coreNetworkSliceService;


    public SubscriberService(){

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
}
