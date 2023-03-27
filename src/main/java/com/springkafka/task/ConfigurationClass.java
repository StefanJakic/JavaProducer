package com.springkafka.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationClass {

	private static Logger logger = LoggerFactory.getLogger(ConfigurationClass.class);
	
	@Bean ProduceMessages produceMessages() {
		ProduceMessages produceMessages = new ProduceMessages();
		return produceMessages;
	}
}