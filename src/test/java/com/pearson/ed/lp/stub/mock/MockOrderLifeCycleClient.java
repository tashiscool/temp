package com.pearson.ed.lp.stub.mock;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.OrderLineItemsResponse;
import com.pearson.ed.lp.stub.api.OrderLifeCycleClient;

/**
 * Mock implementation of OrderLifeCycleClient interface. Wraps another mock instance generated by a mock library like
 * EasyMock. This is to support Spring Integration configuration wiring.
 * 
 * @author ULLOYNI
 * 
 */
public class MockOrderLifeCycleClient implements OrderLifeCycleClient {

	private OrderLifeCycleClient mockClient;

	@Override
	public OrderLineItemsResponse getOrderedISBNsByOrderLineItemIds(OrderLineItemsRequest request)
			throws AbstractRumbaException {
		return mockClient.getOrderedISBNsByOrderLineItemIds(request);
	}

	public OrderLifeCycleClient getMockClient() {
		return mockClient;
	}

	public void setMockClient(OrderLifeCycleClient mockClient) {
		this.mockClient = mockClient;
	}

}
