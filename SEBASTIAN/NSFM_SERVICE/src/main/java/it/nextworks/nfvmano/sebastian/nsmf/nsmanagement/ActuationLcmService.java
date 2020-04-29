package it.nextworks.nfvmano.sebastian.nsmf.nsmanagement;

import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.sebastian.common.ActuationRequest;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.NsmfSbRestClient;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.QoSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ActuationLcmService {

    @Autowired
    QoSService qoSService;

    private static final Logger log = LoggerFactory.getLogger(ActuationLcmService.class);

    private final String REDIRECT="REDIRECT";
    private final String UPDATE_QOS="UPDATE_QOS";
    private final String HANDOVER = "HANDOVER";


    public ActuationLcmService(){}

    private String getActuationType(ActuationRequest request) throws MalformattedElementException {

        Map<String, Object> parametersActuation = request.getParameters();
        if(parametersActuation.containsKey("routes") && parametersActuation.containsKey("ueIMSI"))
            return REDIRECT;

        if(parametersActuation.containsKey("ran_core_constraints") )
            return UPDATE_QOS;

        if(parametersActuation.containsKey("ueid") && parametersActuation.containsKey("sid") && parametersActuation.containsKey("tid"))
            return HANDOVER;

        throw new MalformattedElementException("Error: check the request parameters.");
    }


    public boolean processActuation(ActuationRequest request){

        try {
            log.info("Start to process actuation.");
            final String actuationType = getActuationType(request);
            final Map<String,Object> parameters = request.getParameters();
            JSONObject json;
            HttpStatus httpStatus;
            String url = "http://10.8.202.11:8080"; // CPSR base URL.
            String nsiId = request.getNsiId();
            switch(actuationType)
            {
                case REDIRECT:
                    log.info("The actuation is a REDIRECT request.");
                    json = new JSONObject(parameters);
                    qoSService.setTargetUrl(url);
                    httpStatus =qoSService.redirectTraffic(UUID.fromString(nsiId),
                            "CORE", (String)parameters.get("ueIMSI"), json);
                    return httpStatus==HttpStatus.OK;


                case UPDATE_QOS:
                    log.info("The actuation is a UPDATE QOS request.");
                    qoSService.setTargetUrl(url);
                    json = new JSONObject(parameters);
                    httpStatus =qoSService.setQoS(UUID.fromString(nsiId), json);
                    return httpStatus==HttpStatus.OK;


                case HANDOVER:
                    log.info("The actuation is a handover request.");
                    qoSService.setTargetUrl(url);
                    Map<String,Object> jsonFather = new HashMap<String, Object>();
                    jsonFather.put("ueHandover",parameters);
                    json = new JSONObject(jsonFather);
                    httpStatus =qoSService.handover(UUID.fromString(nsiId), json);
                    return httpStatus==HttpStatus.OK;
                default:
                    log.info("The actuation request is neither REDIRECT nor update QOS.");
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
