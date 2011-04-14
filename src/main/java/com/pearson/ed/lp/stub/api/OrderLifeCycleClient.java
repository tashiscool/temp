package com.pearson.ed.lp.stub.api;

import org.springframework.integration.annotation.ServiceActivator;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.OrderLineItemsResponse;

/**
 * Client stub API for the OrderLifeCycle service.
 * 
 * @author ULLOYNI
 *
 */
public interface OrderLifeCycleClient {

	@ServiceActivator
	OrderLineItemsResponse getOrderedISBNsByOrderLineItemIds(OrderLineItemsRequest request) 
		throws AbstractRumbaException;
	
}
