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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.nextworks.nfvmano.sebastian.record.elements.ImsiInfo;
import org.json.JSONArray;
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

    @Value("${flexran.enodeB}")
    private String enodeB;

    private boolean sliceZeroToOnePercent;

    public FlexRanService() {
        this.flxIdSliceId = new HashMap<>();
        this.availableRanId =  IntStream.range(1, 255)  //NOTE 1-255 taken from FlexRan API Doc
                .boxed()
                .collect(toList());
        sliceZeroToOnePercent=false;
    }


    private Map<String, Object> buildJsonSliceOnePercentCreation(Integer sliceId){
        Map<String, Object> onePercent = new HashMap<>();
        onePercent.put("id", sliceId);
        onePercent.put("percentage", "1");
        Map<String, Object> json = new HashMap<>();
        json.put("dl",onePercent);
        json.put("ul",onePercent);
        return json;
    }

    public HttpStatus setSliceZeroOnePercent(){
        log.debug("enodeB ID is "+getEnodeB());

        if(isSliceZeroToOnePercent()==true){
            log.warn("RAN Slice with ID 0 is already to one percent.");
            return HttpStatus.OK;
        }

        Map<String, Object> json = buildJsonSliceOnePercentCreation(0);
        String url = String.format("http://%s/slice/enb/%s", flexRanURL,getEnodeB());
        try{
            ResponseEntity<String> httpResponse = this.performHTTPRequest(json, url, HttpMethod.POST);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    public void setFlexRanURL(String flexRanURL) {
        this.flexRanURL = flexRanURL;
    }

    public void setRanAdapterUrl(String ranAdapterUrl) {
        this.ranAdapterUrl = ranAdapterUrl;
    }

    public boolean isRan(UUID sliceId) {
        return flxIdSliceId.get(sliceId) != null;
    }

    private Integer getFirstAvailableRanId(){
        return this.availableRanId.remove(0);
    }

    public Map<UUID, Integer> getFlxIdSliceIds() {
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
        String url = String.format("http://%s/slice/enb/%s/slice/%d", flexRanURL, getEnodeB(), ranId);
        try{
            ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.POST);
            this.setIdsMap(sliceId, ranId);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }


    private boolean isImsiInEnodeB(JsonObject ranStats, String imsi, Integer enodeB){
        JsonArray eNbConfigArray=ranStats.get("eNB_config").getAsJsonArray();
        if(eNbConfigArray.size()==0){
            log.info("eNB_config has no element. The RAN might be down.");
            return false;
        }
        for(int i=0; i<eNbConfigArray.size(); i++) {
            JsonObject eNBconfig = eNbConfigArray.get(i).getAsJsonObject();
            if(eNBconfig.get("bs_id").getAsInt()==enodeB){
                log.info("bs_id "+enodeB);
                JsonObject ueJSON = eNBconfig.get("UE").getAsJsonObject();
                log.info("Get UE");
                if(!ueJSON.has("ueConfig")){
                    log.info("No ueConfig found into UE.");
                    return false;
                }
                JsonArray ueConfigArray=ueJSON.get("ueConfig").getAsJsonArray();
                log.info("Get ueConfig");
                for(int j=0; j<ueConfigArray.size(); j++){
                    log.info("Looking for IMSI");
                    if(ueConfigArray.get(j).getAsJsonObject().get("imsi").getAsString().equals(imsi))
                        return true;
                }
            }
        }
        return false;
    }


    public Map<String, Object> getHandoverRequestParams(String imsi){

        String url = String.format("http://%s/stats", flexRanURL);
        ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.GET);
        String rawResponse = httpResponse.getBody();
        JsonParser jsonParser = new JsonParser();
        log.debug("RAN stats raw output: "+rawResponse);
        JsonObject jsonObject= jsonParser.parse(rawResponse).getAsJsonObject();
        JsonArray eNbConfigArray=jsonObject.get("eNB_config").getAsJsonArray();
        if(eNbConfigArray.size()==0){
            log.info("eNB_config has no element. The RAN might be down.");
        }

        ArrayList<Integer> enodeBlsit = new ArrayList<Integer>();
        log.info("There are "+eNbConfigArray.size()+" enodeB");
        for(int i=0; i<eNbConfigArray.size(); i++) {
            Integer enodeBId = eNbConfigArray.get(i).getAsJsonObject().get("bs_id").getAsInt();
            enodeBlsit.add(enodeBId);
            log.info("EnodeB found with ID "+enodeBId);
        }


        Integer enodeBToMigrateFrom=null;
        Integer enodeBToMigrateTo=null;
        for(Integer enodeBId: enodeBlsit){
            log.info("Looking for IMSI "+imsi+" into enodeB "+enodeBId);
            if(isImsiInEnodeB(jsonObject,imsi,enodeBId)){
                enodeBToMigrateFrom=enodeBId;
                log.info("EnodeB with ID "+enodeBToMigrateFrom+" has the IMSI "+imsi);
            }
            if(!isImsiInEnodeB(jsonObject,imsi,enodeBId)){
                enodeBToMigrateTo=enodeBId;
                log.info("EnodeB with ID "+enodeBToMigrateTo+" has NOT the IMSI "+imsi);
            }
        }

        if(enodeBToMigrateFrom==null || enodeBToMigrateTo==null){
            log.info("Not able to find either the source or the destination of the IMSI");
            return null;
        }
        HashMap <String,Object> hoParameters = new HashMap<>();
        hoParameters.put("sid",enodeBToMigrateFrom);
        hoParameters.put("ueid",Integer.valueOf(imsi));
        hoParameters.put("tid",enodeBToMigrateTo);
        return hoParameters;
    }

    public int[] getRanSlicePercentageUsage(String ranSliceID){
        String url = String.format("http://%s/stats", flexRanURL);
        ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.GET);
        JsonParser jsonParser = new JsonParser();
        String rawResponse = httpResponse.getBody();
        log.debug("RAN stats raw output: "+rawResponse);
        JsonObject jsonObject= jsonParser.parse(rawResponse).getAsJsonObject();
        /*
        * the RAN stats is a JSON composed as follow:
        *
        * it is composed by zero to more eNB_Config.
        * Each eNB_Config has the eNB field.
        * The eNB field contains zero to more cellConfig.
        * Each cellConfig contains the sliceConfig.
        * The sliceConfig contains two arrays: dl (download info about the slices) and ul (ul info about the slices).
        * dl and ul contains both the ranSliceID this function is looking for and the related usage in upload and download in percentage.
        * */

        int ulPercentage = -1;
        int dlPercentage = -1;
        JsonArray eNbConfigArray=jsonObject.get("eNB_config").getAsJsonArray();
        if(eNbConfigArray.size()==0){
            log.info("eNB_config has no element. The RAN might be down.");
        }
        for(int i=0; i<eNbConfigArray.size(); i++) {
            JsonElement jsonEnb= eNbConfigArray.get(i).getAsJsonObject().get("eNB");
            JsonArray cellConfigJsonArray=jsonEnb.getAsJsonObject().get("cellConfig").getAsJsonArray();
            for(int j=0; j<cellConfigJsonArray.size(); j++){
                JsonObject sliceConfig=cellConfigJsonArray.get(j).getAsJsonObject().get("sliceConfig").getAsJsonObject();
                JsonArray jsonArrayUl= sliceConfig.get("ul").getAsJsonArray();
                JsonArray jsonArrayDl= sliceConfig.get("dl").getAsJsonArray();
                int ulSize = jsonArrayUl.size();
                int dlSize = jsonArrayDl.size();
                if(ulSize!=dlSize)
                    log.warn("The number of slice in Upload ("+ulSize+") is different from the number of slice in download ("+dlSize+")");

                for(int k=0; k<jsonArrayUl.size(); k++){
                    if(jsonArrayUl.get(k).getAsJsonObject().get("id").getAsInt()==Integer.valueOf(ranSliceID)){
                        ulPercentage=jsonArrayUl.get(k).getAsJsonObject().get("percentage").getAsInt();
                    }
                }
                for(int k=0; k<jsonArrayDl.size(); k++){
                    if(jsonArrayDl.get(k).getAsJsonObject().get("id").getAsInt()==Integer.valueOf(ranSliceID)){
                        dlPercentage=jsonArrayDl.get(k).getAsJsonObject().get("percentage").getAsInt();
                    }
                }

            }

        }
        if(ulPercentage==-1 && dlPercentage==-1){
            log.warn("Not able to find upload and download percentages for ran Slice with ID "+ranSliceID);
        }
        return new int []{ulPercentage,dlPercentage};
    }


    public HttpStatus createSliceRanOnePercent(UUID sliceId){
        String url = String.format("http://%s/slice/enb/%s", flexRanURL,getEnodeB());
        Integer ranSliceId  = getFirstAvailableRanId();
        Map<String, Object> json = buildJsonSliceOnePercentCreation(ranSliceId);
        try{
            ResponseEntity<String> httpResponse = this.performHTTPRequest(json, url, HttpMethod.POST);
            this.setIdsMap(sliceId, ranSliceId);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }



    public HttpStatus mapImsiList(List<ImsiInfo> imsiInfoList, UUID networkSliceUuid){
        Integer sliceId = flxIdSliceId.get(networkSliceUuid);
        String url = String.format("http://%s/associate_ue_vnetwork/%s", flexRanURL, sliceId);
        JSONArray payloadArray = new JSONArray();
        try {
            for (ImsiInfo imsiInfo : imsiInfoList) {
                for(String imsi: imsiInfo.getImsis())
                    payloadArray.put(Long.valueOf(imsi));
            }
            log.debug("IMSI payload: "+payloadArray.toString());
            ResponseEntity<String> httpResponse = this.performHTTPRequest(payloadArray.toString(), url, HttpMethod.POST);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    /*E.g.: {slicenetid: "f5b01594-520e-11e9-8647-d663bd873d93",
             sid: "1" }*/
    public HttpStatus mapIdsRemotely(UUID sliceId){
        Integer ranId = getRanId(sliceId);
        String url = String.format("http://%s/ranadapter/all/v1/set_slice_mapping", ranAdapterUrl);
        Map<String, String> slicePair = new HashMap<>();
        slicePair.put("slicenetid", sliceId.toString());
        slicePair.put("sid", ranId.toString());
        try{
            JSONObject payload = new JSONObject(slicePair);
            JSONArray payloadArray = new JSONArray();
            payloadArray.put(payload);
            ResponseEntity<String> httpResponse = this.performHTTPRequest(payloadArray.toString(), url, HttpMethod.POST);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    public HttpStatus applyRanSliceConfiguration(){ return null; }

    public HttpStatus applyInitialQosConstraints(UUID sliceId, List<JSONObject> qosConstraints){
        String url = String.format("http://%s/ranadapter/all/v1/%s/set_qos_on_ran", ranAdapterUrl, sliceId.toString());
        JSONArray payloadArray = new JSONArray(qosConstraints);
        try{
            ResponseEntity<String> httpResponse = this.performHTTPRequest(payloadArray.toString(), url, HttpMethod.POST);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    public HttpStatus terminateRanSlice(UUID sliceId){

        Integer ranId = getRanId(sliceId);

        String flexRanUrl = String.format("http://%s/slice/enb/-1/slice/%d", flexRanURL, ranId);
        try{

            ResponseEntity<String> httpResponse = this.performHTTPRequest(null, flexRanUrl, HttpMethod.DELETE);
            removeIdMapping(sliceId);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    public HttpStatus removeRemoteMapping(UUID sliceId){
        Map<String, String> slicePair = new HashMap<>();
        slicePair.put("slicenetid", sliceId.toString());
        JSONObject request = new JSONObject(slicePair);
        String ranAdapterUrl = String.format( "http://%s/ranadapter/all/v1/remove_slice_mapping/%s", this.ranAdapterUrl, sliceId.toString());
        try{
            ResponseEntity<String> httpResponse = this.performHTTPRequest(request.toString(), ranAdapterUrl, HttpMethod.DELETE);
            log.info("Slice"+ sliceId.toString()+ "removed from RanAdapter - HTTP Code: " + httpResponse.getStatusCode());
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    public boolean isSliceZeroToOnePercent() {
        return sliceZeroToOnePercent;
    }

    public void setSliceZeroToOnePercent(boolean sliceZeroToOnePercent) {
        this.sliceZeroToOnePercent = sliceZeroToOnePercent;
    }

    public String getEnodeB(){
        if(enodeB==null || enodeB.isEmpty())
            return "-1";
        else
            return this.enodeB;
    }

}
