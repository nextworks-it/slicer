package it.nextworks.nfvmano.catalogue.plugins.vim;

import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.*;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.ScopeType;
import it.nextworks.nfvmano.catalogue.engine.NsdManagementInterface;
import it.nextworks.nfvmano.catalogue.engine.VnfPackageManagementInterface;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class DummyVIMPlugin extends VIMPlugin {

    private static final Logger log = LoggerFactory.getLogger(DummyVIMPlugin.class);

    public DummyVIMPlugin(VIMType vimType, VIM vim, String kafkaBootstrapServers, NsdManagementInterface nsdService, VnfPackageManagementInterface vnfdService, String localTopic, String remoteTopic, KafkaTemplate<String, String> kafkaTemplate) {
        super(vimType, vim, kafkaBootstrapServers, nsdService, vnfdService, localTopic, remoteTopic, kafkaTemplate);
    }

    @Override
    public void acceptVnfPkgOnBoardingNotification(VnfPkgOnBoardingNotificationMessage notification) {
        log.info("Received VNF Pkg onboarding notification");
        log.debug("Body: {}", notification);
        VnfImgUploadingNotificationMessage response = new VnfImgUploadingNotificationMessage(
                notification.getVnfPkgInfoId(),
                notification.getVnfdId(),
                notification.getOperationId(),
                ScopeType.REMOTE,
                OperationStatus.SUCCESSFULLY_DONE,
                vim.getVimId()
        );
        sendNotification(response);
        log.debug("VnfImgUploadingNotification sent to NotificationManager");
    }

    @Override
    public void acceptVnfPkgChangeNotification(VnfPkgChangeNotificationMessage notification) {
        log.info("Received VNF Pkg change notification");
        log.debug("Body: {}", notification);
    }

    @Override
    public void acceptVnfPkgDeletionNotification(VnfPkgDeletionNotificationMessage notification) {
        log.info("Received VNF Pkg deletion notification");
        log.debug("Body: {}", notification);
        VnfImgDeletionNotificationMessage response = new VnfImgDeletionNotificationMessage(
                notification.getVnfPkgInfoId(),
                notification.getVnfdId(),
                notification.getOperationId(),
                ScopeType.REMOTE,
                OperationStatus.SUCCESSFULLY_DONE,
                vim.getVimId()
        );
        sendNotification(response);
        log.debug("VnfPkgDeletionNotification sent to NotificationManager");
    }
}
