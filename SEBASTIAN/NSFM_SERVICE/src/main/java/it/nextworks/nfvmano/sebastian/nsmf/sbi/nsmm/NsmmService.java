package it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.client.model.*;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.records.vnfinfo.VnfInfo;
import it.nextworks.nfvmano.nfvodriver.osm10.OAuthSimpleClient;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.messages.NsmmAllocationResponse;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.api.NetworkResourcesApi;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.api.VpnConnectionsApi;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.model.*;
import it.nextworks.osm.ApiClient;
import it.nextworks.osm.ApiException;
import it.nextworks.osm.openapi.NsInstancesApi;
import it.nextworks.osm.openapi.NsPackagesApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is only compatible with OSM
 */
@Service
public class NsmmService {


    private static final Logger log = LoggerFactory.getLogger(NsmmService.class);
    @Value("${nfvo.catalogue.address}")
    private String nfvoCatalogueAddress;


    @Value("${nfvo.catalogue.username:admin}")
    private String nfvoCatalogueUsername;


    @Value("${nsmm.gw_id:zorro_uc1_gw_vnfd}")
    private String vnfdGw;

    @Value("${nfvo.catalogue.password:admin}")
    private String nfvoCataloguePassword;

    @Value("${nfvo.catalogue.project:admin}")
    private String nfvoCatalogueProject;

    @Value("${nsmf.nsmm.address:http://localhost:8081}")
    private String nsmmAddress;

    @Value("${nfvo.lcm.vim:}")
    private String vimId;

    private NetworkResourcesApi networkResourcesApi = new NetworkResourcesApi();
    private VpnConnectionsApi vpnConnectionsApi = new VpnConnectionsApi();
    private  NsPackagesApi nsPackagesApi = new NsPackagesApi();
    private NsInstancesApi nsInstancesApi = new NsInstancesApi();

    private  OAuthSimpleClient oAuthSimpleClient;



    public NsmmAllocationResponse provisionNetworkServiceMeshNetworking(String nsdId, String version, String sliceId, String excludeSubnet) throws FailedOperationException {
        log.debug("Received request to provision NSMM connectivity for NSD: "+nsdId+" "+version+ " slice:"+sliceId);
        nsPackagesApi.setApiClient(getOsmClient());
        try {

            ArrayOfNsdInfo nsds = nsPackagesApi.getNSDs();
            for(NsdInfo nsdInfo : nsds){
                if(nsdInfo.getId().equals(nsdId)&&nsdInfo.getVersion().equals(version)){
                    log.debug("Found NSD. Trigger network allocation");
                    String nsdContent = nsPackagesApi.getNsPkgsIdContentNSDWithHttpInfoStr(nsdInfo.getIdentifier().toString()).getData();
                    networkResourcesApi.setApiClient(getNsmmClient());
                    return internalProvisionNetworkServiceMeshNetworking(nsdContent, networkResourcesApi, vimId, sliceId, excludeSubnet );
                }
            }
            throw new FailedOperationException("Could not find NSD"+nsdId);

        } catch (ApiException e) {
            log.error("Error during NSMM network allocation", e);
            throw new FailedOperationException(e);
        }


    }


    public void provisionNetworkServiceMeshManagerVpn(Integer nsmmInstanceId, String remoteGw, String exposedSubnet) throws FailedOperationException {
        log.debug("Received request to provision NSMM VPN: "+nsmmInstanceId+" "+" RemoteGW:"+remoteGw);
        vpnConnectionsApi.setApiClient(getNsmmClient());
        try {
            PostConnection body = new PostConnection();
            body.setRemotePeerIp(remoteGw);
            List<String> exposedSubnets = new ArrayList<>();
            exposedSubnets.add(exposedSubnet);
            body.setPeerExposedSubnets(exposedSubnets);
            vpnConnectionsApi.netResourcesIdGatewayConnectionsPost(nsmmInstanceId, body);
        } catch (it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.ApiException e) {
            log.error("Error during NSMM VPN allocation", e);
            throw new FailedOperationException(e);
        }

    }
    public String allocateFloatingIp(Integer nsmmId) throws FailedOperationException{
        log.debug("Received request to allocate floating IP for NSM with ID:" +nsmmId);
        networkResourcesApi.setApiClient(getNsmmClient());
        try {
            return networkResourcesApi.netResourcesIdGatewayExternalIpPut(nsmmId).getExternalIp();
        } catch (it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.ApiException e) {
            log.error("Error during NSMM floating IP allocation", e);
            throw new FailedOperationException(e);
        }

    }

