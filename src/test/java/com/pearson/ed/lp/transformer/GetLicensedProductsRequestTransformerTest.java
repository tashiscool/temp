/**
 * 
 */
package com.pearson.ed.lp.transformer;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProduct;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductRequestElement;
import com.pearson.rws.licensedproduct.doc.v2.QualifyingLicensePool;

/**
 * @author ULLOYNI
 * 
 */
public class GetLicensedProductsRequestTransformerTest {

	private GetLicensedProductsRequestTransformer transformer = new GetLicensedProductsRequestTransformer();
	
	/**
	 * Setup test logging.
	 */
	@BeforeClass
	public static void setUpClass() {
		BasicConfigurator.configure();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.transformer.GetLicensedProductsRequestTransformer#transform(com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductRequestElement)}
	 * .
	 */
	@Test
	public void testTransformGetLicensedProductIntoLicencePoolRequest() {
		String dummyOrgId = "dummy-org-id";
		QualifyingLicensePool dummyQualifyingLicensePool = QualifyingLicensePool.ALL_IN_HIERARCHY;
		GetLicensedProductRequestElement dummyRequest = new GetLicensedProductRequestElement();
		dummyRequest.setGetLicensedProduct(new GetLicensedProduct());
		dummyRequest.getGetLicensedProduct().setOrganizationId(dummyOrgId);
		dummyRequest.getGetLicensedProduct().setQualifyingLicensePool(dummyQualifyingLicensePool);

		LicensePoolByOrganizationIdRequest result = transformer.transform(dummyRequest);

		assertEquals(dummyOrgId, result.getOrganizationId());
		assertEquals(dummyQualifyingLicensePool.toString(), result.getQualifyingLicensePool());
	}

}
