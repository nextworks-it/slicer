package it.nextworks.nfvmano.sebastian.nsmf.sbi;

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
public class LlMecService extends CPSService{

    private Map<UUID, Integer> llMecSliceId;
    private List<Integer> availableLlMecId = new ArrayList<>();

    @Value("${llmec.url}")
    private String llMecUrl;

    @Value("${llmecAdapter.url}")
    private String llMecAdapterUrl;


    private static final Logger log = LoggerFactory.getLogger(LlMecService.class);

    public LlMecService(){
        this.llMecSliceId = new HashMap<>();
        this.availableLlMecId =  IntStream.range(1, 255)
                .boxed()
                .collect(toList());
    }

    public void setLlMecURL(String llMecUrl) {
        this.llMecUrl = llMecUrl;
    }

    public void setLlMecAdapterUrl(String llMecAdapterUrl) {
        this.llMecAdapterUrl = llMecAdapterUrl;
    }

    public boolean isLlMec(UUID sliceId) {
        return llMecSliceId.get(sliceId) != null;
    }

    private Integer getFirstAvailableLlMecId(){
        return this.availableLlMecId.remove(0);
    }

    public Map<UUID, Integer> getFlxIdSliceIds() {
        return llMecSliceId;
    }

    public Integer getRanId(UUID sliceId){
        Integer id = this.llMecSliceId.get(sliceId);
        return id;
    }
    public void removeIdMapping(UUID sliceId){
        this.llMecSliceId.remove(sliceId);
    }

    private void setIdsMap(UUID sliceId, Integer ranId){
        this.llMecSliceId.put(sliceId, ranId);
    }

    public HttpStatus createLlMecSlice(UUID sliceId){
        Integer llMecId  = getFirstAvailableLlMecId();
        String url = String.format("http://%s/slice/enb/-1/slice/%d", llMecUrl, llMecId);
        try{
            ResponseEntity<String> httpResponse = this.performHTTPRequest(null, url, HttpMethod.POST);
            this.setIdsMap(sliceId, llMecId);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }

    }

    public HttpStatus mapIdsRemotely(UUID sliceId){
        Integer ranId = getRanId(sliceId);
        String url = String.format("http://%s/ranadapter/all/v1/set_slice_mapping", llMecAdapterUrl);
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

    public HttpStatus applyLlMecSliceConfiguration(){ return null; }

    public HttpStatus applyInitialQosConstraints(UUID sliceId, List<JSONObject> qosConstraints){
        String url = String.format("http://%s/ranadapter/all/v1/%s/set_qos_on_ran", llMecAdapterUrl, sliceId.toString());
        JSONArray payloadArray = new JSONArray(qosConstraints);
        try{
            ResponseEntity<String> httpResponse = this.performHTTPRequest(payloadArray.toString(), url, HttpMethod.POST);
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }

    public HttpStatus terminateLlMecSlice(UUID sliceId){

        Integer ranId = getRanId(sliceId);

        String flexRanUrl = String.format("http://%s/slice/enb/-1/slice/%d", llMecUrl, ranId);
        try{

            ResponseEntity<String> httpResponse = this.performHTTPRequest(null, llMecUrl, HttpMethod.DELETE);
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
        String llMecAdapterUrl = String.format( "http://%s/llMecAdapter/all/v1/remove_slice_mapping/%s", this.llMecAdapterUrl, sliceId.toString());
        try{
            ResponseEntity<String> httpResponse = this.performHTTPRequest(request.toString(), llMecAdapterUrl, HttpMethod.DELETE);
            log.info("Slice"+ sliceId.toString()+ "removed from RanAdapter - HTTP Code: " + httpResponse.getStatusCode());
            return httpResponse.getStatusCode();
        }catch (RestClientResponseException e){
            log.info("Message received: " + e.getMessage());
            return HttpStatus.valueOf(e.getRawStatusCode());
        }
    }


}
