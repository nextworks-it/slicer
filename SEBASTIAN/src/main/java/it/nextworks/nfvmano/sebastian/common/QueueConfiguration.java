package it.nextworks.nfvmano.sebastian.common;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {

	@Bean(name=ConfigurationParameters.engineQueueExchange)
	TopicExchange exchange() {
		return new TopicExchange(ConfigurationParameters.engineQueueExchange, true, false);
	}
	
}
