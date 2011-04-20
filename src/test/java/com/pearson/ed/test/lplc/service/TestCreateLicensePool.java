package com.pearson.ed.test.lplc.service;

import java.util.Set;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.test.lplc.common.BaseIntegrationTest;

@RunWith(JUnit4ClassRunner.class)
public class TestCreateLicensePool extends BaseIntegrationTest {

	@Test
	public void testCreateLicensePool() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		String licensepoolId = licensepoolService.createLicensePool(licensepool);
		LicensePoolMapping findLicensePool = licensepoolDAO.findByLicensePoolId(licensepoolId);
		assertNotNull(findLicensePool);
	}

	@Test
	public void testCreateLicensePoolForOrganization() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		String licensepoolId = licensepoolService.createLicensePool(licensepool);
		LicensePoolMapping findLicensePool = licensepoolDAO.findByLicensePoolId(licensepoolId);
		Set<OrganizationLPMapping> organizations = findLicensePool.getOrganizations();
		assertEquals(7, organizations.size());
	}

}
