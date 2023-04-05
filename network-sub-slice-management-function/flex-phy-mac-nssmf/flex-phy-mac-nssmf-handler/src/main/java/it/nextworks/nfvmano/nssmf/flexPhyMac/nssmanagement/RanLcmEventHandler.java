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
package it.nextworks.nfvmano.nssmf.flexPhyMac.nssmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import it.nextworks.nfvmano.flexPhyMac.service.RanNetworkSubSliceService;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.ran.RanSlicePayload;
import it.nextworks.nfvmano.nssmf.osm.OsmLcmOperation;
import it.nextworks.nfvmano.nssmf.service.factory.DriverFactory;
import it.nextworks.nfvmano.nssmf.service.messages.BaseMessage;
import it.nextworks.nfvmano.nssmf.service.messages.NssmfMessageType;
import it.nextworks.nfvmano.nssmf.service.messages.notification.NssiStatusChangeNotiticationMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.InstantiateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.ModifyNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.TerminateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.nssmanagement.NssLcmEventHandler;
import it.nextworks.nfvmano.sbi.nfvo.elements.NfvoDriverType;
import it.nextworks.nfvmano.sbi.nfvo.elements.NfvoInformation;
import it.nextworks.nfvmano.sbi.nfvo.osm.Osm10Client;
import it.nextworks.nfvmano.sbi.nfvo.polling.NfvoLcmNotificationsManager;
import it.nextworks.nfvmano.sbi.nfvo.polling.NfvoLcmOperationPollingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class RanLcmEventHandler extends NssLcmEventHandler {

    //OSM related variables
    private String ipOsm;
    private String usernameOsm;
    private String passwordOsm;
    private String projectOsm;
    private String UUID_VIM_ACCOUNT;
    private OsmLcmOperation osmLcmOperation;
    private NfvoLcmOperationPollingManager nfvoLcmOperationPollingManager;
    private String networkServiceInstanceId;

    private NfvoLcmNotificationsManager nfvoLcmNotificationsManager;


    //Network service related valriable
    private String extCpCore;
    private String extMgmtCore;

    private String ipAddressInternal;

    private String nssmfBasePath;

    private String ipAddressManagement;

    private RanSlicePayload ranSlicePayload;

    private RanLcmNotificationConsumer ranLcmNotificationConsumer;

    private String networkSubSliceInstance; //Is the instance of the RAN sub slice


    private RanNetworkSubSliceService ranNetworkSubSliceService;

    private final static Logger LOG = LoggerFactory.getLogger(RanLcmEventHandler.class);

    public RanLcmEventHandler(){ }


    public void setDriverFactory(DriverFactory driverFactory) {
        super.setDriverFactory(driverFactory);
        LOG.info("Getting OSM-related configuration variable.");
        ipOsm = getEnvironment().getProperty("nssmf.osm.baseurl");
        usernameOsm = getEnvironment().getProperty("nssmf.osm.username");
        passwordOsm = getEnvironment().getProperty("nssmf.osm.password");
        projectOsm = getEnvironment().getProperty("nssmf.osm.project");
        UUID_VIM_ACCOUNT = getEnvironment().getProperty("nssmf.osm.vim-id");

        nssmfBasePath = getEnvironment().getProperty("nssmf.ran.nssmfCoreBasePath");

        LOG.info("Getting Connection point configuration variable.");
        extCpCore = getEnvironment().getProperty("nssmf.vim.ext-cp-core");
        extMgmtCore = getEnvironment().getProperty("nssmf.vim.ext-mgmt-core");

        LOG.info("Setting driver factory");
        NfvoInformation info = new NfvoInformation(ipOsm, UUID.fromString(UUID_VIM_ACCOUNT), usernameOsm, passwordOsm, NfvoDriverType.OSM10, projectOsm, "VM_MGMT");
        nfvoLcmOperationPollingManager = (NfvoLcmOperationPollingManager) driverFactory.getDriver("nfvoLcmOperationPollingManager");
        Osm10Client osm10Client = new Osm10Client(info, nfvoLcmOperationPollingManager);
        osmLcmOperation = new OsmLcmOperation(osm10Client);

        ranLcmNotificationConsumer = (RanLcmNotificationConsumer) driverFactory.getDriver("ranLcmNotificationConsumer");
        nfvoLcmNotificationsManager = (NfvoLcmNotificationsManager) driverFactory.getDriver("nfvoLcmNotificationsManager");
        ranNetworkSubSliceService = (RanNetworkSubSliceService) driverFactory.getDriver("ranNetworkSubSliceService");


        setEnableAutoNotification(true);
    }

    private void registerNotificationConsumer(){
        ranLcmNotificationConsumer.setOsmLcmEventHandler(networkServiceInstanceId, this);
        nfvoLcmNotificationsManager.registerNotificationConsumer(networkServiceInstanceId, ranLcmNotificationConsumer);
    }


    public void ranNetworkSliceInstantiationTrigger() {//called when the network service is successfully instantiated
        //pollingCoreNssmf();
        NssiStatusChangeNotiticationMessage notification= new NssiStatusChangeNotiticationMessage();
        notification.setSuccess(true);
        setNssiStatus(NssiStatus.INSTANTIATED);
        processNssStatusChangeNotification(notification);

        try {
            ranNetworkSubSliceService.setNetworkServiceInstanceId(networkServiceInstanceId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }


    private boolean isValid(Object request){
        //TODO implement logic
        return true;
    }




    @Override
    protected void processInstantiateNssRequest(InstantiateNssiRequestMessage message) {
        String nssiId = this.getNetworkSubSliceInstanceId().toString();
        LOG.info("Processing request to instantiate NSSI with ID {}", nssiId);
        if(ranNetworkSubSliceService.isExistingRanSubSlice(nssiId)){
            LOG.error("NSSI with ID "+nssiId+" already exists");
        }

        if(!isValid(message)){
            LOG.error("Cannot instantiate Network sub slice");
            return;
        }
        ranSlicePayload = (RanSlicePayload) message.getInstantiateNssiRequest();
        setNssiStatus(NssiStatus.INSTANTIATING);

        try {
            NSST nsst = ranSlicePayload.getNsst();
            networkSubSliceInstance = nssiId;

            //Case A: the network service where the RAN is not running yet
            boolean skipUeransimInstsantiation = true;
            if(ranNetworkSubSliceService.getNetworkServiceInstanceId()==null){


                if(skipUeransimInstsantiation) {
                    ranNetworkSubSliceService.setNetworkServiceInstanceId("NetworkServiceIdPlaceHolder");
                    LOG.info("Assuming UERANSIM already deployed");

                    NssiStatusChangeNotiticationMessage notification= new NssiStatusChangeNotiticationMessage();

                    notification.setMessageType(NssmfMessageType.INSTANTIATE_NSSI_REQUEST);
                    notification.setSuccess(true);

                    processNssStatusChangeNotification(notification);

                    setNssiStatus(NssiStatus.INSTANTIATED);
                    LOG.info("RAN subslice status set to INSTANTIATED");

                }
                else {
                    LOG.info("UERANSIM instance not available. Going to instantiate corresponding Network Service");
                    String nsdId = nsst.getNsdInfo().getNsdId();
                    String networkServiceName = nsst.getNsdInfo().getNsdName();

                    LOG.info("NSD ID "+nsdId);
                    LOG.info("Network Service name "+networkServiceName);
                    instantiateNetworkService(nsdId, networkServiceName);
                }
                ranNetworkSubSliceService.storeRanNssiInformation(networkSubSliceInstance, nsst);
            }
            //Case B: the network service where the RAN is running is not running yet
            else{
                LOG.info("UERANSIM instance available. Going to provisioning a RAN subslice");


                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(ranSlicePayload);
                LOG.info(json);

                ranNetworkSubSliceService.createRanNetworkSubSlice(nssiId, ranSlicePayload.getNsst());

                NssiStatusChangeNotiticationMessage notification= new NssiStatusChangeNotiticationMessage();
                notification.setMessageType(NssmfMessageType.INSTANTIATE_NSSI_REQUEST);
                notification.setSuccess(true);

                processNssStatusChangeNotification(notification);

                setNssiStatus(NssiStatus.INSTANTIATED);
                LOG.info("RAN subslice status set to INSTANTIATED");
            }

        } catch (FailedOperationException e) {
            LOG.error("The payload in the request is NOT valid.");
            LOG.error(e.getMessage());
            this.setNssiStatus(NssiStatus.NOT_INSTANTIATED);
        } catch (Exception e) {
            LOG.error("Error during the RAN Network subslice instantiation.");
            LOG.error(e.getMessage());
            this.setNssiStatus(NssiStatus.NOT_INSTANTIATED);
        }
    }

    private void instantiateNetworkService(String nsdId, String networkServiceName) throws FailedOperationException {
        if(networkServiceName==null)
            networkServiceName = "NS instance from "+nsdId;
        networkServiceInstanceId = osmLcmOperation.createNetworkServiceInstanceIdentifier(networkServiceName,nsdId);
        LOG.info("Created network service identifier. Its value is "+networkServiceInstanceId);
        registerNotificationConsumer();
        String operationId = osmLcmOperation.instantiateNetworkService(networkServiceInstanceId, nsdId, networkServiceName, UUID_VIM_ACCOUNT);
        LOG.info("Network Service instance identifier is "+operationId);

    }


    @Override
    protected void processModifyNssRequest(ModifyNssiRequestMessage message) {
        LOG.warn("Processing the modifying network subslice request.");
        RanSlicePayload ranSlicePayload = (RanSlicePayload) message.getModifyNssiRequest();
        String nssiId = ranSlicePayload.getNssiId().toString();
        if(!ranNetworkSubSliceService.isExistingRanSubSlice(nssiId)){
            LOG.error("NSSI with ID "+nssiId+" does NOT exists");
            return;
        }
        try {
            ranNetworkSubSliceService.updateRanNetworkSubSlice(nssiId, ranSlicePayload.getNsst());
            LOG.info("RAN subslice status set to INSTANTIATED");
            setNssiStatus(NssiStatus.INSTANTIATED);

        } catch (Exception e) {
            LOG.error("Error during the modifying process of the network sub-slice whose id is "+nssiId);
            LOG.info("Even if error is occured, RAN subslice status set to INSTANTIATED ");
            setNssiStatus(NssiStatus.INSTANTIATED);
            throw new RuntimeException(e);
        }


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

        setNssiStatus(NssiStatus.TERMINATED);
        if(networkServiceInstanceId==null){
            LOG.warn("No network Service Instance available");
            return;
        }
        try {
            osmLcmOperation.performTerminateNetworkService(networkServiceInstanceId);
        } catch (FailedOperationException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
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
        super.processNssSetConfigRequest(message);
    }

    @Override
    protected void processNssUpdateConfigRequest(BaseMessage message) throws NotExistingEntityException {
        super.processNssUpdateConfigRequest(message);
    }

    @Override
    protected void processNssRemoveConfigRequest(BaseMessage message) throws NotExistingEntityException {
        super.processNssRemoveConfigRequest(message);
    }

}
