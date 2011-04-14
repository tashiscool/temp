package com.pearson.ed.lp.stub.mock;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pearson.ed.common.exception.RUMBABaseException;
import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;
import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.stub.api.LicensePoolLifeCycleClient;

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
	public LicensePoolResponse getLicensePoolsByOrganizationId(
			LicensePoolByOrganizationIdRequest request) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received LicensePoolByOrganizationIdRequest message");
		}
		hitClasses.add(this.getClass());
		if(true)
			throw new RUMBABaseException("just a test");
		return new LicensePoolResponse();
	}

}
