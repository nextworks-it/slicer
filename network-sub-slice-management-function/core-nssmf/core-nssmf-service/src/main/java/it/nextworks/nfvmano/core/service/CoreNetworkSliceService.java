/*
 * Copyright (c) 2021 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import it.nextworks.nfvmano.core.recordIM.CoreInstanceInfo;
import it.nextworks.nfvmano.core.recordIM.SubscriberListForSlice;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NstServiceProfile;
import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceSubnetType;
import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.CoreSlicePayload;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.Subscriber;
import it.nextworks.nfvmano.sbi.cnc.messages.NetworkSliceCNC;
import it.nextworks.nfvmano.sbi.cnc.messages.WrapperListNetworkSliceCNC;
import it.nextworks.nfvmano.sbi.cnc.messages.WrapperNetworkSliceCNC;
import it.nextworks.nfvmano.sbi.cnc.rest.CNCrestClient;
import it.nextworks.nfvmano.sbi.cnc.subscribersManagement.CoreSubscriberListInfo;
import it.nextworks.nfvmano.sbi.cnc.subscribersManagement.Data1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CoreNetworkSliceService {
    private static final Logger LOG = LoggerFactory.getLogger(CoreNetworkSliceService.class);

    @Autowired
    private CoreInstanceService coreInstanceService;

    @Value("${nssmf.coreInstanceId}")
    private String configuredCoreInstanceId;

    @Value("${nssmf.skipNsdDeployment}")
    private boolean skipNsdDeployment;

    @Value("${nssmf.cnc.ip}")
    private String cncIp;

    @Value("${nssmf.cnc.port}")
    private int cncPort;

    public CoreNetworkSliceService(){
    }

    public boolean isNssiInstantiated(String nssiId){
        LOG.info("Checking if the core instance with ID "+nssiId+" is available");
        try {
            coreInstanceService.getCoreInstanceInfo(nssiId);
            return true;
        }
        catch (NotExistingEntityException e) {
            return false;
        }
    }


    public void initNetworkCoreSliceSet(String coreInstanceId){
        coreInstanceService.storeCoreInstanceInfo(coreInstanceId, null);
    }

    public void updateCoreNetworkSliceList(String coreInstanceId, String coreNetworkSliceId){
        LOG.info("Storing mapping between Core instance with ID "+coreInstanceId+ " and core network slice with ID "+coreNetworkSliceId);
        coreInstanceService.storeCoreInstanceInfo(coreInstanceId, coreNetworkSliceId);
    }


    public String getNsdIdCore(String coreInstanceId) throws NotExistingEntityException {
        return coreInstanceService.getCoreInstanceInfo(coreInstanceId).getNsdIdCore();

    }

    public void setNsdIdCore(String coreInstanceId, String nsdIdCore) throws NotExistingEntityException {
        coreInstanceService.setCoreInstanceNsdId(coreInstanceId,nsdIdCore);
    }


    public boolean isCoreSliceExisting(String coreInstanceId, String sliceId){
        try {
            getCoreNetworkSliceInformation(coreInstanceId, sliceId);
            return true;
        }
        catch (IOException | NotExistingEntityException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public SubscriberListForSlice getSubscribersForSlice(String coreInstanceId, String coreNetworkSliceId) throws IOException, NotExistingEntityException {
        CoreInstanceInfo coreInstanceInfo = coreInstanceService.getCoreInstanceInfo(coreInstanceId);
        LOG.info("Getting subscribers for slice with ID "+coreNetworkSliceId);

        if (!isCoreSliceExisting(coreInstanceId, coreNetworkSliceId)) {
            String msg = "Slice with ID " + coreNetworkSliceId + " does not exist";
            LOG.error(msg);
            throw new NotExistingEntityException(msg);
        }

        CNCrestClient cncRestClient = new CNCrestClient(coreInstanceInfo.getIpCnc(), coreInstanceInfo.getPortCnc());
        List<SubscriberListForSlice> subscriberListForSliceList = new ArrayList<>();

        String subscriberListString = cncRestClient.getSubscriberList();
        Map<String, List<Subscriber>> sliceSubsListMap = new HashMap<>();

        if (subscriberListString == null) {
            String msg = "No subscribers found for slice " + coreNetworkSliceId;
            LOG.warn(msg);
            throw new NotExistingEntityException(msg);
        }

        ObjectMapper om = new ObjectMapper();
        CoreSubscriberListInfo coreSubscriberListInfo = om.readValue(subscriberListString, CoreSubscriberListInfo.class);

        for (Data1 data1 : coreSubscriberListInfo.data1) {
            if (!data1.slice_name.equals(coreNetworkSliceId))
                continue;

            LOG.info("Found subscriber with IMSI "+data1.imsi);
            Subscriber subscriber = new Subscriber(data1.imsi, data1.k);
            if (sliceSubsListMap.get(coreNetworkSliceId) == null) {
                List<Subscriber> subscribers = new ArrayList<>();
                subscribers.add(subscriber);
                sliceSubsListMap.put(coreNetworkSliceId, subscribers);
            } else
                sliceSubsListMap.get(coreNetworkSliceId).add(subscriber);

        }
        SubscriberListForSlice subscriberListForSlice = new SubscriberListForSlice();
        for (String sliceName : sliceSubsListMap.keySet()) {
            List<Subscriber> subscribers = sliceSubsListMap.get(sliceName);
            subscriberListForSlice.setSliceName(sliceName);
            subscriberListForSlice.setSubscriberList(subscribers);
            subscriberListForSliceList.add(subscriberListForSlice);
            break;
        }
        subscriberListForSlice.setSliceName(coreNetworkSliceId);
        return subscriberListForSlice;
    }

    public NetworkSliceCNC getCoreNetworkSliceInformation(String coreInstanceId, String networkSliceId) throws IOException, NotExistingEntityException {
        CoreInstanceInfo coreInstanceInfo = coreInstanceService.getCoreInstanceInfo(coreInstanceId);

        if(!coreInstanceInfo.getCoreNetworkSliceId().contains(networkSliceId)){
            throw new NotExistingEntityException("Core slice with ID "+networkSliceId+ "not found");
        }


        CNCrestClient cncRestClient = new CNCrestClient(coreInstanceInfo.getIpCnc(), coreInstanceInfo.getPortCnc());
        String networkSlice = cncRestClient.getNetworkSlice(networkSliceId);
        if(networkSlice==null){
            throw new NotExistingEntityException("Core slice with ID "+networkSliceId+ "not found");
        }

        ObjectMapper om = new ObjectMapper();
        WrapperNetworkSliceCNC wrapperNetworkSliceCNC =  om.readValue(networkSlice, WrapperNetworkSliceCNC.class);
        return wrapperNetworkSliceCNC.networkSliceCNC;
    }

    public List<NetworkSliceCNC> getAllCoreNetworkSliceInformation(String coreInstanceId) throws IOException, NotExistingEntityException {

        CoreInstanceInfo coreInstanceInfo = coreInstanceService.getCoreInstanceInfo(coreInstanceId);
        CNCrestClient cncRestClient = new CNCrestClient(coreInstanceInfo.getIpCnc(), coreInstanceInfo.getPortCnc());
        String networkSliceList = cncRestClient.getNetworkSlices();

        if(networkSliceList==null){
            return new ArrayList<>();
        }
        ObjectMapper om = new ObjectMapper();
        WrapperListNetworkSliceCNC wrapperNetworkSliceCNC =  om.readValue(networkSliceList, WrapperListNetworkSliceCNC.class);

        ArrayList<NetworkSliceCNC> coreNetworkSlices = new ArrayList<>();
        for(NetworkSliceCNC networkSliceCNC: wrapperNetworkSliceCNC.networkSliceCNC){
            if(coreInstanceInfo.getCoreNetworkSliceId().contains(networkSliceCNC.sliceName)){
                coreNetworkSlices.add(networkSliceCNC);
            }
        }
        return coreNetworkSlices;
    }


    public NSST getNsstCore(NST nst) throws MalformattedElementException {
        for(NSST nsst: nst.getNsst().getNsstList()){
            if(nsst.getType() == SliceSubnetType.CORE){
                return nsst;
            }

        }
        throw new MalformattedElementException("NSST and Service profile not found. These  attributes are mandatory are mandatory");

    }

    public synchronized void updateCoreNetworkSlice(String coreInstanceId, String coreNetworkSliceId, CoreSlicePayload coreSlicePayload) throws NotExistingEntityException, MalformattedElementException, IOException {
        CoreInstanceInfo coreInstanceInfo = coreInstanceService.getCoreInstanceInfo(coreInstanceId);
        getCoreNetworkSliceInformation(coreInstanceId, coreNetworkSliceId);


        NST nst = coreSlicePayload.getNst();
        LOG.info("Validating payload");
        if(nst.getNstServiceProfileList()==null || nst.getNstServiceProfileList().size() == 0 || nst.getNstServiceProfileList().get(0)==null){
            throw new MalformattedElementException("Service profile is mandatory");
        }
        LOG.info("Requesting Core Network Slice modification toward CNC");
        NSST nsst = getNsstCore(nst);
        NstServiceProfile nstServiceProfile = nst.getNstServiceProfileList().get(0);

        NstToNetworkSliceCmcMapper nstToNetworkSliceCmcMapper = new NstToNetworkSliceCmcMapper();
        NetworkSliceCNC networkSliceCNC = nstToNetworkSliceCmcMapper.mapCoreNetworkSliceToNetworkSliceCNC(nstServiceProfile, nsst);
        networkSliceCNC.sliceName = coreNetworkSliceId;
        LOG.info("Requesting Core Network Slice update toward CNC");
        CNCrestClient cncRestClient = new CNCrestClient(coreInstanceInfo.getIpCnc(), coreInstanceInfo.getPortCnc());
        boolean isCoreNetworkSliceModified = cncRestClient.updateNetworkSlice(networkSliceCNC);
        if(isCoreNetworkSliceModified) {
            LOG.info("Core network slice successfully updated");
            //coreNetworkSliceMap.put(networkSliceCNC.sliceName, networkSliceCNC);
            printJsonObject(networkSliceCNC);

            updateCoreNetworkSliceList(coreInstanceId, networkSliceCNC.sliceName);
            coreInstanceService.storeCoreInstanceInfo(coreInstanceId, networkSliceCNC.sliceName);

        }
        else{
            final String ERR_MSG = "Error updating core network slice";
            LOG.error(ERR_MSG);
            throw new MalformattedElementException(ERR_MSG);
        }
    }

    private void printJsonObject(Object object){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        LOG.info(json);
    }
    public synchronized String createCoreNetworkSlice(String coreInstanceId, CoreSlicePayload coreSlicePayload) throws Exception {

        LOG.info("Creating a new core network slice");
        CoreInstanceInfo coreInstanceInfo;
        if(coreInstanceId.equals(configuredCoreInstanceId) && skipNsdDeployment==true){
            LOG.info("Core instance ID is that one configured");


            coreInstanceInfo = new CoreInstanceInfo();
            coreInstanceInfo.setCoreInstanceId(configuredCoreInstanceId);
            coreInstanceInfo.setCoreNetworkSliceId(new ArrayList<>());
            coreInstanceInfo.setNsdIdCore(null);
            coreInstanceInfo.setIpCnc(cncIp);
            coreInstanceInfo.setPortCnc(cncPort);
            coreInstanceInfo.setgNBList(new HashSet<>());

        }

        else {
            coreInstanceInfo = coreInstanceService.getCoreInstanceInfo(coreInstanceId);
        }
        NST nst = coreSlicePayload.getNst();
        LOG.info("Validating payload");
        if(nst.getNstServiceProfileList()==null || nst.getNstServiceProfileList().size() == 0 || nst.getNstServiceProfileList().get(0)==null){
            throw new MalformattedElementException("Service profile is mandatory");
        }

        NSST nsst = getNsstCore(nst);
        NstServiceProfile nstServiceProfile = nst.getNstServiceProfileList().get(0);

        NstToNetworkSliceCmcMapper nstToNetworkSliceCmcMapper = new NstToNetworkSliceCmcMapper();
        NetworkSliceCNC networkSliceCNC = nstToNetworkSliceCmcMapper.mapCoreNetworkSliceToNetworkSliceCNC(nstServiceProfile, nsst);

        LOG.info("Requesting Core Network Slice creation toward CNC");
        CNCrestClient cncRestClient = new CNCrestClient(coreInstanceInfo.getIpCnc(), coreInstanceInfo.getPortCnc());
        boolean isCoreNetworkSliceCreated = cncRestClient.createNetworkSlice(networkSliceCNC);
        printJsonObject(networkSliceCNC);
        if(isCoreNetworkSliceCreated) {
            LOG.info("Core network slice successfully created");
            updateCoreNetworkSliceList(coreInstanceId, networkSliceCNC.sliceName);
            coreInstanceService.storeCoreInstanceInfo(coreInstanceId, networkSliceCNC.sliceName);
            coreInstanceService.storeCoreCncInfo(coreInstanceId, cncIp, cncPort);

            return networkSliceCNC.sliceName;
        }
        else{
            final String ERR_MSG = "Error creating core network slice";
            LOG.error(ERR_MSG);
            throw new MalformattedElementException(ERR_MSG);
        }
    }

    public CNCrestClient getCncRestClient(String coreInstanceId) throws NotExistingEntityException {
        CoreInstanceInfo coreInstanceInfo = coreInstanceService.getCoreInstanceInfo(coreInstanceId);
        CNCrestClient cnCrestClient = new CNCrestClient(coreInstanceInfo.getIpCnc(), coreInstanceInfo.getPortCnc());
        return cnCrestClient;
    }


    public synchronized void deleteCoreNetworkSlice(String coreInstanceId, String coreNetworkSliceId) throws NotExistingEntityException, IOException, FailedOperationException {
        LOG.info("Deleting core network slice with UUID "+coreNetworkSliceId+" from core instance with ID "+coreInstanceId);
        if(!isCoreSliceExisting(coreInstanceId, coreNetworkSliceId)){
            throw new NotExistingEntityException("Slice with ID "+coreNetworkSliceId+" does not exist");
        }
        CoreInstanceInfo coreInstanceInfo = coreInstanceService.getCoreInstanceInfo(coreInstanceId);
        SubscriberListForSlice SubscriberListForSlice = getSubscribersForSlice(coreInstanceId, coreNetworkSliceId);
        LOG.info("Removing all subscribers related to core network slice "+coreNetworkSliceId);
        CNCrestClient cnCrestClient = new CNCrestClient(coreInstanceInfo.getIpCnc(), coreInstanceInfo.getPortCnc());
        for(Subscriber subscriber: SubscriberListForSlice.getSubscribers()){
            String imsi = subscriber.getImsi();
            if(cnCrestClient.removeSubscriber(imsi)!=null){
                LOG.info("Subscriber with IMSI "+imsi+ " correctly removed");
            }
            else{
                LOG.warn("Error removing subscriber with IMSI "+imsi);
            }

        }
        LOG.info("Deleting network slice with ID "+coreNetworkSliceId);

        if(cnCrestClient.deleteNetworkSlice(coreNetworkSliceId)==false){
            String errorMsg = "Error removing core network slice with ID "+coreNetworkSliceId;
            LOG.error(errorMsg);
            throw new FailedOperationException(errorMsg);
        }
        LOG.info("Core network slice with ID "+coreNetworkSliceId+" correctly removed");
        coreInstanceService.removeCoreNetworkSliceFromCoreInstance(coreInstanceId, coreNetworkSliceId);

    }
}
