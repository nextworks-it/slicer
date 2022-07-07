package it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.smfsm;

import io.swagger.client.slice_manager.v2.api.ApplicationInstanceApi;
import io.swagger.client.slice_manager.v2.invoker.ApiClient;
import io.swagger.client.slice_manager.v2.api.NetworkSliceInstanceNestBasedApi;
import io.swagger.client.slice_manager.v2.api.NetworkSliceTypeNestApi;
import io.swagger.client.slice_manager.v2.model.*;
import it.nextworks.nfvmano.catalogue.domainLayer.NspNbiType;
import it.nextworks.nfvmano.catalogue.template.messages.QueryNsTemplateResponse;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.*;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.AbstractNsmfDriver;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfLcmOperationPollingManager;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfType;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.smfsm.repo.SliceManagerFsmTranslationInformation;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.smfsm.repo.SliceManagerFsmTranslationInformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

import java.util.*;
import java.util.stream.Collectors;

public class SliceManagerFsmNsmfDriver extends AbstractNsmfDriver {
    private NsmfLcmOperationPollingManager pollingManager;
    private SliceManagerFsmTranslationInformationRepository translationInformationRepository;
    private static final Logger log = LoggerFactory.getLogger(SliceManagerFsmNsmfDriver.class);
    private NsTemplateCatalogueService nsTemplateCatalogueService;
    private String baseUrl;

    public SliceManagerFsmNsmfDriver(String domainId, String baseUrl,
                                     SliceManagerFsmTranslationInformationRepository translationInformationRepository,
                                     NsmfLcmOperationPollingManager nsmfLcmOperationPollingManager,
                                     NsTemplateCatalogueService nsTemplateCatalogueService) {
        super(NsmfType.SLICE_MANAGER_FSM, domainId);
        this.baseUrl = baseUrl;
        this.pollingManager = nsmfLcmOperationPollingManager;
        this.translationInformationRepository = translationInformationRepository;
        this.nsTemplateCatalogueService = nsTemplateCatalogueService;
    }

