package com.pearson.ed.lp.stub.api;

import org.springframework.integration.annotation.ServiceActivator;

import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;
import com.pearson.ed.lp.message.LicensePoolResponse;

/**
 * Client stub API for the LicensePoolLifeCycle service.
 * 
 * @author ULLOYNI
 *
 */
public interface LicensePoolLifeCycleClient {

	@ServiceActivator
	LicensePoolResponse getLicensePoolsByOrganizationId(
			LicensePoolByOrganizationIdRequest request);

}
