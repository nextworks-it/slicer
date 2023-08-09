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
package it.nextworks.nfvmano.catalogue.plugins.vim;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.*;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.CatalogueMessageType;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.interfaces.VnfPkgNotificationsConsumerInterface;
import it.nextworks.nfvmano.catalogue.engine.NsdManagementInterface;
import it.nextworks.nfvmano.catalogue.engine.VnfPackageManagementInterface;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.Plugin;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.PluginType;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public abstract class VIMPlugin
        extends Plugin
        implements VnfPkgNotificationsConsumerInterface {

    private static final Logger log = LoggerFactory.getLogger(VIMPlugin.class);

    protected VIMType vimType;
    protected VIM vim;
    protected KafkaConnector connector;
    protected NsdManagementInterface nsdService;
    protected VnfPackageManagementInterface vnfdService;
    protected KafkaTemplate<String, String> kafkaTemplate;
    private String remoteTopic;

    public VIMPlugin(
            VIMType vimType,
            VIM vim,
            String kafkaBootstrapServers,
            NsdManagementInterface nsdService,
            VnfPackageManagementInterface vnfdService,
            String localTopic,
            String remoteTopic,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        super(vim.getVimId(), PluginType.VIM);
        this.vimType = vimType;
        this.vim = vim;
        this.nsdService = nsdService;
        this.vnfdService = vnfdService;
        this.remoteTopic = remoteTopic;
        if (this.vim.getVimType() != this.vimType) {
            throw new IllegalArgumentException("VIM type and VIM do not agree");
        }
        String connectorID = "VIM_" + vim.getVimId(); // assuming it's unique amongVIMs
        Map<CatalogueMessageType, Consumer<CatalogueMessage>> functor = new HashMap<>();
        functor.put(
                CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION,
                msg -> {
                    VnfPkgOnBoardingNotificationMessage castMsg = (VnfPkgOnBoardingNotificationMessage) msg;
                    try {
                        acceptVnfPkgOnBoardingNotification(castMsg);
                    } catch (MethodNotImplementedException e) {
                        log.error("Method not yet implemented: " + e.getMessage());
                    }
                }
        );
        functor.put(
                CatalogueMessageType.VNFPKG_CHANGE_NOTIFICATION,
                msg -> {
                    VnfPkgChangeNotificationMessage castMsg = (VnfPkgChangeNotificationMessage) msg;
                    try {
                        acceptVnfPkgChangeNotification(castMsg);
                    } catch (MethodNotImplementedException e) {
                        log.error("Method not yet implemented: " + e.getMessage());
                    }
                }
        );
        functor.put(
                CatalogueMessageType.VNFPKG_DELETION_NOTIFICATION,
                msg -> {
                    VnfPkgDeletionNotificationMessage castMsg = (VnfPkgDeletionNotificationMessage) msg;
                    try {
                        acceptVnfPkgDeletionNotification(castMsg);
                    } catch (MethodNotImplementedException e) {
                        log.error("Method not yet implemented: " + e.getMessage());
                    }
                }
        );
        connector = KafkaConnector.Builder()
                .setBeanId(connectorID)
                .setKafkaBootstrapServers(kafkaBootstrapServers)
                .setKafkaGroupId(connectorID)
                .addTopic(localTopic)
                .setFunctor(functor)
                .build();
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(CatalogueMessage msg) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            String json = mapper.writeValueAsString(msg);
            kafkaTemplate.send(remoteTopic, json);
        } catch (JsonProcessingException e) {
            log.error("Could not notify main engine: {}", e.getMessage());
            log.debug("Error details: ", e);
        }
    }

    @Override
    public void init() {
        connector.init();
    }

    public VIMType getVimType() {
        return vimType;
    }

    public void setVimType(VIMType vimType) {
        this.vimType = vimType;
    }

    public VIM getVim() {
        return vim;
    }

    public void setVim(VIM vim) {
        this.vim = vim;
    }
}
