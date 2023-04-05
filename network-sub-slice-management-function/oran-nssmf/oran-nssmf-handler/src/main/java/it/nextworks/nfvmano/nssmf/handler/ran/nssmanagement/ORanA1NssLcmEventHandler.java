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
package it.nextworks.nfvmano.nssmf.handler.ran.nssmanagement;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.nssmf.handler.ran.elements.ORanNearRTRicPayload;
import it.nextworks.nfvmano.nssmf.handler.ran.elements.ORanPayloadWrapper;
import it.nextworks.nfvmano.nssmf.handler.ran.plugin.ORanA1PolicyTranslator;
import it.nextworks.nfvmano.nssmf.handler.ran.plugin.exception.ImpossibleToTranslateException;
import it.nextworks.nfvmano.nssmf.record.RecordServiceFactory;
import it.nextworks.nfvmano.nssmf.service.messages.notification.NssiStatusChangeNotiticationMessage;
import it.nextworks.nfvmano.nssmf.service.nssmanagement.NssLcmEventHandler;
import it.nextworks.nfvmano.nssmf.service.messages.BaseMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.InstantiateNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.ModifyNssiRequestMessage;
import it.nextworks.nfvmano.nssmf.service.messages.provisioning.TerminateNssiRequestMessage;
import it.nextworks.nfvmano.oran.policies.elements.Policy;
import it.nextworks.nfvmano.oran.policies.enums.PolicyTypeIdEnum;
import it.nextworks.nfvmano.oran.recordservice.OranNetworkSliceSubnetInstance;
import it.nextworks.nfvmano.oran.recordservice.PolicyTypeJsonSchema;
import it.nextworks.nfvmano.oran.recordservice.service.OranRecordService;
import it.nextworks.nfvmano.oran.restclients.OranA1RestClient;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ORanA1NssLcmEventHandler extends NssLcmEventHandler {

    private final static int QoSTarget=20030;
    private final static int QoETarget=20031;
    private final static int TSP=20032;
    private final static int QoSandTSP =20033;
    private final static int QoEandTSP =20034;

    private ORanA1PolicyTranslator translator;
    private OranA1RestClient restClient;
    private String nearRTRicUrl;
    private OranRecordService recordService;

    public ORanA1NssLcmEventHandler(){}

    @Override
    public void setRecordServiceFactory(RecordServiceFactory recordServiceFactory) {
        super.setRecordServiceFactory(recordServiceFactory);

        this.recordService = (OranRecordService) recordServiceFactory.getRecordService("oranRecordService");
        recordService.createOranNetworkSliceSubnetInstanceEntry(getNetworkSubSliceInstanceId());
    }

    private void setTranslationRequirements(){
        this.translator=new ORanA1PolicyTranslator();
        this.nearRTRicUrl =this.getEnvironment().getProperty("nearRTRic.host");
        log.debug(nearRTRicUrl);
        this.restClient=new OranA1RestClient(nearRTRicUrl);
    }

    @Override
    protected void processInstantiateNssRequest(InstantiateNssiRequestMessage message) throws NotExistingEntityException {
        log.debug("Processing request to instantiate new RAN NSSI with ID {}", this.getNetworkSubSliceInstanceId().toString());

        this.setTranslationRequirements();

        NssiStatusChangeNotiticationMessage notif= new NssiStatusChangeNotiticationMessage();
        ORanNearRTRicPayload payload= (ORanNearRTRicPayload) message.getInstantiateNssiRequest();
        try{
            payload.isValid();
            int policyTypeId= 20030;
            checkPolicyType(policyTypeId);
            for(ORanPayloadWrapper payloadWrapper: payload.getPayload()){
                Policy policy=translator.fromSliceProfileToPolicy(payloadWrapper.getSliceProfile(), payloadWrapper.getAdditionalParams());
                restClient.createSinglePolicy(policyTypeId, policy);
                Map.Entry<String, Map.Entry<Integer,String>> entry=new AbstractMap.SimpleEntry<>(payloadWrapper.getSliceProfile().getSliceProfileId(),
                        new AbstractMap.SimpleEntry<>(policyTypeId,policy.getPolicyId()));
                recordService.addSliceProfileToPolicyEntry(this.getNetworkSubSliceInstanceId(), entry);
            }
        } catch (ImpossibleToTranslateException e){
            log.error("It is not possible to translate the slice profile");
            notif.setSuccess(false);
            processNssStatusChangeNotification(notif);
        } catch (MalformattedElementException e){
            log.error("Additional parameters is missing, it cannot possible to generate policy");
            notif.setSuccess(false);
            processNssStatusChangeNotification(notif);
        } catch (NotPermittedOperationException e){
            log.error("Operation not permitted");
            notif.setSuccess(false);
            processNssStatusChangeNotification(notif);
        }
        notif.setSuccess(true);
        processNssStatusChangeNotification(notif);
    }

    @Override
    protected void processModifyNssRequest(ModifyNssiRequestMessage message) throws NotExistingEntityException {
        log.debug("Precessing request to modify network slice subnet instance with ID {}", message.getModifyNssiRequest().getNssiId());

        UUID nssiId=message.getModifyNssiRequest().getNssiId();
        NssiStatusChangeNotiticationMessage notif= new NssiStatusChangeNotiticationMessage();
        try{
            OranNetworkSliceSubnetInstance nssi=recordService.getNssInstance(nssiId);
            Map<String, Map.Entry<Integer, String>> map=nssi.getSliceProfileToPolicy();
            ORanNearRTRicPayload payload= (ORanNearRTRicPayload) message.getModifyNssiRequest();
            for(ORanPayloadWrapper p: payload.getPayload()){
                String sliceProfileId=p.getSliceProfile().getSliceProfileId();
                if(map.keySet().contains(sliceProfileId)){
                    String policyId=map.get(sliceProfileId).getValue();
                    Policy policy=translator.fromSliceProfileToPolicy(p.getSliceProfile(), p.getAdditionalParams());
                    policy.setPolicyId(policyId);
                    restClient.updateSinglePolicy(map.get(sliceProfileId).getKey(), policy);
                } else {
                    Policy policy=translator.fromSliceProfileToPolicy(p.getSliceProfile(), p.getAdditionalParams());
                    int policyTypeId=20030;
                    restClient.createSinglePolicy(policyTypeId, policy);
                    Map.Entry<String, Map.Entry<Integer,String>> entry=new AbstractMap.SimpleEntry<>(p.getSliceProfile().getSliceProfileId(),
                            new AbstractMap.SimpleEntry<>(policyTypeId,policy.getPolicyId()));
                    recordService.addSliceProfileToPolicyEntry(this.getNetworkSubSliceInstanceId(), entry);
                }
            }
            notif.setSuccess(true);
            processNssStatusChangeNotification(notif);
        } catch (NotExistingEntityException e){
            log.error("Network slice subnet instance with ID {} not found in DB", nssiId);
        } catch (ImpossibleToTranslateException e){
            log.error("It is not possible to translate the slice profile");
            notif.setSuccess(false);
            processNssStatusChangeNotification(notif);
        } catch (MalformattedElementException e){
            log.error("Additional parameters is missing, it cannot possible to generate policy");
            notif.setSuccess(false);
            processNssStatusChangeNotification(notif);
        } catch (NotPermittedOperationException e){
            log.error("Operation not permitted");
            notif.setSuccess(false);
            processNssStatusChangeNotification(notif);
        }
    }

    @Override
    protected void processTerminateNssRequest(TerminateNssiRequestMessage message) throws NotExistingEntityException {
        log.debug("Processing request to terminate network slice subnet instance with ID {}", message.getTerminateNssiRequest().getNssiId());
        UUID nssiId=message.getTerminateNssiRequest().getNssiId();
        NssiStatusChangeNotiticationMessage notif= new NssiStatusChangeNotiticationMessage();

        try {
            OranNetworkSliceSubnetInstance nssi = recordService.getNssInstance(nssiId);
            Map<String, Map.Entry<Integer, String>> map=nssi.getSliceProfileToPolicy();
            for(String sliceProfileId: map.keySet()){
                Map.Entry<Integer, String> entry=map.get(sliceProfileId);
                restClient.deleteSinglePolicy(entry.getKey(), entry.getValue());
                log.debug("Policy with ID {} correctly deleted", entry.getValue());
            }
        }catch (NotExistingEntityException e){
            log.error("Network slice subnet instance with ID {} not found in DB", nssiId);
        } catch (Exception e){
            log.error("Network slice subnet instance termination failed due to internal error");
            notif.setSuccess(false);
            processNssStatusChangeNotification(notif);
        }

        notif.setSuccess(true);
        processNssStatusChangeNotification(notif);
    }

    @Override
    protected void processNssSetConfigRequest(BaseMessage message) throws NotExistingEntityException {
        super.processNssSetConfigRequest(message);
    }

    @Override
    protected void processNssUpdateConfigRequest(BaseMessage message) throws NotExistingEntityException{
        super.processNssUpdateConfigRequest(message);
    }

    @Override
    protected void processNssRemoveConfigRequest(BaseMessage message) throws NotExistingEntityException{
        super.processNssRemoveConfigRequest(message);
    }

    public void checkPolicyType(int policyTypeId){
        log.debug("Checking if PolicyType with ID {} is present into Near-RT RIC", policyTypeId);
        List<Integer> policyTypeIds = restClient.queryAllPolicyTypeIdentifiers();
        if(!policyTypeIds.contains(policyTypeId)){
            log.debug("PolicyType with ID {} not found in Near-RT RIC. Sending request to add it", policyTypeId);
            try {
                PolicyTypeJsonSchema policyTypeJsonSchema;
                String jsonSchema;
                switch (policyTypeId) {
                    case QoSTarget:
                        policyTypeJsonSchema = recordService.getPolicyTypeJsonSchema(PolicyTypeIdEnum.ORAN_QoSTarget_102);
                        jsonSchema=policyTypeJsonSchema.getJsonSchema().toString();
                        log.debug("This is the JSON Schema {}", jsonSchema);
                        restClient.createPolicyType(20030, jsonSchema);
                        break;
                    case QoETarget:
                        policyTypeJsonSchema = recordService.getPolicyTypeJsonSchema(PolicyTypeIdEnum.ORAN_QoETarget_102);
                        jsonSchema=policyTypeJsonSchema.getJsonSchema().toString();
                        restClient.createPolicyType(20030,jsonSchema);
                        break;
                    case TSP:
                        policyTypeJsonSchema = recordService.getPolicyTypeJsonSchema(PolicyTypeIdEnum.ORAN_TrafficSteeringPreference_102);
                        jsonSchema=policyTypeJsonSchema.getJsonSchema().toString();
                        restClient.createPolicyType(20030,jsonSchema);
                        break;
                    case QoSandTSP:
                        policyTypeJsonSchema = recordService.getPolicyTypeJsonSchema(PolicyTypeIdEnum.ORAN_QoSandTSP_102);
                        jsonSchema=policyTypeJsonSchema.getJsonSchema().toString();
                        restClient.createPolicyType(20030,jsonSchema);
                        break;
                    case QoEandTSP:
                        policyTypeJsonSchema = recordService.getPolicyTypeJsonSchema(PolicyTypeIdEnum.ORAN_QoEandTSP_102);
                        jsonSchema=policyTypeJsonSchema.getJsonSchema().toString();
                        restClient.createPolicyType(20030,jsonSchema);
                        break;
                    default:
                        break;
                }
            }catch (NotExistingEntityException e){
                log.debug("Policy Type JSON Schema not found in DB");
            }
        } else
            log.debug("PolicyType with ID {} already presents in Near-RT RIC", policyTypeId);
    }
}
