/**
 * 
 */
package com.pearson.ed.lp.splitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.LicensedProductDataCollection;
import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrderLineItemLPMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

/**
 * @author ULLOYNI
 *
 */
public class ProductOrderDetailsRequestSplitterTest {
	
	private ProductOrderDetailsRequestSplitter splitter = new ProductOrderDetailsRequestSplitter();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
	}

	/**
	 * Test method for {@link com.pearson.ed.lp.splitter.ProductOrderDetailsRequestSplitter#split(com.pearson.ed.lp.message.LicensedProductDataCollection)}.
	 */
	@Test
	public void testSplitEmptyLicensedProductDataCollection() {
		LicensedProductDataCollection emptyCollection = new LicensedProductDataCollection();
		emptyCollection.setLicensePools(new LicensePoolResponse());
		emptyCollection.getLicensePools().setLicensePools(new ArrayList<OrganizationLPMapping>(0));
		
		List<Object> result = splitter.split(emptyCollection);
		
		assertBasicContent(result);
	}

	/**
	 * Test method for {@link com.pearson.ed.lp.splitter.ProductOrderDetailsRequestSplitter#split(com.pearson.ed.lp.message.LicensedProductDataCollection)}.
	 */
	@Test
	public void testSplitDummyLicensedProductDataCollection() {
		Long dummyProductEntityId = Long.valueOf(123456);
		String dummyOrderLineItemId = "dummy-id";
		
		LicensedProductDataCollection dummyCollection = new LicensedProductDataCollection();
		dummyCollection.setLicensePools(new LicensePoolResponse());
		dummyCollection.getLicensePools().setLicensePools(new ArrayList<OrganizationLPMapping>());
		
		OrganizationLPMapping dummyLicensePool = new OrganizationLPMapping();
		dummyCollection.getLicensePools().getLicensePools().add(dummyLicensePool);
		dummyLicensePool.setLicensepoolMapping(new LicensePoolMapping());
		dummyLicensePool.getLicensepoolMapping().setProduct_id(dummyProductEntityId.toString());
		dummyLicensePool.getLicensepoolMapping().setOrderLineItems(new HashSet<OrderLineItemLPMapping>());
		
		OrderLineItemLPMapping dummyOrderLineItem = new OrderLineItemLPMapping();
		dummyOrderLineItem.setOrderLineItemId(dummyOrderLineItemId);
		dummyLicensePool.getLicensepoolMapping().getOrderLineItems().add(dummyOrderLineItem);
		
		List<Object> result = splitter.split(dummyCollection);
		
		assertBasicContent(result);
		
		OrderLineItemsRequest orderRequest = null;
		ProductEntityIdsRequest productRequest = null;
		for(Object resultEntry : result) {
			if(resultEntry instanceof OrderLineItemsRequest) {
				orderRequest = (OrderLineItemsRequest)resultEntry;
			} else if(resultEntry instanceof ProductEntityIdsRequest) {
				productRequest = (ProductEntityIdsRequest)resultEntry;
			}
		}
		
		assertEquals(1, orderRequest.getOrderLineItemIds().size());
		assertEquals(1, productRequest.getProductEntityIds().size());
		assertEquals(dummyOrderLineItemId, orderRequest.getOrderLineItemIds().iterator().next());
		assertEquals(dummyProductEntityId, productRequest.getProductEntityIds().iterator().next());
	}
	
	/**
	 * Basic result set content validation helper.
	 * @param result
	 */
	private void assertBasicContent(List<Object> result) {
		assertEquals(3, result.size());
		
		OrderLineItemsRequest orderRequest = null;
		ProductEntityIdsRequest productRequest = null;
		LicensedProductDataCollection dataCollection = null;
		for(Object resultEntry : result) {
			if(resultEntry instanceof OrderLineItemsRequest) {
				orderRequest = (OrderLineItemsRequest)resultEntry;
			} else if(resultEntry instanceof ProductEntityIdsRequest) {
				productRequest = (ProductEntityIdsRequest)resultEntry;
			} else if(resultEntry instanceof LicensedProductDataCollection) {
				dataCollection = (LicensedProductDataCollection)resultEntry;
			}
		}

		assertNotNull(orderRequest);
		assertNotNull(productRequest);
		assertNotNull(dataCollection);
	}

}
