package it.nextworks.nfvmano.nsmf.sbi.specific;

import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.AssociateSubscriber;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ConfigurationActionType;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ScaleNetworkSlice;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.UpdateConfigurationRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.CoreSlicePayload;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.libs.vs.common.utils.BaseRestClient;
import it.nextworks.nfvmano.nsmf.sbi.NssmfRestClient;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalInstantiateNssiRequest;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalModifyNssiRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class CmcCoreNssmfRestClient extends NssmfRestClient {
    private String baseUrl;
    private final static Logger LOG= LoggerFactory.getLogger(CmcCoreNssmfRestClient.class);

    private CoreSlicePayload originalInstantiationRequestPayload;
    public CmcCoreNssmfRestClient(String url) {
        super(url);
        this.baseUrl = url;
    }

    @Override
    public void instantiateNetworkSubSlice(NssmfBaseProvisioningMessage request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, AlreadyExistingEntityException, NotExistingEntityException {
        if(request instanceof InternalInstantiateNssiRequest){
            InternalInstantiateNssiRequest internalRequest = (InternalInstantiateNssiRequest)request;
            ResourceAllocationComputeResponse raResponse = internalRequest.getResourceAllocationComputeResponse();
            CoreSlicePayload coreSlicePayload = new CoreSlicePayload();

            coreSlicePayload.setNssiId(request.getNssiId());
            coreSlicePayload.setNst(internalRequest.getParentNst());
            originalInstantiationRequestPayload = coreSlicePayload;

            Optional<NssResourceAllocation> allocation = raResponse.getNsResourceAllocation().getNssResourceAllocations().stream()
                    .filter(nssA-> nssA.getNsstId().equals(internalRequest.getNsst().getNsstId()))
                    .findFirst();
            if(allocation.isPresent()){

                super.instantiateNetworkSubSlice(coreSlicePayload);
            }
            else throw new FailedOperationException("Could not find allocation for NSST: "+internalRequest.getNsst().getNsstId());

        }
        else throw new MethodNotImplementedException("Instantiate network sub slice method not implemented for generic message");

    }

    private String getNstCore(NssmfBaseProvisioningMessage request) throws JSONException, FailedOperationException {
        BaseRestClient baseRestClient = new BaseRestClient();
        String url = baseUrl + "/nss/" + request.getNssiId();


        ResponseEntity<String> responseEntity = baseRestClient.performHTTPRequest(null, url, HttpMethod.GET);
        if (responseEntity == null)
            throw new FailedOperationException("Error during IMSI assocation with 5GC ");
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

    @Override
    public void modifyNetworkSlice(NssmfBaseProvisioningMessage request) throws FailedOperationException {
        if (request instanceof InternalModifyNssiRequest) {
            UpdateConfigurationRequest updateConfigurationRequest = ((InternalModifyNssiRequest) request).getUpdateConfigurationRequest();
            ConfigurationActionType configurationActionType =  updateConfigurationRequest.getActionType();

            switch(configurationActionType) {
                case ASSOCIATE_SUBSCRIBER:
                    if(updateConfigurationRequest instanceof AssociateSubscriber){
                        String imsi = ((AssociateSubscriber) updateConfigurationRequest).getImsi();

                        BaseRestClient baseRestClient = new BaseRestClient();
                        try {
                            String nstCoreid = getNstCore(request);
                            LOG.info(nstCoreid);
                            LOG.info(baseUrl);
                            String urlSubscriberCore = baseUrl.split("/core/")[0] + "/core/subscribers-management/"+request.getNssiId()+"/"+nstCoreid;
                            LOG.info(urlSubscriberCore);
                            JSONObject json = new JSONObject();
                            json.put("sliceName", nstCoreid);
                            JSONArray array = new JSONArray();
                            JSONObject item = new JSONObject();
                            item.put("imsi", imsi);
                            item.put("k", "000102030405060708090A0B0C0D0E0F");
                            array.put(item);

                            json.put("subscriberList", array);
                            String message = json.toString();
                            ResponseEntity<String> responseEntitySub = baseRestClient.performHTTPRequest(message, urlSubscriberCore, HttpMethod.POST);
                            if(responseEntitySub==null)
                                throw new FailedOperationException("Error during IMSI assocation with NSSMG Core ");

                        } catch (JSONException e) {
                           LOG.error(e.getMessage());
                        }


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
                                throw new FailedOperationException("Error during IMSI assocation with NSSMG Core ");

                        } catch (JSONException e) {
                            throw new FailedOperationException("Error modifying core slice ");
                        }


                    }

                    break;

            }



        }

    }
}
