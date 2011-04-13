package com.pearson.ed.lp.stub.api;

import org.springframework.integration.annotation.ServiceActivator;

import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.OrderLineItemsResponse;

public interface OrderLifeCycleClient {

	@ServiceActivator
	OrderLineItemsResponse getOrderedISBNsByOrderLineItemIds(OrderLineItemsRequest request);
	
}
