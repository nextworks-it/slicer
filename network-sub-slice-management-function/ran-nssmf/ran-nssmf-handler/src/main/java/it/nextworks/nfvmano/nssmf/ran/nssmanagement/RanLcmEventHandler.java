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
package it.nextworks.nfvmano.nssmf.ran.nssmanagement;

import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.ran.OsmPayload;
import it.nextworks.nfvmano.nssmf.osm.OsmLcmOperation;
import it.nextworks.nfvmano.nssmf.service.factory.DriverFactory;
import it.nextworks.nfvmano.nssmf.service.messages.BaseMessage;
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

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.UUID;

public class RanLcmEventHandler extends NssLcmEventHandler {

    //Config variable OSM related
    private String ipOsm;
    private String usernameOsm;
    private String passwordOsm;
    private String projectOsm;
    private String UUID_VIM_ACCOUNT;
    private OsmLcmOperation osmLcmOperation;

    private String extCpCore;
    private String extMgmtCore;

    private String nssmfBasePath;
    private String ipAddressInternal;
    private String ipAddressManagement;

    private OsmPayload osmPayload;

    private NfvoLcmOperationPollingManager nfvoLcmOperationPollingManager;
    private RanLcmNotificationConsumer ranLcmNotificationConsumer;
    private NfvoLcmNotificationsManager nfvoLcmNotificationsManager;

    private String networkSubSliceInstance; //Is the instance of the RAN sub slice
    private String networkServiceInstanceId;


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
        setEnableAutoNotification(true);
    }




    private void pollingCoreNssmf(){
        getIpAddressesRan();
        final int timeoutTimeMs = 10000;
        boolean isCorereachable = isCoreNssmfReachable();
        while(!isCorereachable){
            try {
                Thread.sleep(timeoutTimeMs);
            }
            catch (InterruptedException e) {
                LOG.error(e.getMessage());
                e.printStackTrace();
            }
            LOG.warn("Core NSSMF not reachable yet. next check in "+timeoutTimeMs/1000+ " seconds");
            isCorereachable = isCoreNssmfReachable();
        }

        LOG.info("NSSMF Core now is reachable!");
    }



    public boolean isCoreNssmfReachable(){
            try (Socket socket = new Socket()) {
                String host = nssmfBasePath.split(":")[0];
                int port = Integer.valueOf(nssmfBasePath.split(":")[1]);
                LOG.info("Pinging NSSMF Core at "+host+" at port "+port);
                socket.connect(new InetSocketAddress(host, port), 10000);
                return true;
            }
            catch (Exception e) {
                LOG.warn(e.getMessage());
                return false; // Either timeout or unreachable or failed DNS lookup.
            }
    }





    public void ranNetworkSliceInstantiationTrigger() {
        pollingCoreNssmf();

        NssmfCoreClient nssmfCoreClient = new NssmfCoreClient("http://"+nssmfBasePath+"/core/configuration/gnb");
        boolean success = nssmfCoreClient.configureRan(ipAddressInternal);

        NssiStatusChangeNotiticationMessage notification= new NssiStatusChangeNotiticationMessage();
        notification.setSuccess(success);
        processNssStatusChangeNotification(notification);
    }



    private void getIpAddressesRan() {
        try {
            ipAddressInternal = osmLcmOperation.getIpAddressVNFonExtConnectionPoint(networkServiceInstanceId, extCpCore);
            ipAddressManagement = osmLcmOperation.getIpAddressVNFonExtConnectionPoint(networkServiceInstanceId, extMgmtCore);

            if (ipAddressInternal == null)
                LOG.warn("Unable to find IP address for CP " + extCpCore);

        } catch (FailedOperationException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
    }

        private void registerNotificationConsumer(){
        ranLcmNotificationConsumer.setOsmLcmEventHandler(networkServiceInstanceId, this);
        nfvoLcmNotificationsManager.registerNotificationConsumer(networkServiceInstanceId, ranLcmNotificationConsumer);
    }


    @Override
    protected void processInstantiateNssRequest(InstantiateNssiRequestMessage message) {
        LOG.debug("Processing request to instantiate new NSSI with ID {}", this.getNetworkSubSliceInstanceId().toString());
        osmPayload = (OsmPayload) message.getInstantiateNssiRequest();
        setNssiStatus(NssiStatus.INSTANTIATING);

        try {
            networkSubSliceInstance = osmPayload.getNssiId().toString();

            String networkServiceName = osmPayload.getNetworkServiceName();
            String nsdId = osmPayload.getNsdId();
            if(networkServiceName==null){
                networkServiceName = "NS instance from "+nsdId;
            }
            networkServiceInstanceId = osmLcmOperation.createNetworkServiceInstanceIdentifier(networkServiceName,nsdId);
            LOG.info("Created network service identifier. Its value is "+networkServiceInstanceId);

            registerNotificationConsumer();


            String operationId = osmLcmOperation.instantiateNetworkService(networkServiceInstanceId, nsdId, networkServiceName, UUID_VIM_ACCOUNT);
            LOG.info("Network Service instance identifier is "+operationId);

        } catch (FailedOperationException e) {
            LOG.error("The payload in the request is NOT valid.");
            LOG.error(e.getMessage());
            this.setNssiStatus(NssiStatus.NOT_INSTANTIATED);
        }


    }



    @Override
    protected void processModifyNssRequest(ModifyNssiRequestMessage message) {
        LOG.warn("Process of modifying the network subsice is not implemented yet.");

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
