package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.domainLayer.NspNbiType;
import it.nextworks.nfvmano.catalogue.template.elements.NsTemplateInfo;
import it.nextworks.nfvmano.catalogues.template.repo.ConfigurationRuleRepository;
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

import java.util.*;

import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements.*;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.repos.SonataTranslationInformationRepository;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;

public class SonataRestClient extends AbstractNsmfDriver {

    private static final Logger log = LoggerFactory.getLogger(SonataRestClient.class);

    private String baseUrl;
    private String username;
    private String password;
    private SonataToken sonataToken;

    private CommonUtils utils;
    private NsmfLcmOperationPollingManager pollingManager;

    private SonataTranslationInformationRepository translationInformationRepository;
    private ConfigurationRuleRepository configurationRuleRepository;

    private ObjectMapper mapper;

    public SonataRestClient(String domainId, String url, String username, String password, SonataTranslationInformationRepository repo, ConfigurationRuleRepository configurationRuleRepository, CommonUtils utils, NsmfLcmOperationPollingManager nsmfLcmOperationPollingManager) {
        super(NsmfType.SONATA, domainId);
        this.baseUrl = url + "/api/v3";
        this.username = username;
        this.password = password;
        this.utils = utils;
        this.mapper = new ObjectMapper();
        this.translationInformationRepository = repo;
        this.pollingManager = nsmfLcmOperationPollingManager;
        this.configurationRuleRepository = configurationRuleRepository;
    }

    public SonataToken getAuthenticationToken() {
        SonataTokenRequest tokenReq = new SonataTokenRequest(username, password);
        String url = String.format("%s/users/sessions/", baseUrl);
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(tokenReq, url, HttpMethod.POST, null,null);
        String tokenResp = utils.manageHTTPResponse(httpResponse, "Cannot obtain Sonata authentication token", "Sonata authentication token correctly obtained", HttpStatus.OK);
        SonataToken token = null;
        try {
            token = mapper.readValue(tokenResp, SonataToken.class);
            token.setTokenExpiresTime(3600000);//3600 seconds
        } catch (Exception e) {
            log.debug("Cannot obtain Sonata authentication token", e);
        }
        return token;
    }

    private void verifyToken() throws FailedOperationException {
        if (sonataToken == null)
            sonataToken = getAuthenticationToken();
        else if (sonataToken.getTokenExpiresTime() < System.currentTimeMillis())
            sonataToken = getAuthenticationToken();
        if (sonataToken == null)
            throw new FailedOperationException("Cannot obtain Sonata authentication token");
    }

