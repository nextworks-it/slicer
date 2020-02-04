/*
 * Copyright (c) 2020 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.sebastian.nsmf.sbi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PnPCommunicationService extends NsmfSbRestClient {

    public PnPCommunicationService() {

    }

    private ObjectNode buildNetworkSliceInfo(NST nst, UUID sliceId) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("slice_id", sliceId.toString());
        objectNode.put("slice_name", nst.getNstName());
        objectNode.put("slice_owner", nst.getNstProvider());
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

    public PnPCommunicationService(String targetUrl) {
        this.setTargetUrl(targetUrl);
    }

    private Object generateRequestPayload(NST nst, UUID sliceId) {
        ObjectNode sliceInfoHeader = buildNetworkSliceInfo(nst, sliceId);
        ObjectNode sliceInfoFeatures = buildRequiredComponentFeature(nst);
        return new Object();

    }

    public String deploySliceComponents(UUID sliceUuid, NST nst){
        String url = this.getTargetUrl() + "/plug-and-play/slice/" + sliceUuid;
        Object requestPayload = generateRequestPayload(nst, sliceUuid);
        ResponseEntity<String> httpResponse = this.performHTTPRequest(requestPayload, url, HttpMethod.POST);
        return manageHTTPResponse(httpResponse, "Error - Per-Slice Components creation request NOT SUCCEEDED",
                "Per-Slice Components creation request succeeded", HttpStatus.OK);
    }

    public String terminateSliceComponents(String sliceUuid){
        String url = this.getTargetUrl() + "/plug-and-play/slice/" + sliceUuid;
        ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.DELETE);
        return manageHTTPResponse(httpResponse, "Error - Per-Slice Components termination request NOT SUCCEEDED",
                "Per-Slice Components termination request succeeded", HttpStatus.OK);
    }

}
