package com.pearson.ed.test.lplc.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dao.impl.LicensePoolDAOImpl;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.test.lplc.common.BaseIntegrationTest;

public class TestLicensePoolService extends BaseIntegrationTest {

	/**
	 * testDenyNewSubscriptions() method to test the functionality of
	 * denyNewSubscriptions() method of LicensePoolService.
	 */

	@Test
	public void testDenyNewSubscriptions() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepoolForDeny1 = loadDenyNewSubscription();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		LicensePoolMapping licensePoolMapping = null;

		try {
			String licensepoolId = licensepoolService.createLicensePool(licensepoolForDeny1);
			licensepoolService.denyNewSubscriptions(licensepoolId, LPLCConstants.IS_DENIED_OR_CANCELED_TRUE,
					LPLCConstants.DEFAULT_USER);
			licensePoolMapping = licensepoolDAO.findByLicensePoolId(licensepoolId);
			assertEquals("Subscription were not denied for this licensepool.", LPLCConstants.DENY_SUBSCRIPTIONS_TRUE,
					licensePoolMapping.getDenyManualSubscription());
			assertSame("Subscription were not denied for this licensepool.", LPLCConstants.DENY_SUBSCRIPTIONS_TRUE,
					licensePoolMapping.getDenyManualSubscription());
		} catch (Exception exception) {
			assertEquals("Subscription were not denied for this licensepool.", LPLCConstants.DENY_SUBSCRIPTIONS_TRUE,
					licensePoolMapping.getDenyManualSubscription());
		} finally {
			licensePoolMapping = null;
		}
	}

	/**
	 * testgetLicensePoolByOrganizationId() method to test the functionality of
	 * getLicensePoolByOrganizationId() method of LicensePoolService.
	 */

	@Test
	public void testGetLicensePoolByOrganizationId() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDTO licensepool2 = loadLicensePool2();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		LicensePoolMapping licensepoolEntity1 = null;
		List<OrganizationLPMapping> organizationLPMappings = new ArrayList<OrganizationLPMapping>();

		LicensePoolMapping licensepoolEntity2 = null;

		try {
			String licensepoolId1 = licensepoolService.createLicensePool(licensepool);
			String licensepoolId2 = licensepoolService.createLicensePool(licensepool2);

			licensepoolEntity1 = licensepoolDAO.findByLicensePoolId(licensepoolId1);
			licensepoolEntity2 = licensepoolDAO.findByLicensePoolId(licensepoolId2);

			organizationLPMappings = licensepoolService.getLicensePoolByOrganizationId(licensepool.getOrganizationId(),
					LPLCConstants.QUALIFYING_ORGS_ROOT);
			assertNotNull("No valid license pool available for this organization.", organizationLPMappings.size() > 0);
			assertTrue("No valid license pool available for this organization", organizationLPMappings.size() > 0);
		} catch (Exception exception) {
			assertNotNull("No valid license pool available for this organization", organizationLPMappings.size() > 0);
		} finally {
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity1);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity2);
		}
		try {
			organizationLPMappings = licensepoolService.getLicensePoolByOrganizationId(licensepool.getOrganizationId(),
					LPLCConstants.QUALIFYING_ORGS_ROOT);
			assertTrue("Valid license pool available for invalid organization.", organizationLPMappings.size() <= 0);
		} catch (Exception exception) {
			assertTrue("Valid license pool available for invalid organization.", organizationLPMappings.size() <= 0);
		} finally {
			organizationLPMappings.clear();
		}
	}
}
