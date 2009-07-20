package com.pearson.ed.test.lplc.service;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.matchers.Each;
import org.junit.runner.RunWith;

import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dao.api.OrganizationLPDAO;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.test.lplc.common.BaseIntegrationTest;



@RunWith(JUnit4ClassRunner.class)
public class TestListLicensePool extends BaseIntegrationTest {
	
    @Test
	public void testListLicensePool() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
    	OrganizationLPDAO organizationLPDAO = loadOrganizationLPDAO();
    	String licensepoolId = licensepoolService
			.createLicensePool(licensepool);

    	List<OrganizationLPMapping> lpList = organizationLPDAO.listOrganizationMappingByOrganizationId("UnitTestOrganizationID",999);
    	assertEquals(lpList.size(), 1);
	}
    
    @Test
	public void testGetLicensePoolToSubscribe() {
		LicensePoolService licensepoolService = loadLicensePoolService();
		LicensePoolDTO licensepool = loadLicensePool();
		LicensePoolDTO licensepool2 = loadLicensePool2();
    	LicensePoolDAO licensepoolDAO = loadLicensePoolDAO();
    	String licensepoolId = licensepoolService
			.createLicensePool(licensepool);
    	String licensepoolId2 = licensepoolService
		.createLicensePool(licensepool2);

    	List<LicensePoolMapping> lpList = licensepoolDAO.findOrganizationMappingToSubscribe("UnitTestOrganizationID","TestProductId1");
    	assertEquals(licensepoolId2,lpList.get(0).getLicensepoolId());
	}
    
   	public LicensePoolService loadLicensePoolService() {
		return (LicensePoolService) applicationContext
				.getBean("licensepoolService");
	}

	public LicensePoolDTO loadLicensePool() {
		return (LicensePoolDTO) applicationContext
				.getBean("serviceTestLicensePool");
	}
	public LicensePoolDTO loadLicensePool2() {
		return (LicensePoolDTO) applicationContext
				.getBean("serviceTestLicensePool2");
	}

	public LicensePoolDAO loadLicensePoolDAO() {
		return (LicensePoolDAO) applicationContext.getBean("licensepoolDAO");
	}
	public OrganizationLPDAO loadOrganizationLPDAO() {
		return (OrganizationLPDAO) applicationContext.getBean("organizationLPDAO");
	}
}
