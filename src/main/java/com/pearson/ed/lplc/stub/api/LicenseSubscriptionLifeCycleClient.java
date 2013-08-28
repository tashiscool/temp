package com.pearson.ed.lplc.stub.api;

import org.springframework.integration.annotation.ServiceActivator;

import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolDetailsByIdRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolDetailsByIdResponse;

public interface LicenseSubscriptionLifeCycleClient {

	@ServiceActivator
	public GetLicensePoolDetailsByIdResponse getLicensePoolById(
			GetLicensePoolDetailsByIdRequest request);
}
