package it.nextworks.nfvmano.nsmf.sbi.generic;

import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.InstantiateNssiRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;

import java.util.UUID;

public class GenericInstantiateNssiRequest extends NssmfBaseProvisioningMessage {


    public GenericInstantiateNssiRequest(UUID nssiId){
        super(nssiId);
    }
}
