package com.pearson.ed.lp.stub.mock;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.OrderLineItemsResponse;
import com.pearson.ed.lp.stub.api.OrderLifeCycleClient;

public class MockOrderLifeCycleClient implements OrderLifeCycleClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MockOrderLifeCycleClient.class);

	@Resource
	private List<Class> hitClasses;

	public List<Class> getHitClasses() {
		return hitClasses;
	}

	public void setHitClasses(List<Class> hitClasses) {
		this.hitClasses = hitClasses;
	}

	@Override
	public OrderLineItemsResponse getOrderedISBNsByOrderLineItemIds(OrderLineItemsRequest request) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received OrderLineItemRequest message");
		}
		hitClasses.add(this.getClass());
		return new OrderLineItemsResponse();
	}

}
