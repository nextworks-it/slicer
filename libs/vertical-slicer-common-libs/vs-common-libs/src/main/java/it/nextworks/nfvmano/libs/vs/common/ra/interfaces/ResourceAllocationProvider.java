package it.nextworks.nfvmano.libs.vs.common.ra.interfaces;

import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;

public interface ResourceAllocationProvider {

    void computeResources(ResourceAllocationComputeRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException, IllegalAccessException, InstantiationException,
            ClassNotFoundException;

    void processResourceAllocationResponse(ResourceAllocationComputeResponse response);
}
