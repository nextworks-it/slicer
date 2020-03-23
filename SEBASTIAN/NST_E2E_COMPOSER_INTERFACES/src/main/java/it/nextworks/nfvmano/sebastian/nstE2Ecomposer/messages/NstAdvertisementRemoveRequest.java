package it.nextworks.nfvmano.sebastian.nstE2Ecomposer.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

public class NstAdvertisementRemoveRequest implements InterfaceMessage {

    private String nstId;
    private String domainName;

    public NstAdvertisementRemoveRequest(){}

    public NstAdvertisementRemoveRequest(String nstId, String domainName){
        this.nstId=nstId;
        this.domainName=domainName;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if(nstId==null)
            throw new MalformattedElementException("NST UUID cannot be null.");

        if(domainName==null)
            throw new MalformattedElementException("Domain name cannot be null.");
    }
    public String getNstId() {
        return nstId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setNstId(String nstId) {
        this.nstId = nstId;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

}