    public static NsmmAllocationResponse internalProvisionNetworkServiceMeshNetworking(String nsdContent, NetworkResourcesApi networkResourcesApi, String vimId, String sliceId, String excludeSubnet) throws FailedOperationException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(nsdContent);
            JsonNode virutalLinkDesc = node.get("virtual-link-desc");
            Iterator<JsonNode> iterator = virutalLinkDesc.elements();
            PostSliceResources provisionNetworksRequest = new PostSliceResources();
            Map<String, String> allocatedVlNetworks = new HashMap<>();
            List<PostNetwork> vpnNetworks = new ArrayList<>();
            List<PostNetwork> internalNetworks = new ArrayList<>();
            while(iterator.hasNext()) {
                JsonNode vlNode = iterator.next();
                String vlId = vlNode.get("id").textValue();
                log.debug("Virtual link node:" + vlNode + " ID:" + vlId);
                if (vlId.startsWith("vpn_")) {
                    PostNetwork network = new PostNetwork();
                    network.setNetworkName(vlId);
                    allocatedVlNetworks.put(vlId, vlId);
                    vpnNetworks.add(network);
                } else if (vlId.startsWith("app_net")){
                    PostNetwork network = new PostNetwork();
                    network.setNetworkName(vlId);
                    //TODO: revise internal network allocation
                    allocatedVlNetworks.put(vlId, vlId);
                    internalNetworks.add(network);
                }else if(vlId.startsWith("gw_ext")){
                    PostSap postSap = new PostSap();
                    postSap.setNetworkName(vlId);
                    allocatedVlNetworks.put(vlId, vlId);

                    provisionNetworksRequest.addServiceAccessPointsItem(postSap);
                }

            }

            vpnNetworks.forEach(network -> provisionNetworksRequest.addNetworksItem(network));
            internalNetworks.forEach(network -> provisionNetworksRequest.addNetworksItem(network));
            if(provisionNetworksRequest.getNetworks().isEmpty())
                throw new FailedOperationException("Could not find internal network to attach VPN");
            if(provisionNetworksRequest.getServiceAccessPoints().isEmpty())
                throw new FailedOperationException("Could not find external network to attach VPN");

            provisionNetworksRequest.setVimName(vimId);
            provisionNetworksRequest.setSliceId(sliceId);
            provisionNetworksRequest.setExcludeSubnet(excludeSubnet);
            SliceResources sliceResources = networkResourcesApi.netResourcesPost(provisionNetworksRequest);
            log.debug("Successfully allocated NSMM networks. Id:"+sliceResources.getId());
            Map<String, String> allocatedVlSubnets = new HashMap<>();

            List<String> internalNetSubnets = new ArrayList<>();
            for(Network nsmNet : sliceResources.getNetworks()){
                allocatedVlSubnets.put(nsmNet.getNetworkName(), nsmNet.getSubnetCidr());
                if(nsmNet.getNetworkName().startsWith("app_net")){
                    internalNetSubnets.add(nsmNet.getSubnetCidr());
                }

            }




            return new NsmmAllocationResponse(sliceResources.getId(), allocatedVlSubnets, allocatedVlNetworks,
                    sliceResources.getServiceAccessPoints().get(0).getSubnetCidr(), internalNetSubnets);
        } catch (IOException e) {
            log.error("Error deserializing NSD:",e);
            throw new FailedOperationException(e);
        } catch (it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.ApiException e) {
            log.error("Error interfacing with NSMM NSD:",e);
            throw new FailedOperationException(e);
        }
    }

    private it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.ApiClient getNsmmClient(){
        it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.ApiClient client;
        client = new it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.ApiClient();
        client.setConnectTimeout(0);
        client.setWriteTimeout(0);
        client.setReadTimeout(0);
        client.setBasePath(nsmmAddress).setDebugging(true);
        return client;
    }

    public void configureVpnGw(Integer nsmmId, String instanceId) throws FailedOperationException {
        log.debug("Received request to configure NSMM VPN for:" +nsmmId+" nsInstance:"+instanceId);
        networkResourcesApi.setApiClient(getNsmmClient());
        nsInstancesApi.setApiClient(getOsmClient());
        try {
            PostGateway body = new PostGateway();
            for(VnfInstanceInfo vnfInstanceInfo : nsInstancesApi.getVnfInstances()) {
                if(vnfInstanceInfo.getNsrIdRef().equals(instanceId)) {

                    if(vnfInstanceInfo.getVnfdId().equals(vnfdGw)){
                        body.setMgmtIp(vnfInstanceInfo.geIpAddress());
                    }
                }
            }



            networkResourcesApi.netResourcesIdGatewayConfigPut(body, nsmmId);
        } catch (it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.ApiException e) {
            log.error("Error during NSMM VPN configuration", e);
            throw new FailedOperationException(e);
        } catch (ApiException e) {
            log.error("Error during NSMM VPN configuration", e);
            throw new FailedOperationException(e);
        }
    }

    private ApiClient getOsmClient() throws FailedOperationException {
        oAuthSimpleClient = new OAuthSimpleClient(nfvoCatalogueAddress+"/osm/admin/v1/tokens", nfvoCatalogueUsername, nfvoCatalogueUsername, nfvoCatalogueProject);
        ApiClient apiClient = new ApiClient();
        try {
            apiClient.setHttpClient(OAuthSimpleClient.getUnsafeOkHttpClient());
            apiClient.setBasePath(nfvoCatalogueAddress+"/osm/");
            apiClient.setUsername(nfvoCatalogueUsername);
            apiClient.setPassword(nfvoCataloguePassword);
            apiClient.setAccessToken(oAuthSimpleClient.getToken());
            apiClient.setDebugging(true);
            apiClient.setConnectTimeout(0);
            apiClient.setWriteTimeout(0);
            apiClient.setReadTimeout(0);
            return apiClient;
        } catch (it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException e) {
            throw new FailedOperationException(e);
        }

    }
}
