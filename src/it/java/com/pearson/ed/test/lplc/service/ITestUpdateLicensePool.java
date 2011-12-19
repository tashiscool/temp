package com.pearson.ed.test.lplc.service;

import java.util.Date;
import java.util.Set;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.dto.UpdateLicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.test.lplc.common.BaseIntegrationTest;

@RunWith(JUnit4ClassRunner.class)
public class ITestUpdateLicensePool extends BaseIntegrationTest {

	@Test
	public void testUpdateLicensePool() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		String licensepoolId = licensepoolService.createLicensePool(licensepool);
		UpdateLicensePoolDTO updateDTO = new UpdateLicensePoolDTO();
		updateDTO.setLicensepoolId(licensepoolId);
		updateDTO.setQuantity(500);
		String updateLicensePool = licensepoolService.updateLicensePool(updateDTO);
		LicensePoolMapping findByLicensePoolId = licensepoolDAO.findByLicensePoolId(updateLicensePool);
		assertEquals(500, findByLicensePoolId.getQuantity());
	}

	@Test
	public void testUpdateLicensePoolForOrderLineItem() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		String licensepoolId = licensepoolService.createLicensePool(licensepool);
		UpdateLicensePoolDTO updateDTO = new UpdateLicensePoolDTO();
		updateDTO.setLicensepoolId(licensepoolId);
		updateDTO.setQuantity(500);
		updateDTO.setOrderLineItem("TestOrderLineItem2");
		String updateLicensePool = licensepoolService.updateLicensePool(updateDTO);
		LicensePoolMapping findByLicensePoolId = licensepoolDAO.findByLicensePoolId(updateLicensePool);
		assertEquals(500, findByLicensePoolId.getQuantity());
		assertEquals(2, findByLicensePoolId.getOrderLineItems().size());
	}

	@Test
	public void testUpdateLicensePoolForStartDateEndDate() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		String licensepoolId = licensepoolService.createLicensePool(licensepool);
		UpdateLicensePoolDTO updateDTO = new UpdateLicensePoolDTO();
		updateDTO.setLicensepoolId(licensepoolId);
		updateDTO.setStartDate(new Date());
		try {
			licensepoolService.updateLicensePool(updateDTO);
		} catch (Exception e) {
			assertEquals("Start Date can not be greater than End Date", e.getMessage());
		}
	}

	@Test
	public void testUpdateLicensePoolForConsumption() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		String licensepoolId = licensepoolService.createLicensePool(licensepool);
		UpdateLicensePoolDTO updateDTO = new UpdateLicensePoolDTO();
		updateDTO.setLicensepoolId(licensepoolId);
		updateDTO.setUsedLicenses(0);
		updateDTO.setOrganizationId("UnitTestOrganizationID");
		licensepoolService.updateLicensePool(updateDTO);
		LicensePoolMapping findByLicensePoolId = licensepoolDAO.findByLicensePoolId(licensepoolId);
		Set<OrganizationLPMapping> organizations = findByLicensePoolId.getOrganizations();
		for (OrganizationLPMapping organizationLPMapping : organizations) {
			assertEquals(0, organizationLPMapping.getUsed_quantity());

		}
	}

	@Test
	public void testUpdateLicensePoolForCancel() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
		String licensepoolId = licensepoolService.createLicensePool(licensepool);
		String createdBy = getCreatedBy();
		UpdateLicensePoolDTO updateDTO = new UpdateLicensePoolDTO();
		updateDTO.setLicensepoolId(licensepoolId);
		String updateLicensePool = licensepoolService.cancel(updateDTO.getLicensepoolId(), createdBy,
				LPLCConstants.IS_DENIED_OR_CANCELED_TRUE);
		LicensePoolMapping findByLicensePoolId = licensepoolDAO.findByLicensePoolId(updateLicensePool);
		assertTrue("Created licensepool has not cancelled", findByLicensePoolId.getIsCancelled().equals("Y"));
	}

}
