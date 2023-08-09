package it.nextworks.nfvmano.nssmf.core.nssmanagement;

import it.nextworks.nfvmano.nssmf.service.messages.NssmfMessageType;
import it.nextworks.nfvmano.nssmf.service.messages.notification.NssiStatusChangeNotiticationMessage;
import it.nextworks.nfvmano.sbi.nfvo.interfaces.NfvoLcmNotificationConsumerInterface;
import it.nextworks.nfvmano.sbi.nfvo.messages.NetworkServiceStatusChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class CoreLcmNotificationConsumer implements NfvoLcmNotificationConsumerInterface {

    private final static Logger LOG = LoggerFactory.getLogger(CoreLcmEventHandler.class);
    private HashMap<String, CoreLcmEventHandler> networkServiceInstanceIdCoreLcmHandlerMapping; //KEY: Network Service Instance Identifier, Value, the related OSM LCM handler


    public CoreLcmNotificationConsumer(){
        networkServiceInstanceIdCoreLcmHandlerMapping = new HashMap<>();
    }

    public void setOsmLcmEventHandler(String networkSubSliceInstanceId, CoreLcmEventHandler CoreLcmEventHandler){
        networkServiceInstanceIdCoreLcmHandlerMapping.put(networkSubSliceInstanceId, CoreLcmEventHandler);
    }


    private void manageFailedNotification(String nfvNsInstanceId,boolean success){


    }



    private void manageNetworkServiceInstantiationNotification(String nfvNsInstanceId, boolean success){
        if(success)
            LOG.info("Network Service instantiation is successful");
        else {
            LOG.warn("Network Service instantiation is NOT successful");
            //return;
        }
        CoreLcmEventHandler coreLcmEventHandler = networkServiceInstanceIdCoreLcmHandlerMapping.get(nfvNsInstanceId);
        coreLcmEventHandler.coreNetworkSliceInstantiationTrigger();

    }




    private void manageNetworkServiceScalingNotification(String nfvNsInstanceId, boolean success){
        if(success){
            LOG.info("Network Service scaling is successful");
        }

        else{
            LOG.info("Network Service scaling is NOT successful");
        }
    }

    private void manageNetworkServiceTerminationNotification(String nfvNsInstanceId, boolean success){
        if(success)
            LOG.info("Network Service termination is successful");
        else
            LOG.warn("Network Service termination is NOT successful");
        NssiStatusChangeNotiticationMessage notification= new NssiStatusChangeNotiticationMessage();
        notification.setSuccess(success);
        notification.setMessageType(NssmfMessageType.TERMINATE_NSSI_REQUEST);

        CoreLcmEventHandler coreLcmEventHandler = networkServiceInstanceIdCoreLcmHandlerMapping.get(nfvNsInstanceId);
        coreLcmEventHandler.processNssStatusChangeNotification(notification);

    }

    @Override
    public void notifyNfvNsStatusChange(String nfvNsInstanceId, NetworkServiceStatusChange changeType, boolean success) {
        LOG.info("Received notification for NFV Network Service with ID "+nfvNsInstanceId);

        if(networkServiceInstanceIdCoreLcmHandlerMapping.get(nfvNsInstanceId)==null){
            LOG.error("Cannot manage the notification. Unable to find the OSM LCM Handler for Network Service instance with ID "+nfvNsInstanceId);
            return;
        }

        switch(changeType) {
            case NS_TERMINATED:
                manageNetworkServiceTerminationNotification(nfvNsInstanceId, success);
                break;

            case NS_MODIFIED:
                manageNetworkServiceScalingNotification(nfvNsInstanceId, success);
            break;

            case NS_CREATED:
                manageNetworkServiceInstantiationNotification(nfvNsInstanceId, success);
                break;

            case NS_FAILED:
                manageFailedNotification(nfvNsInstanceId, success);
            break;

            default:
                LOG.warn("Cannot manage "+changeType.toString()+ " notification type");
                break;
        }
    }
}