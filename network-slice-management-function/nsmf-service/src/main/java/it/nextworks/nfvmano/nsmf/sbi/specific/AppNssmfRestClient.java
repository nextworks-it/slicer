package it.nextworks.nfvmano.nsmf.sbi.specific;

import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.vnfapps.VnfProvisioningPayload;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.ComputeNssResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.nsmf.sbi.NssmfRestClient;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalInstantiateNssiRequest;

import java.util.Optional;

public class AppNssmfRestClient extends NssmfRestClient {
    public AppNssmfRestClient(String url) {
        super(url);
    }

    @Override
    public void instantiateNetworkSubSlice(NssmfBaseProvisioningMessage request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, AlreadyExistingEntityException, NotExistingEntityException {
        if(request instanceof InternalInstantiateNssiRequest){
            InternalInstantiateNssiRequest internalRequest = (InternalInstantiateNssiRequest)request;
            ResourceAllocationComputeResponse raResponse = internalRequest.getResourceAllocationComputeResponse();
            Optional<NssResourceAllocation> allocation = raResponse.getNsResourceAllocation().getNssResourceAllocations().stream()
                    .filter(nssA-> nssA.getNsstId().equals(internalRequest.getNsst().getNsstId()))
                    .findFirst();
            if(allocation.isPresent()){

                VnfProvisioningPayload payload  = new VnfProvisioningPayload();
                payload.setNssiId(request.getNssiId());
                payload.setE2eSliceId(internalRequest.getParentNsiId().toString());
                payload.setVnfApps(((ComputeNssResourceAllocation)allocation.get()).getVnfPlacement());
                payload.setNsdId(internalRequest.getNsst().getNsdInfo().getNsdId());
                super.instantiateNetworkSubSlice(payload);
            }else throw new FailedOperationException("Could not find allocation for NSST:"+internalRequest.getNsst().getNsstId());
           
        }else throw  new MethodNotImplementedException("Instantiate network sub slice method not implemented for generic message");

    }
}
