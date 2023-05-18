package com.louisvuiton.eda.producer.model;

import lombok.Data;

@Data
public class OrderStatusUpdate {
	private String shippingNumber;
	private String status;
	private String channel;
	private String placing;
}
