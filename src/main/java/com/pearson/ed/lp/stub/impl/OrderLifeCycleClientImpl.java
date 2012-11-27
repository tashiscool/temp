package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.exception.LicensedProductExceptionFactory.getFaultMessage;

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
import com.pearson.ed.lp.message.OrderLineItemRequest;
import com.pearson.ed.lp.message.OrderLineItemResponse;
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
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private WebServiceTemplate serviceClient;
	
	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;

	public void setServiceClient(final WebServiceTemplate serviceClient) {
		this.serviceClient = serviceClient;
	}

	public WebServiceTemplate getServiceClient() {
		return serviceClient;
	}

	public void setExceptionFactory(final LicensedProductExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

	/**
	 * Get ISBN number associated with the given order id by calling the OrderLifeCycle service.
	 * Implements {@link OrderLifeCycleClient#getOrderedISBNByOrderLineItemId(OrderLineItemRequest)}.
	 * 
	 * @param request
	 *            OrderLineItemRequest that contains order line item id
	 * @return OrderLineItemResponse ISBN string for the given order line item id
	 * @throws AbstractRumbaException on service error
	 */
	public OrderLineItemResponse getOrderedISBNByOrderLineItemId(final OrderLineItemRequest request)
		throws AbstractRumbaException
	{

		OrderLineItemResponse response = new OrderLineItemResponse();
		String orderLineItemId = request.getOrderLineItemId();
		
		GetOrderLineItemByIdRequest orderLineItemRequest = new GetOrderLineItemByIdRequest();
		orderLineItemRequest.setOrderLineItemId(orderLineItemId);
		response.setOrderLineItemId(orderLineItemId);

		GetOrderLineItemByIdResponse orderLineItemResponse = null;

		try {
			orderLineItemResponse =
				(GetOrderLineItemByIdResponse) serviceClient.marshalSendAndReceive(orderLineItemRequest);

		} catch (SoapFaultClientException exception) {
			logger.error("exception invoking GetOrderLineItemById for " + orderLineItemId, exception);

			String faultMessage = getFaultMessage(exception.getWebServiceMessage());

			if (faultMessage.contains("Required object not found")) {
				throw new OrderLineNotFoundException(
					exceptionFactory.findExceptionMessage(
						LicensedProductExceptionMessageCode.LP_EXC_0004),
						new Object[]{orderLineItemId},
						exception
					);
			} else {
				throw new ExternalServiceCallException(exception.getMessage(), null, exception);
			}
		} catch (Exception exception) {
			logger.error("exception invoking GetOrderLineItemById for " + orderLineItemId, exception);
			
			throw new ExternalServiceCallException(exception.getMessage(), null, exception);
		}

		if (orderLineItemResponse != null) {
			// find the right order line item
			for (ReadOrderLineType orderLineItem : orderLineItemResponse.getOrder().getOrderLineItems().getOrderLine()) {
				if (orderLineItem.getOrderLineItemId().equals(orderLineItemId)) {
					if(orderLineItem.getOrderedISBN() != null) {
						response.setOrderedISBN(orderLineItem.getOrderedISBN());
					}
					break;
				}
			}
		}

		return response;
	}
}
