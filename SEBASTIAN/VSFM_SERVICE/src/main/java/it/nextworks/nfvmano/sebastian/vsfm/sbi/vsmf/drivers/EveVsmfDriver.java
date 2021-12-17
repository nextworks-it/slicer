package it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers;

import io.swagger.elcm.client.api.ElmRestControllerApi;
import io.swagger.elcm.client.model.ExecuteExperimentRequest;
import io.swagger.elcm.client.model.Experiment;
import io.swagger.elcm.client.model.ExperimentExecution;
import io.swagger.elcm.client.model.ExperimentSchedulingRequest;
import io.swagger.eveportal.client.api.ExperimentBlueprintCatalogueApiApi;
import io.swagger.eveportal.client.api.ExperimentDescriptorCatalogueApiApi;
import io.swagger.eveportal.client.api.TestCaseBlueprintCatalogueApiApi;
import io.swagger.eveportal.client.api.VerticalServiceBlueprintCatalogueApiApi;
import io.swagger.eveportal.client.model.ExpBlueprintInfo;
import io.swagger.eveportal.client.model.OnboardExpDescriptorRequest;
import io.swagger.eveportal.client.model.TestCaseBlueprintInfo;
import io.swagger.eveportal.client.model.VsBlueprintInfo;
import io.swagger.rbac.client.ApiClient;
import io.swagger.rbac.client.ApiException;
import io.swagger.rbac.client.api.AuthenticationApi;
import io.swagger.rbac.client.model.LoginResponse;
import io.swagger.rbac.client.model.UserLogin;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.messages.*;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.CsmfType;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers.eve.EveTranslator;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers.eve.ExperimentRegister;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.polling.VsmfLcmOperationPollingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EveVsmfDriver extends AbstractVsmfDriver {

    private String elcmBaseUrl;
    private String catalogueBaseUrl;
    private int socketTimeout=0;
    private String rbacUrl;
    private String username;
    private String password;

    private Map<String, ExperimentRegister> experimentRegisterMap = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(EveVsmfDriver.class);

    public EveVsmfDriver(String domain, VsmfLcmOperationPollingManager pollingManager, AdminService adminService, DomainCatalogueService domainCatalogueService, String elcmBaseUrl,
                         String catalogueBaseUrl, String rbacUrl, VsDescriptorCatalogueService vsDescriptorCatalogueService, String username, String password) {
        super(domain, CsmfType.EVE, pollingManager, adminService, domainCatalogueService,vsDescriptorCatalogueService);
        this.elcmBaseUrl=elcmBaseUrl;
        this.catalogueBaseUrl= catalogueBaseUrl;
        this.rbacUrl= rbacUrl;
        this.username=username;
        this.password=password;
    }

    @Override
    public String instantiateVs(InstantiateVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("instantiateVs");
        //PROFILING INSTANTIATE_VS_REQ <instance_name> <timestamp in ms> <VSSI_ID> <VSI_ID>
        //RemoteTenantInfo remoteTenantInfo = super.getRemoteTenantInfoTest(request.getTenantId(), elcmBaseUrl);
        AuthenticationApi authenticationApi = getRbacApiClient();
        UserLogin userLogin = new UserLogin();
        //userLogin.setEmail(remoteTenantInfo.getRemoteTenantName());

        //userLogin.setPassword(remoteTenantInfo.getRemoteTenantPwd());

        Map<String, String> userData = request.getUserData();
        try {
            //LoginResponse loginResponseApiResponse =  authenticationApi.loginResponse(userLogin);

            //log.debug("Login response:" + loginResponseApiResponse);
            log.debug("retrieving EXPB data");
            String expbId = userData.get("blueprintId");
            ExpBlueprintInfo blueprintInfo = getExpBlueprintApiClient().getExpBlueprintUsingGET(expbId, true, "");
            String vsbId = blueprintInfo.getExpBlueprint().getVsBlueprintId();
            List<String> expbTcbIds = blueprintInfo.getExpBlueprint().getTcBlueprintIds();

            TestCaseBlueprintCatalogueApiApi tcbApi = getTestCaseBlueprintApiClient();
            if(!userData.containsKey("tc.tcBlueprintIds")){

                throw new MalformattedElementException("No TestCaseBlueprint Ids specified");
            }
            String tcbIds = userData.get("tc.tcBlueprintIds");
            Map<String , List<String> > tcbParams = new HashMap<>();
            for(String tcbId: tcbIds.split(",")){
                if(!expbTcbIds.contains(tcbId)){
                    log.error("TCB "+tcbId+" not associated with the experiment");
                    throw new MalformattedElementException("TCB "+tcbId+" not associated with the experiment");
                }
                TestCaseBlueprintInfo tcbInfo = tcbApi.getTcBlueprintUsingGET(tcbId, true, "");
                tcbParams.put(tcbId, new ArrayList<>(tcbInfo.getTestCaseBlueprint().getUserParameters().keySet()));
            }


            VsBlueprintInfo vsBlueprintInfo = getVsBlueprintApi().getVsBlueprintUsingGET(vsbId, true, "");
            List<String> vsbParams = vsBlueprintInfo.getVsBlueprint().getParameters().stream()
                    .map(param -> param.getParameterId())
                    .collect(Collectors.toList());

            OnboardExpDescriptorRequest expDescriptor = EveTranslator.getExpDescriptor(request,
                    username,
                    this.getVsDescriptor(request.getVsdId()),
                    vsbId,
                    vsbParams, tcbParams);
            log.debug("Finished translating EXPD/VSD");
            log.info("PROFILING\tFINISHED_TRANSLATING\t"+request.getName()+"\t"+System.currentTimeMillis());
            log.debug("Onboarding EXPD/VSD");
            ExperimentDescriptorCatalogueApiApi expdApi = getExpDescriptorApiClient();
            String expdId = expdApi.createExpDescriptorUsingPOST(expDescriptor, true, "");
            log.info("PROFILING\tFINISHED_ONBOARDING\t"+request.getName()+"\t"+System.currentTimeMillis());
            log.debug("Received ID:"+expdId);
            log.debug("Creating experiment");
            ElmRestControllerApi elmRestControllerApi = getElmApiClient();
            log.debug("Creating experiment request");
            ExperimentSchedulingRequest expRequest = EveTranslator.translateExperimentRequest(request, expdId, username);
            String expId =  elmRestControllerApi.createExperimentUsingPOST(expRequest, true, "");
            log.info("PROFILING\tINSTANTIATE_VS_REQ\t"+request.getName()
                    +"\t"+System.currentTimeMillis()
                    +"\t"+ expId
                    +"\t"+request.getUserData().get("VSI_PARENT_ID"));
            log.debug("Created experiment");
            ExperimentRegister register = new ExperimentRegister(expRequest.getExperimentName(), expdId, expId, Experiment.StatusEnum.SCHEDULING, null, request.getUserData());
            experimentRegisterMap.put(expId, register);
            this.getPollingManager().addOperation(expId, OperationStatus.SUCCESSFULLY_DONE, expId, "VSI_CREATION", domainId);
            log.info("PROFILING\tWAITING_EXPERIMENT_ACCEPTED\t"+expId+"\t"+System.currentTimeMillis()+"\t"+request.getUserData().get("VSI_PARENT_ID"));
            return  expId;



        } catch (ApiException e) {
           log.error(e.getMessage(), e);
           throw new FailedOperationException(e);
        } catch (io.swagger.elcm.client.ApiException e) {
            log.error(e.getMessage(), e);
            throw new FailedOperationException(e);
        } catch (io.swagger.eveportal.client.ApiException e) {
            log.error(e.getMessage(), e);
            throw new FailedOperationException(e);
        }
    }

    @Override
    public QueryVsResponse queryVs(GeneralizedQueryRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("querying experiment status");
        if(!request.getFilter().getParameters().containsKey("VSI_ID"))
            throw  new MalformattedElementException("Query experiment without experiment id");

        String expId = request.getFilter().getParameters().get("VSI_ID");
        if(!experimentRegisterMap.containsKey(expId))
            throw  new MalformattedElementException("Unknown experiment id");

        ExperimentRegister register = experimentRegisterMap.get(expId);
        String vsiParentId = register.getUserData().get("VSI_PARENT_ID");
        try {

            ElmRestControllerApi elmRestControllerApi = getElmApiClient();
            List<Experiment> experiments;
            try{
                experiments = elmRestControllerApi.getAllExperimentsUsingGET(true, "", "", expId);
            }catch(Exception e){
                log.error(e.getMessage(), e);
                throw new FailedOperationException(e);
            }

            if(experiments.isEmpty())
                throw new FailedOperationException("Failed to retrieve experiment");
            VerticalServiceStatus mappedStatus;
            Experiment experiment =experiments.stream()
                    .filter(e -> e.getExperimentId().equals(expId))
                    .findFirst()
                    .get();
            if(experiment==null)
                throw new FailedOperationException("Failed to retrieve experiment");
            if(experiment.getStatus()== Experiment.StatusEnum.ACCEPTED||experiment.getStatus()== Experiment.StatusEnum.SCHEDULING){
                register.setExperimentStatus(Experiment.StatusEnum.ACCEPTED);
                log.debug("Experiment has been accepted, waiting for ready");
                mappedStatus=VerticalServiceStatus.INSTANTIATING;
                log.info("PROFILING\tWAITING_EXPERIMENT_READY\t"+expId+"\t"+System.currentTimeMillis()+"\t"+vsiParentId);
            }else if(experiment.getStatus()== Experiment.StatusEnum.READY){
                register.setExperimentStatus(Experiment.StatusEnum.READY);
                log.debug("Experiment is ready, triggering deployment");
                ExecuteExperimentRequest executeRequest = EveTranslator.translateExperimentDeploy(register.getUserData(), expId);
                elmRestControllerApi.requestExperimentActionUsingPOST("deploy", expId, true, "", executeRequest );
                mappedStatus=VerticalServiceStatus.INSTANTIATING;
                log.info("PROFILING\tWAITING_EXPERIMENT_INSTANTIATED\t"+expId+"\t"+System.currentTimeMillis()+"\t"+vsiParentId);
            }else if(experiment.getStatus()== Experiment.StatusEnum.INSTANTIATING){
                register.setExperimentStatus(Experiment.StatusEnum.INSTANTIATING);
                log.debug("Experiment is instantiating, waiting for instantiation to complete");
                mappedStatus=VerticalServiceStatus.INSTANTIATING;
            }else if(experiment.getStatus()== Experiment.StatusEnum.INSTANTIATED) {
                register.setExperimentStatus(Experiment.StatusEnum.INSTANTIATED);
                List<ExperimentExecution> executions = experiment.getExecutions();
                if(executions==null || executions.isEmpty()){
                    log.debug("Experiment is instantiated, triggering experiment execution");
                    ExecuteExperimentRequest executeRequest = EveTranslator.translateExperimentExecution(register.getUserData(), expId);
                    elmRestControllerApi.requestExperimentActionUsingPOST("execute", expId, true, "", executeRequest);
                    mappedStatus = VerticalServiceStatus.INSTANTIATING;
                    log.info("PROFILING\tWAITING_EXPERIMENT_EXECUTION\t"+expId+"\t"+System.currentTimeMillis()+"\t"+vsiParentId);
                }else{
                    log.debug("Experiment is instantiated, and already executed");
                    ExperimentExecution ee = executions.get(0);
                    //TODO: improve status mapping

                    mappedStatus= VerticalServiceStatus.INSTANTIATED;
                    if(ee.getState().equals(ExperimentExecution.StateEnum.COMPLETED)){
                        log.debug("Execution completed!!");
                        mappedStatus= VerticalServiceStatus.INSTANTIATED;
                    }
                }

            }else if(experiment.getStatus()== Experiment.StatusEnum.RUNNING_EXECUTION) {
                log.debug("Experiment is running!!");
                mappedStatus= VerticalServiceStatus.INSTANTIATED;

                log.info("PROFILING\tVS_INSTANTIATED\t"+expId+"\t"+System.currentTimeMillis()+"\t"+vsiParentId);
            }else if(experiment.getStatus()== Experiment.StatusEnum.TERMINATING) {
                log.debug("Experiment is terminating");
                register.setExperimentStatus(Experiment.StatusEnum.TERMINATING);
                mappedStatus= VerticalServiceStatus.TERMINATING;
                log.info("PROFILING\tWAITING_EXPERIMENT_TERMINATION\t"+expId+"\t"+System.currentTimeMillis()+"\t"+vsiParentId);
            }else if(experiment.getStatus()== Experiment.StatusEnum.TERMINATED) {
                log.debug("Experiment is terminated");
                register.setExperimentStatus(Experiment.StatusEnum.TERMINATED);
                mappedStatus = VerticalServiceStatus.TERMINATED;
                log.info("PROFILING\tVS_TERMINATED\t"+expId+"\t"+System.currentTimeMillis()+"\t"+vsiParentId);
            }else if(experiment.getStatus()== Experiment.StatusEnum.ABORTED||
                    experiment.getStatus()== Experiment.StatusEnum.FAILED ||
                    experiment.getStatus()== Experiment.StatusEnum.REFUSED) {
                    log.debug("Experiment  failed");
                    register.setExperimentStatus(experiment.getStatus());
                    mappedStatus= VerticalServiceStatus.FAILED;
            }else{
                log.debug("Unknown experiment status");
                mappedStatus = VerticalServiceStatus.FAILED;
            }

            experimentRegisterMap.put(expId, register);
            QueryVsResponse response = new QueryVsResponse(expId,register.getName(),
                    "",
                    register.getExpdId(),
                    mappedStatus,
                    null,
                    null,
                    null );
            return response;
        } catch (ApiException e) {
            log.error(e.getMessage(), e);
            QueryVsResponse response = new QueryVsResponse(expId,register.getName(),
                    "",
                    register.getExpdId(),
                    VerticalServiceStatus.FAILED,
                    null,
                    null,
                    null );
            return response;
        } catch (io.swagger.elcm.client.ApiException e) {
            log.error(e.getMessage(), e);
            QueryVsResponse response = new QueryVsResponse(expId,register.getName(),
                    "",
                    register.getExpdId(),
                    VerticalServiceStatus.FAILED,
                    null,
                    null,
                    null );
            return response;
        }


    }

    @Override
    public List<String> queryAllVsIds(GeneralizedQueryRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        return null;
    }

    @Override
    public List<VerticalServiceInstance> queryAllVsInstances(GeneralizedQueryRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        return null;
    }

    @Override
    public void terminateVs(TerminateVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("terminating VS");
        log.info("PROFILING\tVS_TERMINATE_REQ\t"+request.getVsiId()+"\t"+System.currentTimeMillis());

        String expId = request.getVsiId();

        if(!experimentRegisterMap.containsKey(expId))
            throw  new MalformattedElementException("Unknown experiment id");



        ExperimentRegister register = experimentRegisterMap.get(expId);




        try {
            ElmRestControllerApi elmRestControllerApi = getElmApiClient();
            List<Experiment> experiments = elmRestControllerApi.getAllExperimentsUsingGET(true, "", "", expId);
            Experiment experiment =experiments.stream()
                    .filter(e -> e.getExperimentId().equals(expId))
                    .findAny()
                    .get();
            if(experiment==null)
                throw new FailedOperationException("Failed to retrieve experiment");
            if(experiment.getStatus()== Experiment.StatusEnum.INSTANTIATED){
                ExecuteExperimentRequest executeRequest = EveTranslator.translateExperimentTerminate(register.getUserData(), expId);

                elmRestControllerApi.requestExperimentActionUsingPOST("terminate", expId, true, "", executeRequest );
                register.setExperimentStatus(Experiment.StatusEnum.TERMINATING);
                experimentRegisterMap.put(expId, register);
                this.getPollingManager().addOperation(expId, OperationStatus.SUCCESSFULLY_DONE, expId, "VSI_TERMINATION", domainId);

            }else{
                log.error("Requested experiment termination in wrong status:"+experiment.getStatus());
                throw  new FailedOperationException(("Requested experiment termination in wrong status:"+experiment.getStatus()));
            }


        } catch (io.swagger.elcm.client.ApiException e) {
            log.error(e.getMessage(), e);
            throw new FailedOperationException(e);
        } catch (ApiException e) {
            log.error(e.getMessage(), e);
            throw new FailedOperationException(e);
        }


    }

    @Override
    public void modifyVs(ModifyVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("ModifyVs NOT IMPLEMENTED");
        throw  new MethodNotImplementedException("ModifyVs NOT IMPLEMENTED");
    }

    @Override
    public void purgeVs(PurgeVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("PurgeVs NOT IMPLEMENTED");
        throw  new MethodNotImplementedException("PurgeVs NOT IMPLEMENTED");
    }


    private TestCaseBlueprintCatalogueApiApi getTestCaseBlueprintApiClient() throws ApiException {
        String accessToken = getAccessToken();
        log.debug("Generating TCB API client for accessToken:"+accessToken);
        io.swagger.eveportal.client.ApiClient apiClient = new io.swagger.eveportal.client.ApiClient();
        apiClient.setDebugging(true);
        String bearer = "Bearer "+accessToken;
        apiClient.addDefaultHeader("Authorization", bearer);

        apiClient.setBasePath(this.catalogueBaseUrl);
        TestCaseBlueprintCatalogueApiApi tcbApi = new TestCaseBlueprintCatalogueApiApi(apiClient);
        return tcbApi;


    }

    private ExperimentDescriptorCatalogueApiApi getExpDescriptorApiClient () throws ApiException {
        String accessToken =getAccessToken();
        log.debug("Generating ExpD API client for accessToken:"+accessToken);
        io.swagger.eveportal.client.ApiClient apiClient = new io.swagger.eveportal.client.ApiClient();
        apiClient.setDebugging(true);
        String bearer = "Bearer "+accessToken;
        apiClient.addDefaultHeader("Authorization", bearer);

        apiClient.setBasePath(this.catalogueBaseUrl);
        ExperimentDescriptorCatalogueApiApi expdRestControllerApi = new ExperimentDescriptorCatalogueApiApi(apiClient);
        return expdRestControllerApi;


    }
    private ExperimentBlueprintCatalogueApiApi getExpBlueprintApiClient () throws ApiException {
        String accessToken =getAccessToken();
        log.debug("Generating ExpD API client for accessToken:"+accessToken);

        io.swagger.eveportal.client.ApiClient apiClient = new io.swagger.eveportal.client.ApiClient();
        apiClient.setDebugging(true);
        apiClient.setConnectTimeout(socketTimeout);
        apiClient.setReadTimeout(socketTimeout);
        apiClient.setWriteTimeout(socketTimeout);

        String bearer = "Bearer "+accessToken;
        apiClient.addDefaultHeader("Authorization", bearer);

        apiClient.setBasePath(this.catalogueBaseUrl);
        ExperimentBlueprintCatalogueApiApi expbRestControllerApi = new ExperimentBlueprintCatalogueApiApi(apiClient);
        return expbRestControllerApi;


    }


    private VerticalServiceBlueprintCatalogueApiApi getVsBlueprintApi() throws ApiException {
        String accessToken =getAccessToken();
        log.debug("Generating ELCM API client for accessToken:"+accessToken);
        io.swagger.eveportal.client.ApiClient apiClient = new io.swagger.eveportal.client.ApiClient();
        apiClient.setDebugging(true);
        apiClient.setConnectTimeout(socketTimeout);
        apiClient.setReadTimeout(socketTimeout);
        apiClient.setWriteTimeout(socketTimeout);

        String bearer = "Bearer "+accessToken;
        apiClient.addDefaultHeader("Authorization", bearer);

        apiClient.setBasePath(catalogueBaseUrl);
        VerticalServiceBlueprintCatalogueApiApi vsbControllerApi = new VerticalServiceBlueprintCatalogueApiApi(apiClient);
        return vsbControllerApi;
    }

    private ElmRestControllerApi getElmApiClient() throws ApiException {
        String accessToken =getAccessToken();
        log.debug("Generating ELCM API client for accessToken:"+accessToken);
        io.swagger.elcm.client.ApiClient apiClient = new io.swagger.elcm.client.ApiClient();
        apiClient.setDebugging(true);
        apiClient.setConnectTimeout(socketTimeout);
        apiClient.setReadTimeout(socketTimeout);
        apiClient.setWriteTimeout(socketTimeout);

        String bearer = "Bearer "+accessToken;
        apiClient.addDefaultHeader("Authorization", bearer);

        apiClient.setBasePath(elcmBaseUrl);
        ElmRestControllerApi elmRestControllerApi = new ElmRestControllerApi(apiClient);
        return elmRestControllerApi;


    }

    private AuthenticationApi getRbacApiClient(){
        ApiClient apiClient = new ApiClient();
        apiClient.setDebugging(true);
        apiClient.setConnectTimeout(socketTimeout);
        apiClient.setReadTimeout(socketTimeout);
        apiClient.setWriteTimeout(socketTimeout);

        apiClient.setBasePath(rbacUrl);
        AuthenticationApi authenticationApi = new AuthenticationApi(apiClient);
        return authenticationApi;
    }


    private String getAccessToken() throws ApiException{
        log.debug("Retrieving access token");
        AuthenticationApi authenticationApi = getRbacApiClient();
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(username);
        userLogin.setPassword(password);
        LoginResponse loginResponseApiResponse =  authenticationApi.loginResponse(userLogin);
        log.debug("Login response:" + loginResponseApiResponse);
        return loginResponseApiResponse.getAccessToken();
    }

    /*
    private String getAccessToken(RemoteTenantInfo remoteTenantInfo) throws ApiException {
        log.debug("Retrieving access token");
        AuthenticationApi authenticationApi = getRbacApiClient();
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(remoteTenantInfo.getRemoteTenantName());
        userLogin.setPassword(remoteTenantInfo.getRemoteTenantPwd());
        LoginResponse loginResponseApiResponse =  authenticationApi.loginResponse(userLogin);
        log.debug("Login response:" + loginResponseApiResponse);
        return loginResponseApiResponse.getAccessToken();
    }
    */



}
