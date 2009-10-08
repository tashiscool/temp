package com.pearson.ed.test.lplc.service;

import org.junit.Test;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.exception.NewSubscriptionsDeniedException;
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
			licensepoolService.denyNewSubscriptions(licensepoolId, LPLCConstants.DEFAULT_USER);
			licensePoolMapping = licensepoolDAO.findByLicensePoolId(licensepoolId);
			assertTrue("Subscription were not denied for this licensepool.", licensepoolForDeny1
					.getDenyManualSubscription() != licensePoolMapping.getDenyManualSubscription());
			assertNotSame("Subscription were not denied for this licensepool.", licensepoolForDeny1
					.getDenyManualSubscription(), licensePoolMapping.getDenyManualSubscription());
		} catch (Exception exception) {
			assertTrue("Subscription were not denied for this licensepool.", licensepoolForDeny1
					.getDenyManualSubscription() != licensePoolMapping.getDenyManualSubscription());
		} finally {
			licensePoolMapping = null;
		}

		try {
			LicensePoolDTO licensepoolForDeny2 = loadDenyNewSubscription();
			licensepoolForDeny2.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_TRUE);
			String licensepoolId = licensepoolService.createLicensePool(licensepoolForDeny2);
			licensepoolService.denyNewSubscriptions(licensepoolId, LPLCConstants.DEFAULT_USER);
			licensePoolMapping = licensepoolDAO.findByLicensePoolId(licensepoolId);
		} catch (NewSubscriptionsDeniedException newSubscriptionsDeniedException) {
			assertNotNull("New subscriptions are already denied for this licensepool", newSubscriptionsDeniedException);
		} finally {
			licensePoolMapping = null;
		}
	}

}