    @Override
    public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.info("Processing request to create a new network slice instance identifier for NST {}", request.getNstId());
        request.isValid();
        NsTemplateInfo nstInfo = utils.getNsTemplateInfoFromCatalogue(request.getNstId());
        NST nsTemplate = nstInfo.getNST();
        String nsiId = UUID.randomUUID().toString();
        SonataTranslationInformation newTranslationInformation = new SonataTranslationInformation(nsiId, null, nsTemplate.getNstName(), request.getNstId(), nsTemplate.getNsdId(), nsTemplate.getNsdVersion(), null, null, null);
        translationInformationRepository.saveAndFlush(newTranslationInformation);
        log.info("Network slice instance instance identifier {} created", nsiId);
        return nsiId;
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.info("Processing request to instantiate network slice instance {} for NST {}", request.getNsiId(), request.getNstId());
        request.isValid();
        SonataTranslationInformation translationInformation = getTranslationInformation(request.getNsiId());
        SonataNSInstantiationRequest instantiationRequest = new SonataNSInstantiationRequest(request.getNstId(), translationInformation.getNstName(), translationInformation.getNstName());
        String url = String.format("%s/requests", baseUrl);
        verifyToken();
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(instantiationRequest, url, HttpMethod.POST, null, sonataToken.getToken());
        String nsInstanceResponse = utils.manageHTTPResponse(httpResponse, "Cannot instantiate NS", "NS instantiation request correctly sent", HttpStatus.CREATED);
        if (nsInstanceResponse == null)
            throw new FailedOperationException("Cannot instantiate Network Slice");
        SonataRequestInstance requestInstance;
        try {
            requestInstance = mapper.readValue(nsInstanceResponse, SonataRequestInstance.class);
        } catch (Exception e) {
            log.debug("Cannot read the NS Instance Response", e);
            throw new FailedOperationException("Cannot read the NS Instance Response");
        }
        translationInformation.setRequestId(requestInstance.getId());
        translationInformation.setDfId(request.getDfId());
        translationInformation.setIlId(request.getIlId());
        translationInformationRepository.saveAndFlush(translationInformation);
        log.info("Network slice instance {} instantiated", request.getNsiId());
        pollingManager.addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, request.getNsiId(), "NSI_CREATION", domainId, NspNbiType.SONATA);
    }

    @Override
    public void modifyNetworkSlice(ModifyNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        throw new MethodNotImplementedException();
    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.info("Processing request to terminate network slice instance {}", request.getNsiId());
        request.isValid();
        SonataTranslationInformation translationInformation = getTranslationInformation(request.getNsiId());
        String url = String.format("%s/requests", baseUrl);
        SonataNSTerminationRequest terminationRequest = new SonataNSTerminationRequest(translationInformation.getSonataInstanceId());
        verifyToken();
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(terminationRequest, url, HttpMethod.POST, null, sonataToken.getToken());
        String nsDeleteResponse = utils.manageHTTPResponse(httpResponse, "Cannot terminate NS", "NS termination request correctly sent", HttpStatus.CREATED);
        if (nsDeleteResponse == null)
            throw new FailedOperationException("Cannot terminate Network Slice");
        //translationInformationRepository.delete(translationInformation);//TODO cannot delete here the record, it is used by the polling manager. Delete is needed?
        log.info("Network slice instance {} terminated", request.getNsiId());
        pollingManager.addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, request.getNsiId(), "NSI_TERMINATION", domainId, NspNbiType.SONATA);
    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domainId, String tenantId)
            throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
        log.info("Processing request to query network slice instances");
        request.isValid();
        String nsiId = null;
        String sonataInstanceId = null;
        String url;
        if (request.getFilter().getParameters().containsKey("NSI_ID"))
            nsiId = request.getFilter().getParameters().get("NSI_ID");
        if (nsiId != null) {
            SonataTranslationInformation translationInformation = getTranslationInformation(nsiId);
            sonataInstanceId = translationInformation.getSonataInstanceId();
            if(sonataInstanceId != null)
                url = String.format("%s/slice-instances/%s", baseUrl, sonataInstanceId);
            else//it means that the slice has not been instantiated yet, sonataInstanceId is still unknown
                url = String.format("%s/requests/%s", baseUrl, translationInformation.getRequestId());
        } else
            url = String.format("%s/slice-instances", baseUrl);
        verifyToken();
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, null, sonataToken.getToken());
        String queryNSResponse = utils.manageHTTPResponse(httpResponse, "Cannot obtain Network Slice Instance information", "Network Slice Instance information correctly obtained", HttpStatus.OK);
        if (queryNSResponse == null)
            throw new FailedOperationException("Cannot obtain Network Slice Instance information");
        List<NSI> nsInstances;
        SonataRequestInstance requestInstance = null;//used when sonataInstanceId is unknown, so it is not possible to obtain nsi information directly
        try {
            if (nsiId == null)
                nsInstances = mapper.readValue(queryNSResponse, new TypeReference<List<NSI>>() {});
            else {
                nsInstances = new ArrayList<>();
                if(sonataInstanceId != null)
                    nsInstances.add(mapper.readValue(queryNSResponse, NSI.class));
                else
                    requestInstance = mapper.readValue(queryNSResponse, SonataRequestInstance.class);
            }
        } catch (Exception e) {
            log.debug("Cannot read the NS Instance Response", e);
            throw new FailedOperationException("Cannot read the NS Instance Response");
        }

        return sonataInstanceId != null ? translateSonataNsInstances(nsInstances) : translateSonataRequestInstance(requestInstance, nsiId);
    }

    @Override
    public void configureNetworkSliceInstance(ConfigureNsiRequest request, String domainId, String tenantId) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException{
        throw new MethodNotImplementedException("Day1 configuration currently not supported");
    }

    private List<NetworkSliceInstance> translateSonataNsInstances(List<NSI> sonataNsInstances) throws FailedOperationException {
        List<NetworkSliceInstance> nsInstances = new ArrayList<>();
        for (NSI sonataNSInstance : sonataNsInstances) {
            Optional<SonataTranslationInformation> translationInformationOptional = translationInformationRepository.findBySonataInstanceId(sonataNSInstance.getId());
            SonataTranslationInformation translationInformation;
            if (!translationInformationOptional.isPresent())
                throw new FailedOperationException("Translation information entry not found");
            else
                translationInformation = translationInformationOptional.get();
            NetworkSliceInstance nsInstance = new NetworkSliceInstance(translationInformation.getNsiId(), translationInformation.getNstId(), translationInformation.getNsdId(),
                    translationInformation.getNsdVersion(), translationInformation.getDfId(), translationInformation.getIlId(),
                    null, null, null, sonataNSInstance.getName(), sonataNSInstance.getDescription(), false,new HashMap<>());//TODO add nfvNsId as NSINsrlist.get(0).nsrId ??
            nsInstance.setStatus(translateStatus(sonataNSInstance.getNsiStatus().toString()));
            nsInstances.add(nsInstance);
        }
        return nsInstances;
    }

    private List<NetworkSliceInstance> translateSonataRequestInstance(SonataRequestInstance requestInstance, String nsiId) throws FailedOperationException{
        List<NetworkSliceInstance> nsInstances = new ArrayList<>();
        SonataTranslationInformation translationInformation = getTranslationInformation(nsiId);
        NetworkSliceInstance nsInstance = new NetworkSliceInstance(translationInformation.getNsiId(), translationInformation.getNstId(), translationInformation.getNsdId(),
                translationInformation.getNsdVersion(), translationInformation.getDfId(), translationInformation.getIlId(),
                null, null, null, requestInstance.getName(), requestInstance.getDescription(), false, new HashMap<>());
        nsInstance.setStatus(translateStatus(requestInstance.getStatus()));
        nsInstances.add(nsInstance);
        if(requestInstance.getInstanceId() != null){
            translationInformation.setSonataInstanceId(requestInstance.getInstanceId());
            translationInformationRepository.saveAndFlush(translationInformation);
        }

        return nsInstances;
    }

    private NetworkSliceStatus translateStatus(String status) {
        switch (status) {
            case "INSTANTIATING":
                return NetworkSliceStatus.INSTANTIATING;
            case "INSTANTIATED":
                return NetworkSliceStatus.INSTANTIATED;
            case "TERMINATING":
                return NetworkSliceStatus.TERMINATING;
            case "TERMINATED":
                return NetworkSliceStatus.TERMINATED;
            case "READY":
                return NetworkSliceStatus.NOT_INSTANTIATED;//TODO Is this okay?
            case "ERROR":
                return NetworkSliceStatus.FAILED;
            default:
                return null;
        }
    }

    private SonataTranslationInformation getTranslationInformation(String nsiId) throws FailedOperationException {
        Optional<SonataTranslationInformation> translationInformationOptional = translationInformationRepository.findByNsiId(nsiId);
        if (!translationInformationOptional.isPresent())
            throw new FailedOperationException("Translation information entry not found");
        else
            return translationInformationOptional.get();
    }
}
