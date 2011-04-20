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

	/**
	 * Get request service activator to call the GetLicensePoolsByOrganizationId service function
	 * of the LicensePoolLifeCycle service.
	 * 
	 * @param request {@link LicensePoolByOrganizationIdRequest}
	 * @return {@link LicensePoolResponse}
	 */
	@ServiceActivator
	LicensePoolResponse getLicensePoolsByOrganizationId(LicensePoolByOrganizationIdRequest request);

}
