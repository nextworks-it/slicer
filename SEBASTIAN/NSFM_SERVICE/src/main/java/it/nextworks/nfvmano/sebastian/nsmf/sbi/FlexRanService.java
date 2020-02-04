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

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

@Service
public class FlexRanService extends CPSService{

    private Map<UUID, Integer> flxIdSliceId;
    private List<Integer> availableRanId = new ArrayList<>();


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
        String url = ""; //TODO FlexRan Url
        ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.POST);
        if (httpResponse.getStatusCode() != HttpStatus.OK){
            return httpResponse.getStatusCode();
        }
        this.setIdsMap(sliceId, ranId);
        return HttpStatus.OK;
    }

    public HttpStatus mapIdsRemotely(UUID sliceId){
        Integer ranId = getRanId(sliceId);
        String url= "";
        /*slicenetid: "f5b01594-520e-11e9-8647-d663bd873d93"
             sid: "1"*/
        ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.POST);
        return httpResponse.getStatusCode();
    }

    public HttpStatus terminateRanSlice(UUID sliceId){

        Integer ranId = getRanId(sliceId);
        String ranAdapterUrl =  "";
        ResponseEntity<String> httpResponse = this.performHTTPRequest(null, ranAdapterUrl, HttpMethod.DELETE);
        if (httpResponse.getStatusCode() != HttpStatus.CREATED){
            return httpResponse.getStatusCode();
        }
        String flexRanUrl = "";
        httpResponse = this.performHTTPRequest(null, ranAdapterUrl, HttpMethod.DELETE);
        if (httpResponse.getStatusCode() != HttpStatus.OK){
            return httpResponse.getStatusCode();
        }
        removeIdMapping(sliceId);
        return HttpStatus.OK;
    }
}
