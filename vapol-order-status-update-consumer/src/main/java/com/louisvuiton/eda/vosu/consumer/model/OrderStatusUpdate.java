package com.louisvuiton.eda.vosu.consumer.model;

import lombok.Data;

@Data
public class OrderStatusUpdate {
	private String shippingNumber;
	private String status;
	private String channel;
	private String placing;
}
