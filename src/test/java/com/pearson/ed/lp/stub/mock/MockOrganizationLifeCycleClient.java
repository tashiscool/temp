package com.pearson.ed.lp.stub.mock;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.ed.lp.stub.api.OrganizationLifeCycleClient;

public class MockOrganizationLifeCycleClient implements OrganizationLifeCycleClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MockOrganizationLifeCycleClient.class);

	@Resource
	private List<Class> hitClasses;

	public List<Class> getHitClasses() {
		return hitClasses;
	}

	public void setHitClasses(List<Class> hitClasses) {
		this.hitClasses = hitClasses;
	}

	@Override
	public OrganizationDisplayNamesResponse getParentTreeDisplayNamesByOrganizationId(String organizationId) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("getParentTreeDisplayNamesByOrganizationId: Received organizationId message");
		}
		hitClasses.add(this.getClass());
		return new OrganizationDisplayNamesResponse();
	}

	@Override
	public OrganizationDisplayNamesResponse getChildTreeDisplayNamesByOrganizationId(String organizationId) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("getChildTreeDisplayNamesByOrganizationId: Received organizationId message");
		}
		hitClasses.add(this.getClass());
		return new OrganizationDisplayNamesResponse();
	}

	@Override
	public OrganizationDisplayNamesResponse getOrganizationDisplayName(String organizationId) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("getOrganizationDisplayName: Received organizationId message");
		}
		hitClasses.add(this.getClass());
		return new OrganizationDisplayNamesResponse();
	}

}
