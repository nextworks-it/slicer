package it.nextworks.nfvmano.sebastian.pp.service;

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


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.libs.ifa.templates.plugAndPlay.PpFeatureLevel;
import it.nextworks.nfvmano.libs.ifa.templates.plugAndPlay.PpFeatureType;
import it.nextworks.nfvmano.sebastian.pp.SbRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PnPCommunicationService extends SbRestClient {
    private static final Logger log = LoggerFactory.getLogger(PnPCommunicationService.class);

    private List<UUID> sliceIDs;
    public PnPCommunicationService() {
        sliceIDs = new ArrayList<>();
    }

    public boolean isPnP(UUID sliceId){
        return sliceIDs.contains(sliceId);
    }

    //DSP-SIDE ONLY
    public HttpStatus deployTestFeature(UUID sliceUuid, String sliceName, String sliceTenant, List<String> domainList){
        try{
            String url = this.getTargetUrl() + "/plug-and-play-manager/slice/" + sliceUuid +"/";
            Object requestPayload = generatePayloadFeatureTest(sliceUuid, sliceName, sliceTenant, domainList);
            log.info("Payload");
            log.info(requestPayload.toString());
            ResponseEntity<String> httpResponse = this.performHTTPRequest(requestPayload, url, HttpMethod.POST);
            sliceIDs.add(sliceUuid);
            return httpResponse.getStatusCode();
        } catch (RestClientResponseException e) {
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    //DSP-SIDE ONLY
    public HttpStatus deployQoEFeature(UUID sliceUuid, String sliceName, String sliceTenant, List<String> domainList){
        try{
            String url = this.getTargetUrl() + "/plug-and-play-manager/slice/" + sliceUuid +"/";
            Object requestPayload = generateQoEFeature(sliceUuid, sliceName, sliceTenant, domainList);
            log.info("Payload");
            log.info(requestPayload.toString());
            ResponseEntity<String> httpResponse = this.performHTTPRequest(requestPayload, url, HttpMethod.POST);
            sliceIDs.add(sliceUuid);
            return httpResponse.getStatusCode();
        } catch (RestClientResponseException e) {
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    private ArrayNode buildDomainsList(List<String> domainList) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode domainsList = mapper.createArrayNode();
        for(String domain: domainList){
            domainsList.add(domain);
        }
        return domainsList;
    }


    private ObjectNode generatePayloadFeatureTest(UUID sliceId, String sliceName, String sliceTenant, List<String> domainList) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode networkSliceInfo = mapper.createObjectNode();
        networkSliceInfo.put("slice_id", sliceId.toString());
        networkSliceInfo.put("slice_name", sliceName);
        networkSliceInfo.put("slice_tenant", sliceTenant);
        networkSliceInfo.put("slice_domain_type", "single");
        ArrayNode domainsList = buildDomainsList(domainList);
        networkSliceInfo.putPOJO("domains", domainsList);

        ObjectNode requiredFeatureJSON = mapper.createObjectNode();
        requiredFeatureJSON.put("seq_id", 0);
        requiredFeatureJSON.put("feature_id", "test_feature");
        requiredFeatureJSON.put("feature_type", PpFeatureType.CONTROL.toString().toLowerCase());
        requiredFeatureJSON.put("feature_level", PpFeatureLevel.SLICE.toString().toLowerCase());
        ArrayNode reqComponentFeatureArray = mapper.createArrayNode();
        reqComponentFeatureArray.add(requiredFeatureJSON);
        networkSliceInfo.putPOJO("required_feature", reqComponentFeatureArray);

        return networkSliceInfo;
    }


    private ObjectNode generateQoEFeature(UUID sliceId, String sliceName, String sliceTenant, List<String> domainList) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode networkSliceInfo = mapper.createObjectNode();
        networkSliceInfo.put("slice_id", sliceId.toString());
        networkSliceInfo.put("slice_name", sliceName);
        networkSliceInfo.put("slice_tenant", sliceTenant);
        networkSliceInfo.put("slice_domain_type", "single");
        ArrayNode domainsList = buildDomainsList(domainList);
        //networkSliceInfo.putPOJO("domains", domainsList);

        ObjectNode requiredFeatureJSON = mapper.createObjectNode();
        requiredFeatureJSON.put("seq_id", 0);
        requiredFeatureJSON.put("feature_id", "qoe_man_feature");
        requiredFeatureJSON.put("feature_type", PpFeatureType.MANAGEMENT.toString().toLowerCase());
        requiredFeatureJSON.put("feature_level", PpFeatureLevel.SLICE.toString().toLowerCase());
        ArrayNode reqComponentFeatureArray = mapper.createArrayNode();
        reqComponentFeatureArray.add(requiredFeatureJSON);
        networkSliceInfo.putPOJO("required_feature", reqComponentFeatureArray);

        return networkSliceInfo;
    }

    //NSP-SIDE ONLY
    private ObjectNode generateRequestPayload(NST nst, UUID sliceId) {
        ObjectNode sliceInfoHeader = buildNetworkSliceInfo(nst, sliceId);
        ArrayNode sliceInfoFeatures = buildRequiredComponentFeature(nst);
        sliceInfoHeader.putPOJO("required_feature", sliceInfoFeatures);
        return sliceInfoHeader;
    }


    private ArrayNode buildRequiredComponentFeature(NST nst) {
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

        return reqComponentFeatureArray;
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


    public HttpStatus deploySliceComponents(UUID sliceUuid, NST nst){
        try{
            String url = this.getTargetUrl() + "/plug-and-play-manager/slice/" + sliceUuid +"/";
            Object requestPayload = generateRequestPayload(nst, sliceUuid);

            ResponseEntity<String> httpResponse = this.performHTTPRequest(requestPayload, url, HttpMethod.POST);
            sliceIDs.add(sliceUuid);
            return httpResponse.getStatusCode();
        } catch (RestClientResponseException e) {
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    public HttpStatus terminateSliceComponents(UUID sliceUuid){
        try{
            String url = this.getTargetUrl() + "/plug-and-play-manager/slice/" + sliceUuid + "/";
            ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.DELETE);
            sliceIDs.remove(sliceUuid);
            return httpResponse.getStatusCode();
        } catch (
                RestClientResponseException e) {
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }

    }

}

