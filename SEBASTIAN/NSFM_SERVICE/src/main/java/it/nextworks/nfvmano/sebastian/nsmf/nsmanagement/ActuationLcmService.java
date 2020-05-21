package it.nextworks.nfvmano.sebastian.nsmf.nsmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.sebastian.common.ActuationRequest;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.QoSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActuationLcmService {

    @Autowired
    QoSService qoSService;

    private static final Logger log = LoggerFactory.getLogger(ActuationLcmService.class);

    private final String REDIRECT="REDIRECT";
    private final String UPDATE_QOS="UPDATE_QOS";
    private final String UPDATE_PRIORITY ="UPDATE_PRIORITY";
    private final String HANDOVER = "HANDOVER";
    private final String RAN_CORE_CONSTRAINT = "RAN_CORE_CONSTRAINT";

    @Value("${cpsrBaseUrl}")
    private String cpsrBaseUrl;

    public ActuationLcmService(){

    }

    private String getActuationType(ActuationRequest request) throws MalformattedElementException {

        Map<String, Object> parametersActuation = request.getParameters();
        if(parametersActuation.containsKey("routes") && parametersActuation.containsKey("ueIMSI"))
            return REDIRECT;

        if(parametersActuation.containsKey("ran_core_constraints") )
            return UPDATE_QOS;

        if(parametersActuation.containsKey("ran_slice_priority") )
            return UPDATE_PRIORITY;

        if(parametersActuation.containsKey("ueid") && parametersActuation.containsKey("sid") && parametersActuation.containsKey("tid"))
            return HANDOVER;

        if(parametersActuation.containsKey("bandIncDir") && parametersActuation.containsKey("bandIncVal") && parametersActuation.containsKey("bandUnitScale"))
            return RAN_CORE_CONSTRAINT;

        throw new MalformattedElementException("Error: check the request parameters.");
    }


    public boolean processActuation(ActuationRequest request){

        try {
            log.info("Start to process actuation. Hostname to forward the request to is: "+cpsrBaseUrl);
            final String actuationType = getActuationType(request);
            final Map<String,Object> parameters = request.getParameters();
            JSONObject json;
            HttpStatus httpStatus;
            //String url = "http://10.8.202.11:8080"; // CPSR base URL.
            String url = cpsrBaseUrl;
            String nsiId = request.getNsiId();
            switch(actuationType)
            {
                case REDIRECT:
                    log.info("The actuation is a REDIRECT actuation request.");
                    json = new JSONObject(parameters);
                    qoSService.setTargetUrl(url);
                    httpStatus =qoSService.redirectTraffic(UUID.fromString(nsiId),
                            "CORE", (String)parameters.get("ueIMSI"), json);
                    return httpStatus==HttpStatus.OK;


                case UPDATE_QOS:
                    log.info("The actuation is a UPDATE QOS actuation request.");
                    qoSService.setTargetUrl(url);
                    json = new JSONObject(parameters);
                    httpStatus =qoSService.setQoS(UUID.fromString(nsiId), json);
                    return httpStatus==HttpStatus.OK;


                case UPDATE_PRIORITY:
                    log.info("The actuation is a UPDATE priority actuation request.");
                    qoSService.setTargetUrl(url);
                    json = new JSONObject(parameters);
                    httpStatus =qoSService.setPriority(UUID.fromString(nsiId), json);
                    return httpStatus==HttpStatus.OK;


                case HANDOVER:
                    log.info("The actuation is a HANDOVER actuation request.");
                    qoSService.setTargetUrl(url);
                    Map<String,Object> jsonFather = new HashMap<String, Object>();
                    jsonFather.put("ue_handover",parameters);
                    json = new JSONObject(jsonFather);
                    httpStatus =qoSService.handover(UUID.fromString(nsiId), json);
                    return httpStatus==HttpStatus.CREATED;

                case RAN_CORE_CONSTRAINT:
                    log.info("Received RAN CORE CONSTRAINT actuation request");
                    qoSService.setTargetUrl(url);
                    List<Map<String,Object>> paramsList = new ArrayList<>();
                    paramsList.add(parameters);
                    Map<String,Object> jsonFatherRan = new HashMap<String, Object>();
                    jsonFatherRan.put("ran_core_constraints",paramsList);
                    json = new JSONObject(jsonFatherRan);
                    httpStatus =qoSService.setQoS(UUID.fromString(nsiId), json);
                    return httpStatus==HttpStatus.OK;

                default:
                    log.info("The actuation request has not the expected parameters.");
                    return false;
            }

        } catch (MalformattedElementException e) {
            log.error(e.getMessage());
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

    }
}
