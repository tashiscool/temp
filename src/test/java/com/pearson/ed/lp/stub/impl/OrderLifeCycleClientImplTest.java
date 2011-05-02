/**
 * 
 */
package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.LicensedProductTestHelper.generateDummyOrderRequest;
import static com.pearson.ed.lp.LicensedProductTestHelper.generateDummyOrderResponse;
import static com.pearson.ed.lp.LicensedProductTestHelper.generateDummyOrderResponseMultipleItems;
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
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.test.client.MockWebServiceServer;

import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.OrderLineNotFoundException;
import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.OrderLineItemsResponse;

/**
 * Unit test of {@link OrderLifeCycleClientImpl} using Spring WS mock objects.
 * 
 * @author ULLOYNI
 * 
 */
public class OrderLifeCycleClientImplTest extends BaseLicensedProductClientStubTest {

	@Autowired(required = true)
	private OrderLifeCycleClientImpl testClient;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockServer = MockWebServiceServer.createServer(testClient.getServiceClient());
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNsByOrderLineItemIds(com.pearson.ed.lp.message.OrderLineItemsRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNsByOrderLineItemIdsHappyPathOneOrderId() {
		String dummyOrderId = "dummy-order-id";
		String dummyIsbn = "0123456789012";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withPayload(generateDummyOrderResponse(dummyOrderId, dummyIsbn, true)));

		OrderLineItemsRequest request = new OrderLineItemsRequest();
		request.getOrderLineItemIds().add(dummyOrderId);
		OrderLineItemsResponse response = testClient.getOrderedISBNsByOrderLineItemIds(request);

		mockServer.verify();

		assertNotNull(response);
		assertEquals(1, response.getOrderedISBNsByOrderLineItemIds().size());
		assertTrue(response.getOrderedISBNsByOrderLineItemIds().containsKey(dummyOrderId));
		assertEquals(dummyIsbn, response.getOrderedISBNsByOrderLineItemIds().get(dummyOrderId));
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNsByOrderLineItemIds(com.pearson.ed.lp.message.OrderLineItemsRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNsByOrderLineItemIdsHappyPathMoreThanOneOrderItemsInResponse() {
		String dummyOrderId = "dummy-order-id";
		String dummyIsbn = "0123456789012";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withPayload(generateDummyOrderResponseMultipleItems(dummyOrderId, dummyIsbn, true)));

		OrderLineItemsRequest request = new OrderLineItemsRequest();
		request.getOrderLineItemIds().add(dummyOrderId);
		OrderLineItemsResponse response = testClient.getOrderedISBNsByOrderLineItemIds(request);

		mockServer.verify();

		assertNotNull(response);
		assertEquals(1, response.getOrderedISBNsByOrderLineItemIds().size());
		assertTrue(response.getOrderedISBNsByOrderLineItemIds().containsKey(dummyOrderId));
		assertEquals(dummyIsbn, response.getOrderedISBNsByOrderLineItemIds().get(dummyOrderId));
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNsByOrderLineItemIds(com.pearson.ed.lp.message.OrderLineItemsRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNsByOrderLineItemIdsNonSpecificClientFault() {
		String dummyOrderId = "dummy-order-id";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withClientOrSenderFault("Bad service! No cookie!", Locale.ENGLISH));

		OrderLineItemsRequest request = new OrderLineItemsRequest();
		request.getOrderLineItemIds().add(dummyOrderId);
		
		try {
			testClient.getOrderedISBNsByOrderLineItemIds(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNsByOrderLineItemIds(com.pearson.ed.lp.message.OrderLineItemsRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNsByOrderLineItemIdsNonSpecificIOException() {
		String dummyOrderId = "dummy-order-id";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withException(new IOException("Bad service! No cookie!")));

		OrderLineItemsRequest request = new OrderLineItemsRequest();
		request.getOrderLineItemIds().add(dummyOrderId);
		
		try {
			testClient.getOrderedISBNsByOrderLineItemIds(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNsByOrderLineItemIds(com.pearson.ed.lp.message.OrderLineItemsRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNsByOrderLineItemIdsBadOrderLineItemId() {
		String dummyOrderId = "dummy-order-id";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withClientOrSenderFault("Order Exception - code:ORLC0006, description:Required object not found, " +
						"message:com.pearson.ed.order.exception.RequiredObjectNotFound", 
						Locale.ENGLISH));

		OrderLineItemsRequest request = new OrderLineItemsRequest();
		request.getOrderLineItemIds().add(dummyOrderId);
		
		try {
			testClient.getOrderedISBNsByOrderLineItemIds(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(OrderLineNotFoundException.class));
		}

		mockServer.verify();
	}

}
