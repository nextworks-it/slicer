import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.OsmNfvoDomainLayer;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.NeutralHostingRestClient;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeutralHostRestClient {

    private static NeutralHostingRestClient neutralHostingRestClient;

    @BeforeClass
    public static void preliminarOps() {
        OsmNfvoDomainLayer osmNfvoDomainLayer = new OsmNfvoDomainLayer("OsmDomainLayer", "admin", "admin", "admin");
        neutralHostingRestClient = new NeutralHostingRestClient("i2cat", "http://172.16.150.5:8989", "5e6a5fc83023e56228991819", "5e6b4c413023e52b3a0f27cb", osmNfvoDomainLayer, null, null, null);
    }

    @Test
    @Ignore
    public void restClientTest() throws Exception {
        CreateNsiIdRequest request0 = new CreateNsiIdRequest("nstIdxxxx", null, null);
        String nsdInfoId = neutralHostingRestClient.createNetworkSliceIdentifier(request0, "DomainBid", null);
        InstantiateNsiRequest request = new InstantiateNsiRequest(nsdInfoId, null, null, null, null, null, null, null);
        neutralHostingRestClient.instantiateNetworkSlice(request, "DomainBid", "5e6b4c413023e52b3a0f27cb");
        Map<String, String> parameters = new HashMap<String, String>();
        //parameters.put("NSI_ID", nsdInfoId);
        Filter filter = new Filter(parameters);
        GeneralizedQueryRequest request1 = new GeneralizedQueryRequest(filter, new ArrayList<>());
        List<NetworkSliceInstance> instances = neutralHostingRestClient.queryNetworkSliceInstance(request1, null, null);
        Thread.sleep(20000);
        //neutralHostingRestClient.terminateNetworkSliceInstance(null, null, null);
    }
}
