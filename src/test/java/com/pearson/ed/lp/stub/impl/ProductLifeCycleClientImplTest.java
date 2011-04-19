/**
 * 
 */
package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.TestHelperUtils.marshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Source;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.client.MockWebServiceServer;

import com.pearson.ed.lp.message.ProductData;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.rws.product.doc.v2.AttributeType;
import com.pearson.rws.product.doc.v2.AttributesType;
import com.pearson.rws.product.doc.v2.DisplayInfoType;
import com.pearson.rws.product.doc.v2.DisplayInfosType;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsRequest;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponse;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponseType;

/**
 * Unit test of {@link OrderLifeCycleClientImpl} using Spring WS mock objects.
 * 
 * @author ULLOYNI
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-lp-clients.xml",
		"classpath:applicationContext-test-lplc-ws.xml",
		"classpath:applicationContext-lplc.xml"
})
public class ProductLifeCycleClientImplTest {
	
	@Autowired(required = true)
	private ProductLifeCycleClientImpl testClient;
	
	@Autowired(required = true)
	private Jaxb2Marshaller marshaller;
	
	private MockWebServiceServer mockServer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		
		mockServer = MockWebServiceServer.createServer(testClient.getServiceClient());
	}

	/**
	 * Test method for {@link com.pearson.ed.lp.stub.impl.ProductLifeCycleClientImpl#getProductDataByProductEntityIds(com.pearson.ed.lp.message.ProductEntityIdsRequest)}.
	 */
	@Test
	public void testGetDisplayNamesByProductEntityIdsVarietyOfData() {
		Long[] dummyEntityIds = new Long[]{1l,2l,3l,4l,5l};
		Map<Long,ProductData> dummyProductData = new Hashtable<Long,ProductData>(5);
		// required data only (display name)
		dummyProductData.put(dummyEntityIds[0], new ProductData("id-1", "dummyDisplayName-1", null, null, null, null));
		// short description and long description
		dummyProductData.put(dummyEntityIds[1], new ProductData("id-2", "dummyDisplayName-2", "shortDesc-2", "longDesc-2", null, null));
		// CG program
		dummyProductData.put(dummyEntityIds[2], new ProductData("id-3", "dummyDisplayName-3", null, null, "cgProgram-3", null));
		// grade level
		dummyProductData.put(dummyEntityIds[3], new ProductData("id-4", "dummyDisplayName-4", null, null, null, new String[]{"gradeLevel-4"}));
		// full data, multiple grade levels
		dummyProductData.put(dummyEntityIds[4], new ProductData("id-5", "dummyDisplayName-5", "shortDesc-5", "longDesc-5", "cgProgram-5", 
				new String[]{"gradeLevel-5.1","gradeLevel-5.2","gradeLevel-5.3"}));
		
		mockServer.expect(payload(generateDummyGetProductRequest(dummyEntityIds)))
			.andRespond(withPayload(generateDummyGetProductResponse(dummyProductData)));

		ProductEntityIdsRequest request = new ProductEntityIdsRequest();
		request.getProductEntityIds().addAll(Arrays.asList(dummyEntityIds));
		ProductEntityIdsResponse response = testClient.getProductDataByProductEntityIds(request);
		
		mockServer.verify();
		
		assertEquivalent(dummyProductData, response);
	}

	/**
	 * Compare with assertions the contents of the seed data with the response object.
	 * Wraps a series of assertions.
	 * 
	 * @param seedData seed data
	 * @param responseData {@link ProductEntityIdsResponse} instance to compare against
	 */
	private void assertEquivalent(Map<Long, ProductData> seedData, ProductEntityIdsResponse responseData) {
		assertNotNull(seedData);
		assertNotNull(responseData);
		
		for(Entry<Long, ProductData> expected : seedData.entrySet()) {
			ProductData expectedData = expected.getValue();
			ProductData actualData = responseData.getProductDataByEntityIds().get(expected.getKey());
			
			assertNotNull(String.format("Missing response ProductData for entity id: %d", expected.getKey()), 
					actualData);
			assertEquals(String.format("ProductId mismatch for ProductData with entity id: %d", expected.getKey()), 
					expectedData.getProductId(), actualData.getProductId());
			assertEquals(String.format("DisplayName mismatch for ProductData with entity id: %d", expected.getKey()), 
					expectedData.getDisplayName(), actualData.getDisplayName());
			assertEquals(String.format("ShortDescription mismatch for ProductData with entity id: %d", expected.getKey()), 
					expectedData.getShortDescription(), actualData.getShortDescription());
			assertEquals(String.format("LongDescription mismatch for ProductData with entity id: %d", expected.getKey()), 
					expectedData.getLongDescription(), actualData.getLongDescription());
			assertEquals(String.format("CG Program mismatch for ProductData with entity id: %d", expected.getKey()), 
					expectedData.getCgProgram(), actualData.getCgProgram());
			
			assertEquals(String.format("Size mismatch for ProductData.gradeLevels with entity id: %d", expected.getKey()), 
					expectedData.getGradeLevels().size(), actualData.getGradeLevels().size());
			for(String expectedGradeLevel : expectedData.getGradeLevels()) {
				assertTrue(String.format("Grade Level \"%s\" not found in ProductData with entity id: %d", 
						expectedGradeLevel, expected.getKey()), 
						actualData.getGradeLevels().contains(expectedGradeLevel));
			}
		}
	}

	/**
	 * Generate a source object representing a GetProductsByProductEntityIds request.
	 * 
	 * @param productEntityIds collection of entity ids to use for the request
	 * @return {@link Source} instance
	 */
	private Source generateDummyGetProductRequest(Long ... productEntityIds) {
		GetProductsByProductEntityIdsRequest request = new GetProductsByProductEntityIdsRequest();
		request.getProductEntityId().addAll(Arrays.asList(productEntityIds));
		
		return marshal(marshaller, request);
	}

	/**
	 * Generate a source object representing a GetProductsByProductEntityIds response using
	 * the provided seed data.
	 * 
	 * @param dummyDataByDummyProductEntityIds seed data to convert into a response
	 * @return {@link Source} instance
	 */
	private Source generateDummyGetProductResponse(Map<Long, ProductData> dummyDataByDummyProductEntityIds) {
		GetProductsByProductEntityIdsResponse response = new GetProductsByProductEntityIdsResponse();
		
		for(Entry<Long, ProductData> dummyProduct : dummyDataByDummyProductEntityIds.entrySet()) {
			GetProductsByProductEntityIdsResponseType product = new GetProductsByProductEntityIdsResponseType();
			response.getProduct().add(product);
			
			ProductData dummyData = dummyProduct.getValue();
			
			product.setProductEntityId(dummyProduct.getKey());
			product.setProductId(dummyData.getProductId());
			
			DisplayInfoType displayInfo = new DisplayInfoType();
			displayInfo.setName(dummyData.getDisplayName());
			displayInfo.setShortDescription(dummyData.getShortDescription());
			displayInfo.setLongDescription(dummyData.getLongDescription());
			product.setDisplayInformation(new DisplayInfosType());
			product.getDisplayInformation().getDisplayInfo().add(displayInfo);
			
			product.setAttributes(new AttributesType());
			
			if(dummyData.getCgProgram() != null) {
				AttributeType attribute = new AttributeType();
				product.getAttributes().getAttribute().add(attribute);
				attribute.setAttributeKey(ProductLifeCycleClientImpl.CG_PROGRAM_ATTR_KEY);
				attribute.setAttributeValue(dummyData.getCgProgram());
			}
			
			for(String gradeLevel : dummyData.getGradeLevels()) {
				AttributeType attribute = new AttributeType();
				product.getAttributes().getAttribute().add(attribute);
				attribute.setAttributeKey(ProductLifeCycleClientImpl.GRADE_LEVEL_ATTR_KEY);
				attribute.setAttributeValue(gradeLevel);
			}
		}
		
		return marshal(marshaller, response);
	}
}