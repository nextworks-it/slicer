package it.nextworks.nfvmano.sbi.nfvo.messages;

import it.nextworks.nfvmano.sbi.nfvo.interfaces.NsLcmProviderInterface;
import it.nextworks.nfvmano.sbi.nfvo.polling.NfvoLcmOperationType;

public class InternalNsLifecycleChangeNotification

{

        private String operationId;
        private String nsInstanceId;
        private NsLcmProviderInterface driver;
        private NfvoLcmNotificationType lcmNotificationType;
        //private String operation;
        private NfvoLcmOperationType operationType;

    public InternalNsLifecycleChangeNotification(String operationId, String nsInstanceId, NsLcmProviderInterface driver, NfvoLcmNotificationType lcmNotificationType, NfvoLcmOperationType operationType) {
        this.operationId = operationId;
        this.nsInstanceId = nsInstanceId;
        this.driver = driver;
        this.lcmNotificationType = lcmNotificationType;
        //this.operation=operation;
        this.operationType =operationType;
    }

    public NfvoLcmOperationType getOperationType() {
        return operationType;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getNsInstanceId() {
        return nsInstanceId;
    }

    public NsLcmProviderInterface getDriver() {
        return driver;
    }

    public NfvoLcmNotificationType getLcmNotificationType() {
        return lcmNotificationType;
    }
}
