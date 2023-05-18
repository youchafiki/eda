package com.louisvuiton.eda.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@EnableRetry
public class KafkaProducerService {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Retryable(
					value = {Exception.class},
					maxAttempts = 3,
					backoff = @Backoff(delay = 5000) // Delay between retries in milliseconds
	)
	public void sendMessage(String key, String message) {
		kafkaTemplate.setDefaultTopic("oms.order.status.update");
		kafkaTemplate.sendDefault(key, message);
	}
}
