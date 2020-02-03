package it.nextworks.nfvmano.sebastian.nsmf.ppinteraction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class PpRestClient {

    private static final Logger log = LoggerFactory.getLogger(PpRestClient.class);
    private RestTemplate restTemplate;

    //TODO add config var
    private String ppUrl;

    public PpRestClient() {

    }

    public void startPpApplications() {
        log.debug("Going to start P&P functions");

    }

    public void terminatePpApplication() {
        log.debug("Going to terminate P&P functions");

    }

    public void configurePpApplication() {
        log.debug("Going to configure P&P functions");

    }


    private ObjectNode buildNetworkSliceInfo(NST nst) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("slice_id", "");
        objectNode.put("slice_name", "");
        objectNode.put("slice_owner", "");
        objectNode.put("slice_domain_type", "single");
        return objectNode;
    }

    private ObjectNode buildRequiredComponentFeature(NST nst) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode reqComponentFeatureArray = mapper.createArrayNode();

        for (int i = 0; i < nst.getPpFunctionList().size(); i++) {
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("seq_id", nst.getPpFunctionList().get(i).getSeqId());
            objectNode.put("feature_id", nst.getPpFunctionList().get(i).getPpFeatureName());
            objectNode.put("feature_type", nst.getPpFunctionList().get(i).getPpFeatureType().toString().toLowerCase());
            objectNode.put("feature_level", nst.getPpFunctionList().get(i).getPpFeatureLevel().toString().toLowerCase());
            reqComponentFeatureArray.add(objectNode);
        }
        ObjectNode reqComponentFeature = mapper.createObjectNode();
        reqComponentFeature.putPOJO("required_feature", reqComponentFeatureArray);
        return reqComponentFeature;
    }
}
