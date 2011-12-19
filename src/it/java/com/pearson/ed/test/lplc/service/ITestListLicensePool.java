package com.pearson.ed.test.lplc.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dao.api.OrganizationLPDAO;
import com.pearson.ed.lplc.dao.impl.LicensePoolDAOImpl;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.exception.LicensePoolExpiredException;
import com.pearson.ed.lplc.exception.LicensePoolUnavailableException;
import com.pearson.ed.lplc.exception.NewSubscriptionsDeniedException;
import com.pearson.ed.lplc.exception.RequiredObjectNotFound;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.rws.licensepool.doc._2009._04._01.LicensePoolDetails;
import com.pearson.ed.test.lplc.common.BaseIntegrationTest;

@RunWith(JUnit4ClassRunner.class)
public class ITestListLicensePool extends BaseIntegrationTest {

	@Test
	public void testListLicensePool() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		OrganizationLPDAO organizationLPDAO = loadOrganizationLPDAO();
		String licensepoolId = licensepoolService.createLicensePool(licensepool);

		List<OrganizationLPMapping> lpList = organizationLPDAO.listOrganizationMappingByOrganizationId(
				"UnitTestOrganizationID", 999);
		assertEquals(lpList.size(), 1);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetLicensePoolToSubscribeForSuccess() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();

		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDTO licensepool2 = loadLicensePool2();
		Date currentDate = new Date();
		String orgId = licensepool.getOrganizationId();
		String productId = licensepool.getProductId();

		// create stub data
		String licensepoolId1 = licensepoolService.createLicensePool(licensepool);
		String licensepoolId2 = licensepoolService.createLicensePool(licensepool2);

		// Fetch created license pools

		LicensePoolMapping licensepoolEntity1 = licensepoolDAO.findByLicensePoolId(licensepoolId1);
		LicensePoolMapping licensepoolEntity2 = licensepoolDAO.findByLicensePoolId(licensepoolId2);

		// Check different license pools for the same org and product with different start/end dates

