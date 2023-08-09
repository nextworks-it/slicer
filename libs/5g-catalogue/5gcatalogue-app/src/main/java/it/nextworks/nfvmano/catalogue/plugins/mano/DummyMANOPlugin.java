/*
 * Copyright 2018 Nextworks s.r.l.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.catalogue.plugins.mano;

import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.*;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.ScopeType;
import it.nextworks.nfvmano.catalogue.engine.NsdManagementInterface;
import it.nextworks.nfvmano.catalogue.engine.VnfPackageManagementInterface;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.PluginOperationalState;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANO;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANOPlugin;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANOType;
import it.nextworks.nfvmano.catalogue.translators.tosca.DescriptorsParser;
import it.nextworks.nfvmano.libs.common.elements.KeyValuePair;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Map;

public class DummyMANOPlugin extends MANOPlugin {

    private static final Logger log = LoggerFactory.getLogger(DummyMANOPlugin.class);

    public DummyMANOPlugin(
            MANOType manoType,
            MANO mano,
            String kafkaBootstrapServers,
            NsdManagementInterface nsdService,
            VnfPackageManagementInterface vnfdService,
            DescriptorsParser descriptorsParser,
            String localTopic,
            String remoteTopic,
            KafkaTemplate<String, String> kafkaTemplate,
            boolean manoSync
    ) {
        super(
                manoType,
                mano,
                kafkaBootstrapServers,
                localTopic,
                remoteTopic,
                kafkaTemplate,
                manoSync
        );
    }

    @Override
    public Map<String, List<String>> getAllVnfd(String project){
        return null;
    }

    @Override
    public Map<String, List<String>> getAllNsd(String project){
        return null;
    }

    @Override
    public KeyValuePair getTranslatedPkgPath(String descriptorId, String descriptorVersion, String project){
        return null;
    }

    @Override
    public String getManoPkgInfoId(String cataloguePkgInfoId){
        return null;
    }

    @Override
    public void notifyOnboarding(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus){
    }

    @Override
    public void notifyDelete(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus){
    }

    @Override
    public void RuntimeSynchronization(){}

    @Override
    public void acceptNsdOnBoardingNotification(NsdOnBoardingNotificationMessage notification) {
        if (notification.getScope() == ScopeType.LOCAL) {
            NsdOnBoardingNotificationMessage response;
            if (this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                log.info("Received NSD onboarding notification");
                log.debug("Body: {}", notification);
                response = new NsdOnBoardingNotificationMessage(
                        notification.getNsdInfoId(),
                        notification.getNsdId(),
                        notification.getNsdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.SUCCESSFULLY_DONE,
                        mano.getManoId(),
                        null,
                        null
                );
            } else {
                log.info("Skipping NSD onboarding notification");
                response = new NsdOnBoardingNotificationMessage(
                        notification.getNsdInfoId(),
                        notification.getNsdId(),
                        notification.getNsdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.RECEIVED,
                        mano.getManoId(),
                        null,
                        null
                );
            }
            sendNotification(response);
            log.debug("NsdOnBoardingNotification sent to NotificationManager");
        }
    }

    @Override
    public void acceptNsdChangeNotification(NsdChangeNotificationMessage notification) {
        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("Received NSD change notification");
            log.debug("Body: {}", notification);
        }
    }

    @Override
    public void acceptNsdDeletionNotification(NsdDeletionNotificationMessage notification) {
        if (notification.getScope() == ScopeType.LOCAL) {
            NsdDeletionNotificationMessage response;
            if (this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                log.info("Received NSD deletion notification");
                log.debug("Body: {}", notification);
                response = new NsdDeletionNotificationMessage(
                        notification.getNsdInfoId(),
                        notification.getNsdId(),
                        notification.getNsdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.SUCCESSFULLY_DONE,
                        mano.getManoId(),
                        null
                );
            } else {
                log.info("Skipping NSD deletion notification");
                response = new NsdDeletionNotificationMessage(
                        notification.getNsdInfoId(),
                        notification.getNsdId(),
                        notification.getNsdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.RECEIVED,
                        mano.getManoId(),
                        null
                );
            }
            sendNotification(response);
            log.debug("NsdDeletionNotification sent to NotificationManager");
        }
    }

    @Override
    public void acceptPnfdOnBoardingNotification(PnfdOnBoardingNotificationMessage notification) throws MethodNotImplementedException {
        if (notification.getScope() == ScopeType.LOCAL) {
            PnfdOnBoardingNotificationMessage response;
            if (this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                log.info("Received PNFD onboarding notification");
                log.debug("Body: {}", notification);
                response = new PnfdOnBoardingNotificationMessage(
                        notification.getPnfdInfoId(),
                        notification.getPnfdId(),
                        notification.getPnfdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.SUCCESSFULLY_DONE,
                        mano.getManoId(),
                        null,
                        null
                );
            } else {
                log.info("Skipping PNFD onboarding notification");
                response = new PnfdOnBoardingNotificationMessage(
                        notification.getPnfdInfoId(),
                        notification.getPnfdId(),
                        notification.getPnfdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.RECEIVED,
                        mano.getManoId(),
                        null,
                        null
                );
            }
            sendNotification(response);
            log.debug("PnfdOnBoardingNotification sent to NotificationManager");
        }
    }

    @Override
    public void acceptPnfdDeletionNotification(PnfdDeletionNotificationMessage notification) throws MethodNotImplementedException {
        if (notification.getScope() == ScopeType.LOCAL) {
            PnfdDeletionNotificationMessage response;
            if (this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                log.info("Received PNFD deletion notification");
                log.debug("Body: {}", notification);
                response = new PnfdDeletionNotificationMessage(
                        notification.getPnfdInfoId(),
                        notification.getPnfdId(),
                        notification.getPnfdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.SUCCESSFULLY_DONE,
                        mano.getManoId(),
                        null
                );
            } else {
                log.info("Skipping PNFD deletion notification");
                response = new PnfdDeletionNotificationMessage(
                        notification.getPnfdInfoId(),
                        notification.getPnfdId(),
                        notification.getPnfdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.RECEIVED,
                        mano.getManoId(),
                        null
                );
            }
            sendNotification(response);
            log.debug("PnfdDeletionNotification sent to NotificationManager");
        }
    }

    @Override
    public void acceptVnfPkgOnBoardingNotification(VnfPkgOnBoardingNotificationMessage notification) {
        if (notification.getScope() == ScopeType.LOCAL) {
            VnfPkgOnBoardingNotificationMessage response;
            if (this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                log.info("Received VNF Pkg onboarding notification");
                log.debug("Body: {}", notification);
                response = new VnfPkgOnBoardingNotificationMessage(
                        notification.getVnfPkgInfoId(),
                        notification.getVnfdId(),
                        notification.getVnfdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.SUCCESSFULLY_DONE,
                        mano.getManoId(),
                        null,
                        null
                );
            } else {
                log.info("Skipped VNF Pkg onboarding notification");
                response = new VnfPkgOnBoardingNotificationMessage(
                        notification.getVnfPkgInfoId(),
                        notification.getVnfdId(),
                        notification.getVnfdId(),
                        notification.getVnfdVersion(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.RECEIVED,
                        mano.getManoId(),
                        null,
                        null
                );
            }
            sendNotification(response);
            log.debug("VnfPkgOnBoardingNotification sent to NotificationManager");
        }
    }

    @Override
    public void acceptVnfPkgChangeNotification(VnfPkgChangeNotificationMessage notification) {
        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("Received VNF Pkg change notification");
            log.debug("Body: {}", notification);
        }
    }

    @Override
    public void acceptVnfPkgDeletionNotification(VnfPkgDeletionNotificationMessage notification) {
        if (notification.getScope() == ScopeType.LOCAL) {
            VnfPkgDeletionNotificationMessage response;
            if (this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                log.info("Received VNF Pkg deletion notification");
                log.debug("Body: {}", notification);
                response = new VnfPkgDeletionNotificationMessage(
                        notification.getVnfPkgInfoId(),
                        notification.getVnfdId(),
                        notification.getVnfdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.SUCCESSFULLY_DONE,
                        mano.getManoId(),
                        null
                );
            } else {
                log.info("Skipped VNF Pkg deletion notification");
                response = new VnfPkgDeletionNotificationMessage(
                        notification.getVnfPkgInfoId(),
                        notification.getVnfdId(),
                        notification.getVnfdVersion(),
                        notification.getProject(),
                        notification.getOperationId(),
                        ScopeType.REMOTE,
                        OperationStatus.RECEIVED,
                        mano.getManoId(),
                        null
                );
            }
            sendNotification(response);
            log.debug("VnfPkgDeletionNotification sent to NotificationManager");
        }
    }
}
