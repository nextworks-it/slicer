package it.nextworks.nfvmano.nsmf.ra.algorithms.stat;

import it.nextworks.nfvmano.libs.vs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.query.elements.Filter;
import it.nextworks.nfvmano.libs.vs.common.query.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.messages.OnboardStaticRaResponseRequest;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.record.StaticRaResponseRepository;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.record.elements.StaticRaResponseRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class StaticAlgorithmResponseManager {

    private static final Logger log = LoggerFactory.getLogger(StaticAlgorithmResponseManager.class);

    @Autowired
    private StaticRaResponseRepository raResponseRepository;

    public String createStaticNsiRaResponse(OnboardStaticRaResponseRequest request) throws AlreadyExistingEntityException {
        log.debug("Received request to create a new static Nsi Ra response");

        if(raResponseRepository.findBySliceType(request.getStaticNsiResponse().getSliceType()).isPresent())
            throw new AlreadyExistingEntityException("A static response for slice type "+request.getStaticNsiResponse().getSliceType()+" is already present");

        StaticRaResponseRecord staticNsiRaResponse=request.getStaticNsiResponse();
        String responseId= UUID.randomUUID().toString();

        staticNsiRaResponse.setResponseId(responseId);
        raResponseRepository.saveAndFlush(staticNsiRaResponse);

        return responseId;
    }

    public List<StaticRaResponseRecord> queryStaticNsiResponse(GeneralizedQueryRequest request) throws NotExistingEntityException, MalformattedElementException {
        log.debug("Processing query for StaticNsiRaResponse");
        request.isValid();

        List<StaticRaResponseRecord> staticResponses=new ArrayList<>();
        Filter filter=request.getFilter();
        Map<String,String> fParams=filter.getParameters();
        if(fParams.isEmpty()){
            log.debug("Query all static responses");
            staticResponses=raResponseRepository.findAll();
        } else if((fParams.size()==1)&& fParams.containsKey("RESPONSE_ID")){
            String responseId= fParams.get("RESPONSE_ID");
            Optional<StaticRaResponseRecord> record=raResponseRepository.findByResponseId(UUID.fromString(responseId));
            if(!record.isPresent())
                throw new NotExistingEntityException("StaticNsiRaResponse with ID "+responseId+" not found");
            staticResponses.add(record.get());
        } else {
            log.error("Query filter not supported");
            throw new MalformattedElementException("Query filter not supported");
        }
        return staticResponses;
    }

    public void deleteStaticNsiResponse(String responseId)throws NotExistingEntityException{
        log.debug("Receive request to delete StaticNsiRaResponse with ID "+responseId);

        Optional<StaticRaResponseRecord> record=raResponseRepository.findByResponseId(UUID.fromString(responseId));
        if(!record.isPresent())
            throw new NotExistingEntityException("StaticNsiRaResponse with ID "+responseId+" not found");

        StaticRaResponseRecord response=record.get();

        raResponseRepository.delete(response);

        log.debug("StaticNsiRaResponse with ID "+responseId+" successfully deleted");
    }
}
