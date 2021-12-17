package it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.sm;

import io.swagger.client.slice_manager.ApiClient;
import io.swagger.client.slice_manager.ApiException;
import io.swagger.client.slice_manager.api.NetworkSliceInstanceNestBasedApi;
import io.swagger.client.slice_manager.api.NetworkSliceTypeNestApi;
import io.swagger.client.slice_manager.model.*;
import it.nextworks.nfvmano.catalogue.domainLayer.NspNbiType;
import it.nextworks.nfvmano.catalogue.template.elements.NsTemplateInfo;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.sebastian.nsmf.messages.*;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.AbstractNsmfDriver;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfLcmOperationPollingManager;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfType;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.sm.repo.SliceManagerTranslationInformation;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.sm.repo.SliceManagerTranslationInformationRepository;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.SonataRestClient;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements.SonataTranslationInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SliceManagerNsmfDriver extends AbstractNsmfDriver {
    private  NsmfLcmOperationPollingManager pollingManager;
    private SliceManagerTranslationInformationRepository translationInformationRepository;
    private static final Logger log = LoggerFactory.getLogger(SliceManagerNsmfDriver.class);

    private String baseUrl;

    public SliceManagerNsmfDriver(String domainId, String baseUrl, SliceManagerTranslationInformationRepository translationInformationRepository, NsmfLcmOperationPollingManager nsmfLcmOperationPollingManager) {
        super(NsmfType.SLICE_MANAGER, domainId);
        this.baseUrl= baseUrl;
        this.pollingManager = nsmfLcmOperationPollingManager;
        this.translationInformationRepository = translationInformationRepository;
    }

    @Override
    public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domainId, String s1) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.info("Processing request to create a new network slice instance identifier for NST {}", request.getNstId());
        request.isValid();

        String nsiId = UUID.randomUUID().toString();
        SliceManagerTranslationInformation newTranslationInformation = new SliceManagerTranslationInformation(nsiId, request.getNstId());
        translationInformationRepository.saveAndFlush(newTranslationInformation);
        log.info("Network slice instance instance identifier {} created", nsiId);
        return nsiId;
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String s1) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.info("Processing request to instantiate network slice instance {} for NST {}", request.getNsiId(), request.getNstId());
        request.isValid();
        SliceManagerTranslationInformation newTranslationInformation = translationInformationRepository.findByNsiId(request.getNsiId()).get();
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(baseUrl);
        NetworkSliceTypeNestApi nstApi = new NetworkSliceTypeNestApi();
        nstApi.setApiClient(apiClient);
        try {
            PostSlic3Type nest = nstApi.getSlic3Type(request.getNstId());
            String tenant = nest.getUserId();
            NetworkSliceInstanceNestBasedApi nestInstanceApi = new NetworkSliceInstanceNestBasedApi();
            nestInstanceApi.setApiClient(new ApiClient().setBasePath(baseUrl));

            Slic3InstanceInput instanceInput = new Slic3InstanceInput();
            instanceInput.setName(request.getNsiId());
            instanceInput.setSlic3TypeId(newTranslationInformation.getSliceManagerNestId());
            for(String paramId : request.getUserData().keySet()){
                if(paramId.startsWith("nest.param.")){

                    String paramName = paramId.replace("nest.param.","");

                    for(Slic3TypeInputConfigurableParameters conf : nest.getConfigurableParameters()){
                        if(conf.getParameterName().equals(paramName)){
                            Slic3InstanceInputConfigurableParameters param = new Slic3InstanceInputConfigurableParameters();
                            param.setParameterName(paramName);
                            if (conf.getParameterType().equals("integer"))
                                param.setParameterValue(request.getUserData().get(paramId));
                            else
                                param.setParameterValue(request.getUserData().get(paramId));
                            instanceInput.addConfigurableParametersItem(param);
                        }
                    }


                }
            }
            instanceInput.setUserId(tenant);
            PostSlic3Instance smInstance = nestInstanceApi.postSlic3Instance(instanceInput);

            newTranslationInformation.setSliceManagerId(smInstance.getId());
            translationInformationRepository.saveAndFlush(newTranslationInformation);
            pollingManager.addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, request.getNsiId(), "NSI_CREATION", domainId, NspNbiType.SLICE_MANAGER);
        } catch (ApiException e) {
           throw new FailedOperationException(e);
        }





    }

    @Override
    public void modifyNetworkSlice(ModifyNsiRequest modifyNsiRequest, String s, String s1) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {

    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest terminateNsiRequest, String s, String s1) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {

    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domain, String tenant) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
        log.info("Processing request to query network slice instances");
        request.isValid();
        String nsiId = null;
        String smInstanceId = null;
        String url;
        if (request.getFilter().getParameters().containsKey("NSI_ID"))
            nsiId = request.getFilter().getParameters().get("NSI_ID");
        SliceManagerTranslationInformation translationInformation = translationInformationRepository.findByNsiId(nsiId).get();
        NetworkSliceInstanceNestBasedApi nestInstanceApi = new NetworkSliceInstanceNestBasedApi();
        nestInstanceApi.setApiClient(new ApiClient().setBasePath(baseUrl));
        try {
           PostSlic3Instance smInstance =  nestInstanceApi.getSlic3Instance(translationInformation.getSliceManagerId());
            List<NetworkSliceInstance> nsInstances = new ArrayList<>();
            NetworkSliceInstance nsInstance = new NetworkSliceInstance(
                    translationInformation.getNsiId(),
                    translationInformation.getSliceManagerNestId(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    smInstance.getName(),
                    smInstance.getName(),
                    false,
                    new HashMap<>());
            //TODO: CHECK STATUS
            nsInstance.setStatus(NetworkSliceStatus.INSTANTIATED);
            return nsInstances;
        } catch (ApiException e) {
            log.error("Error instantiating network slice instance",e);
            throw new FailedOperationException(e);
        }

    }

    @Override
    public void configureNetworkSliceInstance(ConfigureNsiRequest configureNsiRequest, String s, String s1) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {

    }
}
