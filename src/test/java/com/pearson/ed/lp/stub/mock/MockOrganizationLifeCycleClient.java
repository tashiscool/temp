package com.pearson.ed.lp.stub.mock;

import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.ed.lp.stub.api.OrganizationLifeCycleClient;

/**
 * Mock implementation of OrganizationLifeCycleClient interface.
 * Wraps another mock instance generated by a mock library like EasyMock.
 * This is to support Spring Integration configuration wiring.
 * 
 * @author ULLOYNI
 *
 */
public class MockOrganizationLifeCycleClient implements OrganizationLifeCycleClient {
	
	private OrganizationLifeCycleClient mockClient;

	public OrganizationDisplayNamesResponse getParentTreeDisplayNamesByOrganizationId(String organizationId) {
		return mockClient.getParentTreeDisplayNamesByOrganizationId(organizationId);
	}

	public OrganizationDisplayNamesResponse getChildTreeDisplayNamesByOrganizationId(String organizationId) {
		return mockClient.getChildTreeDisplayNamesByOrganizationId(organizationId);
	}

	public OrganizationDisplayNamesResponse getOrganizationDisplayName(String organizationId) {
		return mockClient.getOrganizationDisplayName(organizationId);
	}

	public OrganizationLifeCycleClient getMockClient() {
		return mockClient;
	}

	public void setMockClient(OrganizationLifeCycleClient mockClient) {
		this.mockClient = mockClient;
	}

}