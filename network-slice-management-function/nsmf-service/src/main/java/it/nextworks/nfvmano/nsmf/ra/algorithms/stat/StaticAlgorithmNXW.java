package it.nextworks.nfvmano.nsmf.ra.algorithms.stat;

import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NsResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.RAAlgorithmType;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.nsmf.ra.ResourceAllocationComputeService;
import it.nextworks.nfvmano.nsmf.ra.algorithms.BaseResourceAllocationAlgorithm;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.record.StaticRaResponseRepository;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.record.elements.StaticRaResponseRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StaticAlgorithmNXW extends BaseResourceAllocationAlgorithm {

    private static final Logger log = LoggerFactory.getLogger(StaticAlgorithmNXW.class);

    private StaticRaResponseRepository repository;

    public StaticAlgorithmNXW(ResourceAllocationComputeService service, StaticRaResponseRepository staticRaResponseRepository){
        super(service, RAAlgorithmType.STATIC);
        this.repository=staticRaResponseRepository;
    }

    @Override
    public void computeResources(ResourceAllocationComputeRequest request) throws FailedOperationException {
        log.debug("Retrieving static response");

        //StaticRaResponseRecord sResponse = repository.findAll().get(0);

        ResourceAllocationComputeResponse response=new ResourceAllocationComputeResponse(request.getRequestId(), new NsResourceAllocation(null, request.getNsiId(), null), true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    processResourceAllocationResponse(response);
                } catch (InterruptedException e) {
                    log.error("Error",e);
                }
            }
        });
        thread.start();
    }
}