		List<LicensePoolMapping> lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId,
				currentDate, true);
		assertEquals("Did not fetch both the qualifying licensepools", 2, lpList.size());
		assertEquals("Did not fetch the latest license pool available", licensepoolId2, lpList.get(0)
				.getLicensepoolId());

		// Check passing a date out of bound of the license pool's start and end dates

		lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, new Date("1/1/1800"), true);

		assertEquals("Returned results when the given date is out of bound of license pool's start and end dates", 0,
				lpList.size());

		// Deny new subscriptions on the latest license pool for the org and product
		licensepoolEntity2.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_TRUE);
		lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, true);
		assertEquals(1, lpList.size());
		assertEquals("Did not fetch the latest license pool available", licensepoolId1, lpList.get(0)
				.getLicensepoolId());

		// Deny new subscriptions on both license pools, there should be no more qualifying license pools
		licensepoolEntity1.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_TRUE);
		lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, true);
		assertEquals("Fetched license pools with Denied subscriptions set for license pool", 0, lpList.size());

	}

	@Test
	public void testGetLicensePoolToSubscribeForLPWithDeniedSubscriptions() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		List<LicensePoolMapping> lpList = null;
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDTO licensepool2 = loadLicensePool2();
		LicensePoolMapping licensepoolEntity1 = null;
		LicensePoolMapping licensepoolEntity2 = null;

		Date currentDate = new Date();
		String orgId = licensepool.getOrganizationId();
		String productId = licensepool.getProductId();
		try {
			// Create stub data
			String licensepoolId1 = licensepoolService.createLicensePool(licensepool);
			String licensepoolId2 = licensepoolService.createLicensePool(licensepool2);

			// Fetch created license pools

			licensepoolEntity1 = licensepoolDAO.findByLicensePoolId(licensepoolId1);
			licensepoolEntity2 = licensepoolDAO.findByLicensePoolId(licensepoolId2);

			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, true);
			assertEquals("Did not fetch both the qualifying licensepools", 2, lpList.size());
			// LicensePool 2 is the qualifying License pool
			assertEquals("Did not fetch the latest license pool available", licensepoolId2, lpList.get(0)
					.getLicensepoolId());

			licensepoolEntity2.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_TRUE);

			// LicensePool 1 becomes the qualifying license pool since licensePool 2 has New Subscriptions denied to it.
			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, true);
			assertEquals(1, lpList.size());
			assertEquals("Did not fetch the latest license pool available", licensepoolId1, lpList.get(0)
					.getLicensepoolId());

			licensepoolEntity1.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_TRUE);

			// Both qualifying license pools have deny subscriptions set to true
			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, true);
			assertEquals("Fetched license pools with Denied subscriptions set for license pool", 0, lpList.size());

			// Service should raise a NewSubscriptionsDeniedException when both licenses have "DenyNewSubscriptions" set
			try {
				licensepoolService.getLicensePoolToSubscribeId(orgId, productId);
			} catch (NewSubscriptionsDeniedException subscriptionsDeniedException) {
				assertTrue("NewSubscriptionsDeniedException is not raised when all licensepools have "
						+ "denyNewSubscriptions set to true", true);
			}
			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, false);
			assertEquals("Did not fetch license pools with denied subscriptions set for license pool", 2, lpList.size());

			// Check when Subscriptions are denied at Organization level
			licensepoolEntity1.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_FALSE);
			licensepoolEntity2.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_FALSE);

			Set<OrganizationLPMapping> orgLPMapsForLP1 = licensepoolEntity1.getOrganizations();
			for (OrganizationLPMapping orgLPMapForLP1 : orgLPMapsForLP1) {
				if (orgLPMapForLP1.getOrganization_id().equals(orgId)) {
					orgLPMapForLP1.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_TRUE);
				}
			}

			Set<OrganizationLPMapping> orgLPMapsForLP2 = licensepoolEntity2.getOrganizations();
			for (OrganizationLPMapping orgLPMapForLP2 : orgLPMapsForLP2) {
				if (orgLPMapForLP2.getOrganization_id().equals(orgId)) {
					orgLPMapForLP2.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_TRUE);
				}
			}
			// Both qualifying license pools have deny subscriptions set to true
			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, true);
			assertEquals("Fetched license pools with Denied subscriptions set for license pool", 0, lpList.size());

			// Service should raise a NewSubscriptionsDeniedException when both licenses have "DenyNewSubscriptions" set
			try {
				licensepoolService.getLicensePoolToSubscribeId(orgId, productId);
			} catch (NewSubscriptionsDeniedException subscriptionsDeniedException) {
				assertTrue("NewSubscriptionsDeniedException is not raised when all licensepools have "
						+ "denyNewSubscriptions set to true", true);
			}

		} catch (Exception ex) {
			fail("testGetLicensePoolToSubscribeForException1 failed " + ex.getMessage());
		} finally {
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity1);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity2);
		}

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetLicensePoolToSubscribeForLPWithDates() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		List<LicensePoolMapping> lpList = null;
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDTO licensepool2 = loadLicensePool2();
		LicensePoolMapping licensepoolEntity1 = null;
		LicensePoolMapping licensepoolEntity2 = null;

		Date currentDate = new Date();
		String orgId = licensepool.getOrganizationId();
		String productId = licensepool.getProductId();
		try {
			// Create stub data
			String licensepoolId1 = licensepoolService.createLicensePool(licensepool);
			String licensepoolId2 = licensepoolService.createLicensePool(licensepool2);

			// Fetch created license pools

			licensepoolEntity1 = licensepoolDAO.findByLicensePoolId(licensepoolId1);
			licensepoolEntity2 = licensepoolDAO.findByLicensePoolId(licensepoolId2);

			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, true);
			assertEquals("Qualifying license pools are not fetched", 2, lpList.size());
			// LicensePool 2 is the qualifying License pool
			assertEquals("Did not fetch the latest license pool available", licensepoolId2, lpList.get(0)
					.getLicensepoolId());

			// Check passing a date out of bound of the license pool's start and end dates

			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, new Date("1/1/1800"), true);

			assertEquals("Returned results when the given date is out of bound of license pool's start and end dates",
					0, lpList.size());

			// Set the End Dates to a Past date

			licensepoolEntity1.setEnd_date(new Date(currentDate.getYear() - 50));
			licensepoolEntity2.setEnd_date(new Date(currentDate.getYear() - 50));

			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, false);
			assertEquals("Fetched expired and deniedNewSubscriptions license pools", 0, lpList.size());

			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, true);
			assertEquals("Fetched expired and deniedNewSubscriptions license pools", 0, lpList.size());

			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, null, true);
			assertEquals("Did not fetch expired license pools", 2, lpList.size());

			// Service should raise a LicensePoolExpiredException as dates of License pool are out of bound from the
			// current date

			try {
				licensepoolService.getLicensePoolToSubscribeId(orgId, productId);
			} catch (LicensePoolExpiredException lpExpiredException) {
				assertTrue(
						"LicensePoolExpiredException is not raised when all licensepools have denyNewSubscriptions set to "
								+ "true and dates are out of bound", true);
			}

			licensepoolEntity1.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_TRUE);
			licensepoolEntity2.setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_TRUE);

			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, null, false);
			assertEquals("Did not fetch expired license pools", 2, lpList.size());

			// Service should raise a LicensePoolExpiredException when both licenses have "DenyNewSubscriptions" set
			// and dates are out of bound

			try {
				licensepoolService.getLicensePoolToSubscribeId(orgId, productId);
			} catch (LicensePoolExpiredException lpExpiredException) {
				assertTrue(
						"LicensePoolExpiredException is not raised when all licensepools have denyNewSubscriptions set to "
								+ "true and dates are out of bound", true);
			}

			licensepoolEntity1.setStart_date(new Date(currentDate.getYear() + 50));
			licensepoolEntity2.setStart_date(new Date(currentDate.getYear() + 50));
			licensepoolEntity1.setEnd_date(new Date(currentDate.getYear() + 100));
			licensepoolEntity2.setEnd_date(new Date(currentDate.getYear() + 100));

			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, false);
			assertEquals("Fetched licensepools which are not in the date range", 0, lpList.size());

			// Service should raise a LicensePoolUnavailableException as the license pools are configured for the future
			try {
				licensepoolService.getLicensePoolToSubscribeId(orgId, productId);
			} catch (LicensePoolUnavailableException lpExpiredException) {
				assertTrue("LicensePoolUnavailableException is no license pools exist for the given orgId, productId",
						true);
			}

		} catch (Exception ex) {
			fail("testGetLicensePoolToSubscribeForLPWithDates failed " + ex.getMessage());
		} finally {
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity1);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity2);
		}

	}

	@Test
	public void testGetLicensePoolToSubscribeForUnavailableLPs() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		List<LicensePoolMapping> lpList = null;
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDTO licensepool2 = loadLicensePool2();
		LicensePoolMapping licensepoolEntity1 = null;
		LicensePoolMapping licensepoolEntity2 = null;

		Date currentDate = new Date();
		String orgId = licensepool.getOrganizationId();
		String productId = licensepool.getProductId();
		try {
			// Create stub data
			String licensepoolId1 = licensepoolService.createLicensePool(licensepool);
			String licensepoolId2 = licensepoolService.createLicensePool(licensepool2);

			// Fetch created license pools

			licensepoolEntity1 = licensepoolDAO.findByLicensePoolId(licensepoolId1);
			licensepoolEntity2 = licensepoolDAO.findByLicensePoolId(licensepoolId2);

			lpList = licensepoolDAO.findOrganizationMappingToSubscribe(orgId, productId, currentDate, true);
			assertEquals("Qualifying license pools are not fetched", 2, lpList.size());

			// Service should raise a LicensePoolUnavailableException when no matching license pools for
			try {
				licensepoolService.getLicensePoolToSubscribeId(orgId, "non-existent-product-id");
			} catch (LicensePoolUnavailableException lpExpiredException) {
				assertTrue("LicensePoolUnavailableException is no license pools exist for the given orgId, productId",
						true);
			}

			// Service should raise a LicensePoolUnavailableException when no matching license pools for
			try {
				licensepoolService.getLicensePoolToSubscribeId("non-existent-org-id", productId);
			} catch (LicensePoolUnavailableException lpExpiredException) {
				assertTrue("LicensePoolUnavailableException is no license pools exist for the given orgId, productId",
						true);
			}

			// Service should raise a LicensePoolUnavailableException when no matching license pools for
			try {
				licensepoolService.getLicensePoolToSubscribeId("non-existent-org-id", "non-existent-product-id");
			} catch (LicensePoolUnavailableException lpExpiredException) {
				assertTrue("LicensePoolUnavailableException is no license pools exist for the given orgId, productId",
						true);
			}

		} catch (Exception ex) {
			fail("testGetLicensePoolToSubscribeForLPWithDates failed " + ex.getMessage());
		} finally {
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity1);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity2);
		}

	}

	/**
	 * Test to ensure that GetLicensePoolDetailsById fetches the proper requested license pool object.
	 */
	@Test
	public void testGetLicensePoolDetailsById() {
		LicensePoolService licensePoolService = loadLicensePoolService();
		LicensePoolDTO licensePool = loadLicensePool();
		LicensePoolDetails licensePoolDetails = null;
		try {
			String licensePoolId = licensePoolService.createLicensePool(licensePool);
			licensePoolDetails = licensePoolService.getLicensePoolDetailsById(licensePoolId);

			assertNotNull("License pool object was not fetched.", licensePoolDetails);
			assertEquals("Required license pool object was not fetched.", licensePoolDetails.getLicensePoolId(),
					licensePoolId);
		} catch (RequiredObjectNotFound e) {
			assertNull("testGetLicensePoolDetailsById failed ", licensePoolDetails);
		}

		try {
			licensePoolDetails = licensePoolService.getLicensePoolDetailsById("licensePoolId");
			assertNull("License pool object was fetched.", licensePoolDetails);
		} catch (RequiredObjectNotFound e) {
			assertNotNull("testGetLicensePoolDetailsById failed ", licensePoolDetails);
		}

	}

}
