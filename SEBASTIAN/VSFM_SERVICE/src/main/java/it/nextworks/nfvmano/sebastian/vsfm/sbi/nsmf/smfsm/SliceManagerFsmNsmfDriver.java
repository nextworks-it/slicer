package it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.smfsm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.client.mda.ApiException;
import io.swagger.client.mda.model.*;
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
import io.swagger.client.mda.api.DefaultApi;
import java.util.*;
import java.util.stream.Collectors;

public class SliceManagerFsmNsmfDriver extends AbstractNsmfDriver {
    private NsmfLcmOperationPollingManager pollingManager;
    private SliceManagerFsmTranslationInformationRepository translationInformationRepository;
    private static final Logger log = LoggerFactory.getLogger(SliceManagerFsmNsmfDriver.class);
    private NsTemplateCatalogueService nsTemplateCatalogueService;
    private String baseUrl;
    private boolean enableSpectrumMonitoring = false;
    private String mdaMonitoringUrl;
    private String cellularMonitoringUrl;
    private String[] cellularMetrics =  {"location_latitude_coord", "location_longitude_coord",
                "cell_dl_bitrate_bps", "cell_ul_bitrate_bps", "cell_ue_count", "cell_neigh_sinr_db", "cell_eirp"};

    private  String businessDomainId;
    public SliceManagerFsmNsmfDriver(String domainId, String baseUrl,
                                     SliceManagerFsmTranslationInformationRepository translationInformationRepository,
                                     NsmfLcmOperationPollingManager nsmfLcmOperationPollingManager,
                                     NsTemplateCatalogueService nsTemplateCatalogueService,
                                     boolean enableSpectrumMonitoring,
                                     String mdaMonitoringUrl,
                                     String cellularMonitoringUrl,
                                     String businessDomainId) {
        super(NsmfType.SLICE_MANAGER_FSM, domainId);
        this.baseUrl = baseUrl;
        this.pollingManager = nsmfLcmOperationPollingManager;
        this.translationInformationRepository = translationInformationRepository;
        this.nsTemplateCatalogueService = nsTemplateCatalogueService;
        this.enableSpectrumMonitoring=enableSpectrumMonitoring;
        this.mdaMonitoringUrl = mdaMonitoringUrl;
        this.cellularMonitoringUrl = cellularMonitoringUrl;
        this.businessDomainId=businessDomainId;


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

                            if (conf.getParameterType().equals("integer") || conf.getParameterType().equals("int")) {
                                param = new IntegerConfigurableParameter();
                                param.setParameterName(paramName);
                                ((IntegerConfigurableParameter) param).setParameterValue(Float.parseFloat(paramValue));
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
            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(instanceInput);
            log.debug("SM Nest Instantiation Request:" +json);
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
            log.debug("Storing business information transaction_id:"+request.getUserData().get("transaction_id")+ " product_id:"+request.getUserData().get("product_id"));
            newTranslationInformation.setTransactionId(request.getUserData().get("transaction_id"));
            newTranslationInformation.setProductId(request.getUserData().get("product_id"));
            newTranslationInformation.setTenant(tenant);
            translationInformationRepository.saveAndFlush(newTranslationInformation);
            pollingManager.addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, request.getNsiId(), "NSI_CREATION", domainId, NspNbiType.SLICE_MANAGER);
        } catch (RestClientException e) {
            throw new FailedOperationException(e);
        } catch (JsonProcessingException e) {
            throw new FailedOperationException(e);
        }


    }

    @Override
    public void modifyNetworkSlice(ModifyNsiRequest modifyNsiRequest, String s, String s1) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {

    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest terminateNsiRequest, String s, String s1) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("Received request torminate SM network Slice Instance: "+terminateNsiRequest.getNsiId());

        String nsiId= terminateNsiRequest.getNsiId();
        SliceManagerFsmTranslationInformation translationInformation = translationInformationRepository.findByNsiId(nsiId).get();
        DefaultApi mdaClient = getMdaClient();
        log.debug("Removing monitoring metrics");
        try {
            mdaClient.disableConfigIdSettingsConfigIdDisablePut(translationInformation.getCellularMetricId());
            //mdaClient.deleteConfigIdSettingsConfigIdDelete(translationInformation.getCellularMetricId());
        } catch (ApiException e) {
            log.error("Error during MDA metric removal. ",e);
            throw new FailedOperationException(e);
        }
        NetworkSliceInstanceNestBasedApi nestInstanceApi = new NetworkSliceInstanceNestBasedApi();
        nestInstanceApi.setApiClient(new ApiClient().setBasePath(baseUrl));
        nestInstanceApi.deleteSlic3Instance(translationInformation.getSliceManagerNsInstanceId());


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

                log.debug("Retrieving Cell ID information");
                log.debug("Retrieving Radio Chunk");
                PostRadioChunk radioChunk =smInstance.getChunks().getRadioChunk().get(0);
                log.debug("Retrieving Radio Topology");
                RadioChunkTopology topology = radioChunk.getChunkTopology();
                log.debug("Retrieving Radio Selected Phys");
                RadioSelectedPhys selectedPhys = null;
                for(RadioSelectedPhys phys : topology.getSelectedPhys()){
                    if(phys.getType()!=null && phys.getType().equals("AMARISOFT_CELL")){
                        selectedPhys=phys;
                    }
                }
                if(selectedPhys==null)
                        throw new FailedOperationException("Could not find AMARISOFT CELL PHYS");
                log.debug("Retrieving Radio Phys Config");
                RadioSelectedPhysConfig config = selectedPhys.getConfig();
                int cellId = config.getCellId();
                log.debug("Retrieved cell id:"+cellId);
                translationInformation.setCellId(Integer.toString(cellId));
                translationInformationRepository.saveAndFlush(translationInformation);
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
                            if(!translationInformation.isMonitoringConfigured() && enableSpectrumMonitoring)
                                configureSliceMonitoring(nsiId);
                        }
                    }

                }
                else {

                    log.debug("SM Network Slice without network service. ");

                    if(!translationInformation.isMonitoringConfigured() && enableSpectrumMonitoring)
                        configureSliceMonitoring(nsiId);

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

    private void configureSliceMonitoring(String networkSliceInstanceId) throws FailedOperationException{
        log.debug("Configuring MDA Monitoring for SM");
        DefaultApi mdaClient = getMdaClient();
        SliceManagerFsmTranslationInformation smTranslationInfo = translationInformationRepository.findByNsiId(networkSliceInstanceId).get();
        ConfigModel body = new ConfigModel();
        body.setTenantId(businessDomainId);
        body.setTopic(businessDomainId + "-in-0");
        body.setTransactionId(smTranslationInfo.getTransactionId());
        log.debug("Retrieved transaction_id:"+smTranslationInfo.getTransactionId());
        log.debug("Retrieved product_id:"+smTranslationInfo.getProductId());
        body.setProductId(smTranslationInfo.getProductId());
        body.setInstanceId(smTranslationInfo.getSliceManagerNsInstanceId());
        body.setDataSourceType(DataSourceType.RAN);
        ContextModel cm = new ContextModel();
        cm.setNetworkSliceId(smTranslationInfo.getSliceManagerNsInstanceId());
        cm.setResourceId(smTranslationInfo.getCellId());
        //cm.setCellId(smTranslationInfo.getCellId());
        List<ContextModel> cms = new ArrayList<>();
        body.setMonitoringEndpoint(this.cellularMonitoringUrl);
        cms.add(cm);
        body.setContextIds(cms);
        List<MetricModel> metrics = new ArrayList<>();

        for(String cellularMetric: cellularMetrics){
            MetricModel metricModel = new MetricModel();
            metricModel.setMetricName(cellularMetric);
            metricModel.setMetricType("numerical");
            metricModel.setStep("5m");
            metrics.add(metricModel);
        }
        body.setMetrics(metrics);
        try {
            ResponseConfigModel response= mdaClient.setParamSettingsPost(body);
            String metricId = response.getId().toString();
            smTranslationInfo.setCellularMetricId(metricId);
            smTranslationInfo.setMonitoringConfigured(true);
            translationInformationRepository.saveAndFlush(smTranslationInfo);
            log.debug("configured SM Cellular Metrics. Received ID:"+metricId);

        } catch (ApiException e) {
            log.error("ERROR DURING MDA CONFIGURATION REQUEST", e);
            throw new FailedOperationException(e);
        }
    }

    private DefaultApi getMdaClient(){
        log.debug("Creating MDA Client");
        DefaultApi mdaClient = new DefaultApi();
        io.swagger.client.mda.ApiClient apiClient = new io.swagger.client.mda.ApiClient();
        apiClient.setBasePath(mdaMonitoringUrl)
                .setDebugging(true)
                .setReadTimeout(0)
                .setWriteTimeout(0)
                .setConnectTimeout(0);
        mdaClient.setApiClient(apiClient);
        return mdaClient;
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
