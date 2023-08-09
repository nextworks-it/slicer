package it.nextworks.nfvmano.libs.vs.common.vsmf.message;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.interfaces.InterfaceMessage;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceInstanceStatus;
import it.nextworks.nfvmano.libs.vs.common.vsmf.elements.NsiErrors;
import it.nextworks.nfvmano.libs.vs.common.vsmf.elements.NsiNotifType;

import java.util.UUID;

public class VsmfNotificationMessage implements InterfaceMessage {

    private UUID nsiId;

    private NsiNotifType nsiNotifType;

    private NetworkSliceInstanceStatus nsiStatus;

    private NsiErrors errors;

    public VsmfNotificationMessage(){}

    public VsmfNotificationMessage(UUID nsiId, NsiNotifType nsiNotifType, NetworkSliceInstanceStatus nsiStatus) {
        this.nsiId = nsiId;
        this.nsiNotifType = nsiNotifType;
        this.nsiStatus = nsiStatus;
    }

    public UUID getNsiId() {
        return nsiId;
    }

    public void setNsiId(UUID nsiId) {
        this.nsiId = nsiId;
    }

    public NsiNotifType getNsiNotifType() {
        return nsiNotifType;
    }

    public void setNsiNotifType(NsiNotifType nsiNotifType) {
        this.nsiNotifType = nsiNotifType;
    }

    public NetworkSliceInstanceStatus getNsiStatus() {
        return nsiStatus;
    }

    public void setNsiStatus(NetworkSliceInstanceStatus nsiStatus) {
        this.nsiStatus = nsiStatus;
    }

    public NsiErrors getErrors() {
        return errors;
    }

    public void setErrors(NsiErrors errors) {
        this.errors = errors;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (nsiId == null)
            throw new MalformattedElementException("NSI ID not specified.");
        if (nsiNotifType == null)
            throw new MalformattedElementException("NSI Notification Type not specified.");
        if (nsiStatus == null)
            throw new MalformattedElementException("NSI Status not specified.");
        if (nsiStatus == NetworkSliceInstanceStatus.FAILED && errors == null)
            throw new MalformattedElementException("NSI Error Type not specified.");
    }
}
