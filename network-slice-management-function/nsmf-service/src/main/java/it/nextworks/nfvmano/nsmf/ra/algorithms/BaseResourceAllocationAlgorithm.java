package it.nextworks.nfvmano.nsmf.ra.algorithms;

import it.nextworks.nfvmano.libs.vs.common.ra.elements.RAAlgorithmType;
import it.nextworks.nfvmano.libs.vs.common.ra.interfaces.ResourceAllocationProvider;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.nsmf.ra.ResourceAllocationComputeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseResourceAllocationAlgorithm implements ResourceAllocationProvider {

    private static final Logger log = LoggerFactory.getLogger(BaseResourceAllocationAlgorithm.class);
    private ResourceAllocationComputeService resourceAllocationComputeService;

    private RAAlgorithmType type;

    public BaseResourceAllocationAlgorithm(){}

    public BaseResourceAllocationAlgorithm(ResourceAllocationComputeService resourceAllocationComputeService, RAAlgorithmType type){

        this.resourceAllocationComputeService=resourceAllocationComputeService;
        this.type= type;
    }

    public void processResourceAllocationResponse(ResourceAllocationComputeResponse response){
        log.debug("Processing RA response, sending to service");
        resourceAllocationComputeService.processResourceAllocationResponse(response);
    }

    public ResourceAllocationComputeService getResourceAllocationComputeService() {
        return resourceAllocationComputeService;
    }

    public void setResourceAllocationComputeService(ResourceAllocationComputeService resourceAllocationComputeService) {
        this.resourceAllocationComputeService = resourceAllocationComputeService;
    }

    public RAAlgorithmType getType() {
        return type;
    }

    public void setType(RAAlgorithmType type) {
        this.type = type;
    }
}
