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
package it.nextworks.nfvmano.nssmf.service.nssmanagement;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NssiNotifType;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.NsmfNotificationMessage;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiErrors;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;
import it.nextworks.nfvmano.nssmf.service.factory.DriverFactory;
import it.nextworks.nfvmano.nssmf.service.messages.BaseMessage;
import it.nextworks.nfvmano.nssmf.service.messages.NssmfMessageType;
import it.nextworks.nfvmano.nssmf.service.messages.notification.NssiStatusChangeNotiticationMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.ModifyNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.InstantiateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.TerminateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.nbi.NsmfNotifier;
import it.nextworks.nfvmano.nssmf.record.RecordServiceFactory;
import it.nextworks.nfvmano.nssmf.record.services.system.NssiBaseRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.*;

/**
 * Class managing the events related to a nss
 *
 * @author Pietro G. Giardina
 *
 */
public abstract class NssLcmEventHandler {
    protected static final Logger log = LoggerFactory.getLogger(NssLcmEventHandler.class);
    private UUID networkSubSliceInstanceId;
    private NssiStatus nssiStatus = NssiStatus.NOT_INSTANTIATED;
    private EventBus eventBus; //required: it will be forwarded to other services for notifications
    private Map<NssiStatus, List<NssiStatus>> fsmMap;
    private Map<NssiStatus, Map<Boolean, NssiStatus>> fsmTransMap;
    private NsmfNotifier nsmfNotifier;
    private RecordServiceFactory recordServiceFactory;
    private NssiBaseRecordService nssiBaseRecordService;
    private boolean enableAutoNotification = false;
    private Environment env;
    private DriverFactory driverFactory;


    public NssLcmEventHandler() {
        initFsmMaps();
    }


    //Getters and Setters
    public void setEnableAutoNotification(boolean enableAutoNotification) {
        this.enableAutoNotification = enableAutoNotification;
    }

    public void setEnvironment(Environment env){
        this.env=env;
    }

    public Environment getEnvironment(){
        return env;
    }

    //It can be override to do something similar to setRecordServiceFactory method
    public void setDriverFactory(DriverFactory driverFactory){
        this.driverFactory=driverFactory;
    }

    public DriverFactory getDriverFactory(){
        return driverFactory;
    }

    public void setRecordServiceFactory(RecordServiceFactory recordServiceFactory) {
        this.recordServiceFactory = recordServiceFactory;

        //Init DB with the current status
        this.nssiBaseRecordService = (NssiBaseRecordService)recordServiceFactory.getRecordService("nssiBaseRecordService");
        nssiBaseRecordService.createNssiEntry(networkSubSliceInstanceId, nssiStatus);
    }
    public RecordServiceFactory getRecordServiceFactory() {
        return recordServiceFactory;
    }

    public UUID getNetworkSubSliceInstanceId() {
        return networkSubSliceInstanceId;
    }
    public void setNetworkSubSliceInstanceId(UUID networkSubSliceInstanceId) {
        this.networkSubSliceInstanceId = networkSubSliceInstanceId;
    }

    public NssiStatus getNssiStatus() {
        return nssiStatus;
    }
    public void setNssiStatus(NssiStatus nssiStatus) {
        this.nssiStatus = nssiStatus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }
    public EventBus getEventBus() {
        return eventBus;
    }

    public NsmfNotifier getNsmfNotifier() { return nsmfNotifier; }
    public void setNsmfNotifier(NsmfNotifier nsmfNotifier) { this.nsmfNotifier = nsmfNotifier; }

    private boolean validateTransition(){
        return fsmTransMap.containsKey(this.nssiStatus);
    }

    private NssiStatus getNextStableState(boolean success){
        return fsmTransMap.get(this.nssiStatus).get(success);
    }

    private boolean validateRequest(NssiStatus nextStatus) throws NotExistingEntityException {
        if (this.fsmMap.containsKey(this.nssiStatus) && this.fsmMap.get(this.nssiStatus).contains(nextStatus)){
            log.debug("["+networkSubSliceInstanceId.toString()+"] - Request allowed");
            //this.nssiBaseRecordService.nssiStatusUpdate(networkSubSliceInstanceId, nextStatus);
            this.nssiStatus = nextStatus;
            return true;
        }
        return false;
    }

