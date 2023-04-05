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

package it.nextworks.nfvmano.nssmf.service;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.nssmf.interfaces.NssmfLcmConfigInterface;
import it.nextworks.nfvmano.libs.vs.common.nssmf.interfaces.NssmfLcmProvisioningInterface;
import it.nextworks.nfvmano.nssmf.service.factory.DriverFactory;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.ModifyNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.InstantiateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.TerminateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.nbi.NsmfNotifier;
import it.nextworks.nfvmano.nssmf.record.RecordServiceFactory;
import it.nextworks.nfvmano.nssmf.service.nssmanagement.NssLcmEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.vs.common.utils.DynamicClassManager;
import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.configuration.RemoveConfigRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.configuration.SetConfigRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.configuration.UpdateConfigRequest;


import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;

/**
 * Class implementing the nss lcm with base provisioning and configuration interfaces.
 * Monitor interface has not yet been defined (12 Nov 2021)
 *
 * @author Pietro G. Giardina
 */

@Service
public class NssmfLcmService implements NssmfLcmProvisioningInterface, NssmfLcmConfigInterface {

    private static final Logger log = LoggerFactory.getLogger(NssmfLcmService.class);

    @Value("${nssmf.eventhandler.class}")
    private String specializedEventHandlerClass;
    private Map<UUID, NssLcmEventHandler> nssLcmEventHandlers = new HashMap<UUID, NssLcmEventHandler>();

    @Autowired
    private RecordServiceFactory recordServiceFactory;
    @Autowired
    private NsmfNotifier nsmfNotifier;

    @Autowired
    private Environment env;
    @Autowired
    private DriverFactory driverFactory;

    /**
     *
     * @param nssId Id of the Nssi to be checked
     * @return boolean
     */
    private boolean checkNssIdExistence(UUID nssId) {
        return nssLcmEventHandlers.containsKey(nssId);
    }

    /**
     * Generates a unique identifier for the new Nssi
     * @return ID in UUID format
     */
    private UUID generateNssIdentifier(){
        UUID nssId = null;
        do {
            nssId = UUID.randomUUID();
        }while (checkNssIdExistence(nssId));

        return nssId;
    }

