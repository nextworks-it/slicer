package it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.domainLayer.NspNbiType;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.OsmNfvoDomainLayer;
import it.nextworks.nfvmano.catalogue.template.elements.NsTemplateInfo;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.AbstractNsmfDriver;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfLcmOperationPollingManager;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfType;

import java.util.*;

import it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.elements.*;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.repos.NHTranslationInformationRepository;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements.SonataTranslationInformation;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;

public class NeutralHostingRestClient extends AbstractNsmfDriver {

    private static final Logger log = LoggerFactory.getLogger(NeutralHostingRestClient.class);

    private String baseUrl;
    private String smAdmin;//User_id in NS instantiation request
    private String tenantId;//sliceId in NS instantiation request

    private CommonUtils utils;
    private NsmfLcmOperationPollingManager pollingManager;

    private String osmBaseUrl;
    private String osmUser;
    private String osmPassword;
    private String osmProject;
    private OsmTokenSimplified osmToken;

    private NHTranslationInformationRepository translationInformationRepository;

    private ObjectMapper mapper;

    public NeutralHostingRestClient(String domainId, String url, String admin, String tenantId, OsmNfvoDomainLayer osmNfvoDomainLayer, NHTranslationInformationRepository repo, CommonUtils utils, NsmfLcmOperationPollingManager nsmfLcmOperationPollingManager) {
        super(NsmfType.NEUTRAL_HOSTING, domainId);
        this.baseUrl = url;
        this.smAdmin = admin;
        this.tenantId = tenantId;
        String[] splittedUrl = url.split(":");
        this.osmBaseUrl = String.format("https:%s:9999/osm", splittedUrl[1]);
        this.osmUser = osmNfvoDomainLayer.getUsername();
        this.osmPassword = osmNfvoDomainLayer.getPassword();
        this.osmProject = osmNfvoDomainLayer.getProject();
        this.utils = utils;
        this.mapper = new ObjectMapper();
        this.translationInformationRepository = repo;
        this.pollingManager = nsmfLcmOperationPollingManager;
    }

    public OsmTokenSimplified getAuthenticationToken() {
        OsmTokenRequest tokenReq = new OsmTokenRequest(osmUser, osmPassword, osmProject);
        String url = osmBaseUrl + "/admin/v1/tokens";
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(tokenReq, url, HttpMethod.POST, null);
        String tokenResp = utils.manageHTTPResponse(httpResponse, "Cannot obtain OSM authentication token", "OSM authentication token correctly obtained", HttpStatus.OK);
        OsmTokenSimplified token = null;
        try {
            token = mapper.readValue(tokenResp, OsmTokenSimplified.class);
        } catch (Exception e) {
            log.debug("Cannot obtain OSM authentication token", e);
        }
        return token;
    }

    private void verifyToken() throws FailedOperationException {
        if (osmToken == null)
            osmToken = getAuthenticationToken();
        else {
            Double tokenExpireTime = new Double(osmToken.getExpires());
            if (tokenExpireTime.longValue() * 1000 < System.currentTimeMillis()) {
                osmToken = getAuthenticationToken();
            }
        }
        if (osmToken == null)
            throw new FailedOperationException("Cannot obtain OSM authentication token");
    }