    //Utility methods
    private void requestNotAllowed(){
        log.warn("["+networkSubSliceInstanceId.toString()+"] - Request NOT Allowed");
    }

    private void notifySuccess() throws NotExistingEntityException {
        NssiStatusChangeNotiticationMessage notif = new NssiStatusChangeNotiticationMessage();
        notif.setSuccess(true);
        this.processNssStatusChangeNotification(notif);
    }

    private void initFsmMaps(){
        fsmMap = new HashMap<>();
        fsmMap.put(NssiStatus.NOT_INSTANTIATED, Arrays.asList(NssiStatus.INSTANTIATING));
        fsmMap.put(NssiStatus.INSTANTIATED, Arrays.asList(NssiStatus.UPDATING, NssiStatus.CONFIGURING, NssiStatus.TERMINATING));
        fsmMap.put(NssiStatus.TERMINATED, Arrays.asList(NssiStatus.TERMINATED));

        fsmTransMap = new HashMap<>();
        Map<Boolean, NssiStatus> auxMap = new HashMap<>();
        //Instantiating
        auxMap.put(true, NssiStatus.INSTANTIATED);
        auxMap.put(false, NssiStatus.ERROR);
        fsmTransMap.put(NssiStatus.INSTANTIATING, auxMap);
        //Configuring
        auxMap = new HashMap<>();
        auxMap.put(true, NssiStatus.INSTANTIATED);
        auxMap.put(false, NssiStatus.ERROR);
        fsmTransMap.put(NssiStatus.CONFIGURING, auxMap);
        //Updating
        auxMap = new HashMap<>();
        auxMap.put(true, NssiStatus.INSTANTIATED);
        auxMap.put(false, NssiStatus.ERROR);
        fsmTransMap.put(NssiStatus.UPDATING, auxMap);
        //Terminating
        auxMap = new HashMap<>();
        auxMap.put(true, NssiStatus.TERMINATED);
        auxMap.put(false, NssiStatus.ERROR);
        fsmTransMap.put(NssiStatus.TERMINATING, auxMap);
    }


    @Subscribe
    private void eventHandler(BaseMessage message) throws NotExistingEntityException, FailedOperationException {
        log.info("Received message for NSI " + networkSubSliceInstanceId + "\n" + message);
        NssmfMessageType messageType = message.getMessageType();
        switch (messageType){
            case INSTANTIATE_NSSI_REQUEST: {
                log.info("["+networkSubSliceInstanceId.toString()+"] - Processing NSSI instantiation request.");
                if(validateRequest(NssiStatus.INSTANTIATING)) {
                    processInstantiateNssRequest((InstantiateNssiRequestMessage) message);
                }else {
                    this.requestNotAllowed();
                }
                break;
            }
            case MODIFY_NSSI_REQUEST: {
                log.info("["+networkSubSliceInstanceId.toString()+"] - Processing NSSI modification request.");
                if(validateRequest(NssiStatus.UPDATING)) {
                    processModifyNssRequest((ModifyNssiRequestMessage)message);
                }else {
                    this.requestNotAllowed();
                }
                break;
            }
            case TERMINATE_NSSI_REQUEST: {
                log.info("["+networkSubSliceInstanceId.toString()+"] - Processing NSSI termination request.");
                if(validateRequest(NssiStatus.TERMINATING)) {
                    processTerminateNssRequest((TerminateNssiRequestMessage)message);
                }else {
                    this.requestNotAllowed();
                }
                break;
            }
            case SET_NSSI_CONFIG: {
                log.info("["+networkSubSliceInstanceId.toString()+"] - Processing set NSSI configuration request.");

                if(validateRequest(NssiStatus.CONFIGURING)) {
                    processNssSetConfigRequest(message);
                }else {
                    this.requestNotAllowed();
                }
                break;
            }
            case UPDATE_NSSI_CONFIG: {
                log.info("["+networkSubSliceInstanceId.toString()+"] - Processing update NSSI configuration request.");

                if(validateRequest(NssiStatus.CONFIGURING)) {
                    processNssUpdateConfigRequest(message);
                }else {
                    this.requestNotAllowed();
                }
                break;
            }
            case REMOVE_NSSI_CONFIG: {
                log.info("["+networkSubSliceInstanceId.toString()+"] - Processing remove NSSI configuration request.");

                if(validateRequest(NssiStatus.CONFIGURING)) {
                    processNssRemoveConfigRequest(message);
                }else {
                    this.requestNotAllowed();
                }
                break;
            }
            case NOTIFY_NSSI_STATUS_CHANGE:
                log.info("["+networkSubSliceInstanceId.toString()+"] - Processing Notification.");
                processNssStatusChangeNotification((NssiStatusChangeNotiticationMessage)message);
                break;
            default:{
                log.error("["+networkSubSliceInstanceId.toString()+"] - Received message with not supported type. Skipping.");
                break;
            }
        }

    }

