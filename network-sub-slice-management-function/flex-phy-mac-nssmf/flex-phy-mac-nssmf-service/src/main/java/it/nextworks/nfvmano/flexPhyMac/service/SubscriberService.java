package it.nextworks.nfvmano.flexPhyMac.service;


import it.nextworks.nfvmano.flexPhyMac.rest.record.*;
import it.nextworks.nfvmano.flexPhyMac.sbi.FlexPhyMacDriver;
import it.nextworks.nfvmano.flexPhyMac.sbi.FlexPhyMacResourceAllocation;
import it.nextworks.nfvmano.flexPhyMac.sbi.FlexPhyMacUeAllocation;
import it.nextworks.nfvmano.flexPhyMac.sbi.FlexiblePhyMacConfiguration;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscriberService {
    private static final Logger LOG = LoggerFactory.getLogger(RanNetworkSubSliceService.class);

    @Autowired
    RanNetworkSubSliceService ranNetworkSubSliceService;

    @Value("${bs-control.ip}")
    private String ipFlexiblePhyMac;
    @Value("${bs-control.port}")
    private int portFlexiblePhyMac;

    private Map<String, String> imsiUeMapping;

    public SubscriberService(){
        this.imsiUeMapping = new HashMap<>();
        //TODO get somehow
        imsiUeMapping.put(FlexiblePhyMacConfiguration.IMSI_UE0, FlexiblePhyMacConfiguration.ID_UE0);
        imsiUeMapping.put(FlexiblePhyMacConfiguration.IMSI_UE1,FlexiblePhyMacConfiguration.ID_UE1);
        imsiUeMapping.put(FlexiblePhyMacConfiguration.IMSI_UE2,FlexiblePhyMacConfiguration.ID_UE2);
    }



    public List<FlexPhyMacSubscriber> getSubscriberForSlice(String ranSubsliceId) throws NotExistingEntityException {
        LOG.info("Getting subscriber for slice with ID "+ranSubsliceId);
        return ranNetworkSubSliceService.getFlexPhyMacSubscriberListPerSlice(ranSubsliceId);
    }


    public void associateSubscriberWithSlice(String ranSubSliceId, String imsiSubscriber, boolean newImsiEntry, NSST newNsst) throws MalformattedElementException, NotExistingEntityException {
        LOG.info("Register subscribers with IMSI "+imsiSubscriber+" for slice "+ranSubSliceId);
        isValid(ranSubSliceId, imsiSubscriber);

        NSST nsst = ranNetworkSubSliceService.getNSSTfromNetworkSubslice(ranSubSliceId);
        if(newNsst!=null)
            nsst = newNsst;

        isNSSTvalid(nsst);
        int uploadBytesPerSecond = nsst.getSliceProfileList().get(0).geteMBBPerfReq().get(0).getExpDataRateUL();

        float percentageBandwidth = uploadBytesPerSecond*100/FlexiblePhyMacConfiguration.MAX_BYTES_PER_SECONDS;
        int percentageBandwidthUe = Math.round(percentageBandwidth);
        String ueId = imsiUeMapping.get(imsiSubscriber);
        int percentageBandwidthOccupiedByOtherUes = 0;
        LOG.info("UE to look for has ID "+ueId+ ", whose percentage bandwidth is "+percentageBandwidthUe);
        List<FlexPhyMacUeAllocation>  flexPhyMacUeAllocationList  = ranNetworkSubSliceService.getCurrentFlexPhyMacAllocation().getUes();
        int indexElement = -1;
        for(int i=0; i< flexPhyMacUeAllocationList.size(); i++)
        {
            String currentUe = flexPhyMacUeAllocationList.get(i).getUeId();

            LOG.info("Current UE: "+currentUe);
            if(currentUe.equals(ueId)) {
                indexElement = i;
                continue;
            }

            percentageBandwidthOccupiedByOtherUes+= Integer.valueOf(flexPhyMacUeAllocationList.get(i).getAllocation());
        }

        if(indexElement==-1){
            String messageError = "UE to look for "+ueId+" not found.";
            LOG.info(messageError);
            throw new MalformattedElementException(messageError);
        }
        int totalBandwidthOccupied = percentageBandwidthUe+percentageBandwidthOccupiedByOtherUes;
        if(totalBandwidthOccupied>100){
            String messageError = "Total maximum percentage bandwidth after allocation is greater than 100. Please check slice params";
            LOG.error(messageError);
            throw new MalformattedElementException(messageError);
        }

        FlexPhyMacUeAllocation flexPhyMacUeAllocation = flexPhyMacUeAllocationList.get(indexElement);
        flexPhyMacUeAllocation.setAllocation(String.valueOf(percentageBandwidthUe));
        flexPhyMacUeAllocationList.set(indexElement,flexPhyMacUeAllocation);
        FlexPhyMacResourceAllocation flexPhyMacResourceAllocation = ranNetworkSubSliceService.getCurrentFlexPhyMacAllocation();
        flexPhyMacResourceAllocation.setUes(flexPhyMacUeAllocationList);
        requestAllocationTowardFlexPhyMac(flexPhyMacResourceAllocation);
        ranNetworkSubSliceService.setCurrentFlexPhyMacAllocation(flexPhyMacResourceAllocation);
        ranNetworkSubSliceService.associateSubscriberWithRanNetworkSubSlice(ranSubSliceId, new FlexPhyMacSubscriber(imsiSubscriber, ueId),newImsiEntry, nsst);

    }

    public void removeSubscriber(String ranSubSliceId, String imsiSubscriber) throws NotExistingEntityException, MalformattedElementException {

        LOG.info("Removing subscribers with IMSI "+imsiSubscriber+" from slice "+ranSubSliceId);
        isValid(ranSubSliceId, imsiSubscriber);
        String ueId = imsiUeMapping.get(imsiSubscriber);
        List<FlexPhyMacUeAllocation>  flexPhyMacUeAllocationList  = ranNetworkSubSliceService.getCurrentFlexPhyMacAllocation().getUes();
        int indexElement = -1;
        for(int i=0; i< flexPhyMacUeAllocationList.size(); i++)
        {
            String currentUe = flexPhyMacUeAllocationList.get(i).getUeId();
            if(currentUe.equals(ueId)) {
                indexElement = i;
                break;
            }
        }
        FlexPhyMacUeAllocation flexPhyMacUeAllocation = flexPhyMacUeAllocationList.get(indexElement);
        flexPhyMacUeAllocation.setAllocation(FlexiblePhyMacConfiguration.NO_ALLOCATION);
        flexPhyMacUeAllocationList.set(indexElement,flexPhyMacUeAllocation);
        FlexPhyMacResourceAllocation flexPhyMacResourceAllocation = ranNetworkSubSliceService.getCurrentFlexPhyMacAllocation();
        flexPhyMacResourceAllocation.setUes(flexPhyMacUeAllocationList);
        requestAllocationTowardFlexPhyMac(flexPhyMacResourceAllocation);
        ranNetworkSubSliceService.setCurrentFlexPhyMacAllocation(flexPhyMacResourceAllocation);
        ranNetworkSubSliceService.removeAssociationSubscriberWithRanNetworkSubSlice(ranSubSliceId, new FlexPhyMacSubscriber(imsiSubscriber, ueId));
    }

    private void isValid(String ranSubSliceId, String imsiSubscriber) throws NotExistingEntityException {
        ranNetworkSubSliceService.checkRanSubSliceExistence(ranSubSliceId);
        String ueId = imsiUeMapping.get(imsiSubscriber);

        if(ueId==null){
            String messageError = "IMSI "+imsiSubscriber+" does not exist";
            LOG.error(messageError);
            throw new NotExistingEntityException(messageError);
        }
    }

    private void isNSSTvalid(NSST nsst) throws MalformattedElementException {
        final int MAXIMUM_BANDWIDTH_BYTES_PER_SEC = FlexiblePhyMacConfiguration.MAX_BYTES_PER_SECONDS;
        int uploadBytesPerSecond = nsst.getSliceProfileList().get(0).geteMBBPerfReq().get(0).getExpDataRateUL();
        if(uploadBytesPerSecond>MAXIMUM_BANDWIDTH_BYTES_PER_SEC){
            String messageError = "Expected rate in UL "+uploadBytesPerSecond+" greater than maximum "+MAXIMUM_BANDWIDTH_BYTES_PER_SEC;
            LOG.error(messageError);
            throw new MalformattedElementException(messageError);
        }
    }

    private void requestAllocationTowardFlexPhyMac(FlexPhyMacResourceAllocation flexPhyMacResourceAllocation ) throws MalformattedElementException {
        FlexPhyMacDriver flexPhyMacDriver = new FlexPhyMacDriver(ipFlexiblePhyMac, portFlexiblePhyMac);
        boolean configurationSuccess = flexPhyMacDriver.sendResourceAllocationRequest(flexPhyMacResourceAllocation);
        if(!configurationSuccess) {
            String messageError = "Error during configuration of Flexible PHY\\MAC";
            LOG.error(messageError);
            throw new MalformattedElementException(messageError);
        }

        ranNetworkSubSliceService.setCurrentFlexPhyMacAllocation(flexPhyMacResourceAllocation);
    }
}
