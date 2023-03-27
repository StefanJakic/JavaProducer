package com.springkafka.task;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class SpringKafkaTaskProducer {

	private static final String TOPIC_ONE = "topicTask1";

	public static Logger logger = LoggerFactory.getLogger(SpringKafkaTaskProducer.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaTaskProducer.class, args);
	}
	
	@Autowired
	ProduceMessages produceMessages;
	
	
	@Bean
	public NewTopic topicTask1() {
		return TopicBuilder.name(TOPIC_ONE).partitions(2).build();
	}

}
