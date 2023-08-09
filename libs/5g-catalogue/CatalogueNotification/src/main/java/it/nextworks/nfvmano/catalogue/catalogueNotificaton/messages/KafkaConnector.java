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
package it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.CatalogueMessageType;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.*;
import java.util.function.Consumer;

public class KafkaConnector {

    public static final Map<String, Object> DEFAULT_PROPS;

    static {
        Map<String, Object> temp = new HashMap<>();
        temp.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        temp.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 500);
        temp.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        temp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        temp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        DEFAULT_PROPS = Collections.unmodifiableMap(temp);
    }

    private final Logger log;
    private final String beanId;
    private final String kafkaBootstrapServers;
    private final String kafkaGroupId;
    private final Map<String, Object> props;
    private final String[] topics;
    private final Map<CatalogueMessageType, Consumer<CatalogueMessage>> functor;

    public KafkaConnector(
            String beanId,
            String kafkaBootstrapServers,
            String kafkaGroupId,
            Map<String, Object> props,
            Map<CatalogueMessageType, Consumer<CatalogueMessage>> functor,
            String... topics) {
        if (props.containsKey(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG)
                || props.containsKey(ConsumerConfig.GROUP_ID_CONFIG)) {
            throw new IllegalArgumentException(String.format(
                    "The properties must not contain key %s nor %s",
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                    ConsumerConfig.GROUP_ID_CONFIG
            ));
        }
        this.beanId = beanId;
        this.kafkaBootstrapServers = kafkaBootstrapServers;
        this.kafkaGroupId = kafkaGroupId;
        this.props = props;
        this.topics = topics;
        this.functor = functor;
        this.log = LoggerFactory.getLogger(String.format("KafkaConnector-%s", this.beanId));
    }

    public static Builder Builder() {
        return new Builder();
    }

    public void init() {
        try {
            // Initialize the Kafka listener container nsdService for this consumer/plugin

            // Build the prop map
            Map<String, Object> actual_props = new HashMap<>(DEFAULT_PROPS);
            actual_props.putAll(props);

            actual_props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
            actual_props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);

            //Create container properties listing topics to subscribe
            ContainerProperties containerProps = new ContainerProperties(topics);
            //Create new listener container
            ConsumerFactory<Integer, String> consumerFactory =
                    new DefaultKafkaConsumerFactory<>(actual_props);
            KafkaMessageListenerContainer<Integer, String> container =
                    new KafkaMessageListenerContainer<>(consumerFactory, containerProps);
            container.setBeanName(beanId);

            //setup the message listeners overriding MessageListener methods
            container.setupMessageListener((MessageListener<Integer, String>) message -> {

                // Log message
                log.info("Received message from kafka bus. Bean {}. MsgId: {}",
                        beanId,
                        message.key()
                );
                log.debug("Message body: {}", message.value());

                try {
                    // Read msg type
                    ObjectMapper mapper = new ObjectMapper();
                    CatalogueMessage catalogueMessage = mapper.readValue(message.value(), CatalogueMessage.class);
                    CatalogueMessageType type = catalogueMessage.getType();

                    // Dispatch to registered function
                    Consumer<CatalogueMessage> consumer = functor.get(type);
                    if (consumer == null) {
                        log.warn("Received unknown message type {}. Bean: {}", type, beanId);
                        return;
                    }
                    consumer.accept(catalogueMessage);
                    log.info("Completed processing of message. Bean: {}. MsgId: {}",
                            beanId,
                            message.key()
                    );
                } catch (Exception e) {
                    log.error("Error while receiving message from kafka bus. BeanId {}. Error: {}",
                            beanId,
                            e.getMessage()
                    );
                    log.debug("Error details:", e);
                }
            });
            //Start the Kafka listener container nsdService
            container.start();

        } catch (Exception e) {
            log.error("Could not initialize Kafka connector. Bean {}. Error: {}", beanId, e.getMessage());
            log.debug("Error details:", e);
        }
    }

    public static class Builder {
        private String beanId;
        private String kafkaBootstrapServers;
        private String kafkaGroupId;
        private Map<String, Object> props = new HashMap<>();
        private Map<CatalogueMessageType, Consumer<CatalogueMessage>> functor = new HashMap<>();
        private List<String> topics = new ArrayList<>();

        public Builder setBeanId(String beanId) {
            this.beanId = beanId;
            return this;
        }

        public Builder setKafkaBootstrapServers(String kafkaBootstrapServers) {
            this.kafkaBootstrapServers = kafkaBootstrapServers;
            return this;
        }

        public Builder setKafkaGroupId(String kafkaGroupId) {
            this.kafkaGroupId = kafkaGroupId;
            return this;
        }

        public Builder setProp(String propName, Object propValue) {
            props.put(propName, propValue);
            return this;
        }

        public Builder setProps(Map<String, Object> props) {
            this.props = props;
            return this;
        }

        public Builder setFunctor(Map<CatalogueMessageType, Consumer<CatalogueMessage>> functor) {
            this.functor = functor;
            return this;
        }

        public Builder addCallback(CatalogueMessageType type, Consumer<CatalogueMessage> consumer) {
            functor.put(type, consumer);
            return this;
        }

        public Builder addTopic(String topic) {
            topics.add(topic);
            return this;
        }

        public Builder setTopics(List<String> topics) {
            this.topics = topics;
            return this;
        }

        public KafkaConnector build() {
            if (beanId == null) {
                throw new IllegalArgumentException("A Bean ID must be provided");
            }
            if (kafkaBootstrapServers == null) {
                throw new IllegalArgumentException("A Kafka bootstrap servers value must be provided");
            }
            if (kafkaGroupId == null) {
                throw new IllegalArgumentException("A Kafka group ID must be provided");
            }
            return new KafkaConnector(
                    beanId,
                    kafkaBootstrapServers,
                    kafkaGroupId,
                    props,
                    functor,
                    topics.toArray(new String[0])
            );
        }
    }
}
