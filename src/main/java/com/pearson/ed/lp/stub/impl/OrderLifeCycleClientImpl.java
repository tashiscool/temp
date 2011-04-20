package com.pearson.ed.lp.stub.impl;

import java.util.Map;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.OrderLineItemsResponse;
import com.pearson.ed.lp.stub.api.OrderLifeCycleClient;
import com.pearson.rws.order.doc._2009._02._09.GetOrderLineItemByIdRequest;
import com.pearson.rws.order.doc._2009._02._09.GetOrderLineItemByIdResponse;
import com.pearson.rws.order.doc._2009._02._09.ReadOrderLineType;

/**
 * Web Service Client stub implementation of the {@link OrderLifeCycleClient} interface. Wraps an instance of the
 * {@link WebServiceTemplate} class pointing to the OrderLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public class OrderLifeCycleClientImpl implements OrderLifeCycleClient {

	private WebServiceTemplate serviceClient;

	// private LicensedProductExceptionFactory exceptionFactory;

	/**
	 * Get all ISBN numbers associated with the given order ids by calling the OrderLifeCycle service.
	 * Implements {@link OrderLifeCycleClient#getOrderedISBNsByOrderLineItemIds(OrderLineItemsRequest)}.
	 * 
	 * @param request
	 *            OrderLineItemsRequest wrapping a list of order line item ids
	 * @return OrderLineItemsResponse mapping ISBN strings to associated order line item ids
	 * @throws AbstractRumbaException on service error
	 */
	public OrderLineItemsResponse getOrderedISBNsByOrderLineItemIds(OrderLineItemsRequest request)
			throws AbstractRumbaException {

		OrderLineItemsResponse response = new OrderLineItemsResponse();
		Map<String, String> responsePayload = response.getOrderedISBNsByOrderLineItemIds();

		// we have to loop for each OrderLineItemId since this is a one-at-a-time request, NOT ideal
		for (String orderLineItemId : request.getOrderLineItemIds()) {
			GetOrderLineItemByIdRequest orderLineItemRequest = new GetOrderLineItemByIdRequest();
			orderLineItemRequest.setOrderLineItemId(orderLineItemId);

			GetOrderLineItemByIdResponse orderLineItemResponse = null;

			try {
				orderLineItemResponse = (GetOrderLineItemByIdResponse) serviceClient
						.marshalSendAndReceive(orderLineItemRequest);
			} catch (SoapFaultClientException exception) {
				// TODO
			} catch (Exception exception) {
				// TODO
				// throw new ExternalServiceCallException(exception.getMessage());
			}

			if (orderLineItemResponse != null) {
				// find the right order line item
				for (ReadOrderLineType orderLineItem : orderLineItemResponse.getOrder().getOrderLineItems()
						.getOrderLine()) {
					if (orderLineItem.getOrderLineItemId().equals(orderLineItemId)) {
						responsePayload.put(orderLineItemId, orderLineItem.getOrderedISBN());
						break;
					}
				}
			}
		}

		return response;
	}

	public WebServiceTemplate getServiceClient() {
		return serviceClient;
	}

	public void setServiceClient(WebServiceTemplate serviceClient) {
		this.serviceClient = serviceClient;
	}

}
