package it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers.eve;

;
import io.swagger.elcm.client.model.ExecuteExperimentRequest;
import io.swagger.elcm.client.model.ExperimentExecution;
import io.swagger.elcm.client.model.ExperimentExecutionTimeslot;
import io.swagger.elcm.client.model.ExperimentSchedulingRequest;
import io.swagger.eveportal.client.model.BlueprintUserInformation;
import io.swagger.eveportal.client.model.KpiThreshold;
import io.swagger.eveportal.client.model.OnboardExpDescriptorRequest;
import it.nextworks.nfvmano.catalogue.blueprint.elements.ServiceConstraints;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.sebastian.vsfm.messages.InstantiateVsRequest;
import org.threeten.bp.OffsetDateTime;

import java.util.*;
import java.util.stream.Collectors;

public class EveTranslator {


    public static OnboardExpDescriptorRequest getExpDescriptor(InstantiateVsRequest instantiateVsRequest,
                                                               String tenantId,
                                                               VsDescriptor originalDescriptor,
                                                               String vsBlueprintId,
                                                               List<String> vsbParams,
                                                               Map<String, List<String>> tcbParams) throws MalformattedElementException {
        Map<String, String> params = instantiateVsRequest.getUserData();
        String name = originalDescriptor.getName()+"_expd";
        String version = originalDescriptor.getVersion();
        String blueprintId = params.get("blueprintId");

        List<String> kpiList = params.keySet().stream()
                .filter(currentKey -> currentKey.startsWith("expd.kpi."))
                .collect(Collectors.toList());
        Map<String, KpiThreshold> kpiThresholdMap= new HashMap<>();
        for(String kpiFullId : kpiList ){
            String kpiId = kpiFullId.split("\\.")[3];

            int lowerbound = Integer.MIN_VALUE;
            int upperbound = Integer.MAX_VALUE;
            String lowerboundKey = "expd.kpi."+kpiId+".lowerbound";
            String upperboundKey = "expd.kpi."+kpiId+".upperbound";
            if(params.containsKey(lowerboundKey))
                lowerbound=Integer.parseInt(params.get(lowerboundKey));

            if(params.containsKey(upperboundKey))
                upperbound=Integer.parseInt(params.get(upperboundKey));
            KpiThreshold kpiThreshold = new KpiThreshold();
            kpiThreshold.setLowerBound(lowerbound);
            kpiThreshold.setUpperBound(upperbound);
            kpiThresholdMap.put(kpiId, kpiThreshold);
        }

        OnboardExpDescriptorRequest expDescriptorReq = new OnboardExpDescriptorRequest();
        expDescriptorReq.setName(name);
        expDescriptorReq.setVersion(version);
        expDescriptorReq.setExperimentBlueprintId(blueprintId);
        io.swagger.eveportal.client.model.VsDescriptor vsDescriptor = new io.swagger.eveportal.client.model.VsDescriptor();
        vsDescriptor.setName(originalDescriptor.getName());
        vsDescriptor.setVersion(originalDescriptor.getVersion());
        vsDescriptor.setVsBlueprintId(vsBlueprintId);
        //vsDescriptor.setSst(io.swagger.eveportal.client.model.VsDescriptor.SstEnum.fromValue(originalDescriptor.get.toString()));
        vsDescriptor.setManagementType(io.swagger.eveportal.client.model.VsDescriptor.ManagementTypeEnum.valueOf(originalDescriptor.getManagementType().toString()));
        Map<String, String > qosParams = new HashMap<>();
        for(String vsbParam : vsbParams){
            if(!originalDescriptor.getQosParameters().containsKey(vsbParam)){

                throw new MalformattedElementException("Missing QoS parameter:"+vsbParam);
            }
            qosParams.put(vsbParam, originalDescriptor.getQosParameters().get(vsbParam));

        }
        vsDescriptor.setQosParameters(qosParams);
        List<io.swagger.eveportal.client.model.ServiceConstraints> serviceConstraints = new ArrayList<>();
        for(ServiceConstraints sc : originalDescriptor.getServiceConstraints()){
            io.swagger.eveportal.client.model.ServiceConstraints currentSc = new io.swagger.eveportal.client.model.ServiceConstraints();
            currentSc.setAtomicComponentId(sc.getAtomicComponentId());
            currentSc.setCanIncludeSharedElements(sc.isCanIncludeSharedElements());
            for(String provider : sc.getNonPreferredProviders()){
                currentSc.addNonPreferredProvidersItem(provider);
            }
            for(String provider : sc.getPreferredProviders()){
                currentSc.addPreferredProvidersItem(provider);
            }
            for(String provider : sc.getProhibitedProviders()){
                currentSc.addProhibitedProvidersItem(provider);
            }
            serviceConstraints.add(currentSc);
        }
        vsDescriptor.setServiceConstraints(serviceConstraints);
        io.swagger.eveportal.client.model.VsdSla.AvailabilityCoverageEnum coverage = null;
        io.swagger.eveportal.client.model.VsdSla.ServiceCreationTimeEnum creation = null;
        if(originalDescriptor.getSla()!=null && originalDescriptor.getSla().getAvailabilityCoverage()!=null){
            coverage= io.swagger.eveportal.client.model.VsdSla.AvailabilityCoverageEnum.fromValue(
                    originalDescriptor.getSla().getAvailabilityCoverage().toString());
        }
        if(originalDescriptor.getSla()!=null && originalDescriptor.getSla().getServiceCreationTime()!=null){
            creation = io.swagger.eveportal.client.model.VsdSla.ServiceCreationTimeEnum.fromValue(
                    originalDescriptor.getSla().getServiceCreationTime().toString());
        }


        io.swagger.eveportal.client.model.VsdSla sla = new io.swagger.eveportal.client.model.VsdSla();
        sla.setAvailabilityCoverage(coverage);
        sla.setLowCostRequired(originalDescriptor.getSla().isLowCostRequired());
        sla.setServiceCreationTime(creation);
        vsDescriptor.setSla(sla);

        List<BlueprintUserInformation> tcbInfos = new ArrayList<>();
        String tcbIds = instantiateVsRequest.getUserData().get("tc.tcBlueprintIds");
        for(String tcbId : tcbIds.split(",")){
            BlueprintUserInformation tcbInfo = new BlueprintUserInformation();
            tcbInfo.blueprintId(tcbId);
            for(String tcbParam : tcbParams.get(tcbId)){
                String parameterUrl = "tc."+tcbId+".params."+tcbParam;
                if(!instantiateVsRequest.getUserData().containsKey(parameterUrl)){
                    throw new MalformattedElementException("Missing TCB parameter:"+tcbId+" - "+tcbParam);
                }
                tcbInfo.putParametersItem(tcbParam,instantiateVsRequest.getUserData().get(parameterUrl) );
            }
            tcbInfos.add(tcbInfo);
        }

        expDescriptorReq.setTestCaseConfiguration(tcbInfos);
        expDescriptorReq.setVsDescriptor(vsDescriptor);
        expDescriptorReq.setTenantId(tenantId);
        expDescriptorReq.setKpiThresholds(kpiThresholdMap);

        return expDescriptorReq;
    }