    @Override
    public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domainId, String s1) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.info("Processing request to create a new network slice instance identifier for NST {}", request.getNstId());
        request.isValid();

        String nsiId = UUID.randomUUID().toString();
        SliceManagerFsmTranslationInformation newTranslationInformation = new SliceManagerFsmTranslationInformation(nsiId, request.getNstId());
        translationInformationRepository.saveAndFlush(newTranslationInformation);
        log.info("Network slice instance instance identifier {} created", nsiId);

        return nsiId;
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String s1) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.info("Processing request to instantiate network slice instance {} for NST {}", request.getNsiId(), request.getNstId());
        request.isValid();

        SliceManagerFsmTranslationInformation newTranslationInformation = translationInformationRepository.findByNsiId(request.getNsiId()).get();
        ApiClient apiClient = getApiClient();
        ;
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
            for (String paramId : request.getUserData().keySet()) {
                if (paramId.startsWith("sm.")) {

                    String paramName = paramId.replace("sm.", "");
                    String paramValue = request.getUserData().get(paramId);
                    for (ConfigurableParameter conf : nest.getConfigurableParameters()) {
                        if (conf.getParameterName().equals(paramName)) {
                            ConfigurableInputParameter param = null;

                            if (conf.getParameterType().equals("integer")) {
                                param = new StringConfigurableInputParameter();
                                param.setParameterName(paramName);
                                ((StringConfigurableInputParameter) param).setParameterValue(paramValue);
                            } else if (conf.getParameterType().equals("string")) {
                                param = new StringConfigurableInputParameter();
                                param.setParameterName(paramName);
                                ((StringConfigurableInputParameter) param).setParameterValue(paramValue);
                            } else if (conf.getParameterType().equals("array")) {
                                param = new ArrayConfigurableInputParameter();
                                param.setParameterName(paramName);
                                ((ArrayConfigurableInputParameter) param).setParameterValue(Arrays.stream(paramValue.split(",")).collect(Collectors.toList()));
                            }
                            instanceInput.addConfigurableParametersItem(param);
                        }
                    }


                }
            }
            instanceInput.setUserId(tenant);
            PostSlic3Instance smInstance = nestInstanceApi.postSlic3Instance(instanceInput);

            newTranslationInformation.setSliceManagerId(smInstance.getId());

            Map<String, String> nstQueryParams = new HashMap<>();
            nstQueryParams.put("NST_ID", request.getNstId());
            Filter nstFilter = new Filter(nstQueryParams);
            GeneralizedQueryRequest nstQuery = new GeneralizedQueryRequest(nstFilter, null);
            QueryNsTemplateResponse queryResponse = nsTemplateCatalogueService.queryNsTemplate(nstQuery);
            String nsdId = queryResponse.getNsTemplateInfos().get(0).getNST().getNsdId();
            newTranslationInformation.setSliceManagerNsdId(nsdId);
            newTranslationInformation.setSliceManagerNsInstanceId(smInstance.getId());

            newTranslationInformation.setTenant(tenant);
            translationInformationRepository.saveAndFlush(newTranslationInformation);
            pollingManager.addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, request.getNsiId(), "NSI_CREATION", domainId, NspNbiType.SLICE_MANAGER);
        } catch (RestClientException e) {
            throw new FailedOperationException(e);
        }


    }

    @Override
    public void modifyNetworkSlice(ModifyNsiRequest modifyNsiRequest, String s, String s1) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {

    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest terminateNsiRequest, String s, String s1) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("Received request torminate SM network Slice Instance: "+terminateNsiRequest.getNsiId());
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
        SliceManagerFsmTranslationInformation translationInformation = translationInformationRepository.findByNsiId(nsiId).get();
        NetworkSliceInstanceNestBasedApi nestInstanceApi = new NetworkSliceInstanceNestBasedApi();
        ApplicationInstanceApi nsInstanceApi = new ApplicationInstanceApi();

        ApiClient apiClient = getApiClient();
        nestInstanceApi.setApiClient(apiClient);
        nsInstanceApi.setApiClient(apiClient);
        try {
            PostSlic3Instance smInstance = nestInstanceApi.getSlic3Instance(translationInformation.getSliceManagerId());
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
            if (smInstance.getActivationStatus().equals("configured")) {
                log.debug("SM Network Slice instantiated");
                log.debug("Retrieving VIM information:"+smInstance.getChunks().getComputeChunk().get(0).getOsmVimId());
                nsInstance.setVimId(smInstance.getChunks().getComputeChunk().get(0).getOsmVimId());
                if (translationInformation.getSliceManagerNsdId() != null
                        && !translationInformation.getSliceManagerNsdId().isEmpty()) {
                    log.debug("SM slice associated with NSD");
                    if (translationInformation.getSliceManagerNetworkServiceInstanceId() == null
                            || translationInformation.getSliceManagerNetworkServiceInstanceId().isEmpty()) {
                        log.debug("Triggering NS instantiation on SM");
                        NetworkServiceInstanceInput nsRequest = new NetworkServiceInstanceInput();
                        nsRequest.setName(translationInformation.getNsiId());
                        nsRequest.setDescription("FSM_PoC_" + translationInformation.getNsiId());
                        nsRequest.setSlic3Id(translationInformation.getSliceManagerNsInstanceId());
                        nsRequest.setUserId(translationInformation.getTenant());
                        nsRequest.setNetworkServiceId(translationInformation.getSliceManagerNsdId());
                        PostNetworkServiceInstance instantiationResponse = nsInstanceApi.postNetworkServiceInstance(nsRequest);
                        log.debug("Instantiation response:" + instantiationResponse.getId());
                        translationInformation.setSliceManagerNetworkServiceInstanceId(instantiationResponse.getId());
                        translationInformationRepository.saveAndFlush(translationInformation);
                    }else{
                        log.debug("SM Network Service Instance already triggered. Retrieving status");
                        PostNetworkServiceInstance currentNsInstance = nsInstanceApi.getNetworkServiceInstance(translationInformation.getSliceManagerNetworkServiceInstanceId());
                        if (currentNsInstance.getStatus().equals("deployed")) {
                            log.debug("SM Network Service instance instantiated");
                            nsInstance.setStatus(NetworkSliceStatus.INSTANTIATED);
                        }
                    }

                }
                else {

                    log.debug("SM Network Slice without network service. ");
                    nsInstance.setStatus(NetworkSliceStatus.INSTANTIATED);


                }
            }else{
                log.debug("SM network slice still INSTANTIATING.");
            }
            nsInstances.add(nsInstance);
            return nsInstances;
        } catch (RestClientException e) {
            log.error("Error instantiating network slice instance", e);
            throw new FailedOperationException(e);
        }

    }

    private ApiClient getApiClient() {
        log.debug("Generating SM API client using URL:" + baseUrl);
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(baseUrl);
        apiClient.setDebugging(true);

        return apiClient;
    }

    @Override
    public void configureNetworkSliceInstance(ConfigureNsiRequest configureNsiRequest, String s, String s1) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {

    }
}
