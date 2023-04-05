package it.nextworks.nfvmano.flexPhyMac.service;

import it.nextworks.nfvmano.flexPhyMac.repo.RanSubSliceRepository;
import it.nextworks.nfvmano.flexPhyMac.rest.record.FlexPhyMacSubscriber;
import it.nextworks.nfvmano.flexPhyMac.rest.record.RanSubSlice;
import it.nextworks.nfvmano.flexPhyMac.sbi.FlexPhyMacDriver;
import it.nextworks.nfvmano.flexPhyMac.sbi.FlexPhyMacResourceAllocation;
import it.nextworks.nfvmano.libs.ifa.templates.nst.*;
import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;


@Service
public class RanNetworkSubSliceService {
    private static final Logger LOG = LoggerFactory.getLogger(RanNetworkSubSliceService.class);

    @Autowired
    private RanSubSliceRepository ranSubSliceRepository;

    @Autowired
    private SubscriberService subscriberService;
    private String networkServiceInstanceId; //Network Service identifier UERANSIM is running on
    @Value("${bs-control.ip}")
    private String ipFlexiblePhyMac;
    @Value("${bs-control.port}")
    private int portFlexiblePhyMac;



    private FlexPhyMacResourceAllocation currentFlexPhyMacAllocation;

    public RanNetworkSubSliceService(){
        networkServiceInstanceId = null;
    }



    public NSST getNsstRan(NST nst) throws MalformattedElementException {
        for(NSST nsst: nst.getNsst().getNsstList()){
            if(nsst.getType() == SliceSubnetType.RAN){
                return nsst;
            }

        }
        throw new MalformattedElementException("NSST and Service profile not found. These  attributes are mandatory");

    }

    public void createRanNetworkSubSlice(String nssiId, NSST nsst) throws Exception {
        LOG.info("Creating a new RAN network sub slice whose id is "+nssiId);
        checkNsstValidity(nsst);
        storeRanNssiInformation(nssiId,nsst);
    }

    private RanSubSlice getRanSubSliceFromDb(String ranSubSliceId) throws MalformattedElementException {
        Optional<RanSubSlice> ranSubSliceOptional = ranSubSliceRepository.findBySliceId(ranSubSliceId);
        if(!ranSubSliceOptional.isPresent()){
            String errorMsg = "RAN subslice whose ID is "+ranSubSliceId+" NOT  found";
            LOG.error(errorMsg);
            throw new MalformattedElementException(errorMsg);
        }
        return ranSubSliceOptional.get();
    }
    public void updateRanNetworkSubSlice(String nssiId, NSST nsst)throws Exception{
        LOG.info("Updating existing RAN network sub slice whose id is "+nssiId);
        checkNsstValidity(nsst);

        RanSubSlice ranSubSlice =getRanSubSliceFromDb(nssiId);
        int subscriberCount = ranSubSlice.getSubscribers().size();
        if(subscriberCount==0){
            String errorMsg = "No UEs associated to the slice whose id is "+nssiId;
            LOG.error(errorMsg);
            throw new MalformattedElementException(errorMsg);
        }
        else if(subscriberCount==1) {
            String firstImsi= ranSubSlice.getSubscribers().get(0).getImsi();
            subscriberService.associateSubscriberWithSlice(nssiId, firstImsi, false,nsst);
        }
        else{
            String errorMsg = "Logic for modifying a slice with more than one UEs associated with NOT implemented";
            LOG.error(errorMsg);
            throw new MalformattedElementException(errorMsg);
        }

    }

    private void checkNsstValidity(NSST nsst) throws Exception {
        SliceProfile sliceProfile = nsst.getSliceProfileList().get(0);
        EMBBPerfReq embbPerfReq = sliceProfile.geteMBBPerfReq().get(0);
        int maxNumberOfUe = sliceProfile.getMaxNumberofUEs();
        if(maxNumberOfUe>1){
            String errorMsg = "You can have only one UE for this slice";
            LOG.error(errorMsg);
            throw new MalformattedElementException(errorMsg);
        }
        int expectedDataRateUl = embbPerfReq.getExpDataRateUL();//In bytes
        int MAX_DATA_RATE_BYTE_SEC = 2*1024*1024;
        if(expectedDataRateUl>MAX_DATA_RATE_BYTE_SEC){
            String errorMsg = "Maximum data rate cannot exceed 2MB\\s";
            LOG.error(errorMsg);
            throw new MalformattedElementException(errorMsg);
        }

    }

    ///Method for managing the ran network sub slice information. START
    public void storeRanNssiInformation(String nssiId, NSST nsst) throws MalformattedElementException {
        if(ranSubSliceRepository.findBySliceId(nssiId).isPresent()){
            String errorMsg = "NSSI with ID "+nssiId+ " already exists";
            throw new MalformattedElementException(errorMsg);
        }
        RanSubSlice ranSubSlice = new RanSubSlice(nssiId, new ArrayList<>(),networkServiceInstanceId, nsst);
        ranSubSliceRepository.saveAndFlush(ranSubSlice);
    }


    public NSST getNSSTfromNetworkSubslice(String nssiId) throws NotExistingEntityException {
        checkRanSubSliceExistence(nssiId);
        return ranSubSliceRepository.findBySliceId(nssiId).get().getNsst();
    }

