package it.nextworks.nfvmano.nsmf.ra.algorithms.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NsResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.RAAlgorithmType;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.nsmf.ra.ResourceAllocationComputeService;
import it.nextworks.nfvmano.nsmf.ra.algorithms.BaseResourceAllocationAlgorithm;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.StaticAlgorithmNXW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

public class FileResourceAllocationAlgorithm extends BaseResourceAllocationAlgorithm {
    private static final RAAlgorithmType type =RAAlgorithmType.FILE ;

    private String defaultRaFilePath;

    private static final Logger log = LoggerFactory.getLogger(FileResourceAllocationAlgorithm.class);
    public FileResourceAllocationAlgorithm(ResourceAllocationComputeService resourceAllocationComputeService, String defaultRaFilePath) {
        super(resourceAllocationComputeService, type);
        this.defaultRaFilePath=defaultRaFilePath;
    }

    @Override
    public void computeResources(ResourceAllocationComputeRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.debug("Received ResourceAllocationCompute request");
        try {
            ObjectMapper mapper = new ObjectMapper();
            ResourceAllocationComputeResponse defaultResponse;

            if(!defaultRaFilePath.startsWith("/")) {
                String resourcePath = defaultRaFilePath;
                Resource resourceSpec = new ClassPathResource(resourcePath);
                InputStream resource = resourceSpec.getInputStream();
                defaultResponse= mapper.readValue(resource, ResourceAllocationComputeResponse.class);
            } else {

                //TODO: ADD CONTROL
                log.debug("Find a valid path");
                defaultResponse=mapper.readValue(new File(defaultRaFilePath), ResourceAllocationComputeResponse.class);
            }

            NsResourceAllocation nsResourceAllocation = new NsResourceAllocation(UUID.randomUUID().toString(),request.getNsiId(), defaultResponse.getNsResourceAllocation().getNssResourceAllocations() );
            ResourceAllocationComputeResponse response = new ResourceAllocationComputeResponse(request.getRequestId(), nsResourceAllocation, true);

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
        } catch (IOException e) {
            log.error("Error reading default RA response",e);
            throw new FailedOperationException("Could not read default Resource allocation response file (DefaultResourceAllocation.json)");
        }
    }
}
