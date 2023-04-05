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
package it.nextworks.nfvmano.nssmf.osm.nssmanagement;

import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
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
import it.nextworks.nfvmano.nssmf.service.nbi.NsmfNotifier;
import it.nextworks.nfvmano.nssmf.service.nssmanagement.NssLcmEventHandler;
import it.nextworks.nfvmano.sbi.nfvo.elements.NfvoDriverType;
import it.nextworks.nfvmano.sbi.nfvo.elements.NfvoInformation;
import it.nextworks.nfvmano.sbi.nfvo.osm.Osm10Client;
import it.nextworks.nfvmano.sbi.nfvo.polling.NfvoLcmNotificationsManager;
import it.nextworks.nfvmano.sbi.nfvo.polling.NfvoLcmOperationPollingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


public class OsmLcmEventHandler extends NssLcmEventHandler {

    private String ipOsm;
    private String usernameOsm;
    private String passwordOsm;
    private String projectOsm;
    private String UUID_VIM_ACCOUNT;
    private final static Logger LOG = LoggerFactory.getLogger(OsmLcmEventHandler.class);
    private OsmLcmOperation osmLcmOperation;

    private NfvoLcmOperationPollingManager nfvoLcmOperationPollingManager;
    private OsmLcmNotificationConsumer osmLcmNotificationConsumer;
    private NfvoLcmNotificationsManager nfvoLcmNotificationsManager;

    private NsmfNotifier nsmfNotifier;

    private String networkSubSliceInstance;
    private String networkServiceInstanceId;

    public OsmLcmEventHandler(){
    }


    @Override
    public void setDriverFactory(DriverFactory driverFactory) {
        super.setDriverFactory(driverFactory);
        ipOsm = getEnvironment().getProperty("nssmf.osm.baseurl");
        usernameOsm = getEnvironment().getProperty("nssmf.osm.username");
        passwordOsm = getEnvironment().getProperty("nssmf.osm.password");
        projectOsm = getEnvironment().getProperty("nssmf.osm.project");
        UUID_VIM_ACCOUNT = getEnvironment().getProperty("nssmf.osm.vim-id");

        nfvoLcmOperationPollingManager = (NfvoLcmOperationPollingManager) driverFactory.getDriver("nfvoLcmOperationPollingManager");
        osmLcmNotificationConsumer = (OsmLcmNotificationConsumer) driverFactory.getDriver("osmLcmNotificationConsumer");
        nfvoLcmNotificationsManager = (NfvoLcmNotificationsManager) driverFactory.getDriver("nfvoLcmNotificationsManager");

        NfvoInformation info = new NfvoInformation(ipOsm, UUID.fromString(UUID_VIM_ACCOUNT), usernameOsm, passwordOsm, NfvoDriverType.OSM10, projectOsm, "VM_MGMT");
        Osm10Client osm10Client = new Osm10Client(info, nfvoLcmOperationPollingManager);
        osmLcmOperation = new OsmLcmOperation(osm10Client);
        nsmfNotifier = (NsmfNotifier) driverFactory.getDriver("nsmfNotifier");
        setEnableAutoNotification(true);
    }




    public void processNssStatusChangeNotification(NssiStatusChangeNotiticationMessage message){
        try {
            super.processNssStatusChangeNotification(message);
        } catch (NotExistingEntityException e) {
            e.printStackTrace();
        }
    }

    public void registerNotificationConsumer(){
            osmLcmNotificationConsumer.setOsmLcmEventHandler(networkServiceInstanceId, this);
            nfvoLcmNotificationsManager.registerNotificationConsumer(networkServiceInstanceId,osmLcmNotificationConsumer);
    }

    @Override
    protected void processInstantiateNssRequest(InstantiateNssiRequestMessage message) {
        LOG.debug("Processing request to instantiate new NSSI with ID {}", this.getNetworkSubSliceInstanceId().toString());
        OsmPayload osmPayload= (OsmPayload) message.getInstantiateNssiRequest();
        this.setNssiStatus(NssiStatus.INSTANTIATING);

        try {
            if(osmPayload.getNsdId()==null){
                throw new MalformattedElementException("Error: nsdId not provided into body request.");
            }
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

        } catch (FailedOperationException | MalformattedElementException e) {
            LOG.error("The payload in the request is NOT valid.");
            LOG.error(e.getMessage());
            this.setNssiStatus(NssiStatus.NOT_INSTANTIATED);
        }



    }


    @Override
    protected void processModifyNssRequest(ModifyNssiRequestMessage message) {
        LOG.debug("Precessing request to modify network slice subnet instance with ID {}", message.getModifyNssiRequest().getNssiId());

        UUID nssiId=message.getModifyNssiRequest().getNssiId();
        
        setNssiStatus(NssiStatus.UPDATING);

        //Implement logic: check payload and send request TODO
        LOG.info("Pretending to modify network service");

    }

    @Override
    protected void processTerminateNssRequest(TerminateNssiRequestMessage message) {
        log.info("Processing request to terminate network slice subnet instance with ID {}", message.getTerminateNssiRequest().getNssiId());
        String nssiIdToBeTerminated = message.getTerminateNssiRequest().getNssiId().toString();

        if(!nssiIdToBeTerminated.equals(networkSubSliceInstance)){
            LOG.error("Identifier of network Sub slice instance to terminate ("+nssiIdToBeTerminated+") is different from the expected one ("+ networkSubSliceInstance +")");
            return;
        }

        setNssiStatus(NssiStatus.TERMINATING);

        try {
            osmLcmOperation.performTerminateNetworkService(networkServiceInstanceId);
        } catch (FailedOperationException e) {
            e.printStackTrace();
            this.setNssiStatus(NssiStatus.ERROR);
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
