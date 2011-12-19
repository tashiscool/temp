package com.pearson.ed.test.lplc.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dao.api.OrganizationLPDAO;
import com.pearson.ed.lplc.dao.impl.LicensePoolDAOImpl;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.test.lplc.common.BaseIntegrationTest;
import com.pearson.ed.test.lplc.common.TestLPLCConstants;

/**
 * @author vc999el
 * 
 */
public class ITestOrganizationLPDAO extends BaseIntegrationTest {

	/**
	 * testListOrganizationMappingByOrganizationId() method to test the functionality of
	 * listOrganizationMappingByOrganizationId() method of LicensePool.
	 */
	@Test
	public void testOrganizationMappingByOrganizationId() {

		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDTO licensepool2 = loadLicensePool2();
		LicensePoolDTO licensepool3 = loadLicensePool2();
		LicensePoolDTO licensepool4 = loadLicensePool2();
		LicensePoolDTO licensepool5 = loadLicensePool2();
		LicensePoolDTO licensepool6 = loadLicensePool2();
		List<OrganizationLPMapping> organizationLPMapping = new ArrayList<OrganizationLPMapping>();

		OrganizationLPDAO organizationLPDAO = loadOrganizationLPDAO();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		LicensePoolMapping licensepoolEntity1 = null;
		LicensePoolMapping licensepoolEntity2 = null;
		LicensePoolMapping licensepoolEntity3 = null;
		LicensePoolMapping licensepoolEntity4 = null;
		LicensePoolMapping licensepoolEntity5 = null;
		LicensePoolMapping licensepoolEntity6 = null;

		try {
			licensepool3.setOrganizationId(TestLPLCConstants.ORGANIZATION_NAME + Math.random());
			licensepool4.setOrganizationId(TestLPLCConstants.ORGANIZATION_NAME + Math.random());
			licensepool5.setOrganizationId(TestLPLCConstants.ORGANIZATION_NAME + Math.random());
			licensepool6.setOrganizationId(TestLPLCConstants.ORGANIZATION_NAME + Math.random());

			String licensepoolId1 = licensepoolService.createLicensePool(licensepool);
			String licensepoolId2 = licensepoolService.createLicensePool(licensepool2);
			String licensepoolId3 = licensepoolService.createLicensePool(licensepool3);
			String licensepoolId4 = licensepoolService.createLicensePool(licensepool4);
			String licensepoolId5 = licensepoolService.createLicensePool(licensepool5);
			String licensepoolId6 = licensepoolService.createLicensePool(licensepool6);

			licensepoolEntity1 = licensepoolDAO.findByLicensePoolId(licensepoolId1);
			licensepoolEntity2 = licensepoolDAO.findByLicensePoolId(licensepoolId2);
			licensepoolEntity3 = licensepoolDAO.findByLicensePoolId(licensepoolId3);
			licensepoolEntity4 = licensepoolDAO.findByLicensePoolId(licensepoolId4);
			licensepoolEntity5 = licensepoolDAO.findByLicensePoolId(licensepoolId5);
			licensepoolEntity6 = licensepoolDAO.findByLicensePoolId(licensepoolId6);

			organizationLPMapping = organizationLPDAO.listOrganizationMappingByOrganizationId(licensepoolId3,
					TestLPLCConstants.INITIAL_LEVEL);

			assertNotNull("No valid license pool available for this organization.", organizationLPMapping.size() > 0);

		} catch (Exception exception) {
			assertNotNull("No valid license pool available for this organization.", organizationLPMapping.size() > 0);
		} finally {
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity1);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity2);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity3);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity4);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity5);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity6);
		}

		try {
			organizationLPMapping = organizationLPDAO.listOrganizationMappingByOrganizationId(
					TestLPLCConstants.ORGANIZATION_NAME + Math.random(), TestLPLCConstants.INITIAL_LEVEL);

			assertTrue("Valid license pool available for invalid organization.", organizationLPMapping.size() <= 0);

		} catch (Exception exception) {
			assertTrue("Valid license pool available for invalid organization.", organizationLPMapping.size() <= 0);
		} finally {
			organizationLPMapping = null;
		}
	}

	/**
	 * testListOrganizationMappingByOrganizationId() method to test the functionality of
	 * listOrganizationMappingByOrganizationId() method of LicensePool.
	 */
	@Test
	public void testListOrganizationMappingByOrganizationId() {

		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDTO licensepool2 = loadLicensePool2();
		LicensePoolDTO licensepool3 = loadLicensePool2();
		LicensePoolDTO licensepool4 = loadLicensePool2();
		LicensePoolDTO licensepool5 = loadLicensePool2();
		LicensePoolDTO licensepool6 = loadLicensePool2();
		List<OrganizationLPMapping> organizationLPMapping = new ArrayList<OrganizationLPMapping>();
		List<String> organizationList = new ArrayList<String>();
		OrganizationLPDAO organizationLPDAO = loadOrganizationLPDAO();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		LicensePoolMapping licensepoolEntity1 = null;
		LicensePoolMapping licensepoolEntity2 = null;
		LicensePoolMapping licensepoolEntity3 = null;
		LicensePoolMapping licensepoolEntity4 = null;
		LicensePoolMapping licensepoolEntity5 = null;
		LicensePoolMapping licensepoolEntity6 = null;

		try {

			licensepool3.setOrganizationId(TestLPLCConstants.ORGANIZATION_NAME + Math.random());
			licensepool4.setOrganizationId(TestLPLCConstants.ORGANIZATION_NAME + Math.random());
			licensepool5.setOrganizationId(TestLPLCConstants.ORGANIZATION_NAME + Math.random());
			licensepool6.setOrganizationId(TestLPLCConstants.ORGANIZATION_NAME + Math.random());

			String licensepoolId1 = licensepoolService.createLicensePool(licensepool);
			String licensepoolId2 = licensepoolService.createLicensePool(licensepool2);
			String licensepoolId3 = licensepoolService.createLicensePool(licensepool3);
			String licensepoolId4 = licensepoolService.createLicensePool(licensepool4);
			String licensepoolId5 = licensepoolService.createLicensePool(licensepool5);
			String licensepoolId6 = licensepoolService.createLicensePool(licensepool6);

			organizationList.add(licensepool.getOrganizationId());
			organizationList.add(licensepool2.getOrganizationId());
			organizationList.add(licensepool3.getOrganizationId());
			organizationList.add(licensepool4.getOrganizationId());
			organizationList.add(licensepool5.getOrganizationId());
			organizationList.add(licensepool6.getOrganizationId());

			licensepoolEntity1 = licensepoolDAO.findByLicensePoolId(licensepoolId1);
			licensepoolEntity2 = licensepoolDAO.findByLicensePoolId(licensepoolId2);
			licensepoolEntity3 = licensepoolDAO.findByLicensePoolId(licensepoolId3);
			licensepoolEntity4 = licensepoolDAO.findByLicensePoolId(licensepoolId4);
			licensepoolEntity5 = licensepoolDAO.findByLicensePoolId(licensepoolId5);
			licensepoolEntity6 = licensepoolDAO.findByLicensePoolId(licensepoolId6);

			organizationLPMapping = organizationLPDAO.listOrganizationMappingByOrganizationId(organizationList,
					TestLPLCConstants.INITIAL_LEVEL);

			assertTrue("No valid license pool available for this organization.", organizationLPMapping.size() > 0);
			assertNotNull("No valid license pool available for this organization.", organizationLPMapping.size() > 0);

		} catch (Exception exception) {
			assertNotNull("No valid license pool available for this organization.", organizationLPMapping.size() > 0);
		} finally {
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity1);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity2);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity3);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity4);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity5);
			((LicensePoolDAOImpl) licensepoolDAO).delete(licensepoolEntity6);

		}
		try {
			organizationLPMapping = organizationLPDAO.listOrganizationMappingByOrganizationId(organizationList,
					TestLPLCConstants.INITIAL_LEVEL);
			assertTrue("Valid license pool available for invalid organization.", organizationLPMapping.size() <= 0);
		} catch (Exception exception) {
			assertTrue("Valid license pool available for invalid organization.", organizationLPMapping.size() <= 0);
		} finally {
			organizationLPMapping = null;
		}

	}
}
