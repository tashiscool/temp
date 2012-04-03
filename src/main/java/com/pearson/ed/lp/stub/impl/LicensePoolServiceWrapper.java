package com.pearson.ed.lp.stub.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.InvalidOrganizationException;
import com.pearson.ed.lp.exception.LicensedProductExceptionFactory;
import com.pearson.ed.lp.exception.LicensedProductExceptionMessageCode;
import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;
import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.stub.api.LicensePoolLifeCycleClient;
import com.pearson.ed.lplc.exception.OrganizationNotValidException;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;

/**
 * LicensePoolLifeCycleClient implementation that directly wraps a live implementation of the LicensePoolService
 * interface for direct communication through LicensePoolLifeCycle service DAOs.
 * 
 * This offers optimization over external service calls since GetLicensedProduct shares the same resources.
 * 
 * @author ULLOYNI
 * 
 */
public class LicensePoolServiceWrapper implements LicensePoolLifeCycleClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LicensePoolServiceWrapper.class);

	@Autowired(required = true)
	private LicensePoolService licensePoolService;

	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;

	/**
	 * Implementation of 
	 * {@link LicensePoolLifeCycleClient#getLicensePoolsByOrganizationId(LicensePoolByOrganizationIdRequest)}
	 * which wraps the internal {@link LicensePoolService} instance which in turn wraps LicensePoolLifeCycle DAOs.
	 * 
	 * @param request {@link LicensePoolByOrganizationIdRequest}
	 * @return {@link LicensePoolResponse}
	 */
	public LicensePoolResponse getLicensePoolsByOrganizationId(LicensePoolByOrganizationIdRequest request) {
		List<OrganizationLPMapping> licensePools;
		
		try {
			licensePools = licensePoolService.getLicensePoolByOrganizationId(
					request.getOrganizationId(), request.getQualifyingLicensePool());
		} catch (OrganizationNotValidException e) {
			throw new InvalidOrganizationException(
					exceptionFactory.findExceptionMessage(
							LicensedProductExceptionMessageCode.LP_EXC_0002),
					new Object[]{request.getOrganizationId()}, e);
		} catch (Exception e) {
			LOGGER.error("Unexpected exception thrown by wrapped LicensePoolService instance: " + e.getMessage());
			throw new ExternalServiceCallException(
					exceptionFactory.findExceptionMessage(LicensedProductExceptionMessageCode.LP_EXC_0001), 
					null, 
					e);
		}
		
		return new LicensePoolResponse(licensePools);
	}

	public LicensePoolService getLicensePoolService() {
		return licensePoolService;
	}

	public void setLicensePoolService(LicensePoolService licensePoolService) {
		this.licensePoolService = licensePoolService;
	}

	public LicensedProductExceptionFactory getExceptionFactory() {
		return exceptionFactory;
	}

	public void setExceptionFactory(LicensedProductExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

}
