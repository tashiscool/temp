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
import com.pearson.ed.lp.message.OrderLineItemRequest;
import com.pearson.ed.lp.message.OrderLineItemResponse;

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
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNByOrderLineItemId(com.pearson.ed.lp.message.OrderLineItemRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNByOrderLineItemIdHappyPathOneOrderId() {
		String dummyOrderId = "dummy-order-id";
		String dummyIsbn = "0123456789012";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withPayload(generateDummyOrderResponse(dummyOrderId, dummyIsbn, true)));

		OrderLineItemRequest request = new OrderLineItemRequest();
		request.setOrderLineItemId(dummyOrderId);
		OrderLineItemResponse response = testClient.getOrderedISBNByOrderLineItemId(request);

		mockServer.verify();

		assertNotNull(response);
		assertEquals(dummyOrderId, response.getOrderLineItemId());
		assertEquals(dummyIsbn, response.getOrderedISBN());
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNByOrderLineItemId(com.pearson.ed.lp.message.OrderLineItemRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNByOrderLineItemIdHappyPathMoreThanOneOrderItemsInResponse() {
		String dummyOrderId = "dummy-order-id";
		String dummyIsbn = "0123456789012";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withPayload(generateDummyOrderResponseMultipleItems(dummyOrderId, dummyIsbn, true)));

		OrderLineItemRequest request = new OrderLineItemRequest();
		request.setOrderLineItemId(dummyOrderId);
		OrderLineItemResponse response = testClient.getOrderedISBNByOrderLineItemId(request);

		mockServer.verify();

		assertNotNull(response);
		assertEquals(dummyOrderId, response.getOrderLineItemId());
		assertEquals(dummyIsbn, response.getOrderedISBN());
		
		assertNotNull(response);
		assertEquals(dummyOrderId, response.getOrderLineItemId());
		assertEquals(dummyIsbn, response.getOrderedISBN());
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNByOrderLineItemId(com.pearson.ed.lp.message.OrderLineItemRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNByOrderLineItemIdNonSpecificClientFault() {
		String dummyOrderId = "dummy-order-id";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withClientOrSenderFault("Bad service! No cookie!", Locale.ENGLISH));

		OrderLineItemRequest request = new OrderLineItemRequest();
		request.setOrderLineItemId(dummyOrderId);
		
		try {
			testClient.getOrderedISBNByOrderLineItemId(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNByOrderLineItemId(com.pearson.ed.lp.message.OrderLineItemRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNByOrderLineItemIdNonSpecificIOException() {
		String dummyOrderId = "dummy-order-id";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withException(new IOException("Bad service! No cookie!")));

		OrderLineItemRequest request = new OrderLineItemRequest();
		request.setOrderLineItemId(dummyOrderId);
		
		try {
			testClient.getOrderedISBNByOrderLineItemId(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNByOrderLineItemId(com.pearson.ed.lp.message.OrderLineItemRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNByOrderLineItemIdBadOrderLineItemId() {
		String dummyOrderId = "dummy-order-id";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withClientOrSenderFault("Order Exception - code:ORLC0006, description:Required object not found, " +
						"message:com.pearson.ed.order.exception.RequiredObjectNotFound", 
						Locale.ENGLISH));

		OrderLineItemRequest request = new OrderLineItemRequest();
		request.setOrderLineItemId(dummyOrderId);
		
		try {
			testClient.getOrderedISBNByOrderLineItemId(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(OrderLineNotFoundException.class));
		}

		mockServer.verify();
	}

}
