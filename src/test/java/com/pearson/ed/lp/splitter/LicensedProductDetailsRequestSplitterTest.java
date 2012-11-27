/**
 * 
 */
package com.pearson.ed.lp.splitter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.OrderLineItemRequest;
import com.pearson.ed.lp.message.OrganizationDisplayNameRequest;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrderLineItemLPMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

/**
 * @author ULLOYNI
 * 
 */
public class LicensedProductDetailsRequestSplitterTest {

	private LicensedProductDetailsRequestSplitter splitter = new LicensedProductDetailsRequestSplitter();
	
	/**
	 * Setup test logging.
	 */
	@BeforeClass
	public static void setUpClass() {
		BasicConfigurator.configure();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.splitter.LicensedProductDetailsRequestSplitter#split(com.pearson.ed.lp.message.LicensePoolResponse)}
	 */
	@Test(expected=NullPointerException.class)
	public void testSplitNullLicensePoolResponse() {
		splitter.split(null);
	}
	
	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.splitter.LicensedProductDetailsRequestSplitter#split(com.pearson.ed.lp.message.LicensePoolResponse)}
	 * .
	 */
	@Test
	public void testSplitNoOrderLineItems() {
		Long dummyProductEntityId = Long.valueOf(123456);
		String dummyOrgId = "dummy-org-id";

		LicensePoolResponse licensePoolResponse = new LicensePoolResponse();
		licensePoolResponse.setLicensePools(new ArrayList<OrganizationLPMapping>());

		OrganizationLPMapping dummyLicensePool = new OrganizationLPMapping();
		licensePoolResponse.getLicensePools().add(dummyLicensePool);
		dummyLicensePool.setLicensepoolMapping(new LicensePoolMapping());
		dummyLicensePool.getLicensepoolMapping().setOrg_id(dummyOrgId);
		dummyLicensePool.getLicensepoolMapping().setProduct_id(dummyProductEntityId.toString());

		try {
			splitter.split(licensePoolResponse);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(IllegalStateException.class));
			assertTrue(e.getMessage().startsWith("no order line items in "));
		}
		
	}
	
	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.splitter.LicensedProductDetailsRequestSplitter#split(com.pearson.ed.lp.message.LicensePoolResponse)}
	 * .
	 */
	@Test
	public void testSplitEmptyLicensePoolResponse() {
		LicensePoolResponse licensePoolResponse = new LicensePoolResponse();
		licensePoolResponse.setLicensePools(new ArrayList<OrganizationLPMapping>(0));

		List<Object> result = splitter.split(licensePoolResponse);

		assertEquals(1, result.size());
		OrderLineItemRequest orderRequest = null;
		ProductEntityIdsRequest productRequest = null;
		LicensePoolResponse licenseProductResponse = null;
		OrganizationDisplayNameRequest orgRequest = null;

		for (Object resultEntry : result) {
			if (resultEntry instanceof OrderLineItemRequest) {
				orderRequest = (OrderLineItemRequest) resultEntry;
			} else if (resultEntry instanceof ProductEntityIdsRequest) {
				productRequest = (ProductEntityIdsRequest) resultEntry;
			} else if (resultEntry instanceof OrganizationDisplayNameRequest) {
				orgRequest = (OrganizationDisplayNameRequest) resultEntry;
			} else if (resultEntry instanceof LicensePoolResponse) {
				licenseProductResponse = (LicensePoolResponse) resultEntry;
			}
		}

		assertNull(orderRequest);
		assertNull(orgRequest);
		assertNull(productRequest);
		assertNotNull(licenseProductResponse);
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.splitter.LicensedProductDetailsRequestSplitter#split(com.pearson.ed.lp.message.LicensePoolResponse)}
	 * .
	 */
	@Test
	public void testSplitDummyLicensePoolResponse() {
		Long dummyProductEntityId = Long.valueOf(123456);
		String dummyOrderLineItemId = "dummy-order-id";
		String dummyOrgId = "dummy-org-id";

		LicensePoolResponse licensePoolResponse = new LicensePoolResponse();
		licensePoolResponse.setLicensePools(new ArrayList<OrganizationLPMapping>());

		OrganizationLPMapping dummyLicensePool = new OrganizationLPMapping();
		licensePoolResponse.getLicensePools().add(dummyLicensePool);
		dummyLicensePool.setLicensepoolMapping(new LicensePoolMapping());
		dummyLicensePool.getLicensepoolMapping().setOrg_id(dummyOrgId);
		dummyLicensePool.getLicensepoolMapping().setProduct_id(dummyProductEntityId.toString());
		dummyLicensePool.getLicensepoolMapping().setOrderLineItems(new HashSet<OrderLineItemLPMapping>());

		OrderLineItemLPMapping dummyOrderLineItem = new OrderLineItemLPMapping();
		dummyOrderLineItem.setOrderLineItemId(dummyOrderLineItemId);
		dummyLicensePool.getLicensepoolMapping().getOrderLineItems().add(dummyOrderLineItem);

		List<Object> result = splitter.split(licensePoolResponse);

		assertBasicContent(result);

		assertEquals(4, result.size());
		OrderLineItemRequest orderRequest = null;
		ProductEntityIdsRequest productRequest = null;
		OrganizationDisplayNameRequest orgRequest = null;
		LicensePoolResponse licenseResponse = null;
		
		for (Object resultEntry : result) {
			if (resultEntry instanceof OrderLineItemRequest) {
				orderRequest = (OrderLineItemRequest) resultEntry;
			} else if (resultEntry instanceof ProductEntityIdsRequest) {
				productRequest = (ProductEntityIdsRequest) resultEntry;
			} else if (resultEntry instanceof OrganizationDisplayNameRequest) {
				orgRequest = (OrganizationDisplayNameRequest) resultEntry;
			} else if (resultEntry instanceof OrganizationDisplayNameRequest) {
				orgRequest = (OrganizationDisplayNameRequest) resultEntry;
			} else if (resultEntry instanceof LicensePoolResponse) {
				licenseResponse = (LicensePoolResponse) resultEntry;
			}
		}

		assertEquals(dummyOrderLineItemId, orderRequest.getOrderLineItemId());
		assertEquals(dummyOrgId, orgRequest.getOrganizationId());
		assertEquals(1, productRequest.getProductEntityIds().size());
		assertEquals(dummyProductEntityId, productRequest.getProductEntityIds().iterator().next());
		assertEquals(licensePoolResponse, licenseResponse);
	}

	/**
	 * Basic result set content validation helper.
	 * 
	 * @param result
	 */
	private void assertBasicContent(List<Object> result) {
		OrderLineItemRequest orderRequest = null;
		ProductEntityIdsRequest productRequest = null;
		LicensePoolResponse licensePoolResponse = null;
		OrganizationDisplayNameRequest orgRequest = null;

		for (Object resultEntry : result) {
			if (resultEntry instanceof OrderLineItemRequest) {
				orderRequest = (OrderLineItemRequest) resultEntry;
			} else if (resultEntry instanceof ProductEntityIdsRequest) {
				productRequest = (ProductEntityIdsRequest) resultEntry;
			} else if (resultEntry instanceof OrganizationDisplayNameRequest) {
				orgRequest = (OrganizationDisplayNameRequest) resultEntry;
			} else if (resultEntry instanceof LicensePoolResponse) {
				licensePoolResponse = (LicensePoolResponse) resultEntry;
			}
		}

		assertNotNull(orderRequest);
		assertNotNull(orgRequest);
		assertNotNull(productRequest);
		assertNotNull(licensePoolResponse);
	}

}
