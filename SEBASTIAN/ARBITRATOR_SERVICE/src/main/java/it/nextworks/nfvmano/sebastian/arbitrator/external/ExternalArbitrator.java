package it.nextworks.nfvmano.sebastian.arbitrator.external;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.arbitrator.AbstractArbitrator;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ExternalArbitrator extends AbstractArbitrator {

    private static final Logger log = LoggerFactory.getLogger(ExternalArbitrator.class);
    private ExternalArbitratorService externalArbitratorService;
    private ExternalArbitratorRestClient externalArbitratorRestClient;

    public ExternalArbitrator(ExternalArbitratorService externalArbitratorService, String arbitratorBaseUrl){
        this.externalArbitratorService= externalArbitratorService;
        externalArbitratorRestClient = new ExternalArbitratorRestClient(arbitratorBaseUrl);
    }

    @Override
    public List<ArbitratorResponse> computeArbitratorSolution(List<ArbitratorRequest> requests) throws FailedOperationException, NotExistingEntityException {
        log.debug("Received request to compute external arbitration");
        //this will be completed by the ExternalArbitratorService
        if(requests==null || requests.size()!=1)
            throw new FailedOperationException("Only one Arbitration Request is supported at the moment");
        CompletableFuture<ArbitratorResponse> arbitratorResponseFuture = new CompletableFuture();

        //Key request id, value operation id
        Map<String, String> operationIds=externalArbitratorRestClient.requestArbitration(requests);
        if(operationIds== null || operationIds.size()!=1){
            throw new FailedOperationException("Wrong return from the arbitrator. ONLY ONE CONCURRENT OPERATION SUPPORTED");
        }
        String operationId = operationIds.values().iterator().next();
        log.debug("External arbitration request id:"+operationId);
        externalArbitratorService.registerPendingResponse(operationId, arbitratorResponseFuture);
        log.debug("Waiting external arbitrator response");
        try {
            //The thread execution is locked until receiving the response on the ExternalArbitratorService.
            ArbitratorResponse response = arbitratorResponseFuture.get();
            log.debug("Received external arbitrator response");
            List<ArbitratorResponse> responseList = new ArrayList<>();
            responseList.add(response);
            return responseList;
        } catch (InterruptedException e) {
            log.error("Exception during external arbitration procedure", e);
            throw  new FailedOperationException(e);
        } catch (ExecutionException e) {
            log.error("Exception during external arbitration procedure", e);
            throw  new FailedOperationException(e);
        }

    }

    @Override
    public List<ArbitratorResponse> arbitrateVsScaling(List<ArbitratorRequest> requests) throws FailedOperationException, NotExistingEntityException, MethodNotImplementedException {
        throw new MethodNotImplementedException("VS scaling method not implemented for the External Arbitrator");
    }
}