    //Process methods to be overridden
    protected void processInstantiateNssRequest(InstantiateNssiRequestMessage message) throws NotExistingEntityException {
        log.error("["+networkSubSliceInstanceId.toString()+"] -processInstantiateNssRequest not implemented." + message);
        this.notifySuccess();
    }
    protected void processModifyNssRequest(ModifyNssiRequestMessage message) throws NotExistingEntityException {
        log.error("["+networkSubSliceInstanceId.toString()+"] -processModifyNssRequest not implemented." + message);
        this.notifySuccess();
    }
    protected void processTerminateNssRequest(TerminateNssiRequestMessage message) throws NotExistingEntityException, FailedOperationException {
        log.error("["+networkSubSliceInstanceId.toString()+"] -processTerminateNssRequest not implemented." + message);
        this.notifySuccess();
    }
    protected void processNssSetConfigRequest(BaseMessage message) throws NotExistingEntityException {
        log.error("["+networkSubSliceInstanceId.toString()+"] -processNssSetConfigRequest not implemented."+ message);
        this.notifySuccess();
    }
    protected void processNssUpdateConfigRequest(BaseMessage message) throws NotExistingEntityException {
        log.error("["+networkSubSliceInstanceId.toString()+"] -processNssUpdateConfigRequest not implemented." + message);
        this.notifySuccess();
    }
    protected void processNssRemoveConfigRequest(BaseMessage message) throws NotExistingEntityException {
        log.error("["+networkSubSliceInstanceId.toString()+"] -processNssUpdateConfigRequest not implemented." + message);
        this.notifySuccess();
    }
    protected void processNssStatusChangeNotification(NssiStatusChangeNotiticationMessage message) throws NotExistingEntityException {
        if(validateTransition()){
            NssiStatus nextStatus = getNextStableState(message.isSuccess());
            if (nextStatus == NssiStatus.ERROR){
                log.error("["+networkSubSliceInstanceId.toString()+"] - is in ERROR state");
                this.nssiBaseRecordService.nssiSetError(networkSubSliceInstanceId, NssiErrors.STATUS_TRANSITION,
                        "Transitional State: " + this.nssiStatus.toString());
                this.nssiStatus = this.nssiBaseRecordService.getLastStatus(networkSubSliceInstanceId);
            }else{
                this.nssiBaseRecordService.nssiStatusUpdate(networkSubSliceInstanceId, nextStatus);
                this.nssiStatus = nextStatus;
            }
            if(enableAutoNotification){
                log.info("["+networkSubSliceInstanceId.toString()+"] - Send notification to NSMF");
                NsmfNotificationMessage notificationMessage = new NsmfNotificationMessage(networkSubSliceInstanceId,
                        NssiNotifType.STATUS_CHANGED, nextStatus);
                if(nextStatus == NssiStatus.ERROR) notificationMessage.setNssiError(NssiErrors.STATUS_TRANSITION);
                nsmfNotifier.notifyNsmf(notificationMessage);
            }
        }else
            log.warn("["+networkSubSliceInstanceId.toString()+"] - NSSI not in a transition state. Current state is "
                    +this.nssiStatus.toString() + ". Notification will be ignored.");
    }

}
