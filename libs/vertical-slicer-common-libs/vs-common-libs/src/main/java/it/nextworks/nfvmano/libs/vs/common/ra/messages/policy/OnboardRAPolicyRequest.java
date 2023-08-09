package it.nextworks.nfvmano.libs.vs.common.ra.messages.policy;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.interfaces.InterfaceMessage;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.ResourceAllocationPolicy;

public class OnboardRAPolicyRequest  implements InterfaceMessage {

    private ResourceAllocationPolicy resourceAllocationPolicy;

    public OnboardRAPolicyRequest(){

    }

    public OnboardRAPolicyRequest(ResourceAllocationPolicy resourceAllocationPolicy){
        this.resourceAllocationPolicy=resourceAllocationPolicy;
    }

    public ResourceAllocationPolicy getResourceAllocationPolicy(){
        return  resourceAllocationPolicy;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if(resourceAllocationPolicy==null)
            throw new MalformattedElementException("Onboard RA policy without policy");
        resourceAllocationPolicy.isValid();
    }
}
