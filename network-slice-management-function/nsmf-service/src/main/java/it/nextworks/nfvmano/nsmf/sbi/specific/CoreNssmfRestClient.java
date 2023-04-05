package it.nextworks.nfvmano.nsmf.sbi.specific;

import it.nextworks.nfvmano.libs.ifa.templates.nst.EMBBPerfReq;
import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.UpfProvisioningPayload;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.ComputeNssResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.nsmf.sbi.NssmfRestClient;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalInstantiateNssiRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CoreNssmfRestClient extends NssmfRestClient {
    public CoreNssmfRestClient(String url ) {
        super(url);
    }

    @Override
    public void instantiateNetworkSubSlice(NssmfBaseProvisioningMessage request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, AlreadyExistingEntityException, NotExistingEntityException {
        if(request instanceof InternalInstantiateNssiRequest){
            InternalInstantiateNssiRequest internalRequest = (InternalInstantiateNssiRequest)request;
            ResourceAllocationComputeResponse raResponse = internalRequest.getResourceAllocationComputeResponse();
            UpfProvisioningPayload payload = new UpfProvisioningPayload();
            payload.setNssiId(request.getNssiId());
            payload.setE2eSliceId(internalRequest.getParentNsiId().toString());
            payload.setNetworkSliceType(internalRequest.getNsst().getType().toString());
            Map<String, Integer> networkSliceParameters = new HashMap<>();

            EMBBPerfReq  perfReq = internalRequest.getNsst().getSliceProfileList().get(0).geteMBBPerfReq().get(0);

            networkSliceParameters.put("min_DL_rate", new Integer(perfReq.getExpDataRateDL()-40));
            networkSliceParameters.put("max_DL_rate", new Integer(perfReq.getExpDataRateDL()+40));

            networkSliceParameters.put("min_UL_rate", new Integer(perfReq.getExpDataRateUL()-10));
            networkSliceParameters.put("max_UL_rate", new Integer(perfReq.getExpDataRateUL()+10));

            networkSliceParameters.put("max_latency", new Integer(internalRequest.getNsst().getSliceProfileList().get(0).getLatency()));
            networkSliceParameters.put("max-jitter", new Integer(internalRequest.getParentNst().getNstServiceProfileList().get(0).getJitter()));

            payload.setNetworkSliceParamters(networkSliceParameters);
            //TODO: add userplane functions from the resource allocation
            Optional<NssResourceAllocation> allocation = raResponse.getNsResourceAllocation().getNssResourceAllocations().stream()
                    .filter(nssA-> nssA.getNsstId().equals(internalRequest.getNsst().getNsstId()))
                    .findFirst();
            if(allocation.isPresent()){

                payload.setUserPlaneFunctions(((ComputeNssResourceAllocation)allocation.get()).getVnfPlacement());
                super.instantiateNetworkSubSlice(payload);
            }else throw new FailedOperationException("Could not find allocation for NSST:"+internalRequest.getNsst().getNsstId());

        }else throw  new MethodNotImplementedException("Instantiate network sub slice method not implemented for generic message");

    }
}
