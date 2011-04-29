/**
 * 
 */
package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.LicensedProductTestHelper.generateDummyGetProductRequest;
import static com.pearson.ed.lp.LicensedProductTestHelper.generateDummyGetProductResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withClientOrSenderFault;
import static org.springframework.ws.test.client.ResponseCreators.withException;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.test.client.MockWebServiceServer;

import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.ProductNotFoundException;
import com.pearson.ed.lp.exception.RequiredObjectNotFoundException;
import com.pearson.ed.lp.message.ProductData;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;

/**
 * Unit test of {@link OrderLifeCycleClientImpl} using Spring WS mock objects.
 * 
 * @author ULLOYNI
 * 
 */
public class ProductLifeCycleClientImplTest extends BaseLicensedProductClientStubTest {

	@Autowired(required = true)
	private ProductLifeCycleClientImpl testClient;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockServer = MockWebServiceServer.createServer(testClient.getServiceClient());
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.ProductLifeCycleClientImpl#getProductDataByProductEntityIds(com.pearson.ed.lp.message.ProductEntityIdsRequest)}
	 * .
	 */
	@Test
	public void testGetDisplayNamesByProductEntityIdsVarietyOfData() {
		Long[] dummyEntityIds = new Long[] { 1l, 2l, 3l, 4l, 5l };
		Map<Long, ProductData> dummyProductData = new Hashtable<Long, ProductData>(5);
		// required data only (display name)
		dummyProductData.put(dummyEntityIds[0], new ProductData("id-1", "dummyDisplayName-1", null, null, null, null));
		// short description and long description
		dummyProductData.put(dummyEntityIds[1], new ProductData("id-2", "dummyDisplayName-2", "shortDesc-2",
				"longDesc-2", null, null));
		// CG program
		dummyProductData.put(dummyEntityIds[2], new ProductData("id-3", "dummyDisplayName-3", null, null,
				"cgProgram-3", null));
		// grade level
		dummyProductData.put(dummyEntityIds[3], new ProductData("id-4", "dummyDisplayName-4", null, null, null,
				new String[] { "gradeLevel-4" }));
		// full data, multiple grade levels
		dummyProductData.put(dummyEntityIds[4], new ProductData("id-5", "dummyDisplayName-5", "shortDesc-5",
				"longDesc-5", "cgProgram-5.1 cgProgram-5.2", new String[] { "gradeLevel-5.1", "gradeLevel-5.2", "gradeLevel-5.3" }));

		mockServer.expect(payload(generateDummyGetProductRequest(dummyEntityIds))).andRespond(
				withPayload(generateDummyGetProductResponse(dummyProductData)));

		ProductEntityIdsRequest request = new ProductEntityIdsRequest();
		request.getProductEntityIds().addAll(Arrays.asList(dummyEntityIds));
		ProductEntityIdsResponse response = testClient.getProductDataByProductEntityIds(request);

		mockServer.verify();

		assertEquivalent(dummyProductData, response);
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.ProductLifeCycleClientImpl#getProductDataByProductEntityIds(com.pearson.ed.lp.message.ProductEntityIdsRequest)}
	 * .
	 */
	@Test
	public void testGetDisplayNamesByProductEntityIdsNonSpecificClientFault() {
		Long[] dummyEntityIds = new Long[] { 1l, 2l, 3l, 4l, 5l };

		mockServer.expect(payload(generateDummyGetProductRequest(dummyEntityIds))).andRespond(
				withClientOrSenderFault("Bad service! No cookie!", Locale.ENGLISH));

		ProductEntityIdsRequest request = new ProductEntityIdsRequest();
		request.getProductEntityIds().addAll(Arrays.asList(dummyEntityIds));
		
		try {
			testClient.getProductDataByProductEntityIds(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.ProductLifeCycleClientImpl#getProductDataByProductEntityIds(com.pearson.ed.lp.message.ProductEntityIdsRequest)}
	 * .
	 */
	@Test
	public void testGetDisplayNamesByProductEntityIdsNonSpecificIOException() {
		Long[] dummyEntityIds = new Long[] { 1l, 2l, 3l, 4l, 5l };

		mockServer.expect(payload(generateDummyGetProductRequest(dummyEntityIds))).andRespond(
				withException(new IOException("Bad service! No cookie!")));

		ProductEntityIdsRequest request = new ProductEntityIdsRequest();
		request.getProductEntityIds().addAll(Arrays.asList(dummyEntityIds));
		
		try {
			testClient.getProductDataByProductEntityIds(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.ProductLifeCycleClientImpl#getProductDataByProductEntityIds(com.pearson.ed.lp.message.ProductEntityIdsRequest)}
	 * .
	 */
	@Test
	public void testGetDisplayNamesByProductEntityIdsBadProductEntityIds() {
		Long[] dummyEntityIds = new Long[] { 1l, 2l, 3l, 4l, 5l };

		mockServer.expect(payload(generateDummyGetProductRequest(dummyEntityIds))).andRespond(
				withClientOrSenderFault("Required object not found", Locale.ENGLISH));

		ProductEntityIdsRequest request = new ProductEntityIdsRequest();
		request.getProductEntityIds().addAll(Arrays.asList(dummyEntityIds));
		
		try {
			testClient.getProductDataByProductEntityIds(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ProductNotFoundException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.ProductLifeCycleClientImpl#getProductDataByProductEntityIds(com.pearson.ed.lp.message.ProductEntityIdsRequest)}
	 * .
	 */
	@Test
	public void testGetDisplayNamesByProductEntityIdsNoDisplayInfo() {
		Long[] dummyEntityIds = new Long[] { 1l, 2l, 3l, 4l, 5l };
		Map<Long, ProductData> dummyProductData = new Hashtable<Long, ProductData>(5);
		// required data only (display name)
		dummyProductData.put(dummyEntityIds[0], new ProductData("id-1", "dummyDisplayName-1", null, null, null, null));
		// short description and long description
		dummyProductData.put(dummyEntityIds[1], new ProductData("id-2", "dummyDisplayName-2", "shortDesc-2",
				"longDesc-2", null, null));
		// CG program
		dummyProductData.put(dummyEntityIds[2], new ProductData("id-3", "dummyDisplayName-3", null, null,
				"cgProgram-3", null));
		// grade level
		dummyProductData.put(dummyEntityIds[3], new ProductData("id-4", "dummyDisplayName-4", null, null, null,
				new String[] { "gradeLevel-4" }));
		// missing all but entity id for exception throw check
		dummyProductData.put(dummyEntityIds[4], new ProductData("id-5", null, null,
				null, null, null));

		mockServer.expect(payload(generateDummyGetProductRequest(dummyEntityIds))).andRespond(
				withPayload(generateDummyGetProductResponse(dummyProductData)));

		ProductEntityIdsRequest request = new ProductEntityIdsRequest();
		request.getProductEntityIds().addAll(Arrays.asList(dummyEntityIds));
		
		try {
			testClient.getProductDataByProductEntityIds(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(RequiredObjectNotFoundException.class));
		}

		mockServer.verify();
	}

	/**
	 * Compare with assertions the contents of the seed data with the response object. Wraps a series of assertions.
	 * 
	 * @param seedData
	 *            seed data
	 * @param responseData
	 *            {@link ProductEntityIdsResponse} instance to compare against
	 */
	private void assertEquivalent(Map<Long, ProductData> seedData, ProductEntityIdsResponse responseData) {
		assertNotNull(seedData);
		assertNotNull(responseData);

		for (Entry<Long, ProductData> expected : seedData.entrySet()) {
			ProductData expectedData = expected.getValue();
			ProductData actualData = responseData.getProductDataByEntityIds().get(expected.getKey());

			assertNotNull(String.format("Missing response ProductData for entity id: %d", expected.getKey()),
					actualData);
			assertEquals(String.format("ProductId mismatch for ProductData with entity id: %d", expected.getKey()),
					expectedData.getProductId(), actualData.getProductId());
			assertEquals(String.format("DisplayName mismatch for ProductData with entity id: %d", expected.getKey()),
					expectedData.getDisplayName(), actualData.getDisplayName());
			assertEquals(
					String.format("ShortDescription mismatch for ProductData with entity id: %d", expected.getKey()),
					expectedData.getShortDescription(), actualData.getShortDescription());
			assertEquals(
					String.format("LongDescription mismatch for ProductData with entity id: %d", expected.getKey()),
					expectedData.getLongDescription(), actualData.getLongDescription());
			assertEquals(String.format("CG Program mismatch for ProductData with entity id: %d", expected.getKey()),
					expectedData.getCgProgram(), actualData.getCgProgram());

			assertEquals(
					String.format("Size mismatch for ProductData.gradeLevels with entity id: %d", expected.getKey()),
					expectedData.getGradeLevels().size(), actualData.getGradeLevels().size());
			for (String expectedGradeLevel : expectedData.getGradeLevels()) {
				assertTrue(String.format("Grade Level \"%s\" not found in ProductData with entity id: %d",
						expectedGradeLevel, expected.getKey()), actualData.getGradeLevels()
						.contains(expectedGradeLevel));
			}
		}
	}
}
