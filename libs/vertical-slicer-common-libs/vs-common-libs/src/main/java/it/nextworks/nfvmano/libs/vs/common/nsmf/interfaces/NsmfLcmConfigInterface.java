package it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces;

import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.UpdateConfigurationRequest;

import java.util.UUID;

public interface NsmfLcmConfigInterface {

    UUID configureNetworkSlice(UpdateConfigurationRequest request, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;


}
