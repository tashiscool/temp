package com.pearson.ed.test.lplc.service;

import org.junit.Test;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
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
			licensepoolService.denyNewSubscriptions(licensepoolId, LPLCConstants.DENY_SUBSCRIPTIONS_TRUE,
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
}
