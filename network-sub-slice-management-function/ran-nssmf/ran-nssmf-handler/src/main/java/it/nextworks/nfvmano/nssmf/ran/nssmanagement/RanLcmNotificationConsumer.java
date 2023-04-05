package it.nextworks.nfvmano.nssmf.ran.nssmanagement;

import it.nextworks.nfvmano.nssmf.service.messages.NssmfMessageType;
import it.nextworks.nfvmano.nssmf.service.messages.notification.NssiStatusChangeNotiticationMessage;
import it.nextworks.nfvmano.sbi.nfvo.interfaces.NfvoLcmNotificationConsumerInterface;
import it.nextworks.nfvmano.sbi.nfvo.messages.NetworkServiceStatusChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class RanLcmNotificationConsumer implements NfvoLcmNotificationConsumerInterface {

    private final static Logger LOG = LoggerFactory.getLogger(RanLcmEventHandler.class);
    private HashMap<String, RanLcmEventHandler> networkServiceInstanceIdCoreLcmHandlerMapping; //KEY: Network Service Instance Identifier, Value, the related OSM LCM handler


    public RanLcmNotificationConsumer(){
        networkServiceInstanceIdCoreLcmHandlerMapping = new HashMap<>();
    }

    public void setOsmLcmEventHandler(String networkSubSliceInstanceId, RanLcmEventHandler RanLcmEventHandler){
        networkServiceInstanceIdCoreLcmHandlerMapping.put(networkSubSliceInstanceId, RanLcmEventHandler);
    }


    private void manageFailedNotification(String nfvNsInstanceId,boolean success){
        LOG.warn("Failed notification not implemented yet.");
    }



    private void manageNetworkServiceInstantiationNotification(String nfvNsInstanceId,boolean success){
        if(success)
            LOG.info("Network Service instantiation is successful");
        else {
            LOG.info("Network Service instantiation is NOT successful");
            return;
        }
        RanLcmEventHandler ranLcmEventHandler = networkServiceInstanceIdCoreLcmHandlerMapping.get(nfvNsInstanceId);
        ranLcmEventHandler.ranNetworkSliceInstantiationTrigger();

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
        RanLcmEventHandler ranLcmEventHandler = networkServiceInstanceIdCoreLcmHandlerMapping.get(nfvNsInstanceId);
        ranLcmEventHandler.processNssStatusChangeNotification(notification);

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
