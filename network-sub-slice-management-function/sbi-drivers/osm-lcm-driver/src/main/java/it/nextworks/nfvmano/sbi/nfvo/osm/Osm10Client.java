package it.nextworks.nfvmano.sbi.nfvo.osm;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import it.nextworks.nfvmano.sbi.nfvo.elements.NfvoInformation;
import it.nextworks.nfvmano.sbi.nfvo.interfaces.NetworkSliceLcmProviderInterface;
import it.nextworks.nfvmano.sbi.nfvo.interfaces.NsLcmProviderInterface;
import it.nextworks.nfvmano.sbi.nfvo.messages.*;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.api.NetSliceTemplatesApi;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.api.NetworkSliceInstancesApi;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.api.NsInstancesApi;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.api.NsPackagesApi;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.auth.OAuthSimpleClient;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.client.ApiClient;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.client.ApiException;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.model.*;
import it.nextworks.nfvmano.sbi.nfvo.polling.NfvoLcmOperationPollingManager;
import it.nextworks.nfvmano.sbi.nfvo.polling.NfvoLcmOperationType;
import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Osm10Client implements NsLcmProviderInterface, NetworkSliceLcmProviderInterface {
    private static final Logger log = LoggerFactory.getLogger(Osm10Client.class);

    private NsInstancesApi nsInstancesApi;
    private NetworkSliceInstancesApi networkSliceInstancesApi;
    private NsPackagesApi nsPackagesApi;
    private NetSliceTemplatesApi netSliceTemplatesApi;
    private NfvoInformation nfvoInformation;
    private final OAuthSimpleClient oAuthSimpleClient;
    private NfvoLcmOperationPollingManager pollingManager;
    private ArrayList<String> nsLevelOperations= new ArrayList<>();
    private ArrayList<String> networkSliceLevelOperations= new ArrayList<>();
    public Osm10Client(NfvoInformation info, NfvoLcmOperationPollingManager pollingManager){

        nsInstancesApi = new NsInstancesApi();
        nsPackagesApi = new NsPackagesApi();
        netSliceTemplatesApi = new NetSliceTemplatesApi();
        networkSliceInstancesApi  = new NetworkSliceInstancesApi();
        this.nfvoInformation =info;
        this.oAuthSimpleClient = new OAuthSimpleClient(info.getBaseUrl()+"/admin/v1/tokens",
                info.getUsername(),
                info.getPassword(),
                info.getProject());
        this.pollingManager = pollingManager;
    }

    @Override
    public String createNetworkServiceIdentifier(CreateNsIdentifierRequestInternal request) throws FailedOperationException {
        log.debug("Received request to create a network service identifier");
        ApiClient apiClient = getClient();
        nsInstancesApi.setApiClient(apiClient);
        nsPackagesApi.setApiClient(apiClient);
        InstantiateNsRequest osmReq = new InstantiateNsRequest();
        osmReq.setVimAccountId(nfvoInformation.getVimAccountId());

        osmReq.setNsName(request.getNsName());

        try {

            UUID osmId = getIdForNsdId(request.getNsdId());
            osmReq.setNsdId(osmId);
            return nsInstancesApi.addNSinstance(osmReq).getId().toString();


        } catch (ApiException e) {
            log.error("Error while creating NS Identifier", e);
          throw new FailedOperationException(e);
        }

    }


    @Override
    public String createNetworkSliceIdentifier(CreateNetworkSliceIdentifierRequestInternal request) throws FailedOperationException {
        log.debug("Received request to create a network slice identifier");
        ApiClient apiClient = getClient();
        networkSliceInstancesApi.setApiClient(apiClient);
        netSliceTemplatesApi.setApiClient(apiClient);
        InstantiateNetworkSliceRequest osmReq = new InstantiateNetworkSliceRequest();
        osmReq.setVimAccountId(nfvoInformation.getVimAccountId());
        File configFile = new File(request.getConfigFile());
        log.debug("Trying to read NST config file:"+configFile.getAbsolutePath());
        List<NetSliceSubnet> subnets = new ArrayList<>();
        if(configFile.exists()&& !configFile.isDirectory()){
            try {
                log.debug("Parsing NST config file:"+configFile.getAbsolutePath());
                Reader reader = Files.newBufferedReader(Paths.get(configFile.getAbsolutePath()));
                subnets = new Gson().fromJson(reader, new TypeToken<List<NetSliceSubnet>>() {}.getType());
                osmReq.setNetSliceSubnets(subnets);
            } catch (IOException e) {
                throw new FailedOperationException(e);
            }
        }
        osmReq.setNsiName(request.getNsName());

        try {

            UUID osmId = getIdForNstId(request.getNstId());
            osmReq.setNstId(osmId);
            return networkSliceInstancesApi.addNetworkSliceInstance(osmReq).getId().toString();


        } catch (ApiException e) {
            log.error("Error while creating NS Identifier", e);
            throw new FailedOperationException(e);
        }

    }

    @Override
    public String instantiateNetworkSlice(InstantiateNetworkSliceRequestInternal request) throws FailedOperationException {
        log.debug("Received request to instantiate a network slice");
        ApiClient apiClient = getClient();
        networkSliceInstancesApi.setApiClient(apiClient);
        InstantiateNetworkSliceRequest osmReq = new InstantiateNetworkSliceRequest();
        //osmReq.setAdditionalParamsForNs(request.getAdditionalParamForNs());
        osmReq.setNsiName(request.getNsName());
        osmReq.setVimAccountId(nfvoInformation.getVimAccountId());

        try {
            UUID osmNstId = getIdForNstId(request.getNstId());
            osmReq.setNstId(osmNstId);
            String operationId = networkSliceInstancesApi.instantiateNetworkSliceinstance(request.getNsInstanceId(), osmReq).getId().toString();
            log.debug("triggered instantiation with ID:"+operationId);
            networkSliceLevelOperations.add(operationId);
            pollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, request.getNsInstanceId(), NfvoLcmOperationType.NS_INSTANTIATION, this);
            return operationId;
        }catch (ApiException e) {
            log.error("Error while instantiating a Network Slice Identifier", e);
            throw new FailedOperationException(e);
        }
    }

    @Override
    public NsInstance getNetworkSliceInstance(String nsInstanceId) throws FailedOperationException {
        return null;
    }

    @Override
    public String instantiateNetworkService(InstantiateNsRequestInternal request) throws FailedOperationException {
        log.debug("Received request to create a network service identifier");
        ApiClient apiClient = getClient();
        nsInstancesApi.setApiClient(apiClient);
        nsPackagesApi.setApiClient(apiClient);
        InstantiateNsRequest osmReq = new InstantiateNsRequest();
        osmReq.setAdditionalParamsForNs(request.getAdditionalParamForNs());
        osmReq.setNsName(request.getNsName());
        osmReq.setVimAccountId(nfvoInformation.getVimAccountId());
        try {
            UUID osmNsdId = getIdForNsdId(request.getNsdId());
            //Nsd nsd = nsPackagesApi.getNSDescriptor(osmNsdId.toString());

            osmReq.setNsdId(osmNsdId);
            if(request.getVlAllocations()!=null && !request.getVlAllocations().isEmpty()){
                List<InstantiateNsRequestVld> vlds = new ArrayList<>();
                for(VlAllocation vlAllocation: request.getVlAllocations()){
                    InstantiateNsRequestVld vld = new InstantiateNsRequestVld();
                    vld.setName(vlAllocation.getName());
                    vld.setVimNetworkName(vlAllocation.getVimNetworkName());
                    List<InstantiateNsRequestVnfdconnectionpointref> cps = new ArrayList<>();
                    if(vlAllocation.getVnfConnectionPointAllocations()!=null &&
                    !vlAllocation.getVnfConnectionPointAllocations().isEmpty()){
                        for(VnfConnectionPointAllocation vnfConnectionPointAllocation: vlAllocation.getVnfConnectionPointAllocations()){
                            InstantiateNsRequestVnfdconnectionpointref cpRef = new InstantiateNsRequestVnfdconnectionpointref();
                            cpRef.setIpAddress(vnfConnectionPointAllocation.getIpAddress());
                            cpRef.setVnfdConnectionPointRef(vnfConnectionPointAllocation.getVnfdConnectionPoint());
                            cpRef.setMemberVnfIndexRef(vnfConnectionPointAllocation.getMemberVnfIndex());
                            cps.add(cpRef);
                        }
                        vld.setVnfdConnectionPointRef(cps);
                    }

                    vlds.add(vld);
                }
                osmReq.setVld(vlds);
            }
            if(request.getVnfAllocations()!=null && !request.getVnfAllocations().isEmpty()){

                List<InstantiateNsRequestVnf> vnfs = new ArrayList<>();
                for(VnfAllocation vnfAllocation: request.getVnfAllocations()){
                    InstantiateNsRequestVnf curVnf = new InstantiateNsRequestVnf();
                    curVnf.setMemberVnfIndex(vnfAllocation.getMemberVnfIndex());
                    curVnf.setVimAccountId(vnfAllocation.getVimId());
                    vnfs.add( curVnf);
                }
                osmReq.setVnf(vnfs);
            }
            String operationId = nsInstancesApi.instantiateNSinstance(request.getNsInstanceId(), osmReq).getId().toString();
            nsLevelOperations.add(operationId);
            pollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, request.getNsInstanceId(), NfvoLcmOperationType.NS_INSTANTIATION, this);
            return operationId;
        } catch (ApiException e) {
            log.error("Error while instantiating an NS Identifier", e);
            throw new FailedOperationException(e);
        }
    }

    @Override
    public NsInstance getNsInstance(String nsInstanceId) throws  FailedOperationException{
        log.debug("Received request to retrieve NS Instance:"+nsInstanceId);
        ApiClient apiClient = getClient();
        nsInstancesApi.setApiClient(apiClient);
        try {
            return nsInstancesApi.getNSinstance(nsInstanceId, false);
        } catch (ApiException e) {
            log.error("Error retrieving VNF instance:", e );
            throw new FailedOperationException(e);
        }
    }
    @Override
    public VnfInstanceInfo getVnfInstanceInfo(String vnfInstanceRecordId) throws FailedOperationException {
        log.debug("Received request to retrieve VNF Instance info for record:"+vnfInstanceRecordId);
        ApiClient apiClient = getClient();
        nsInstancesApi.setApiClient(apiClient);
        try {
            return nsInstancesApi.getVnfInstance(vnfInstanceRecordId);
        } catch (ApiException e) {
            log.error("Error retrieving VNF instance:", e );
            throw new FailedOperationException(e);
        }
    }

    @Override
    public OperationStatus getOperationStatus(String operationId) throws FailedOperationException {
        log.debug("Received request to query a network service operation");
        nsInstancesApi.setApiClient(getClient());
        try {
            String operationResult = null;
            if(nsLevelOperations.contains(operationId)){
                NsLcmOpOcc opResult =  nsInstancesApi.getNSLCMOpOcc(operationId);
                operationResult = opResult.getOperationState();
            }else{
                NetworkSliceLcmOpOcc opResult = networkSliceInstancesApi.getNetworkSliceLCMOpOcc(operationId);
                operationResult = opResult.getOperationState();
            }

            OsmNsLcmOperationStatus  osmOperationStatus = OsmNsLcmOperationStatus.valueOf(operationResult);

            if (OsmNsLcmOperationStatus.FAILED == osmOperationStatus || OsmNsLcmOperationStatus.FAILED_TEMP == osmOperationStatus) {
                return OperationStatus.FAILED;
            } else if (OsmNsLcmOperationStatus.COMPLETED==osmOperationStatus || osmOperationStatus==OsmNsLcmOperationStatus.PARTIALLY_COMPLETED) {
                return OperationStatus.SUCCESSFULLY_DONE;
            } else if (osmOperationStatus==OsmNsLcmOperationStatus.ROLLING_BACK || osmOperationStatus==OsmNsLcmOperationStatus.ROLLED_BACK) {
                //TODO: See implications
                return OperationStatus.FAILED;
            } else if (osmOperationStatus==OsmNsLcmOperationStatus.PROCESSING) {
                return OperationStatus.PROCESSING;
            }else return null;

        } catch (ApiException e) {
            log.error("Error while creating NS Identifier", e);
            throw new FailedOperationException(e);
        }
    }

    @Override
    public String terminateNetworkSlice(String networkSliceInstanceId) throws FailedOperationException {
        log.debug("Received request to terminate network slice:"+networkSliceInstanceId);
        networkSliceInstancesApi.setApiClient(getClient());

        try {
            String operationId = networkSliceInstancesApi.terminateNetworkSliceInstance(networkSliceInstanceId, null).getId().toString();
            pollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, networkSliceInstanceId, NfvoLcmOperationType.NS_TERMINATION, this);
            networkSliceLevelOperations.add(operationId);
            return operationId;
        } catch (ApiException e) {
            log.error("Error while terminating Network Slice", e);
            throw new FailedOperationException(e);
        }
    }

    @Override
    public String terminateNetworkService(String networkServiceInstanceId) throws FailedOperationException {
        log.debug("Received request to terminate network service:"+networkServiceInstanceId);
        nsInstancesApi.setApiClient(getClient());

        try {
            String operationId = nsInstancesApi.terminateNSinstance(networkServiceInstanceId, null).getId().toString();
            pollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, networkServiceInstanceId, NfvoLcmOperationType.NS_TERMINATION, this);
            nsLevelOperations.add(operationId);
            return operationId;
        } catch (ApiException e) {
            log.error("Error while terminating NS", e);
            throw new FailedOperationException(e);
        }
    }

    public void deleteNetworkServiceRecord(String networkServiceInstanceId) throws FailedOperationException {
        log.debug("Receive request to delete record of network service: "+networkServiceInstanceId);
        nsInstancesApi.setApiClient(getClient());

        try{
            nsInstancesApi.deleteNSinstance(networkServiceInstanceId, null);
        }catch (ApiException e){
            log.error("Error while deleting NS record", e);
            throw new FailedOperationException(e);
        }
    }


    private ApiClient getClient() throws FailedOperationException {

        ApiClient apiClient = new ApiClient();
        apiClient.setHttpClient(OAuthSimpleClient.getUnsafeOkHttpClient());
        apiClient.setBasePath(this.nfvoInformation.getBaseUrl());
        apiClient.setUsername(this.nfvoInformation.getUsername());
        apiClient.setPassword(this.nfvoInformation.getPassword());
        apiClient.setAccessToken(oAuthSimpleClient.getToken());
        apiClient.setReadTimeout(0);
        apiClient.setConnectTimeout(0);
        apiClient.setWriteTimeout(0);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        apiClient.getHttpClient().interceptors().add(interceptor);
        apiClient.setDebugging(true);
        return apiClient;
    }

    private UUID getIdForNsdId(String nsdId) throws FailedOperationException, ApiException {
        ApiClient apiClient = getClient();
        nsPackagesApi.setApiClient(apiClient);
        ArrayOfNsdInfo nsdInfos = nsPackagesApi.getNSDs();
        UUID osmId = null;
        for(NsdInfo nsdInfo : nsdInfos){
            if(nsdInfo.getIdentifier().equals(nsdId)){
                osmId = nsdInfo.getId();
                return osmId;
            }
        }
        throw new FailedOperationException("Could not find UUID for nsdId:"+nsdId);


    }
    private UUID getIdForNstId(String nstId) throws FailedOperationException, ApiException {
        ApiClient apiClient = getClient();
        netSliceTemplatesApi.setApiClient(apiClient);
        ArrayOfNstInfo nstInfos = netSliceTemplatesApi.getNSTs();
        UUID osmId = null;
        for(NstInfo nstInfo : nstInfos){
            if(nstInfo.getIdentifier().equals(nstId)){
                osmId = nstInfo.getId();
                return osmId;
            }
        }
        throw new FailedOperationException("Could not find UUID for nstId:"+nstId);


    }

}