    @Override
    public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.info("Processing request to create a new network slice instance identifier for NST {}", request.getNstId());
        request.isValid();
        NsTemplateInfo nstInfo = utils.getNsTemplateInfoFromCatalogue(request.getNstId());
        NST nsTemplate = nstInfo.getNST();
        String nsdId = nsTemplate.getNsdId();
        verifyToken();
        String url = String.format("%s/nsd/v1/ns_descriptors?id=%s", osmBaseUrl, nsdId);
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, osmToken.getId());
        String nsDescriptorResponse = utils.manageHTTPResponse(httpResponse, "Cannot obtain OSM NSD", "OSM NSD correctly obtained", HttpStatus.OK);
        if (nsDescriptorResponse == null)
            throw new FailedOperationException("Cannot create Network Slice Identifier");
        List<OsmInfoObjectSimplified> osmInfoObject;
        try {
            osmInfoObject = mapper.readValue(nsDescriptorResponse, new TypeReference<List<OsmInfoObjectSimplified>>() {
            });
        } catch (Exception e) {
            log.debug("Cannot read OSM NSD response", e);
            throw new FailedOperationException("Cannot read OSM NSD response");
        }
        String nsiId = UUID.randomUUID().toString();
        NHTranslationInformation newTranslationInformation = new NHTranslationInformation(nsiId, osmInfoObject.get(0).getId(), null, nsTemplate.getNstName(), request.getNstId(), nsdId, nsTemplate.getNsdVersion(), null, null);
        translationInformationRepository.saveAndFlush(newTranslationInformation);
        log.info("Network slice instance identifier {} created", nsiId);
        return nsiId;
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.info("Processing request to instantiate network slice instance {} for NST {}", request.getNsiId(), request.getNstId());
        request.isValid();
        NHTranslationInformation translationInformation = getTranslationInformation(request.getNsiId());
        NeutralHostNSInstantiationRequest instantiationRequest = new NeutralHostNSInstantiationRequest(translationInformation.getNstName(), translationInformation.getNstName(), translationInformation.getOsmInfoId(), new ArrayList<>(), this.tenantId, false, smAdmin);
        String url = String.format("%s/api/v0.1/network_service_instance", baseUrl);
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(instantiationRequest, url, HttpMethod.POST, null);
        String nsInstanceResponse = utils.manageHTTPResponse(httpResponse, "Cannot instantiate NS", "NS instantiation request correctly sent", HttpStatus.OK);
        if (nsInstanceResponse == null)
            throw new FailedOperationException("Cannot instantiate Network Slice");
        NeutralHostNSInstance nsInstance;
        try {
            nsInstance = mapper.readValue(nsInstanceResponse, NeutralHostNSInstance.class);
        } catch (Exception e) {
            log.debug("Cannot read the NS Instance Response", e);
            throw new FailedOperationException("Cannot read the NS Instance Response");
        }
        translationInformation.setSmNsInstanceId(nsInstance.getId());
        translationInformation.setDfId(request.getDfId());
        translationInformation.setIlId(request.getIlId());
        translationInformationRepository.saveAndFlush(translationInformation);
        log.info("Network slice instance {} instantiated", request.getNsiId());
        pollingManager.addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, request.getNsiId(), "NSI_CREATION", domainId, NspNbiType.NEUTRAL_HOSTING);
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
        NHTranslationInformation translationInformation = getTranslationInformation(request.getNsiId());
        String url = String.format("%s/api/v0.1/network_service_instance/%s", baseUrl, translationInformation.getSmNsInstanceId());
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.DELETE, null);
        String nsDeleteResponse = utils.manageHTTPResponse(httpResponse, "Cannot terminate NS", "NS termination request correctly sent", HttpStatus.NO_CONTENT);
        if (nsDeleteResponse == null)
            throw new FailedOperationException("Cannot terminate Network Slice");
        //translationInformationRepository.delete(translationInformation);//TODO cannot delete here the record, it is used by the polling manager. Delete is needed?
        log.info("Network slice instance {} terminated", request.getNsiId());
        pollingManager.addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, request.getNsiId(), "NSI_TERMINATION", domainId, NspNbiType.NEUTRAL_HOSTING);
    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domainId, String tenantId)
            throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
        log.info("Processing request to query network slice instances");
        request.isValid();
        String nsiId = null;
        String url;
        if (request.getFilter().getParameters().containsKey("NSI_ID"))
            nsiId = request.getFilter().getParameters().get("NSI_ID");
        if (nsiId != null) {
            NHTranslationInformation translationInformation = getTranslationInformation(nsiId);
            url = String.format("%s/api/v0.1/network_service_instance/%s", baseUrl, translationInformation.getSmNsInstanceId());
        } else
            url = String.format("%s/api/v0.1/network_service_instance", baseUrl);
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, null);
        String queryNSResponse = utils.manageHTTPResponse(httpResponse, "Cannot obtain Network Slice Instance information", "Network Slice Instance information correctly obtained", HttpStatus.OK);
        if (queryNSResponse == null)
            throw new FailedOperationException("Cannot obtain Network Slice Instance information");
        List<NeutralHostNSInstance> nsInstances;
        try {
            if (nsiId == null)
                nsInstances = mapper.readValue(queryNSResponse, new TypeReference<List<NeutralHostNSInstance>>() {
                });
            else {
                nsInstances = new ArrayList<>();
                nsInstances.add(mapper.readValue(queryNSResponse, NeutralHostNSInstance.class));
            }
        } catch (Exception e) {
            log.debug("Cannot read the NS Instance Response", e);
            throw new FailedOperationException("Cannot read the NS Instance Response");
        }

        return translateNeutralHostInstances(nsInstances);
    }

    private List<NetworkSliceInstance> translateNeutralHostInstances(List<NeutralHostNSInstance> NHNsInstances){
        List<NetworkSliceInstance> nsInstances = new ArrayList<>();
        for (NeutralHostNSInstance NHInstance : NHNsInstances) {
            Optional<NHTranslationInformation> translationInformationOptional = translationInformationRepository.findBySmNsInstanceId(NHInstance.getId());
            NHTranslationInformation translationInformation;
            if(translationInformationOptional.isPresent()) {
                translationInformation = translationInformationOptional.get();
                NetworkSliceInstance nsInstance = new NetworkSliceInstance(translationInformation.getNsiId(), translationInformation.getNstId(), translationInformation.getNsdId(),
                        translationInformation.getNsdVersion(), translationInformation.getDfId(), translationInformation.getIlId(),
                        NHInstance.getNsiId(), null, NHInstance.getSliceId(), NHInstance.getName(), NHInstance.getDescription(), false,new HashMap<>());
                nsInstance.setStatus(translateStatus(NHInstance.getStatus()));
                nsInstances.add(nsInstance);
            }
        }
        return nsInstances;
    }

    private NetworkSliceStatus translateStatus(String status) {
        switch (status) {
            case "deployed":
            case "configured":
                return NetworkSliceStatus.INSTANTIATED;
            case "pending":
                return NetworkSliceStatus.INSTANTIATING;//TODO failed?
            default:
                return null;
        }
    }

    private NHTranslationInformation getTranslationInformation(String nsiId) throws FailedOperationException {
        Optional<NHTranslationInformation> translationInformationOptional = translationInformationRepository.findByNsiId(nsiId);
        if (!translationInformationOptional.isPresent())
            throw new FailedOperationException("Translation information entry not found");
        else
            return translationInformationOptional.get();
    }
}
