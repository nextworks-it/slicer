package it.nextworks.nfvmano.nsmf.sbi.specific;

import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceSubnetType;
import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ConfigurationActionType;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.SatelliteNetworkConfiguration;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.SliceTransferConfig;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.transport.SdnConfigPayload;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.*;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.nsmf.record.NsiRecordService;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceInstanceRecord;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceSubnetInstanceRecord;
import it.nextworks.nfvmano.nsmf.sbi.NssmfRestClient;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalInstantiateNssiRequest;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalModifyNssiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TransportNssmfRestClient extends NssmfRestClient {
    private NsiRecordService nsiRecordService;
    private static final Logger log = LoggerFactory.getLogger(TransportNssmfRestClient.class);
    public TransportNssmfRestClient(String url, NsiRecordService nsiRepo) {
        super(url);
        this.nsiRecordService = nsiRepo;
    }

    @Override
    public void instantiateNetworkSubSlice(NssmfBaseProvisioningMessage request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, AlreadyExistingEntityException, NotExistingEntityException {
        if(request instanceof InternalInstantiateNssiRequest){
            InternalInstantiateNssiRequest internalRequest = (InternalInstantiateNssiRequest)request;
            ResourceAllocationComputeResponse raResponse = internalRequest.getResourceAllocationComputeResponse();
            SdnConfigPayload sdnConfigPayload = new SdnConfigPayload();
            sdnConfigPayload.setNssiId(request.getNssiId());
            NetworkSliceInstanceRecord nsiRecord = nsiRecordService.getNetworkSliceInstanceRecord(internalRequest.getParentNsiId());
            log.debug("Retrieving CORE slice");
            for(NetworkSliceSubnetInstanceRecord nssiRecord : nsiRecord.getNetworkSliceSubnetInstanceIds()){
                log.debug("NSSI ID:"+nssiRecord.getNssiIdentifier()+" type:"+nssiRecord.getSliceSubnetType());
                if(nssiRecord.getSliceSubnetType().equals(SliceSubnetType.CORE)){
                    sdnConfigPayload.setTargetNssiId(nssiRecord.getNssiIdentifier());
                }
            }

            Optional<NssResourceAllocation> allocation = raResponse.getNsResourceAllocation().getNssResourceAllocations().stream()
                    .filter(nssA-> nssA.getNsstId().equals(internalRequest.getNsst().getNsstId()))
                    .findFirst();
            if(allocation.isPresent()){
                if(allocation.get().getAllocationType().equals(NssResourceAllocationType.TRANSPORT)){
                    TransportNssResourceAllocation tAllocation = (TransportNssResourceAllocation) allocation.get();
                    List<Map<String, String>> transportSpecifications = new ArrayList<>();
                    for(TransportFlowAllocation tsAllocation: tAllocation.getTransportAllocations()){
                        Map<String, String> curTAlloc = new HashMap<>();
                        String transportLink = null;
                        if(tsAllocation.getTransportFlowType().equals(TransportFlowType.TERRESTRIAL)){
                            transportLink= TransportFlowType.TERRESTRIAL.toString();

                        }else if(tsAllocation.getTransportFlowType().equals(TransportFlowType.SATELLITE)){
                            transportLink= TransportFlowType.SATELLITE.toString();

                        }
                        curTAlloc.put("transport-id", transportLink);
                        if(tsAllocation.isActive())
                            sdnConfigPayload.setTargetTransportId(transportLink);
                        if(tsAllocation.getDefaultGw()!=null)
                            curTAlloc.put("gateway-id", tsAllocation.getDefaultGw());
                        transportSpecifications.add(curTAlloc);
                    }

                    sdnConfigPayload.setTransportSpecifications(transportSpecifications);
                    super.instantiateNetworkSubSlice(sdnConfigPayload);
                }else throw new FailedOperationException("NSS Resource Allocation type not supported");

            }else throw new FailedOperationException("Could not find allocation for NSST:"+internalRequest.getNsst().getNsstId());

        }else throw  new MethodNotImplementedException("Instantiate network sub slice method not implemented for generic message");

    }


    @Override
    public void modifyNetworkSlice(NssmfBaseProvisioningMessage request) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        if(request instanceof InternalModifyNssiRequest){
            InternalModifyNssiRequest internalRequest = (InternalModifyNssiRequest)request;

            SdnConfigPayload sdnConfigPayload = new SdnConfigPayload();
            sdnConfigPayload.setNssiId(request.getNssiId());
            NetworkSliceInstanceRecord nsiRecord = nsiRecordService.getNetworkSliceInstanceRecord(internalRequest.getParentNsiId());
            sdnConfigPayload.setOperationId(internalRequest.getOperationId());
            sdnConfigPayload.setConfigurationActionType(internalRequest.getUpdateConfigurationRequest().getActionType());
            if(internalRequest.getUpdateConfigurationRequest().getActionType()== ConfigurationActionType.SATELLITE_NETWORK_CONFIGURATION){
                SatelliteNetworkConfiguration reconf = (SatelliteNetworkConfiguration) internalRequest.getUpdateConfigurationRequest();
                sdnConfigPayload.setTransportConfig(reconf.getTransportConfig());
            }else if (internalRequest.getUpdateConfigurationRequest().getActionType()== ConfigurationActionType.SLICE_TRANSFER){
                Map<String, String> flowTransfers = new HashMap<>();
                SliceTransferConfig reconf = (SliceTransferConfig) internalRequest.getUpdateConfigurationRequest();
                flowTransfers.put(reconf.getOrigin(), reconf.getTarget());
                sdnConfigPayload.setSliceFlowTransfers(flowTransfers);

            }

            super.modifyNetworkSlice(sdnConfigPayload);


        }else throw  new MethodNotImplementedException("Instantiate network sub slice method not implemented for generic message");

    }
}
