package it.nextworks.nfvmano.nsmf.sbi.specific;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceSubnetType;
import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.AssociateSubscriber;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ConfigurationActionType;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ScaleNetworkSlice;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.UpdateConfigurationRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.ran.RanSlicePayload;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.libs.vs.common.utils.BaseRestClient;
import it.nextworks.nfvmano.nsmf.sbi.NssmfRestClient;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalInstantiateNssiRequest;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalModifyNssiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


public class FlexPhyMacRanNssmfClient  extends NssmfRestClient {
    private String baseUrl;
    private final static Logger LOG= LoggerFactory.getLogger(FlexPhyMacRanNssmfClient.class);
    private RanSlicePayload originalRanSlicePayload;
    public FlexPhyMacRanNssmfClient(String url) {
        super(url);
        this.baseUrl = url;
    }
    @Override
    public void instantiateNetworkSubSlice(NssmfBaseProvisioningMessage request) throws FailedOperationException, AlreadyExistingEntityException, MethodNotImplementedException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException {
        if (request instanceof InternalInstantiateNssiRequest) {
            InternalInstantiateNssiRequest internalRequest = (InternalInstantiateNssiRequest) request;
            ResourceAllocationComputeResponse raResponse = internalRequest.getResourceAllocationComputeResponse();
            RanSlicePayload ranSlicePayload = new RanSlicePayload();

            ranSlicePayload.setNssiId(request.getNssiId());
            ranSlicePayload.setNst(internalRequest.getParentNst().getNsst());

            Optional<NssResourceAllocation> allocation = raResponse.getNsResourceAllocation().getNssResourceAllocations().stream()
                    .filter(nssA -> nssA.getNsstId().equals(internalRequest.getNsst().getNsstId()))
                    .findFirst();
            originalRanSlicePayload = ranSlicePayload;

            if (allocation.isPresent()) {

                super.instantiateNetworkSubSlice(ranSlicePayload);
            } else
                throw new FailedOperationException("Could not find allocation for NSST: " + internalRequest.getNsst().getNsstId());

        } else
            throw new MethodNotImplementedException("Instantiate network sub slice method not implemented for generic message");
    }

        @Override
        public void modifyNetworkSlice(NssmfBaseProvisioningMessage request) throws FailedOperationException {
            if (request instanceof InternalModifyNssiRequest) {
                UpdateConfigurationRequest updateConfigurationRequest = ((InternalModifyNssiRequest) request).getUpdateConfigurationRequest();
                ConfigurationActionType configurationActionType =  updateConfigurationRequest.getActionType();
                String nssiId = request.getNssiId().toString();
                switch(configurationActionType) {
                    case ASSOCIATE_SUBSCRIBER:
                        if(updateConfigurationRequest instanceof AssociateSubscriber){
                            String imsi = ((AssociateSubscriber) updateConfigurationRequest).getImsi();
                            BaseRestClient baseRestClient = new BaseRestClient();

                            String url =  baseUrl.split("/nssmf/")[0] + "/ran/subscribers-management/"+nssiId+"/"+imsi;
                            ResponseEntity<String> responseEntity = baseRestClient.performHTTPRequest(null, url, HttpMethod.POST);
                            if(responseEntity==null)
                                throw new FailedOperationException("Error during IMSI association with FlexPhy ");
                            HttpStatus httpStatus = responseEntity.getStatusCode();
                            String body = responseEntity.getBody();

                            LOG.info(httpStatus.toString());
                            LOG.info(body);
                            //TODO manage error case
                        }
                        break;

                    case SLICE_SCALING:
                        if(updateConfigurationRequest instanceof ScaleNetworkSlice) {
                            ScaleNetworkSlice scaleNetworkSlice = (ScaleNetworkSlice) updateConfigurationRequest;
                            int newDataRate = scaleNetworkSlice.getNewDataRate();

                            RanSlicePayload ranSlicePayload = new RanSlicePayload();
                            ranSlicePayload.setNssiId(originalRanSlicePayload.getNssiId());
                            ranSlicePayload.setNst(originalRanSlicePayload.getNsst());

                            boolean isUlScaling = scaleNetworkSlice.getScalingOption().equals("UL_SCALING");
                            boolean isDlScaling = scaleNetworkSlice.getScalingOption().equals("DL_SCALING");
                            NSST nsstRan=null;
                            for(NSST nsst: ranSlicePayload.getNsst().getNsstList()){
                                if(nsst.getType()== SliceSubnetType.RAN){
                                    nsstRan = nsst;

                                    if(isUlScaling)
                                        nsstRan.getSliceProfileList().get(0).geteMBBPerfReq().get(0).setExpDataRateUL(newDataRate);

                                    else if(isDlScaling)
                                        nsstRan.getSliceProfileList().get(0).geteMBBPerfReq().get(0).setExpDataRateDL(newDataRate);
                                    else {
                                        LOG.warn("Scaling must be either UL_SCALING or DL_SCALING");
                                        return;
                                    }
                                    break;
                                }
                            }
                            if(nsstRan==null){
                                LOG.error("NSST type RAN not found");
                                return;
                            }

                            ranSlicePayload.setNst(nsstRan);


                            BaseRestClient baseRestClient = new BaseRestClient();
                            String url =  baseUrl.split("/nssmf/")[0] + "/nssmf/ran/provisioning/nss/"+nssiId+"/action/modify";
                            LOG.info(url);

                            ResponseEntity<String> responseEntity = baseRestClient.performHTTPRequest(ranSlicePayload, url, HttpMethod.PUT);
                            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                            String json = null;
                            try {
                                json = ow.writeValueAsString(ranSlicePayload);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            LOG.info(json);

                            if(responseEntity==null)
                                throw new FailedOperationException("Error scaling RAN Slice");
                            HttpStatus httpStatus = responseEntity.getStatusCode();
                            String body = responseEntity.getBody();

                            LOG.info(httpStatus.toString());
                            LOG.info(body);
                            //TODO manage error case

                        }

                        break;

                    }



                }
            }
}
