package it.nextworks.nfvmano.sebastian.vsfm.interfaces;


import it.nextworks.nfvmano.sebastian.vsfm.messages.VerticalServiceStatusChangeNotification;

public interface VsLcmConsumerInterface {
    public void notifyVerticalServiceStatusChange(VerticalServiceStatusChangeNotification notification, String domain);
}
