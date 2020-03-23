package it.nextworks.nfvmano.sebastian.nstE2Ecomposer.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.templates.NST;

public class NstAdvertisementRequest implements InterfaceMessage {
    private NST nst;
    private String domainName;

    public NstAdvertisementRequest(){}

    public NstAdvertisementRequest(NST nst, String domainName){
        this.nst=nst;
        this.domainName=domainName;
    }


    public NST getNst() {
        return nst;
    }

    public String getDomainName() {
        return domainName;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        nst.isValid();
        if(domainName==null)
            throw new MalformattedElementException("Domain name cannot be null.");
    }
}