    /**
     * Instantiate a specialized event handler defined by the specializedEventHandlerClass
     * @return A generic NssLcmEventHandler object
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private NssLcmEventHandler instantiateSpecializedEventHandler() throws IllegalAccessException, InstantiationException,
            ClassNotFoundException {
        return (NssLcmEventHandler) DynamicClassManager.instantiateFromString(this.specializedEventHandlerClass);

    }
    @PostConstruct
    private void loadConfig(){
        log.info("Startup Configuration loading");
        try{
            if(!DynamicClassManager.checkClassType(this.specializedEventHandlerClass, NssLcmEventHandler.class)){
                log.error("Error in loading class "+ this.specializedEventHandlerClass + ": NOT compatible with " +
                        NssLcmEventHandler.class.getName() + "\n NSSMF is shutting down!");
                System.exit(0);
            }
            log.debug("Class "+ this.specializedEventHandlerClass + ": is compatible with " + NssLcmEventHandler.class.getName());

        } catch (ClassNotFoundException e) {
            log.error("Error in loading class "+ this.specializedEventHandlerClass + ": class NOT Found!\nNSSMF is shutting down");
            System.exit(0);
        }

    }

//    public void setSpecializedEventHandlerClass(String specializedEventHandlerClass) {
//        this.specializedEventHandlerClass = specializedEventHandlerClass;
//    }

    @Override
    public UUID createNetworkSubSliceIdentifier() throws MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException, IllegalAccessException, ClassNotFoundException,
            InstantiationException {
        log.debug("Processing NSSI ID creation request");

        EventBus eventBus = new AsyncEventBus(new Executor() {
            @Override
            public void execute(Runnable runnable) {
                new Thread(runnable).start();
            }
        });
        UUID nssId = generateNssIdentifier();

        NssLcmEventHandler eventHandler = this.instantiateSpecializedEventHandler();
        eventHandler.setNetworkSubSliceInstanceId(nssId);
        eventHandler.setEventBus(eventBus);
        eventHandler.setRecordServiceFactory(recordServiceFactory);
        eventHandler.setNsmfNotifier(nsmfNotifier);
        eventHandler.setEnvironment(env);
        eventHandler.setDriverFactory(driverFactory);
        eventBus.register(eventHandler);
        nssLcmEventHandlers.put(nssId, eventHandler);
        log.debug("New NSSI ID created: " + nssId.toString());
        return nssId;
    }

    @Override
    public void instantiateNetworkSubSlice(NssmfBaseProvisioningMessage request) throws MethodNotImplementedException,
            FailedOperationException, MalformattedElementException, NotPermittedOperationException,
            AlreadyExistingEntityException, NotExistingEntityException {

        request.isValid();
        UUID nssiId = request.getNssiId();
        log.debug("Processing NSSI ID " + nssiId.toString() + " instantiation request");
        if (!this.checkNssIdExistence(nssiId))
            throw new NotExistingEntityException("NSSI Instantiation error: NSSI ID "+ nssiId.toString() +" not found");

        InstantiateNssiRequestMessage instantiateNssiRequestMessage = new InstantiateNssiRequestMessage();
        instantiateNssiRequestMessage.setInstantiateNssiRequest(request);
        EventBus eventBus = nssLcmEventHandlers.get(nssiId).getEventBus();
        eventBus.post(instantiateNssiRequestMessage);
        log.debug("Instantiation request sent for NSSI ID " + nssiId.toString());
    }

    @Override
    public void modifyNetworkSlice(NssmfBaseProvisioningMessage request) throws NotExistingEntityException,
            MethodNotImplementedException, FailedOperationException, MalformattedElementException,
            NotPermittedOperationException {
        request.isValid();
        UUID nssiId = request.getNssiId();
        log.debug("Processing NSSI ID " + nssiId.toString() + " modification request");
        if (!this.checkNssIdExistence(nssiId))
            throw new NotExistingEntityException("NSSI Modification/Update error: NSSI ID "+ nssiId.toString() +" not found");

        ModifyNssiRequestMessage modifyNssiRequestMessage = new ModifyNssiRequestMessage();
        modifyNssiRequestMessage.setModifyNssiRequest(request);
        EventBus eventBus = nssLcmEventHandlers.get(nssiId).getEventBus();
        eventBus.post(modifyNssiRequestMessage);
        log.debug("Modification/Update request sent for NSSI ID " + nssiId.toString());
    }

    @Override
    public void terminateNetworkSliceInstance(NssmfBaseProvisioningMessage request) throws NotExistingEntityException,
            MethodNotImplementedException, FailedOperationException, MalformattedElementException,
            NotPermittedOperationException {
        request.isValid();
        UUID nssiId = request.getNssiId();
        log.debug("Processing NSSI ID " + nssiId.toString() + " termination request");
        if (!this.checkNssIdExistence(nssiId))
            throw new NotExistingEntityException("NSSI Termination error: NSSI ID "+ nssiId.toString() +" not found");

        TerminateNssiRequestMessage terminateNssiRequestMessage = new TerminateNssiRequestMessage();
        terminateNssiRequestMessage.setTerminateNssiRequest(request);
        EventBus eventBus = nssLcmEventHandlers.get(nssiId).getEventBus();
        eventBus.post(terminateNssiRequestMessage);
        log.debug("Termination request sent for NSSI ID " + nssiId.toString());

    }
    @Override
    public void setNetworkSubSliceConfiguration(SetConfigRequest request) throws MethodNotImplementedException,
            FailedOperationException, MalformattedElementException, NotPermittedOperationException,
            NotExistingEntityException, AlreadyExistingEntityException {

    }

    @Override
    public void updateNetworkSubSliceConfiguration(UpdateConfigRequest request) throws MethodNotImplementedException,
            FailedOperationException, MalformattedElementException, NotPermittedOperationException,
            NotExistingEntityException {

    }

    @Override
    public void removeNetworkSubSliceConfiguration(RemoveConfigRequest request) throws MethodNotImplementedException,
            FailedOperationException, MalformattedElementException, NotPermittedOperationException,
            NotExistingEntityException {

    }
}
