package com.pearson.ed.lp.stub.api;

import java.util.List;

import org.springframework.integration.annotation.ServiceActivator;

import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

public interface LicensePoolLifeCycleClient {

	@ServiceActivator
	List<OrganizationLPMapping> getLicensePoolsByOrganizationId(
			LicensePoolByOrganizationIdRequest request);

}
