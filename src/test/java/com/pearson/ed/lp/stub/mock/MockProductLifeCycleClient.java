package com.pearson.ed.lp.stub.mock;

import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lp.stub.api.ProductLifeCycleClient;

/**
 * Mock implementation of ProductLifeCycleClient interface.
 * Wraps another mock instance generated by a mock library like EasyMock.
 * This is to support Spring Integration configuration wiring.
 * 
 * @author ULLOYNI
 *
 */
public class MockProductLifeCycleClient implements ProductLifeCycleClient {

	private ProductLifeCycleClient mockClient;

	@Override
	public ProductEntityIdsResponse getDisplayNamesByProductEntityIds(ProductEntityIdsRequest request) {
		return mockClient.getDisplayNamesByProductEntityIds(request);
	}

	public ProductLifeCycleClient getMockClient() {
		return mockClient;
	}

	public void setMockClient(ProductLifeCycleClient mockClient) {
		this.mockClient = mockClient;
	}

}