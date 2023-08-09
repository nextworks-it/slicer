package it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NssStatusChange;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.NssmfGenericNssiMessage;

import java.util.UUID;

public class NotifyNssiStatusChange extends NssmfGenericNssiMessage {
    
    private NssStatusChange nssStatusChange;
    private boolean successful;

    public NotifyNssiStatusChange(){

    }
    public NotifyNssiStatusChange(UUID nssiId, NssStatusChange nssStatusChange, boolean successful ){
        super(nssiId);
        this.nssStatusChange= nssStatusChange;
        this.successful= successful;
        
    }

    public NssStatusChange getNssStatusChange() {
        return nssStatusChange;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void isValid() throws MalformattedElementException{
        if(getNssiId()==null) throw  new MalformattedElementException("NotifyNssiStatusChange without NSSI ID");
        if(nssStatusChange==null) throw  new MalformattedElementException("NotifyNssiStatusChange without NSS Status Change");
    }
}
