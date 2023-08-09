package it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces;

import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceSubnetInstance;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.UpdateConfigurationRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.CreateNsiIdFromNestRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.CreateNsiIdRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.InstantiateNsiRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.TerminateNsiRequest;
import it.nextworks.nfvmano.libs.vs.common.query.messages.GeneralizedQueryRequest;

import java.util.List;
import java.util.UUID;

public interface NsmfLcmProvisioningInterface {

    UUID createNetworkSliceIdentifierFromNst(CreateNsiIdRequest request, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;

    UUID createNetworkSliceIdentifierFromNest(CreateNsiIdFromNestRequest request, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;

    void instantiateNetworkSlice(InstantiateNsiRequest request, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;

    void terminateNetworkSliceInstance(TerminateNsiRequest request, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;

    List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String tenantId)
            throws MalformattedElementException;
    List<NetworkSliceSubnetInstance> queryNetworkSliceSubnetInstance(GeneralizedQueryRequest request, String tenantId) throws MalformattedElementException;;




}
