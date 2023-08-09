package it.nextworks.nfvmano.nsmf.sbi.specific;

import it.nextworks.nfvmano.libs.ifa.templates.nst.NST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NsdInfo;
import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.*;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.CoreSlicePayload;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.libs.vs.common.utils.BaseRestClient;
import it.nextworks.nfvmano.nsmf.sbi.NssmfRestClient;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalInstantiateNssiRequest;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalModifyNssiRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public class CmcCoreNssmfRestClient extends NssmfRestClient {
    private String baseUrl;
    private String upfName;

    private UUID newNssiScaling;
    private final static Logger LOG= LoggerFactory.getLogger(CmcCoreNssmfRestClient.class);

    private CoreSlicePayload originalInstantiationRequestPayload;
    public CmcCoreNssmfRestClient(String url) {
        super(url);
        this.baseUrl = url;
    }

    @Override
    public void instantiateNetworkSubSlice(NssmfBaseProvisioningMessage request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, AlreadyExistingEntityException, NotExistingEntityException {
        LOG.info("Requesting instantiation of Core network slice ");
        if(request instanceof InternalInstantiateNssiRequest){
            InternalInstantiateNssiRequest internalRequest = (InternalInstantiateNssiRequest)request;
            ResourceAllocationComputeResponse raResponse = internalRequest.getResourceAllocationComputeResponse();
            CoreSlicePayload coreSlicePayload = new CoreSlicePayload();

            coreSlicePayload.setNssiId(request.getNssiId());
            coreSlicePayload.setNst(internalRequest.getParentNst());
            coreSlicePayload.setUpfName(this.upfName);

            originalInstantiationRequestPayload = coreSlicePayload;

            Optional<NssResourceAllocation> allocation = raResponse.getNsResourceAllocation().getNssResourceAllocations().stream()
                    .filter(nssA-> nssA.getNsstId().equals(internalRequest.getNsst().getNsstId()))
                    .findFirst();
            LOG.info("Requesting to NSSMF Core to instantiate NSSI with ID "+request.getNssiId());
            if(allocation.isPresent()){
                String upfName = coreSlicePayload.getUpfName();
                LOG.info("UPF name is "+upfName);
                super.instantiateNetworkSubSlice(coreSlicePayload);
            }
            else throw new FailedOperationException("Could not find allocation for NSST: "+internalRequest.getNsst().getNsstId());

        }
        else throw new MethodNotImplementedException("Instantiate network sub slice method not implemented for generic message");

    }

    public void setUpfName(String upfName){
        this.upfName = upfName;
    }

    private String getNstCore(NssmfBaseProvisioningMessage request) throws JSONException, FailedOperationException {
        BaseRestClient baseRestClient = new BaseRestClient();
        String url = baseUrl + "/nss/" + request.getNssiId();


        ResponseEntity<String> responseEntity = baseRestClient.performHTTPRequest(null, url, HttpMethod.GET);
        if (responseEntity == null)
            throw new FailedOperationException("Error during IMSI association with 5GC ");
        ;
        String body = responseEntity.getBody();
        JSONObject obj = null;

            obj = new JSONObject(body);
            
            String nstCoreid = obj.getJSONArray("coreNetworkSliceId").getString(0);
            String nstCoreid2 = obj.getJSONArray("coreNetworkSliceId").getString(1);
            if(nstCoreid.length()>nstCoreid2.length()){
                return nstCoreid;
            }
        return nstCoreid2;


    }

    private void associateSubscriberToSlice(String imsi, String coreSliceId) throws FailedOperationException {
        String url = baseUrl.split("/core/")[0] + "/core/subscribers-management/associate-subscriber-to-slice/"+imsi+"/"+coreSliceId;

        LOG.info("Requesting to associate IMSI "+imsi+ " with slice");
        LOG.info(url);

        BaseRestClient baseRestClient = new BaseRestClient();
        ResponseEntity<String> responseEntitySub = baseRestClient.performHTTPRequest(null, url, HttpMethod.POST);
        if(responseEntitySub==null)
            throw new FailedOperationException("Error during IMSI association with NSSMF Core ");
    }
    @Override
    public void modifyNetworkSlice(NssmfBaseProvisioningMessage request) throws FailedOperationException {
        if (request instanceof InternalModifyNssiRequest) {
            UpdateConfigurationRequest updateConfigurationRequest = ((InternalModifyNssiRequest) request).getUpdateConfigurationRequest();
            ConfigurationActionType configurationActionType =  updateConfigurationRequest.getActionType();
            LOG.info("Configuration action type is :"+configurationActionType);
            switch(configurationActionType) {
                case ASSOCIATE_SUBSCRIBER:
                    if(updateConfigurationRequest instanceof AssociateSubscriber){
                        String imsi = ((AssociateSubscriber) updateConfigurationRequest).getImsi();
                        associateSubscriberToSlice(imsi, request.getNssiId().toString());
                    }
                    break;


                case SLICE_SCALING:
                    if(updateConfigurationRequest instanceof ScaleNetworkSlice) {
                        ScaleNetworkSlice scaleNetworkSlice = (ScaleNetworkSlice) updateConfigurationRequest;
                        boolean isUlScaling = scaleNetworkSlice.getScalingOption().equals("UL_SCALING");
                        boolean isDlScaling = scaleNetworkSlice.getScalingOption().equals("DL_SCALING");
                        int newDatarate = scaleNetworkSlice.getNewDataRate();
                        originalInstantiationRequestPayload.getNst();
                        CoreSlicePayload coreSlicePayload = originalInstantiationRequestPayload;
                        if(isUlScaling){
                            coreSlicePayload.getNst().getNstServiceProfileList().get(0).setuLThptPerSlice(newDatarate);
                        }
                        if(isDlScaling){
                            coreSlicePayload.getNst().getNstServiceProfileList().get(0).setdLThptPerSlice(newDatarate);
                        }

                        try {
                            String nstCoreid = getNstCore(request);
                            String urlSubscriberCore = baseUrl.split("/core/")[0]+"/core/slice-management/"+request.getNssiId()+"/"+nstCoreid;
                            BaseRestClient baseRestClient = new BaseRestClient();
                            ResponseEntity<String> responseEntitySub = baseRestClient.performHTTPRequest(coreSlicePayload, urlSubscriberCore, HttpMethod.PUT);
                            if(responseEntitySub==null)
                                throw new FailedOperationException("Error during IMSI association with NSSMF Core");

                        } catch (JSONException e) {
                            throw new FailedOperationException("Error modifying core slice ");
                        }
                    }
                    break;
                case UPF_SCALING:
                    LOG.info("I should not be here");
                    /*
                    ScaleNetworksSliceNewUpf scaleNetworksSliceNewUpf = (ScaleNetworksSliceNewUpf) updateConfigurationRequest;
                    LOG.info(scaleNetworksSliceNewUpf.getUpfName());
                    LOG.info(scaleNetworksSliceNewUpf.getNssiId().toString());
                    NsdInfo nsdInfo = new NsdInfo();
                    nsdInfo.setNsdId("fff1deee-df12-4243-98ad-081e574d93df");

                    NST parentNST = ((InternalModifyNssiRequest) request).getParentNst();
                    parentNST.getNsst().setNsdInfo(nsdInfo);
                    CoreSlicePayload coreSlicePayload =new CoreSlicePayload();
                    coreSlicePayload.setNst(parentNST);
                    coreSlicePayload.setNssiId(request.getNssiId());

                    super.modifyNetworkSlice(coreSlicePayload);
                    */

                    break;

            }



        }

    }
    public UUID getNewNssiScaling() {
        return newNssiScaling;
    }

}
