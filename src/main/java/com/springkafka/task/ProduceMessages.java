package com.springkafka.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class ProduceMessages {

	private static final String TOPIC_ONE = "topicTask1";

	private static final long EXECUTOR_SHUTDOWN_TIMEOUT_MINUTES = 5;

	private static final long SEMD_EVERY_SECONDS_TIMEOUT = 5;

	private static volatile int counter = 0;

	public static Logger logger = LoggerFactory.getLogger(ProduceMessages.class);

	@Autowired
	private KafkaTemplate<String, String> template;

	private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	@PostConstruct
	private void init() {
		scheduleSendingMessagesToTopic();
	}

	@PreDestroy
	private void destroy() {
		scheduler.shutdown();
		try {
			if (!scheduler.awaitTermination(EXECUTOR_SHUTDOWN_TIMEOUT_MINUTES, TimeUnit.MINUTES)) {
				scheduler.shutdownNow();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			scheduler.shutdownNow();
		}
	}

	private void scheduleSendingMessagesToTopic() {

		scheduler.scheduleAtFixedRate(() -> {

			if (counter < 1000) {
				logger.info(
						"Every 5 seconds I produce 30 messages with random{\"callStatus\":\"START,END,wRonG\", \"timestamp\":100-104, \"callId\":1679696000-1679696010}");
				for (int i = 0; i < 30; i++) {

					logger.info("Number of message in batch {}", i + 1);

					int wrongMessageStatus = 0;

					String call_status = "START";
					int random_start_end = ThreadLocalRandom.current().nextInt(0, 3);
					Integer random_callId = ThreadLocalRandom.current().nextInt(100, 104);
					Integer random_timestamp = ThreadLocalRandom.current().nextInt(1679696000, 1679696010);

					if (random_start_end == 1) {
						call_status = "END";
					}

					if (random_start_end == 2) {
						wrongMessageStatus++;
					}

					if (wrongMessageStatus == 2) {
						call_status = "wRonG";
						wrongMessageStatus = 0;
					}

					// there may be cases where time < 0, or sent data that is not in json format

					String message = String.format("{\"callStatus\":\"%s\", \"timestamp\":%d, \"callId\":%d}",
							call_status, random_callId, random_timestamp);

					logger.info("From producer {}", message);

					sendMessageToTopicOne(random_callId.toString(), message);

				}
				counter++;
			}
		}, 0, SEMD_EVERY_SECONDS_TIMEOUT, TimeUnit.SECONDS);
	}

	private void sendMessageToTopicOne(String key, String message) {
		this.template.send(TOPIC_ONE, key, message);
	}

}
