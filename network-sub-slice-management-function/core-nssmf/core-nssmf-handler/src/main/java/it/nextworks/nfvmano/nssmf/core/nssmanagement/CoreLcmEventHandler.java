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
package it.nextworks.nfvmano.nssmf.core.nssmanagement;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.core.recordIM.SubscriberListForSlice;
import it.nextworks.nfvmano.core.recordIM.UpfInstanceInfo;
import it.nextworks.nfvmano.core.service.CoreNetworkSliceService;
import it.nextworks.nfvmano.core.service.SubscriberService;
import it.nextworks.nfvmano.core.service.UpfInstanceService;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.CoreSlicePayload;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.Subscriber;
import it.nextworks.nfvmano.nssmf.osm.OsmLcmOperation;
import it.nextworks.nfvmano.nssmf.service.factory.DriverFactory;
import it.nextworks.nfvmano.nssmf.service.messages.BaseMessage;
import it.nextworks.nfvmano.nssmf.service.messages.notification.NssiStatusChangeNotiticationMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.InstantiateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.ModifyNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.TerminateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.nssmanagement.NssLcmEventHandler;
import it.nextworks.nfvmano.sbi.cnc.messages.NetworkSliceCNC;
import it.nextworks.nfvmano.sbi.cnc.operator.Operator;
import it.nextworks.nfvmano.sbi.cnc.rest.CNCrestClient;
import it.nextworks.nfvmano.sbi.nfvo.elements.NfvoDriverType;
import it.nextworks.nfvmano.sbi.nfvo.elements.NfvoInformation;
import it.nextworks.nfvmano.sbi.nfvo.osm.Osm10Client;
import it.nextworks.nfvmano.sbi.nfvo.polling.NfvoLcmNotificationsManager;
import it.nextworks.nfvmano.sbi.nfvo.polling.NfvoLcmOperationPollingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

public class CoreLcmEventHandler extends NssLcmEventHandler {

    //Config variable OSM related
    private String ipOsm;
    private String usernameOsm;
    private String passwordOsm;
    private String projectOsm;
    private String UUID_VIM_ACCOUNT;
    private OsmLcmOperation osmLcmOperation;

    //Config variable Core network related
    private String ipAddressCNC;
    private int portCNC;
    CNCrestClient cnCrestClient;

    private String upfInstanceId;

    boolean skipNetworkServiceDeployment;
    private String extCpCore;
    private String extMgmtCore;

    private CoreSlicePayload coreSlicePayload;
    private NSST nsstCore;

    private NfvoLcmOperationPollingManager nfvoLcmOperationPollingManager;
    private CoreLcmNotificationConsumer coreLcmNotificationConsumer;
    private NfvoLcmNotificationsManager nfvoLcmNotificationsManager;
    private CoreNetworkSliceService coreNetworkSliceService;
    private SubscriberService subscribersService;
    private UpfInstanceService upfInstanceService;

    private String networkSubSliceInstance; //Is the instance of the core
    private String networkServiceInstanceId;

    private final static Logger LOG = LoggerFactory.getLogger(CoreLcmEventHandler.class);
    private String nsdId;

    private boolean instantiateSliceAfterUpfDeployment;

    private boolean isModificationRequest;
    private String nssiIdToModify;
    public CoreLcmEventHandler(){ }


