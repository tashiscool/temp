package com.pearson.ed.lp.stub.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;
import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.stub.api.LicensePoolLifeCycleClient;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;

/**
 * LicensePoolLifeCycleClient implementation that directly wraps a live implementation
 * of the LicensePoolService interface for direct communication through LicensePoolLifeCycle service
 * DAOs.
 * 
 * This offers optimization over external service calls since GetLicensedProduct shares the same resources.
 * 
 * @author ULLOYNI
 *
 */
public class LicensePoolServiceWrapper implements LicensePoolLifeCycleClient {
	
	@Autowired(required = true)
	private LicensePoolService licensePoolService;

	public LicensePoolResponse getLicensePoolsByOrganizationId(LicensePoolByOrganizationIdRequest request) {
		List<OrganizationLPMapping> licensePools = licensePoolService.getLicensePoolByOrganizationId(
				request.getOrganizationId(), 
				request.getQualifyingLicensePool());
		return new LicensePoolResponse(licensePools);
	}

	public LicensePoolService getLicensePoolService() {
		return licensePoolService;
	}

	public void setLicensePoolService(LicensePoolService licensePoolService) {
		this.licensePoolService = licensePoolService;
	}

}