    public static ExperimentSchedulingRequest translateExperimentRequest(InstantiateVsRequest vsRequest, String expdId,  String tenantId) throws MalformattedElementException {
        ExperimentSchedulingRequest request = new ExperimentSchedulingRequest();
        Map<String, String> userParams = vsRequest.getUserData();
        if(!userParams.containsKey("exp.useCase"))
            throw new MalformattedElementException("Request without useCase");

        request.setUseCase(userParams.get("exp.useCase"));

        if(userParams.containsKey("exp.targetSites")){

            for(String targetSite: userParams.get("exp.targetSites").split(",")){
                request.addTargetSitesItem(ExperimentSchedulingRequest.TargetSitesEnum.fromValue(targetSite));
            }

        }else throw new MalformattedElementException("Request without targetSites");

        if(!userParams.containsKey("exp.stopTime"))
            throw new MalformattedElementException("Request without stopTime");
        ExperimentExecutionTimeslot timeslot = new ExperimentExecutionTimeslot();
        timeslot.setStartTime(OffsetDateTime.now().toString());
        timeslot.setStopTime(userParams.get("exp.stopTime"));
        request.setProposedTimeSlot(timeslot);
        request.setExperimentName(vsRequest.getName());
        request.setExperimentDescriptorId(expdId);

        return request;
    }

    public static ExecuteExperimentRequest translateExperimentExecution(Map<String, String> userData, String expId ) throws MalformattedElementException {

        ExecuteExperimentRequest experimentExecution = new ExecuteExperimentRequest();
        String name = "5GROWTH-"+ UUID.randomUUID().toString();
        experimentExecution.setExecutionName(name);
        experimentExecution.setExperimentId(expId);

        String tcId = userData.get("tc.id");
        Map<String, String> tcParams = new HashMap<>();
        List<String> tcParamIds = userData.keySet().stream()
                .filter(currentKey -> currentKey.startsWith("tc.params."))

                .collect(Collectors.toList());
        for(String tcParamId : tcParamIds){
            tcParams.put(tcParamId.split("\\.")[3], userData.get(tcParamId));
        }
        Map<String, Map<String, String>> allParams = new HashMap<>();
        allParams.put(tcId, tcParams);
        experimentExecution.setTestCaseDescriptorConfiguration(allParams);
        return experimentExecution;

    }

    public static ExecuteExperimentRequest translateExperimentDeploy(Map<String, String> userData, String expId){
        ExecuteExperimentRequest experimentExecution = new ExecuteExperimentRequest();


        experimentExecution.setExperimentId(expId);
        return experimentExecution;
    }

    public static ExecuteExperimentRequest translateExperimentTerminate(Map<String, String> userData, String expId){
        ExecuteExperimentRequest experimentExecution = new ExecuteExperimentRequest();


        experimentExecution.setExperimentId(expId);
        return experimentExecution;
    }
}
