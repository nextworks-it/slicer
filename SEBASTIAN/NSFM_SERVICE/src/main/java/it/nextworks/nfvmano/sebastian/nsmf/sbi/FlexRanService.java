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

    private int currentSliceId;

    public FlexRanService() {
        this.flxIdSliceId = new HashMap<>();
        this.availableRanId =  IntStream.range(1, 255)  //NOTE 1-255 taken from FlexRan API Doc
                .boxed()
                .collect(toList());
        sliceZeroToOnePercent=false;
        currentSliceId=0;
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


    //private boolean isImsiInEnodeB(JsonObject ranStats, String imsi, Integer enodeB){
    private String isImsiInEnodeB(JsonObject ranStats, String imsi, Integer enodeB){
        JsonArray eNbConfigArray=ranStats.get("eNB_config").getAsJsonArray();
        if(eNbConfigArray.size()==0){
            log.info("eNB_config has no element. The RAN might be down.");
            return null;
        }
        for(int i=0; i<eNbConfigArray.size(); i++) {
            JsonObject eNBconfig = eNbConfigArray.get(i).getAsJsonObject();
            if(eNBconfig.get("bs_id").getAsInt()==enodeB){
                log.info("bs_id "+enodeB);
                JsonObject ueJSON = eNBconfig.get("UE").getAsJsonObject();
                log.info("Get UE");
                if(!ueJSON.has("ueConfig")){
                    log.info("No ueConfig found into UE.");
                    return null;
                }
                JsonArray ueConfigArray=ueJSON.get("ueConfig").getAsJsonArray();
                log.info("Get ueConfig");
                for(int j=0; j<ueConfigArray.size(); j++){
                    log.info("Looking for IMSI");
                    if(ueConfigArray.get(j).getAsJsonObject().get("imsi").getAsString().equals(imsi)){
                        String rnti = ueConfigArray.get(j).getAsJsonObject().get("rnti").getAsString();
                        return rnti;
                        //return true;
                    }
                }
            }
        }
        return null;
        //return false;
    }


    public Map<String, Object> getHandoverRequestParams(String imsi){

        String url = String.format("http://%s/stats", flexRanURL);
        ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.GET);
        String rawResponse = httpResponse.getBody();
        JsonParser jsonParser = new JsonParser();
        //String rawResponse = "{\"date_time\":\"2020-06-26T17:06:25.130\",\"eNB_config\":[{\"bs_id\":10027,\"agent_info\":[{\"agent_id\":27,\"bs_id\":10027,\"capabilities\":[\"LOPHY\",\"HIPHY\",\"LOMAC\",\"HIMAC\",\"RLC\",\"PDCP\",\"SDAP\",\"RRC\"]}],\"eNB\":{\"header\":{\"version\":0,\"type\":8,\"xid\":0},\"cellConfig\":[{\"phyCellId\":1,\"puschHoppingOffset\":0,\"hoppingMode\":0,\"nSb\":1,\"phichResource\":0,\"phichDuration\":0,\"initNrPDCCHOFDMSym\":1,\"siConfig\":{\"sfn\":618,\"sib1Length\":17,\"siWindowLength\":5},\"dlBandwidth\":25,\"ulBandwidth\":25,\"ulCyclicPrefixLength\":0,\"dlCyclicPrefixLength\":0,\"antennaPortsCount\":1,\"duplexMode\":1,\"subframeAssignment\":0,\"specialSubframePatterns\":0,\"prachConfigIndex\":0,\"prachFreqOffset\":2,\"raResponseWindowSize\":7,\"macContentionResolutionTimer\":5,\"maxHARQMsg3Tx\":0,\"n1PUCCHAN\":0,\"deltaPUCCHShift\":1,\"nRBCqi\":0,\"srsSubframeConfig\":0,\"srsBwConfig\":0,\"srsMacUpPts\":0,\"enable64QAM\":0,\"carrierIndex\":0,\"dlFreq\":2685,\"ulFreq\":2565,\"eutraBand\":7,\"dlPdschPower\":-27,\"ulPuschPower\":-96,\"plmnId\":[{\"mcc\":208,\"mnc\":92,\"mncLength\":2}],\"sliceConfig\":{\"dl\":[{\"id\":0,\"label\":\"xMBB\",\"percentage\":100,\"isolation\":false,\"priority\":10,\"positionLow\":0,\"positionHigh\":25,\"maxmcs\":28,\"sorting\":[\"CR_ROUND\",\"CR_SRB12\",\"CR_HOL\",\"CR_LC\",\"CR_CQI\",\"CR_LCP\"],\"accounting\":\"POL_FAIR\",\"schedulerName\":\"schedule_ue_spec\"}],\"ul\":[{\"id\":0,\"label\":\"xMBB\",\"percentage\":100,\"isolation\":false,\"priority\":0,\"firstRb\":0,\"maxmcs\":20,\"accounting\":\"POLU_FAIR\",\"schedulerName\":\"schedule_ulsch_rnti\"}],\"intrasliceShareActive\":true,\"intersliceShareActive\":true},\"x2HoNetControl\":false}]},\"UE\":{\"ueConfig\":[{\"rnti\":52139,\"timeAlignmentTimer\":7,\"measGapConfigPattern\":4294967295,\"measGapConfigSfOffset\":4294967295,\"transmissionMode\":0,\"ueAggregatedMaxBitrateUL\":\"0\",\"ueAggregatedMaxBitrateDL\":\"0\",\"capabilities\":{\"halfDuplex\":0,\"intraSFHopping\":0,\"type2Sb1\":1,\"ueCategory\":4,\"resAllocType1\":1},\"ueTransmissionAntenna\":2,\"ttiBundling\":0,\"maxHARQTx\":4,\"betaOffsetACKIndex\":0,\"betaOffsetRIIndex\":0,\"betaOffsetCQIIndex\":8,\"ackNackSimultaneousTrans\":0,\"simultaneousAckNackCqi\":0,\"aperiodicCqiRepMode\":3,\"tddAckNackFeedback\":4294967295,\"ackNackRepetitionFactor\":0,\"extendedBsrSize\":4294967295,\"imsi\":\"0\",\"dlSliceId\":0,\"ulSliceId\":0,\"info\":{\"offsetFreqServing\":\"0\",\"offsetFreqNeighbouring\":\"0\",\"cellIndividualOffset\":[\"0\",\"0\"],\"filterCoefficientRsrp\":\"4\",\"filterCoefficientRsrq\":\"4\",\"event\":{\"a3\":{\"a3Offset\":\"0\",\"reportOnLeave\":1,\"hysteresis\":\"0\",\"timeToTrigger\":\"40\",\"maxReportCells\":\"2\"}}}}]},\"LC\":{\"header\":{\"version\":0,\"type\":12,\"xid\":0},\"lcUeConfig\":[{\"rnti\":18567,\"lcConfig\":[{\"lcid\":1,\"lcg\":0,\"direction\":2,\"qosBearerType\":0,\"qci\":1},{\"lcid\":2,\"lcg\":0,\"direction\":2,\"qosBearerType\":0,\"qci\":1},{\"lcid\":3,\"lcg\":1,\"direction\":1,\"qosBearerType\":0,\"qci\":1}]},{\"rnti\":50169,\"lcConfig\":[{\"lcid\":1,\"lcg\":0,\"direction\":2,\"qosBearerType\":0,\"qci\":1},{\"lcid\":2,\"lcg\":0,\"direction\":2,\"qosBearerType\":0,\"qci\":1},{\"lcid\":3,\"lcg\":1,\"direction\":1,\"qosBearerType\":0,\"qci\":1}]},{\"rnti\":14416,\"lcConfig\":[{\"lcid\":1,\"lcg\":0,\"direction\":2,\"qosBearerType\":0,\"qci\":1},{\"lcid\":2,\"lcg\":0,\"direction\":2,\"qosBearerType\":0,\"qci\":1},{\"lcid\":3,\"lcg\":1,\"direction\":1,\"qosBearerType\":0,\"qci\":1}]},{\"rnti\":52139,\"lcConfig\":[{\"lcid\":1,\"lcg\":0,\"direction\":2,\"qosBearerType\":0,\"qci\":1},{\"lcid\":2,\"lcg\":0,\"direction\":2,\"qosBearerType\":0,\"qci\":1},{\"lcid\":3,\"lcg\":1,\"direction\":1,\"qosBearerType\":0,\"qci\":1}]}]}},{\"bs_id\":10028,\"agent_info\":[{\"agent_id\":28,\"bs_id\":10028,\"capabilities\":[\"LOPHY\",\"HIPHY\",\"LOMAC\",\"HIMAC\",\"RLC\",\"PDCP\",\"SDAP\",\"RRC\"]}],\"eNB\":{\"header\":{\"version\":0,\"type\":8,\"xid\":0},\"cellConfig\":[{\"phyCellId\":0,\"puschHoppingOffset\":0,\"hoppingMode\":0,\"nSb\":1,\"phichResource\":0,\"phichDuration\":0,\"initNrPDCCHOFDMSym\":1,\"siConfig\":{\"sfn\":818,\"sib1Length\":17,\"siWindowLength\":5},\"dlBandwidth\":25,\"ulBandwidth\":25,\"ulCyclicPrefixLength\":0,\"dlCyclicPrefixLength\":0,\"antennaPortsCount\":1,\"duplexMode\":1,\"subframeAssignment\":0,\"specialSubframePatterns\":0,\"prachConfigIndex\":0,\"prachFreqOffset\":2,\"raResponseWindowSize\":7,\"macContentionResolutionTimer\":5,\"maxHARQMsg3Tx\":0,\"n1PUCCHAN\":0,\"deltaPUCCHShift\":1,\"nRBCqi\":0,\"srsSubframeConfig\":0,\"srsBwConfig\":0,\"srsMacUpPts\":0,\"enable64QAM\":0,\"carrierIndex\":0,\"dlFreq\":2685,\"ulFreq\":2565,\"eutraBand\":7,\"dlPdschPower\":-27,\"ulPuschPower\":-96,\"plmnId\":[{\"mcc\":208,\"mnc\":92,\"mncLength\":2}],\"sliceConfig\":{\"dl\":[{\"id\":0,\"label\":\"xMBB\",\"percentage\":1,\"isolation\":false,\"priority\":10,\"positionLow\":0,\"positionHigh\":25,\"maxmcs\":28,\"sorting\":[\"CR_ROUND\",\"CR_SRB12\",\"CR_HOL\",\"CR_LC\",\"CR_CQI\",\"CR_LCP\"],\"accounting\":\"POL_FAIR\",\"schedulerName\":\"schedule_ue_spec\"}],\"ul\":[{\"id\":0,\"label\":\"xMBB\",\"percentage\":1,\"isolation\":false,\"priority\":0,\"firstRb\":0,\"maxmcs\":20,\"accounting\":\"POLU_FAIR\",\"schedulerName\":\"schedule_ulsch_rnti\"}],\"intrasliceShareActive\":true,\"intersliceShareActive\":true},\"x2HoNetControl\":false}]},\"UE\":{},\"LC\":{}}],\"mac_stats\":[{\"bs_id\":10027,\"ue_mac_stats\":[{\"rnti\": 52139,\"mac_stats\":{\"rnti\":52139,\"bsr\":[0,0,0,0],\"phr\":40,\"rlcReport\":[{\"lcId\":1,\"txQueueSize\":0,\"txQueueHolDelay\":0,\"statusPduSize\":0},{\"lcId\":2,\"txQueueSize\":0,\"txQueueHolDelay\":0,\"statusPduSize\":0},{\"lcId\":3,\"txQueueSize\":0,\"txQueueHolDelay\":0,\"statusPduSize\":0}],\"pendingMacCes\":0,\"dlCqiReport\":{\"sfnSn\":6294,\"csiReport\":[{\"servCellIndex\":0,\"ri\":0,\"type\":\"FLCSIT_P10\",\"p10csi\":{\"wbCqi\":15}}]},\"ulCqiReport\":{\"sfnSn\":6294,\"cqiMeas\":[{\"type\":\"FLUCT_SRS\",\"servCellIndex\":0}],\"pucchDbm\":[{\"p0PucchDbm\":0,\"servCellIndex\":0}]},\"rrcMeasurements\":{\"measid\":5,\"pcellRsrp\":-65,\"pcellRsrq\":-3,\"neighMeas\":{\"eutraMeas\":[{\"physCellId\":0,\"measResult\":{\"rsrp\":-73,\"rsrq\":-8}},{\"physCellId\":341,\"measResult\":{\"rsrp\":-87,\"rsrq\":-19}}]}},\"pdcpStats\":{\"pktTx\":97,\"pktTxBytes\":15352,\"pktTxSn\":96,\"pktTxW\":0,\"pktTxBytesW\":0,\"pktTxAiat\":11099542,\"pktTxAiatW\":0,\"pktRx\":102,\"pktRxBytes\":13004,\"pktRxSn\":102,\"pktRxW\":0,\"pktRxBytesW\":0,\"pktRxAiat\":11099539,\"pktRxAiatW\":0,\"pktRxOo\":0,\"sfn\":\"12826771\"},\"macStats\":{\"tbsDl\":4,\"tbsUl\":63,\"prbRetxDl\":2,\"prbRetxUl\":0,\"prbDl\":2,\"prbUl\":0,\"mcs1Dl\":28,\"mcs2Dl\":0,\"mcs1Ul\":10,\"mcs2Ul\":10,\"totalBytesSdusUl\":4209695,\"totalBytesSdusDl\":271486,\"totalPrbDl\":255826,\"totalPrbUl\":1408237,\"totalPduDl\":127878,\"totalPduUl\":469374,\"totalTbsDl\":529342,\"totalTbsUl\":29580588,\"macSdusDl\":[{\"sduLength\":2,\"lcid\":1}],\"harqRound\":8},\"gtpStats\":[{\"eRabId\":5,\"teidEnb\":2063422212,\"teidSgw\":13}]},\"harq\":[\"ACK\",\"ACK\",\"ACK\",\"ACK\",\"ACK\",\"ACK\",\"ACK\",\"ACK\"]}]},{\"bs_id\":10028,\"ue_mac_stats\":[]}]}\n";
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
        String rntiToSet = null;
        for(Integer enodeBId: enodeBlsit){
            //log.info("Looking for IMSI "+imsi+" into enodeB "+enodeBId);
            String rnti = isImsiInEnodeB(jsonObject,"0",enodeBId);
            if(rnti!=null){
                enodeBToMigrateFrom=enodeBId;
                log.info("EnodeB with ID "+enodeBToMigrateTo+" has the IMSI you are looking for. The rnti is "+rnti );
                //log.info("EnodeB with ID "+enodeBToMigrateFrom+" has the IMSI "+imsi);
                rntiToSet=rnti;
            }
            if(rnti==null){
                enodeBToMigrateTo=enodeBId;
                log.info("EnodeB with ID "+enodeBToMigrateTo+" has NOT the IMSI you are looking for");
                //log.info("EnodeB with ID "+enodeBToMigrateTo+" has NOT the IMSI "+imsi);
            }
        }

        if(enodeBToMigrateFrom==null || enodeBToMigrateTo==null){
            log.info("Not able to find either the source or the destination of the IMSI");
            return null;
        }
        HashMap <String,Object> hoParameters = new HashMap<>();
        hoParameters.put("sid",enodeBToMigrateFrom);
        hoParameters.put("ueid",rntiToSet);
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



    public HttpStatus mapSliceStartingFromZero(UUID sliceId) {
        String url = String.format("http://%s/ranadapter/all/v1/set_slice_mapping", ranAdapterUrl);
        Map<String, String> slicePair = new HashMap<>();

        this.setIdsMap(sliceId, currentSliceId);
        slicePair.put("slicenetid", sliceId.toString());
        slicePair.put("sid", String.valueOf(currentSliceId));
        try{
            JSONObject payload = new JSONObject(slicePair);
            JSONArray payloadArray = new JSONArray();
            payloadArray.put(payload);
            ResponseEntity<String> httpResponse = this.performHTTPRequest(payloadArray.toString(), url, HttpMethod.POST);
            currentSliceId++;
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
        //slicePair.put("sid", "0"); //FOR DELL
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
        if(ranId==0){
            log.warn("Slice RAN with ID 0 cannot be removed.");
            return HttpStatus.OK;
        }

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
    public int getCurrentSliceId() {
        return currentSliceId;
    }
}
