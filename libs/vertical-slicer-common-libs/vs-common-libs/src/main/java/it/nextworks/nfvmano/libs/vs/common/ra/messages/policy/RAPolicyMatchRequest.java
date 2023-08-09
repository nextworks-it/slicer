package it.nextworks.nfvmano.libs.vs.common.ra.messages.policy;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.interfaces.InterfaceMessage;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.RAPolicySliceType;

public class RAPolicyMatchRequest implements InterfaceMessage {

    private String nstId;
    private String tenant;
    private RAPolicySliceType sst;

    public RAPolicyMatchRequest(String nstId, String tenant, RAPolicySliceType sst) {
        this.nstId = nstId;
        this.tenant = tenant;
        this.sst = sst;
    }

    public String getNstId() {
        return nstId;
    }

    public String getTenant() {
        return tenant;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }

    public RAPolicySliceType getSST() {
        return sst;
    }
}
