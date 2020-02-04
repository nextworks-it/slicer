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

import java.util.UUID;

public class CPSService extends NsmfSbRestClient {

    private CSPTypes cspType;


    public CPSService() {
        this.setTargetUrl("CPSRTargetURL");
    }

    public CSPTypes getCspType() {
        return cspType;
    }

    public void setCspType(CSPTypes cspType) {
        this.cspType = cspType;
    }

    public String retrieveCpsUri(UUID sliceUuid){
        //http://10.8.202.11:8080/slicenet/ctrlplane/cpsr_cps/v1/cps-instances?cpsType=QOS_CP&slicenetId=f5b01594-520e-11e9-8647-d663bd873d93
        String url = String.format("%s/slicenet/ctrlplane/cpsr_cps/v1/cps-instances?cpsType=%s&slicenetId=%s",
                this.getTargetUrl(), this.cspType.name(), sliceUuid.toString());
        ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.GET);
        return manageHTTPResponse(httpResponse, "Error - Per-Slice Components creation request NOT SUCCEEDED",
                "Per-Slice Components creation request succeeded", HttpStatus.OK);
    }
}
