package com.louisvuiton.eda.vosu.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.louisvuiton.eda.vosu.consumer.model.OrderStatusUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.KafkaClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Component
@Slf4j
public class VapolConsumer {

	@KafkaListener(topics = "oms.order.status.update", groupId = "vapol-consumer", containerFactory =
					"kafkaListenerContainerFactory")
	public void listen(@Payload final String payload) throws JsonProcessingException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			OrderStatusUpdate orderStatusUpdate = objectMapper.readValue(payload, OrderStatusUpdate.class);

			log.info("##### received orderStatusUpdate with content : " + orderStatusUpdate);

		} catch(Exception e) {
			if (e instanceof Retryable) {
				log.info("Throwing retryable exception.");
				throw e;
			}
		}
	}
}
