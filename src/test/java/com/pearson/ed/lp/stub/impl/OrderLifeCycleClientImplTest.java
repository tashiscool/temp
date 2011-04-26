/**
 * 
 */
package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.ltg.rumba.common.test.XmlUtils.marshalToSource;
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

import javax.xml.transform.Source;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.test.client.MockWebServiceServer;

import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.OrderLineNotFoundException;
import com.pearson.ed.lp.exception.RequiredObjectNotFoundException;
import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.OrderLineItemsResponse;
import com.pearson.rws.order.doc._2009._02._09.GetOrderLineItemByIdRequest;
import com.pearson.rws.order.doc._2009._02._09.GetOrderLineItemByIdResponse;
import com.pearson.rws.order.doc._2009._02._09.ReadOrderLineItemsListType;
import com.pearson.rws.order.doc._2009._02._09.ReadOrderLineType;
import com.pearson.rws.order.doc._2009._02._09.ReadOrderType;

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
		BasicConfigurator.configure();

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

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrderLifeCycleClientImpl#getOrderedISBNsByOrderLineItemIds(com.pearson.ed.lp.message.OrderLineItemsRequest)}
	 * .
	 */
	@Test
	public void testGetOrderedISBNsByOrderLineItemIdsOrderLineItemWithoutIsbn() {
		String dummyOrderId = "dummy-order-id";

		mockServer.expect(payload(generateDummyOrderRequest(dummyOrderId))).andRespond(
				withPayload(generateDummyOrderResponseMultipleItems(dummyOrderId, null, true)));

		OrderLineItemsRequest request = new OrderLineItemsRequest();
		request.getOrderLineItemIds().add(dummyOrderId);
		
		try {
			testClient.getOrderedISBNsByOrderLineItemIds(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(RequiredObjectNotFoundException.class));
		}

		mockServer.verify();
	}

	/**
	 * Generate a Source object representing an xml request to the OrderLifeCycle service's GetOrderLineItemById
	 * function.
	 * 
	 * @param requestedOrderLineItemId
	 *            dummy order line item id string
	 * @return {@link Source} instance
	 */
	private Source generateDummyOrderRequest(String requestedOrderLineItemId) {
		GetOrderLineItemByIdRequest request = new GetOrderLineItemByIdRequest();
		request.setOrderLineItemId(requestedOrderLineItemId);

		return marshalToSource(marshaller, request);
	}

	/**
	 * Generate a Source object representing an (optionally) valid xml response from the OrderLifeCycle service's
	 * GetOrderLineItemById function.
	 * 
	 * @param requestedOrderLineItemId
	 *            dummy order line item id string
	 * @param dummyIsbn
	 *            dummy isbn string
	 * @param valid
	 *            if the response should be valid (i.e. have an OrderedISBN value set)
	 * @return {@link Source} instance
	 */
	private Source generateDummyOrderResponse(String requestedOrderLineItemId, String dummyIsbn, boolean valid) {
		GetOrderLineItemByIdResponse response = new GetOrderLineItemByIdResponse();
		ReadOrderType order = new ReadOrderType();
		response.setOrder(order);
		order.setOrderId(requestedOrderLineItemId);
		ReadOrderLineItemsListType orderLineItems = new ReadOrderLineItemsListType();
		order.setOrderLineItems(orderLineItems);

		if (valid) {
			ReadOrderLineType orderLineItem = new ReadOrderLineType();
			orderLineItems.getOrderLine().add(orderLineItem);
			orderLineItem.setOrderLineItemId(requestedOrderLineItemId);
			orderLineItem.setOrderedISBN(dummyIsbn);
		}

		return marshalToSource(marshaller, response);
	}

	/**
	 * Generate a Source object representing an (optionally) valid xml response from the OrderLifeCycle service's
	 * GetOrderLineItemById function. This response has multiple line items with only one having the right id.
	 * 
	 * @param requestedOrderLineItemId
	 *            dummy order line item id string
	 * @param dummyIsbn
	 *            dummy isbn string
	 * @param valid
	 *            if the response should be valid (i.e. have an OrderedISBN value set)
	 * @return {@link Source} instance
	 */
	private Source generateDummyOrderResponseMultipleItems(String requestedOrderLineItemId, String dummyIsbn,
			boolean valid) {
		GetOrderLineItemByIdResponse response = new GetOrderLineItemByIdResponse();
		ReadOrderType order = new ReadOrderType();
		response.setOrder(order);
		order.setOrderId(requestedOrderLineItemId);
		ReadOrderLineItemsListType orderLineItems = new ReadOrderLineItemsListType();
		order.setOrderLineItems(orderLineItems);

		if (valid) {
			ReadOrderLineType orderLineItem = new ReadOrderLineType();
			orderLineItems.getOrderLine().add(orderLineItem);
			orderLineItem.setOrderLineItemId(requestedOrderLineItemId);
			orderLineItem.setOrderedISBN(dummyIsbn);

			orderLineItem = new ReadOrderLineType();
			orderLineItems.getOrderLine().add(orderLineItem);
			orderLineItem.setOrderLineItemId("NOT-" + requestedOrderLineItemId);
		}

		return marshalToSource(marshaller, response);
	}
}