    public void setDriverFactory(DriverFactory driverFactory) {
        super.setDriverFactory(driverFactory);
        LOG.info("Getting OSM-related configuration variable.");
        ipOsm = getEnvironment().getProperty("nssmf.osm.baseurl");
        usernameOsm = getEnvironment().getProperty("nssmf.osm.username");
        passwordOsm = getEnvironment().getProperty("nssmf.osm.password");
        projectOsm = getEnvironment().getProperty("nssmf.osm.project");
        UUID_VIM_ACCOUNT = getEnvironment().getProperty("nssmf.osm.vim-id");

        LOG.info("Getting Core-related configuration variable.");
        skipNetworkServiceDeployment = Boolean.valueOf(getEnvironment().getProperty("nssmf.skipNsdDeployment"));
        upfInstanceId = getEnvironment().getProperty("nssmf.coreInstanceId");
        ipAddressCNC = getEnvironment().getProperty("nssmf.cnc.ip");
        portCNC = Integer.valueOf(getEnvironment().getProperty("nssmf.cnc.port"));
        extCpCore = getEnvironment().getProperty("nssmf.vim.ext-cp-core");
        extMgmtCore = getEnvironment().getProperty("nssmf.vim.ext-mgmt-core");

        LOG.info("Setting driver factory for cmc-5gc-handler");
        LOG.info("IP OSM: "+ipOsm);
        LOG.info("Username OSM: "+usernameOsm);
        LOG.info("PWD OSM: "+passwordOsm);
        LOG.info("ProjectOsm: "+projectOsm);

        NfvoInformation info = new NfvoInformation(ipOsm, UUID.fromString(UUID_VIM_ACCOUNT), usernameOsm, passwordOsm, NfvoDriverType.OSM10, projectOsm, "VM_MGMT");
        nfvoLcmOperationPollingManager = (NfvoLcmOperationPollingManager) driverFactory.getDriver("nfvoLcmOperationPollingManager");
        Osm10Client osm10Client = new Osm10Client(info, nfvoLcmOperationPollingManager);
        osmLcmOperation = new OsmLcmOperation(osm10Client);

        LOG.info("Getting driver");
        coreLcmNotificationConsumer = (CoreLcmNotificationConsumer) driverFactory.getDriver("coreLcmNotificationConsumer");
        nfvoLcmNotificationsManager = (NfvoLcmNotificationsManager) driverFactory.getDriver("nfvoLcmNotificationsManager");
        coreNetworkSliceService = (CoreNetworkSliceService)driverFactory.getDriver("coreNetworkSliceService");
        subscribersService = (SubscriberService)driverFactory.getDriver("subscriberService");
        upfInstanceService = (UpfInstanceService) driverFactory.getDriver("upfInstanceService");
        cnCrestClient = new CNCrestClient(ipAddressCNC,portCNC);
        setEnableAutoNotification(true);
        instantiateSliceAfterUpfDeployment = true;

    }




