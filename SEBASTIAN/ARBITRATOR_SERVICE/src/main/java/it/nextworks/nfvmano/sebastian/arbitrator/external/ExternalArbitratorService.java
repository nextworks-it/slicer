package it.nextworks.nfvmano.sebastian.arbitrator.external;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class ExternalArbitratorService {

    private static final Logger log = LoggerFactory.getLogger(ExternalArbitratorService.class);
    private Map<String, CompletableFuture<ArbitratorResponse>>  pendingArbitratorResponses = new HashMap<>();

   public void registerPendingResponse(String requestId, CompletableFuture<ArbitratorResponse> response){
       log.debug("Received request to add a pending ArbitratorResponse with id:"+requestId);
       pendingArbitratorResponses.put(requestId, response);

   }

   public void processArbitratorRepsonse(String operationId, ArbitratorResponse response) throws NotExistingEntityException {
       log.debug("Received ArbitratorResponse with id:"+operationId);
       if(pendingArbitratorResponses.containsKey(operationId)){
          pendingArbitratorResponses.get(operationId).complete(response);
       }else{
           throw new NotExistingEntityException("Arbitration operation with id:"+operationId+" not registered");
       }

   }
}
