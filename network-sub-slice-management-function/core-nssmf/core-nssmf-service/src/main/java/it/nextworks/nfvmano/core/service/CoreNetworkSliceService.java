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
import it.nextworks.nfvmano.core.recordIM.UpfInstanceInfo;
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
import it.nextworks.nfvmano.sbi.cnc.UPFsliceAssociationCnc.UpfSliceAssociationCnc;
import it.nextworks.nfvmano.sbi.cnc.dnnList.*;
import it.nextworks.nfvmano.sbi.cnc.messages.*;
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
    private UpfInstanceService upfInstanceService;

    @Value("${nssmf.coreInstanceId}")
    private String configuredCoreInstanceId;

    @Value("${nssmf.skipNsdDeployment}")
    private boolean skipNsdDeployment;

    @Value("${nssmf.cnc.ip}")
    private String cncIp;

    @Value("${nssmf.cnc.port}")
    private int cncPort;

    private Map<String, UpfMainInfo> upfMainInfoMap;

    public CoreNetworkSliceService(){
        upfMainInfoMap = new HashMap<>();
    }

    public boolean isNssiInstantiated(String nssiId){
        LOG.info("Checking if the core instance with ID "+nssiId+" is available");
        try {
            upfInstanceService.getUpfInstanceInfoByName(nssiId);
            return true;
        }
        catch (NotExistingEntityException e) {
            return false;
        }
    }


    public void initNetworkCoreSliceSet(String coreInstanceId){
        upfInstanceService.storeUpfInstanceInfo(coreInstanceId, null, null);
    }

    public void updateCoreNetworkSliceList(String coreInstanceId, String coreNetworkSliceId){
        LOG.info("Storing mapping between UPF instance with ID "+coreInstanceId+ " and core network slice with ID "+coreNetworkSliceId);
        upfInstanceService.storeUpfInstanceInfo(coreInstanceId, coreNetworkSliceId, null);
    }


    public String getNsdIdCore(String coreInstanceId) throws NotExistingEntityException {
        return upfInstanceService.getUpfInstanceInfoByName(coreInstanceId).getNsdIdCore();

    }

    public void setNsdIdCore(String coreInstanceId, String nsdIdCore) throws NotExistingEntityException {
        upfInstanceService.setUpfInstanceNsdId(coreInstanceId,nsdIdCore);
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
        UpfInstanceInfo upfInstanceInfo = upfInstanceService.getUpfInstanceInfoByName(coreInstanceId);
        LOG.info("Getting subscribers for slice with ID "+coreNetworkSliceId);

        if (!isCoreSliceExisting(coreInstanceId, coreNetworkSliceId)) {
            String msg = "Slice with ID " + coreNetworkSliceId + " does not exist";
            LOG.error(msg);
            throw new NotExistingEntityException(msg);
        }

        CNCrestClient cncRestClient = new CNCrestClient(upfInstanceInfo.getIpCnc(), upfInstanceInfo.getPortCnc());
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
        UpfInstanceInfo upfInstanceInfo = upfInstanceService.getUpfInstanceInfoByName(coreInstanceId);

        if(!upfInstanceInfo.getUpfNetworkSliceId().contains(networkSliceId)){
            throw new NotExistingEntityException("Core slice with ID "+networkSliceId+ "not found");
        }


        CNCrestClient cncRestClient = new CNCrestClient(upfInstanceInfo.getIpCnc(), upfInstanceInfo.getPortCnc());
        String networkSlice = cncRestClient.getNetworkSlice(networkSliceId);
        if(networkSlice==null){
            throw new NotExistingEntityException("Core slice with ID "+networkSliceId+ "not found");
        }

        ObjectMapper om = new ObjectMapper();
        WrapperNetworkSliceCNC wrapperNetworkSliceCNC =  om.readValue(networkSlice, WrapperNetworkSliceCNC.class);
        return wrapperNetworkSliceCNC.networkSliceCNC;
    }

    public List<NetworkSliceCNC> getAllCoreNetworkSliceInformation(String upfInstanceId) throws IOException, NotExistingEntityException {
        LOG.info("Getting all core network slice information");
        UpfInstanceInfo upfInstanceInfo = upfInstanceService.getUpfInstanceInfoById(upfInstanceId);
        CNCrestClient cncRestClient = new CNCrestClient(upfInstanceInfo.getIpCnc(), upfInstanceInfo.getPortCnc());
        String networkSliceList = cncRestClient.getNetworkSlices();

        if(networkSliceList==null){
            return new ArrayList<>();
        }
        ObjectMapper om = new ObjectMapper();
        WrapperListNetworkSliceCNC wrapperNetworkSliceCNC =  om.readValue(networkSliceList, WrapperListNetworkSliceCNC.class);

        ArrayList<NetworkSliceCNC> coreNetworkSlices = new ArrayList<>();
        for(NetworkSliceCNC networkSliceCNC: wrapperNetworkSliceCNC.networkSliceCNC){
            if(upfInstanceInfo.getUpfNetworkSliceId().contains(networkSliceCNC.sliceName)){
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
        UpfInstanceInfo upfInstanceInfo = upfInstanceService.getUpfInstanceInfoByName(coreInstanceId);
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
        NetworkSliceCNC networkSliceCNC = nstToNetworkSliceCmcMapper.mapCoreNetworkSliceToNetworkSliceCNC(nstServiceProfile, nsst,upfInstanceService.getUpfCount());
        networkSliceCNC.sliceName = coreNetworkSliceId;
        LOG.info("Requesting Core Network Slice update toward CNC");
        CNCrestClient cncRestClient = new CNCrestClient(upfInstanceInfo.getIpCnc(), upfInstanceInfo.getPortCnc());
        boolean isCoreNetworkSliceModified = cncRestClient.updateNetworkSlice(networkSliceCNC);
        if(isCoreNetworkSliceModified) {
            LOG.info("Core network slice successfully updated");
            //coreNetworkSliceMap.put(networkSliceCNC.sliceName, networkSliceCNC);
            printJsonObject(networkSliceCNC);

            updateCoreNetworkSliceList(coreInstanceId, networkSliceCNC.sliceName);
            upfInstanceService.storeUpfInstanceInfo(coreInstanceId, networkSliceCNC.sliceName, null);

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



    private UpfSliceAssociationCnc buildAssociationPayload(String nfId, String nfName, String nfIp, int sst, String sd){
        UpfSliceAssociationCnc upfSliceAssociationCnc = new UpfSliceAssociationCnc();
        upfSliceAssociationCnc.nfInstanceId = nfId;
        upfSliceAssociationCnc.nfInstanceName = nfName;
        upfSliceAssociationCnc.nfType = "UPF";
        upfSliceAssociationCnc.nfStatus = "REGISTERED";
        ArrayList<String> ipv4Addresses = new ArrayList<>();
        ipv4Addresses.add(nfIp);
        upfSliceAssociationCnc.ipv4Addresses = ipv4Addresses;

        LOG.info("Building payload");
        SNssai sNssai = new SNssai();
        sNssai.sst=sst;
        sNssai.sd =sd;

        DnnUpfInfoList dnnUpfInfoList = new DnnUpfInfoList();
        dnnUpfInfoList.dnn="internet";
        ArrayList<DnnUpfInfoList> dnnUpfInfoLists = new ArrayList<>();
        dnnUpfInfoLists.add(dnnUpfInfoList);

        SNssaiUpfInfoList sNssaiUpfInfoList = new SNssaiUpfInfoList();
        sNssaiUpfInfoList.dnnUpfInfoList = dnnUpfInfoLists;
        sNssaiUpfInfoList.sNssai = sNssai;
        ArrayList<SNssaiUpfInfoList> sNssaiUpfInfoLists = new ArrayList<>();
        sNssaiUpfInfoLists.add(sNssaiUpfInfoList);

        InterfaceUpfInfoList interfaceUpfInfoList = new InterfaceUpfInfoList();
        interfaceUpfInfoList.interfaceType="N3";
        ArrayList<String> ipv4EndpointAddresses = new ArrayList<>();
        ipv4EndpointAddresses.add(nfIp);
        interfaceUpfInfoList.ipv4EndpointAddresses = ipv4EndpointAddresses;

        ArrayList<InterfaceUpfInfoList> interfaceUpfInfoLists = new ArrayList<>();
        interfaceUpfInfoLists.add(interfaceUpfInfoList);
        UpfInfo upfInfo = new UpfInfo();
        upfInfo.sNssaiUpfInfoList = sNssaiUpfInfoLists;
        upfInfo.interfaceUpfInfoList = interfaceUpfInfoLists;

        upfSliceAssociationCnc.upfInfo = upfInfo;

        return upfSliceAssociationCnc;
    }


    public void associateSliceToUpf(String upfName, int sst, String sd) throws IOException, MalformattedElementException {

        CNCrestClient cnCrestClient = new  CNCrestClient(cncIp, cncPort);
        upfMainInfoMap=  getUpfInfo();
        UpfMainInfo firstUpfMainInfo = null;
        LOG.info("Looking for UPF with name "+upfName);
        for(String nfId: upfMainInfoMap.keySet()){
            String nfInstanceName = upfMainInfoMap.get(nfId).getNfInstanceName();
            LOG.info("Current UPF name is "+nfInstanceName);
            if(nfInstanceName.equals(upfName)){
                firstUpfMainInfo = upfMainInfoMap.get(nfId);
                break;
            }
        }
        if(firstUpfMainInfo==null){
            LOG.error("UPF with name "+upfName+ "NOT found");
            throw new MalformattedElementException("UPF with name "+upfName+ "NOT found");
        }
        UpfSliceAssociationCnc upfSliceAssociationCnc = buildAssociationPayload(firstUpfMainInfo.getNfInstanceId(),firstUpfMainInfo.getNfInstanceName(), firstUpfMainInfo.getIp(), sst, sd);
        LOG.info("Associating UPF with the following payload");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(upfSliceAssociationCnc);
        LOG.info("Payload");
        LOG.info(json);
        String response = cnCrestClient.associateUpfToSlice(firstUpfMainInfo.getNfInstanceId(), upfSliceAssociationCnc);
        LOG.info("response "+response);
    }
    private Map<String, UpfMainInfo> getUpfInfo() throws IOException {
        CNCrestClient cnCrestClient = new  CNCrestClient(cncIp, cncPort);
        String dnnListString = cnCrestClient.getUpfsInfo();

        Map map = new HashMap();
        ObjectMapper om = new ObjectMapper();
        LOG.info("Raw payload ");
        LOG.info(dnnListString);
        DnnListCnc dnnListCnc = om.readValue(dnnListString, DnnListCnc.class);

        for(DnnList dnnList: dnnListCnc.dnnList){
            String nfIp = dnnList.ipv4Addresses.get(0);
            String nfUuid = dnnList.nfInstanceId;
            String nfInstanceName = dnnList.nfInstanceName;
            List<SNssai> sNssaiList = new ArrayList();
            LOG.info("nfIp "+nfIp);
            LOG.info("nfUuid"+ nfUuid);
            LOG.info("nfInstanceName "+nfInstanceName);

            for(SNssaiUpfInfoList sNssaiUpfInfoList: dnnList.upfInfo.sNssaiUpfInfoList){

                SNssai sNssai = new SNssai();
                sNssai.sst = sNssaiUpfInfoList.sNssai.sst;
                sNssai.sd = sNssaiUpfInfoList.sNssai.sd;
                LOG.info(String.valueOf(sNssai.sst));
                LOG.info(sNssai.sd);
                sNssaiList.add(sNssai);
            }
            UpfMainInfo upfMainInfo = new UpfMainInfo(nfUuid, nfInstanceName, nfIp, sNssaiList);
            map.put(nfUuid, upfMainInfo);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);
        LOG.info("json");
        LOG.info(json);

        return map;
    }

    public synchronized String createCoreNetworkSlice(String upfInstanceId, CoreSlicePayload coreSlicePayload) throws Exception {

        LOG.info("Instantiating a new core network slice");
        UpfInstanceInfo upfInstanceInfo;
        if(upfInstanceId.equals(configuredCoreInstanceId) && skipNsdDeployment==true){
            LOG.info("Core instance ID is that one configured");
            upfInstanceInfo = new UpfInstanceInfo();
            upfInstanceInfo.setUpfInstanceId(configuredCoreInstanceId);
            upfInstanceInfo.setUpfNetworkSliceId(new ArrayList<>());
            upfInstanceInfo.setNsdIdCore(null);
            upfInstanceInfo.setIpCnc(cncIp);
            upfInstanceInfo.setPortCnc(cncPort);
            upfInstanceInfo.setgNBList(new HashSet<>());
        }

        else {
            upfInstanceInfo = upfInstanceService.getUpfInstanceInfoById(upfInstanceId);
        }

        NST nst = coreSlicePayload.getNst();
        LOG.info("Validating payload");
        if(nst.getNstServiceProfileList()==null || nst.getNstServiceProfileList().size() == 0 || nst.getNstServiceProfileList().get(0)==null){
            LOG.error("nstServiceProfileList is mandatory and must contain at least one element");
            throw new MalformattedElementException("nstServiceProfileList is mandatory and must contain at least one element");
        }

        NSST nsst = getNsstCore(nst);
        NstServiceProfile nstServiceProfile = nst.getNstServiceProfileList().get(0);

        NstToNetworkSliceCmcMapper nstToNetworkSliceCmcMapper = new NstToNetworkSliceCmcMapper();
        String countUPFstr = String.valueOf(upfInstanceService.getUpfCount());
        NetworkSliceCNC networkSliceCNC = nstToNetworkSliceCmcMapper.mapCoreNetworkSliceToNetworkSliceCNC(nstServiceProfile, nsst, upfInstanceService.getUpfCount());

        LOG.info("Requesting Core Network Slice creation toward CNC");
        CNCrestClient cncRestClient = new CNCrestClient(upfInstanceInfo.getIpCnc(), upfInstanceInfo.getPortCnc());
        boolean isCoreNetworkSliceCreated = cncRestClient.createNetworkSlice(networkSliceCNC);
        printJsonObject(networkSliceCNC);
        if(isCoreNetworkSliceCreated) {
            LOG.info("Core network slice successfully created");
            updateCoreNetworkSliceList(upfInstanceId, networkSliceCNC.sliceName);
            upfInstanceService.storeUpfInstanceInfo(upfInstanceId, networkSliceCNC.sliceName, coreSlicePayload.getUpfName());
            upfInstanceService.storeCoreCncInfo(upfInstanceId, cncIp, cncPort);


            associateSliceToUpf("UPF-"+countUPFstr,networkSliceCNC.serviceProfile.sNSSAIList.get(0).sst, networkSliceCNC.serviceProfile.sNSSAIList.get(0).sd);

            return networkSliceCNC.sliceName;
        }
        else{
            final String ERR_MSG = "Error creating core network slice";
            LOG.error(ERR_MSG);
            throw new MalformattedElementException(ERR_MSG);
        }
    }

    public CNCrestClient getCncRestClient(String coreInstanceId) throws NotExistingEntityException {
        UpfInstanceInfo upfInstanceInfo = upfInstanceService.getUpfInstanceInfoByName(coreInstanceId);
        CNCrestClient cnCrestClient = new CNCrestClient(upfInstanceInfo.getIpCnc(), upfInstanceInfo.getPortCnc());
        return cnCrestClient;
    }


    public synchronized void deleteCoreNetworkSlice(String coreInstanceId, String coreNetworkSliceId) throws NotExistingEntityException, IOException, FailedOperationException {
        LOG.info("Deleting core network slice with UUID "+coreNetworkSliceId+" from core instance with ID "+coreInstanceId);
        if(!isCoreSliceExisting(coreInstanceId, coreNetworkSliceId)){
            throw new NotExistingEntityException("Slice with ID "+coreNetworkSliceId+" does not exist");
        }
        UpfInstanceInfo upfInstanceInfo = upfInstanceService.getUpfInstanceInfoByName(coreInstanceId);
        SubscriberListForSlice SubscriberListForSlice = getSubscribersForSlice(coreInstanceId, coreNetworkSliceId);
        LOG.info("Removing all subscribers related to core network slice "+coreNetworkSliceId);
        CNCrestClient cnCrestClient = new CNCrestClient(upfInstanceInfo.getIpCnc(), upfInstanceInfo.getPortCnc());
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
        upfInstanceService.removeCoreNetworkSliceFromUpfInstance(coreInstanceId, coreNetworkSliceId);

    }
}
