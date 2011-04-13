package com.pearson.ed.lp;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pearson.ed.lp.ws.MarshallingLicensedProductServiceEndpoint;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProduct;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductRequestElement;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductResponseElement;
import com.pearson.rws.licensedproduct.doc.v2.QualifyingLicensePool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-lp-integration.xml",
		"classpath:applicationContext-test-lp-integration-plumbing.xml"
})
public class MockEndToEndGetLicensedProductsServiceTest {
	
	@Autowired(required = true)
	private MarshallingLicensedProductServiceEndpoint serviceEndpoint;
	
	@Resource
	private List<Class> hitClasses;

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		hitClasses.clear();
	}

	@Test
	public void testEndToEndMessagingWithQualifyingLicensePoolRootOnly() {
		GetLicensedProductRequestElement request = new GetLicensedProductRequestElement();
		request.setGetLicensedProduct(new GetLicensedProduct());
		request.getGetLicensedProduct().setOrganizationId("dummyId");
		request.getGetLicensedProduct().setQualifyingLicensePool(QualifyingLicensePool.ALL_IN_HIERARCHY);
		
		GetLicensedProductResponseElement response = serviceEndpoint.getLicensedProducts(request);
		
		System.out.println(hitClasses);
		for(Class clazz : hitClasses)
			System.out.println(clazz.toString());
	}
}
