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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.UUID;

@Service
public class QoSService extends CPSService{
    private static final Logger log = LoggerFactory.getLogger(QoSService.class);
    private String qosCpBaseURl;

    public QoSService() {
        this.setCspType(CPSTypes.QOS_CP);
    }

    public void getQoSInfo(UUID sliceId) {
        this.qosCpBaseURl = this.retrieveCpsUri(sliceId);
    }

    public HttpStatus setQoS(UUID sliceId, JSONObject qosConstraints) throws Exception{
        try {
            this.getQoSInfo(sliceId);
            String url = String.format("http://%s/slicenet/ctrlplane/qos/v1/qos-instance/%s/qos_constraints?segment_id=ACCESS", /*this.qosCpBaseURl*/"10.8.202.4:30001", sliceId.toString());
            ResponseEntity<String> httpResponse = this.performHTTPRequest(qosConstraints.toString(), url, HttpMethod.POST);
            System.out.println(httpResponse.getBody());
            return httpResponse.getStatusCode();
        } catch (RestClientResponseException e){
            log.info("Message received: "+e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    public HttpStatus updateQoS(UUID sliceId, JSONObject qosConstraints){
        try {
            this.getQoSInfo(sliceId);
            String url = String.format("http://%s/qos-instance/%s/qos_constraints", this.qosCpBaseURl, sliceId.toString());
            ResponseEntity<String> httpResponse = this.performHTTPRequest(qosConstraints.toString(), url, HttpMethod.PUT);
            System.out.println(httpResponse.getBody());
            return httpResponse.getStatusCode();
        } catch (RestClientResponseException e){
            log.info("Message received: "+e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

}