    private void pollingCNC(){
        //getIpAddressesCore();
        final int timeoutTimeMs = 10000;
        boolean isCNCreachable = isCNCreachable(ipAddressCNC, portCNC, timeoutTimeMs);
        while(!isCNCreachable){
            try {
                Thread.sleep(timeoutTimeMs);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            LOG.warn("CNC is not reachable yet. next check in "+timeoutTimeMs/1000+ " seconds");
            isCNCreachable = isCNCreachable(ipAddressCNC, portCNC, timeoutTimeMs);
        }

        LOG.info("CNC now is reachable!");
    }


    private void addOperators(List<Subscriber> subscribersToRegister){
        //final String OP = "F964BA9478CDC6C72EAF1E95DBC6674A";
        final String OP = "f964ba9478cdc6c72eaf1e95dbc6674a";

        final String AMF ="8000";
        final String nameOp ="Test network";
        Set<String> mccMncPairs = new HashSet<>();
        for(Subscriber subscriber: subscribersToRegister){
            String mccMncPair = subscriber.getImsi().substring(0,5);
            mccMncPairs.add(mccMncPair);
        }
        for(String mccMncPair: mccMncPairs){
            String mcc = mccMncPair.substring(0,3);
            String mnc = mccMncPair.substring(3,5);
            Operator operator = new Operator();
            operator.mcc = mcc;
            operator.mnc = mnc;
            operator.amf = AMF;
            operator.name = nameOp;
            operator.op = OP;
            LOG.info("Adding operator (mcc:"+mcc+", mnc:"+mnc+")");
            cnCrestClient.addOperator(operator);
        }
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
    public void initInstanceInfo() {
        boolean success;
        try {
            LOG.info("Creating new core network slice");
            SubscriberListForSlice subscriberListForSlice = new SubscriberListForSlice();
            String sliceName = nsstCore.getNsstId();
            subscriberListForSlice.setSliceName(sliceName);
            List<Subscriber> subscriberList = new ArrayList<>();

            coreNetworkSliceService.initNetworkCoreSliceSet(networkSubSliceInstance);
            upfInstanceService.storeCoreCncInfo(networkSubSliceInstance, ipAddressCNC, portCNC);
            coreNetworkSliceService.createCoreNetworkSlice(networkSubSliceInstance, coreSlicePayload);
            //coreNetworkSliceService.updateCoreNetworkSliceList(networkSubSliceInstance, nsstCore.getNsstId());
            //upfInstanceService.storeUpfInstanceInfo(networkSubSliceInstance,nsstCore.getNsstId(),coreSlicePayload.getUpfName() );

            List<Subscriber> subscribersToRegister = new ArrayList<>();
            coreNetworkSliceService.setNsdIdCore(networkSubSliceInstance, nsdId);

            addOperators(subscribersToRegister);
            if (subscribersToRegister == null || subscribersToRegister.size() == 0) {
                LOG.warn("No subscribers found into core network slice instantiation. Skipping subscriber registration");
            }
            else {
                LOG.info("Registering subscribers for core network slice "+sliceName);
                for (Subscriber subscriber : subscribersToRegister) {
                    subscriberList.add(subscriber);
                    subscriberListForSlice.setSubscriberList(subscriberList);
                }

                subscribersService.registerSubscribersForSlice(networkSubSliceInstance, sliceName, subscriberListForSlice);

            }
            success = true;
        } catch (MalformattedElementException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            success = false;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            success = false;
        }

        sendNotification(success);
    }




    public void coreNetworkSliceInstantiationTrigger() {

        if(!instantiateSliceAfterUpfDeployment){
            try {
                List<UpfInstanceInfo> upfInstanceInfoList = upfInstanceService.getAllUpfInstanceInfo();
                LOG.info("nssiIdToModify "+nssiIdToModify);
                boolean sliceFound = false;
                String sd = null;
                int sst=0;
                int upfCount = upfInstanceInfoList.size();
                for(UpfInstanceInfo upfInstanceInfo: upfInstanceInfoList){
                    String upfInstanceId = upfInstanceInfo.getUpfInstanceId();
                    String upfName = upfInstanceInfo.getUpfName();
                    String sliceNameCnc = upfInstanceInfo.getUpfNetworkSliceId().iterator().next();
                    LOG.info(upfInstanceId);
                    LOG.info(upfName);
                    LOG.info(sliceNameCnc);


                    for(NetworkSliceCNC networkSliceCNC: coreNetworkSliceService.getAllCoreNetworkSliceInformation(nssiIdToModify)){
                        if(networkSliceCNC.sliceName.equals(sliceNameCnc)){
                            sd = networkSliceCNC.serviceProfile.sNSSAIList.get(0).sd;
                            sst = networkSliceCNC.serviceProfile.sNSSAIList.get(0).sst;
                            sliceFound = true;
                            LOG.info(sd);
                            LOG.info(String.valueOf(sst));
                            break;
                        }
                    }
                    if(upfInstanceId.equals(nssiIdToModify) && sliceFound){
                        int newUpfCount = upfCount+1;
                        coreNetworkSliceService.associateSliceToUpf("UPF-"+newUpfCount,sst,sd);
                    }
                }

                NssiStatusChangeNotiticationMessage notification= new NssiStatusChangeNotiticationMessage();
                notification.setSuccess(true);

                processNssStatusChangeNotification(notification);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (MalformattedElementException e) {
                throw new RuntimeException(e);
            } catch (NotExistingEntityException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        pollingCNC();
        boolean success;
        try {
            LOG.info("Creating new core network slice");
            SubscriberListForSlice subscriberListForSlice = new SubscriberListForSlice();
            String sliceName = nsstCore.getNsstId();
            subscriberListForSlice.setSliceName(sliceName);
            //List<Subscriber> subscriberList = new ArrayList<>();

            coreNetworkSliceService.initNetworkCoreSliceSet(networkSubSliceInstance);
            upfInstanceService.storeCoreCncInfo(networkSubSliceInstance, cnCrestClient.getIpCNC(),cnCrestClient.getPortCNC());
            int upfCount = upfInstanceService.getUpfCount() +1;
            upfInstanceService.setUpfCount(upfCount);

            coreNetworkSliceService.createCoreNetworkSlice(networkSubSliceInstance, coreSlicePayload);
            //coreNetworkSliceService.updateCoreNetworkSliceList(networkSubSliceInstance, nsstCore.getNsstId());
            //upfInstanceService.storeUpfInstanceInfo(networkSubSliceInstance,nsstCore.getNsstId(),coreSlicePayload.getUpfName());

            //List<Subscriber> subscribersToRegister = new ArrayList<>();
            if(!isModificationRequest) {
                coreNetworkSliceService.setNsdIdCore(networkSubSliceInstance, nsdId);
            }
            /*if (subscribersToRegister == null || subscribersToRegister.size() == 0) {
                LOG.warn("No subscribers found into core network slice instantiation. Skipping subscriber registration");
            }
            else {
                LOG.info("Registering operator for the subscriber(s)");
                addOperators(subscribersToRegister);
                LOG.info("Registering subscribers for core network slice "+sliceName);
                for (Subscriber subscriber : subscribersToRegister) {
                    subscriberList.add(subscriber);
                    subscriberListForSlice.setSubscriberList(subscriberList);
                }

                subscribersService.registerSubscribersForSlice(networkSubSliceInstance, sliceName, subscriberListForSlice);
            }
            */


            success = true;
        } catch (MalformattedElementException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            success = false;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            success = false;
        }

        sendNotification(success);
    }


    private void sendNotification(boolean success){
        NssiStatusChangeNotiticationMessage notification= new NssiStatusChangeNotiticationMessage();
        notification.setSuccess(success);

        processNssStatusChangeNotification(notification);
    }


    private boolean isCNCreachable(String host, int port, int timeout) {
        LOG.info("Pinging CNC host at "+host+" at port "+port);
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {

            //LOG.error(e.getMessage());
            //e.printStackTrace();
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }

    private void getIpAddressesCore(){
        String ipAddressAmf = null;
        String ipAddressCNC;
        try {
            ipAddressAmf = osmLcmOperation.getIpAddressVNFonExtConnectionPoint(networkServiceInstanceId, extCpCore);
            ipAddressCNC = osmLcmOperation.getIpAddressVNFonExtConnectionPoint(networkServiceInstanceId, extMgmtCore);

            upfInstanceService.storeCoreCncInfo(networkSubSliceInstance,ipAddressCNC,portCNC );

        } catch (FailedOperationException | NotExistingEntityException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        if(ipAddressAmf==null)
            LOG.warn("Unable to find IP address for CP "+extCpCore);

    }


    private void registerNotificationConsumer(String currentNetworkServiceInstanceId){
        LOG.info("Registering Notification consumer");
        coreLcmNotificationConsumer.setOsmLcmEventHandler(currentNetworkServiceInstanceId, this);
        nfvoLcmNotificationsManager.registerNotificationConsumer(currentNetworkServiceInstanceId,coreLcmNotificationConsumer);
    }

    private void isPayloadValid(CoreSlicePayload coreSlicePayload) throws MalformattedElementException {
        NSST nsstCore = coreNetworkSliceService.getNsstCore(coreSlicePayload.getNst());

        if(nsstCore.getNsdInfo().getNsdId()==null){
            LOG.error("nsdID not provided");
            throw new MalformattedElementException("Error: nsdId not provided into body request.");
        }
        nsdId = nsstCore.getNsdInfo().getNsdId();
        if(coreSlicePayload.getNssiId()==null){
            LOG.error("NSSI not provided");
            throw new MalformattedElementException("Error: nsdId not provided into body request.");
        }

        if(nsstCore.getSliceProfileList().get(0)==null){
            LOG.error("Service profile not provided");
            throw new MalformattedElementException("Error: Service profile not provided into body request.");
        }


    }

    private String isNsdAlreadyUsed(String nsdIdToCheck){
        List<UpfInstanceInfo>upfInstanceInfoList = upfInstanceService.getAllUpfInstanceInfo();
        for(UpfInstanceInfo coreInstanceInfo: upfInstanceInfoList){
            if(nsdIdToCheck.equals(coreInstanceInfo.getNsdIdCore())){
                return coreInstanceInfo.getUpfInstanceId();
            }
        }
        return null;
    }

    @Override
    protected void processInstantiateNssRequest(InstantiateNssiRequestMessage message) {

        LOG.info("Processing request to instantiate new NSSI with ID {}", this.getNetworkSubSliceInstanceId().toString());
        instantiateSliceAfterUpfDeployment= true;
        coreSlicePayload = (CoreSlicePayload) message.getInstantiateNssiRequest();
        try {
            nsstCore = coreNetworkSliceService.getNsstCore(coreSlicePayload.getNst());
            isPayloadValid(coreSlicePayload);
            networkSubSliceInstance = coreSlicePayload.getNssiId().toString();


            if(skipNetworkServiceDeployment){
                LOG.info("Skipping NSD instantiation");
                initInstanceInfo();
                coreSlicePayload.getNssiId().toString();
                int upfCount = upfInstanceService.getUpfCount() +1;
                upfInstanceService.setUpfCount(upfCount);
                //coreNetworkSliceService.createCoreNetworkSlice(this.getNetworkSubSliceInstanceId().toString(), coreSlicePayload);
                sendNotification(true);
                return;
            }



            String networkServiceName = nsstCore.getNsdInfo().getNsdName();
            String nsdId = nsstCore.getNsdInfo().getNsdId();
            if(networkServiceName==null){
                LOG.warn("No network service name found. Providing a custom one");
                networkServiceName = "NS instance from "+nsdId;
            }

            LOG.info("Checking NSD identifier");
            String upfInstanceId = isNsdAlreadyUsed(nsdId);
            if(upfInstanceId==null) {
                networkServiceInstanceId = osmLcmOperation.createNetworkServiceInstanceIdentifier(networkServiceName,nsdId);
                LOG.info("Created network service identifier. Its value is "+networkServiceInstanceId);
                registerNotificationConsumer(networkServiceInstanceId);
                String operationId = osmLcmOperation.instantiateNetworkService(networkServiceInstanceId, nsdId, networkServiceName, UUID_VIM_ACCOUNT);
                LOG.info("Network Service instance identifier is " + operationId);
            }
            else{
                LOG.info("NSD already used. going to create a Core network slice");
                coreSlicePayload.getNssiId().toString();
                coreNetworkSliceService.createCoreNetworkSlice(upfInstanceId, coreSlicePayload);
                sendNotification(true);
            }
            }
                catch (FailedOperationException | MalformattedElementException e) {
                    LOG.error("The payload in the request is NOT valid.");
                    LOG.error(e.getMessage());
                    this.setNssiStatus(NssiStatus.NOT_INSTANTIATED);
            }
                catch (Exception e) {
                    LOG.error(e.getMessage());
                    e.printStackTrace();
                }


    }

    private void instantiateNewNetworkService(String nsdId){
        String operationId = null;
        try {
            String networkServiceInstanceIdThirdUpf = osmLcmOperation.createNetworkServiceInstanceIdentifier("UPF-3 network service",nsdId);
            registerNotificationConsumer(networkServiceInstanceIdThirdUpf);
            operationId = osmLcmOperation.instantiateNetworkService(networkServiceInstanceIdThirdUpf, nsdId, "UPF-3 network service", UUID_VIM_ACCOUNT);
            LOG.info("Network Service instance identifier is " + operationId);

        } catch (FailedOperationException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void processModifyNssRequest(ModifyNssiRequestMessage message) {
        LOG.info("Received request for modify NSS");
        nssiIdToModify = message.getModifyNssiRequest().getNssiId().toString();
        CoreSlicePayload coreSlicePayload = (CoreSlicePayload) message.getModifyNssiRequest();
        String nsdId = coreSlicePayload.getNst().getNsst().getNsdInfo().getNsdId();
        instantiateSliceAfterUpfDeployment = false;
        isModificationRequest = true;
        instantiateNewNetworkService(nsdId);
    }

    @Override
    protected void processTerminateNssRequest(TerminateNssiRequestMessage message) {
        log.info("Processing request to terminate network slice subnet instance with ID {}", message.getTerminateNssiRequest().getNssiId());
        String nssiIdToBeTerminated = message.getTerminateNssiRequest().getNssiId().toString();

        LOG.info("Validating terminate request.");
        if(!nssiIdToBeTerminated.equals(networkSubSliceInstance)){
            LOG.error("Identifier of network Sub slice instance to terminate ("+nssiIdToBeTerminated+") is different from the expected one ("+ networkSubSliceInstance +")");
            return;
        }

        setNssiStatus(NssiStatus.TERMINATING);
        LOG.info("removing previously instantiated slices. TODO");
        try {
            LOG.info("Dismissing 5GC instance");
            osmLcmOperation.performTerminateNetworkService(networkServiceInstanceId);
        } catch (FailedOperationException e) {
            e.printStackTrace();
            this.setNssiStatus(NssiStatus.ERROR);
        }


    }

    public void processNssStatusChangeNotification(NssiStatusChangeNotiticationMessage message){
        try {
            super.processNssStatusChangeNotification(message);

        } catch (NotExistingEntityException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void processNssSetConfigRequest(BaseMessage message) throws NotExistingEntityException {
        LOG.info("Received request for setting config request");
        super.processNssSetConfigRequest(message);
    }

    @Override
    protected void processNssUpdateConfigRequest(BaseMessage message) throws NotExistingEntityException {
        LOG.info("Received request for update config request");
        super.processNssUpdateConfigRequest(message);

    }

    @Override
    protected void processNssRemoveConfigRequest(BaseMessage message) throws NotExistingEntityException {
        super.processNssRemoveConfigRequest(message);
    }

}
