package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.domainLayer.NspNbiType;
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
import it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.elements.OsmInfoObjectSimplified;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.elements.OsmTokenRequest;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.elements.OsmTokenSimplified;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements.*;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.repos.OsmTranslationInformationRepository;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public class OsmRestClient extends AbstractNsmfDriver {

    private static final Logger log = LoggerFactory.getLogger(OsmRestClient.class);

    private String baseUrl;
    private String username;
    private String password;
    private String project;
    private String vimAccount;
    private OsmTokenSimplified osmToken;

    private CommonUtils utils;
    private NsmfLcmOperationPollingManager pollingManager;

    private OsmTranslationInformationRepository translationInformationRepository;

    private ObjectMapper mapper;

    public OsmRestClient(String domainId, String url, String username, String password, String project, String vimAccount, OsmTranslationInformationRepository repo, CommonUtils utils, NsmfLcmOperationPollingManager nsmfLcmOperationPollingManager) {
        super(NsmfType.OSM, domainId);
        this.baseUrl = url + "/osm";
        this.username = username;
        this.password = password;
        this.project = project;
        this.vimAccount = vimAccount;
        this.utils = utils;
        this.mapper = new ObjectMapper();
        this.translationInformationRepository = repo;
        this.pollingManager = nsmfLcmOperationPollingManager;
    }

    public OsmTokenSimplified getAuthenticationToken() {
        OsmTokenRequest tokenReq = new OsmTokenRequest(username, password, project);
        String url = baseUrl + "/admin/v1/tokens";
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
            throws NotExistingEntityException, MalformattedElementException, FailedOperationException{
        log.info("Processing request to create a new network slice instance identifier for NST {}", request.getNstId());
        request.isValid();
        NsTemplateInfo nstInfo = utils.getNsTemplateInfoFromCatalogue(request.getNstId());
        NST nsTemplate = nstInfo.getNST();
        verifyToken();
        String url = String.format("%s/nst/v1/netslice_templates?id=%s", baseUrl, nsTemplate.getNstId());
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, osmToken.getId());
        String nsTemplateResponse = utils.manageHTTPResponse(httpResponse, "Cannot obtain OSM NST", "OSM NST correctly obtained", HttpStatus.OK);
        if (nsTemplateResponse == null)
            throw new FailedOperationException("Cannot create Network Slice Template Identifier");
        List<OsmInfoObjectSimplified> osmInfoObject;
        try {
            osmInfoObject = mapper.readValue(nsTemplateResponse, new TypeReference<List<OsmInfoObjectSimplified>>() {
            });
        } catch (Exception e) {
            log.debug("Cannot read OSM NSD response", e);
            throw new FailedOperationException("Cannot read OSM NSD response");
        }
        String nsiId = UUID.randomUUID().toString();
        OsmTranslationInformation newTranslationInformation = new OsmTranslationInformation(nsiId, null, osmInfoObject.get(0).getId(), nsTemplate.getNstName(), request.getNstId(), osmInfoObject.get(0).getDescriptorId(), "1.0", null, null);//TODO handle multiple Nsd for each NST
        newTranslationInformation.setStatus(NetworkSliceStatus.NOT_INSTANTIATED);
        translationInformationRepository.saveAndFlush(newTranslationInformation);
        log.info("Network slice instance instance identifier {} created", nsiId);
        return nsiId;
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String tenantId)
            throws FailedOperationException, MalformattedElementException {
        log.info("Processing request to instantiate network slice instance {} for NST {}", request.getNsiId(), request.getNstId());
        request.isValid();
        OsmTranslationInformation translationInformation = getTranslationInformation(request.getNsiId());
        OsmNSInstantiationRequest instantiationRequest = new OsmNSInstantiationRequest(translationInformation.getNstName(), translationInformation.getOsmInfoId(), vimAccount);
        verifyToken();
        String url = String.format("%s/nsilcm/v1/netslice_instances_content", baseUrl);
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(instantiationRequest, url, HttpMethod.POST, osmToken.getId());
        String nsInstanceResponse = utils.manageHTTPResponse(httpResponse, "Cannot instantiate NS", "NS instantiation request correctly sent", HttpStatus.CREATED);
        if (nsInstanceResponse == null)
            throw new FailedOperationException("Cannot instantiate Network Slice");
        OsmRequestInstance requestInstance;
        try {
            requestInstance = mapper.readValue(nsInstanceResponse, OsmRequestInstance.class);
        } catch (Exception e) {
            log.debug("Cannot read the NS Instance Response", e);
            throw new FailedOperationException("Cannot read the NS Instance Response");
        }
        translationInformation.setOsmInstanceId(requestInstance.getId());
        translationInformation.setInstantiationOperationId(requestInstance.getOperationId());
        translationInformation.setDfId("simple_df");
        translationInformation.setIlId("simple_il");
        translationInformation.setStatus(NetworkSliceStatus.INSTANTIATING);
        translationInformationRepository.saveAndFlush(translationInformation);
        log.info("Network slice instance {} instantiated", request.getNsiId());
        pollingManager.addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, request.getNsiId(), "NSI_CREATION", domainId, NspNbiType.OSM);
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
        OsmTranslationInformation translationInformation = getTranslationInformation(request.getNsiId());
        verifyToken();
        String url = String.format("%s/nsilcm/v1/netslice_instances/%s/terminate", baseUrl, translationInformation.getOsmInstanceId());
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.POST, osmToken.getId());
        String nsDeleteResponse = utils.manageHTTPResponse(httpResponse, "Cannot terminate NS", "NS termination request correctly sent", HttpStatus.ACCEPTED);
        if (nsDeleteResponse == null)
            throw new FailedOperationException("Cannot terminate Network Slice");
        OsmRequestInstance requestInstance;
        try {
            requestInstance = mapper.readValue(nsDeleteResponse, OsmRequestInstance.class);
        } catch (Exception e) {
            log.debug("Cannot read the NS Instance Response", e);
            throw new FailedOperationException("Cannot read the NS Instance Response");
        }
        translationInformation.setTerminationOperationId(requestInstance.getId());
        translationInformation.setStatus(NetworkSliceStatus.TERMINATING);
        translationInformationRepository.saveAndFlush(translationInformation);
        log.info("Network slice instance {} terminated", request.getNsiId());
        pollingManager.addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, request.getNsiId(), "NSI_TERMINATION", domainId, NspNbiType.OSM);
    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domainId, String tenantId)
            throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
        log.info("Processing request to query network slice instances");
        request.isValid();
        String nsiId = null;
        String osmInstanceId;
        List<OsmNSInstanceSimplified> nsInstances;
        String url;
        verifyToken();
        OsmTranslationInformation translationInformation = null;
        if (request.getFilter().getParameters().containsKey("NSI_ID"))
            nsiId = request.getFilter().getParameters().get("NSI_ID");
        if (nsiId != null) {
            translationInformation = getTranslationInformation(nsiId);
            osmInstanceId = translationInformation.getOsmInstanceId();
            if(translationInformation.getStatus().equals(NetworkSliceStatus.TERMINATED)) {//nsInstance already terminated
                nsInstances = new ArrayList<>();
                OsmNSInstanceSimplified nsInstance = new OsmNSInstanceSimplified();
                nsInstance.setId(osmInstanceId);
                nsInstances.add(nsInstance);
                return translateOsmNsInstances(nsInstances, NetworkSliceStatus.TERMINATED.toString(), null);
            }
            url = String.format("%s/nsilcm/v1/netslice_instances/%s", baseUrl, osmInstanceId);
        } else
            url = String.format("%s/nsilcm/v1/netslice_instances", baseUrl);
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, osmToken.getId());
        String queryNSResponse = utils.manageHTTPResponse(httpResponse, "Cannot obtain Network Slice Instance information", "Network Slice Instance information correctly obtained", HttpStatus.OK);
        if (queryNSResponse == null)
            throw new FailedOperationException("Cannot obtain Network Slice Instance information");
        try {
            if (nsiId == null)
                nsInstances = mapper.readValue(queryNSResponse, new TypeReference<List<OsmNSInstanceSimplified>>() {});
            else {
                nsInstances = new ArrayList<>();
                nsInstances.add(mapper.readValue(queryNSResponse, OsmNSInstanceSimplified.class));
            }
        } catch (Exception e) {
            log.debug("Cannot read the NS Instance Response", e);
            throw new FailedOperationException("Cannot read the NS Instance Response");
        }
        String status = null;
        String operationType = request.getFilter().getParameters().get("REQUEST_TYPE");
        if(operationType == null)
            operationType = "NSI_CREATION";
        if (nsiId != null){
            String operationId;
            if(operationType.equals("NSI_CREATION"))
                operationId = translationInformation.getInstantiationOperationId();
            else
                operationId = translationInformation.getTerminationOperationId();
            url = String.format("%s/nsilcm/v1/nsi_lcm_op_occs/%s", baseUrl, operationId);
            httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, osmToken.getId());
            queryNSResponse = utils.manageHTTPResponse(httpResponse, "Cannot obtain Network Slice Instance information", "Network Slice Instance information correctly obtained", HttpStatus.OK);
            if (queryNSResponse == null)
                throw new FailedOperationException("Cannot obtain Network Slice Instance information");
            OsmOperationObjectSimplified operationObject;
            try {
                operationObject = mapper.readValue(queryNSResponse, OsmOperationObjectSimplified.class);
            } catch (Exception e) {
                log.debug("Cannot read the NS Instance Response", e);
                throw new FailedOperationException("Cannot read the NS Instance Response");
            }
            status = operationObject.getOperationState();
        }
        return translateOsmNsInstances(nsInstances, status, operationType);
    }

    private List<NetworkSliceInstance> translateOsmNsInstances(List<OsmNSInstanceSimplified> osmNsInstances, String status, String operationType) throws FailedOperationException {
        List<NetworkSliceInstance> nsInstances = new ArrayList<>();
        for (OsmNSInstanceSimplified osmNSInstance : osmNsInstances) {
            Optional<OsmTranslationInformation> translationInformationOptional = translationInformationRepository.findByOsmInstanceId(osmNSInstance.getId());
            OsmTranslationInformation translationInformation;
            if (!translationInformationOptional.isPresent())
                throw new FailedOperationException("Translation information entry not found");
            else
                translationInformation = translationInformationOptional.get();
            NetworkSliceInstance nsInstance = new NetworkSliceInstance(translationInformation.getNsiId(), translationInformation.getNstId(), translationInformation.getNsdId(),
                    translationInformation.getNsdVersion(), translationInformation.getDfId(), translationInformation.getIlId(),
                    null, null, null, translationInformation.getNstName(), null, false, new HashMap<>());
            if(status == null)
                nsInstance.setStatus(NetworkSliceStatus.UNKNOWN);
            else if(operationType != null)
                nsInstance.setStatus(translateStatus(status, operationType));
            else //case when the nsi has already been deleted from OSM
                nsInstance.setStatus(NetworkSliceStatus.TERMINATED);
            nsInstances.add(nsInstance);

            //delete NS instance already terminated
            if(nsInstance.getStatus().equals(NetworkSliceStatus.TERMINATED) && operationType != null){
                verifyToken();
                String url = String.format("%s/nsilcm/v1/netslice_instances/%s", baseUrl, osmNSInstance.getId());
                ResponseEntity<String>  httpResponse = utils.performHTTPRequest(null, url, HttpMethod.DELETE, osmToken.getId());
                String queryNSResponse = utils.manageHTTPResponse(httpResponse, "Cannot delete Network Slice Instance", "Network Slice Instance deleted", HttpStatus.NO_CONTENT);
                if (queryNSResponse == null)
                    throw new FailedOperationException("Cannot delete Network Slice Instance");
                translationInformation.setStatus(NetworkSliceStatus.TERMINATED);
                translationInformationRepository.saveAndFlush(translationInformation);
            }
        }
        return nsInstances;
    }

    private NetworkSliceStatus translateStatus(String status, String operationType) {
        switch (status) {
            case "COMPLETED":
                return operationType.equals("NSI_CREATION") ? NetworkSliceStatus.INSTANTIATED : NetworkSliceStatus.TERMINATED;
            case "FAILED":
                return NetworkSliceStatus.FAILED;
            case "PROCESSING":
                return operationType.equals("NSI_CREATION") ? NetworkSliceStatus.INSTANTIATING : NetworkSliceStatus.TERMINATING;
            default:
                return null;
        }
    }

    private OsmTranslationInformation getTranslationInformation(String nsiId) throws FailedOperationException {
        Optional<OsmTranslationInformation> translationInformationOptional = translationInformationRepository.findByNsiId(nsiId);
        if (!translationInformationOptional.isPresent())
            throw new FailedOperationException("Translation information entry not found");
        else
            return translationInformationOptional.get();
    }
}