    public List<FlexPhyMacSubscriber> getFlexPhyMacSubscriberListPerSlice(String ranSubsliceId) throws NotExistingEntityException {
        checkRanSubSliceExistence(ranSubsliceId);
        return ranSubSliceRepository.findBySliceId(ranSubsliceId).get().getSubscribers();
    }

    private int isSubscriberAlreadyRegistered(String imsi, List<FlexPhyMacSubscriber> subscriberList) {
        int index = -1;
        for(int i=0; i<subscriberList.size(); i++){
            FlexPhyMacSubscriber flexPhyMacSubscriber = subscriberList.get(i);
            String currentImsi = flexPhyMacSubscriber.getImsi();
            if(currentImsi.equals(imsi)){
                return i;
            }
        }
        return index;
    }
    public void associateSubscriberWithRanNetworkSubSlice(String nssiId, FlexPhyMacSubscriber subscriber, boolean newImsiEntry, NSST nsst) throws NotExistingEntityException, MalformattedElementException {
        checkRanSubSliceExistence(nssiId);
        RanSubSlice ranSubSlice=ranSubSliceRepository.findBySliceId(nssiId).get();
        String imsi = subscriber.getImsi();
        List<FlexPhyMacSubscriber> subscriberList = ranSubSlice.getSubscribers();

        int index = isSubscriberAlreadyRegistered(imsi, subscriberList);

        if(index!=-1 && newImsiEntry){
            String errMsg = "Subscriber with IMSI "+imsi+" already associated with RAN NSS with ID "+nssiId;
            throw new MalformattedElementException(errMsg);
        }

        if(newImsiEntry) {
            List<FlexPhyMacSubscriber> subscriberSet = ranSubSlice.getSubscribers();
            LOG.info("BEFORE Subscriber set size is "+ranSubSlice.getSubscribers().size());
            subscriberSet.add(subscriber);
            ranSubSlice.setSubscriberList(subscriberSet);
            LOG.info("AFTER Subscriber set size is "+ranSubSlice.getSubscribers().size());
        }

        ranSubSlice.setNsst(nsst);
        ranSubSliceRepository.saveAndFlush(ranSubSlice);
        LOG.info("Association between UE whose IMSI is "+imsi+" and RAN whose ID is "+nssiId+ " succesfully performed");
    }

    public void removeAssociationSubscriberWithRanNetworkSubSlice(String nssiId, FlexPhyMacSubscriber subscriber) throws NotExistingEntityException {
        RanSubSlice ranSubSlice=ranSubSliceRepository.findBySliceId(nssiId).get();
        String imsi = subscriber.getImsi();
        checkRanSubSliceExistence(nssiId);
        List<FlexPhyMacSubscriber> subscriberList = ranSubSlice.getSubscribers();
        int index = isSubscriberAlreadyRegistered(imsi, subscriberList);

        if(index==-1){
            String errMsg = "Subscriber with IMSI "+imsi+" does not exist";
            throw new NotExistingEntityException(errMsg);
        }
        subscriberList.remove(index);
        ranSubSlice.setSubscriberList(subscriberList);
        ranSubSliceRepository.saveAndFlush(ranSubSlice);
    }



    public boolean isExistingRanSubSlice(String ranNetworkSubSliceId){
        return ranSubSliceRepository.findBySliceId(ranNetworkSubSliceId).isPresent();
    }

    public void checkRanSubSliceExistence(String ranNetworkSubSliceId) throws NotExistingEntityException {
        if(!ranSubSliceRepository.findBySliceId(ranNetworkSubSliceId).isPresent()){
            String messageError = "RAN network slice with ID "+ranNetworkSubSliceId+" does not exist";
            LOG.error(messageError);
            throw new NotExistingEntityException(messageError);
        }
        LOG.info("Get from DB Ran Network sub slice with ID "+ranNetworkSubSliceId);
    }



    public void setNetworkServiceInstanceId(String networkServiceInstanceId) throws ExecutionException {
        this.networkServiceInstanceId = networkServiceInstanceId;
        FlexPhyMacDriver flexPhyMacDriver = new FlexPhyMacDriver(ipFlexiblePhyMac, portFlexiblePhyMac);
        currentFlexPhyMacAllocation = flexPhyMacDriver.clearResources();//Once set the network service instance id, then the allocation are clear on flex-phy mac side
    }

    public FlexPhyMacResourceAllocation getCurrentFlexPhyMacAllocation() {
        return currentFlexPhyMacAllocation;
    }

    public void setCurrentFlexPhyMacAllocation(FlexPhyMacResourceAllocation flexPhyMacAllocation) {
        currentFlexPhyMacAllocation= flexPhyMacAllocation;
    }

    public String getNetworkServiceInstanceId() {
        return networkServiceInstanceId;
    }



    public void deleteRanNetworkSlice(String ranNetworkSubSliceId) throws NotExistingEntityException, IOException, FailedOperationException {
        LOG.info("Deleting RAN network sub slice with UUID "+ranNetworkSubSliceId);
        checkRanSubSliceExistence(ranNetworkSubSliceId);
        LOG.info("Removing all subscribers related to RAN network sub slice "+ranNetworkSubSliceId);

        //TODO implement logic

        LOG.info("Deleting network slice with ID "+ranNetworkSubSliceId);

        //TODO implement logic;

    }
}
