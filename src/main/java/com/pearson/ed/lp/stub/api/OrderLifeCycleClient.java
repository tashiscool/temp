package com.pearson.ed.lp.stub.api;

import org.springframework.integration.annotation.ServiceActivator;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.message.OrderLineItemRequest;
import com.pearson.ed.lp.message.OrderLineItemResponse;

/**
 * Client stub API for the OrderLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public interface OrderLifeCycleClient {

	/**
	 * Get request service activator to call the GetOrderLineItemById service function
	 * of the OrderLifeCycle service.
	 * 
	 * @param request {@link OrderLineItemsRequest}
	 * @return {@link OrderLineItemsResponse}
	 * @throws AbstractRumbaException on error from the service
	 */
	@ServiceActivator
	OrderLineItemResponse getOrderedISBNByOrderLineItemId(OrderLineItemRequest request)
			throws AbstractRumbaException;

}
