package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import it.nextworks.nfvmano.catalogue.domainLayer.NspNbiType;
import it.nextworks.nfvmano.catalogue.template.elements.*;
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
import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements.OsmInfoObjectSimplified;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements.OsmTokenRequest;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements.OsmTokenSimplified;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements.*;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.repos.OsmTranslationInformationRepository;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

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
    private ConfigurationRuleRepository configurationRuleRepository;

    private ObjectMapper mapper;

    public OsmRestClient(String domainId, String url, String username, String password, String project, String vimAccount, OsmTranslationInformationRepository repo, ConfigurationRuleRepository configurationRuleRepository, CommonUtils utils, NsmfLcmOperationPollingManager nsmfLcmOperationPollingManager) {
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
        this.configurationRuleRepository = configurationRuleRepository;
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
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, null, osmToken.getId());
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
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(instantiationRequest, url, HttpMethod.POST, null, osmToken.getId());
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
        translationInformation.setUserData(request.getUserData());
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
    public void configureNetworkSliceInstance(ConfigureNsiRequest request, String domainId, String tenantId) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException{
        log.info("Processing request to configure network slice instance {}", request.getNsiId());
        request.isValid();
        OsmTranslationInformation translationInformation = getTranslationInformation(request.getNsiId());
        List<NstConfigurationRule> configurationRules = configurationRuleRepository.findByNstId(translationInformation.getNstId());
        //Checking which instances have to be fetched
        Set<String> serviceToFetch = configurationRules.stream().map(NstConfigurationRule::getNsdId).collect(Collectors.toSet());
        Set<String> functionToFetch = new HashSet<>();
        for(NstConfigurationRule configurationRule : configurationRules){
            List<String> configParams = configurationRule.getParams();
            configParams.forEach(param -> { String [] splits = param.split("\\.");
                if(splits[0].equals("vnf") || splits[0].equals("pnf") ||
                        (splits[0].equals("knf") && splits[2].equals("service")))
                    functionToFetch.add(splits[1]);
            });
        }
        if(serviceToFetch.isEmpty()) {
            log.debug("No configuration rules to apply");
            return;
        }
        log.debug("Fetching instances information");
        OsmNSInstanceSimplified nsInstance = queryNSI(translationInformation.getOsmInstanceId());
        List<OsmNSSInstanceSimplified> nssInstances = queryNSSIs(nsInstance.getAdmin().getNsrs(), serviceToFetch);
        Map<String, OsmVNFInstanceSimplified> osmFunctionInstances = null;
        if(!functionToFetch.isEmpty())
            osmFunctionInstances = queryVNFIs(nssInstances, functionToFetch);

        log.debug("Fetching and translating configuration rules");
        translateConfigurationRules(configurationRules, translationInformation.getUserData(), osmFunctionInstances);

        configurationRules.forEach(rule -> log.debug(rule.toString()));

        log.debug("Applying configuration rules");
        for(NstConfigurationRule configurationRule : configurationRules){
            if(configurationRule.getType().equals(NstConfigurationRuleType.REST)) {
                RestNstConfigurationRule rule = (RestNstConfigurationRule)configurationRule;
                applyRestConfigurationRule(rule);
            }else if(configurationRule.getType().equals(NstConfigurationRuleType.SSH)) {
                SshNstConfigurationRule rule = (SshNstConfigurationRule)configurationRule;
                applySshConfigurationRule(rule);
            }else
                throw new FailedOperationException("Configuration rule type currently not supported");
        }
        log.info("Network slice instance {} configured", request.getNsiId());
        translationInformation.setStatus(NetworkSliceStatus.CONFIGURED);
        translationInformationRepository.saveAndFlush(translationInformation);
        pollingManager.addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, request.getNsiId(), "NSI_CONFIGURATION", domainId, NspNbiType.OSM);
    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.info("Processing request to terminate network slice instance {}", request.getNsiId());
        request.isValid();
        OsmTranslationInformation translationInformation = getTranslationInformation(request.getNsiId());
        verifyToken();
        String url = String.format("%s/nsilcm/v1/netslice_instances/%s/terminate", baseUrl, translationInformation.getOsmInstanceId());
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.POST, null, osmToken.getId());
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
        List<OsmNSInstanceSimplified> nsInstances;
        String nsiId = null;
        String osmInstanceId;
        String url;
        verifyToken();
        OsmTranslationInformation translationInformation = null;
        String operationType = request.getFilter().getParameters().get("REQUEST_TYPE");
        if (request.getFilter().getParameters().containsKey("NSI_ID"))
            nsiId = request.getFilter().getParameters().get("NSI_ID");
        if (nsiId != null) {
            OsmNSInstanceSimplified nsInstance;
            translationInformation = getTranslationInformation(nsiId);
            osmInstanceId = translationInformation.getOsmInstanceId();
            if(translationInformation.getStatus().equals(NetworkSliceStatus.TERMINATED) || translationInformation.getStatus().equals(NetworkSliceStatus.CONFIGURED)) {//nsInstance already terminated or already configured
                nsInstances = new ArrayList<>();
                nsInstance = new OsmNSInstanceSimplified();
                nsInstance.setId(osmInstanceId);
                nsInstances.add(nsInstance);
                return translateOsmNsInstances(nsInstances, translationInformation.getStatus().toString(), null);
            }
            url = String.format("%s/nsilcm/v1/netslice_instances/%s", baseUrl, osmInstanceId);
        } else
            url = String.format("%s/nsilcm/v1/netslice_instances", baseUrl);
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, null, osmToken.getId());
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
        if(operationType == null)
            operationType = "NSI_CREATION";
        if (nsiId != null){
            String operationId;
            if(operationType.equals("NSI_CREATION"))
                operationId = translationInformation.getInstantiationOperationId();
            else
                operationId = translationInformation.getTerminationOperationId();
            url = String.format("%s/nsilcm/v1/nsi_lcm_op_occs/%s", baseUrl, operationId);
            httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, null, osmToken.getId());
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

    private OsmTokenSimplified getAuthenticationToken() {
        OsmTokenRequest tokenReq = new OsmTokenRequest(username, password, project);
        String url = baseUrl + "/admin/v1/tokens";
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(tokenReq, url, HttpMethod.POST, null,null);
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
            else
                nsInstance.setStatus(translateStatus(status, operationType));
            nsInstances.add(nsInstance);

            //delete NS instance already terminated
            if(nsInstance.getStatus().equals(NetworkSliceStatus.TERMINATED) && operationType != null){
                verifyToken();
                String url = String.format("%s/nsilcm/v1/netslice_instances/%s", baseUrl, osmNSInstance.getId());
                ResponseEntity<String>  httpResponse = utils.performHTTPRequest(null, url, HttpMethod.DELETE, null, osmToken.getId());
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
            case "CONFIGURED":
                return NetworkSliceStatus.CONFIGURED;
            case "TERMINATED":
                return NetworkSliceStatus.TERMINATED;
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

    private void translateConfigurationRules(List<NstConfigurationRule> configurationRules, Map<String, String> userData, Map<String, OsmVNFInstanceSimplified> functionInstances) throws FailedOperationException{
        for(NstConfigurationRule configurationRule : configurationRules){
            log.debug("Translating user parameters in configuration rule {}", configurationRule.getName());
            //translating user parameters
            List<String> configParams = new ArrayList<>(configurationRule.getParams());
            for(Map.Entry<String, String> userDataPair : userData.entrySet()){
                if(configParams.contains(userDataPair.getKey())){
                    configParams.remove(userDataPair.getKey());
                    switch(configurationRule.getType()){
                        case REST:
                            RestNstConfigurationRule restConfigurationRule = (RestNstConfigurationRule)configurationRule;
                            String config = translateIntoString(userDataPair.getKey(), userDataPair.getValue(), restConfigurationRule.getConfig());
                            restConfigurationRule.setConfig(config);
                            String url = translateIntoString(userDataPair.getKey(), userDataPair.getValue(), restConfigurationRule.getUrl());
                            restConfigurationRule.setUrl(url);
                            break;
                        case SSH:
                            SshNstConfigurationRule sshConfigurationRule = (SshNstConfigurationRule)configurationRule;
                            String command = translateIntoString(userDataPair.getKey(), userDataPair.getValue(), sshConfigurationRule.getCommand());
                            sshConfigurationRule.setCommand(command);
                            String ip = translateIntoString(userDataPair.getKey(), userDataPair.getValue(), sshConfigurationRule.getIpAddress());
                            sshConfigurationRule.setIpAddress(ip);
                            break;
                        default:
                            throw new FailedOperationException("Configuration type currently not supported");
                    }
                }
            }

            log.debug("Translating infrastructure parameters in configuration rule {}", configurationRule.getName());
            //translating infrastructure parameters
            ListIterator<String> iter = configParams.listIterator();
            while(iter.hasNext()) {
                String configParam = iter.next();
                NstConfigurationRuleType ruleType = configurationRule.getType();
                boolean infrastructureParameter = configParam.startsWith("vnf") || configParam.startsWith("pnf") || configParam.startsWith("knf");
                if (infrastructureParameter){
                    String value = findInfrastructureParameterValue(configParam, functionInstances);
                    switch(ruleType){
                        case REST:
                            RestNstConfigurationRule restConfigurationRule = (RestNstConfigurationRule)configurationRule;
                            String config = translateIntoString(configParam, value, restConfigurationRule.getConfig());
                            restConfigurationRule.setConfig(config);
                            String url = translateIntoString(configParam, value, restConfigurationRule.getUrl());
                            restConfigurationRule.setUrl(url);
                            break;
                        case SSH:
                            SshNstConfigurationRule sshConfigurationRule = (SshNstConfigurationRule)configurationRule;
                            String command = translateIntoString(configParam, value, sshConfigurationRule.getCommand());
                            sshConfigurationRule.setCommand(command);
                            String ip = translateIntoString(configParam, value, sshConfigurationRule.getIpAddress());
                            sshConfigurationRule.setIpAddress(ip);
                            break;
                        default:
                            throw new FailedOperationException("Configuration type currently not supported");
                    }
                    iter.remove();
                }
            }

            //check if all config params have been translated
            if(!configParams.isEmpty())
                throw new FailedOperationException("Not all configuration parameters have been translated");
        }
    }

    private String translateIntoString(String parameterKey, String parameterValue, String script) {
        if(script == null)
            return null;
        script = script.replace("$$" + parameterKey, parameterValue);
        return script;
    }

    private String findInfrastructureParameterValue(String configParam, Map<String, OsmVNFInstanceSimplified> osmFunctionInstances) throws FailedOperationException{
        //format example:
        //vnf.<vnfd_id>.interface.<interface_name>.ipaddress (<interface_name> = vnfd -> vdu -> interface -> name)
        //pnf.<pnfd_id>.interface.<interface_name>.ipaddress
        //knf.<knfd_id>.service.<service_name>.clusterip (Consider a k8 service definition <service_name> = metadata -> name)
        //knf.<knfd_id>.service.<service_name>.externalip
        log.debug("Finding infrastructure parameter value");
        Optional<String> optValue;
        String value = null;
        String [] splits = configParam.split("\\.");
        //List<String> ids = new ArrayList<>();
        if(configParam.startsWith("vnf") || configParam.startsWith("pnf")) {
            OsmVNFInstanceSimplified instance = osmFunctionInstances.get(splits[1]);
            if (splits.length == 5) {
                log.debug("Infrastructure parameter related to VNF or PNF with descriptor Id {}", splits[1]);
                if (splits[2].equalsIgnoreCase("interface") && splits[4].equalsIgnoreCase("ipaddress")) {
                    if(instance != null) {//single vdu support
                        optValue = instance.getVdur().get(0).getInterfaces().stream().filter(i -> i.getName().equalsIgnoreCase(splits[3])).map(OsmVDUInterfaceSimplified::getIpAddress).findFirst();
                        if(optValue.isPresent())
                            value = optValue.get();
                        else
                            log.error("Unable to get VNF interface ip address, no interfaces with name {}", splits[3]);
                    }else
                        log.error("Unable to get VNF interface ip address, instance information missing");
                } else
                    log.error("Unacceptable Infrastructure parameter format: {}", configParam);
            } else
                log.error("Unacceptable Infrastructure parameter format: {}", configParam);
        } else if(configParam.startsWith("knf")) {
            if (splits.length == 5) {
                log.debug("Infrastructure parameter related to KNF with descriptor Id {}", splits[1]);
                if (splits[2].equalsIgnoreCase("service") && (splits[4].equalsIgnoreCase("clusterip") || splits[4].equalsIgnoreCase("externalip"))) {
                    OsmVNFInstanceSimplified instance = osmFunctionInstances.get(splits[1]);
                    if(instance != null) {//single kdu support
                        if (splits[4].equalsIgnoreCase("clusterip")) {
                            optValue = instance.getKdur().get(0).getServices().stream().filter(s -> s.getName().equalsIgnoreCase(splits[3])).map(OsmKDUServiceSimplified::getClusterIp).findFirst();
                            if(optValue.isPresent())
                                value = optValue.get();
                            else
                                log.error("Unable to get KNF cluster service address, no services with name {}", splits[3]);
                        }else {
                            Optional<List<String>> optIps = instance.getKdur().get(0).getServices().stream().filter(s -> s.getName().equalsIgnoreCase(splits[3])).map(OsmKDUServiceSimplified::getExternalIps).findAny();
                            if(optIps.isPresent())
                                value = optIps.get().get(0);
                            else
                                log.error("Unable to get KNF external service address, no services with name {}", splits[3]);
                        }

                    }else
                        log.error("Unable to get KNF ip address, instance information missing");
                } else
                    log.error("Unacceptable Infrastructure parameter format: {}", configParam);
            } else
                log.error("Unacceptable Infrastructure parameter format: {}", configParam);
        } else
            log.error("Unacceptable Infrastructure parameter format: {}", configParam);

        if(value == null)
            throw new FailedOperationException("Cannot find the value for Infrastructure Parameter " + configParam);

        return value;
    }

    private void applyRestConfigurationRule(RestNstConfigurationRule rule) throws FailedOperationException{
        log.debug("Applying Rest configuration rule " + rule.getName());
        ResponseEntity<String>  httpResponse = utils.performHTTPRequest(rule.getConfig(), rule.getUrl(), rule.getHttpMethod(), rule.getHeaders(), null);
        String queryNSResponse = utils.manageHTTPResponse(httpResponse, "Cannot apply Rest configuration rule " + rule.getName(), "Configuration Rule " + rule.getName() + " applied", rule.getExpectedStatusCode());
        if (queryNSResponse == null)
            throw new FailedOperationException("Cannot apply Rest configuration rule " +  rule.getName());
    }

    private void applySshConfigurationRule(SshNstConfigurationRule rule) throws FailedOperationException{
        log.debug("Applying Ssh configuration rule " + rule.getName());
        Session session = null;
        ChannelExec channel = null;
        try {
            session = new JSch().getSession(rule.getUsername(), rule.getIpAddress(), rule.getPort());
            session.setPassword(rule.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(rule.getCommand());
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            log.debug("Configuration Rule  " + rule.getName() + " applied");
        }catch (Exception e){
            log.error(null, e);
            throw new FailedOperationException("Cannot apply Ssh configuration rule " + rule.getName());
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    private OsmNSInstanceSimplified queryNSI(String nsiId) throws FailedOperationException{
        log.info("Fetching information from OSM for NSI {}", nsiId);
        verifyToken();
        String url = String.format("%s/nsilcm/v1/netslice_instances/%s", baseUrl, nsiId);
        ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, null, osmToken.getId());
        String queryNSResponse = utils.manageHTTPResponse(httpResponse, "Cannot obtain Network Slice Instance information", "Network Slice Instance information correctly obtained", HttpStatus.OK);
        if (queryNSResponse == null)
            throw new FailedOperationException("Cannot obtain Network Slice Instance information");
        try {
            return mapper.readValue(queryNSResponse, OsmNSInstanceSimplified.class);
        } catch (Exception e) {
            log.debug("Cannot read the NS Instance Response", e);
            throw new FailedOperationException("Cannot read the NS Instance Response");
        }
    }

    private List<OsmNSSInstanceSimplified> queryNSSIs(List<OsmNSSInformationSimplified> nssrInformation, Set<String> nsdToFetch) throws FailedOperationException{
        List<OsmNSSInstanceSimplified> nssis = new ArrayList<>();
        verifyToken();
        for(String nsdId : nsdToFetch) {
            String osmInfoId;
            Optional<String> optOsmInfoId = nssrInformation.stream().filter(nss -> nss.getNsdId().equals(nsdId)).map(OsmNSSInformationSimplified::getInstanceId).findFirst();
            if(optOsmInfoId.isPresent())
                osmInfoId = optOsmInfoId.get();
            else
                throw new FailedOperationException("NSI does not contain NSSI with nsdId " + nsdId);

            log.info("Fetching information from OSM for NSSI {}", osmInfoId);
            String url = String.format("%s/nslcm/v1/ns_instances/%s", baseUrl, osmInfoId);
            ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, null, osmToken.getId());
            String queryNSResponse = utils.manageHTTPResponse(httpResponse, "Cannot obtain Network Slice Subnet Instance information", "Network Slice Subnet Instance information correctly obtained", HttpStatus.OK);
            if (queryNSResponse == null)
                throw new FailedOperationException("Cannot obtain Network Slice Subnet Instance information");
            try {
                nssis.add(mapper.readValue(queryNSResponse, OsmNSSInstanceSimplified.class));
            } catch (Exception e) {
                log.debug("Cannot read the NSS Instance Response", e);
                throw new FailedOperationException("Cannot read the NSS Instance Response");
            }
        }
        return nssis;
    }

    private Map<String, OsmVNFInstanceSimplified> queryVNFIs(List<OsmNSSInstanceSimplified> nssInstances, Set<String> functionToFetchFromOsm) throws FailedOperationException{
        Map<String, OsmVNFInstanceSimplified> vnfis = new HashMap<>();
        verifyToken();
        for(String vnfdId : functionToFetchFromOsm) {
            String osmInfoId = null;
            for(OsmNSSInstanceSimplified nssi : nssInstances){
                Optional<Integer> optMemberIndex = nssi.getNsd().getConstituentVnfs().stream().filter(cVnf -> cVnf.getVnfdId().equals(vnfdId)).map(OsmConstituentVnfSimplified::getMemberVnfIndex).findFirst();//does not support NSD with same VNFD present twice or more
                if(optMemberIndex.isPresent()){
                    osmInfoId = nssi.getVnfrs().get(optMemberIndex.get() - 1);
                    break;
                }
            }
            if(osmInfoId == null)
                throw new FailedOperationException("NSSIs don't contain VNFI with vnfdId " + vnfdId);

            log.info("Fetching information from OSM for VNFI {}", osmInfoId);
            String url = String.format("%s/nslcm/v1/vnf_instances/%s", baseUrl, osmInfoId);
            ResponseEntity<String> httpResponse = utils.performHTTPRequest(null, url, HttpMethod.GET, null, osmToken.getId());
            String queryNSResponse = utils.manageHTTPResponse(httpResponse, "Cannot obtain Virtual Network Function Instance information", "Virtual Network Function Instance information correctly obtained", HttpStatus.OK);
            if (queryNSResponse == null)
                throw new FailedOperationException("Cannot obtain Virtual Network Function Instance information");
            try {
                vnfis.put(vnfdId, mapper.readValue(queryNSResponse, OsmVNFInstanceSimplified.class));
            } catch (Exception e) {
                log.debug("Cannot read the VNF Instance Response", e);
                throw new FailedOperationException("Cannot read the VNF Instance Response");
            }
        }
        return vnfis;
    }
}
