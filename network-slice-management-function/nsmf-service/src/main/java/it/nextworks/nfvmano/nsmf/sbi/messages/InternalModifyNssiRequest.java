package it.nextworks.nfvmano.nsmf.sbi.messages;

import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NST;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.UpdateConfigurationRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.nsmf.sbi.generic.GenericInstantiateNssiRequest;

import java.util.UUID;

public class InternalModifyNssiRequest extends GenericInstantiateNssiRequest {

    private UUID parentNsiId;

    private NST parentNst;
    private NSST nsst;
    private UpdateConfigurationRequest updateConfigurationRequest;
    private UUID operationId;


    public InternalModifyNssiRequest(UUID nssiId, UUID parentNsiId, NSST nsst, UpdateConfigurationRequest updateConfigurationRequest, NST parentNST, UUID operationId){
        super(nssiId);
        this.nsst=nsst;
        this.updateConfigurationRequest = updateConfigurationRequest;
        this.parentNst=parentNST;
        this.parentNsiId=parentNsiId;
        this.operationId=operationId;
    }

    public UUID getParentNsiId() {
        return parentNsiId;
    }

    public NSST getNsst() {
        return nsst;
    }

    public UpdateConfigurationRequest getUpdateConfigurationRequest() {
        return updateConfigurationRequest;
    }

    public UUID getOperationId() {
        return operationId;
    }

    public NST getParentNst() {
        return parentNst;
    }
}
