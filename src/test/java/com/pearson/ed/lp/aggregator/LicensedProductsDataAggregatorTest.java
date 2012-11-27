/**
 * 
 */
package com.pearson.ed.lp.aggregator;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.OrderLineItemResponse;
import com.pearson.ed.lp.message.OrganizationDisplayNameResponse;
import com.pearson.ed.lp.message.ProductData;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrderLineItemLPMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductResponseElement;
import com.pearson.rws.licensedproduct.doc.v2.LicensedProduct;

/**
 * @author ULLOYNI
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-lp-exception.xml",
		"classpath:applicationContext-test-lplc.xml"
})
public class LicensedProductsDataAggregatorTest {

	private LicensedProductResponseAggregator aggregator = new LicensedProductResponseAggregator();
	
	private String dummyOrgId = "dummy-org-id";
	private String dummyOrgDisplayName = "dummy-org-disp-name";
	private String dummyOrderLineItemId = "dummy-order-line-item-id";
	private String dummyIsbn = "0123456789012";
	
	private Long dummyProductEntityId = Long.valueOf(123);
	private String dummyProductId = "dummy-product-id";
	private String dummyDisplayName = "dummy-display-name";
	private String dummyShortDescription = "dummy-short-desc";
	private String dummyLongDescription =" dummy-long-desc";
	private String dummyCGProgram = "dummy-cg -program";

	
	/**
	 * Setup test logging.
	 */
	@BeforeClass
	public static void setUpClass() {
		BasicConfigurator.configure();
	}
	
	/**
	 * Setup test.
	 */
	@Before
	public void setUp() {
//		aggregator.setExceptionFactory(exceptionFactory);
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductResponseAggregator#aggregate(java.util.List)}.
	 */
	@Test
	public void testAggregateOrgDisplayNameResponse() {
		List<Object> input = new ArrayList<Object>();
		input.add(createLicensePoolResponse());
		input.add(new ProductEntityIdsResponse());
		input.add(createOrderLineItemResponse());
		
		try {
			aggregator.aggregate(input);
		} catch (Exception e) {
			assertThat(e, is(NullPointerException.class));
			assertEquals("Required OrganizationDisplayNameResponse messages not received for final response generation!", e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductResponseAggregator#aggregate(java.util.List)}.
	 */
	@Test
	public void testAggregateNoOrderLineItemResponse() {
		List<Object> input = new ArrayList<Object>();
		input.add(createLicensePoolResponse());
		input.add(createOrganizationDisplayNameResponse());
		input.add(new ProductEntityIdsResponse());

		try {
			aggregator.aggregate(input);
		} catch (Exception e) {
			assertThat(e, is(NullPointerException.class));
			assertEquals("Required OrderLineItemResponse message not received for final response generation!", e.getMessage());
		}
	}
	
	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductResponseAggregator#aggregate(java.util.List)}.
	 */
	@Test
	public void testAggregateNoProductEntityIdsResponse() {
		List<Object> input = new ArrayList<Object>();
		input.add(createLicensePoolResponse());
		input.add(createOrganizationDisplayNameResponse());

		try {
			aggregator.aggregate(input);
		} catch (Exception e) {
			assertThat(e, is(NullPointerException.class));
			assertEquals("Required ProductEntityIdsResponse message not received for final response generation!", e.getMessage());
		}
	}
	
	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductResponseAggregator#aggregate(java.util.List)}.
	 */
	@Test
	public void testAggregateNoLicensePoolResponse() {
		List<Object> input = new ArrayList<Object>();

		try {
			aggregator.aggregate(input);
		} catch (Exception e) {
			assertThat(e, is(NullPointerException.class));
			assertEquals("Required LicensePoolResponse message not received for final response generation!", e.getMessage());
		}
	}
	
	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductResponseAggregator#aggregate(java.util.List)}.
	 */
	@Test
	public void testAggregateHappyPath() {
		List<Object> input = new ArrayList<Object>();
		
		ProductEntityIdsResponse productResponse = createProductEntityIdsResponse();
		OrganizationDisplayNameResponse orgResponse = createOrganizationDisplayNameResponse();
		LicensePoolResponse licenseResponse = createLicensePoolResponse();
		OrderLineItemResponse orderResponse = createOrderLineItemResponse();
		
		input.add(productResponse);
		input.add(orgResponse);
		input.add(licenseResponse);
		input.add(orderResponse);

		GetLicensedProductResponseElement response = aggregator.aggregate(input);
		
		assertProductData(response, productResponse);
		assertOrgData(response, orgResponse);
		assertLicenseData(response, licenseResponse);
		assertOrderData(response, orderResponse);

	}
	
	private void assertOrderData(GetLicensedProductResponseElement response,
			OrderLineItemResponse orderResponse) {
		LicensedProduct licensedProduct = response.getLicensedProduct().get(0);
		assertEquals(licensedProduct.getOrderedISBN(), orderResponse.getOrderedISBN());
	}

	private void assertLicenseData(GetLicensedProductResponseElement response,
			LicensePoolResponse licenseResponse) {
		LicensedProduct licensedProduct = response.getLicensedProduct().get(0);
		OrganizationLPMapping lpMapping = licenseResponse.getLicensePools().get(0);
		assertEquals(licensedProduct.getDenyNewSubscription(), lpMapping.getDenyManualSubscription());	
		assertEquals(licensedProduct.getLicensePoolId(), lpMapping.getLicensepoolMapping().getLicensepoolId());	
		assertEquals(licensedProduct.getLicensePoolType(), lpMapping.getLicensepoolMapping().getType());	
		assertEquals(licensedProduct.getLicensePoolStatus(), lpMapping.getLicensepoolMapping().getStatus());	
		assertEquals(licensedProduct.getQuantity(), lpMapping.getLicensepoolMapping().getQuantity());	
		assertEquals(licensedProduct.getUsedLicenses(), lpMapping.getUsed_quantity());			
	}

	private void assertOrgData(GetLicensedProductResponseElement response,
			OrganizationDisplayNameResponse orgResponse) {
		LicensedProduct licensedProduct = response.getLicensedProduct().get(0);
		assertEquals(licensedProduct.getOrganizationId(), orgResponse.getOrganizationId());	
		assertEquals(licensedProduct.getLicensedOrganizationDisplayName(), orgResponse.getOrganizationDisplayName());		
	}

	private void assertProductData(GetLicensedProductResponseElement response,
			ProductEntityIdsResponse productResponse) {
		LicensedProduct licensedProduct = response.getLicensedProduct().get(0);
		ProductData productData = productResponse.getProductDataByEntityIds().get(dummyProductEntityId);
		assertEquals(licensedProduct.getProductId(), productData.getProductId());	
		assertEquals(licensedProduct.getProductDisplayName(), productData.getDisplayName());	
		assertEquals(licensedProduct.getProductLongDescription(), productData.getLongDescription());		
		assertEquals(licensedProduct.getProductShortDescription(), productData.getShortDescription());		
		assertEquals(licensedProduct.getCGProgram(), productData.getCgProgram());		
	}

	private ProductEntityIdsResponse createProductEntityIdsResponse() {
		ProductEntityIdsResponse response = new ProductEntityIdsResponse();
		Map<Long, ProductData> map = new HashMap<Long, ProductData>();
		map.put(dummyProductEntityId, createProductData());
		response.setProductDataByEntityIds(map);
		return response;
	}

	private ProductData createProductData() {
		ProductData productData = new ProductData();
		productData.setProductId(dummyProductId);
		productData.setDisplayName(dummyDisplayName);
		productData.setShortDescription(dummyShortDescription);
		productData.setLongDescription(dummyLongDescription);
		productData.setCgProgram(dummyCGProgram);
		return productData;
	}
	
	private OrganizationDisplayNameResponse createOrganizationDisplayNameResponse() {
		OrganizationDisplayNameResponse response = new OrganizationDisplayNameResponse();
		response.setOrganizationDisplayName(dummyOrgDisplayName);
		response.setOrganizationId(dummyOrgId);
		return response;
	}
	
	private LicensePoolResponse createLicensePoolResponse() {
		LicensePoolResponse response = new LicensePoolResponse();
		List<OrganizationLPMapping> lpMappingList = new ArrayList<OrganizationLPMapping>();
		OrganizationLPMapping dummyLicensePool = new OrganizationLPMapping();
		dummyLicensePool.setOrganization_id(dummyOrgId);
		dummyLicensePool.setUsed_quantity(10);
		LicensePoolMapping dummyLicensePoolMapping = new LicensePoolMapping();
		dummyLicensePool.setLicensepoolMapping(dummyLicensePoolMapping);
		
		dummyLicensePoolMapping.setLicensepoolId("dummy-license-pool-id");
		dummyLicensePoolMapping.setOrg_id(dummyOrgId);
		dummyLicensePoolMapping.setProduct_id(dummyProductEntityId.toString());
		dummyLicensePoolMapping.setType("dummy-license-pool-type");
		dummyLicensePoolMapping.setStatus("dummy-license-pool-status");
		dummyLicensePoolMapping.setDenyManualSubscription(0);
		dummyLicensePoolMapping.setQuantity(20);
		dummyLicensePoolMapping.setStart_date(new Date());
		dummyLicensePoolMapping.setEnd_date(new Date());
		
		OrderLineItemLPMapping dummyOrderLineItem = new OrderLineItemLPMapping();
		dummyLicensePoolMapping.getOrderLineItems().add(dummyOrderLineItem);
		dummyOrderLineItem.setOrderLineItemId(dummyOrderLineItemId);
		
		lpMappingList.add(dummyLicensePool);
		response.setLicensePools(lpMappingList);
		return response;
	}

	private OrderLineItemResponse createOrderLineItemResponse() {
		OrderLineItemResponse response = new OrderLineItemResponse();
		response.setOrderedISBN(dummyIsbn);
		response.setOrderLineItemId(dummyOrderLineItemId);
		return response;
	}
}
