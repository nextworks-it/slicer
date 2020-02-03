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

import it.nextworks.nfvmano.libs.ifa.templates.NST;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PnPCommunicationService extends NsmfSbRestClient {

    public PnPCommunicationService() {

    }

    public PnPCommunicationService(String targetUrl) {
        this.setTargetUrl(targetUrl);
    }

    private Object GenerateRequestPayload(NST nst) {
        //TODO
        return new Object();

    }

    public String DeploySliceComponents(String sliceUuid, NST nst){
        String url = this.getTargetUrl() + "/plug-and-play/slice/" + sliceUuid;
        Object requestPayload = GenerateRequestPayload(nst);
        ResponseEntity<String> httpResponse = this.performHTTPRequest(requestPayload, url, HttpMethod.POST);
        return manageHTTPResponse(httpResponse, "Error - Per-Slice Components creation request NOT SUCCEEDED",
                "Per-Slice Components creation request succeeded", HttpStatus.OK);
    }

    public String TerminateSliceComponents(String sliceUuid){
        String url = this.getTargetUrl() + "/plug-and-play/slice/" + sliceUuid;
        ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.DELETE);
        return manageHTTPResponse(httpResponse, "Error - Per-Slice Components termination request NOT SUCCEEDED ",
                "Per-Slice Components termination request succeeded", HttpStatus.OK);
    }

}
