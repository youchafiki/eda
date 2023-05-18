package com.louisvuiton.eda.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louisvuiton.eda.producer.model.OrderStatusUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class PushOrderStatusUpdateController {

	// Autowiring Kafka Template
	@Autowired
	KafkaProducerService kafkaProducerService;

	// Publish messages using the GetMapping
	@PutMapping("/publish")
	public String publishMessage(@RequestBody final OrderStatusUpdate orderStatusUpdate)
	{
		log.info("#### new order status to push : " +orderStatusUpdate);
		ObjectMapper Obj = new ObjectMapper();
		String orderStatusUpdateMessage=null;

		try {
			// Converting the Java object into a JSON string
			orderStatusUpdateMessage = Obj.writeValueAsString(orderStatusUpdate);
			// Displaying Java object into a JSON string
			System.out.println(orderStatusUpdateMessage);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		kafkaProducerService.sendMessage(orderStatusUpdate.getShippingNumber(), orderStatusUpdateMessage);

		return "Published Successfully";
	}
}
