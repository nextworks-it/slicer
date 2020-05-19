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


import it.nextworks.nfvmano.sebastian.pp.SbRestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

import java.util.UUID;

public class CPSService extends SbRestClient {
    private static final Logger log = LoggerFactory.getLogger(CPSService.class);
    private CPSTypes cspType;

    public CPSService() {

    }

    public CPSTypes getCspType() {
        return cspType;
    }

    public void setCspType(CPSTypes cspType) {
        this.cspType = cspType;
    }

    //http://10.8.202.11:8080/slicenet/ctrlplane/cpsr_cps/v1/cps-instances?cpsType=QOS_CP&slicenetId=f5b01594-520e-11e9-8647-d663bd873d93
    public String retrieveCpsUri(UUID sliceUuid) throws RestClientResponseException {

        String url = String.format("%s/slicenet/ctrlplane/cpsr_cps/v1/cps-instances?cpsType=%s&slicenetId=%s",
                this.getTargetUrl(), this.cspType.name(), sliceUuid.toString());
        ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.GET);
        try {
            JSONArray json = new JSONArray(httpResponse.getBody());
            if (json.length() == 0) {
                return null;
            }
            JSONObject jobj = (JSONObject) json.get(0);
            return jobj.getString("uri");
        }catch (JSONException e){
            log.info("Error in JSON manipulation: "+e.getMessage());
            return "";
        }
    }
}
