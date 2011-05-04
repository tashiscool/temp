package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.exception.LicensedProductExceptionFactory.getFaultMessage;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.LicensedProductExceptionFactory;
import com.pearson.ed.lp.exception.LicensedProductExceptionMessageCode;
import com.pearson.ed.lp.exception.OrderLineNotFoundException;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderLifeCycleClientImpl.class);

	private WebServiceTemplate serviceClient;
	
	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;

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
				String faultMessage = getFaultMessage(exception.getWebServiceMessage());
				if(faultMessage.contains("Required object not found")) {
					throw new OrderLineNotFoundException(
							exceptionFactory.findExceptionMessage(
									LicensedProductExceptionMessageCode.LP_EXC_0004.toString()), 
									new Object[]{orderLineItemId}, exception);
				} else {
					throw new ExternalServiceCallException(exception.getMessage(), null, exception);
				}
			} catch (Exception exception) {
				throw new ExternalServiceCallException(exception.getMessage(), null, exception);
			}

			if (orderLineItemResponse != null) {
				// find the right order line item
				for (ReadOrderLineType orderLineItem : orderLineItemResponse.getOrder().getOrderLineItems()
						.getOrderLine()) {
					if (orderLineItem.getOrderLineItemId().equals(orderLineItemId)) {
						if(orderLineItem.getOrderedISBN() != null) {
							responsePayload.put(orderLineItemId, orderLineItem.getOrderedISBN());
						}
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

	public LicensedProductExceptionFactory getExceptionFactory() {
		return exceptionFactory;
	}

	public void setExceptionFactory(LicensedProductExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

}
