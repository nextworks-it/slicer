package it.nextworks.nfvmano.sbi.nfvo.osm;


import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import it.nextworks.nfvmano.sbi.nfvo.elements.NfvoInformation;
import it.nextworks.nfvmano.sbi.nfvo.interfaces.NsLcmProviderInterface;
import it.nextworks.nfvmano.sbi.nfvo.messages.CreateNsIdentifierRequestInternal;
import it.nextworks.nfvmano.sbi.nfvo.messages.InstantiateNsRequestInternal;
import it.nextworks.nfvmano.sbi.nfvo.messages.OperationStatus;
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

import java.util.UUID;




public class Osm10Client implements NsLcmProviderInterface {
    private static final Logger log = LoggerFactory.getLogger(Osm10Client.class);

    private NsInstancesApi nsInstancesApi;
    private NsPackagesApi nsPackagesApi;
    private NfvoInformation nfvoInformation;
    private final OAuthSimpleClient oAuthSimpleClient;
    private NfvoLcmOperationPollingManager pollingManager;

    public Osm10Client(NfvoInformation info, NfvoLcmOperationPollingManager pollingManager){

        nsInstancesApi = new NsInstancesApi();
        nsPackagesApi = new NsPackagesApi();
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
            Nsd nsd = nsPackagesApi.getNSDescriptor(osmNsdId.toString());
            for(VirtualLinkDesc vl: nsd.getVirtualLinkDesc()){
                if(vl.getId().contains("ext")){
                    log.debug("mapping vl:"+vl.getId()+"to external network:"+ nfvoInformation.getVimExternalNetwork());
                    InstantiateNsRequestVld vldReq = new InstantiateNsRequestVld();
                    vldReq.setName(vl.getId());
                    vldReq.setVimNetworkName(nfvoInformation.getVimExternalNetwork());
                    osmReq.addVldItem(vldReq);
                }
            }
            osmReq.setNsdId(osmNsdId);
            String operationId = nsInstancesApi.instantiateNSinstance(request.getNsInstanceId(), osmReq).getId().toString();
            pollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, request.getNsInstanceId(), NfvoLcmOperationType.NS_INSTANTIATION, this);
            return operationId;
        } catch (ApiException e) {
            log.error("Error while instantiating an NS Identifier", e);
            throw new FailedOperationException(e);
        }
    }

    @Override
    public OperationStatus getOperationStatus(String operationId) throws FailedOperationException {
        log.debug("Received request to query a network service operation");
        nsInstancesApi.setApiClient(getClient());
        try {
            NsLcmOpOcc opResult = nsInstancesApi.getNSLCMOpOcc(operationId);
            OsmNsLcmOperationStatus  osmOperationStatus = OsmNsLcmOperationStatus.valueOf(opResult.getOperationState());

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
    public String terminateNetworkService(String networkServiceInstanceId) throws FailedOperationException {
        log.debug("Received request to terminate network service:"+networkServiceInstanceId);
        nsInstancesApi.setApiClient(getClient());

        try {
            String operationId = nsInstancesApi.terminateNSinstance(networkServiceInstanceId, null).getId().toString();
            pollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, networkServiceInstanceId, NfvoLcmOperationType.NS_TERMINATION, this);
            return operationId;
        } catch (ApiException e) {
            log.error("Error while terminating NS", e);
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
}


