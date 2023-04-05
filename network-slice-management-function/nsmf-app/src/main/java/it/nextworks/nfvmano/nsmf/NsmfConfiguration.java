package it.nextworks.nfvmano.nsmf;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NsmfConfiguration {

    @Bean(name="engine-queue-exchange")
    TopicExchange exchange() {
        return new TopicExchange("engine-queue-exchange", true, false);
    }

}
