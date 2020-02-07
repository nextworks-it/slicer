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

import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.*;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

@Service
public class FlexRanService extends CPSService{
    private static final Logger log = LoggerFactory.getLogger(FlexRanService.class);

    private Map<UUID, Integer> flxIdSliceId;
    private List<Integer> availableRanId = new ArrayList<>();

    @Value("${flexran.url}")
    private String flexRanURL;

    @Value("${ranadapter.url}")
    private String ranAdapterUrl;

    public FlexRanService() {
        this.flxIdSliceId = new HashMap<>();
        this.availableRanId =  IntStream.range(1, 255)  //NOTE 1-255 taken from FlexRan API Doc
                .boxed()
                .collect(toList());
    }

    private Integer getFirstAvailableRanId(){
        return this.availableRanId.remove(0);
    }

    public Map<UUID, Integer> getFlxIdSliceId() {
        return flxIdSliceId;
    }

    public Integer getRanId(UUID sliceId){
        Integer id = this.flxIdSliceId.get(sliceId);
        return id;
    }
    public void removeIdMapping(UUID sliceId){
        this.flxIdSliceId.remove(sliceId);
    }

    private void setIdsMap(UUID sliceId, Integer ranId){
        this.flxIdSliceId.put(sliceId, ranId);
    }

    public HttpStatus createRanSlice(UUID sliceId){
        Integer ranId  = getFirstAvailableRanId();
        String url = String.format("%s/slice/enb/-1/slice/%d", flexRanURL, ranId);
        try{
            ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.POST);
            this.setIdsMap(sliceId, ranId);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }

    }

    /*slicenetid: "f5b01594-520e-11e9-8647-d663bd873d93"
             sid: "1"*/
    public HttpStatus mapIdsRemotely(UUID sliceId){
        Integer ranId = getRanId(sliceId);
        String url = String.format("%s/ranadapter/all/v1/set_slice_mapping", ranAdapterUrl);
        Map<String, String> slicePair = new HashMap<>();
        slicePair.put("slicenetid", sliceId.toString());
        slicePair.put("sid", ranId.toString());
        try{
            JSONObject payload = new JSONObject(slicePair);
            ResponseEntity<String> httpResponse = this.performHTTPRequest(payload.toString(), url, HttpMethod.POST);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }

    }

    public HttpStatus terminateRanSlice(UUID sliceId){

        Integer ranId = getRanId(sliceId);
        Map<String, String> slicePair = new HashMap<>();
        slicePair.put("slicenetid", sliceId.toString());
        JSONObject request = new JSONObject(slicePair);
        String ranAdapterUrl = String.format( "%s/ranadapter/all/v1/set_slice_mapping", this.ranAdapterUrl);
        String flexRanUrl = String.format("%s/slice/enb/-1/slice/%d", flexRanURL, ranId);
        try{
            ResponseEntity<String> httpResponse = this.performHTTPRequest(request, ranAdapterUrl, HttpMethod.DELETE);
            log.info("Slice"+ sliceId.toString()+ "removed from RanAdapter - HTTP Code: " + httpResponse.getStatusCode());
            removeIdMapping(sliceId);
            httpResponse = this.performHTTPRequest(null, flexRanUrl, HttpMethod.DELETE);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }
}
