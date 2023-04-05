package it.nextworks.nfvmano.sbi.nfvo.interfaces;

import it.nextworks.nfvmano.sbi.nfvo.messages.InternalNsLifecycleChangeNotification;

public interface NsLcmConsumerInterface {
    public void notifyNetworkServiceStatusChange(InternalNsLifecycleChangeNotification notification) ;
}
