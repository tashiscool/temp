package com.pearson.ed.lp;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;
import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.OrderLineItemsResponse;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lp.stub.api.LicensePoolLifeCycleClient;
import com.pearson.ed.lp.stub.api.OrderLifeCycleClient;
import com.pearson.ed.lp.stub.api.OrganizationLifeCycleClient;
import com.pearson.ed.lp.stub.api.ProductLifeCycleClient;
import com.pearson.ed.lp.stub.mock.MockLicensePoolLifeCycleClient;
import com.pearson.ed.lp.stub.mock.MockOrderLifeCycleClient;
import com.pearson.ed.lp.stub.mock.MockOrganizationLifeCycleClient;
import com.pearson.ed.lp.stub.mock.MockProductLifeCycleClient;
import com.pearson.ed.lp.ws.MarshallingLicensedProductServiceEndpoint;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProduct;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductRequestElement;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductResponseElement;
import com.pearson.rws.licensedproduct.doc.v2.QualifyingLicensePool;

/**
 * Simple test of Spring Integration configuration, verifying that the end-to-end
 * messaging is functioning as expected complete with message routing.
 * Uses mock web service client objects.
 * 
 * @author ULLOYNI
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-lp-integration.xml",
		"classpath:applicationContext-test-lp-client-mocks.xml"
})
public class MockEndToEndGetLicensedProductsServiceTest {
	
	
	@Autowired(required = true)
	private MarshallingLicensedProductServiceEndpoint serviceEndpoint;

	@Autowired(required = true)
	private MockLicensePoolLifeCycleClient mockLicensePoolClient;

	@Autowired(required = true)
	private MockProductLifeCycleClient mockProductClient;

	@Autowired(required = true)
	private MockOrderLifeCycleClient mockOrderClient;

	@Autowired(required = true)
	private MockOrganizationLifeCycleClient mockOrganizationClient;

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		
		mockLicensePoolClient.setMockClient(createMock(LicensePoolLifeCycleClient.class));
		mockProductClient.setMockClient(createMock(ProductLifeCycleClient.class));
		mockOrderClient.setMockClient(createMock(OrderLifeCycleClient.class));
		mockOrganizationClient.setMockClient(createMock(OrganizationLifeCycleClient.class));
	}

	/**
	 * Setup the mock client service object behaviors based on the value for the qualifying license pool.
	 * @param qualifyingLicensePool {@link QualifyingLicensePool} value
	 */
	private void setMockClientsBehaviors(QualifyingLicensePool qualifyingLicensePool) {
		LicensePoolLifeCycleClient innerMockLicensePoolClient = mockLicensePoolClient.getMockClient();
		ProductLifeCycleClient innerMockProductClient = mockProductClient.getMockClient();
		OrderLifeCycleClient innerMockOrderClient = mockOrderClient.getMockClient();
		OrganizationLifeCycleClient innerMockOrganizationClient = mockOrganizationClient.getMockClient();
		
		reset(mockLicensePoolClient.getMockClient(), 
				mockProductClient.getMockClient(), 
				mockOrderClient.getMockClient(), 
				mockOrganizationClient.getMockClient());
		
		LicensePoolResponse dummyLicensePoolResponse = new LicensePoolResponse();
		dummyLicensePoolResponse.setLicensePools(new ArrayList<OrganizationLPMapping>());
		ProductEntityIdsResponse dummyProducEntityIdsResponse = new ProductEntityIdsResponse();
		OrderLineItemsResponse dummyOrderLineItemsResponse = new OrderLineItemsResponse();
		OrganizationDisplayNamesResponse dummyOrgDisplayNamesResponse = new OrganizationDisplayNamesResponse();
		
		expect(innerMockLicensePoolClient.getLicensePoolsByOrganizationId(
				isA(LicensePoolByOrganizationIdRequest.class)))
				.andReturn(dummyLicensePoolResponse);
		expect(innerMockProductClient.getDisplayNamesByProductEntityIds(isA(ProductEntityIdsRequest.class)))
			.andReturn(dummyProducEntityIdsResponse);
		expect(innerMockOrderClient.getOrderedISBNsByOrderLineItemIds(isA(OrderLineItemsRequest.class)))
			.andReturn(dummyOrderLineItemsResponse);
		
		switch(qualifyingLicensePool) {
		case ALL_IN_HIERARCHY:
			expect(innerMockOrganizationClient.getParentTreeDisplayNamesByOrganizationId(isA(String.class)))
				.andReturn(dummyOrgDisplayNamesResponse);
			expect(innerMockOrganizationClient.getChildTreeDisplayNamesByOrganizationId(isA(String.class)))
				.andReturn(dummyOrgDisplayNamesResponse);
			expect(innerMockOrganizationClient.getOrganizationDisplayName(isA(String.class)))
				.andReturn(dummyOrgDisplayNamesResponse);
			break;
		case ROOT_AND_PARENTS:
			expect(innerMockOrganizationClient.getParentTreeDisplayNamesByOrganizationId(isA(String.class)))
			.andReturn(dummyOrgDisplayNamesResponse);
			expect(innerMockOrganizationClient.getOrganizationDisplayName(isA(String.class)))
				.andReturn(dummyOrgDisplayNamesResponse);
			break;
		case ROOT_ONLY:
			expect(innerMockOrganizationClient.getOrganizationDisplayName(isA(String.class)))
				.andReturn(dummyOrgDisplayNamesResponse);
			break;
		}
		
		replay(mockLicensePoolClient.getMockClient(), 
				mockProductClient.getMockClient(), 
				mockOrderClient.getMockClient(), 
				mockOrganizationClient.getMockClient());
	}

	/**
	 * Test behavior with {@link QualifyingLicensePool} set to ROOT_ONLY.
	 */
	@Test
	public void testEndToEndMessagingWithQualifyingLicensePoolRootOnly() {
		QualifyingLicensePool qualifyingLicensePool = QualifyingLicensePool.ROOT_ONLY;
		setMockClientsBehaviors(qualifyingLicensePool);
		
		GetLicensedProductRequestElement request = new GetLicensedProductRequestElement();
		request.setGetLicensedProduct(new GetLicensedProduct());
		request.getGetLicensedProduct().setOrganizationId("dummyId");
		request.getGetLicensedProduct().setQualifyingLicensePool(qualifyingLicensePool);
		
		try {
			GetLicensedProductResponseElement response = serviceEndpoint.getLicensedProducts(request);
			assertNotNull(response);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		verify(mockLicensePoolClient.getMockClient(), 
				mockProductClient.getMockClient(), 
				mockOrderClient.getMockClient(), 
				mockOrganizationClient.getMockClient());
	}

	/**
	 * Test behavior with {@link QualifyingLicensePool} set to ROOT_AND_PARENTS.
	 */
	@Test
	public void testEndToEndMessagingWithQualifyingLicensePoolRootAndParents() {
		QualifyingLicensePool qualifyingLicensePool = QualifyingLicensePool.ROOT_AND_PARENTS;
		setMockClientsBehaviors(qualifyingLicensePool);
		
		GetLicensedProductRequestElement request = new GetLicensedProductRequestElement();
		request.setGetLicensedProduct(new GetLicensedProduct());
		request.getGetLicensedProduct().setOrganizationId("dummyId");
		request.getGetLicensedProduct().setQualifyingLicensePool(qualifyingLicensePool);
		
		try {
			GetLicensedProductResponseElement response = serviceEndpoint.getLicensedProducts(request);
			assertNotNull(response);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		verify(mockLicensePoolClient.getMockClient(), 
				mockProductClient.getMockClient(), 
				mockOrderClient.getMockClient(), 
				mockOrganizationClient.getMockClient());
	}

	/**
	 * Test behavior with {@link QualifyingLicensePool} set to ALL_IN_HIERARCHY.
	 */
	@Test
	public void testEndToEndMessagingWithQualifyingLicensePoolAllInHierarchy() {
		QualifyingLicensePool qualifyingLicensePool = QualifyingLicensePool.ALL_IN_HIERARCHY;
		setMockClientsBehaviors(qualifyingLicensePool);
		
		GetLicensedProductRequestElement request = new GetLicensedProductRequestElement();
		request.setGetLicensedProduct(new GetLicensedProduct());
		request.getGetLicensedProduct().setOrganizationId("dummyId");
		request.getGetLicensedProduct().setQualifyingLicensePool(qualifyingLicensePool);
		
		try {
			GetLicensedProductResponseElement response = serviceEndpoint.getLicensedProducts(request);
			assertNotNull(response);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		verify(mockLicensePoolClient.getMockClient(), 
				mockProductClient.getMockClient(), 
				mockOrderClient.getMockClient(), 
				mockOrganizationClient.getMockClient());
	}
}
