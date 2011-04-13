package com.pearson.ed.lp.stub.mock;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;
import com.pearson.ed.lp.stub.api.LicensePoolLifeCycleClient;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

/**
 * Simple mock implementation of LicensePoolLifeCycleClient interface that provides
 * simple validation of message passing behavior.
 * 
 * @author ULLOYNI
 *
 */
public class MockLicensePoolLifeCycleClient implements LicensePoolLifeCycleClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(MockLicensePoolLifeCycleClient.class);

	@Resource
	private List<Class> hitClasses;

	public List<Class> getHitClasses() {
		return hitClasses;
	}

	public void setHitClasses(List<Class> hitClasses) {
		this.hitClasses = hitClasses;
	}

	@Override
	public List<OrganizationLPMapping> getLicensePoolsByOrganizationId(
			LicensePoolByOrganizationIdRequest request) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received LicensePoolByOrganizationIdRequest message");
		}
		hitClasses.add(this.getClass());
		return new ArrayList<OrganizationLPMapping>();
	}

}
