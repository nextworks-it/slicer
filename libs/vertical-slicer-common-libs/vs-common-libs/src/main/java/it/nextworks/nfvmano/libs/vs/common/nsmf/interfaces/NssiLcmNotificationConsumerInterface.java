package it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NssStatusChange;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.NsmfNotificationMessage;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.NotifyNssiStatusChange;

public interface NssiLcmNotificationConsumerInterface {

    void notifyNssStatusChange(NsmfNotificationMessage nssiStatusChange) throws NotExistingEntityException, MalformattedElementException;
}
